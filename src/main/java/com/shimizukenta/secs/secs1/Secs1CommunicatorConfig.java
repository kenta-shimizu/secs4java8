package com.shimizukenta.secs.secs1;

import com.shimizukenta.secs.AbstractSecsCommunicatorConfig;
import com.shimizukenta.secs.BooleanProperty;
import com.shimizukenta.secs.IntegerProperty;

public class Secs1CommunicatorConfig extends AbstractSecsCommunicatorConfig {
	
	private static final long serialVersionUID = -4562702342609110048L;
	
	private final BooleanProperty isMaster = new BooleanProperty(true);
	private final IntegerProperty retry = new IntegerProperty(3);
	
	public Secs1CommunicatorConfig() {
		super();
	}
	
	/**
	 * 
	 * @param isMasterMode
	 */
	public void isMaster(boolean isMaster) {
		this.isMaster.set(isMaster);
	}
	
	/**
	 * 
	 * @return isMasterMode
	 */
	public BooleanProperty isMaster() {
		return isMaster;
	}
	
	/**
	 * 
	 * @param retry-count-value is >= 0
	 */
	public void retry(int retryCount) {
		if ( retryCount < 0 ) {
			throw new IllegalArgumentException("retry is >= 0");
		}
		this.retry.set(retryCount);
	}
	
	/**
	 * 
	 * @return retry-count
	 */
	public IntegerProperty retry() {
		return retry;
	}
	
}
