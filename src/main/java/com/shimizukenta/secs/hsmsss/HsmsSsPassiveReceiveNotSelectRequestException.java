package com.shimizukenta.secs.hsmsss;

import com.shimizukenta.secs.hsms.HsmsException;

/**
 * HSMS-SS passive receive NOT Select.req Exception.
 * 
 * @author shimizukenta
 *
 */
public class HsmsSsPassiveReceiveNotSelectRequestException extends HsmsException {
	
	private static final long serialVersionUID = -5410805951789181837L;
	
	/**
	 * Constructor.
	 * 
	 */
	public HsmsSsPassiveReceiveNotSelectRequestException() {
		super();
	}
	
	/**
	 * Constructor.
	 * 
	 * @param message the message
	 */
	public HsmsSsPassiveReceiveNotSelectRequestException(String message) {
		super(message);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param cause the cause
	 */
	public HsmsSsPassiveReceiveNotSelectRequestException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param message the message
	 * @param cause the cause
	 */
	public HsmsSsPassiveReceiveNotSelectRequestException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
