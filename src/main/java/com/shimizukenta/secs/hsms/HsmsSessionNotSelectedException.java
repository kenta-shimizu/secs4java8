package com.shimizukenta.secs.hsms;

/**
 * Hsms Session NOT SELECTED Exception.
 * 
 * @author kenta-shimizu
 *
 */
public class HsmsSessionNotSelectedException extends HsmsException {
	
	private static final long serialVersionUID = 3311637413899884977L;
	
	/**
	 * Constructor.
	 * 
	 */
	public HsmsSessionNotSelectedException() {
		super();
	}
	
	/**
	 * Constructor.
	 * 
	 * @param message the message
	 */
	public HsmsSessionNotSelectedException(String message) {
		super(message);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param cause the cause
	 */
	public HsmsSessionNotSelectedException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param message the message
	 * @param cause the cause
	 */
	public HsmsSessionNotSelectedException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
