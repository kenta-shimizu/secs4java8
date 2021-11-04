package com.shimizukenta.secs.secs1;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.shimizukenta.secs.AbstractSecsMessage;
import com.shimizukenta.secs.secs2.Secs2;

public abstract class AbstractSecs1Message extends AbstractSecsMessage implements Secs1Message {
	
	private static final long serialVersionUID = -7944936333752743698L;
	
	private final byte[] header;
	private final Secs2 body;
	
	public AbstractSecs1Message(byte[] header, Secs2 body) {
		super();
		this.header = Arrays.copyOf(header, header.length);
		this.body = body;
	}
	
	public AbstractSecs1Message(byte[] header) {
		super();
		this.header = Arrays.copyOf(header, header.length);
		this.body = Secs2.empty();
	}
	
	@Override
	public int getStream() {
		return (int)(this.header[2]) & 0x7F;
	}
	
	@Override
	public int getFunction() {
		return (int)(this.header[3]) & 0xFF;
	}

	@Override
	public boolean wbit() {
		return ((int)(this.header[2]) & 0x80) == 0x80;
	}
	
	@Override
	public Secs2 secs2() {
		return this.body;
	}
	
	@Override
	public int deviceId() {
		return (((int)(this.header[0]) << 8) & 0x00007F00) | (this.header[1] & 0x000000FF);
	}
	
	@Override
	public int sessionId() {
		return this.deviceId();
	}
	
	@Override
	public byte[] header10Bytes() {
		return Arrays.copyOf(this.header, this.header.length);
	}
	
	@Override
	public boolean rbit() {
		return ((int)(this.header[0]) & 0x80) == 0x80;
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
	
	@Override
	public List<Secs1MessageBlock> toBlocks() {
		return this.toAbstractBlocks().stream()
				.map(b -> (Secs1MessageBlock)b)
				.collect(Collectors.toList());
	}
	
	abstract public List<AbstractSecs1MessageBlock> toAbstractBlocks();
	
}
