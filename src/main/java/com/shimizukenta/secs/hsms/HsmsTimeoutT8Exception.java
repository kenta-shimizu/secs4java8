package com.shimizukenta.secs.hsms;

/**
 * HSMS T8-Timeout Exception.
 * 
 * @author kenta-shimizu
 *
 */
public class HsmsTimeoutT8Exception extends HsmsException {
	
	private static final long serialVersionUID = -3497475923757215910L;
	
	/**
	 * Constructor.
	 * 
	 */
	public HsmsTimeoutT8Exception() {
		super();
	}
	
	/**
	 * Constructor.
	 * 
	 * @param cause the cause
	 */
	public HsmsTimeoutT8Exception(Throwable cause) {
		super(cause);
	}
	
}
