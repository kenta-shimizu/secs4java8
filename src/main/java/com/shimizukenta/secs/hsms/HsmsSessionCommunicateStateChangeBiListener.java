package com.shimizukenta.secs.hsms;

import java.util.EventListener;

/**
 * HSMS-Session Communicate State change listener.
 * 
 * @author kenta-shimizu
 *
 */
public interface HsmsSessionCommunicateStateChangeBiListener extends EventListener {
	
	/**
	 * HSMS-Session communicate State changed.
	 * 
	 * @param state the HSMS communicate State
	 * @param session the HSMS-Session
	 */
	public void changed(HsmsCommunicateState state, HsmsSession session);
}
