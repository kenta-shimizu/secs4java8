package com.shimizukenta.secs.secs1.impl;

public final class ByteOrSecs1Message {
	
	private final Byte b;
	private final Secs1MessageBlockPack msg;
	
	public ByteOrSecs1Message(Byte b, Secs1MessageBlockPack msg) {
		this.b = b;
		this.msg = msg;
	}
	
	public Secs1MessageBlockPack message() {
		return msg;
	}
	
	public Byte getByte() {
		return b;
	}
	
	private static final byte ENQ = (byte)0x5;
	
	public boolean isENQ() {
		return b != null && b.byteValue() == ENQ;
	}
	
}
