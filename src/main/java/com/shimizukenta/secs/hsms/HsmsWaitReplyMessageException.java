package com.shimizukenta.secs.hsms;

import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.SecsWaitReplyMessageException;

/**
 * HSMS Wait reply message Exception.
 * 
 * @author kenta-shimizu
 *
 */
public class HsmsWaitReplyMessageException extends SecsWaitReplyMessageException {
	
	private static final long serialVersionUID = 1959829976603850269L;
	
	/**
	 * Constructor.
	 * 
	 * @param primaryMessage the primary message
	 */
	public HsmsWaitReplyMessageException(SecsMessage primaryMessage) {
		super(primaryMessage);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param primaryMessage the primary message
	 * @param cause the cause
	 */
	public HsmsWaitReplyMessageException(SecsMessage primaryMessage, Throwable cause) {
		super(primaryMessage, cause);
	}

}
