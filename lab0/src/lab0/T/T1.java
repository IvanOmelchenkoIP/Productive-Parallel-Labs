package lab0.T;

import java.util.Arrays;

import lab0.Data.Data;

public class T1 implements Runnable {

	double[] addVectors(double[] A, double[] B) {
		double[] C = new double[A.length];
		for (int i = 0; i < A.length; i++) {
			C[i] = A[i] + B[i];
		}
		return C;
	}

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
		System.out.println("F1");

		// D = (SORT(A + B) + C) * (MA * MB)
		Data data = new Data();
		double[] A = data.generateVector();
		double[] B = data.generateVector();
		double[] C = data.generateVector();

		A = addVectors(A, B);
		Arrays.sort(addVectors(A, B));
		A = addVectors(A, C);

		for (int i = 0; i < 10; i++) {
			System.out.println(A[i]);
		}

		double[][] MA = data.generateMatrix();
		double[][] MB = data.generateMatrix();
		MA = multiplyMatrixes(MA, MB);
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				System.out.print(MA[i][j] + " ");
			}

			System.out.println();
		}
		
		A = multiplyMatrixByVector(MA, A);
		for (int i = 0; i < 10; i++) {
			System.out.println(A[i]);
		}

	}

}
