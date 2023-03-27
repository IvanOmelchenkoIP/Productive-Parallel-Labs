package lab1.Data.vector;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import lab1.Data.UserInputScanner;

//допоміжний клас для створення векторів
public class VectorData {
	
	private UserInputScanner ui;
	
	public VectorData(UserInputScanner ui) {
		this.ui = ui;
	}
	
	public int[] getVectorFromInput(String name, int N) {
		System.out.println("Ввід вектора " + name + ": ");
		int[] A = new int[N];
		for (int i = 0; i < N; i++) {
			System.out.print("Введіть елемент " + name.toLowerCase() + (i + 1) + ": ");
			A[i] = ui.scanNumber();
		}
		return A;
	}
	
	public int[] generateRandom(int N) {
		int[] A = new int[N];
		for (int i = 0; i < N; i++) {
			A[i] = ThreadLocalRandom.current().nextInt();
		}
		return A;
	}
	
	public int[] fillWithNumber(int N, int num) {
		int[] A = new int[N];
		Arrays.fill(A, num);
		return A;
	}
	
	public String getFromFileInput(String contents, int N, String vectorName) {
		String[] lines = contents.split("\n");
		int index = Arrays.asList(lines).indexOf(vectorName) + 1;
		String vector = String.join("\n", Arrays.copyOfRange(lines, index, index + 1));
		return vector;
	}
}
