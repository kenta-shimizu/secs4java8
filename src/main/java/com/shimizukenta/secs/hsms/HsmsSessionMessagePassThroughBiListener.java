package com.shimizukenta.secs.hsms;

import java.util.EventListener;

/**
 * HSMS Session Bi-Listener.
 * 
 * @author kenta-shimizu
 *
 */
public interface HsmsSessionMessagePassThroughBiListener extends EventListener {
	
	/**
	 * Pass thgough message.
	 * 
	 * @param message the HSMS Session message.
	 * @param session the HsmsSession
	 */
	public void passThrough(HsmsMessage message, HsmsSession session);
}
