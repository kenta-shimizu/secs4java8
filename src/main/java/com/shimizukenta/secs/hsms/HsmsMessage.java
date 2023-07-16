package com.shimizukenta.secs.hsms;

import com.shimizukenta.secs.SecsMessage;

/**
 * HSMS Message.
 * 
 * @author kenta-shimizu
 *
 */
public interface HsmsMessage extends SecsMessage {
	
	/**
	 * Returns HSMS Message Type.
	 * 
	 * @return HSMS Message Type
	 */
	public HsmsMessageType messageType();
	
	/**
	 * Return true if Message is Data type, otherwise false.
	 * 
	 * @return true if Message is Data type, otherwise false.
	 */
	public boolean isDataMessage();
	
	/**
	 * Returns p-Type byte code.
	 * 
	 * @return p-Type byte code
	 */
	public byte pType();
	
	/**
	 * Returns s-Type byte code.
	 * 
	 * @return s-Type byte code
	 */
	public byte sType();
	
}
