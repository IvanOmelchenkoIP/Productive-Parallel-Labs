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
 * файл: ./src/lab3/Data/Creator.java
 * Даний файл містить абстрактний клас Creator
 */

package lab3.Data;

public abstract class Creator {

	protected UserInputScanner ui;

	protected String fileContents;
	protected int N;
	protected int dataGeneration;
	protected int fillNumber;
	
	protected Creator(UserInputScanner ui) {
		this.ui = ui;
	}
	
	void setParams(String fileContents, int N, int dataGeneration, int fillNumber) {
		this.fileContents = fileContents;
		this.N = N;
		this.dataGeneration = dataGeneration;
		this.fillNumber = fillNumber;
	}
	
	public abstract Object create(String name) throws Exception;
}
