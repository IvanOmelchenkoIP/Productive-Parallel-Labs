package lab0.Data;

import java.util.ArrayList;
import java.util.List;

public class DataMath {

	public static double[] addVectors(int N, double[] A, double[] B) {
		double[] C = new double[N];
		for (int i = 0; i < N; i++) {
			C[i] = A[i] + B[i];
		}
		return C;
	}

	public static double[] multiplyMatrixAndVector(int N, double[][] MA, double[] A) {
		double[] B = new double[N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				B[i] += MA[i][j] * A[j];
			}
		}
		return B;
	}

	public static double[][] substractMatrixes(int N, double[][] MA, double[][] MB) {
		double[][] MC = new double[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				MC[i][j] = MA[i][j] - MB[i][j];
			}
		}
		return MC;
	}

	public static double[][] multiplyMatrixes(int N, double[][] MA, double[][] MB) {
		double[][] MC = new double[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				for (int k = 0; k < N; k++)
					MC[i][j] += MA[i][k] * MB[k][j];
			}
		}
		return MC;
	}

	public static double max(double[][] MA) {
		List<Double> B = new ArrayList<Double>();
		for (double[] A : MA) {
			for (double a : A) {
				B.add(a);
			}
		}
		double max = B.remove(0);
		for (double b : B) {
			if (b > max) {
				max = b;
			}
		}
		return max;
	}
}
