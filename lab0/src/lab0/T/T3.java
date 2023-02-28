package lab0.T;

import java.util.Arrays;

import lab0.Data.Data;
import lab0.Data.DataMath;

public class T3 implements Runnable {
	
	@Override
	public void run() {
		System.out.println("Функція F3 - математичний вираз: O = SORT(P) * (MR * MT)");
		
		int n = 10;
		
		Data data = new Data();
		
		int[] P = data.generateVector(n);
		int[][] MR = data.generateMatrix(n);
		int[][] MT = data.generateMatrix(n);
		
		Arrays.sort(P);
		int[] O = DataMath.multiplyMatrixAndVector(n, DataMath.multiplyMatrixes(n, MR, MT), P);
		
		System.out.println("Функція F3 - результуючий вектор:");
		for (int i = 0; i < n; i++) {
			System.out.print(O[i] + " ");
		}
		System.out.println();
	}

}
