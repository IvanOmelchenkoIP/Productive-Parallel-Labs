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
 * файл: ./src/lab4/Data/DataCreator.java
 * Даний файл клас для створення різних необхідних типів даних і доаоміжні класи
 */

package lab4.Data;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

//Допоміжний клас для зберігання різних типів генерації даних
class DataGenerationTypes {
	static final int USER_INPUT = 0, READ_FILE = 1, GENERATE_RANDOM = 2, FILL_WITH_NUMBER = 3;
}

public class UserDataManager {

	private final int MIN_N;
	private final int MAX_SMALL_N;
	
	private int N;
	private int dataGeneration;
	private int fillNumber;
	private String fileContents;
	
	private UserInputScanner ui;
	private FileReader fr;

	private NumberCreator nc;
	private MatrixCreator mc;
	private VectorCreator vc;
	
	public UserDataManager(int threads) {
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
		nc.setParams(fileContents, dataGeneration, fillNumber);
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
	
	public int createNumber(String name) throws Exception {
		return nc.createNumber(name);
	}

	public Matrix createMatrix(String name) throws Exception {
		return mc.createMatrix(name);
	}

	public Vector createVector(String name) throws Exception {
		return vc.createVector(name);
	}	
	
	public void printMatrix(String name, Matrix MA) {
		System.out.println("Матриця " + name + "\n");
		int[][] dataMA = MA.getData();
		for (int i = 0; i < MA.getN(); i++) {
			for (int j = 0; j < MA.getM(); j++) {
				System.out.print(dataMA[i][j] + " ");
			}
			System.out.println();
		}
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

//Клас для створення чисел
class NumberCreator {

	private UserInputScanner ui;

	private String fileContents;
	private int dataGeneration;
	private int fillNumber;

	NumberCreator(UserInputScanner ui) {
		this.ui = ui;
	}

	void setParams(String fileContents, int dataGeneration, int fillNumber) {
		this.fileContents = fileContents;
		this.dataGeneration = dataGeneration;
		this.fillNumber = fillNumber;
	}

	int createNumber(String name) throws Exception {
		switch (dataGeneration) {
		case DataGenerationTypes.USER_INPUT -> {
			return getNumberFromUnput(name);
		}
		case DataGenerationTypes.READ_FILE -> {
			return getFromInputFile(name);
		}
		case DataGenerationTypes.GENERATE_RANDOM -> {
			return generateRandom();
		}
		case DataGenerationTypes.FILL_WITH_NUMBER -> {
			return fillNumber;
		}
		default -> {
			throw new Exception("Число " + name + " не може бути згенероване!");
		}
		}
	}
	
	int getNumberFromUnput(String name) {
		System.out.print("Введіть число " + name + ": ");
		int a = ui.readNumber();
		return a;
	}
	
	int generateRandom() {
		return ThreadLocalRandom.current().nextInt();
	}
	
	int getFromInputFile(String name) {
		String[] lines = fileContents.split("\n");
		int index = Arrays.asList(lines).indexOf(name) + 1;
		return Integer.parseInt(lines[index]);
	}
}

//Кдас для створення матриць
class MatrixCreator {

	private UserInputScanner ui;

	private String fileContents;
	private int N;
	private int dataGeneration;
	private int fillNumber;

	MatrixCreator(UserInputScanner ui) {
		this.ui = ui;
	}

	void setParams(String fileContents, int N, int dataGeneration, int fillNumber) {
		this.fileContents = fileContents;
		this.N = N;
		this.dataGeneration = dataGeneration;
		this.fillNumber = fillNumber;
	}

	Matrix createMatrix(String name) throws Exception {
		switch (dataGeneration) {
		case DataGenerationTypes.USER_INPUT -> {
			return new Matrix(getMatrixFromInput(name));
		}
		case DataGenerationTypes.READ_FILE -> {
			return Matrix.fromString(getFromFileInput(name), N);
		}
		case DataGenerationTypes.GENERATE_RANDOM -> {
			return new Matrix(generateRandom());
		}
		case DataGenerationTypes.FILL_WITH_NUMBER -> {
			return new Matrix(fillWithNumber());
		}
		default -> {
			throw new Exception("Матриця " + name + " не може бути згенерована!");
		}
		}
	}

	int[][] getMatrixFromInput(String name) {
		System.out.println("Ввід матриці " + name + ": ");
		int[][] MA = new int[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				System.out.print("Введіть елемент " + name.toLowerCase() + (i + 1) + (j + 1) + ": ");
				MA[i][j] = ui.readNumber();
			}
		}
		return MA;
	}

	int[][] generateRandom() {
		int[][] MA = new int[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				MA[i][j] = ThreadLocalRandom.current().nextInt();
			}
		}
		return MA;
	}

	int[][] fillWithNumber() {
		final int[][] MA = new int[N][N];
		Arrays.stream(MA).forEach(row -> Arrays.fill(row, fillNumber));
		return MA;
	}

	String getFromFileInput(String name) {
		String[] lines = fileContents.split("\n");
		int index = Arrays.asList(lines).indexOf(name) + 1;
		String matrix = String.join("\n", Arrays.copyOfRange(lines, index, index + N));
		return matrix;
	}
}

//Клас для створення векторів
class VectorCreator {

	private UserInputScanner ui;

	private String fileContents;
	private int N;
	private int dataGeneration;
	private int fillNumber;

	VectorCreator(UserInputScanner ui) {
		this.ui = ui;
	}

	void setParams(String fileContents, int N, int dataGeneration, int fillNumber) {
		this.fileContents = fileContents;
		this.N = N;
		this.dataGeneration = dataGeneration;
		this.fillNumber = fillNumber;
	}

	Vector createVector(String name) throws Exception {
		switch (dataGeneration) {
		case DataGenerationTypes.USER_INPUT -> {
			return new Vector(getVectorFromInput(name));
		}
		case DataGenerationTypes.READ_FILE -> {
			return Vector.fromString(getFromFileInput(name), N);
		}
		case DataGenerationTypes.GENERATE_RANDOM -> {
			return new Vector(generateRandom());
		}
		case DataGenerationTypes.FILL_WITH_NUMBER -> {
			return new Vector(fillWithNumber());
		}
		default -> {
			throw new Exception("Вектор " + name + " не може бути згенерованим!");
		}
		}
	}
	
	int[] getVectorFromInput(String name) {
		System.out.println("Ввід вектора " + name + ": ");
		int[] A = new int[N];
		for (int i = 0; i < N; i++) {
			System.out.print("Введіть елемент " + name.toLowerCase() + (i + 1) + ": ");
			A[i] = ui.readNumber();
		}
		return A;
	}
	
	int[] generateRandom() {
		int[] A = new int[N];
		for (int i = 0; i < N; i++) {
			A[i] = ThreadLocalRandom.current().nextInt();
		}
		return A;
	}
	
	int[] fillWithNumber() {
		int[] A = new int[N];
		Arrays.fill(A, fillNumber);
		return A;
	}
	
	String getFromFileInput(String name) {
		String[] lines = fileContents.split("\n");
		int index = Arrays.asList(lines).indexOf(name) + 1;
		String vector = String.join("\n", Arrays.copyOfRange(lines, index, index + 1));
		return vector;
	}
}
