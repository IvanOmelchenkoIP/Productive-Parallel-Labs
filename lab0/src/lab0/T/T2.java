/*
 * Лабораторна робота 0 ЛР0, Варіанти - 1.14, 2.23, 3.3
 * F1: D = (SORT(A + B) + C) * (MA * MB)
 * F2: q = MAX(MH * MK - ML)
 * F3: O = SORT(P)*(MR*MT)
 * Омельченко І. ІП-04
 * Дата відправлення: 06.03.2023 
 * 
 * файл: ./src/lab0/T/T2.java
 * Потік: T2
 */

package lab0.T;

import lab0.Data.MatrixData;
import lab0.Data.UserInputProcessor;

public class T2 extends Thread {
	
	@Override
	public void run() {
		MatrixData md = new MatrixData();
		UserInputProcessor ui = new UserInputProcessor();

		System.out.println("Функція F2 - математичний вираз: q = MAX(MH * MK - ML)");
		
		// q = MAX(MH * MK - ML)
		int n = ui.getUserN();
		
		int[][] MH;
		int[][] MK;
		int[][] ML;
		try {
			MH = ui.createMatrix(n, "MH");
			MK = ui.createMatrix(n, "MK");
			ML = ui.createMatrix(n, "ML");
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return;
		} finally {
			ui.close();
		}
			
		int q = md.max(md.getMatrixDifference(md.getMatrixMultiplyProduct(MH, MK, n), ML, n));
		
		System.out.println("Функція F1 - результуюче число:");
		System.out.println(q);
	}

}
