package lab0.Data;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class Data {

	public int getInputNumber(String msg) {
		System.out.print(msg);
		return Integer.parseInt(DataInputScan.readInput());
	}
	
	public int[][] getMatrixFromInput(String name, int n) {
		final int[][] MA = new int[n][n];
		
		System.out.println("Ввід матриці " + name + ":");
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				MA[i][j] = getInputNumber("Введіть елемент " + name.toLowerCase() + (i + 1) + (j + 1) + ": ");
			}
		}
		return MA;
	}
	
	public int[][] generateMatrixFromFile(int n, String filepath) throws IOException {
		String contents = DataFileRead.read(filepath);
				
		final int[][] MA = new int[n][n];
		String[] lines = contents.trim().split("\n");
		for (int i = 0; i < n; i++) {
			String[] elements = lines[i].split(" ");
			for (int j = 0; j < n; j++) {
				MA[i][j] = Integer.parseInt(elements[j]);
			}
		}
		return MA;
	}
	
	public int[][] generatePresetMatrix(int n, int val) {
		final int[][] MA = new int[n][n];
		Arrays.stream(MA).forEach(row -> Arrays.fill(row, val));
		return MA;
	}
	
	public int[][] generateRandomMatrix(int n) {
		final int[][] MA = new int[n][n];
		for (int i = 0; i < 10; i ++) {
			for (int j = 0; j < 10; j++) {
				MA[i][j] = ThreadLocalRandom.current().nextInt();
			}
		}
		return MA;
	}
	
	public int[] generateVectorFromInput(int n, String name) {
		System.out.println("Ввід вектора " + name + ": ");
		
		final int[] A = new int[n];
		for (int i = 0; i < n; i++) {
			A[i] = getInputNumber("Введіть елемент " + name.toLowerCase() + (i + 1) + ": ");
		}
		return A;
	}
	
	public int[] generateVectorFromFile(int n, String filepath) throws IOException {
		String contents = DataFileRead.read(filepath);
		
		final int[] A = new int[n];
		String[] elements = contents.trim().split("");
		for (int i = 0; i < n; i++) {
			A[i] = Integer.parseInt(elements[i]);
		}
		return A;
	}
	
	public int[] generatePresetVector(int n, int val) {
		final int[] A = new int[n];
		Arrays.fill(A, val);
		return A;
	}
	
	public int[] generateRandomVector(int n) {
		final int[] A = new int[10];
		for (int i = 0; i < 10; i++) {
			A[i] = ThreadLocalRandom.current().nextInt();
		}
		return A;
	}
}
