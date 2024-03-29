package chat;

import java.io.IOException;
import java.net.InetAddress;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {

	public static final int PORT = 5000;
	public static final String IP = "127.0.0.1";
	
	public static List<Writer> listWriters = new ArrayList<Writer>();

	public static void main(String[] args) {

		ServerSocket serverSocket = null;
	
		try {
			// 1. 서버 소켓 생성
			serverSocket = new ServerSocket();

			// 2. 바인딩
			serverSocket.bind(new InetSocketAddress(IP, PORT));
			consoleLog("연결 기다림 - " + IP + ":" + PORT);

			// 3. 요청 대기
			while (true) {
				Socket socket = serverSocket.accept();
				new ChatServerThread(socket, listWriters).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (serverSocket != null && !serverSocket.isClosed()) {
					serverSocket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static void consoleLog(String log) {
		System.out.println("[server " + Thread.currentThread().getId() + "] " + log);
	}

}
