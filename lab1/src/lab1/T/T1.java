package lab1.T;

public class T1 extends Thread {

	private final int P;
	
	public T1(int P) {
		this.P = P;
	}
	
	@Override
	public void run() {
		System.out.println("T1");
	}

}
