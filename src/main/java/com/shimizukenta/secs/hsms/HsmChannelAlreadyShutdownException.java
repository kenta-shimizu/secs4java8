package com.shimizukenta.secs.hsms;

/**
 * HsmChannelAlreadyShutdownException.
 * 
 * @author kenta-shimizu
 *
 */
public class HsmChannelAlreadyShutdownException extends HsmsSendMessageException {
	
	private static final long serialVersionUID = 5066241411215536354L;
	
	/**
	 * Constructor.
	 * 
	 * @param message the HSMS message
	 */
	public HsmChannelAlreadyShutdownException(HsmsMessage message) {
		super(message);
	}
	
}
