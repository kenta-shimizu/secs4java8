package com.shimizukenta.secs.hsms;

public class HsmChannelAlreadyShutdownException extends HsmsSendMessageException {
	
	private static final long serialVersionUID = 5066241411215536354L;
	
	public HsmChannelAlreadyShutdownException(HsmsMessage msg) {
		super(msg);
	}
	
}
