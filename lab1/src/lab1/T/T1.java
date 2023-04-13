package lab1.T;

import java.util.concurrent.BrokenBarrierException;

import lab1.Data.Data;
import lab1.Data.matrix.Matrix;

public class T1 extends Thread {

	private final int N;
	private final int H;
	private final Data data;

	public T1(int N, int H, Data data) {
		this.N = N;
		this.H = H;
		this.data = data;
	}

	@Override
	public void run() {
		Matrix MB;
		try {
			synchronized (data.getInputSyncObj()) {
				MB = data.createMatrix("MB");
			}
			data.setMatrixMB(MB);
		} catch (Exception ex) {
			System.out.println("Неможливо продовжити виконання - " + ex);
			return;
		}

		data.getT1InputSemaphore().release(data.getRestThreadsAmount());

		try {
			data.getT3InputSemaphore().acquire();
			data.getT4InputSemaphore().acquire();
		} catch (InterruptedException ex) {
			System.out.println(ex);
			return;
		}

		int MIN_H = 0;
		int MAX_H = H - 1;

		int q1 = data.getVectorZ().getPartialVector(MIN_H, MAX_H).min();
		data.getQLock().lock();
		int q = data.getValQ();
		data.setValQ(q > q1 ? q1 : q);
		data.getQLock().unlock();

		/*data.getQT1Semaphore().release(data.getRestThreadsAmount());

		try {
			data.getQT2Semaphore().acquire();
			data.getQT3Semaphore().acquire();
			data.getQT4Semaphore().acquire();
		} catch (InterruptedException ex) {
			System.out.println(ex);
			return;
		}*/
		try {
			data.getQBarrier().await();
		} catch (InterruptedException | BrokenBarrierException ex) {
			System.out.println(ex);
		}

		System.out.println("MC\n" + data.getMatrixMC().toString());
		
		System.out.println("q = " + data.getValQ());
		System.out.println("T1 " + MIN_H + " " + MAX_H + "|" + (MAX_H - MIN_H + 1));
		System.out.println("T1\n" + data.getMatrixMC().getPartialMatrix(MIN_H, MAX_H).toString());

		/*
		 * System.out.println(data.getMatrixMC().toString());
		 * System.out.println(data.getMatrixMC().getPartialMatrix(MIN_H,
		 * MAX_H).toString()); System.out
		 * .println(data.getMatrixMC().getPartialMatrix(MIN_H,
		 * MAX_H).getNumberProduct(data.getValQ()).toString());
		 */

		/*
		 * System.out.println(data.getMatrixMM().getPartialMatrix(MIN_H, MAX_H));
		 * System.out.println("Multiply2\n" +
		 * data.getMatrixMC().getMatrixProduct(data.getMatrixMM().getPartialMatrix(
		 * MIN_H, MAX_H)).toString());
		 * System.out.println(data.getMatrixMB().getMatrixProduct(
		 * data.getMatrixMC().getMatrixProduct(data.getMatrixMM().getPartialMatrix(
		 * MIN_H, MAX_H))).toString());
		 */

		Matrix MR = data.getMatrixMR();
		MR.insertIntoIndexes(MIN_H, MAX_H,
				data.getMatrixMB()
						.getMatrixProduct(
								data.getMatrixMC().getMatrixProduct(data.getMatrixMM().getPartialMatrix(MIN_H, MAX_H)))
						.getNumberProduct(data.getValD()).getMatrixSum(
								data.getMatrixMC().getPartialMatrix(MIN_H, MAX_H).getNumberProduct(data.getValQ())));

		data.getT1FinishSemaphore().release();
	}
}
