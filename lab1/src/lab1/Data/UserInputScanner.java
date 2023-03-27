package lab1.Data;

import java.util.Scanner;

public class UserInputScanner {
	
	private Scanner scanner;
	
	UserInputScanner() {
		scanner = new Scanner(System.in);
	}
	
	public int getUserN() {
		System.out.print("Введіть розмірність векторів та матриць: ");
		return scanner.nextInt();
	}
	
	int scanDataGenerationType() {
		System.out.print("Виберіть спосіб вводу даних:\n1 - зчитати з файлу\n2 - згенерувати випадково\n3 - заповнити числом\n> ");
		return scanNumber();
	}
	
	String scanFilename() {
		System.out.print("Введіть назву файлу: ");
		return scanLine();
	}
	
	int scanFillNumber() {
		System.out.print("Введіть число для заповнення структур даних: ");
		return scanNumber();
	}
	
	public int scanNumber() {
		int num = scanner.nextInt();
		scanner.nextLine();
		return num;
	}
	
	private String scanLine() {
		return scanner.nextLine();
	}
}