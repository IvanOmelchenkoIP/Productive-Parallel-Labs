// MR = MB*(MC*MM)*d + min(Z)*MC

package lab1;

import lab1.T.T1;
import lab1.T.T2;
import lab1.T.T3;
import lab1.T.T4;

public class Lab1 {

	public static void main(String[] args) {
		final int P = 4;
		final int MIN_N = 4;

		int N = 1;
		System.out.println("Введіть N: ");
		
		if (N < MIN_N) {
			System.out.println("Неможливо продовжити виконання програми - невірне значення N!");
			return;
		}
		
		final int H = N / P;
		
		Thread t1 = new T1(N, P);
		Thread t2 = new T2(N, P);
		Thread t3 = new T3(N, P);
		Thread t4 = new T4(N, P);
		
		t1.start();
		t2.start();
		t3.start();
		t4.start();
	}
}
