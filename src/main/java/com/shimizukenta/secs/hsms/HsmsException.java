package com.shimizukenta.secs.hsms;

import com.shimizukenta.secs.SecsException;

/**
 * HSMS Communicate Exception.
 * 
 * @author shimizukenta
 *
 */
public class HsmsException extends SecsException {
	
	private static final long serialVersionUID = 6427923930733052896L;
	
	/**
	 * Constructor.
	 * 
	 */
	public HsmsException() {
		super();
	}
	
	/**
	 * Constructor.
	 * 
	 * @param message the message
	 */
	public HsmsException(String message) {
		super(message);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param cause the cause
	 */
	public HsmsException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param message the message
	 * @param cause the cause
	 */
	public HsmsException(String message, Throwable cause) {
		super(message, cause);
	}

}
