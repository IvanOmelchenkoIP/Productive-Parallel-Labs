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
 * файл: ./src/lab1/T/T3.java
 * Даний файл містить код потоку T3
 */

package lab1.T;

import java.util.concurrent.BrokenBarrierException;

import lab1.Data.Data;
import lab1.Data.Matrix;

public class T3 extends Thread {

	private final int minH;
	private final int maxH;
	private final Data data;

	private int q3;
	private int d3;
	private Matrix MB3;
	private Matrix MC3;
	
	public T3(int H, Data data) {
		this.minH = H * 2;
		this.maxH = H * 3 - 1;
		this.data = data;
	}

	@Override
	public void run() {
		Matrix MC;
		try {
			// Синхронізація введення - використання механізму synchronized критичних секцій
			synchronized(data.getInputSyncObj()) {
				MC = data.createMatrix("MC");
				data.setMC(MC);
			}
		} catch (Exception ex) {
			System.out.println("Неможливо продовжити виконання потоку T3 - " + ex);
			return;
		}
		
		// Сигнал про завершення введення - використання семафора
		data.getT3InputSemaphore().release(data.getRestThreads());
		
		// Очікування на введення в інших потоках - використання семафорів
		try {
			data.getT1InputSemaphore().acquire();
			data.getT4InputSemaphore().acquire();
		} catch (InterruptedException ex) {
			System.out.println("Неможливо продовжити виконання потоку T3 - " + ex);
			return;
		}

		// Крок обчислення 1
		q3 = data.getZ().getPartialVector(minH, maxH).min();
		// КД1 - використання атомарної змінної
		// Крок обчислення 2
		data.getQ().set(data.getQ().get() > q3 ? q3 : data.getQ().get());
		
		// Очікування на завершення кроків обчислення 1-2 в іншиш потоках - використання бар'єра
		try {
			data.getQBarrier().await();
		} catch (InterruptedException | BrokenBarrierException ex) {
			System.out.println("Неможливо продовжити виконання потоку T3 - " + ex);
			return;
		}
		
		// Копіювання даних
		// КД2 - використання атомарної змінної
		q3 = data.getQ().get();
		// КД3 - використання атомарної змінної
		d3 = data.getD().get();
		// КД4 - використання семафора
		try {
			data.getMBSemaphore().acquire();
			MB3 = data.getMB().getCopy();
		} catch (InterruptedException ex) {
			System.out.println("Неможливо продовжити виконання потоку T3 - " + ex);
			return;
		} finally {
			data.getMBSemaphore().release();
		}
		// КД5 - використання механізму Lock критичних секцій
		data.getMCLock().lock();
		MC3 = MC.getCopy();
		data.getMCLock().unlock();

		// Крок обчислення 3
		data.getMR().insertIntoIndexes(minH, maxH,
				MB3.getMatrixSum(MC3).getMatrixProduct(data.getMM().getPartialMatrix(minH, maxH)).getNumberProduct(d3)
						.getMatrixSum(data.getMC().getPartialMatrix(minH, maxH).getNumberProduct(q3)));

		// Сигнал про завершення обчислень - використання семафора
		data.getT3FinishSemaphore().release();
	}
}
