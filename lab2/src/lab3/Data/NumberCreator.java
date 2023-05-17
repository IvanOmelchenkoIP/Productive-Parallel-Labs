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
 * файл: ./src/lab3/Data/NumberCreator.java
 * Даний файл містить клас NumberCreator
 */
package lab3.Data;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class NumberCreator extends Creator {

	NumberCreator(UserInputScanner ui) {
		super(ui);
	}

	@Override
	public Integer create(String name) throws Exception {
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
