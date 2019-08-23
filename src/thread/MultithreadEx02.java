package thread;

public class MultithreadEx02 {
	
	public static void main(String[] args) {
		//Thread라는 객체만 만들어 놓은것!!!
		//그렇기 때문에 실행되는 것은 아니다.
		Thread thread1 = new DigitThread();
		Thread thread2 = new AlphabetThread();
		
		thread1.start();
		thread2.start();
		
		
	}

}
