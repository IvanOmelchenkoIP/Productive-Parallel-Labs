/*
 * Лабораторна робота 3 ЛР3, Варіант - 11
 * e = ( (p*(A*MB) )*( B*(MZ*MR) ) + min(B)
 * Введенні і виведення даних:
 * T1: MZ
 * T2: e, A, MR
 * T3: MB, B, p
 * T4: -
 * Омельченко І. ІП-04
 * Дата відправлення: 03.05.2023 
 * 
 * файл: ./src/lab3/T/T1.java
 * Даний файл містить клас потоку T1
 */

package lab3.T;

import lab3.Data.Data;
import lab3.Data.DataCreators;
import lab3.Data.Matrix;
import lab3.Data.Vector;

public class T1 extends Thread {

	final private int minH;
	final private int maxH;

	private Data data;
	private DataCreators creators;
	
	private int q1;
	private int p1;
	private int x1;
	private Matrix MZ1;
	private Vector A1;
	private Vector B1;

	public T1(int H, Data data, DataCreators creators) {
		this.minH = 0;
		this.maxH = H - 1;

		this.data = data;
		this.creators = creators;
	}

	@Override
	public void run() {
		// введення даних
		try {
			data.setMZ((Matrix)data.syncInput(creators.getMatrixCreator(), "MZ"));
		} catch (Exception ex) {
			System.out.println("Виконання потоку T1 було перервано: " + ex);
			return;
		}

		// очікування на завершення введення
		data.getInputBarrier()

		// крок 1 обчислення
		q1 = data.getBCopy().getPartialVector(minH, maxH).min();
		// крок 2 обчислення
		// КД1
		data.setMinQ(q1);

		// сигнал про завершення обчислення q
		data.signalQ();
		// очікування на завершення обчислення q в інших потоках
		try {
			data.waitForQ();
		} catch (InterruptedException ex) {
			System.out.println("Виконання потоку T1 було перервано: " + ex);
			return;
		}

		// копіювання даних для кроку 3 обчислень
		// КД3
		MZ1 = data.getMZCopy();
		// крок 3 обчислень
		data.insertToMQByM(MZ1.getMatrixProduct(data.getMR().getPartialMatrixByM(minH, maxH)), minH, maxH);

		// сигнал про завершення обчисдення MQ
		data.signalMQ();
		// очікування а завершення обчислення MQ і інших потоках
		try {
			data.waitForMQ();
		} catch (InterruptedException ex) {
			System.out.println("Виконання потоку T1 було перервано: " + ex);
			return;
		}

		// копіювання даних для кроку 4 обчислень 
		// КД3
		p1 = data.getP();
		// КД4
		A1 = data.getACopy();
		// КД5
		B1 = data.getBCopy();

		// крок 4 обчислень
		x1 = A1.getMatrixMultiplyProduct(data.getMB().getPartialMatrixByN(minH, maxH)).getNumberProduct(p1)
				.getVectorsToScalar(B1.getMatrixMultiplyProduct(data.getMQ().getPartialMatrixByN(minH, maxH)));
		// крок 5 обчислень
		// КД6
		data.addToX(x1);

		// сигнал про завершення обчислень x
		data.signalX();
	}
}
