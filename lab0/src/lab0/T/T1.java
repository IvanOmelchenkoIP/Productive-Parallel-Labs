/*
 * Лабораторна робота 0 ЛР0, Варіанти - 1.14, 2.23, 3.3
 * F1: D = (SORT(A + B) + C) * (MA * MB)
 * F2: q = MAX(MH * MK - ML)
 * F3: O = SORT(P)*(MR*MT)
 * Омельченко І. ІП-04
 * Дата відправлення: 07.03.2023 
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

		System.out.println("Функція F1 - математичний вираз: D = (SORT(A + B) + C) * (MA * MB)");	
		int N = data.getUserN();
		Vector A;
		Vector B;
		Vector C;
		Matrix MA;
		Matrix MB;
		try {
			A = data.createVector("A", N);
			B = data.createVector("B", N);
			C = data.createVector("C", N);
			MA = data.createMatrix("MA", N);
			MB = data.createMatrix("MB", N);
		} catch (IOException ex) {
			System.out.println("Потік Т1 - неможливо продовжити виконання! Помилка при зчитувані файлу: " + ex.getMessage());
			return;
		} catch (Exception ex) {
			System.out.println("Потік Т1 - неможливо продовжити виконання! " + ex.getMessage());
			return;
		} finally {
			data.closeInput();
		}
		Vector D =  A.getVectorSum(B).sort().getVectorSum(C).getMatrixMultiplyProduct(MA.getMatrixMultiplyProduct(MB));
		
		System.out.println("Функція F1 - результуючий вектор:");
		System.out.println(D.toString());
	}
}
