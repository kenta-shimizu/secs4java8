package com.shimizukenta.secs.secs1;

/**
 * SECS-I Message Too Big Send Message Exception.
 * 
 * @author kenta-shimizu
 *
 */
public class Secs1TooBigSendMessageException extends Secs1SendMessageException {
	
	private static final long serialVersionUID = 7790874216559023571L;
	
	/**
	 * Constructor.
	 * 
	 * @param secs1Message the SECS-I Message
	 */
	public Secs1TooBigSendMessageException(Secs1Message secs1Message) {
		super(secs1Message);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param secs1Message the SECS-I Message
	 * @param cause the cause
	 */
	public Secs1TooBigSendMessageException(Secs1Message secs1Message, Throwable cause) {
		super(secs1Message, cause);
	}
	
}
