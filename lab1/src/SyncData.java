import java.util.concurrent.Semaphore;

public class SyncData {

	private Semaphore t1Input;
	private Semaphore t3Input;
	private Semaphore t4Input;
	
	private Object inputSync;
	
	private Semaphore t1Finish;
	private Semaphore t3Finish;
	private Semaphore t4Finish;
	
	public SyncData() {
		t1Input = new Semaphore(4);
		t1Input.drainPermits();
		
		t3Input = new Semaphore(4);
		t3Input.drainPermits();
		
		t4Input = new Semaphore(4);
		t4Input.drainPermits();
		
		t1Finish = new Semaphore(0);
		t3Finish = new Semaphore(0);
		t4Finish = new Semaphore(0);
	}
	
	public Semaphore getT1Input() {
		return t1Input;
	}
	
	public Semaphore getT3Input() {
		return t3Input;
	}
	
	public Semaphore getT4Input() {
		return t4Input;
	}
	
	public Object getInputSync() {
		return inputSync;
	}
	
	public Semaphore getT1Finish() {
		return t1Finish;
	}
	
	public Semaphore getT3Finish() {
		return t3Finish;
	}
	
	public Semaphore getT4Finish() {
		return t4Finish;
	}
}
