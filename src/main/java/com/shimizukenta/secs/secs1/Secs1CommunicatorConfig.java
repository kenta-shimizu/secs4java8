package com.shimizukenta.secs.secs1;

import com.shimizukenta.secs.AbstractProperty;
import com.shimizukenta.secs.AbstractSecsCommunicatorConfig;
import com.shimizukenta.secs.Property;

public class Secs1CommunicatorConfig extends AbstractSecsCommunicatorConfig {
	
	private static final long serialVersionUID = -4562702342609110048L;
	
	private class BooleanProperty extends AbstractProperty<Boolean> {

		public BooleanProperty(boolean initial) {
			super(Boolean.valueOf(initial));
		}
	}
	
	private class IntegerProperty extends AbstractProperty<Integer> {

		public IntegerProperty(int initial) {
			super(Integer.valueOf(initial));
		}
	}
	
	private final Property<Boolean> isMaster = new BooleanProperty(true);
	private final Property<Integer> retry = new IntegerProperty(3);
	
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
	public Property<Boolean> isMaster() {
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
	public Property<Integer> retry() {
		return retry;
	}
	
}
