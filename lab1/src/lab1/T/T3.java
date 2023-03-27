package lab1.T;

import lab1.Data.Matrix;

public class T3 extends Thread {

	private final int N;
	private final int P;
	
	public T3(int MIN_N, int P) {
		this.N = MIN_N;
		this.P = P;
	}
	
	@Override
	public void run() {
		Matrix MC;
		
		int MIN_H = (N / P) * 2 - 1;
		int MAX_H = (N / P) * 3 - 1;
		
		System.out.println("T3");
		
	}
}
