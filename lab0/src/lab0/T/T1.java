/*
 * Лабораторна робота 0 ЛР0, Варіанти - 1.14, 2.23, 3.3
 * F1: D = (SORT(A + B) + C) * (MA * MB)
 * F2: q = MAX(MH * MK - ML)
 * F3: O = SORT(P)*(MR*MT)
 * Омельченко І. ІП-04
 * Дата відправлення: 10.03.2023 
 * 
 * файл: ./src/lab0/T/T1.java
 * Даний файл містить клас потоку T1 для обчислення функції F1: D = (SORT(A + B) + C) * (MA * MB)
 */

package lab0.T;

import java.io.IOException;

import lab0.Data.Data;
import lab0.Data.Vector;
import lab0.Data.Matrix;

public class T1 extends Thread {

	@Override
	public void run() {
		Data data = new Data("F1");
		Vector A;
		Vector B;
		Vector C;
		Matrix MA;
		Matrix MB;

		System.out.println("Функція F1 - математичний вираз: D = (SORT(A + B) + C) * (MA * MB)");
		try {
			data.setUserInputType();
		} catch (IOException ex) {
			System.out.println("Потік Т1 - неможливо продовжити виконання! Помилка при читанні файлу: " + ex.getMessage());
			return;
		} catch (Exception ex) {
			System.out.println("Потік Т1 - неможливо продовжити виконання! " + ex.getMessage());
			data.closeInput();
			return;
		}

		try {
			Thread.sleep(500);
		} catch (InterruptedException ex) {
			System.out.println("Потік Т1 - неможливо продовжити виконання! " + ex.getMessage());
			return;
		}

		try {
			A = data.createVector("A");
			B = data.createVector("B");
			C = data.createVector("C");
			MA = data.createMatrix("MA");
			MB = data.createMatrix("MB");
		} catch (Exception ex) {
			System.out.println("Потік Т1 - неможливо продовжити виконання! " + ex.getMessage());
			return;
		} finally {
			data.closeInput();
		}

		try {
			Thread.sleep(500);
		} catch (InterruptedException ex) {
			System.out.println("Потік Т1 - неможливо продовжити виконання! " + ex.getMessage());
			return;
		}

		Vector D = A.getVectorSum(B).sort().getVectorSum(C).getMatrixMultiplyProduct(MA.getMatrixMultiplyProduct(MB));

		try {
			Thread.sleep(500);
		} catch (InterruptedException ex) {
			System.out.println("Потік Т1 - неможливо продовжити виконання! " + ex.getMessage());
			return;
		}

		System.out.println("Функція F1 - результуючий вектор:");
		System.out.println(D.toString());
		System.out.println("Виконання потоку T1 завершено...");
	}
}
