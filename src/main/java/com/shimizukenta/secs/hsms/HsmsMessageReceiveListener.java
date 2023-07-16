package com.shimizukenta.secs.hsms;

import java.util.EventListener;

import com.shimizukenta.secs.hsms.impl.AbstractHsmsMessage;

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
	 * @param msg the HSMS Message
	 */
	public void received(AbstractHsmsMessage msg);
}
