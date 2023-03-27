package lab1.Data.vector;

public class Vector {

	private int N;
	private int[] data;
	
	public Vector(int[] data) {
		this.data = data;
		this.N = data.length;
	}
	
	public static Vector fromString(String vector, int N) {
		int[] data = new int[N];
		String[] elements = vector.trim().split("");
		for (int i = 0; i < N; i++) {
			data[i] = Integer.parseInt(elements[i]);
		}
		return new Vector(data);
	}
	
	public int[] getData() {
		return data;
	}
	
	public Vector getPartialVector(int start, int end) {
		int[] dataB = new int[end - start];
		int ind = 0;
		for (int i = start; i <= end; i++) {
			dataB[ind] = data[i];
			ind += 1;
		}
		return new Vector(data);
	}
	
	public int min() {
		int minimal = data[0];
		for (int i = 1; i < N; i++) {
			if (minimal > data[i]) {
				minimal = data[i];
			}
		}
		return minimal;
	}
}
