package lab0.T;

import java.util.Arrays;

import lab0.Data.Data;

public class T3 implements Runnable {

	
	double[][] multiplyMatrixes(double[][] MA, double[][] MB) {
		double[][] MC = new double[10][10];
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				for (int k = 0; k < 10; k++)
					MC[i][j] += MA[i][k] * MB[k][j];
			}
		}
		return MC;
	}
	
	double[] multiplyMatrixByVector(double[][] MA, double[] A) {
		double[] B = new double[10];
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				B[i] += MA[i][j] * A[j];
			}
		}
		return B;
	}
	
	@Override
	public void run() {
		System.out.println("F3");
		
		// O = SORT(P) * (MR * MT)
		Data data = new Data();
		
		double[] P = data.generateVector();
		double[][] MR = data.generateMatrix();
		double[][] MT = data.generateMatrix();
		
		Arrays.sort(P);
		double[] O = multiplyMatrixByVector(multiplyMatrixes(MR, MT), P);
		
		for (int i = 0; i < 10; i++) {
			System.out.println(O[i]);
		}
	}

}
