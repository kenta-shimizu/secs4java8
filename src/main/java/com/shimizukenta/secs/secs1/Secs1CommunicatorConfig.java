package com.shimizukenta.secs.secs1;

import com.shimizukenta.secs.AbstractBooleanProperty;
import com.shimizukenta.secs.AbstractNumberProperty;
import com.shimizukenta.secs.AbstractSecsCommunicatorConfig;
import com.shimizukenta.secs.ReadOnlyBooleanProperty;
import com.shimizukenta.secs.ReadOnlyNumberProperty;
import com.shimizukenta.secs.SimpleBooleanProperty;
import com.shimizukenta.secs.SimpleNumberProperty;

/**
 * This class is SECS-I config<br />
 * To set Master-Mode, {@link #isMaster(boolean)}<br />
 * To set Retry, {@link #retry(int)}
 * 
 * @author kenta-shimizu
 *
 */
public class Secs1CommunicatorConfig extends AbstractSecsCommunicatorConfig {
	
	private static final long serialVersionUID = 4897063498275749609L;
	
	private final AbstractBooleanProperty isMaster = new SimpleBooleanProperty(true);
	private final AbstractNumberProperty retry = new SimpleNumberProperty(3);
	
	public Secs1CommunicatorConfig() {
		super();
	}
	
	/**
	 * Set true if Master Mode.
	 * 
	 * @param isMasterMode
	 */
	public void isMaster(boolean isMaster) {
		this.isMaster.set(isMaster);
	}
	
	/**
	 * Master Mode getter.
	 * 
	 * @return true if Master Mode
	 */
	public ReadOnlyBooleanProperty isMaster() {
		return isMaster;
	}
	
	/**
	 * Rety setter.
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
	 * Retry getter.
	 * 
	 * @return retry-count
	 */
	public ReadOnlyNumberProperty retry() {
		return retry;
	}
	
}
