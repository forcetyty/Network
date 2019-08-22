package test;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class LocalHost {
	
	public static void main(String[] args) {
		//inetAddress는 ip Address를 가져오는 것.
		//                       socketAddress
		//         InetAddress + port    +     Port    
		//                  ipAddress
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			String hostName  = inetAddress.getHostName();
			String hostAddress = inetAddress.getHostAddress(); //가상머신 주소가 찍힐수도 있다. ip protocol은 4Byte로 본다 
			
			System.out.println(hostName);
			System.out.println(hostAddress);
			
			byte[] ipAddress = inetAddress.getAddress();
			
			//****중요****
			//Casting을 해도 MSB(부호비트는 살려놓는다!!!)
			for(byte ipADDress : ipAddress) {
				//16진수 00 -> 1byte
				System.out.print(ipADDress & 0x000000ff);
				System.out.print(".");
			}
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
