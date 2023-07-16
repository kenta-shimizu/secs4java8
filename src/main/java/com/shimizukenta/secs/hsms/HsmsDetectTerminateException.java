package com.shimizukenta.secs.hsms;

/**
 * HSMS Detect terminate Exception.
 * 
 * @author kenta-shimizu
 *
 */
public class HsmsDetectTerminateException extends HsmsException {
	
	private static final long serialVersionUID = -3363838905023886189L;
	
	/**
	 * Constructor.
	 * 
	 */
	public HsmsDetectTerminateException() {
		super();
	}
	
	/**
	 * Constructor.
	 * 
	 * @param cause the cause
	 */
	public HsmsDetectTerminateException(Throwable cause) {
		super(cause);
	}
	
}
