package lab1.T;

import lab1.Data.CommonData;
import lab1.Data.Data;
import lab1.Data.matrix.Matrix;
import lab1.Data.vector.Vector;

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
		int d;
		Vector Z;
		Matrix MM;
		try {
			Z = data.createVector("Z");
			cd.setZ(Z);
			d = data.createNumber("d");
			cd.setD(d);
			MM = data.createMatrix("MM");
			cd.setMM(MM);
		} catch (Exception ex) {
			System.out.println("Неможливо продовжити виконання - " + ex);
			return;
		}
		
		int MIN_H = H * 3 - 1;
		int MAX_H = N - 1;
		
		System.out.println("T4");
	}
}
