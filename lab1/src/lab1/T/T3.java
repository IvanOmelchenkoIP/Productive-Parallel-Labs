package lab1.T;

import java.util.concurrent.BrokenBarrierException;

import lab1.Data.Data;
import lab1.Data.Matrix;

public class T3 extends Thread {

	private final int minH;
	private final int maxH;
	private final Data data;

	public T3(int H, Data data) {
		this.minH = H * 2;
		this.maxH = H * 3 - 1;
		this.data = data;
	}

	@Override
	public void run() {
		Matrix MC;
		try {
			synchronized(data.getInputSyncObj()) {
				MC = data.createMatrix("MC");
			}
			data.setMC(MC);
		} catch (Exception ex) {
			System.out.println("Неможливо продовжити виконання - " + ex);
			return;
		}
		
		data.getT3InputSemaphore().release(data.getRestThreads());
		
		try {
			data.getT1InputSemaphore().acquire();
			data.getT4InputSemaphore().acquire();
		} catch (InterruptedException ex) {
			System.out.println(ex);
		}

		int q3 = data.getZ().getPartialVector(minH, maxH).min();
		data.getQ().set(data.getQ().get() > q3 ? q3 : data.getQ().get());
		
		try {
			data.getQBarrier().await();
		} catch (InterruptedException | BrokenBarrierException ex) {
			System.out.println(ex);
		}
		/*System.out.println("q = " + data.getValQ());
		System.out.println("T3 " + MIN_H + " " + MAX_H + "|" + (MAX_H - MIN_H + 1));
		System.out.println("T3\n" + data.getMatrixMC().getPartialMatrix(MIN_H, MAX_H).toString());*/

		/*Matrix MR = data.getMatrixMR();
		MR.insertIntoIndexes(MIN_H, MAX_H,
				data.getMatrixMB()
						.getMatrixProduct(
								data.getMatrixMC().getMatrixProduct(data.getMatrixMM().getPartialMatrix(MIN_H, MAX_H)))
						.getNumberProduct(data.getValD()).getMatrixSum(
								data.getMatrixMC().getPartialMatrix(MIN_H, MAX_H).getNumberProduct(data.getValQ())));*/
		q3 = data.getQ().get();
		int d3 = data.getD().get();
		Matrix MB3, MC3;
		try {
			data.getMBSemaphore().acquire();
			MB3 = data.getMB();
		} catch (InterruptedException ex) {
			System.out.println(ex);
			return;
		} finally {
			data.getMBSemaphore().release();
		}
		data.getMCLock().lock();
		MC3 = data.getMC();
		data.getMCLock().unlock();

		data.getMR().insertIntoIndexes(minH, maxH,
				MB3.getMatrixSum(MC3).getMatrixProduct(data.getMM().getPartialMatrix(minH, maxH)).getNumberProduct(d3)
						.getMatrixSum(data.getMC().getPartialMatrix(minH, maxH).getNumberProduct(q3)));

	
		data.getT3FinishSemaphore().release();
	}
}
