package com.shimizukenta.secs.hsms;

import com.shimizukenta.secs.SecsMessage;

/**
 * HSMS T6-Timeout Exception.
 * 
 * @author kenta-shimizu
 *
 */
public class HsmsTimeoutT6Exception extends HsmsWaitReplyMessageException {
	
	private static final long serialVersionUID = -4799649738977591873L;
	
	/**
	 * Constructor.
	 * 
	 * @param primaryMessage the primary message
	 */
	public HsmsTimeoutT6Exception(SecsMessage primaryMessage) {
		super(primaryMessage);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param primaryMessage the primary message
	 * @param cause the cause
	 */
	public HsmsTimeoutT6Exception(SecsMessage primaryMessage, Throwable cause) {
		super(primaryMessage, cause);
	}

}
