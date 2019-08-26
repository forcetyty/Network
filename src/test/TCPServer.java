package test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import javax.swing.text.TabableView;

public class TCPServer {

	// 서버!!!
	private static final int PORT = 5000; // final 변수는 대문자로 작성하는게 관례!!!

	public static void main(String[] args) {
		ServerSocket serverSocket = null;

		try {
			// 1. 서버소켓 생성
			serverSocket = new ServerSocket();

			// 1-1. Time-Wait 상태에서 서버 소켓을 즉시 사용하기 위해서!!!!
			serverSocket.setReuseAddress(true);

			// 2. Binding
			// Socket에 SocketAddress(IPAddress + port)
			// 바인딩한다.
			// 127.0.0.1 - loop back Address -> 주소 -> 자기주소!!!
			InetAddress inetAddress = InetAddress.getLocalHost();
			String localhostAddress = inetAddress.getHostAddress();
			InetSocketAddress inetSocketAddress = new InetSocketAddress(inetAddress, PORT);
			serverSocket.bind(inetSocketAddress);
			System.out.println("[TCPServer] binding" + inetAddress.getHostAddress() + ":" + PORT);

			// 3. accept:
			// 클아이언트로 부터 연결요청(Connet)을 기다린다.

			// serverSocket.accept(); // Blocking
			// System.out.println("Hello~");

			Socket socket = serverSocket.accept(); // Blocking
			// Downcasting
			InetSocketAddress inetRemoteSocketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
			String remoteHostAddress = inetRemoteSocketAddress.getAddress().getHostAddress();
			int remoteHostPort = inetRemoteSocketAddress.getPort();

			System.out.println("[TCPServer] connected from client[" + remoteHostAddress + ":" + remoteHostPort + "]");

			try {
				// 4. IOStream 받아오기
				InputStream is = socket.getInputStream();
				OutputStream os = socket.getOutputStream();

				while (true) {

					// 5. 데이터 읽기
					byte[] buffer = new byte[256];
					int readByteCount = is.read(buffer); // Blocking
					if (readByteCount == -1) {
						// 정상종료 : Remote Socket이 close()
						// 메소드를 통해서 정상적으로 소켓을 닫은 경우
						System.out.println("[TCPServer] closed by client");
						break;
					}

					String data = new String(buffer, 0, readByteCount, "UTF-8");
					System.out.print("[TCPServer] received : " + data);

					// 6. 데이터 쓰기
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					os.write(data.getBytes("UTF-8"));
				}
			} catch (SocketException e) {
				e.printStackTrace();
				System.out.println("[TCPServer] abnormal closed by client");
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				// 7. Server socket 자원정리
				if (socket != null && socket.isClosed() == false) {
					socket.close();
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			// 서버 소켓을 예외처리해야 된다.
			e.printStackTrace();
		} finally {
			// 자원정리
			try {
				if (serverSocket != null && serverSocket.isClosed() == false) {
					serverSocket.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
