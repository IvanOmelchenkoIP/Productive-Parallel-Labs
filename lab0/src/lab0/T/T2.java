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

import java.io.IOException;

import lab0.Data.Data;
import lab0.Data.Matrix;


public class T2 extends Thread {
	
	@Override
	public void run() {
		Data data = new Data("F2");

		System.out.println("Функція F2 - математичний вираз: q = MAX(MH * MK - ML)");
		int n = data.getUserN();
		Matrix MH;
		Matrix MK;
		Matrix ML;
		try {
			MH = data.createMatrix("MH", n);
			MK = data.createMatrix("MK", n);
			ML = data.createMatrix("ML", n);
		} catch (IOException ex) {
			System.out.println("Потік Т1 - неможливо продовжити виконання! Помилка при зчитувані файлу: " + ex.getMessage());
			return;
		} catch (Exception ex) {
			System.out.println("Потік Т1 - неможливо продовжити виконання! " + ex.getMessage());
			return;
		} finally {
			data.closeInput();
		}
		int q = MH.getMatrixMultiplyProduct(MK).getMatrixDifference(ML).max();
						
		System.out.println("Функція F2 - результуюче число:");
		System.out.println(q + "\n");
	}

}
