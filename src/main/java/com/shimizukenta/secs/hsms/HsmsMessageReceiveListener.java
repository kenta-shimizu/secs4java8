package com.shimizukenta.secs.hsms;

import java.util.EventListener;

/**
 * HSMS Message receive listener.
 * 
 * @author kenta-shimizu
 *
 */
public interface HsmsMessageReceiveListener extends EventListener {
	
	/**
	 * received.
	 * 
	 * @param message the HSMS message
	 */
	public void received(HsmsMessage message);
	
}
