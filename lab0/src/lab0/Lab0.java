/*
 * Лабораторна робота 0 ЛР0, Варіанти - 1.14, 2.23, 3.3
 * F1: D = (SORT(A + B) + C) * (MA * MB)
 * F2: q = MAX(MH * MK - ML)
 * F3: O = SORT(P)*(MR*MT)
 * Омельченко І. ІП-04
 * Дата відправлення: 10.03.2023 
 * 
 * файл: ./src/lab0/Lab0.java
 * Даний файл містить головну процедуру
 */

package lab0;

import java.util.concurrent.Semaphore;

import lab0.T.*;

public class Lab0 {

	public static void main(String[] args) {
		Semaphore inOutSemaphore = new Semaphore(1);
		Thread t1 = new T1(inOutSemaphore);
		Thread t2 = new T2(inOutSemaphore);
		Thread t3 = new T3(inOutSemaphore);
		
		t1.start();
		t2.start();
		t3.start();

		try {
			t1.join();
		} catch (InterruptedException ex) {
			System.out.println("Виконання потоку T1 було перерване: " + ex.getMessage());
		}
		try {
			t2.join();
		} catch (InterruptedException ex) {
			System.out.println("Виконання потоку T2 було перерване: " + ex.getMessage());
		}
		try {
			t3.join();	
		} catch (InterruptedException ex) {
			System.out.println("Виконання потоку T3 було перерване: " + ex.getMessage());
		}
	}
}