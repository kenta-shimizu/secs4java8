package com.shimizukenta.secs.hsms.impl;

import java.util.Arrays;

import com.shimizukenta.secs.hsms.HsmsMessage;
import com.shimizukenta.secs.hsms.HsmsMessageType;
import com.shimizukenta.secs.impl.AbstractSecsMessage;
import com.shimizukenta.secs.secs2.Secs2;

public abstract class AbstractHsmsMessage extends AbstractSecsMessage implements HsmsMessage {
	
	private static final long serialVersionUID = 7234808180120409439L;
	
	private static final int HEADER_SIZE = 10;
	
	private final byte[] header;
	private final Secs2 body;
	
	private final HsmsMessageType msgType;
	private final boolean isDataMsg;
	private final byte pType;
	private final byte sType;
	
	private final int sessionId;
	private final int strm;
	private final int func;
	private final boolean wbit;
	
	public AbstractHsmsMessage(byte[] header, Secs2 body) {
		super();
		this.header = Arrays.copyOf(header, HEADER_SIZE);
		this.body = body;
		
		this.msgType = HsmsMessageType.get(this);
		this.isDataMsg = this.msgType == HsmsMessageType.DATA;
		this.pType = this.msgType.pType();
		this.sType = this.msgType.sType();
		
		{
			int i = ((((int)(header[0])) << 8) & 0x0000FF00) | (((int)(header[1])) & 0x000000FF);
			if ( isDataMsg ) {
				this.sessionId = i;
			} else {
				this.sessionId = (i == 0x0000FFFF ? -1 : i);
			}
		}
		
		if ( this.isDataMsg ) {
			this.strm = (int)(header[2]) & 0x0000007F;
			this.func = (int)(header[3]) & 0x000000FF;
			this.wbit = ((int)(header[2]) & 0x80) == 0x80;
		} else {
			this.strm = -1;
			this.func = -1;
			this.wbit = false;
		}
	}
	
	public AbstractHsmsMessage(byte[] header) {
		this(header, Secs2.empty());
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
	public int deviceId() {
		return this.sessionId;
	}

	@Override
	public int sessionId() {
		return this.sessionId;
	}
	
	@Override
	public int getStream() {
		return this.strm;
	}
	
	@Override
	public int getFunction() {
		return this.func;
	}

	@Override
	public boolean wbit() {
		return this.wbit;
	}
	
	@Override
	public boolean isDataMessage() {
		return this.isDataMsg;
	}
	
	@Override
	public HsmsMessageType messageType() {
		return this.msgType;
	}
	
	@Override
	public byte pType() {
		return this.pType;
	}
	
	@Override
	public byte sType() {
		return this.sType;
	}
	
	@Override
	protected String toJsonProxy() {
		return this.isDataMsg ? this.toDataMessageJsonProxy() : this.toControlMessageJsonProxy();
	}
	
	protected String toDataMessageJsonProxy() {
		
		return "{\"messageType\":\"" + messageType().toString()
				+ "\",\"strm\":" + getStream()
				+ ",\"func\":" + getFunction()
				+ ",\"wbit\":" + (wbit() ? "true" : "false")
				+ ",\"sessionId\":" + sessionId()
				+ ",\"systemBytes\":"+ this.systemBytesKey().toString()
				+ ",\"secs2\":"+ secs2().toJson()
				+ "}";
	}
	
	protected String toControlMessageJsonProxy() {
		
		return "{\"messageType\":\"" + messageType().toString()
				+ "\",\"p\":" + pType()
				+ ",\"s\":" + sType()
				+ ",\"sessionId\":" + sessionId()
				+ ",\"systemBytes\":" + this.systemBytesKey().toString()
				+ "}";
	}
	
	@Override
	protected String toStringProxy() {
		return this.isDataMsg ? this.toDataMessageStringProxy() : this.toControlMessageStringProxy();
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
	
	protected String toControlMessageStringProxy() {
		return toHeaderBytesString();
	}
	
}
