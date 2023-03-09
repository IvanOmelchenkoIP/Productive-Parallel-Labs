/*
 * Лабораторна робота 0 ЛР0, Варіанти - 1.14, 2.23, 3.3
 * F1: D = (SORT(A + B) + C) * (MA * MB)
 * F2: q = MAX(MH * MK - ML)
 * F3: O = SORT(P)*(MR*MT)
 * Омельченко І. ІП-04
 * Дата відправлення: 10.03.2023 
 * 
 * файл: ./src/lab0/T/T3.java
 * Даний файл містить клас потоку T3 для обчислення функції F3: O = SORT(P)*(MR*MT)
 */

package lab0.T;

import java.io.IOException;
import java.util.concurrent.Semaphore;

import lab0.Data.Data;
import lab0.Data.Vector;
import lab0.Data.Matrix;

public class T3 extends Thread {

	private int maxSmallN;
	private Semaphore inOutSemaphore;
	
	public T3(Semaphore inOutSemaphore, int maxSmallN) {
		this.inOutSemaphore = inOutSemaphore;
		this.maxSmallN = maxSmallN;
	}
	
	@Override
	public void run() {
		Data data = new Data("F3", maxSmallN);
		int N;
		Vector P;
		Matrix MR;
		Matrix MT;
		
		try {
			inOutSemaphore.acquire();
		} catch (InterruptedException ex) {
			System.out.println("Потік Т1 - неможливо продовжити виконання! " + ex.getMessage());
			return;
		}
		
		System.out.println("Функція F3 - математичний вираз: O = SORT(P)*(MR*MT)");
		try {
			N = data.setUserInputType();
		} catch (IOException ex) {
			System.out.println("Потік Т3 - неможливо продовжити виконання! Помилка при читанні файлу: " + ex.getMessage());
			return;
		} catch (Exception ex) {
			System.out.println("Потік Т3 - неможливо продовжити виконання! " + ex.getMessage());
			return;
		}

		inOutSemaphore.release();
		
		if (N <= maxSmallN) {
			try {
				inOutSemaphore.acquire();
			} catch (InterruptedException ex) {
				System.out.println("Потік Т1 - неможливо продовжити виконання! " + ex.getMessage());
				return;
			}
		}

		try {
			P = data.createVector("P");
			MR = data.createMatrix("MR");
			MT = data.createMatrix("MT");
		} catch (Exception ex) {
			System.out.println("Потік Т3 - неможливо продовжити виконання! " + ex.getMessage());
			return;
		}
		
		if (!inOutSemaphore.tryAcquire()) {
			inOutSemaphore.release();
		}

		Vector O = P.sort().getMatrixMultiplyProduct(MR.getMatrixMultiplyProduct(MT));

		try {
			inOutSemaphore.acquire();
		} catch (InterruptedException ex) {
			System.out.println("Потік Т1 - неможливо продовжити виконання! " + ex.getMessage());
			return;
		}
		
		System.out.println("Функція F3 - результуючий вектор:");
		System.out.println(O.toString());
		System.out.println("Виконання потоку T3 завершено...\n");

		inOutSemaphore.release();
	}
}
