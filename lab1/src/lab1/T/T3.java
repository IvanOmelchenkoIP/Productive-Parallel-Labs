package lab1.T;

import lab1.Data.CommonData;
import lab1.Data.Data;
import lab1.Data.Matrix;

public class T3 extends Thread {

	private final int N;
	private final int H;
	
	private final CommonData cd;
	private final Data data;
	
	public T3(int N, int H, CommonData cd, Data data) {
		this.N = N;
		this.H = H;
		this.cd = cd;
		this.data = data;
	}
	
	
	@Override
	public void run() {
		Matrix MC = data.newMatrix();
		cd.setMC(MC);
		
		int MIN_H = H * 2 - 1;
		int MAX_H = H * 3 - 1;
		
		System.out.println("T3");
		
	}
}
