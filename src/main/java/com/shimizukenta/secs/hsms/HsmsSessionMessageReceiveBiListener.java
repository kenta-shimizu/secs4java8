package com.shimizukenta.secs.hsms;

import java.util.EventListener;

/**
 * HSMS Message receive listener.
 * 
 * @author kenta-shimizu
 *
 */
public interface HsmsSessionMessageReceiveBiListener extends EventListener {
	
	/**
	 * received.
	 * 
	 * @param message the HSMS Message
	 * @param hsmsSession the HSMS Session
	 */
	public void received(HsmsMessage message, HsmsSession hsmsSession);
}
