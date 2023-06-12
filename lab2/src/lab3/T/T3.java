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
 * файл: ./src/lab3/T/T3.java
 * Даний файл містить клас потоку T3
 */

package lab3.T;

import lab3.Data.Data;
import lab3.Data.DataCreators;
import lab3.Data.Matrix;
import lab3.Data.Vector;

public class T3 extends Thread {
	
	final private int minH;
	final private int maxH;
	
	private Data data;
	private DataCreators creators;
	
	private int q3;
	private int p3;
	private int x3;
	private Matrix MZ3;
	private Vector A3;
	private Vector B3;
	
	public T3(int H, Data data, DataCreators creators) {
		this.minH = H * 2;
		this.maxH = H * 3 - 1;
		
		this.data = data;
		this.creators = creators;
	}
	
	@Override
	public void run() {
		// введення даних
		try {
			data.setMB((Matrix)data.syncInput(creators.getMatrixCreator(), "MB"));
		} catch (Exception ex) {
			System.out.println("Виконання потоку T3 було перервано: " + ex);
			return;
		}
		try {
			data.setB((Vector)data.syncInput(creators.getVectorCreator(), "B"));
		} catch (Exception ex) {
			System.out.println("Виконання потоку T3 було перервано: " + ex);
			return;
		}
		try {
			data.setP((Integer)data.syncInput(creators.getNumberCreator(), "p"));
		} catch (Exception ex) {
			System.out.println("Виконання потоку T3 було перервано: " + ex);
			return;
		}
			
		// сигнал про завершення введення
		data.signalInput();
		// очікування на завершення введення
		try {
			data.waitForInput();
		} catch (InterruptedException ex) {
			System.out.println("Виконання потоку T3 було перервано: " + ex);
			return;
		}
		
		// крок 1 обчислень
		q3 = data.getBCopy().getPartialVector(minH, maxH).min();
		// крок 2 обчислень
		// КД1
		data.setMinQ(q3);
		
		// сигнал про завершення обчислення q
		data.signalQ();
		// очікування на завершення обчислення q в інших потоках
		try {
			data.waitForQ();
		} catch (InterruptedException ex) {
			System.out.println("Виконання потоку T3 було перервано: " + ex);
			return;
		}
		
		// копіювання даних для кроку 3 обчислень
		// КД2
		MZ3 = data.getMZCopy();
		// крок 3 обчислень
		data.insertToMQByM(MZ3.getMatrixProduct(data.getMR().getPartialMatrixByM(minH, maxH)), minH, maxH);

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
		p3 = data.getP();
		// КД4
		A3 = data.getACopy();
		// КД5
		B3 = data.getBCopy();

		// крок 4 обчислень
		x3 = A3.getMatrixMultiplyProduct(data.getMB().getPartialMatrixByN(minH, maxH)).getNumberProduct(p3)
				.getVectorsToScalar(B3.getMatrixMultiplyProduct(data.getMQ().getPartialMatrixByN(minH, maxH)));
		// крок 5 обчислень
		// КД6
		data.addToX(x3);
		
		// сигнал про завершення обчислень x
		data.signalX();
	}
}
