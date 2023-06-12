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
 * файл: ./src/lab3/Data/DataCreators.java
 * Даний файл містить клас DataCreators і допоміжні класи DataGenerationTypes, UserInputScanner і FileReader
 */

package lab3.Data;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

//Допоміжний клас для зберігання різних типів генерації даних
class DataGenerationTypes {
	static final int USER_INPUT = 0, READ_FILE = 1, GENERATE_RANDOM = 2, FILL_WITH_NUMBER = 3;
}

public class DataCreators {

	final private int MIN_N;
	final private int MAX_SMALL_N;

	private int N;
	private int dataGeneration;
	private int fillNumber;
	private String fileContents;

	private UserInputScanner ui;
	private FileReader fr;

	private NumberCreator nc;
	private MatrixCreator mc;
	private VectorCreator vc;

	public DataCreators(int threads) {
		this.MIN_N = threads;
		this.MAX_SMALL_N = threads * 3;
		
		this.ui = new UserInputScanner();
		this.nc = new NumberCreator(ui);
		this.mc = new MatrixCreator(ui);
		this.vc = new VectorCreator(ui);
		
	}

	private void setUserN() throws Exception {
		System.out.print("Введіть розмірність векторів та матриць N (N >= 4): ");
		N = ui.readNumber();
		if (N < MIN_N) {
			throw new Exception("Неможливо продовжити виконання програми - невірне значення N!");
		}
	}

	private void setUserInputType() throws IOException, Exception {
		if (N <= MAX_SMALL_N) {
			dataGeneration = DataGenerationTypes.USER_INPUT;
		} else {
			System.out.print(
					"Виберіть спосіб вводу даних:\n1 - зчитати з файлу\n2 - згенерувати випадково\n3 - заповнити числом\n> ");
			dataGeneration = ui.readNumber();
			switch (dataGeneration) {
			case DataGenerationTypes.READ_FILE -> {
				System.out.print("Введіть назву файлу: ");
				String filename = ui.readLine();
				fileContents = fr.read(filename);
			}
			case DataGenerationTypes.GENERATE_RANDOM -> {
				return;
			}
			case DataGenerationTypes.FILL_WITH_NUMBER -> {
				System.out.print("Введіть число для заповнення структур даних: ");
				fillNumber = ui.readNumber();
			}
			default -> {
				throw new Exception("Невірний вибір при виборі методу заповнення матриці!");
			}
			}
		}
	}
	
	private void setDataParams() {
		nc.setParams(fileContents, N, dataGeneration, fillNumber);
		mc.setParams(fileContents, N, dataGeneration, fillNumber);
		vc.setParams(fileContents, N, dataGeneration, fillNumber);
	}
	
	public void config() throws IOException, Exception {
		setUserN();
		setUserInputType();
		setDataParams();
	}
	
	public int getN() {
		return N;
	}

	public NumberCreator getNumberCreator() {
		return nc;
	}

	public MatrixCreator getMatrixCreator() {
		return mc;
	}

	public VectorCreator getVectorCreator() {
		return vc;
	}
}

//Клас для читання вводу користувача
class UserInputScanner {

	private Scanner scanner;

	UserInputScanner() {
		scanner = new Scanner(System.in);
	}

	String readLine() {
		return scanner.nextLine();
	}

	int readNumber() {
		int number = scanner.nextInt();
		readLine();
		return number;
	}
}

//Клас для читання файлу
class FileReader {

	String read(String filename) throws IOException {

		DataInputStream stream = null;
		try {
			stream = new DataInputStream(new FileInputStream(filename));
			return new String(stream.readAllBytes(), StandardCharsets.UTF_8);
		} finally {
			if (stream != null) {
				stream.close();
			}
		}
	}
}