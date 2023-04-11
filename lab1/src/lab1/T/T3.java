package lab1.T;

import lab1.Data.Data;
import lab1.Data.matrix.Matrix;

public class T3 extends Thread {

	private final int N;
	private final int H;
	private final Data data;

	public T3(int N, int H, Data data) {
		this.N = N;
		this.H = H;
		this.data = data;
	}

	@Override
	public void run() {
		Matrix MC;
		try {
			synchronized(data.getInputSyncObj()) {
				MC = data.createMatrix("MC");
			}
			data.setMatrixMC(MC);
		} catch (Exception ex) {
			System.out.println("Неможливо продовжити виконання - " + ex);
			return;
		}
		
		data.getT3InputSemaphore().release(data.getRestThreadsAmount());
		
		try {
			data.getT1InputSemaphore().acquire();
			data.getT4InputSemaphore().acquire();
		} catch (InterruptedException ex) {
			System.out.println(ex);
		}
		
		int MIN_H = H * 2;
		int MAX_H = H * 3 - 1;

		int q3 = data.getVectorZ().getPartialVector(MIN_H, MAX_H).min();
		data.getQLock().lock();
		int q = data.getValQ();
		data.setValQ(q > q3 ? q3 : q);
		data.getQLock().unlock();

		data.getQT3Semaphore().release(data.getRestThreadsAmount());
		
		try {
			data.getQT1Semaphore().acquire();
			data.getQT2Semaphore().acquire();
			data.getQT4Semaphore().acquire();
		} catch (InterruptedException ex) {
			System.out.println(ex);
			return;
		}
		System.out.println("q = " + data.getValQ());
		System.out.println("T3 " + MIN_H + " " + MAX_H + "|" + (MAX_H - MIN_H + 1));
		System.out.println("T3\n" + data.getMatrixMC().getPartialMatrix(MIN_H, MAX_H).toString());

		Matrix MR = data.getMatrixMR();
		MR.insertIntoIndexes(MIN_H, MAX_H,
				data.getMatrixMB()
						.getMatrixProduct(
								data.getMatrixMC().getMatrixProduct(data.getMatrixMM().getPartialMatrix(MIN_H, MAX_H)))
						.getNumberProduct(data.getValD()).getMatrixSum(
								data.getMatrixMC().getPartialMatrix(MIN_H, MAX_H).getNumberProduct(data.getValQ())));
	
		data.getT3FinishSemaphore().release();
	}
}
