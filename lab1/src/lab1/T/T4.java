package lab1.T;

public class T4 extends Thread {

	private final int P;
	
	public T4(int P) {
		this.P = P;
	}
	
	@Override
	public void run() {
		System.out.println("T4");
		
	}

}
