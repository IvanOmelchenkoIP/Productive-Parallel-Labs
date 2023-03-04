package lab0.T;

import lab0.Data.Data;
import lab0.Data.DataMath;
import lab0.Data.MatrixData;

public class T2 implements Runnable {
	
	@Override
	public void run() {
		MatrixData md = new MatrixData();
		
		System.out.println("Функція F2 - математичний вираз: q = MAX(MH * MK - ML)");
		
		// q = MAX(MH * MK - ML)
		int n = 10;
		
		Data data = new Data();
		
		int[][] MH = data.generateMatrix(n);
		int[][] MK = data.generateMatrix(n);
		int[][] ML = data.generateMatrix(n);
		
		//int q = DataMath.max(DataMath.substractMatrixes(n, DataMath.multiplyMatrixes(n, MH, MK), ML));
		
		int q = md.max(md.getMatrixDifference(md.getMatrixMultiplyProduct(MH, MK, n), ML, n));
		
		System.out.println("Функція F1 - результуюче число:");
		System.out.println(q);
	}

}
