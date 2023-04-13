package lab1.Data;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class DataGenerationTypes {
	static final int USER_INPUT = 0, READ_FILE = 1, GENERATE_RANDOM = 2, FILL_WITH_NUMBER = 3;
}

public class Data {

	private final int MIN_N = 4;
	private final int MAX_SMALL_N = 8;

	private int N;
	private int restThreads;
	private int dataGeneration;
	private int fillNumber;
	private String fileContents;

	private UserInputScanner ui;
	private FileReader fr;

	private NumberCreator nc;
	private MatrixCreator mc;
	private VectorCreator vc;

	private AtomicInteger q;
	private AtomicInteger d;
	private Vector Z;
	private Matrix MB;
	private Matrix MC;
	private Matrix MM;
	private Matrix MR;

	private Object inputSyncObj;
	private Semaphore T1InputSemaphore;
	private Semaphore T3InputSemaphore;
	private Semaphore T4InputSemaphore;

	private CyclicBarrier qBarrier;

	private Lock MCLock;
	private Semaphore MBSemaphore;

	private Semaphore T1FinishSemaphore;
	private Semaphore T3FinishSemaphore;
	private Semaphore T4FinishSemaphore;

	public Data() {
		ui = new UserInputScanner();
		fr = new FileReader();

		nc = new NumberCreator(ui);
		mc = new MatrixCreator(ui);
		vc = new VectorCreator(ui);

		q = new AtomicInteger();
		d = new AtomicInteger();
		
		inputSyncObj = new Object();
		T1InputSemaphore = new Semaphore(0);
		T3InputSemaphore = new Semaphore(0);
		T4InputSemaphore = new Semaphore(0);
		qBarrier = new CyclicBarrier(4);

		MCLock = new ReentrantLock();
		MBSemaphore = new Semaphore(1);
		T1FinishSemaphore = new Semaphore(0);
		T3FinishSemaphore = new Semaphore(0);
		T4FinishSemaphore = new Semaphore(0);
	}

	public int setUserN() throws IOException, Exception {
		System.out.print("Введіть розмірність векторів та матриць N (N > 4): ");
		N = ui.readNumber();
		if (N <= MIN_N) {
			throw new Exception("Неможливо продовжити виконання програми - невірне значення N!");
		}
		setUserInputType();
		initData();
		return N;
	}

	public void setUserInputType() throws IOException, Exception {
		if (N <= MAX_SMALL_N) {
			dataGeneration = DataGenerationTypes.USER_INPUT;
		} else {
			System.out.print(
					"Виберіть спосіб вводу даних:\n1 - зчитати з файлу\n2 - згенерувати випадково\n3 - заповнити числом\n> ");
			dataGeneration = ui.readNumber();
			switch (dataGeneration) {
			case DataGenerationTypes.READ_FILE -> {
				System.out.print("Введіть назву файлу: ");
				String filename = ui.readLine();
				fileContents = fr.read(filename);
			}
			case DataGenerationTypes.GENERATE_RANDOM -> {
				return;
			}
			case DataGenerationTypes.FILL_WITH_NUMBER -> {
				System.out.print("Введіть число для заповнення структур даних: ");
				fillNumber = ui.readNumber();
			}
			default -> {
				throw new Exception("Невірний вибір при виборі методу заповнення матриці!");
			}
			}
		}

	}

	public int createNumber(String name) throws Exception {
		return nc.createNumber(name);
	}

	public Matrix createMatrix(String name) throws Exception {
		return mc.createMatrix(name);
	}

	public Vector createVector(String name) throws Exception {
		return vc.createVector(name);
	}

	private void initData() {
		q.set((int) Double.POSITIVE_INFINITY);
		MR = Matrix.cleanMarix(N);

		nc.setParams(fileContents, dataGeneration, fillNumber);
		mc.setParams(fileContents, N, dataGeneration, fillNumber);
		vc.setParams(fileContents, N, dataGeneration, fillNumber);

		restThreads = N - 1;
	}

	public AtomicInteger getQ() {
		return q;
	}

	public void setQ(int q) {
		this.q.set(q);
	}

	public AtomicInteger getD() {
		return d;
	}

	public void setD(int d) {
		this.d.set(d);
	}

	public Vector getZ() {
		return Z;
	}

	public void setZ(Vector Z) {
		this.Z = Z;
	}

	public Matrix getMB() {
		return MB;
	}

	public void setMB(Matrix MB) {
		this.MB = MB;
	}

	public Matrix getMC() {
		return MC;
	}

	public void setMC(Matrix MC) {
		this.MC = MC;
	}

	public Matrix getMM() {
		return MM;
	}

	public void setMM(Matrix MM) {
		this.MM = MM;
	}

	public Matrix getMR() {
		return MR;
	}

	public void setMR(Matrix MR) {
		this.MR = MR;
	}

	public Semaphore getT1InputSemaphore() {
		return T1InputSemaphore;
	}

	public Semaphore getT3InputSemaphore() {
		return T3InputSemaphore;
	}

	public Semaphore getT4InputSemaphore() {
		return T4InputSemaphore;
	}

	public Object getInputSyncObj() {
		return inputSyncObj;
	}

	public Lock getMCLock() {
		return MCLock;
	}

	public Semaphore getMBSemaphore() {
		return MBSemaphore;
	}

	public CyclicBarrier getQBarrier() {
		return qBarrier;
	}

	public Semaphore getT1FinishSemaphore() {
		return T1FinishSemaphore;
	}

	public Semaphore getT3FinishSemaphore() {
		return T3FinishSemaphore;
	}

	public Semaphore getT4FinishSemaphore() {
		return T4FinishSemaphore;
	}

	public int getRestThreads() {
		return restThreads;
	}
}

class UserInputScanner {

	private Scanner scanner;

	UserInputScanner() {
		scanner = new Scanner(System.in);
	}

	String readLine() {
		return scanner.nextLine();
	}

	int readNumber() {
		int number = scanner.nextInt();
		readLine();
		return number;
	}
}

class FileReader {

	String read(String filename) throws IOException {

		DataInputStream stream = null;
		try {
			stream = new DataInputStream(new FileInputStream(filename));
			return new String(stream.readAllBytes(), StandardCharsets.UTF_8);
		} finally {
			if (stream != null) {
				stream.close();
			}
		}
	}
}

class NumberCreator {

	private UserInputScanner ui;

	private String fileContents;
	private int dataGeneration;
	private int fillNumber;

	NumberCreator(UserInputScanner ui) {
		this.ui = ui;
	}

	void setParams(String fileContents, int dataGeneration, int fillNumber) {
		this.fileContents = fileContents;
		this.dataGeneration = dataGeneration;
		this.fillNumber = fillNumber;
	}

	int createNumber(String name) throws Exception {
		switch (dataGeneration) {
		case DataGenerationTypes.USER_INPUT -> {
			return getNumberFromUnput(name);
		}
		case DataGenerationTypes.READ_FILE -> {
			return getFromInputFile(name);
		}
		case DataGenerationTypes.GENERATE_RANDOM -> {
			return generateRandom();
		}
		case DataGenerationTypes.FILL_WITH_NUMBER -> {
			return fillNumber;
		}
		default -> {
			throw new Exception("Число " + name + " не може бути згенероване!");
		}
		}
	}
	
	int getNumberFromUnput(String name) {
		System.out.print("Введіть число " + name + ": ");
		int a = ui.readNumber();
		return a;
	}
	
	int generateRandom() {
		return ThreadLocalRandom.current().nextInt();
	}
	
	int getFromInputFile(String name) {
		String[] lines = fileContents.split("\n");
		int index = Arrays.asList(lines).indexOf(name) + 1;
		return Integer.parseInt(lines[index]);
	}
}

class MatrixCreator {

	private UserInputScanner ui;

	private String fileContents;
	private int N;
	private int dataGeneration;
	private int fillNumber;

	MatrixCreator(UserInputScanner ui) {
		this.ui = ui;
	}

	void setParams(String fileContents, int N, int dataGeneration, int fillNumber) {
		this.fileContents = fileContents;
		this.N = N;
		this.dataGeneration = dataGeneration;
		this.fillNumber = fillNumber;
	}

	Matrix createMatrix(String name) throws Exception {
		switch (dataGeneration) {
		case DataGenerationTypes.USER_INPUT -> {
			return new Matrix(getMatrixFromInput(name));
		}
		case DataGenerationTypes.READ_FILE -> {
			return Matrix.fromString(getFromFileInput(name), N);
		}
		case DataGenerationTypes.GENERATE_RANDOM -> {
			return new Matrix(generateRandom());
		}
		case DataGenerationTypes.FILL_WITH_NUMBER -> {
			return new Matrix(fillWithNumber());
		}
		default -> {
			throw new Exception("Матриця " + name + " не може бути згенерована!");
		}
		}
	}

	int[][] getMatrixFromInput(String name) {
		System.out.println("Ввід матриці " + name + ": ");
		int[][] MA = new int[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				System.out.print("Введіть елемент " + name.toLowerCase() + (i + 1) + (j + 1) + ": ");
				MA[i][j] = ui.readNumber();
			}
		}
		return MA;
	}

	int[][] generateRandom() {
		int[][] MA = new int[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				MA[i][j] = ThreadLocalRandom.current().nextInt();
			}
		}
		return MA;
	}

	int[][] fillWithNumber() {
		final int[][] MA = new int[N][N];
		Arrays.stream(MA).forEach(row -> Arrays.fill(row, fillNumber));
		return MA;
	}

	String getFromFileInput(String name) {
		String[] lines = fileContents.split("\n");
		int index = Arrays.asList(lines).indexOf(name) + 1;
		String matrix = String.join("\n", Arrays.copyOfRange(lines, index, index + N));
		return matrix;
	}
}

class VectorCreator {

	private UserInputScanner ui;

	private String fileContents;
	private int N;
	private int dataGeneration;
	private int fillNumber;

	VectorCreator(UserInputScanner ui) {
		this.ui = ui;
	}

	void setParams(String fileContents, int N, int dataGeneration, int fillNumber) {
		this.fileContents = fileContents;
		this.N = N;
		this.dataGeneration = dataGeneration;
		this.fillNumber = fillNumber;
	}

	Vector createVector(String name) throws Exception {
		switch (dataGeneration) {
		case DataGenerationTypes.USER_INPUT -> {
			return new Vector(getVectorFromInput(name));
		}
		case DataGenerationTypes.READ_FILE -> {
			return Vector.fromString(getFromFileInput(name), N);
		}
		case DataGenerationTypes.GENERATE_RANDOM -> {
			return new Vector(generateRandom());
		}
		case DataGenerationTypes.FILL_WITH_NUMBER -> {
			return new Vector(fillWithNumber());
		}
		default -> {
			throw new Exception("Вектор " + name + " не може бути згенерованим!");
		}
		}
	}
	
	int[] getVectorFromInput(String name) {
		System.out.println("Ввід вектора " + name + ": ");
		int[] A = new int[N];
		for (int i = 0; i < N; i++) {
			System.out.print("Введіть елемент " + name.toLowerCase() + (i + 1) + ": ");
			A[i] = ui.readNumber();
		}
		return A;
	}
	
	int[] generateRandom() {
		int[] A = new int[N];
		for (int i = 0; i < N; i++) {
			A[i] = ThreadLocalRandom.current().nextInt();
		}
		return A;
	}
	
	int[] fillWithNumber() {
		int[] A = new int[N];
		Arrays.fill(A, fillNumber);
		return A;
	}
	
	String getFromFileInput(String name) {
		String[] lines = fileContents.split("\n");
		int index = Arrays.asList(lines).indexOf(name) + 1;
		String vector = String.join("\n", Arrays.copyOfRange(lines, index, index + 1));
		return vector;
	}
}
