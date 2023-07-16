package com.shimizukenta.secs.hsms;

/**
 * HSMS Too Big Send Message Size Exception.
 * 
 * @author kenta-shimizu
 *
 */
public class HsmsTooBigSendMessageException extends HsmsSendMessageException {
	
	private static final long serialVersionUID = -876996703664224152L;
	
	/**
	 * Constructor.
	 * 
	 * @param msg the reference message
	 */
	public HsmsTooBigSendMessageException(HsmsMessage msg) {
		super(msg);
	}

}
