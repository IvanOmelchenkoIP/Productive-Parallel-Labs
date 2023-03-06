package lab0.Data;

import java.util.Arrays;

public class Vector {
	private int size;
	private int[] data;
	
	public Vector(int[] data) {
		this.data = data;
		this.size = data.length;
	}
	
	public int[] getData() {
		return data;
	}
	
	public static Vector fromString(String vector, int size) {
		int[] data = new int[size];
		String[] elements = vector.trim().split("");
		for (int i = 0; i < size; i++) {
			data[i] = Integer.parseInt(elements[i]);
		}
		return new Vector(data);
	}
	
	@Override
	public String toString() {
		String MA = "";
		for (int num : data) {
			MA += num + " ";
		}
		MA += "\n";
		return MA;
	}
	
	public Vector getVectorSum(Vector B) {
		int[] dataB = B.getData();
		int[] dataC = new int[size];
		for (int i = 0; i < size; i++) {
			dataC[i] = data[i] + dataB[i];
		}
		return new Vector(dataC);
	}
	
	public Vector getMatrixMultiplyProduct(Matrix MA) {
		int[][] dataMA = MA.getData();
		int[] dataB = new int[size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				dataB[i] += dataMA[i][j] * data[j];
			}
		}
		return new Vector(dataB);
	}
	
	public Vector sort() {
		Arrays.sort(data);
		return this;
	}
}