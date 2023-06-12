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
 * файл: ./src/lab3/Data/MatrixCreator.java
 * Даний файл містить клас MatrixCreator
 */

package lab3.Data;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class MatrixCreator extends Creator {

	MatrixCreator(UserInputScanner ui) {
		super(ui);
	}

	@Override
	public Matrix create(String name) throws Exception {
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
