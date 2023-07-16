package com.shimizukenta.secs.hsms;

import com.shimizukenta.secs.SecsMessage;

/**
 * HSMS Reject Exception.
 * 
 * @author kenta-shimizu
 *
 */
public class HsmsRejectException extends HsmsWaitReplyMessageException {
	
	private static final long serialVersionUID = -5627136181555783679L;
	
	/**
	 * Constructor.
	 * 
	 * @param primaryMessage the primary message
	 */
	public HsmsRejectException(SecsMessage primaryMessage) {
		super(primaryMessage);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param primaryMessage the primary message
	 * @param cause the cause
	 */
	public HsmsRejectException(SecsMessage primaryMessage, Throwable cause) {
		super(primaryMessage, cause);
	}

}
