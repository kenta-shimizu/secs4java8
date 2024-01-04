package com.shimizukenta.secs.secs1.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.shimizukenta.secs.secs1.Secs1MessageBlock;
import com.shimizukenta.secs.secs2.Secs2;

public final class Secs1ValidMessage extends AbstractSecs1Message {
	
	private static final long serialVersionUID = 2549586453996223638L;
	
	private final byte[] header10Bytes;
	private final Secs2 body;
	private final List<Secs1MessageBlock> blocks;
	
	private final int strm;
	private final int func;
	private final boolean wbit;
	private final int deviceId;
	private final boolean rbit;
	
	public Secs1ValidMessage(
			byte[] header10Bytes,
			Secs2 body,
			List<? extends Secs1MessageBlock> blocks) {
		
		super();
		
		this.header10Bytes = Objects.requireNonNull(header10Bytes);
		if (this.header10Bytes.length != 10) {
			throw new IllegalArgumentException("header10BYtes require 10 bytes.");
		}
		
		this.body = Objects.requireNonNull(body);
		this.blocks = Collections.unmodifiableList(Objects.requireNonNull(blocks));
		
		this.strm = (int)(this.header10Bytes[2]) & 0x7F;
		this.func = (int)(this.header10Bytes[3]) & 0xFF;
		this.wbit = ((int)(this.header10Bytes[2]) & 0x80) == 0x80;
		this.deviceId = (((int)(this.header10Bytes[0]) << 8) & 0x00007F00) | (this.header10Bytes[1] & 0x000000FF);
		this.rbit = ((int)(this.header10Bytes[0]) & 0x80) == 0x80;
	}
	
	@Override
	public boolean rbit() {
		return this.rbit;
	}
	
	@Override
	public List<Secs1MessageBlock> toBlocks() {
		return this.blocks;
	}
	
	@Override
	public boolean isValidBlocks() {
		return true;
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
	public Secs2 secs2() {
		return this.body;
	}
	
	@Override
	public int deviceId() {
		return this.deviceId;
	}
	
	@Override
	public byte[] header10Bytes() {
		return Arrays.copyOf(this.header10Bytes, this.header10Bytes.length);
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
