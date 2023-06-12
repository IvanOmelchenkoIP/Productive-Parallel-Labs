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
 * файл: ./src/lab4/T/T1.java
 * Даний файл містить код потоку T1
 */

package lab4.T;

import java.util.concurrent.BrokenBarrierException;

import lab4.Data.Data;
import lab4.Data.UserDataManager;
import lab4.Data.Matrix;

public class T1 extends Thread {

	private final int minH;
	private final int maxH;
	private final Data data;
	private final UserDataManager manager;

	private int q1;
	private int d1;
	private Matrix MB1;
	private Matrix MC1;

	public T1(int H, Data data, UserDataManager manager) {
		this.minH = 0;
		this.maxH = H - 1;
		this.data = data;
		this.manager = manager;
	}

	@Override
	public void run() {
		// Введення матриці MB
		try {
			// Синхронізація введення на випадок введення даних з клавіатури (N <= 12) -
			// критичні секції за допомогою механізму Lock
			data.getInputLock().lock();
			data.setMB(manager.createMatrix("MB"));
			data.getInputLock().unlock();
		} catch (Exception ex) {
			System.out.println("Неможливо продовжити виконання потоку T1 - " + ex);
			return;
		}

		// Очікування на введення даних в інших потоках
		try {
			data.getInputBarrier().await();
		} catch (InterruptedException | BrokenBarrierException ex) {
			System.out.println("Неможливо продовжити виконання потоку T1 - " + ex);
			return;
		}

		// Крок обчислення 1
		q1 = data.getZ().getPartialVector(minH, maxH).min();
		// КД1 - критична секція за допомогою механізму Lock
		// Крок обчислення 2
		data.getQSetLock().lock();
		data.setMinQ(q1);
		data.getQSetLock().unlock();

		// Очікування на завершення кроків обчислення 1-2 в іншиш потоках - використання
		// бар'єра
		try {
			data.getQBarrier().await();
		} catch (InterruptedException | BrokenBarrierException ex) {
			System.out.println("Неможливо продовжити виконання потоку T1 - " + ex);
			return;
		}

		// Копіювання даних - критичні секцій за допомогою механізму synchronized
		// КД2
		synchronized (data.getQCopyObject()) {
			q1 = data.getQ();
		}
		// КД3
		synchronized (data.getDCopyObject()) {
			d1 = data.getD();
		}
		// КД4
		synchronized (data.getMBCopyObject()) {
			MB1 = data.getMB().getCopy();
		}
		// КД5
		synchronized (data.getMCCopyObject()) {
			MC1 = data.getMC().getCopy();
		}

		// Крок обчислення 3
		data.insertIntoMR(minH, maxH,
				MB1.getMatrixProduct(MC1.getMatrixProduct(data.getMM().getPartialMatrix(minH, maxH)))
						.getNumberProduct(d1)
						.getMatrixSum(data.getMC().getPartialMatrix(minH, maxH).getNumberProduct(q1)));

		// Очікування завершення обчислень в інших потоках
		try {
			data.getFinishBarrier().await();
		} catch (InterruptedException | BrokenBarrierException ex) {
			System.out.println("Неможливо продовжити виконання потоку T1 - " + ex);
			return;
		}
	}
}
