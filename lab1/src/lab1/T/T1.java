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
 * файл: ./src/lab1/T/T1.java
 * Даний файл містить код потоку T1
 */

package lab1.T;

import java.util.concurrent.BrokenBarrierException;

import lab1.Data.Data;
import lab1.Data.Matrix;

public class T1 extends Thread {

	private final int minH;
	private final int maxH;
	private final Data data;

	private int q1;
	private int d1;
	private Matrix MB1;
	private Matrix MC1;
	
	public T1(int H, Data data) {
		this.minH = 0;
		this.maxH = H - 1;
		this.data = data;
	}

	@Override
	public void run() {
		Matrix MB;
		// Введення матриці MB
		try {
			// Синхронізація введення - використання механізму synchronized критичних секцій
			synchronized (data.getInputSyncObj()) {
				MB = data.createMatrix("MB");
				data.setMB(MB);
			}
		} catch (Exception ex) {
			System.out.println("Неможливо продовжити виконання потоку T1 - " + ex);
			return;
		}

		// Сигнал про завершення введення - використання семафора
		data.getT1InputSemaphore().release(data.getRestThreads());

		// Очікування на введення в інших потоках - використання семафорів
		try {
			data.getT3InputSemaphore().acquire();
			data.getT4InputSemaphore().acquire();
		} catch (InterruptedException ex) {
			System.out.println("Неможливо продовжити виконання потоку T1 - " + ex);
			return;
		}

		// Крок обчислення 1
		q1 = data.getZ().getPartialVector(minH, maxH).min();
		// КД1 - використання атомарної змінної
		// Крок обчислення 2
		data.getQ().set(data.getQ().get() > q1 ? q1 : data.getQ().get());

		// Очікування на завершення кроків обчислення 1-2 в іншиш потоках - використання бар'єра
		try {
			data.getQBarrier().await();
		} catch (InterruptedException | BrokenBarrierException ex) {
			System.out.println("Неможливо продовжити виконання потоку T1 - " + ex);
			return;
		}

		// Копіювання даних
		// КД2 - використання атомарної змінної
		q1 = data.getQ().get();
		// КД3 - використання атомарної змінної
		d1 = data.getD().get();
		// КД4 - використання семафора
		try {
			data.getMBSemaphore().acquire();
			MB1 = MB.getCopy();
		} catch (InterruptedException ex) {
			System.out.println("Неможливо продовжити виконання потоку T1 - " + ex);
			return;
		} finally {
			data.getMBSemaphore().release();
		}
		// КД5 - використання механізму Lock критичних секцій
		data.getMCLock().lock();
		MC1 = data.getMC().getCopy();
		data.getMCLock().unlock();

		// Крок обчислення 3
		data.getMR().insertIntoIndexes(minH, maxH,
				MB1.getMatrixSum(MC1).getMatrixProduct(data.getMM().getPartialMatrix(minH, maxH)).getNumberProduct(d1)
						.getMatrixSum(data.getMC().getPartialMatrix(minH, maxH).getNumberProduct(q1)));

		// Сигнал про завершення обчислень - використання семафора
		data.getT1FinishSemaphore().release();
	}
}
