package lab0.T;

import java.util.Arrays;

import lab0.Data.Data;
import lab0.Data.DataMath;
import lab0.Data.MatrixData;
import lab0.Data.VectorData;

public class T1 implements Runnable {

	@Override
	public void run() {
		VectorData vd = new VectorData();
		MatrixData md = new MatrixData();
		
		System.out.println("Функція F1 - математичний вираз: D = (SORT(A + B) + C) * (MA * MB)");
		
		// D = (SORT(A + B) + C) * (MA * MB)
		
		int n = 10;
		
		Data data = new Data();
		int[] A = data.generateVector(n);
		int[] B = data.generateVector(n);
		int[] C = data.generateVector(n);
		
		int[][] MA = data.generateMatrix(n);
		int[][] MB = data.generateMatrix(n);

		int[] D =  vd.getMatrixMultiplyProduct(vd.getVectorSum(vd.sort(vd.getVectorSum(A, B, n)), C, n), md.getMatrixMultiplyProduct(MA, MB, n), n);		
		System.out.println("Функція F1 - результуючий вектор:");
		for (int i = 0; i < n; i++) {
			System.out.print(D[i] + " ");
		}
		System.out.println();

	}

}
