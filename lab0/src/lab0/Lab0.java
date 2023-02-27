package lab0;

import lab0.T.*;

public class Lab0 {

	public static void main(String[] args) {

		new Thread(new T1()).start();
		new Thread(new T2()).start();
		new Thread(new T3()).start();
	}

}
