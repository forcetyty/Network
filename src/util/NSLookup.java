package util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

/*
 * 과제 : NSLookup
 * 제출자 : 안태영
 */

public class NSLookup {

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		String address = null;

		try {
			while (true) {
				System.out.println(">>");
				address = scanner.nextLine();

				
				if (address.equals("exit")) {
					System.out.println("종료");
					break;
				}
				
				InetAddress[] inetAddresses = InetAddress.getAllByName(address);

				for (InetAddress inetAddress : inetAddresses) {
					System.out.println(address + ":" + inetAddress.getHostAddress());
				}

				
			}

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
