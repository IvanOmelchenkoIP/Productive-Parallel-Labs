package lab1.T;

import java.util.concurrent.BrokenBarrierException;

import lab1.Data.Data;
import lab1.Data.Matrix;
import lab1.Data.Vector;

public class T4 extends Thread {

	private final int minH;
	private final int maxH;
	private final Data data;

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
			synchronized(data.getInputSyncObj()) {
				Z = data.createVector("Z");
			}
			data.setZ(Z);
			synchronized(data.getInputSyncObj()) {
				d = data.createNumber("d");
			}
			data.setD(d);
			synchronized(data.getInputSyncObj()) {
				MM = data.createMatrix("MM");
			}
			data.setMM(MM);
		} catch (Exception ex) {
			System.out.println("Неможливо продовжити виконання - " + ex);
			return;
		}
		
		data.getT4InputSemaphore().release(data.getRestThreads());
		
		try {
			data.getT1InputSemaphore().acquire();
			data.getT3InputSemaphore().acquire();
		} catch (InterruptedException ex) {
			System.out.println(ex);
		}

		int q4 = data.getZ().getPartialVector(minH, maxH).min();
		data.getQ().set(data.getQ().get() > q4 ? q4 : data.getQ().get());
		
		try {
			data.getQBarrier().await();
		} catch (InterruptedException | BrokenBarrierException ex) {
			System.out.println(ex);
		}
		/*System.out.println("q = " + data.getValQ());
		System.out.println("T4 " + MIN_H + " " + MAX_H + "|" + (MAX_H - MIN_H + 1));
		System.out.println("T4\n" + data.getMatrixMC().getPartialMatrix(MIN_H, MAX_H).toString());*/

		/*Matrix MR = data.getMatrixMR();
		MR.insertIntoIndexes(MIN_H, MAX_H,
				data.getMatrixMB()
						.getMatrixProduct(
								data.getMatrixMC().getMatrixProduct(data.getMatrixMM().getPartialMatrix(MIN_H, MAX_H)))
						.getNumberProduct(data.getValD()).getMatrixSum(
								data.getMatrixMC().getPartialMatrix(MIN_H, MAX_H).getNumberProduct(data.getValQ())));*/
		q4 = data.getQ().get();
		int d4 = data.getD().get();
		Matrix MB4, MC4;
		try {
			data.getMBSemaphore().acquire();
			MB4 = data.getMB();
		} catch (InterruptedException ex) {
			System.out.println(ex);
			return;
		} finally {
			data.getMBSemaphore().release();
		}
		data.getMCLock().lock();
		MC4 = data.getMC();
		data.getMCLock().unlock();

		data.getMR().insertIntoIndexes(minH, maxH,
				MB4.getMatrixSum(MC4).getMatrixProduct(data.getMM().getPartialMatrix(minH, maxH)).getNumberProduct(d4)
						.getMatrixSum(data.getMC().getPartialMatrix(minH, maxH).getNumberProduct(q4)));

	
		data.getT4FinishSemaphore().release();
	}
}
