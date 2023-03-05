/*
 * Лабораторна робота 0 ЛР0, Варіанти - 1.14, 2.23, 3.3
 * F1: D = (SORT(A + B) + C) * (MA * MB)
 * F2: q = MAX(MH * MK - ML)
 * F3: O = SORT(P)*(MR*MT)
 * Омельченко І. ІП-04
 * Дата відправлення: 06.03.2023 
 * 
 * файл: ./src/lab0/T/T3.java
 * Потік: T3
 */

package lab0.T;

import lab0.Data.MatrixData;
import lab0.Data.UserInputProcessor;
import lab0.Data.VectorData;

public class T3 extends Thread {
	
	@Override
	public void run() {
		VectorData vd = new VectorData();
		MatrixData md = new MatrixData();
		UserInputProcessor ui = new UserInputProcessor();

		System.out.println("Функція F3 - математичний вираз: O = SORT(P) * (MR * MT)");
		
		int n = ui.getUserN();
		
		// O = SORT(P)*(MR*MT)
		
		int[] P;
		int[][] MR;
		int[][] MT;
		try {
			P = ui.createVector(n, "P");
			MR = ui.createMatrix(n, "MR");
			MT = ui.createMatrix(n, "MT");
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return;
		} finally {
			ui.close();
		}
		
		int[] O = vd.getMatrixMultiplyProduct(vd.sort(P), md.getMatrixMultiplyProduct(MR, MT, n), n);
		
		System.out.println("Функція F3 - результуючий вектор:");
		for (int i = 0; i < n; i++) {
			System.out.print(O[i] + " ");
		}
		System.out.println();
	}

}
