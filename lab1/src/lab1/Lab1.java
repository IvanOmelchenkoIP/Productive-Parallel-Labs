// MR = MB*(MC*MM)*d + min(Z)*MC

package lab1;

import java.io.IOException;

import lab1.Data.CommonData;
import lab1.Data.Data;
import lab1.T.T1;
import lab1.T.T2;
import lab1.T.T3;
import lab1.T.T4;

public class Lab1 {

	public static void main(String[] args) {
		final int P = 4;

		Data data = new Data();
		int N;
		try {
			N = data.setUserN();
		} catch (IOException ex) {
			System.out.println(ex);
			return;
		} catch (Exception ex) {
			System.out.println(ex);
			return;
		};
		
		final int H = N / P;
		
		CommonData cd = new CommonData();

		Thread t1 = new T1(N, H, cd, data);
		Thread t2 = new T2(N, H, cd, data);
		Thread t3 = new T3(N, H, cd, data);
		Thread t4 = new T4(N, H, cd, data);
		
		t1.start();
		t2.start();
		t3.start();
		t4.start();
	}
}
