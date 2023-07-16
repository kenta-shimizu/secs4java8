package com.shimizukenta.secs.hsms;

import com.shimizukenta.secs.SecsMessage;

/**
 * Hsms T3-Timeout Exception.
 * 
 * @author kenta-shimizu
 *
 */
public class HsmsTimeoutT3Exception extends HsmsWaitReplyMessageException {
	
	private static final long serialVersionUID = -8380997736755238819L;
	
	/**
	 * Constructor.
	 * 
	 * @param primaryMessage the primary message
	 */
	public HsmsTimeoutT3Exception(SecsMessage primaryMessage) {
		super(primaryMessage);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param primaryMessage the primary message
	 * @param cause the cause
	 */
	public HsmsTimeoutT3Exception(SecsMessage primaryMessage, Throwable cause) {
		super(primaryMessage, cause);
	}

}
