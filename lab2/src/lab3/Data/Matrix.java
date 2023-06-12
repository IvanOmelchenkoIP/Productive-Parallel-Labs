/*
 * Лабораторна робота 3 ЛР3, Варіант - 11
 * e = ( (p*(A*MB) )*( B*(MZ*MR) ) + min(B)
 * Введенні і виведення даних:
 * T1: MZ
 * T2: e, A, MR
 * T3: MB, B, p
 * T4: -
 * Омельченко І. ІП-04
 * Дата відправлення: 03.05.2023 
 * 
 * файл: ./src/lab3/Data/Matrix.java
 * Даний файл містить клас Matrix
 */

package lab3.Data;

public class Matrix {

	private int N;
	private int M;
	private int[][] data;
	
	public Matrix(int[][] data) {
		this.data = data;
		this.N = data.length;
		this.M = data[0].length;
	}
	
	public static Matrix fromString(String matrix, int N) {
		int[][] data = new int[N][N];		
		String[] lines = matrix.trim().split("\n");
		for (int i = 0; i < N; i++) {
			String[] elements = lines[i].split(" ");
			for (int j = 0; j < N; j++) {
				data[i][j] = Integer.parseInt(elements[j]);
			}
		}
		return new Matrix(data);
	}
	
	@Override
	public String toString() {
		String MA = "";
		for (int[] row : data) {
			for (int num : row) {
				MA += num + " ";
			}
			MA += "\n";
		}
		return MA;
	}
	
	public Matrix getCopy() {
		int[][] dataMB = new int[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				dataMB[i][j] = data[i][j];
			}
		}
		return new Matrix(dataMB);
	}
	
	public int[][] getData() {
		return data;
	}
	
	public int getN() {
		return N;
	}
	
	public int getM() {
		return M;
	}
	
	public Matrix getPartialMatrixByM(int start, int end) {
		int[][] dataMB = new int[N][end - start + 1];
		for (int i = 0; i < N; i++) {
			for (int j = start, k = 0; j <= end; j++, k++) {
				dataMB[i][k] = data[i][j];
			}
		}
		return new Matrix(dataMB);
	}
	
	public Matrix getPartialMatrixByN(int start, int end) {
		int[][] dataMB = new int[end - start + 1][M];
		for (int i = start, j = 0; i <= end; i++, j++) {
			for (int k = 0; k < M; k++) {
				dataMB[j][k] = data[i][k];
			}
		}
		return new Matrix(dataMB);
	}

	
	public Matrix getMatrixProduct(Matrix MB) {
		int[][] dataMB = MB.getData();
		int[][] dataMC = new int[N][MB.getM()];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < MB.getM(); j++) {
				for (int k = 0; k < MB.getN(); k++)
					dataMC[i][j] += data[i][k] * dataMB[k][j];
			}
		}
		return new Matrix(dataMC);
	}
	
	public void insertByM(Matrix MB, int start, int end) {
		int[][] dataB = MB.getData();
		for (int i = 0; i < N; i++) {
			for (int j = start, k = 0; j <= end; j++, k++) {
				data[i][j] = dataB[i][k];
			}
		}
	}
}
