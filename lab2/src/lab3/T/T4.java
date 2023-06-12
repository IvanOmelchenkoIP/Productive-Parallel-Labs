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
 * файл: ./src/lab3/T/T4.java
 * Даний файл містить клас потоку T4
 */

package lab3.T;

import lab3.Data.Data;
import lab3.Data.Matrix;
import lab3.Data.Vector;

public class T4 extends Thread {
	
	final private int minH;
	final private int maxH;
	
	private Data data;
	
	private int q4;
	private int p4;
	private int x4;
	private Matrix MZ4;
	private Vector A4;
	private Vector B4;
	
	public T4(int H, Data data) {
		this.minH = H * 3;
		this.maxH = H * 4 - 1;
		
		this.data = data;
	}
	
	@Override
	public void run() {
		// очікування на завершення введення
		try {
			data.waitForInput();
		} catch (InterruptedException ex) {
			System.out.println("Виконання потоку T4 було перервано: " + ex);
			return;
		}
		
		// крок 1 обчислення
		q4 = data.getBCopy().getPartialVector(minH, maxH).min();
		// крок 2 обчислення
		// КД1
		data.setMinQ(q4);
		
		// сигнал про завершення обчислення q
		data.signalQ();
		// очікування на завершення обчислення q в інших потоках
		try {
			data.waitForQ();
		} catch (InterruptedException ex) {
			System.out.println("Виконання потоку T4 було перервано: " + ex);
			return;
		}
		
		// копіювання даних для кроку 3 обчислень
		// КД2
		MZ4 = data.getMZCopy();
		// крок 3 обчислень
		data.insertToMQByM(MZ4.getMatrixProduct(data.getMR().getPartialMatrixByM(minH, maxH)), minH, maxH);

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
		p4 = data.getP();
		// КД4
		A4 = data.getACopy();
		//КД5
		B4 = data.getBCopy();

		// крок 4 обчислень
		x4 = A4.getMatrixMultiplyProduct(data.getMB().getPartialMatrixByN(minH, maxH)).getNumberProduct(p4)
				.getVectorsToScalar(B4.getMatrixMultiplyProduct(data.getMQ().getPartialMatrixByN(minH, maxH)));
		// крок 5 обчислень
		// КД6
		data.addToX(x4);

		// сигнал про завершення обчислень x
		data.signalX();
	}
}
