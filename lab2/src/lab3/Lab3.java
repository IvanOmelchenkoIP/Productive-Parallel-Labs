/*
 * Лабораторна робота 3 ЛР3, Варіант - 11
 * e = ( (p*(A*MB) )*( B*(MZ*MR) ) + min(B)
 * Введенні і виведення даних:
 * T1: MZ
 * T2: e, A, MR
 * T3: MB, B, p
 * T4: -
 * Омельченко І. ІП-04
 * Дата відправлення: 03.05.2023 
 * 
 * файл: ./src/lab3/Lab3.java
 * Даний файл містить головну процедуру
 */

package lab3;

import java.io.IOException;

import lab3.Data.Data;
import lab3.Data.DataCreators;
import lab3.T.T1;
import lab3.T.T2;
import lab3.T.T3;
import lab3.T.T4;

public class Lab3 {

	public static void main(String[] args) {
		final int THREADS = 4;
		DataCreators creators = new DataCreators(THREADS);		
		
		try {
			creators.config();
		} catch (IOException ex) {
			System.out.println(ex);
			return;
		} catch (Exception ex) {
			System.out.println(ex);
			return;
		}
		
		long start = System.currentTimeMillis();
		int N = creators.getN();
		Data data = new Data(THREADS, N);
		int H = N / THREADS;
		
		T1 t1 = new T1(H, data, creators);
		T2 t2 = new T2(H, data, creators);
		T3 t3 = new T3(H, data, creators);
		T4 t4 = new T4(H, data);
		
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		
		try {
			t1.join();
		} catch (InterruptedException ex) {
			System.out.println("Виконання потоку T1 було перервано: " + ex);
		}
		try {
			t2.join();
		} catch (InterruptedException ex) {
			System.out.println("Виконання потоку T2 було перервано: " + ex);
		}
		try {
			t3.join();
		} catch (InterruptedException ex) {
			System.out.println("Виконання потоку T3 було перервано: " + ex);
		}
		try {
			t4.join();
		} catch (InterruptedException ex) {
			System.out.println("Виконання потоку T4 було перервано: " + ex);
		}
		
		long ms = System.currentTimeMillis() - start;
		System.out.println("Час виконання програми - " + ms + " мс");
	}
}
