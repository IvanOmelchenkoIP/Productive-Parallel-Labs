/*
 * Лабораторна робота 0 ЛР0, Варіанти - 1.14, 2.23, 3.3
 * F1: D = (SORT(A + B) + C) * (MA * MB)
 * F2: q = MAX(MH * MK - ML)
 * F3: O = SORT(P)*(MR*MT)
 * Омельченко І. ІП-04
 * Дата відправлення: 10.03.2023 
 * 
 * файл: ./src/lab0/T/T2.java
 * Даний файл містить клас потоку T2 для обчислення функції F2: q = MAX(MH * MK - ML)
 */

package lab0.T;

import java.io.IOException;
import java.util.concurrent.Semaphore;

import lab0.Data.Data;
import lab0.Data.Matrix;

public class T2 extends Thread {

	@Override
	public void run() {
		Data data = new Data("F2");
		Matrix MH;
		Matrix MK;
		Matrix ML;

		System.out.println("Функція F2 - математичний вираз: q = MAX(MH * MK - ML)");
		try {
			data.setUserInputType();
			MH = data.createMatrix("MH");
			MK = data.createMatrix("MK");
			ML = data.createMatrix("ML");
		} catch (IOException ex) {
			System.out.println(
					"Потік Т2 - неможливо продовжити виконання! Помилка при читанні файлу: " + ex.getMessage());
			return;
		} catch (Exception ex) {
			System.out.println("Потік Т2 - неможливо продовжити виконання! " + ex.getMessage());
			return;
		}
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException ex) {
			System.out.println("Потік Т1 - неможливо продовжити виконання! " + ex.getMessage());
			return;
		}
		
		int q = MH.getMatrixMultiplyProduct(MK).getMatrixDifference(ML).max();
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException ex) {
			System.out.println("Потік Т1 - неможливо продовжити виконання! " + ex.getMessage());
			return;
		}

		System.out.println("Функція F2 - результуюче число:");
		System.out.println(q + "\n");
		System.out.println("Виконання потоку T2 завершено...\n");
	}
}
