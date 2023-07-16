package com.shimizukenta.secs.hsms;

import com.shimizukenta.secs.SecsMessage;

/**
 * HAMA nor expected Control type reply message Exception.
 * 
 * @author kenta-shimizu
 *
 */
public class HsmsNotExpectControlTypeReplyMessageException extends HsmsWaitReplyMessageException {
	
	private static final long serialVersionUID = -3694450588050886894L;
	
	/**
	 * Constructor.
	 * 
	 * @param primaryMessage the primary message
	 */
	public HsmsNotExpectControlTypeReplyMessageException(SecsMessage primaryMessage) {
		super(primaryMessage);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param primaryMessage the primary message
	 * @param cause the cause
	 */
	public HsmsNotExpectControlTypeReplyMessageException(SecsMessage primaryMessage, Throwable cause) {
		super(primaryMessage, cause);
	}
	
}
