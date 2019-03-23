package secs.secs1;

import secs.SecsCommunicatorConfig;

public class Secs1CommunicatorConfig extends SecsCommunicatorConfig {
	
	public static int ZERO = 0;
	public static int ONE  = 1;
	
	private boolean isMaster;
	private int retry;
	private int firstBlockNumber;
	
	public Secs1CommunicatorConfig() {
		super();
		
		isMaster = true;
		retry = 3;
		firstBlockNumber = ONE;
	}
	
	/**
	 * 
	 * @param isMasterMode
	 */
	public void isMaster(boolean isMaster) {
		synchronized ( this ) {
			this.isMaster = isMaster;
		}
	}
	
	/**
	 * 
	 * @return isMasterMode
	 */
	public boolean isMaster() {
		synchronized ( this ) {
			return isMaster;
		}
	}
	
	/**
	 * 
	 * @param retry-count-value is >= 0
	 */
	public void retry(int retryCount) {
		
		if ( retryCount < 0 ) {
			throw new IllegalArgumentException("retry is >= 0");
		}
		
		synchronized ( this ) {
			this.retry = retryCount;
		}
	}
	
	/**
	 * 
	 * @return retry-count
	 */
	public int retry() {
		synchronized ( this ) {
			return retry;
		}
	}
	
	/**
	 * 
	 * @param first-block-number value is ONE or ZERO
	 */
	public void firstBlockNumber(int n) {
		
		if ( n != ZERO && n != ONE ) {
			throw new IllegalArgumentException("first-block-number is ONE or ZERO");
		}
		
		synchronized ( this ) {
			this.firstBlockNumber = n;
		}
	}
	
	/**
	 * 
	 * @return first-block-number
	 */
	public int firstBlockNumber() {
		synchronized ( this ) {
			return firstBlockNumber;
		}
	}
	
}
