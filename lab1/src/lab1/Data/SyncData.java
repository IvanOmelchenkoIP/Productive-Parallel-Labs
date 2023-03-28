package lab1.Data;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SyncData {

	private final int WAITING_FOR_INPUT = 3;
	
	private Semaphore t1Input;
	private Semaphore t3Input;
	private Semaphore t4Input;
	
	private Object inputSync;
	
	private Lock qLock;
	
	private Semaphore MRinit;
	
	private Semaphore t1Finish;
	private Semaphore t3Finish;
	private Semaphore t4Finish;
	
	public SyncData() {
		t1Input = new Semaphore(0);		
		t3Input = new Semaphore(0);		
		t4Input = new Semaphore(0);
		
		qLock = new ReentrantLock();
		
		MRinit = new Semaphore(1);
		
		t1Finish = new Semaphore(0);
		t3Finish = new Semaphore(0);
		t4Finish = new Semaphore(0);
	}
	
	public int getWaitingForInput() {
		return WAITING_FOR_INPUT;
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
	
	
	public Lock getQLock() {
		return qLock;
	}
	
	public Semaphore getMRinit() {
		return MRinit;
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
