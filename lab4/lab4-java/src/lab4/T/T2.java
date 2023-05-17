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
 * файл: ./src/lab4/T/T2.java
 * Даний файл містить код потоку T2
 */

package lab4.T;

import java.util.concurrent.BrokenBarrierException;

import lab4.Data.Data;
import lab4.Data.Matrix;
import lab4.Data.UserDataManager;

public class T2 extends Thread {

	private final int minH;
	private final int maxH;
	private final Data data;

	private int q2;
	private int d2;
	private Matrix MB2;
	private Matrix MC2;
	private final UserDataManager manager;

	public T2(int H, Data data, UserDataManager manager) {
		this.minH = H;
		this.maxH = H * 2 - 1;
		this.data = data;
		this.manager = manager;
	}

	@Override
	public void run() {
		// Очікування на введення даних в інших потоках
		try {
			data.getInputBarrier().await();
		} catch (InterruptedException | BrokenBarrierException ex) {
			System.out.println("Неможливо продовжити виконання потоку T1 - " + ex);
			return;
		}

		// Крок обчислення 1
		q2 = data.getZ().getPartialVector(minH, maxH).min();
		// КД1 - критична секція за допомогою механізму Lock
		// Крок обчислення 2
		data.getQSetLock().lock();
		data.setMinQ(q2);
		data.getQSetLock().unlock();
		
		// Очікування на завершення кроків обчислення 1-2 в іншиш потоках - використання
		// бар'єра
		try {
			data.getQBarrier().await();
		} catch (InterruptedException | BrokenBarrierException ex) {
			System.out.println("Неможливо продовжити виконання потоку T2 - " + ex);
			return;
		}

		// Копіювання даних - критичні секцій за допомогою механізму synchronized
		// КД2
		synchronized (data.getQCopyObject()) {
			q2 = data.getQ();
		}
		// КД3
		synchronized (data.getDCopyObject()) {
			d2 = data.getD();
		}
		// КД4
		synchronized (data.getMBCopyObject()) {
			MB2 = data.getMB().getCopy();
		}
		// КД5
		synchronized (data.getMCCopyObject()) {
			MC2 = data.getMC().getCopy();
		}

		// Крок обчислення 3
		data.insertIntoMR(minH, maxH,
				MB2.getMatrixProduct(MC2.getMatrixProduct(data.getMM().getPartialMatrix(minH, maxH)))
						.getNumberProduct(d2)
						.getMatrixSum(data.getMC().getPartialMatrix(minH, maxH).getNumberProduct(q2)));

		// Очікування завершення обчислень в інших потоках
		try {
			data.getFinishBarrier().await();
		} catch (InterruptedException | BrokenBarrierException ex) {
			System.out.println("Неможливо продовжити виконання потоку T1 - " + ex);
			return;
		}
		// Виведення результату
		System.out.println("Результат виконання обчислень\n");
		manager.printMatrix("MR", data.getMR());
	}
}
