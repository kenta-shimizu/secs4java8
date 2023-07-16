package com.shimizukenta.secs.hsms;

/**
 * HSMS Connection mode Illegal state Exception.
 * 
 * @author kenta-shimizu
 *
 */
public class HsmsConnectionModeIllegalStateException extends IllegalStateException {
	
	private static final long serialVersionUID = 6844816258989049537L;
	
	/**
	 * Constructor.
	 * 
	 */
	public HsmsConnectionModeIllegalStateException() {
		super();
	}
	
	/**
	 * Constructor.
	 * 
	 * @param message the message
	 */
	public HsmsConnectionModeIllegalStateException(String message) {
		super(message);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param cause the cause
	 */
	public HsmsConnectionModeIllegalStateException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param message the message
	 * @param cause the cause
	 */
	public HsmsConnectionModeIllegalStateException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
