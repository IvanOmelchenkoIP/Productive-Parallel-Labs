package lab0.T;

import java.util.Arrays;

import lab0.Data.Data;
import lab0.Data.DataMath;

public class T3 implements Runnable {
	
	@Override
	public void run() {
		System.out.println("F3");
		
		// O = SORT(P) * (MR * MT)
		int sizeN = 10;
		
		Data data = new Data();
		
		double[] P = data.generateVector();
		double[][] MR = data.generateMatrix();
		double[][] MT = data.generateMatrix();
		
		Arrays.sort(P);
		double[] O = DataMath.multiplyMatrixAndVector(sizeN, DataMath.multiplyMatrixes(sizeN, MR, MT), P);
		
		for (int i = 0; i < sizeN; i++) {
			System.out.println(O[i]);
		}
	}

}
