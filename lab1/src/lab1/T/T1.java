package lab1.T;

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
			synchronized(data.getInputSyncObj()) {
				MB = data.createMatrix("MB");
			}
			data.setMatrixMM(MB);
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
		System.out.println("q1 = " + q1);
		data.getQLock().lock();
		int q = data.getValQ();
		data.setValQ(q > q1 ? q1 : q);
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
	
		syncData.getT1Finish().release();*/
	}
}
