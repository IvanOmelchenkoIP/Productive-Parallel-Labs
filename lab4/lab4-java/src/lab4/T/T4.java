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
 * файл: ./src/lab4/T/T4.java
 * Даний файл містить код потоку T4
 */

package lab4.T;

import java.util.concurrent.BrokenBarrierException;

import lab4.Data.Data;
import lab4.Data.UserDataManager;
import lab4.Data.Matrix;

public class T4 extends Thread {

	private final int minH;
	private final int maxH;
	private final Data data;
	private final UserDataManager manager;

	private int q4;
	private int d4;
	private Matrix MB4;
	private Matrix MC4;

	public T4(int H, Data data, UserDataManager manager) {
		this.minH = H * 3;
		this.maxH = H * 4 - 1;
		this.data = data;
		this.manager = manager;
	}

	@Override
	public void run() {
		// Введення скаляра d, вектора Z і матриці MM
		try {
			// Синхронізація введення на випадок введення даних з клавіатури (N <= 12) -
			// критичні секції за допомогою механізму Lock
			data.getInputLock().lock();
			data.setZ(manager.createVector("Z"));
			data.getInputLock().unlock();

			data.getInputLock().lock();
			data.setD(manager.createNumber("d"));
			data.getInputLock().unlock();

			data.getInputLock().lock();
			data.setMM(manager.createMatrix("MM"));
			data.getInputLock().unlock();
		} catch (Exception ex) {
			System.out.println("Неможливо продовжити виконання потоку T4 - " + ex);
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
		q4 = data.getZ().getPartialVector(minH, maxH).min();
		// КД1 - критична секція за допомогою механізму Lock
		// Крок обчислення 2
		data.getQSetLock().lock();
		data.setMinQ(q4);
		data.getQSetLock().unlock();

		// Очікування на завершення кроків обчислення 1-2 в іншиш потоках - використання
		// бар'єра
		try {
			data.getQBarrier().await();
		} catch (InterruptedException | BrokenBarrierException ex) {
			System.out.println("Неможливо продовжити виконання потоку T4 - " + ex);
			return;
		}

		// Копіювання даних - критичні секцій за допомогою механізму synchronized
		// КД2
		synchronized (data.getQCopyObject()) {
			q4 = data.getQ();
		}
		// КД3
		synchronized (data.getDCopyObject()) {
			d4 = data.getD();
		}
		// КД4
		synchronized (data.getMBCopyObject()) {
			MB4 = data.getMB().getCopy();
		}
		// КД5
		synchronized (data.getMCCopyObject()) {
			MC4 = data.getMC().getCopy();
		}

		// Крок обчислення 3
		data.insertIntoMR(minH, maxH,
				MB4.getMatrixProduct(MC4.getMatrixProduct(data.getMM().getPartialMatrix(minH, maxH)))
						.getNumberProduct(d4)
						.getMatrixSum(data.getMC().getPartialMatrix(minH, maxH).getNumberProduct(q4)));

		// Очікування завершення обчислень в інших потоках
		try {
			data.getFinishBarrier().await();
		} catch (InterruptedException | BrokenBarrierException ex) {
			System.out.println("Неможливо продовжити виконання потоку T1 - " + ex);
			return;
		}
	}
}
