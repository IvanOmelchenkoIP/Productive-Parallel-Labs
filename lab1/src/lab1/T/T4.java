package lab1.T;

import lab1.Data.Matrix;
import lab1.Data.Vector;

public class T4 extends Thread {

	private final int N;
	private final int P;
	
	public T4(int MIN_N, int P) {
		this.N = MIN_N;
		this.P = P;
	}
	
	@Override
	public void run() {
		Vector Z;
		int d;
		Matrix MM;
		
		int MIN_H = (N / P) * 3 - 1;
		int MAX_H = N - 1;
		
		System.out.println("T4");
	}
}
