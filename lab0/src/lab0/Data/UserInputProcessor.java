package lab0.Data;

import java.util.Scanner;

class EnterChoices {
	public static final short READ_FILE = 1, GENERATE_RANDOM = 2, FILL_WITH_NUMBER = 3;
}

public class UserInputProcessor {
	
	private final int N = 4;
	
	private Scanner scanner;
	
	public UserInputProcessor() {
		scanner = new Scanner(System.in);
	}
	
	public int getUserN() {
		return scanner.nextInt();
	}
	
	public int[][] getMatrixFromInput(int size, String name) {
		int[][] MA = new int[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				System.out.println("Введіть елемент " + name.toLowerCase() + (i + 1) + (j + 1) + ": ");
				MA[i][j] = scanner.nextInt();
			}
		}
		return MA;
	}
	
	public int[][] createMatrix(int size, String name) throws Exception {
		System.out.println("Ввід матриці " + name + ":");
		
		if (size <= N) {
			return getMatrixFromInput(size, name);
		}
		
		System.out.println("Введіть:\n1 - зчитати матрицю з файлу\n2 - згенерувати випадкову матрицю\n3 - заповнити матрицю числом");
		int choice = scanner.nextInt();
		MatrixData md = new MatrixData();
		switch(choice) {
		case EnterChoices.READ_FILE: 
			System.out.println("Введіть назву файлу: ");
			String contents = new FileReader().read(scanner.nextLine());
			return md.generateFromFileInput(contents, size, name);
		case EnterChoices.GENERATE_RANDOM:
			return md.generateRandom(size);
		case EnterChoices.FILL_WITH_NUMBER:
			System.out.println("Введіть число для заповнення матриці: ");
			return md.fillWithNumber(size, scanner.nextInt());
		default:
			throw new Exception("Неможливо продовжити роботу даного потоку!");
		}
	}
	
	private int[] generateVectorFromInput(int size, String name) {
		int[] A = new int[size];
		for (int i = 0; i < size; i++) {
			System.out.println("Введіть елемент " + name.toLowerCase() + (i + 1) + ": ");
			A[i] = scanner.nextInt();
		}
		return A;
	}
	
	public int[] createVector(int size, String name) throws Exception {
		System.out.println("Ввід вектора " + name + ": ");
		
		if (size <= N) {
			return generateVectorFromInput(size, name);
		}
		
		System.out.println("Введіть:\n1 - зчитати вектор з файлу\n2 - згенерувати випадковий вектор\n3 - заповнити вектор числом");
		int choice = scanner.nextInt();
		VectorData vd = new VectorData();
		switch(choice) {
		case EnterChoices.READ_FILE: 
			System.out.println("Введіть назву файлу: ");
			String contents = new FileReader().read(scanner.nextLine());
			return vd.generateFromFileInput(contents, size, name);
		case EnterChoices.GENERATE_RANDOM:
			return vd.generateRandom(size);
		case EnterChoices.FILL_WITH_NUMBER:
			System.out.println("Введіть число для заповнення вектора: ");
			return vd.fillWithNumber(size, scanner.nextInt());
		default:
			throw new Exception("Неможливо продовжити роботу даного потоку!");
		}
	}
	
	public void close() {
		scanner.close();
	}
}
