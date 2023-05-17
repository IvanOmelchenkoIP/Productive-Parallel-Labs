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
 * файл: ./src/lab3/Data/VectorCreator.java
 * Даний файл містить клас VectorCreator
 */

package lab3.Data;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class VectorCreator extends Creator {

	VectorCreator(UserInputScanner ui) {
		super(ui);
	}

	@Override
	public Vector create(String name) throws Exception {
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
