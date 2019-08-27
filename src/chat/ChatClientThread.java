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
	String name = null;
	PrintWriter pw = null;
	Scanner scanner;
	BufferedReader br;

	public ChatClientThread(Socket socket, BufferedReader br) {
		this.socket = socket;
		this.br = br;
	}

	public void run() {

		// 서버로 부터 받는것!!!
		// 서버로 부터 받기 때문에 클라이언트 쓰레드로 병행처리 하는 것!
		try {
			while (true) {
				String request = br.readLine();
				System.out.println("<<" + request);

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
