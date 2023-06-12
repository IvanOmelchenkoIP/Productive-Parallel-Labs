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
 * файл: ./src/lab3/Data/Vector.java
 * Даний файл містить клас Vector
 */

package lab3.Data;

public class Vector {

	private int N;
	private int[] data;
	
	public Vector(int[] data) {
		this.data = data;
		this.N = data.length;
	}
	
	public static Vector fromString(String vector, int N) {
		int[] data = new int[N];
		String[] elements = vector.trim().split("");
		for (int i = 0; i < N; i++) {
			data[i] = Integer.parseInt(elements[i]);
		}
		return new Vector(data);
	}
	
	public Vector getCopy() {
		int[] dataB = new int[N];
		for (int i = 0; i < N; i++) {
			dataB[i] = data[i];
		}
		return new Vector(dataB);
	}
	
	public int[] getData() {
		return data;
	}
	
	public Vector getPartialVector(int start, int end) {
		int[] dataB = new int[end - start + 1];
		for (int i = start, j = 0; i <= end; i++, j++) {
			dataB[j] = data[i];
		}
		return new Vector(dataB);
	}
	
	public Vector getNumberProduct(int a) {
		int[] dataB = new int[N];
		for (int i = 0; i < N; i++) {
			dataB[i] = a * data[i];
		}
		return new Vector(dataB);
	}
	
	public Vector getMatrixMultiplyProduct(Matrix MA) {
		int[][] dataMA = MA.getData();
		int[] dataB = new int[MA.getN()];
		for (int i = 0; i < MA.getN(); i++) {
			for (int j = 0; j < MA.getM(); j++) {
				dataB[i] += dataMA[i][j] * data[j];
			}
		}
		return new Vector(dataB);
	} 
	
	public int min() {
		int minimal = data[0];
		for (int i = 1; i < N; i++) {
			if (minimal > data[i]) {
				minimal = data[i];
			}
		}
		return minimal;
	}

	public int getVectorsToScalar(Vector B) {
		int[] dataB = B.getData();
		int result = 0;
		for (int i = 0; i < N; i++) {
			result += dataB[i] * data[i];
		}
		return result;
	}
	
	public void insert(Vector B, int start, int end) {
		int[] dataB = B.getData();
		for (int i = start, j = 0; i <= end; i++, j++) {
			data[i] = dataB[j];
		}
	}
	
	@Override
	public String toString() {
		String str = "";
		for (int i = 0; i < N; i++) {
			str += data[i] + " ";
		}
		return str;
	}
}
