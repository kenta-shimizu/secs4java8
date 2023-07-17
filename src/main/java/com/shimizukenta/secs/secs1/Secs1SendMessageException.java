package com.shimizukenta.secs.secs1;

import com.shimizukenta.secs.SecsSendMessageException;

/**
 * SECS-I send message Exception.
 * 
 * @author kenta-shimizu
 *
 */
public class Secs1SendMessageException extends SecsSendMessageException {
	
	private static final long serialVersionUID = 8277809939091038496L;
	
	/**
	 * Constructor.
	 * 
	 * @param secs1Message the SECS-I Message
	 */
	public Secs1SendMessageException(Secs1Message secs1Message) {
		super(secs1Message);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param secs1Message the SECS-I Message
	 * @param cause the cause
	 */
	public Secs1SendMessageException(Secs1Message secs1Message, Throwable cause) {
		super(secs1Message, cause);
	}

}
