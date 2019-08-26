package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;

//과제!!!
//날짜 출력!!!
//작성일 : 2019.08.26
//작성자 : 안태영
public class UDPTimeServer {

	public static final int PORT = 8000;
	public static final int BUFFER_SIZE = 1024;

	public static void main(String[] args) {
		DatagramSocket socket = null;
		SimpleDateFormat format = null;

		try {
			// 1. socket 생성
			socket = new DatagramSocket(PORT);

			while (true) {
				// 2. data 수신
				DatagramPacket receivePacket = new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE);
				socket.receive(receivePacket); // Blocking

				// 3. data 처리(확인)
				byte[] messageData = receivePacket.getData();
				int length = receivePacket.getLength();

				// 3-1. 날짜 데이터 처리
				format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a");
				String message = new String(messageData, 0, length, "UTF-8");

				//Enter와 문자를 비교해서 다르게 처리하는 구문!!!
				if (message.equals("")) {
					message = format.format(new Date());
					System.out.println("[UDP Time Echo] Time received :" + message);
				} else {
					System.out.println("[UDP Echo Server] received:" + message);
				}

				// 4. data 전송
				byte[] sendData = message.getBytes("UTF-8");

				// 편지봉투와 같은 역활!
				DatagramPacket sendPack = new DatagramPacket(sendData, sendData.length, receivePacket.getAddress(),
						receivePacket.getPort());
				socket.send(sendPack);
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (socket != null && socket.isClosed() == false) {
				socket.close();
			}
		}
	}

}
