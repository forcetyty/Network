package thread;

public class MutithreadEx03 {
	
	public static void main(String[] args) {
		Thread thread1 = new DigitThread();
		Thread thread2 = new AlphabetThread();
		
		//기존에 구현된 코드에 Thread를 태우겠다면 아래와 같이 인터페이스로 만들어서 구현해주면 된다.
		Thread thread3 = new Thread(new UppercaseAlphabetRunnableImpl());
		
		thread1.start();
		thread2.start();
		thread3.start();
	}

}
