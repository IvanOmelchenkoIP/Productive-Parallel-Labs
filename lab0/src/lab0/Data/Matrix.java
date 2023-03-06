/*
 * Лабораторна робота 0 ЛР0, Варіанти - 1.14, 2.23, 3.3
 * F1: D = (SORT(A + B) + C) * (MA * MB)
 * F2: q = MAX(MH * MK - ML)
 * F3: O = SORT(P)*(MR*MT)
 * Омельченко І. ІП-04
 * Дата відправлення: 07.03.2023 
 * 
 * файл: ./src/lab0/Data/Matrix.java
 * Даний файл містить тип даних Matrix
 */

package lab0.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Matrix {
	private int N;
	private int[][] data;
	
	public Matrix(int[][] data) {
		this.data = data;
		this.N = data.length;
	}
	
	public int[][] getData() {
		return data;
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
	
	public Matrix getMatrixDifference(Matrix MB) {
		int[][] dataMB = MB.getData();
		int[][] dataMC = new int[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				dataMC[i][j] = data[i][j] - dataMB[i][j];
			}
		}
		return new Matrix(dataMC);
	}

	public Matrix getMatrixMultiplyProduct(Matrix MB) {
		int[][] dataMB = MB.getData();
		int[][] dataMC = new int[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				for (int k = 0; k < N; k++)
					dataMC[i][j] += data[i][k] * dataMB[k][j];
			}
		}
		return new Matrix(dataMC);
	}

	public int max() {
		List<Integer> values = new ArrayList<Integer>();
		for (int[] row : data) {
			for (int num : row) {
				values.add(num);
			}
		}
		return Collections.max(values);
	}	
}
