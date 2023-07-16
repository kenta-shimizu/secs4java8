package com.shimizukenta.secs.hsms;

import java.util.EventListener;

/**
 * HSMS Communicate State change listener.
 * 
 * @author kenta-shimizu
 *
 */
public interface HsmsCommunicateStateChangeListener extends EventListener {
	
	/**
	 * HSMS-SS Communicate State Changed.
	 * 
	 * @param state the HSMS Communicate State
	 */
	public void changed(HsmsCommunicateState state);
}
