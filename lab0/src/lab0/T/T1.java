package lab0.T;

import lab0.Data.MatrixData;
import lab0.Data.UserInputProcessor;
import lab0.Data.VectorData;

public class T1 implements Runnable {

	@Override
	public void run() {
		VectorData vd = new VectorData();
		MatrixData md = new MatrixData();
		UserInputProcessor ui = new UserInputProcessor();
		
		System.out.println("Функція F1 - математичний вираз: D = (SORT(A + B) + C) * (MA * MB)");
		
		// D = (SORT(A + B) + C) * (MA * MB)
		
		int n = ui.getUserN();
		
		int[] A;
		int[] B;
		int[] C;
		int[][] MA;
		int[][] MB;
		try {
			A = ui.createVector(n, "A");
			B = ui.createVector(n, "B");
			C = ui.createVector(n, "C");
			MA = ui.createMatrix(n, "MA");
			MB = ui.createMatrix(n, "MB");
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return;
		} finally {
			ui.close();
		}
		
		int[] D =  vd.getMatrixMultiplyProduct(vd.getVectorSum(vd.sort(vd.getVectorSum(A, B, n)), C, n), md.getMatrixMultiplyProduct(MA, MB, n), n);		
		
		System.out.println("Функція F1 - результуючий вектор:");
		for (int i = 0; i < n; i++) {
			System.out.print(D[i] + " ");
		}
		System.out.println();
	}
}
