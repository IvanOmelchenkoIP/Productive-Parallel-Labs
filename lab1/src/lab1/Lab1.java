package lab1;

import lab1.T.T1;
import lab1.T.T2;
import lab1.T.T3;
import lab1.T.T4;

public class Lab1 {

	public static void main(String[] args) {
		final int P = 4;
		
		Thread t1 = new T1(P);
		Thread t2 = new T2(P);
		Thread t3 = new T3(P);
		Thread t4 = new T4(P);
		
		t1.start();
		t2.start();
		t3.start();
		t4.start();
	}

}
