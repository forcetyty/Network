package thread;

public class MultithreadEx01 {
	
	//Single Thread
	public static void main(String[] args) {
		
		/*
		 * for(int i = 0; i < 10; i++) { System.out.print(i); }
		 */
		
		//Thread가 구현되어 있는 객체 생성
		Thread digitThred = new DigitThread();
		digitThred.start();
		
		//for문은 선점이 어렵다!!!
		for(char c = 'a'; c < 'z'; c++) {
			System.out.print(c);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
