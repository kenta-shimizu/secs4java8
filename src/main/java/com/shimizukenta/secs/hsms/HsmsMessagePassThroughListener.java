package com.shimizukenta.secs.hsms;

import java.util.EventListener;

/**
 * HSMS Message Pass through listener.
 * 
 * @author kenta-shimizu
 *
 */
public interface HsmsMessagePassThroughListener extends EventListener {
	
	/**
	 * Pass through.
	 * 
	 * @param message the HSMS Message
	 */
	public void passThrough(HsmsMessage message);
}
