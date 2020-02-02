package com.shimizukenta.secs.hsmsss;

public class HsmsSsNotConnectedException extends HsmsSsSendMessageException {
	
	private static final long serialVersionUID = -4981654208857229126L;
	
	public HsmsSsNotConnectedException() {
		super();
	}

	public HsmsSsNotConnectedException(String message) {
		super(message);
	}

	public HsmsSsNotConnectedException(Throwable cause) {
		super(cause);
	}

	public HsmsSsNotConnectedException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public HsmsSsNotConnectedException(HsmsSsMessage msg) {
		super(msg);
	}

	public HsmsSsNotConnectedException(HsmsSsMessage msg, Throwable cause) {
		super(msg, cause);
	}

}
