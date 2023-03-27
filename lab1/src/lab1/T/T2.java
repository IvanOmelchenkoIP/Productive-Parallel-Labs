package lab1.T;

public class T2 extends Thread {

	private final int P;
	
	public T2(int P) {
		this.P = P;
	}
	
	@Override
	public void run() {
		System.out.println("T2");
		
	}

}
