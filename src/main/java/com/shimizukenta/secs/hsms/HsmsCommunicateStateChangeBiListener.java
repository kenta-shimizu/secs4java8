package com.shimizukenta.secs.hsms;

import java.util.EventListener;

/**
 * HSMS Communicate State change listener.
 * 
 * @author kenta-shimizu
 *
 */
public interface HsmsCommunicateStateChangeBiListener extends EventListener {
	
	/**
	 * HSMS communicate State changed.
	 * 
	 * @param state the HSMS communicate State
	 * @param communicator the HSMS communicator
	 */
	public void changed(HsmsCommunicateState state, HsmsCommunicator communicator);
	
}
