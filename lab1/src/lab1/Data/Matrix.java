package lab1.Data;

import lab1.Data.Matrix;

public class Matrix {

	private int N;
	private int M;
	private int[][] data;
	
	public Matrix(int[][] data) {
		this.data = data;
		this.N = data.length;
		this.M = data[0].length;
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
	
	@Override
	public String toString() {
		String MA = "";
		for (int[] row : data) {
			for (int num : row) {
				MA += num + " ";
			}
			MA += "\n";
		}
		return MA;
	}
	
	public int[][] getData() {
		return data;
	}
	
	public int getN() {
		return N;
	}
	
	public int getM() {
		return M;
	}
	
	public Matrix getPartialMatrix(int start, int end) {
		int[][] dataB = new int[end - start][M];
		int ind = 0;
		for (int i = start; i <= end; i++) {
			for (int j = 0; j < M; j++) {
				dataB[ind][j] = data[i][j];
			}
			ind += 1;
		}
		return new Matrix(dataB);
	}
	
	public Matrix getMatrixProduct(Matrix MB) {
		int[][] dataMB = MB.getData();
		int[][] dataMC = new int[N][MB.getM()];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < MB.getM(); j++) {
				for (int k = 0; k < MB.getN(); k++)
					dataMC[i][j] += data[i][k] * dataMB[k][j];
			}
		}
		return new Matrix(dataMC);
	}
	
	public Matrix getNumberProduct(int a) {
		int[][] dataB = new int[N][M];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				dataB[i][j] = data[i][j] * a;
			}
		}
		return new Matrix(dataB);
	}
	
	public void insertIntoIndexes(int start, int end, Matrix MB) {
		int[][] dataB = MB.getData();
		int ind = 0;
		for (int i = start; i <= end; i++) {
			for (int j = 0; j <= M; j++) {
				data[i][j] = dataB[ind][j];
			}
			ind += 1;
		}
	}
}
