package com.shimizukenta.secs.hsms;

import java.util.Arrays;

import com.shimizukenta.secs.AbstractSecsMessage;
import com.shimizukenta.secs.secs2.Secs2;

public abstract class AbstractHsmsMessage extends AbstractSecsMessage implements HsmsMessage {
	
	private static final long serialVersionUID = 7234808180120409439L;
	
	private static final int HEADER_SIZE = 10;
	
	private final byte[] header;
	private final Secs2 body;
	
	public AbstractHsmsMessage(byte[] header, Secs2 body) {
		super();
		this.header = Arrays.copyOf(header, HEADER_SIZE);
		this.body = body;
	}
	
	public AbstractHsmsMessage(byte[] header) {
		super();
		this.header = Arrays.copyOf(header, HEADER_SIZE);
		this.body = Secs2.empty();
	}
	
	@Override
	public Secs2 secs2() {
		return this.body;
	}
	
	@Override
	public byte[] header10Bytes() {
		return Arrays.copyOf(this.header, HEADER_SIZE);
	}
	
	@Override
	public HsmsMessageType messageType() {
		return HsmsMessageType.get(this);
	}
	
	@Override
	public byte pType() {
		return this.messageType().pType();
	}
	
	@Override
	public byte sType() {
		return this.messageType().sType();
	}
	
	protected int getSessionIdFromHeader() {
		return (((int)(this.header[0]) << 8) & 0x0000FF00)
		| ((int)(header[1]) & 0x000000FF);
	}
	
	protected String toDataMessageJsonProxy() {
		
		return "{\"messageType\":\"" + messageType().toString()
				+ "\",\"strm\":" + getStream()
				+ ",\"func\":" + getFunction()
				+ ",\"wbit\":" + (wbit() ? "true" : "false")
				+ ",\"sessionId\":" + sessionId()
				+ ",\"systemBytes\":"+ systemBytesKey().toString()
				+ ",\"secs2\":"+ secs2().toJson()
				+ "}";
	}
	
	protected String toControllMessageJsonProxy() {
		
		return "{\"messageType\":\"" + messageType().toString()
				+ "\",\"p\":" + pType()
				+ ",\"s\":" + sType()
				+ ",\"sessionId\":" + sessionId()
				+ ",\"systemBytes\":"+ systemBytesKey().toString()
				+ "}";
	}
	
	private static final String BR = System.lineSeparator();
	
	protected String toDataMessageStringProxy() {
		
		StringBuilder sb = new StringBuilder(toHeaderBytesString());
		
		sb.append(BR)
		.append("S").append(getStream())
		.append("F").append(getFunction());
		
		if (wbit()) {
			sb.append(" W");
		}
		
		String body = secs2().toString();
		
		if ( ! body.isEmpty() ) {
			sb.append(BR).append(body);
		}
		
		sb.append(".");
		
		return sb.toString();
	}
	
	protected String toControllMessageStringProxy() {
		return toHeaderBytesString();
	}
	
}
