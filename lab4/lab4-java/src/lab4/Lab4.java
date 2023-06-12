/*
 * Лабораторна робота 4 ЛР4, Варіант - 10
 * MR = MB*(MC*MM)*d + min(Z)*MC
 * Введенні і виведення даних:
 * T1: MB
 * T2: MR
 * T3: MC
 * T4: d, Z, MM
 * Омельченко І. ІП-04
 * Дата відправлення: 11.05.2023 
 * 
 * файл: ./src/lab4/Lab4.java
 * Даний файл містить головну процедуру
 */

package lab4;

import java.io.IOException;

import lab4.Data.Data;
import lab4.Data.UserDataManager;
import lab4.T.T1;
import lab4.T.T2;
import lab4.T.T3;
import lab4.T.T4;

public class Lab4 {

	public static void main(String[] args) {
		final int P = 4;	
		
		UserDataManager manager = new UserDataManager(P);
		
		try {
			manager.config();
		} catch (IOException ex) {
			System.out.println("Несожливо продовжити виконання програми - " + ex);
			return;
		} catch (Exception ex) {
			System.out.println("Несожливо продовжити виконання програми - " + ex);
			return;
		}
		
		int N = manager.getN();
		Data data = new Data(P, N);

		long start = System.currentTimeMillis();
		final int H = manager.getN() / P;		
		Thread t1 = new T1(H, data, manager);
		Thread t2 = new T2(H, data, manager);
		Thread t3 = new T3(H, data, manager);
		Thread t4 = new T4(H, data, manager);
		
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
