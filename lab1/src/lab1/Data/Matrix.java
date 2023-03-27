package lab1.Data;

import lab1.Data.Matrix;

public class Matrix {

	private int N;
	private int[][] data;
	
	public Matrix(int[][] data) {
		this.data = data;
		this.N = data.length;
	}
	
	public static Matrix fromString(String matrix, int N) {
		int[][] data = new int[N][N];		
		String[] lines = matrix.trim().split("\n");
		for (int i = 0; i < N; i++) {
			String[] elements = lines[i].split(" ");
			for (int j = 0; j < N; j++) {
				data[i][j] = Integer.parseInt(elements[j]);
			}
		}
		return new Matrix(data);
	}
	
	public static Matrix cleanMarix(int N) {
		int [][] data = new int[N][N];
		return new Matrix(data);
	}
	
	public int[][] getData() {
		return data;
	}
}
