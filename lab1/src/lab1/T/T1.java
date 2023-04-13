package lab1.T;

import java.util.concurrent.BrokenBarrierException;

import lab1.Data.Data;
import lab1.Data.Matrix;

public class T1 extends Thread {

	private final int minH;
	private final int maxH;
	private final Data data;

	public T1(int H, Data data) {
		this.minH = 0;
		this.maxH = H - 1;
		this.data = data;
	}

	@Override
	public void run() {
		Matrix MB;
		try {
			synchronized (data.getInputSyncObj()) {
				MB = data.createMatrix("MB");
			}
			data.setMB(MB);
		} catch (Exception ex) {
			System.out.println("Неможливо продовжити виконання - " + ex);
			return;
		}

		data.getT1InputSemaphore().release(data.getRestThreads());

		try {
			data.getT3InputSemaphore().acquire();
			data.getT4InputSemaphore().acquire();
		} catch (InterruptedException ex) {
			System.out.println(ex);
			return;
		}

		int q1 = data.getZ().getPartialVector(minH, maxH).min();
		data.getQ().set(data.getQ().get() > q1 ? q1 : data.getQ().get());

		try {
			data.getQBarrier().await();
		} catch (InterruptedException | BrokenBarrierException ex) {
			System.out.println(ex);
		}

		/*
		 * System.out.println("MC\n" + data.getMatrixMC().toString());
		 * 
		 * System.out.println("q = " + data.getValQ()); System.out.println("T1 " + MIN_H
		 * + " " + MAX_H + "|" + (MAX_H - MIN_H + 1)); System.out.println("T1\n" +
		 * data.getMatrixMC().getPartialMatrix(MIN_H, MAX_H).toString());
		 */

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

		q1 = data.getQ().get();
		int d1 = data.getD().get();
		Matrix MB1, MC1;
		try {
			data.getMBSemaphore().acquire();
			MB1 = data.getMB();
		} catch (InterruptedException ex) {
			System.out.println(ex);
			return;
		} finally {
			data.getMBSemaphore().release();
		}
		data.getMCLock().lock();
		MC1 = data.getMC();
		data.getMCLock().unlock();

		data.getMR().insertIntoIndexes(minH, maxH,
				MB1.getMatrixSum(MC1).getMatrixProduct(data.getMM().getPartialMatrix(minH, maxH)).getNumberProduct(d1)
						.getMatrixSum(data.getMC().getPartialMatrix(minH, maxH).getNumberProduct(q1)));

		/*
		 * Matrix MR = data.getMatrixMR(); MR.insertIntoIndexes(MIN_H, MAX_H,
		 * data.getMatrixMB() .getMatrixProduct(
		 * data.getMatrixMC().getMatrixProduct(data.getMatrixMM().getPartialMatrix(
		 * MIN_H, MAX_H))) .getNumberProduct(data.getValD()).getMatrixSum(
		 * data.getMatrixMC().getPartialMatrix(MIN_H,
		 * MAX_H).getNumberProduct(data.getValQ())));
		 */

		data.getT1FinishSemaphore().release();
	}
}
