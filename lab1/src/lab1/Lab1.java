// MR = MB*(MC*MM)*d + min(Z)*MC

package lab1;

import java.io.IOException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import lab1.Data.CommonData;
import lab1.Data.Data;
import lab1.Data.SyncData;
import lab1.T.T1;
import lab1.T.T2;
import lab1.T.T3;
import lab1.T.T4;

public class Lab1 {

	public static void main(String[] args) {
		final int P = 4;
		
		Data data = new Data();
		
		SyncData syncData = new SyncData();
		/*int N;
		try {
			N = data.setUserN();
		} catch (IOException ex) {
			System.out.println(ex);
			return;
		} catch (Exception ex) {
			System.out.println(ex);
			return;
		};*/
		
		//final int H = N / P;
		int H = 1;
		int N = 1;
		CommonData cd = new CommonData();
		
		Lock inputMutex = new ReentrantLock();
		Lock qMutex = new ReentrantLock();
		Semaphore t1InputSemaphore = new Semaphore(1);
		Semaphore t2InputSemaphore = new Semaphore(1);
		Semaphore t3InputSemaphore = new Semaphore(1);
		Semaphore t4InputSemaphore = new Semaphore(1);
		
		Thread t1 = new T1(N, H, cd, data, syncData);
		Thread t2 = new T2(N, H, cd, data, syncData);
		Thread t3 = new T3(N, H, cd, data, syncData);
		Thread t4 = new T4(N, H, cd, data, syncData);
		
		t1.start();
		t2.start();
		t3.start();
		t4.start();
	}
}
