package lab1.T;

import java.util.concurrent.BrokenBarrierException;

import lab1.Data.Data;
import lab1.Data.Matrix;

public class T2 extends Thread {

	private final int minH;
	private final int maxH;
	private final Data data;

	public T2(int H, Data data) {
		this.minH = H;
		this.maxH = H * 2 - 1;
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

		int q2 = data.getZ().getPartialVector(minH, maxH).min();
		data.getQ().set(data.getQ().get() > q2 ? q2 : data.getQ().get());
		
		try {
			data.getQBarrier().await();
		} catch (InterruptedException | BrokenBarrierException ex) {
			System.out.println(ex);
		}
		//System.out.println("q = " + data.getValQ());

		/*System.out.println("T2 " + MIN_H + " " + MAX_H + "|" + (MAX_H - MIN_H + 1));
		System.out.println("T2\n" + data.getMatrixMC().getPartialMatrix(MIN_H, MAX_H).toString());
		Matrix MR = data.getMatrixMR();
		MR.insertIntoIndexes(MIN_H, MAX_H,
				data.getMatrixMB()
						.getMatrixProduct(
								data.getMatrixMC().getMatrixProduct(data.getMatrixMM().getPartialMatrix(MIN_H, MAX_H)))
						.getNumberProduct(data.getValD()).getMatrixSum(
								data.getMatrixMC().getPartialMatrix(MIN_H, MAX_H).getNumberProduct(data.getValQ())));*/
		q2 = data.getQ().get();
		int d2 = data.getD().get();
		Matrix MB2, MC2;
		try {
			data.getMBSemaphore().acquire();
			MB2 = data.getMB();
		} catch (InterruptedException ex) {
			System.out.println(ex);
			return;
		} finally {
			data.getMBSemaphore().release();
		}
		data.getMCLock().lock();
		MC2 = data.getMC();
		data.getMCLock().unlock();

		data.getMR().insertIntoIndexes(minH, maxH,
				MB2.getMatrixSum(MC2).getMatrixProduct(data.getMM().getPartialMatrix(minH, maxH)).getNumberProduct(d2)
						.getMatrixSum(data.getMC().getPartialMatrix(minH, maxH).getNumberProduct(q2)));

		try {
			data.getT1FinishSemaphore().acquire();
			data.getT3FinishSemaphore().acquire();
			data.getT4FinishSemaphore().acquire();
		} catch (InterruptedException ex) {
			System.out.println(ex);
		}
		System.out.println("Результат виконання обчислень. MR = \n" + data.getMR().toString());
	}
}
