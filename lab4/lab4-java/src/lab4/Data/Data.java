/*
 * Лабораторна робота 4 ЛР4, Варіант - 10
 * MR = MB*(MC*MM)*d + min(Z)*MC
 * Введенні і виведення даних:
 * T1: MB
 * T2: MR
 * T3: MC
 * T4: d, Z, MM
 * Омельченко І. ІП-04
 * Дата відправлення: 11.05.2023 
 * 
 * файл: ./src/lab4/Data/Data.java
 * Даний файл містить дані і об'єкти для синхронізації і вирішення проблем взаємного виключення
 */

package lab4.Data;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// Клас, що містить дані, необхідні для виконання програми
public class Data {

	// атомарні змінні
	private int q;
	private int d;
	
	// вектори і матриці
	private Vector Z;
	private Matrix MB;
	private Matrix MC;
	private Matrix MM;
	private Matrix MR;

	// об'єкти для використання критичних секцй і бар'єри 
	private CyclicBarrier inputBarrier;
	private CyclicBarrier qBarrier;
	private CyclicBarrier finishBarrier;

	private Lock inputLock;
	private Lock qSetLock;
	
	private Object qCopyObject;
	private Object dCopyObject;
	private Object MCCopyObject;
	private Object MBCopyObject;

	public Data(int threads, int N) {	
		q = (int) Double.POSITIVE_INFINITY;
		MR = new Matrix(new int[N][N]);
		
		inputBarrier = new CyclicBarrier(threads);
		qBarrier = new CyclicBarrier(threads);
		finishBarrier = new CyclicBarrier(threads);
		
		inputLock = new ReentrantLock();
		qSetLock = new ReentrantLock();
		
		qCopyObject = new Object();
		dCopyObject = new Object();
		
		MCCopyObject = new Object();
		MBCopyObject = new Object();
	}
	
	public void setD(int d) {
		this.d = d;
	}
	
	public int getD() {
		return d;
	}
	
	public void setMinQ(int q) {
		this.q = this.q > q ? q : this.q;
	}
	
	public int getQ() {
		return q;
	}
	
	public void setZ(Vector Z) {
		this.Z = Z;
	}
	
	public Vector getZ() {
		return Z;
	}

	public void setMB(Matrix MB) {
		this.MB = MB;
	}
	
	public Matrix getMB() {
		return MC;
	}
	
	public void setMC(Matrix MC) {
		this.MC = MC;
	}
	
	public Matrix getMC() {
		return MC;
	}
	
	public void setMM(Matrix MM) {
		this.MM = MM;
	}
	
	public Matrix getMM() {
		return MM;
	}
	
	public void insertIntoMR(int start, int end, Matrix MRh) {
		MR.insertIntoIndexes(start, end, MRh);
	}
	
	public Matrix getMR() {
		return MR;
	}
	
	public CyclicBarrier getInputBarrier() {
		return inputBarrier;
	}
	
	public CyclicBarrier getQBarrier() {
		return qBarrier;
	}
	
	public CyclicBarrier getFinishBarrier() {
		return finishBarrier;
	}
	
	public Lock getInputLock() {
		return inputLock;
	}
	
	public Lock getQSetLock() {
		return qSetLock;
	}
	
	public Object getQCopyObject() {
		return qCopyObject;
	}
	
	public Object getDCopyObject() {
		return dCopyObject;
	}
	
	public Object getMCCopyObject() {
		return MCCopyObject;
	}
	
	public Object getMBCopyObject() {
		return MBCopyObject;
	}
}