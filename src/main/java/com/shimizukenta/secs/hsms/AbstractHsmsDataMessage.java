package com.shimizukenta.secs.hsms;

import com.shimizukenta.secs.secs2.Secs2;

public abstract class AbstractHsmsDataMessage extends AbstractHsmsMessage {
	
	private static final long serialVersionUID = -7880566294772934939L;
	
	public AbstractHsmsDataMessage(byte[] header, Secs2 body) {
		super(header, body);
	}
	
	public AbstractHsmsDataMessage(byte[] header) {
		super(header);
	}
	
	@Override
	public boolean isDataMessage() {
		return true;
	}
	
	@Override
	public int getStream() {
		return (int)((this.header10Bytes())[2]) & 0x7F;
	}
	
	@Override
	public int getFunction() {
		return (int)((this.header10Bytes())[3]) & 0xFF;
	}
	
	@Override
	public boolean wbit() {
		return ((int)((this.header10Bytes())[2]) & 0x80) == 0x80;
	}
	
	@Override
	public int deviceId() {
		return this.sessionId();
	}
	
	@Override
	public int sessionId() {
		return this.getSessionIdFromHeader();
	}

	@Override
	protected String toJsonProxy() {
		return this.toDataMessageJsonProxy();
	}
	
	@Override
	protected String toStringProxy() {
		return this.toDataMessageStringProxy();
	}
	
}
