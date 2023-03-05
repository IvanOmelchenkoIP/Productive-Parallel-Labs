/*
 * Лабораторна робота 0 ЛР0, Варіанти - 1.14, 2.23, 3.3
 * F1: D = (SORT(A + B) + C) * (MA * MB)
 * F2: q = MAX(MH * MK - ML)
 * F3: O = SORT(P)*(MR*MT)
 * Омельченко І. ІП-04
 * Дата відправлення: 06.03.2023 
 * 
 * файл: ./src/lab0/data/VectorData.java
 */

package lab0.Data;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class VectorData {
	
	public int[] generateRandom(int size) {
		int[] A = new int[size];
		for (int i = 0; i < size; i++) {
			A[i] = ThreadLocalRandom.current().nextInt();
		}
		return A;
	}
	
	public int[] fillWithNumber(int size, int num) {
		int[] A = new int[size];
		Arrays.fill(A, num);
		return A;
	}
	
	public int[] generateFromFileInput(String contents, int size, String vectorName) {
		String[] lines = contents.split("\n");
		int index = Arrays.asList(lines).indexOf(vectorName) + 1;
		String str = String.join("\n", Arrays.copyOfRange(lines, index, index + 1));
		return generateFromString(str, size);
	}
	
	private int[] generateFromString(String str, int size) {
		int[] A = new int[size];
		String[] elements = str.trim().split("");
		for (int i = 0; i < size; i++) {
			A[i] = Integer.parseInt(elements[i]);
		}
		return A;
	}
	
	
	public int[] getVectorSum(int[] A, int[] B, int size) {
		int[] C = new int[size];
		for (int i = 0; i < size; i++) {
			C[i] = A[i] + B[i];
		}
		return C;
	}
	
	public int[] getMatrixMultiplyProduct(int[] A, int[][] MA, int size) {
		int[] B = new int[size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				B[i] += MA[i][j] * A[j];
			}
		}
		return B;
	}
	
	public int[] sort(int[] A) {
		Arrays.sort(A);
		return A;
	}
}
