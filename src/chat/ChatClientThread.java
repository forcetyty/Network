package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ChatClientThread extends Thread {
	Socket socket = null;
	PrintWriter pw = null;
	Scanner scanner;
	BufferedReader br;
	String name;

	public ChatClientThread(Socket socket, BufferedReader br, String name) {
		this.socket = socket;
		this.br = br;
		this.name = name;
	}

	public void run() {

		// 서버로 부터 받는것!!!
		// 서버로 부터 받기 때문에 클라이언트 쓰레드로 병행처리 하는 것!
		try {
			while (true) {
				// BroadCast 되는 문자열을 읽어오는 기능!!!
				String request = br.readLine();
				//귓속말 구분!!!
				String[] req1 = request.split("=");
				if (req1.length == 2) {

					if (req1[0].equals(name)) {

						System.out.println("귓속말 =>" + req1[0] + "=" + req1[1]);

					}
				} else {
					System.out.println("<<" + request);
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
