package lab1.T;

import lab1.Data.Matrix;

public class T1 extends Thread {

	private final int N;
	private final int H;
	
	public T1(int N, int H) {
		this.N = N;
		this.H = H;
	}
	
	@Override
	public void run() {
		Matrix MB;
		
		int MIN_H = 0;
		int MAX_H = H - 1;
		
		System.out.println("T1");
	}
}
