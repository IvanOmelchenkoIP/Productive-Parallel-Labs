package lab1.T;

import lab1.Data.CommonData;
import lab1.Data.Data;
import lab1.Data.Matrix;
import lab1.Data.Vector;

public class T4 extends Thread {

	private final int N;
	private final int H;
	
	private final CommonData cd;
	private final Data data;
	
	public T4(int N, int H, CommonData cd, Data data) {
		this.N = N;
		this.H = H;
		this.cd = cd;
		this.data = data;
	}
	
	
	@Override
	public void run() {
		Vector Z = data.newVector();
		cd.setZ(Z);
		int d = data.newNumber();
		cd.setD(d);
		Matrix MM = data.newMatrix();
		cd.setMM(MM);
		
		int MIN_H = H * 3 - 1;
		int MAX_H = N - 1;
		
		System.out.println("T4");
	}
}
