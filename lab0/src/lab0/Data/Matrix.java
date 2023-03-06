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
	private int size;
	private int[][] data;
	
	public Matrix(int[][] data) {
		this.data = data;
		this.size = data.length;
	}
	
	public int[][] getData() {
		return data;
	}
	
	public static Matrix fromString(String matrix, int size) {
		int[][] data = new int[size][size];		
		String[] lines = matrix.trim().split("\n");
		for (int i = 0; i < size; i++) {
			String[] elements = lines[i].split(" ");
			for (int j = 0; j < size; j++) {
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
		int[][] dataMC = new int[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				dataMC[i][j] = data[i][j] - dataMB[i][j];
			}
		}
		return new Matrix(dataMC);
	}

	public Matrix getMatrixMultiplyProduct(Matrix MB) {
		int[][] dataMB = MB.getData();
		int[][] dataMC = new int[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				for (int k = 0; k < size; k++)
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
