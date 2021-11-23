package com.shimizukenta.secs.hsmsss;

import java.io.IOException;

import com.shimizukenta.secs.hsms.HsmsCommunicator;
import com.shimizukenta.secs.hsms.HsmsConnectionMode;

/**
 * This interface is implementation of HSMS-SS (SEMI-E37.1).
 * 
 * <p>
 * To create newInstance, {@link #newInstance(HsmsSsCommunicatorConfig)}<br />
 * To create newInstance and open, {@link #open(HsmsSsCommunicatorConfig)}<br />
 * </p>
 * 
 * @author kenta-shimizu
 *
 */
public interface HsmsSsCommunicator extends HsmsCommunicator {
	
	/**
	 * create new HSMS-SS-Communicator instance.
	 * 
	 * @param config
	 * @return new HSMS-SS-Communicator instance
	 */
	public static HsmsSsCommunicator newInstance(HsmsSsCommunicatorConfig config) {
		
		final HsmsConnectionMode mode = config.connectionMode().get();
		
		switch ( mode ) {
		case PASSIVE: {
			return new AbstractHsmsSsPassiveCommunicator(config) {};
			/* break; */
		}
		case ACTIVE: {
			return new AbstractHsmsSsActiveCommunicator(config) {};
			/* break; */
		}
		default: {
			
			throw new IllegalStateException("undefined connecton-mode: " + mode);
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
	
}
