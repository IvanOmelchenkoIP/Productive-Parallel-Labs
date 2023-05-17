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
 * файл: ./src/lab1/T/T4.java
 * Даний файл містить код потоку T4
 */

package lab1.T;

import java.util.concurrent.BrokenBarrierException;

import lab1.Data.Data;
import lab1.Data.Matrix;
import lab1.Data.Vector;

public class T4 extends Thread {

	private final int minH;
	private final int maxH;
	private final Data data;
	
	private int q4;
	private int d4;
	private Matrix MB4;
	private Matrix MC4;
	
	public T4(int H, Data data) {
		this.minH = H * 3;
		this.maxH = H * 4 - 1;
		this.data = data;
	}

	@Override
	public void run() {
		int d;
		Vector Z;
		Matrix MM;
		try {
			// Синхронізація введення - використання механізму synchronized критичних секцій
			synchronized(data.getInputSyncObj()) {
				Z = data.createVector("Z");
				data.setZ(Z);
			}
			// Синхронізація введення - використання механізму synchronized критичних секцій
			synchronized(data.getInputSyncObj()) {
				d = data.createNumber("d");
				data.setD(d);
			}
			// Синхронізація введення - використання механізму synchronized критичних секцій
			synchronized(data.getInputSyncObj()) {
				MM = data.createMatrix("MM");
				data.setMM(MM);
			}
		} catch (Exception ex) {
			System.out.println("Неможливо продовжити виконання потоку T4 - " + ex);
			return;
		}
		
		// Сигнал про завершення введення - використання семафора
		data.getT4InputSemaphore().release(data.getRestThreads());
		
		// Очікування на введення в інших потоках - використання семафорів
		try {
			data.getT1InputSemaphore().acquire();
			data.getT3InputSemaphore().acquire();
		} catch (InterruptedException ex) {
			System.out.println("Неможливо продовжити виконання потоку T4 - " + ex);
			return;
		}

		// Крок обчислення 1
		q4 = data.getZ().getPartialVector(minH, maxH).min();
		// КД1 - використання атомарної змінної
		// Крок обчислення 2
		data.getQ().set(data.getQ().get() > q4 ? q4 : data.getQ().get());
		
		// Очікування на завершення кроків обчислення 1-2 в іншиш потоках - використання бар'єра
		try {
			data.getQBarrier().await();
		} catch (InterruptedException | BrokenBarrierException ex) {
			System.out.println("Неможливо продовжити виконання потоку T4 - " + ex);
			return;
		}
		
		// Копіювання даних
		// КД2 - використання атомарної змінної
		q4 = data.getQ().get();
		// КД3 - використання атомарної змінної
		d4 = data.getD().get();
		// КД4 - використання семафора
		try {
			data.getMBSemaphore().acquire();
			MB4 = data.getMB().getCopy();
		} catch (InterruptedException ex) {
			System.out.println("Неможливо продовжити виконання потоку T4 - " + ex);
			return;
		} finally {
			data.getMBSemaphore().release();
		}
		// КД5 - використання механізму Lock критичних секцій
		data.getMCLock().lock();
		MC4 = data.getMC().getCopy();
		data.getMCLock().unlock();

		// Крок обчислення 3
		data.getMR().insertIntoIndexes(minH, maxH,
				MB4.getMatrixSum(MC4).getMatrixProduct(data.getMM().getPartialMatrix(minH, maxH)).getNumberProduct(d4)
						.getMatrixSum(data.getMC().getPartialMatrix(minH, maxH).getNumberProduct(q4)));

		// Сигнал про завершення обчислень - використання семафора
		data.getT4FinishSemaphore().release();
	}
}
