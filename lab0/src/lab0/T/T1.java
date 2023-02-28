package lab0.T;

import java.util.Arrays;

import lab0.Data.Data;
import lab0.Data.DataMath;

public class T1 implements Runnable {

	@Override
	public void run() {
		System.out.println("Функція F1 - математичний вираз: D = (SORT(A + B) + C) * (MA * MB)");
		
		// D = (SORT(A + B) + C) * (MA * MB)
		
		int n = 10;
		
		Data data = new Data();
		int[] A = data.generateVector(n);
		int[] B = data.generateVector(n);
		int[] C = data.generateVector(n);
		
		int[][] MA = data.generateMatrix(n);
		int[][] MB = data.generateMatrix(n);

		A = DataMath.addVectors(n, A, B);
		Arrays.sort(A);
		A = DataMath.addVectors(n, A, C);		
		int D[] = DataMath.multiplyMatrixAndVector(n, DataMath.multiplyMatrixes(n, MA, MB), A);
		
		System.out.println("Функція F1 - результуючий вектор:");
		for (int i = 0; i < n; i++) {
			System.out.print(D[i] + " ");
		}
		System.out.println();

	}

}
