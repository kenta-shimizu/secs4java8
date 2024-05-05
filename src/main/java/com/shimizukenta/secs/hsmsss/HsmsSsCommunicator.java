package com.shimizukenta.secs.hsmsss;

import java.io.IOException;

import com.shimizukenta.secs.SecsCommunicator;
import com.shimizukenta.secs.hsms.HsmsCommunicateStateDetectable;
import com.shimizukenta.secs.hsms.HsmsConnectionMode;
import com.shimizukenta.secs.hsms.HsmsConnectionModeIllegalStateException;
import com.shimizukenta.secs.hsms.HsmsGemAccessor;
import com.shimizukenta.secs.hsms.HsmsLogObservable;
import com.shimizukenta.secs.hsms.HsmsMessagePassThroughObservable;
import com.shimizukenta.secs.hsms.HsmsMessageReceiveObservable;
import com.shimizukenta.secs.hsmsss.impl.AbstractHsmsSsActiveCommunicator;
import com.shimizukenta.secs.hsmsss.impl.AbstractHsmsSsPassiveCommunicator;

/**
 * This interface is implementation of HSMS-SS (SEMI-E37.1).
 * 
 * <ul>
 * <li>To create newInstance, {@link #newInstance(HsmsSsCommunicatorConfig)}</li>
 * <li>To create newInstance and open, {@link #open(HsmsSsCommunicatorConfig)}</li>
 * </ul>
 * 
 * @author kenta-shimizu
 *
 */
public interface HsmsSsCommunicator extends SecsCommunicator, HsmsGemAccessor, HsmsMessageReceiveObservable, HsmsCommunicateStateDetectable, HsmsMessagePassThroughObservable, HsmsLogObservable {
	
	/**
	 * Linktest.
	 * 
	 * @return true if linktest success, otherwise false.
	 * @throws InterruptedException if interrupted
	 */
	public boolean linktest() throws InterruptedException;
	
	/**
	 * create new HSMS-SS-Communicator instance.
	 * 
	 * @param config the HSMS-SS config
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
			
			throw new HsmsConnectionModeIllegalStateException("undefined connecton-mode: " + mode);
		}
		}
	}
	
	/**
	 * Create new HSMS-SS-Communicator instance and {@link #open()}.
	 * 
	 * @param config the HSMS-SS config
	 * @return new HSMS-SS-Communicator instance
	 * @throws IOException if open failed
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
