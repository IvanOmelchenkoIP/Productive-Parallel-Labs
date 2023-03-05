package lab0;

import lab0.T.*;

public class Lab0 {

	public static void main(String[] args) {
		Thread t1 = new T1();
		Thread t2 = new T2();
		Thread t3 = new T3();
		
		t1.start();
		t2.start();
		t3.start();
		
		try {
			t1.join();
		} catch (InterruptedException ex) {
			System.out.println("Виконання потоку T1 було перерване: " + ex.getMessage());
		}
		try {
			t2.join();
		} catch (InterruptedException ex) {
			System.out.println("Виконання потоку T2 було перерване: " + ex.getMessage());
		}
		try {
			t3.join();	
		} catch (InterruptedException ex) {
			System.out.println("Виконання потоку T3 було перерване: " + ex.getMessage());
		}		
	}
}
