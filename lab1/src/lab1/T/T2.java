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
 * файл: ./src/lab1/T/T2.java
 * Даний файл містить код потоку T2
 */

package lab1.T;

import java.util.concurrent.BrokenBarrierException;

import lab1.Data.Data;
import lab1.Data.Matrix;

public class T2 extends Thread {

	private final int minH;
	private final int maxH;
	private final Data data;

	private int q2;
	private int d2;
	private Matrix MB2;
	private Matrix MC2;
	
	public T2(int H, Data data) {
		this.minH = H;
		this.maxH = H * 2 - 1;
		this.data = data;
	}

	@Override
	public void run() {
		// Очікування на введення в інших потоках - використання семафорів
		try {
			data.getT1InputSemaphore().acquire();
			data.getT3InputSemaphore().acquire();
			data.getT4InputSemaphore().acquire();
		} catch (InterruptedException ex) {
			System.out.println("Неможливо продовжити виконання потоку T2 - " + ex);
			return;
		}

		// Крок обчислення 1
		q2 = data.getZ().getPartialVector(minH, maxH).min();
		// КД1 - використання атомарної змінної
		// Крок обчислення 2
		data.getQ().set(data.getQ().get() > q2 ? q2 : data.getQ().get());
		
		// Очікування на завершення кроків обчислення 1-2 в іншиш потоках - використання бар'єра
		try {
			data.getQBarrier().await();
		} catch (InterruptedException | BrokenBarrierException ex) {
			System.out.println("Неможливо продовжити виконання потоку T2 - " + ex);
			return;
		}
		
		// Копіювання даних
		// КД2 - використання атомарної змінної
		q2 = data.getQ().get();
		// КД3 - використання атомарної змінної
		d2 = data.getD().get();
		// КД4 - використання семафора
		try {
			data.getMBSemaphore().acquire();
			MB2 = data.getMB().getCopy();
		} catch (InterruptedException ex) {
			System.out.println("Неможливо продовжити виконання потоку T2 - " + ex);
			return;
		} finally {
			data.getMBSemaphore().release();
		}
		// КД5 - використання механізму Lock критичних секцій
		data.getMCLock().lock();
		MC2 = data.getMC().getCopy();
		data.getMCLock().unlock();

		// Крок обчислення 3
		data.getMR().insertIntoIndexes(minH, maxH,
				MB2.getMatrixSum(MC2).getMatrixProduct(data.getMM().getPartialMatrix(minH, maxH)).getNumberProduct(d2)
						.getMatrixSum(data.getMC().getPartialMatrix(minH, maxH).getNumberProduct(q2)));

		// Очікування завершення обчислень в інших потоках - використання семафорів
		try {
			data.getT1FinishSemaphore().acquire();
			data.getT3FinishSemaphore().acquire();
			data.getT4FinishSemaphore().acquire();
		} catch (InterruptedException ex) {
			System.out.println("Неможливо продовжити виконання потоку T3 - " + ex);
			return;
		}
		// Виведення результату
		System.out.println("Результат виконання обчислень. MR:\n" + data.getMR().toString());
	}
}
