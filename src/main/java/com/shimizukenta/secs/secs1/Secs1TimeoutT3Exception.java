package com.shimizukenta.secs.secs1;

/**
 * SECS-I T3-Timeout Exception.
 * 
 * @author kenta-shimizu
 *
 */
public class Secs1TimeoutT3Exception extends Secs1WaitReplyMessageException {
	
	private static final long serialVersionUID = -2366554276167734382L;
	
	/**
	 * Constructor.
	 * 
	 * @param primaryMessage the primary SECS-I Message
	 */
	public Secs1TimeoutT3Exception(Secs1Message primaryMessage) {
		super(primaryMessage);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param primaryMessage the primary SECS-I Message
	 * @param cause the cause
	 */
	public Secs1TimeoutT3Exception(Secs1Message primaryMessage, Throwable cause) {
		super(primaryMessage, cause);
	}

}
