package com.shimizukenta.secs.secs2;

/**
 * Secs2 build Exception.
 * 
 * @author kenta-shimizu
 */
@Deprecated
public class Secs2BuildException extends RuntimeException {
	
	private static final long serialVersionUID = -7679941208943175508L;
	
	/**
	 * Constructor.
	 */
	public Secs2BuildException() {
		super();
	}
	
	/**
	 * Constructor.
	 * 
	 * @param message the message
	 */
	public Secs2BuildException(String message) {
		super(message);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param cause the cause
	 */
	public Secs2BuildException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param message the message
	 * @param cause the cause
	 */
	public Secs2BuildException(String message, Throwable cause) {
		super(message, cause);
	}

}
