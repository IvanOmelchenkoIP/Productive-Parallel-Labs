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
 * файл: ./src/lab3/Data/Data.java
 * Даний файл містить захищений модуль Data
 */

package lab3.Data;

public class Data {
	
	final private int THREADS;
	final private int REST;
	
	private Matrix MB;
	private Matrix MR;
	
	private Matrix MQ;
	
	// спільні ресурси
	private int x;
	private int q;
	
	private int p;
	private int e;
	
	private Matrix MZ;
	private Vector A;
	private Vector B;
	
	private int FInput = 0;
	private int Fq = 0;
	private int FMQ = 0;
	private int Fx = 0;
	
	public Data(int THREADS, int N) {
		this.THREADS = THREADS;
		this.REST = THREADS - 1;
		
		this.q = (int)Double.POSITIVE_INFINITY;
		this.MQ = new Matrix(new int[N][N]);
	}
	
	
	public Matrix getMB() {
		return MB;
	}
	
	public void setMB(Matrix MB) {
		this.MB = MB;
	}
	
	public Matrix getMR() {
		return MR;
	}
	
	public void setMR(Matrix MR) {
		this.MR = MR;
	}
	
	public void insertToMQByM(Matrix MB, int start, int finish) {
		MQ.insertByM(MB, start, finish);
	}
	
	public Matrix getMQ() {
		return MQ;
	}
	
	
	// захищені методи і функції
	public synchronized Object syncInput(Creator creator, String name) throws Exception {
		return creator.create(name);
	}
	
	
	public synchronized void waitForInput() throws InterruptedException {
		if (FInput < REST) {
			wait();
		}
	}
	
	public synchronized void signalInput() {
		if (++FInput == REST) {
			notifyAll();
		}
	}
	
	public synchronized void waitForQ() throws InterruptedException {
		if (Fq < THREADS) {
			wait();
		}
	}
	
	public synchronized void signalQ() {
		if (++Fq == THREADS) {
			notifyAll();
		}
	}
	
	public synchronized void waitForMQ() throws InterruptedException {
		if (FMQ < THREADS) {
			wait();
		}
	}
	
	public synchronized void signalMQ() {
		if (++FMQ == THREADS) {
			notifyAll();
		}
	}
	public synchronized void waitForX() throws InterruptedException {
		if (Fx < REST) {
			wait();
		}
	}
	
	public synchronized void signalX() {
		if (++Fx == REST) {
			notify();
		}
	}
	
	
	public synchronized void addToX(int y) {
		x += y;
	}
	
	public synchronized int getX() {
		return x;
	}
	
	public synchronized void setMinQ(int x) {
		this.q = q > x ? x : q;
	}
	
	public synchronized int getQ() {
		return q;
	}
	
	 
	public synchronized void setA(Vector A) {
		this.A = A;
	}
	
	public synchronized Vector getACopy() {
		return A.getCopy();
	}
	
	public synchronized void setB(Vector B) {
		this.B = B;
	}
	
	public synchronized Vector getBCopy() {
		return B.getCopy();
	}
	
	public synchronized void setMZ(Matrix MZ) {
		this.MZ = MZ;
	}
	
	public synchronized Matrix getMZCopy() {
		return MZ.getCopy();
	}
	
	public synchronized void setP(int p) {
		this.p = p;
	}
	
	public synchronized int getP() {
		return p;
	}
	
	public synchronized void addToE(int x) {
		this.e += x;
	}
	
	public synchronized int getE() {
		return e;
	}
}
