package lab1.T;

import java.util.concurrent.BrokenBarrierException;

import lab1.Data.Data;
import lab1.Data.matrix.Matrix;
import lab1.Data.vector.Vector;

public class T4 extends Thread {

	private final int N;
	private final int H;
	private final Data data;

	public T4(int N, int H, Data data) {
		this.N = N;
		this.H = H;
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
			data.setVectorZ(Z);
			synchronized(data.getInputSyncObj()) {
				d = data.createNumber("d");
			}
			data.setValD(d);
			synchronized(data.getInputSyncObj()) {
				MM = data.createMatrix("MM");
			}
			data.setMatrixMM(MM);
		} catch (Exception ex) {
			System.out.println("Неможливо продовжити виконання - " + ex);
			return;
		}
		
		data.getT4InputSemaphore().release(data.getRestThreadsAmount());
		
		try {
			data.getT1InputSemaphore().acquire();
			data.getT3InputSemaphore().acquire();
		} catch (InterruptedException ex) {
			System.out.println(ex);
		}

		int MIN_H = H * 3;
		int MAX_H = N - 1;

		int q4 = data.getVectorZ().getPartialVector(MIN_H, MAX_H).min();
		data.getQLock().lock();
		int q = data.getValQ();
		data.setValQ(q > q4 ? q4 : q);
		data.getQLock().unlock();
		
		//data.getQT4Semaphore().release(data.getRestThreadsAmount());
		
		/*try {
			data.getQT1Semaphore().acquire();
			data.getQT2Semaphore().acquire();
			data.getQT3Semaphore().acquire();
		} catch (InterruptedException ex) {
			System.out.println(ex);
			return;
		}*/
		
		try {
			data.getQBarrier().await();
		} catch (InterruptedException | BrokenBarrierException ex) {
			System.out.println(ex);
		}
		System.out.println("q = " + data.getValQ());
		System.out.println("T4 " + MIN_H + " " + MAX_H + "|" + (MAX_H - MIN_H + 1));
		System.out.println("T4\n" + data.getMatrixMC().getPartialMatrix(MIN_H, MAX_H).toString());

		Matrix MR = data.getMatrixMR();
		MR.insertIntoIndexes(MIN_H, MAX_H,
				data.getMatrixMB()
						.getMatrixProduct(
								data.getMatrixMC().getMatrixProduct(data.getMatrixMM().getPartialMatrix(MIN_H, MAX_H)))
						.getNumberProduct(data.getValD()).getMatrixSum(
								data.getMatrixMC().getPartialMatrix(MIN_H, MAX_H).getNumberProduct(data.getValQ())));
	
		data.getT4FinishSemaphore().release();
	}
}
