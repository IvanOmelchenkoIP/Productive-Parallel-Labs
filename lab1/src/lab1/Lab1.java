/*
 * Лабораторна робота 1 ЛР1, Варіант - 10
 * MR = MB*(MC*MM)*d + min(Z)*MC
 * Введенні і виведення даних:
 * T1: MB
 * T2: MR
 * T3: MC
 * F3: d, Z, MM
 * Омельченко І. ІП-04
 * Дата відправлення: 15.04.2023 
 * 
 * файл: ./src/lab1/Lab1.java
 * Даний файл містить головну процедуру
 */

package lab1;

import java.io.IOException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import lab1.Data.Data;
import lab1.T.T1;
import lab1.T.T2;
import lab1.T.T3;
import lab1.T.T4;

public class Lab1 {

	public static void main(String[] args) {
		final int P = 4;		
		final int N;
		
		Data data = new Data();
		try {
			N = data.setUserN();
		} catch (IOException ex) {
			System.out.println(ex);
			return;
		} catch (Exception ex) {
			System.out.println(ex);
			return;
		};
		
		long start = System.currentTimeMillis();
		final int H = N / P;		
		Thread t1 = new T1(H, data);
		Thread t2 = new T2(H, data);
		Thread t3 = new T3(H, data);
		Thread t4 = new T4(H, data);
		
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
