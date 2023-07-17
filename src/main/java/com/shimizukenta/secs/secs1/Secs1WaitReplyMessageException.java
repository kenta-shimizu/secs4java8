package com.shimizukenta.secs.secs1;

import com.shimizukenta.secs.SecsWaitReplyMessageException;

/**
 * SECS-I Reply Message Exception.
 * 
 * @author kenta-shimizu
 *
 */
public class Secs1WaitReplyMessageException extends SecsWaitReplyMessageException {
	
	private static final long serialVersionUID = -6010907057511916221L;
	
	/**
	 * Constructor.
	 * 
	 * @param primaryMessage the primary SECS-I Message
	 */
	public Secs1WaitReplyMessageException(Secs1Message primaryMessage) {
		super(primaryMessage);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param primaryMessage the primary SECS-I Message
	 * @param cause the cause
	 */
	public Secs1WaitReplyMessageException(Secs1Message primaryMessage, Throwable cause) {
		super(primaryMessage, cause);
	}

}
