package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ChatServerThread extends Thread {

	private String nickname = null;
	private Socket socket = null;
	private List<Writer> listWriters = null;

	public ChatServerThread(Socket socket, List<Writer> listWriters) {
		this.socket = socket;
		this.listWriters = listWriters;
	}

	@Override
	public void run() {
		try {

			// I/O Stream 생성
			BufferedReader buffereedReader = new BufferedReader(
					new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));

			PrintWriter printWriter = new PrintWriter(
					new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));

			while (true) {
				String request = buffereedReader.readLine();

				if (request == null) {
					consoleLog("클라이언트로부터 연결 끊김");
					doQuit(printWriter);
					break;
				}

				String[] tokens = request.split(":");

				String[] mutter = tokens[1].split("=");
				// String[] mtokens = request.split("=");

				System.out.println(request);

				if ("join".equals(tokens[0])) {
					// Join
					doJoin(tokens[1], printWriter);
				} else if ("message".equals(tokens[0])) {

					// Message 전달
					if (mutter.length != 2) {
						doMessage(tokens[1]);
						System.out.println(tokens[1]);
					}

					else {
						//귓속말 구현 메소드 & 프로토콜
						System.out.println("귓속말");
						personMessage(mutter[0], mutter[1]);
					}

				} else if ("quit".equals(tokens[0])) {
					doQuit(printWriter);
				}
			}
		} catch (IOException e) {
			consoleLog(this.nickname + "님이 채팅방을 나갔습니다.");
		}
	}

	private void doQuit(PrintWriter writer) {
		removeWriter(writer);
		String data = this.nickname + "님이 퇴장했습니다.";
		broadcast(data);
	}

	private void removeWriter(PrintWriter writer) {
		synchronized (listWriters) {
			listWriters.remove(writer);
		}
	}

	private void doMessage(String data) {
		broadcast(this.nickname + ":" + data);
	}

	// ndate - name information
	// sdate - chatting content
	private void personMessage(String ndata, String sdata) {
		personcast(ndata + "=" + sdata);
		System.out.println("personMessage = " + ndata + "=" + sdata);
	}

	private void doJoin(String nickname, Writer writer) {
		this.nickname = nickname;
		String data = nickname + "님이 입장하였습니다.";

		broadcast(data);
		addWriter(writer);

	}

	private void addWriter(Writer writer) {
		synchronized (listWriters) {
			listWriters.add(writer);
		}
	}

	private void broadcast(String data) {
		synchronized (listWriters) {
			for (Writer writer : listWriters) {
				PrintWriter printWriter = (PrintWriter) writer;
				printWriter.println(data);
				printWriter.flush();
			}
		}
	}

	private void personcast(String data) {

		for (Writer writer : listWriters) {
			PrintWriter printWriter = (PrintWriter) writer;
			printWriter.println(data);
			printWriter.flush();
		}

	}

	private void consoleLog(String log) {
		System.out.println(log);
	}
}
