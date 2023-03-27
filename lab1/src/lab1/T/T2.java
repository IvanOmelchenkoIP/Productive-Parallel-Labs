package lab1.T;

import lab1.Data.CommonData;
import lab1.Data.Data;
import lab1.Data.matrix.Matrix;

public class T2 extends Thread {

	private final int N;
	private final int H;

	private final CommonData cd;
	private final Data data;

	public T2(int N, int H, CommonData cd, Data data) {
		this.N = N;
		this.H = H;
		this.cd = cd;
		this.data = data;
	}

	@Override
	public void run() {
		int MIN_H = H - 1;
		int MAX_H = H * 2 - 1;

		// -------------
		int q2 = cd.retrieveZ().getPartialVector(MIN_H, MAX_H).min();
		int q = cd.retrieveQ();
		cd.setQ(q > q2 ? q2 : q);
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
		// -------------W

		System.out.println("Результат виконання обчислень. MR = " + MR.toString());

	}
}
