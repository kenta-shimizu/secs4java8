package secs.secs1;

import secs.SecsCommunicatorConfig;

public class Secs1CommunicatorConfig extends SecsCommunicatorConfig {
	
	private boolean isMaster;
	private int retry;
	
	public Secs1CommunicatorConfig() {
		super();
		
		isMaster = true;
		retry = 3;
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
	
}
