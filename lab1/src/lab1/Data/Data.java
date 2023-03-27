package lab1.Data;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Data {
	
	private final static int MAX_SMALL_N = 4;
	
	private String fnName;
	private MatrixData md;
	private VectorData vd;
	private FileReader fr;
	private UserInputScanner ui;
	
	private int N;
	private String fileContents;
	private int fillNumber;
	private int dataGeneration;
	
	public Data(String fnName) {
		this.fnName = fnName;
		this.md = new MatrixData();
		this.vd = new VectorData();
		this.fr = new FileReader();
		this.ui = new UserInputScanner();
	}
	
	public void setUserInputType() throws IOException, Exception {
		N = ui.getUserN(fnName);
		if (N <= MAX_SMALL_N) {
			dataGeneration = DataGenerationTypes.USER_INPUT;
			return;
		}
		dataGeneration = ui.scanDataGenerationType();
		switch(dataGeneration) {
		case DataGenerationTypes.READ_FILE -> { String filename = ui.scanFilename(); fileContents = fr.read(filename);}
		case DataGenerationTypes.GENERATE_RANDOM -> { return; }
		case DataGenerationTypes.FILL_WITH_NUMBER -> { fillNumber = ui.scanFillNumber(); }
		default -> { throw new Exception("Невірний вибір при виборі методу заповнення матриці!"); }	
		}
	}
	
	public Matrix createMatrix(String name) throws Exception {
		switch(dataGeneration) {
		case DataGenerationTypes.USER_INPUT -> { return new Matrix(ui.getMatrixFromInput(fnName, name, N)); }
		case DataGenerationTypes.READ_FILE -> { return Matrix.fromString(md.getFromFileInput(fileContents, N, name), N); }
		case DataGenerationTypes.GENERATE_RANDOM -> { return new Matrix(md.generateRandom(N)); }
		case DataGenerationTypes.FILL_WITH_NUMBER -> { return new Matrix(md.fillWithNumber(N, fillNumber)); }
		default -> { throw new Exception("Матриця " + name + " не може бути згенерована!"); }
		}
	}
	
	public Vector createVector(String name) throws Exception {
		switch(dataGeneration) {
		case DataGenerationTypes.USER_INPUT -> { return new Vector(ui.getVectorFromInput(fnName, name, N)); }
		case DataGenerationTypes.READ_FILE -> { return Vector.fromString(vd.getFromFileInput(fileContents, N, name), N); }
		case DataGenerationTypes.GENERATE_RANDOM -> { return new Vector(vd.generateRandom(N)); }
		case DataGenerationTypes.FILL_WITH_NUMBER -> { return new Vector(vd.fillWithNumber(N, fillNumber)); }
		default -> { throw new Exception("Вектор " + name + " не може бути згенерованим!"); }
		}
	}
	
	public int createNumber(String name) throws Exception {
		switch(dataGeneration) {
		case DataGenerationTypes.USER_INPUT -> { return 0; }
		case DataGenerationTypes.READ_FILE -> { return 0; }
		case DataGenerationTypes.GENERATE_RANDOM -> { return 0; }
		case DataGenerationTypes.FILL_WITH_NUMBER -> { return 0; }
		default -> { throw new Exception("Число " + name + " не може бути згенероване!"); }
		}
	}
}

// допоміжний клас для створення матриць
class MatrixData {
	
	public int[][] generateRandom(int N) {
		int[][] MA = new int[N][N];
		for (int i = 0; i < N; i ++) {
			for (int j = 0; j < N; j++) {
				MA[i][j] = ThreadLocalRandom.current().nextInt();
			}
		}
		return MA;
	}
	
	public int[][] fillWithNumber(int N, int num) {
		final int[][] MA = new int[N][N];
		Arrays.stream(MA).forEach(row -> Arrays.fill(row, num));
		return MA;
	}
	
	public String getFromFileInput(String contents, int N, String matrixName) {
		String[] lines = contents.split("\n");
		int index = Arrays.asList(lines).indexOf(matrixName) + 1;
		String matrix = String.join("\n", Arrays.copyOfRange(lines, index, index + N));
		return matrix;
	}
}

// допоміжний клас для створення векторів
class VectorData {
	
	public int[] generateRandom(int N) {
		int[] A = new int[N];
		for (int i = 0; i < N; i++) {
			A[i] = ThreadLocalRandom.current().nextInt();
		}
		return A;
	}
	
	public int[] fillWithNumber(int N, int num) {
		int[] A = new int[N];
		Arrays.fill(A, num);
		return A;
	}
	
	public String getFromFileInput(String contents, int N, String vectorName) {
		String[] lines = contents.split("\n");
		int index = Arrays.asList(lines).indexOf(vectorName) + 1;
		String vector = String.join("\n", Arrays.copyOfRange(lines, index, index + 1));
		return vector;
	}
}

// допоміжні класи для зчитування даних користувача
class FileReader {
	
	public String read(String filepath) throws IOException {
		DataInputStream stream = null;
		try {
			stream = new DataInputStream(new FileInputStream(filepath));
			return new String(stream.readAllBytes(), StandardCharsets.UTF_8);
		} finally {
			if (stream != null) {
				stream.close();
			}
		}
	}
}

class DataGenerationTypes {
	static final int USER_INPUT = 0, READ_FILE = 1, GENERATE_RANDOM = 2, FILL_WITH_NUMBER = 3;
}

class UserInputScanner {
	
	private Scanner scanner;
	
	UserInputScanner() {
		scanner = new Scanner(System.in);
	}
	
	int getUserN(String fnName) {
		System.out.print("Введіть розмірність векторів та матриць для функції " + fnName + ": ");
		return scanner.nextInt();
	}
	
	int[][] getMatrixFromInput(String fnName, String name, int N) {
		System.out.println("Функція " + fnName + ". Ввід матриці " + name + ": ");
		int[][] MA = new int[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				System.out.print("Введіть елемент " + name.toLowerCase() + (i + 1) + (j + 1) + ": ");
				MA[i][j] = scanNumber();
			}		
		}
		return MA;
	}
	
	int[] getVectorFromInput(String fnName, String name, int N) {
		System.out.println("Функція " + fnName + ". Ввід вектора " + name + ": ");
		int[] A = new int[N];
		for (int i = 0; i < N; i++) {
			System.out.print("Введіть елемент " + name.toLowerCase() + (i + 1) + ": ");
			A[i] = scanNumber();
		}
		return A;
	}
	
	int scanDataGenerationType() {
		System.out.print("Виберіть спосіб вводу даних:\n1 - зчитати з файлу\n2 - згенерувати випадково\n3 - заповнити числом\n> ");
		return scanNumber();
	}
	
	String scanFilename() {
		System.out.print("Введіть назву файлу: ");
		return scanLine();
	}
	
	int scanFillNumber() {
		System.out.print("Введіть число для заповнення структур даних: ");
		return scanNumber();
	}
	
	private int scanNumber() {
		int num = scanner.nextInt();
		scanner.nextLine();
		return num;
	}
	
	private String scanLine() {
		return scanner.nextLine();
	}
}