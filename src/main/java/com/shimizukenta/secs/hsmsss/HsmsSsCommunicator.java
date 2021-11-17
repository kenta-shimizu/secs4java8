package com.shimizukenta.secs.hsmsss;

import java.io.IOException;

import com.shimizukenta.secs.SecsCommunicator;
import com.shimizukenta.secs.SecsException;

/**
 * This interface is implementation of HSMS-SS (SEMI-E37.1).
 * 
 * <p>
 * To create newInstance, {@link #newInstance(HsmsSsCommunicatorConfig)}<br />
 * To create newInstance and open, {@link #open(HsmsSsCommunicatorConfig)}<br />
 * To linktest, {@link #linktest()}<br />
 * </p>
 * 
 * @author kenta-shimizu
 *
 */
public interface HsmsSsCommunicator extends SecsCommunicator {
	
	/**
	 * create new HSMS-SS-Communicator instance.
	 * 
	 * @param config
	 * @return new HSMS-SS-Communicator instance
	 */
	public static HsmsSsCommunicator newInstance(HsmsSsCommunicatorConfig config) {
		
		switch ( config.connectionMode().get() ) {
		case PASSIVE: {
			
			if ( config.rebindIfPassive().geZero() ) {
				
				return new AbstractHsmsSsRebindPassiveCommunicator(config) {};
				
			} else {
				
				return new AbstractHsmsSsPassiveCommunicator(config) {};
			}
			/* break; */
		}
		case ACTIVE: {
			
			return new AbstractHsmsSsActiveCommunicator(config) {};
			/* break; */
		}
		default: {
			
			throw new IllegalStateException("undefined connecton-mode: " + config.connectionMode());
		}
		}
	}
	
	/**
	 * Create new HSMS-SS-Communicator instance and {@link #open()}.
	 * 
	 * @param config
	 * @return new HSMS-SS-Communicator instance
	 * @throws IOException
	 */
	public static HsmsSsCommunicator open(HsmsSsCommunicatorConfig config) throws IOException {
		
		final HsmsSsCommunicator inst = newInstance(config);
		
		try {
			inst.open();
		}
		catch ( IOException e ) {
			
			try {
				inst.close();
			}
			catch ( IOException giveup ) {
			}
			
			throw e;
		}
		
		return inst;
	}
	
	/**
	 * HSMS-SS linktest.
	 * 
	 * <p>
	 * Blocking-method.
	 * </p>
	 * 
	 * @return {@code true} if success
	 * @throws InterruptedException
	 * @throws SecsException
	 */
	public boolean linktest() throws InterruptedException;
	
}
