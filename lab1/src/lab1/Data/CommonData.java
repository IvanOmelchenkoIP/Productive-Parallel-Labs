package lab1.Data;

import lab1.Data.matrix.Matrix;
import lab1.Data.vector.Vector;

public class CommonData {

	int d;
	int q;
	
	Matrix MR;
	Matrix MB;
	Matrix MC;
	Matrix MM;
	
	Vector Z;
	
	public int retrieveD() {
		return d;
	}
	
	public int retrieveQ() {
		return q;
	}
	
	public void setD(int value) {
		d = value;
	}
	
	public void setQ(int value) {
		q = value;
	}
	
	public Matrix retrieveMR() {
		return MR;
	}
	
	public Matrix retrieveMB() {
		return MB;
	}
	
	public Matrix retrieveMC() {
		return MC;
	}
	
	public Matrix retrieveMM() {
		return MM;
	}
	
	public void setMB(Matrix value) {
		MB = value;
	}
	
	public void setMC(Matrix value) {
		MC = value;
	}
	
	public void setMM(Matrix value) {
		MM = value;
	}
	
	
	public Vector retrieveZ() {
		return Z;
	}
	
	public void setZ(Vector value) {
		Z = value;
	}
}
