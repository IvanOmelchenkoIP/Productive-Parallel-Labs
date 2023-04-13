package lab1.T;

import java.util.concurrent.BrokenBarrierException;

import lab1.Data.Data;
import lab1.Data.matrix.Matrix;

public class T2 extends Thread {

	private final int N;
	private final int H;
	private final Data data;

	public T2(int N, int H, Data data) {
		this.N = N;
		this.H = H;
		this.data = data;
	}

	@Override
	public void run() {
		try {
			data.getT1InputSemaphore().acquire();
			data.getT3InputSemaphore().acquire();
			data.getT4InputSemaphore().acquire();
		} catch (InterruptedException ex) {
			System.out.println(ex);
			return;
		}

		int MIN_H = H;
		int MAX_H = H * 2 - 1;

		int q2 = data.getVectorZ().getPartialVector(MIN_H, MAX_H).min();
		data.getQLock().lock();
		int q = data.getValQ();
		data.setValQ(q > q2 ? q2 : q);
		data.getQLock().unlock();

		/*data.getQT2Semaphore().release(data.getRestThreadsAmount());

		try {
			data.getQT1Semaphore().acquire();
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
		System.out.println("q = " + data.getValQ());

		System.out.println("T2 " + MIN_H + " " + MAX_H + "|" + (MAX_H - MIN_H + 1));
		System.out.println("T2\n" + data.getMatrixMC().getPartialMatrix(MIN_H, MAX_H).toString());
		Matrix MR = data.getMatrixMR();
		MR.insertIntoIndexes(MIN_H, MAX_H,
				data.getMatrixMB()
						.getMatrixProduct(
								data.getMatrixMC().getMatrixProduct(data.getMatrixMM().getPartialMatrix(MIN_H, MAX_H)))
						.getNumberProduct(data.getValD()).getMatrixSum(
								data.getMatrixMC().getPartialMatrix(MIN_H, MAX_H).getNumberProduct(data.getValQ())));

		try {
			data.getT1FinishSemaphore().acquire();
			data.getT3FinishSemaphore().acquire();
			data.getT4FinishSemaphore().acquire();
		} catch (InterruptedException ex) {
			System.out.println(ex);
		}
		System.out.println("Результат виконання обчислень. MR = \n" + MR.toString());
	}
}
