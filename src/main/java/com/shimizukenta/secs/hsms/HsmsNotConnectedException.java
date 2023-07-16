package com.shimizukenta.secs.hsms;

/**
 * HSMS NOT Connected Exception.
 * 
 * @author kenta-shimizu
 *
 */
public class HsmsNotConnectedException extends HsmsException {
	
	private static final long serialVersionUID = 183609995184389928L;
	
	/**
	 * Constructor.
	 * 
	 */
	public HsmsNotConnectedException() {
		super();
	}
	
	/**
	 * Constructor.
	 * 
	 * @param cause the cause
	 */
	public HsmsNotConnectedException(Throwable cause) {
		super(cause);
	}
	
}
