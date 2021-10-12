package com.shimizukenta.secs.hsms;

public class HsmsNotConnectedException extends HsmsSendMessageException {
	
	private static final long serialVersionUID = -5939879202806123248L;
	
	public HsmsNotConnectedException(HsmsMessage msg) {
		super(msg);
	}
	
	public HsmsNotConnectedException(HsmsMessage msg, Throwable cause) {
		super(msg, cause);
	}

}
