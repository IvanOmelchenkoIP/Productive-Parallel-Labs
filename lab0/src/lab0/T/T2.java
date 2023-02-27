package lab0.T;

import lab0.Data.Data;
import lab0.Data.DataMath;

public class T2 implements Runnable {
	
	@Override
	public void run() {
		System.out.println("F2");
		
		// q = MAX(MH * MK - ML)
		int sizeN = 10;
		
		Data data = new Data();
		
		double[][] MH = data.generateMatrix();
		double[][] MK = data.generateMatrix();
		double[][] ML = data.generateMatrix();
		
		double q = DataMath.max(DataMath.substractMatrixes(sizeN, DataMath.multiplyMatrixes(sizeN, MH, MK), ML));
		
		System.out.println(q);
	}

}
