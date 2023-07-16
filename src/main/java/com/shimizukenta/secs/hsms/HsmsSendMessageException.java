package com.shimizukenta.secs.hsms;

import com.shimizukenta.secs.SecsSendMessageException;

/**
 * HSMS Send Message Exception.
 * 
 * @author kenta-shimizu
 *
 */
public class HsmsSendMessageException extends SecsSendMessageException {

	private static final long serialVersionUID = 5687264135212088790L;
	
	/**
	 * Constructor.
	 * 
	 * @param msg HSMS Message
	 */
	public HsmsSendMessageException(HsmsMessage msg) {
		super(msg);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param msg the HSMS Message
	 * @param cause the cause
	 */
	public HsmsSendMessageException(HsmsMessage msg, Throwable cause) {
		super(msg, cause);
	}
}
