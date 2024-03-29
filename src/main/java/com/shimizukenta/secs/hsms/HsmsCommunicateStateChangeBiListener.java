package com.shimizukenta.secs.hsms;

import java.util.EventListener;

/**
 * HSMS Communicate State chenge-Bi-Listener.
 * 
 * @author kenta-shimizu
 *
 */
public interface HsmsCommunicateStateChangeBiListener extends EventListener {
	
	/**
	 * HSMS Communicate State Changed.
	 * 
	 * @param state the HSMS Communicate State
	 * @param accessor the HSMS communicate accessor
	 */
	public void changed(HsmsCommunicateState state, HsmsGemAccessor accessor);
	
}
