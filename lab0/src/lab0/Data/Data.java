package lab0.Data;

public class Data {

	public double[][] generateMatrix() {
		final double[][] M = new double[10][10];
		for (int i = 0; i < 10; i ++) {
			for (int j = 0; j < 10; j++) {
				M[i][j] = 2;
			}
		}
		return M;
	}
	
	public double[] generateVector() {
		final double[] A = new double[10];
		for (int i = 0; i < 10; i++) {
			A[i] = 2;
		}
		return A;
	}
}
