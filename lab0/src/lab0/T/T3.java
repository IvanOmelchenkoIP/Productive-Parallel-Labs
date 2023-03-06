/*
 * Лабораторна робота 0 ЛР0, Варіанти - 1.14, 2.23, 3.3
 * F1: D = (SORT(A + B) + C) * (MA * MB)
 * F2: q = MAX(MH * MK - ML)
 * F3: O = SORT(P)*(MR*MT)
 * Омельченко І. ІП-04
 * Дата відправлення: 07.03.2023 
 * 
 * файл: ./src/lab0/T/T3.java
 * Даний файл містить клас потоку T3 для обчислення функції F3: O = SORT(P)*(MR*MT)
 */

package lab0.T;

import java.io.IOException;

import lab0.Data.Data;
import lab0.Data.Vector;
import lab0.Data.Matrix;

public class T3 extends Thread {
	
	@Override
	public void run() {
		Data data = new Data("F3");

		System.out.println("Функція F3 - математичний вираз: O = SORT(P) * (MR * MT)");
		int N = data.getUserN();		
		Vector P;
		Matrix MR;
		Matrix MT;
		try {
			P = data.createVector("P", N);
			MR = data.createMatrix("MR", N);
			MT = data.createMatrix("MT", N);
		} catch (IOException ex) {
			System.out.println("Потік Т1 - неможливо продовжити виконання! Помилка при зчитувані файлу: " + ex.getMessage());
			return;
		} catch (Exception ex) {
			System.out.println("Потік Т1 - неможливо продовжити виконання! " + ex.getMessage());
			return;
		} finally {
			data.closeInput();
		}
		Vector O = P.sort().getMatrixMultiplyProduct(MR.getMatrixMultiplyProduct(MT));
						
		System.out.println("Функція F3 - результуючий вектор:");
		System.out.println(O.toString());
	}

}
