package lab1.T;

import lab1.Data.Matrix;

public class T2 extends Thread {

	private final int N;
	private final int P;
	
	public T2(int N, int P) {
		this.N = N;
		this.P = P;
	}
	
	@Override
	public void run() {
		Matrix MR;
		
		int MIN_H = N / P - 1;
		int MAX_H = (N / P) * 2 - 1;
		
		System.out.println("Результат виконання обчислень. MR = ");
		
	}
}
