package com.shimizukenta.secs.secs1;

import java.util.Arrays;

import com.shimizukenta.secs.AbstractSecsMessage;
import com.shimizukenta.secs.secs2.Secs2;

public abstract class AbstractSecs1Message extends AbstractSecsMessage implements Secs1Message {
	
	private static final long serialVersionUID = -7944936333752743698L;
	
	private final byte[] head;
	private final Secs2 body;
	
	public AbstractSecs1Message(byte[] head, Secs2 body) {
		this.head = Arrays.copyOf(head, head.length);
		this.body = body;
	}
	
	public AbstractSecs1Message(byte[] head) {
		this.head = Arrays.copyOf(head, head.length);
		this.body = Secs2.empty();
	}
	
	@Override
	public int getStream() {
		return (int)(this.head[2]) & 0x7F;
	}
	
	@Override
	public int getFunction() {
		return (int)(this.head[3]) & 0xFF;
	}

	@Override
	public boolean wbit() {
		return ((int)(this.head[2]) & 0x80) == 0x80;
	}
	
	@Override
	public Secs2 secs2() {
		return this.body;
	}
	
	@Override
	public int deviceId() {
		return (((int)(this.head[0]) << 8) & 0x00007F00) | (this.head[1] & 0x000000FF);
	}
	
	@Override
	public int sessionId() {
		return this.deviceId();
	}
	
	@Override
	public byte[] header10Bytes() {
		return Arrays.copyOf(this.head, this.head.length);
	}
	
	@Override
	public boolean rbit() {
		return ((int)(this.head[0]) & 0x80) == 0x80;
	}
	
	@Override
	protected String toJsonProxy() {
		
		return "{\"strm\":" + this.getStream()
		+ ",\"func\":" + this.getFunction()
		+ ",\"wbit\":" + (this.wbit() ? "true" : "false")
		+ ",\"rbit\":" + (this.rbit() ? "true" : "false")
		+ ",\"deviceId\":" + this.deviceId()
		+ ",\"systemBytes\":"+ this.systemBytesKey().toString()
		+ ",\"body\":"+ this.secs2().toJson()
		+ "}";
	}
	
	private static final String BR = System.lineSeparator();
	
	@Override
	protected String toStringProxy() {
		
		final StringBuilder sb = new StringBuilder(toHeaderBytesString())
				.append(BR)
				.append("S").append(this.getStream())
				.append("F").append(this.getFunction());
		
		if ( this.wbit() ) {
			sb.append(" W");
		}
		
		final String ss = this.secs2().toString();
		
		if ( ss.length() > 0 ) {
			sb.append(BR).append(ss);
		}
		
		return sb.append(".").toString();
	}
	
}
