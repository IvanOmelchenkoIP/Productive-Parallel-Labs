package lab1.T;

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
		System.out.println("q4 = " + q4);
		data.getQLock().lock();
		int q = data.getValQ();
		data.setValQ(q > q4 ? q4 : q);
		System.out.println("q = " + data.getValQ());
		data.getQLock().unlock();
		/*Matrix MR = commonData.retrieveMR();
		if (MR == null) {
			MR = Matrix.cleanMarix(N);
			commonData.setMR(MR);
		}
		MR.insertIntoIndexes(MIN_H, MAX_H, commonData.retrieveMB()
				.getMatrixProduct(commonData.retrieveMC().getMatrixProduct(commonData.retrieveMM().getPartialMatrix(MIN_H, MAX_H)))
				.getNumberProduct(commonData.retrieveD())
				.getMatrixSum(commonData.retrieveMC().getPartialMatrix(MIN_H, MAX_H).getNumberProduct(commonData.retrieveQ())));

		
		syncData.getQLock().lock();
		syncData.getQLock().unlock();
		
		try {
			syncData.getMRinit().acquire();
		} catch (InterruptedException ex) {
			System.out.println(ex);
		} finally {
			syncData.getMRinit().release();
		}
		syncData.getT4Finish().release();
		
		System.out.println("T4");*/
	}
}
