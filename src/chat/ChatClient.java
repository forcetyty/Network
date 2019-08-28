package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ChatClient {
	private static String SERVER_IP = "127.0.0.1";
	private static final int SERVER_PORT = 5000;

	public static void main(String[] args) {
		String name = null;
		Scanner scanner = new Scanner(System.in);

		while (true) {

			System.out.println("대화명을 입력하세요.");
			System.out.print(">>> ");
			name = scanner.nextLine(); // 닉네임이 지정!!!

			if (name.isEmpty() == false) {
				break;
			}

			System.out.println("대화명은 한글자 이상 입력해야 합니다.\n");
		}

		// 1. Socket 생성
		Socket socket = new Socket();
		try {

			// 2. Connect to Server
			socket.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT));
			consoleLog("채팅방에 입장하였습니다.");

			BufferedReader br = new BufferedReader(
					new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));

			// 3. Create I/O Stream
			new ChatClientThread(socket, br).start();

			// 4. Join Protocol
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8),
					true);
			String request = "join:" + name;

			pw.println(request);
			pw.flush();

			// 5. Chatting input
			while (true) {

				String message = scanner.nextLine();

				if ("quit".equals(message)) {
					pw.println("quit:");
					break;
				}

				pw.println("message:" + message);
				pw.flush();
			}

			scanner.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void consoleLog(String log) {
		System.out.println(log);
	}

}
