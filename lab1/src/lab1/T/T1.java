package lab1.T;

import lab1.Data.CommonData;
import lab1.Data.Data;
import lab1.Data.matrix.Matrix;

public class T1 extends Thread {

	private final int N;
	private final int H;

	private final CommonData cd;
	private final Data data;

	public T1(int N, int H, CommonData cd, Data data) {
		this.N = N;
		this.H = H;
		this.cd = cd;
		this.data = data;
	}

	@Override
	public void run() {
		Matrix MB;
		try {
			MB = data.createMatrix("MB");
			cd.setMM(MB);
		} catch (Exception ex) {
			System.out.println("Неможливо продовжити виконання - " + ex);
			return;
		}
		int MIN_H = 0;
		int MAX_H = H - 1;
		
		
		//-------------
		int q1 = cd.retrieveZ().getPartialVector(MIN_H, MAX_H).min();
		int q = cd.retrieveQ();
		cd.setQ(q > q1 ? q1 : q);
		//-------------W
		Matrix MR = cd.retrieveMR();
		if (MR == null) {
			MR = Matrix.cleanMarix(N);
			cd.setMR(MR);
		}
		MR.insertIntoIndexes(MIN_H, MAX_H, cd.retrieveMB()
				.getMatrixProduct(cd.retrieveMC().getMatrixProduct(cd.retrieveMM().getPartialMatrix(MIN_H, MAX_H)))
				.getNumberProduct(cd.retrieveD())
				.getMatrixSum(cd.retrieveMC().getPartialMatrix(MIN_H, MAX_H).getNumberProduct(cd.retrieveQ())));
		//-------------W
		
		
		System.out.println("T1");
	}
}
