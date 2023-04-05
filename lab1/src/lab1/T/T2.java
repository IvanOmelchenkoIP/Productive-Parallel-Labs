package lab1.T;

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
		System.out.println("q2 = " + q2);
		data.getQLock().lock();
		int q = data.getValQ();
		data.setValQ(q > q2 ? q2 : q);
		System.out.println("q = " + data.getValQ());
		data.getQLock().unlock();

		/*Matrix MR;
		try {
			syncData.getMRinit().acquire();
			MR = commonData.retrieveMR();
			if (MR == null) {
				MR = Matrix.cleanMarix(N);
				commonData.setMR(MR);
			}
		} catch (InterruptedException ex) {
			System.out.println(ex);
			return;
		} finally {
			syncData.getMRinit().release();
		}
		MR.insertIntoIndexes(MIN_H, MAX_H, commonData.retrieveMB()
				.getMatrixProduct(commonData.retrieveMC().getMatrixProduct(commonData.retrieveMM().getPartialMatrix(MIN_H, MAX_H)))
				.getNumberProduct(commonData.retrieveD())
				.getMatrixSum(commonData.retrieveMC().getPartialMatrix(MIN_H, MAX_H).getNumberProduct(commonData.retrieveQ())));

		try {
			syncData.getT1Finish().acquire();
			syncData.getT3Finish().acquire();
			syncData.getT4Finish().acquire();
		} catch (InterruptedException ex) {
			System.out.println(ex);
			return;
		}
		
		System.out.println("Результат виконання обчислень. MR = " + MR.toString());		*/
	}
}
