package com.shimizukenta.secs.hsmsss;

import java.util.EventListener;

import com.shimizukenta.secs.hsms.HsmsMessage;

/**
 * HSMS Message receive listener.
 * 
 * @author kenta-shimizu
 *
 */
public interface HsmsSsMessageReceiveBiListener extends EventListener {
	
	/**
	 * received.
	 * 
	 * @param message the HSMS Message
	 * @param communicator the HSMS-SS Communicator
	 */
	public void received(HsmsMessage message, HsmsSsCommunicator communicator);
}
