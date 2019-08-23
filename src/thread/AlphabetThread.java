package thread;

public class AlphabetThread extends Thread {

	@Override
	public void run() {
		// for문은 선점이 어렵다!!!
		for (char c = 'a'; c < 'z'; c++) {
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
