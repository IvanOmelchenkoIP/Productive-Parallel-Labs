package lab1.Data.scalar;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import lab1.Data.UserInputScanner;

public class ScalarNumberData {

	private UserInputScanner ui;
	
	public ScalarNumberData(UserInputScanner ui) {
		this.ui = ui;
	}
	
	public int getNumberFromUnput(String name) {
		System.out.print("Введіть число " + name + ": ");
		int a = ui.scanNumber();
		return a;
	}
	
	public int generateRandom() {
		return ThreadLocalRandom.current().nextInt();
	}
	
	public int getFromInputFile(String contents, String vectorName) {
		String[] lines = contents.split("\n");
		int index = Arrays.asList(lines).indexOf(vectorName) + 1;
		return Integer.parseInt(lines[index]);
	}
}
