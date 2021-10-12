package com.shimizukenta.secs.hsms;

public class HsmsTooBigSendMessageException extends HsmsSendMessageException {
	
	private static final long serialVersionUID = -876996703664224152L;
	
	public HsmsTooBigSendMessageException(HsmsMessage msg) {
		super(msg);
	}

}
