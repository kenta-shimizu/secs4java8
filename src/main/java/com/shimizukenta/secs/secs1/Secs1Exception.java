package com.shimizukenta.secs.secs1;

import com.shimizukenta.secs.SecsException;

/**
 * SECS communicate Exception.
 * 
 * @author kenta-shimizu
 *
 */
public class Secs1Exception extends SecsException {
	
	private static final long serialVersionUID = -4498265114805329024L;
	
	/**
	 * Constructor.
	 * 
	 */
	public Secs1Exception() {
		super();
	}
	
	/**
	 * Constructor.
	 * 
	 * @param message the message
	 */
	public Secs1Exception(String message) {
		super(message);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param cause the cause
	 */
	public Secs1Exception(Throwable cause) {
		super(cause);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param message the message
	 * @param cause the cause
	 */
	public Secs1Exception(String message, Throwable cause) {
		super(message, cause);
	}
	
}
