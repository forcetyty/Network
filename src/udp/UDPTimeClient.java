package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.Scanner;

//과제!!!
//날짜 출력!!!
//작성일 : 2019.08.26
//작성자 : 안태영
public class UDPTimeClient {

	// 나중에 설정파일로 변경
		private static final String SERVER_IP = "192.168.1.18";

		public static void main(String[] args) {
			Scanner scanner = null;
			DatagramSocket socket = null;

			try {
				// 1. 키보드 연결
				scanner = new Scanner(System.in);

				// 2. 소켓 생성
				socket = new DatagramSocket();

				while (true) {
					// 3. 사용자 입력을 받음
					System.out.print(">>");
					String message = scanner.nextLine();

					if ("quit".equals(message)) {
						break;
					}

					// 4. 메시지 전송
					byte[] sendData = message.getBytes("UTF-8");

					DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,
							new InetSocketAddress(SERVER_IP, UDPEchoServer.PORT));
					socket.send(sendPacket);

					// 5. 메세지 수신
					DatagramPacket receivePacket = new DatagramPacket(new byte[UDPEchoServer.BUFFER_SIZE], UDPEchoServer.BUFFER_SIZE);
					socket.receive(receivePacket); // blocking

					byte[] data = receivePacket.getData();
					int length = receivePacket.getLength();
					message = new String(data, 0, length, "UTF-8");
					System.out.println("<<" + message);

				}
			} catch (IOException e) {
				// TODO: handle exception
				e.printStackTrace();
			} finally {
				if (scanner != null) {
					scanner.close();
				}

				if (scanner != null && socket.isClosed() == false) {
					socket.close();
				}
			}
		}

}
