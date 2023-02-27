package lab0.T;

import java.util.Arrays;

import lab0.Data.Data;
import lab0.Data.DataMath;

public class T1 implements Runnable {

	@Override
	public void run() {
		System.out.println("F1");

		// D = (SORT(A + B) + C) * (MA * MB)
		int sizeN = 10;
		
		Data data = new Data();
		double[] A = data.generateVector();
		double[] B = data.generateVector();
		double[] C = data.generateVector();
		
		double[][] MA = data.generateMatrix();
		double[][] MB = data.generateMatrix();

		A = DataMath.addVectors(sizeN, A, B);
		Arrays.sort(A);
		A = DataMath.addVectors(sizeN, A, C);		
		double D[] = DataMath.multiplyMatrixAndVector(sizeN, DataMath.multiplyMatrixes(sizeN, MA, MB), A);
		for (int i = 0; i < sizeN; i++) {
			System.out.println(D[i]);
		}

	}

}
