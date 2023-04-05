package lab1.Data;

import java.io.IOException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import lab1.Data.matrix.Matrix;
import lab1.Data.matrix.MatrixData;
import lab1.Data.scalar.ScalarNumberData;
import lab1.Data.vector.Vector;
import lab1.Data.vector.VectorData;

class DataGenerationTypes {
	static final int USER_INPUT = 0, READ_FILE = 1, GENERATE_RANDOM = 2, FILL_WITH_NUMBER = 3;
}

public class Data {

	final int MIN_N = 4;
	private final static int MAX_SMALL_N = 8;

	private UserInputScanner ui;
	private MatrixData md;
	private VectorData vd;
	private ScalarNumberData sd;
	private FileReader fr;

	private int N;
	private String fileContents;
	private int fillNumber;
	private int dataGeneration;

	private ParallelData parallelData;
	private CommonData commonData;

	public Data() {
		this.ui = new UserInputScanner();
		this.md = new MatrixData(ui);
		this.vd = new VectorData(ui);
		this.sd = new ScalarNumberData(ui);
		this.fr = new FileReader();

		this.parallelData = new ParallelData();
		this.commonData = new CommonData();
	}

	public int setUserN() throws IOException, Exception {
		N = ui.getUserN();
		if (N <= MIN_N) {
			throw new Exception("Неможливо продовжити виконання програми - невірне значення N!");
		}
		setUserInputType();
		return N;
	}

	public void setUserInputType() throws IOException, Exception {
		if (N <= MAX_SMALL_N) {
			dataGeneration = DataGenerationTypes.USER_INPUT;
			return;
		}
		dataGeneration = ui.scanDataGenerationType();
		switch (dataGeneration) {
		case DataGenerationTypes.READ_FILE -> {
			String filename = ui.scanFilename();
			fileContents = fr.read(filename);
		}
		case DataGenerationTypes.GENERATE_RANDOM -> {
			return;
		}
		case DataGenerationTypes.FILL_WITH_NUMBER -> {
			fillNumber = ui.scanFillNumber();
		}
		default -> {
			throw new Exception("Невірний вибір при виборі методу заповнення матриці!");
		}
		}
	}

	public Matrix createMatrix(String name) throws Exception {
		switch (dataGeneration) {
		case DataGenerationTypes.USER_INPUT -> {
			return new Matrix(md.getMatrixFromInput(name, N));
		}
		case DataGenerationTypes.READ_FILE -> {
			return Matrix.fromString(md.getFromFileInput(fileContents, N, name), N);
		}
		case DataGenerationTypes.GENERATE_RANDOM -> {
			return new Matrix(md.generateRandom(N));
		}
		case DataGenerationTypes.FILL_WITH_NUMBER -> {
			return new Matrix(md.fillWithNumber(N, fillNumber));
		}
		default -> {
			throw new Exception("Матриця " + name + " не може бути згенерована!");
		}
		}
	}

	public Vector createVector(String name) throws Exception {
		switch (dataGeneration) {
		case DataGenerationTypes.USER_INPUT -> {
			return new Vector(vd.getVectorFromInput(name, N));
		}
		case DataGenerationTypes.READ_FILE -> {
			return Vector.fromString(vd.getFromFileInput(fileContents, N, name), N);
		}
		case DataGenerationTypes.GENERATE_RANDOM -> {
			return new Vector(vd.generateRandom(N));
		}
		case DataGenerationTypes.FILL_WITH_NUMBER -> {
			return new Vector(vd.fillWithNumber(N, fillNumber));
		}
		default -> {
			throw new Exception("Вектор " + name + " не може бути згенерованим!");
		}
		}
	}

	public int createNumber(String name) throws Exception {
		switch (dataGeneration) {
		case DataGenerationTypes.USER_INPUT -> {
			return sd.getNumberFromUnput(name);
		}
		case DataGenerationTypes.READ_FILE -> {
			return sd.getFromInputFile(fileContents, name);
		}
		case DataGenerationTypes.GENERATE_RANDOM -> {
			return sd.generateRandom();
		}
		case DataGenerationTypes.FILL_WITH_NUMBER -> {
			return fillNumber;
		}
		default -> {
			throw new Exception("Число " + name + " не може бути згенероване!");
		}
		}
	}

	// дані синхронізації і паралельного очікування

	public Semaphore getT1InputSemaphore() {
		return parallelData.getT1InputSemaphore();
	}

	public Semaphore getT3InputSemaphore() {
		return parallelData.getT3InputSemaphore();
	}

	public Semaphore getT4InputSemaphore() {
		return parallelData.getT4InputSemaphore();
	}

	public Object getInputSyncObj() {
		return parallelData.getInputSyncObj();
	}

	public Lock getQLock() {
		return parallelData.getQLock();
	}

	public Semaphore getT1FinishSemaphore() {
		return parallelData.getT1FinishSemaphore();
	}

	public Semaphore getT3FinishSemaphore() {
		return parallelData.getT3FinishSemaphore();
	}

	public Semaphore getT4FinishSemaphore() {
		return parallelData.getT4FinishSemaphore();
	}
	
	public int getRestThreadsAmount() {
		return parallelData.getRestThreadsAmount();
	}

	// спільні дані
	// змінні
	public int getValD() {
		return commonData.getValD();
	}

	public void setValD(int value) {
		commonData.setValD(value);
	}

	public int getValQ() {
		return commonData.getValQ();
	}

	public void setValQ(int value) {
		commonData.setValQ(value);
	}

	// матриці
	public Matrix getMatrixMR() {
		return commonData.getMatrixMR();
	}

	public void setMatrixMR(Matrix value) {
		commonData.setMatrixMR(value);
	}

	public Matrix getMatrixMB() {
		return commonData.getMatrixMB();
	}

	public void setMatrixMB(Matrix value) {
		commonData.setMatrixMB(value);
	}

	public Matrix getMatrixMC() {
		return commonData.getMatrixMC();
	}

	public void setMatrixMC(Matrix value) {
		commonData.setMatrixMC(value);
	}

	public Matrix getMatrixMM() {
		return commonData.getMatrixMM();
	}

	public void setMatrixMM(Matrix value) {
		commonData.setMatrixMM(value);
	}

	// ветори
	public Vector getVectorZ() {
		return commonData.getVectorZ();
	}

	public void setVectorZ(Vector value) {
		commonData.setVectorZ(value);
	}
}

// допоміжний клас для конструкцій синхронізації
class ParallelData {
	private Semaphore T1InputSemaphore;
	private Semaphore T3InputSemaphore;
	private Semaphore T4InputSemaphore;

	private Object inputSyncObj;

	private Lock qLock;

	private Semaphore MRInitSemaphore;

	private Semaphore T1FinishSemaphore;
	private Semaphore T3FinishSemaphore;
	private Semaphore T4FinishSemaphore;
	
	private int REST_AMOUNT = 3;

	ParallelData() {
		this.T1InputSemaphore = new Semaphore(0);
		this.T3InputSemaphore = new Semaphore(0);
		this.T4InputSemaphore = new Semaphore(0);

		this.inputSyncObj = new Object();

		this.qLock = new ReentrantLock();

		this.T1FinishSemaphore = new Semaphore(0);
		this.T3FinishSemaphore = new Semaphore(0);
		this.T4FinishSemaphore = new Semaphore(0);
	}

	Semaphore getT1InputSemaphore() {
		return T1InputSemaphore;
	}

	Semaphore getT3InputSemaphore() {
		return T3InputSemaphore;
	}

	Semaphore getT4InputSemaphore() {
		return T4InputSemaphore;
	}

	Object getInputSyncObj() {
		return inputSyncObj;
	}

	Lock getQLock() {
		return qLock;
	}

	Semaphore getT1FinishSemaphore() {
		return T1FinishSemaphore;
	}

	Semaphore getT3FinishSemaphore() {
		return T3FinishSemaphore;
	}

	Semaphore getT4FinishSemaphore() {
		return T4FinishSemaphore;
	}
	
	int getRestThreadsAmount() {
		return REST_AMOUNT;
	}
}

// допоміжний клад для спільних даних
class CommonData {

	private int d;
	private int q;

	private Matrix MR;
	private Matrix MB;
	private Matrix MC;
	private Matrix MM;

	private Vector Z;

	// змінні
	int getValD() {
		return d;
	}

	void setValD(int value) {
		d = value;
	}

	int getValQ() {
		return q;
	}

	void setValQ(int value) {
		q = value;
	}

	// матриці
	Matrix getMatrixMR() {
		return MR;
	}

	void setMatrixMR(Matrix value) {
		MR = value;
	}

	Matrix getMatrixMB() {
		return MB;
	}

	void setMatrixMB(Matrix value) {
		MB = value;
	}

	Matrix getMatrixMC() {
		return MC;
	}

	void setMatrixMC(Matrix value) {
		MC = value;
	}

	Matrix getMatrixMM() {
		return MM;
	}

	void setMatrixMM(Matrix value) {
		MM = value;
	}

	// ветори
	Vector getVectorZ() {
		return Z;
	}

	void setVectorZ(Vector value) {
		Z = value;
	}
}