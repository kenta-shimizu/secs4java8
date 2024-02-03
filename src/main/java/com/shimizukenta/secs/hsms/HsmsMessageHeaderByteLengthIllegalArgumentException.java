package com.shimizukenta.secs.hsms;

/**
 * HSMS-Message Header-10-bytes length is NOT equals 10 IllegalArgumentException.
 * 
 * @author kenta-shimizu
 *
 */
public class HsmsMessageHeaderByteLengthIllegalArgumentException extends IllegalArgumentException {
	
	private static final long serialVersionUID = -8037404719535482414L;
	
	/**
	 * Constructor.
	 * 
	 */
	public HsmsMessageHeaderByteLengthIllegalArgumentException() {
		super();
	}
}
