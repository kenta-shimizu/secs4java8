package com.shimizukenta.secs.hsmsss;

import java.util.EventListener;

import com.shimizukenta.secs.hsms.HsmsMessage;

/**
 * HSMS-SS Message Pass through Bilistener.
 * 
 * @author kenta-shimizu
 *
 */
public interface HsmsSsMessagePassThroughBiListener extends EventListener {
	
	/**
	 * Pass through.
	 * 
	 * @param message the HSMS Message
	 * @param communicator the HsmsSsCommunicator
	 */
	public void passThrough(HsmsMessage message, HsmsSsCommunicator communicator);
	
}
