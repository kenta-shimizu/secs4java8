package com.shimizukenta.secs.secs1.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.shimizukenta.secs.secs1.Secs1MessageBlock;
import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.secs2.Secs2Builder;

public final class Secs1InvalidMessage extends AbstractSecs1Message {

	private static final long serialVersionUID = -593840794878995032L;
	
	private final List<Secs1MessageBlock> blocks;
	
	public Secs1InvalidMessage(List<? extends Secs1MessageBlock> blocks) {
		super();
		
		this.blocks = Collections.unmodifiableList(Objects.requireNonNull(blocks));
	}
	
	@Override
	public boolean rbit() {
		return false;
	}
	
	@Override
	public List<Secs1MessageBlock> toBlocks() {
		return this.blocks;
	}
	
	@Override
	public boolean isValidBlocks() {
		return false;
	}

	@Override
	public int getStream() {
		return -1;
	}

	@Override
	public int getFunction() {
		return -1;
	}

	@Override
	public boolean wbit() {
		return false;
	}

	@Override
	public Secs2 secs2() {
		return Secs2Builder.getInstance().empty();
	}
	
	@Override
	public int deviceId() {
		return -1;
	}
	
	private static final byte[] defaultHeader10Bytes = new byte[] {
			(byte)0x0,
			(byte)0x0,
			(byte)0x0,
			(byte)0x0,
			(byte)0x0,
			(byte)0x0,
			(byte)0x0,
			(byte)0x0,
			(byte)0x0,
			(byte)0x0,
			(byte)0x0};
	
	@Override
	public byte[] header10Bytes() {
		return Arrays.copyOf(defaultHeader10Bytes, defaultHeader10Bytes.length);
	}
	
	@Override
	protected String toJsonProxy() {
		return "{\"valid\":false,\"message\":\"SECS-I Invalid Blocks Message\"}";
	}
	
	@Override
	protected String toStringProxy() {
		return "SECS-I Invalid Blocks Message";
	}
	
}
