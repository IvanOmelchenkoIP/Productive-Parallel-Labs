package lab0.T;

import java.util.ArrayList;
import java.util.List;

import lab0.Data.Data;

public class T2 implements Runnable {

	double[][] substractMatrixes(double[][] MA, double[][] MB) {
		double[][] MC = new double[10][10];
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				MC[i][j] = MA[i][j] - MB[i][j];
			}
		}
		return MC;
	}
	
	double[][] multiplyMatrixes(double[][] MA, double[][] MB) {
		double[][] MC = new double[10][10];
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				for (int k = 0; k < 10; k++)
					MC[i][j] += MA[i][k] * MB[k][j];
			}
		}
		return MC;
	}
	
	double max(double[][] MA) {
		List<Double> B = new ArrayList<Double>();
		for (double[] A : MA) {
			for (double a : A) {
				B.add(a);
			}
		}	
		double max = B.remove(0);
		for (double b : B) {
			if (b > max) {
				max = b;
			}
		}
		return max;
	}
	
	@Override
	public void run() {
		System.out.println("F2");
		
		// q = MAX(MH * MK - ML)
		Data data = new Data();
		
		double[][] MH = data.generateMatrix();
		double[][] MK = data.generateMatrix();
		double[][] ML = data.generateMatrix();
		
		double q = max(substractMatrixes(multiplyMatrixes(MH,MK), ML));
		
		System.out.println(q);
	}

}
