package com.shimizukenta.secs.secs2;

/**
 * Secs2 base Exception.
 * 
 * @author shimizukenta
 *
 */
public class Secs2Exception extends Exception {
	
	private static final long serialVersionUID = -4421453269132804889L;
	
	/**
	 * Constructor.
	 */
	public Secs2Exception() {
		super();
	}
	
	/**
	 * Constructor.
	 * 
	 * @param message the message
	 */
	public Secs2Exception(String message) {
		super(message);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param cause the cause
	 */
	public Secs2Exception(Throwable cause) {
		super(cause);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param message the message
	 * @param cause the cause
	 */
	public Secs2Exception(String message, Throwable cause) {
		super(message, cause);
	}

}
