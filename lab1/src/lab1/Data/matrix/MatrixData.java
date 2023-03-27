package lab1.Data.matrix;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import lab1.Data.UserInputScanner;

//допоміжний клас для створення матриць
public class MatrixData {
	
	private UserInputScanner ui;
	
	public MatrixData(UserInputScanner ui) {
		this.ui = ui;
	}
	
	public int[][] getMatrixFromInput(String name, int N) {
		System.out.println("Ввід матриці " + name + ": ");
		int[][] MA = new int[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				System.out.print("Введіть елемент " + name.toLowerCase() + (i + 1) + (j + 1) + ": ");
				MA[i][j] = ui.scanNumber();
			}		
		}
		return MA;
	}
	
	public int[][] generateRandom(int N) {
		int[][] MA = new int[N][N];
		for (int i = 0; i < N; i ++) {
			for (int j = 0; j < N; j++) {
				MA[i][j] = ThreadLocalRandom.current().nextInt();
			}
		}
		return MA;
	}
	
	public int[][] fillWithNumber(int N, int num) {
		final int[][] MA = new int[N][N];
		Arrays.stream(MA).forEach(row -> Arrays.fill(row, num));
		return MA;
	}
	
	public String getFromFileInput(String contents, int N, String matrixName) {
		String[] lines = contents.split("\n");
		int index = Arrays.asList(lines).indexOf(matrixName) + 1;
		String matrix = String.join("\n", Arrays.copyOfRange(lines, index, index + N));
		return matrix;
	}
}
