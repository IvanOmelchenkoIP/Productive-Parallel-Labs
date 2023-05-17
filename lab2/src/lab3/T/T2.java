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
 * файл: ./src/lab3/T/T2.java
 * Даний файл містить клас потоку T2
 */

package lab3.T;

import lab3.Data.Data;
import lab3.Data.DataCreators;
import lab3.Data.Matrix;
import lab3.Data.Vector;

public class T2 extends Thread {
	
	final private int minH;
	final private int maxH;
	
	private Data data;
	private DataCreators creators;
	
	private int q2;
	private int p2;
	private int x2;
	private Matrix MZ2;
	private Vector A2;
	private Vector B2;
	
	public T2(int H, Data data, DataCreators creators) {
		this.minH = H;
		this.maxH = H * 2 - 1;
		
		this.data = data;
		this.creators = creators;
	}
	
	@Override
	public void run() {
		// введення даних
		try {
			data.setA((Vector)data.syncInput(creators.getVectorCreator(), "A"));
		} catch (Exception ex) {
			System.out.println("Виконання потоку T2 було перервано: " + ex);
			return;
		}
		try {
			data.setMR((Matrix)data.syncInput(creators.getMatrixCreator(), "MR"));
		} catch (Exception ex) {
			System.out.println("Виконання потоку T2 було перервано: " + ex);
			return;
		}
		
		// сигнал про завершення введення
		data.signalInput();
		// очікування на завершення введення
		try {
			data.waitForInput();
		} catch (InterruptedException ex) {
			System.out.println("Виконання потоку T2 було перервано: " + ex);
			return;
		}
		
		// крок 1 обчислення
		q2 = data.getBCopy().getPartialVector(minH, maxH).min();
		// крок 2 обчислення
		// КД1
		data.setMinQ(q2);
		
		// сигнал про завершення обчислення q
		data.signalQ();
		// очікування на завершення обчислення q в інших потоках
		try {
			data.waitForQ();
		} catch (InterruptedException ex) {
			System.out.println("Виконання потоку T2 було перервано: " + ex);
			return;
		}
		
		// копіювання даних для кроку 3 обчислень
		// КД2
		MZ2 = data.getMZCopy();
		// крок 3 обчислень
		data.insertToMQByM(MZ2.getMatrixProduct(data.getMR().getPartialMatrixByM(minH, maxH)), minH, maxH);

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
		p2 = data.getP();
		// КД4
		A2 = data.getACopy();
		// КД5
		B2 = data.getBCopy();

		// крок 4 обчислень
		x2 = A2.getMatrixMultiplyProduct(data.getMB().getPartialMatrixByN(minH, maxH)).getNumberProduct(p2)
				.getVectorsToScalar(B2.getMatrixMultiplyProduct(data.getMQ().getPartialMatrixByN(minH, maxH)));
		// крок 5 обчислень
		// КД6
		data.addToX(x2);
		
		// очікування на завершення обчислень x
		try {
			data.waitForX();
		} catch (InterruptedException ex) {
			System.out.println("Виконання потоку T2 було перервано: " + ex);
			return;
		}
		
		// копіювання даних для кроку 6 обчислень
		// КД7
		x2 = data.getX();
		// КД8
		q2 = data.getQ();
		
		// крок 6 обчислень
		// КД9
		data.addToE(x2 + q2);
		
		// виведення результату
		System.out.println("Результат обчислень: e = " + data.getE());
	}
}
