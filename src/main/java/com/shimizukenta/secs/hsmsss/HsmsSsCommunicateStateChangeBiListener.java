package com.shimizukenta.secs.hsmsss;

import java.util.EventListener;

import com.shimizukenta.secs.hsms.HsmsCommunicateState;

/**
 * HSMS-SS Communicate State change listener.
 * 
 * @author kenta-shimizu
 *
 */
public interface HsmsSsCommunicateStateChangeBiListener extends EventListener {
	
	/**
	 * HSMS-SS communicate State changed.
	 * 
	 * @param state the HSMS communicate State
	 * @param communicator the HSMS-SS communicator
	 */
	public void changed(HsmsCommunicateState state, HsmsSsCommunicator communicator);
	
}
