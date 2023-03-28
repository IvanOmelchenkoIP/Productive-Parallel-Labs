package lab1.T;

import lab1.Data.CommonData;
import lab1.Data.Data;
import lab1.Data.SyncData;
import lab1.Data.matrix.Matrix;
import lab1.Data.vector.Vector;

public class T4 extends Thread {

	private final int N;
	private final int H;

	private final CommonData cd;
	private final Data data;
	private final SyncData syncData;

	public T4(int N, int H, CommonData cd, Data data, SyncData syncData) {
		this.N = N;
		this.H = H;
		this.cd = cd;
		this.data = data;
		this.syncData = syncData;
	}

	@Override
	public void run() {
		
		syncData.getT4Input().release(3);
		
		try {
			syncData.getT1Input().acquire(1);
			syncData.getT3Input().acquire(1);
		} catch (InterruptedException ex) {
			System.out.println(ex);
		}
		/*int d;
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

		// -------------
		int q4 = cd.retrieveZ().getPartialVector(MIN_H, MAX_H).min();
		int q = cd.retrieveQ();
		cd.setQ(q > q4 ? q4 : q);
		// -------------W
		Matrix MR = cd.retrieveMR();
		if (MR == null) {
			MR = Matrix.cleanMarix(N);
			cd.setMR(MR);
		}
		MR.insertIntoIndexes(MIN_H, MAX_H, cd.retrieveMB()
				.getMatrixProduct(cd.retrieveMC().getMatrixProduct(cd.retrieveMM().getPartialMatrix(MIN_H, MAX_H)))
				.getNumberProduct(cd.retrieveD())
				.getMatrixSum(cd.retrieveMC().getPartialMatrix(MIN_H, MAX_H).getNumberProduct(cd.retrieveQ())));
		// -------------W*/

		System.out.println("T4");
	}
}
