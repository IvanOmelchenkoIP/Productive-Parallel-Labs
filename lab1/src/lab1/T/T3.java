package lab1.T;

public class T3 extends Thread {

	private final int P;
	
	public T3(int P) {
		this.P = P;
	}
	
	@Override
	public void run() {
		System.out.println("T3");
		
	}

}
