/*
 * Лабораторна робота 0 ЛР0, Варіанти - 1.14, 2.23, 3.3
 * F1: D = (SORT(A + B) + C) * (MA * MB)
 * F2: q = MAX(MH * MK - ML)
 * F3: O = SORT(P)*(MR*MT)
 * Омельченко І. ІП-04
 * Дата відправлення: 10.03.2023 
 * 
 * файл: ./src/lab0/Data/Vector.java
 * Даний файл містить тип даних Vector
 */

package lab0.Data;

import java.util.Arrays;

public class Vector {
	private int N;
	private int[] data;
	
	public Vector(int[] data) {
		this.data = data;
		this.N = data.length;
	}
	
	public int[] getData() {
		return data;
	}
	
	public static Vector fromString(String vector, int N) {
		int[] data = new int[N];
		String[] elements = vector.trim().split("");
		for (int i = 0; i < N; i++) {
			data[i] = Integer.parseInt(elements[i]);
		}
		return new Vector(data);
	}
	
	@Override
	public String toString() {
		String MA = "";
		for (int num : data) {
			MA += num + " ";
		}
		MA += "\n";
		return MA;
	}
	
	public Vector getVectorSum(Vector B) {
		int[] dataB = B.getData();
		int[] dataC = new int[N];
		for (int i = 0; i < N; i++) {
			dataC[i] = data[i] + dataB[i];
		}
		return new Vector(dataC);
	}
	
	public Vector getMatrixMultiplyProduct(Matrix MA) {
		int[][] dataMA = MA.getData();
		int[] dataB = new int[N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				dataB[i] += dataMA[i][j] * data[j];
			}
		}
		return new Vector(dataB);
	}
	
	public Vector sort() {
		Arrays.sort(data);
		return this;
	}
}
