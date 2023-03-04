package lab0.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MatrixData {

	public int[][] generateRandom(int size) {
		int[][] MA = new int[size][size];
		for (int i = 0; i < 10; i ++) {
			for (int j = 0; j < 10; j++) {
				MA[i][j] = ThreadLocalRandom.current().nextInt();
			}
		}
		return MA;
	}
	
	public int[][] fillWithNumber(int size, int num) {
		final int[][] MA = new int[size][size];
		Arrays.stream(MA).forEach(row -> Arrays.fill(row, num));
		return MA;
	}
	
	public int[][] generateFromFileInput(String contents, int size, String matrixName) {
		String[] lines = contents.split("\n");
		int index = Arrays.asList(lines).indexOf(matrixName) + 1;
		String str = String.join("\n", Arrays.copyOfRange(lines, index, index + size));
		return generateFromString(str, size);
	}
	
	private int[][] generateFromString(String str, int size) {
		int[][] MA = new int[size][size];		
		String[] lines = str.trim().split("\n");
		for (int i = 0; i < size; i++) {
			String[] elements = lines[i].split(" ");
			for (int j = 0; j < size; j++) {
				MA[i][j] = Integer.parseInt(elements[j]);
			}
		}
		return MA;
	}
	
	
	public int[][] getMatrixDifference(int[][] MA, int[][] MB, int size) {
		int[][] MC = new int[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				MC[i][j] = MA[i][j] - MB[i][j];
			}
		}
		return MC;
	}

	public int[][] getMatrixMultiplyProduct(int[][] MA, int[][] MB, int size) {
		int[][] MC = new int[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				for (int k = 0; k < size; k++)
					MC[i][j] += MA[i][k] * MB[k][j];
			}
		}
		return MC;
	}

	public int max(int[][] MA) {
		List<Integer> B = new ArrayList<Integer>();
		for (int[] A : MA) {
			for (int a : A) {
				B.add(a);
			}
		}
		int max = B.remove(0);
		for (int b : B) {
			if (b > max) {
				max = b;
			}
		}
		return max;
	}
}
