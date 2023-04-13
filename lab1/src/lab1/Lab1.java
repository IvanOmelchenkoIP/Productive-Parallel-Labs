// MR = MB*(MC*MM)*d + min(Z)*MC

package lab1;

import java.io.IOException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import lab1.Data.Data;
import lab1.T.T1;
import lab1.T.T2;
import lab1.T.T3;
import lab1.T.T4;

public class Lab1 {

	public static void main(String[] args) {
		final int P = 4;		
		int N;
		
		Data data = new Data();

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
		System.out.println(H);
		
		Thread t1 = new T1(H, data);
		Thread t2 = new T2(H, data);
		Thread t3 = new T3(H, data);
		Thread t4 = new T4(H, data);
		
		t1.start();
		t2.start();
		t3.start();
		t4.start();
	}
}
