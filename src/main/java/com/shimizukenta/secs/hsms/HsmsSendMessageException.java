package com.shimizukenta.secs.hsms;

import com.shimizukenta.secs.SecsSendMessageException;

public class HsmsSendMessageException extends SecsSendMessageException {

	private static final long serialVersionUID = 5687264135212088790L;
	
	public HsmsSendMessageException(HsmsMessage msg) {
		super(msg);
	}
	
	public HsmsSendMessageException(HsmsMessage msg, Throwable cause) {
		super(msg, cause);
	}
}
