package lab1.Data;

import java.io.IOException;

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
	
	public Data() {
		this.ui = new UserInputScanner();
		this.md = new MatrixData(ui);
		this.vd = new VectorData(ui);
		this.sd = new ScalarNumberData(ui);
		this.fr = new FileReader();
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
		switch(dataGeneration) {
		case DataGenerationTypes.READ_FILE -> { String filename = ui.scanFilename(); fileContents = fr.read(filename);}
		case DataGenerationTypes.GENERATE_RANDOM -> { return; }
		case DataGenerationTypes.FILL_WITH_NUMBER -> { fillNumber = ui.scanFillNumber(); }
		default -> { throw new Exception("Невірний вибір при виборі методу заповнення матриці!"); }	
		}
	}
	
	public Matrix createMatrix(String name) throws Exception {
		switch(dataGeneration) {
		case DataGenerationTypes.USER_INPUT -> { return new Matrix(md.getMatrixFromInput(name, N)); }
		case DataGenerationTypes.READ_FILE -> { return Matrix.fromString(md.getFromFileInput(fileContents, N, name), N); }
		case DataGenerationTypes.GENERATE_RANDOM -> { return new Matrix(md.generateRandom(N)); }
		case DataGenerationTypes.FILL_WITH_NUMBER -> { return new Matrix(md.fillWithNumber(N, fillNumber)); }
		default -> { throw new Exception("Матриця " + name + " не може бути згенерована!"); }
		}
	}
	
	public Vector createVector(String name) throws Exception {
		switch(dataGeneration) {
		case DataGenerationTypes.USER_INPUT -> { return new Vector(vd.getVectorFromInput(name, N)); }
		case DataGenerationTypes.READ_FILE -> { return Vector.fromString(vd.getFromFileInput(fileContents, N, name), N); }
		case DataGenerationTypes.GENERATE_RANDOM -> { return new Vector(vd.generateRandom(N)); }
		case DataGenerationTypes.FILL_WITH_NUMBER -> { return new Vector(vd.fillWithNumber(N, fillNumber)); }
		default -> { throw new Exception("Вектор " + name + " не може бути згенерованим!"); }
		}
	}
	
	public int createNumber(String name) throws Exception {
		switch(dataGeneration) {
		case DataGenerationTypes.USER_INPUT -> { return sd.getNumberFromUnput(name); }
		case DataGenerationTypes.READ_FILE -> { return sd.getFromInputFile(fileContents, name); }
		case DataGenerationTypes.GENERATE_RANDOM -> { return sd.generateRandom(); }
		case DataGenerationTypes.FILL_WITH_NUMBER -> { return fillNumber; }
		default -> { throw new Exception("Число " + name + " не може бути згенероване!"); }
		}
	}
}