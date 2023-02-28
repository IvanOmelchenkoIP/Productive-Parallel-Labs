package lab0.T;

import lab0.Data.Data;
import lab0.Data.DataMath;

public class T2 implements Runnable {
	
	@Override
	public void run() {
		System.out.println("Функція F2 - математичний вираз: q = MAX(MH * MK - ML)");
		
		// q = MAX(MH * MK - ML)
		int n = 10;
		
		Data data = new Data();
		
		int[][] MH = data.generateMatrix(n);
		int[][] MK = data.generateMatrix(n);
		int[][] ML = data.generateMatrix(n);
		
		int q = DataMath.max(DataMath.substractMatrixes(n, DataMath.multiplyMatrixes(n, MH, MK), ML));
		
		System.out.println("Функція F1 - результуюче число:");
		System.out.println(q);
	}

}
