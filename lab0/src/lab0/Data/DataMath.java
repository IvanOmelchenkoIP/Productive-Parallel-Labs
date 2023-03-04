package lab0.Data;

import java.util.ArrayList;
import java.util.List;

public class DataMath {

	public static int[] addVectors(int n, int[] A, int[] B) {
		int[] C = new int[n];
		for (int i = 0; i < n; i++) {
			C[i] = A[i] + B[i];
		}
		return C;
	}

	public static int[] multiplyMatrixAndVector(int n, int[][] MA, int[] A) {
		int[] B = new int[n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				B[i] += MA[i][j] * A[j];
			}
		}
		return B;
	}

	public static int[][] substractMatrixes(int[][] MA, int[][] MB, int size) {
		int[][] MC = new int[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				MC[i][j] = MA[i][j] - MB[i][j];
			}
		}
		return MC;
	}

	public static int[][] multiplyMatrixes(int[][] MA, int[][] MB, int size) {
		int[][] MC = new int[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				for (int k = 0; k < size; k++)
					MC[i][j] += MA[i][k] * MB[k][j];
			}
		}
		return MC;
	}

	public static int max(int[][] MA) {
		List<Integer> B = new ArrayList<Integer>();
		for (int[] A : MA) {
			for (int a : A) {
				B.add(a);
			}
		}
		int max = B.remove(0);
		for (int b : B) {
			if (b > max) {
				max = b;
			}
		}
		return max;
	}
	
	
	
}
