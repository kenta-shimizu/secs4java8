/**
 * 
 */
package com.shimizukenta.secs.hsms;

import java.util.EventListener;

/**
 * HSMS Message receive Bi-Listener.
 * 
 * @author kenta-shimizu
 *
 */
public interface HsmsMessageReceiveBiListener extends EventListener {
	
	/**
	 * HSMS Message received.
	 * 
	 * @param message the HSMS message
	 * @param communicator the HSMS Communicator accessor
	 */
	public void received(HsmsMessage message, HsmsGemAccessor communicator);
	
}
