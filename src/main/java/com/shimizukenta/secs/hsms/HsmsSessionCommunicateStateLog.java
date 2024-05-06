package com.shimizukenta.secs.hsms;

import com.shimizukenta.secs.SecsLog;

/**
 * HSMS Session communicate state log.
 * 
 * @author kenta-shimizu
 *
 */
public interface HsmsSessionCommunicateStateLog extends SecsLog {
	
	/**
	 * Returns Session-ID.
	 * 
	 * @return Session-ID
	 */
	public int sessionId();
	
	/**
	 * Returns HSMS-Communicate-state.
	 * 
	 * @return HSMS-Communicate-state
	 */
	public HsmsCommunicateState state();
	
}
