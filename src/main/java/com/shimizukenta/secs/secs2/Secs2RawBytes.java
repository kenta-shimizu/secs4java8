package com.shimizukenta.secs.secs2;

import java.util.Arrays;

public class Secs2RawBytes extends AbstractSecs2 {
	
	private final byte[] bs;
	
	protected Secs2RawBytes() {
		this.bs = new byte[]{};
	}
	
	protected Secs2RawBytes(byte[] bs) {
		this.bs = Arrays.copyOf(bs, bs.length);
	}
	
	@Override
	public int size() {
		return -1;
	}

	@Override
	public Secs2Item secs2Item() {
		return Secs2Item.UNDEFINED;
	}

	@Override
	protected void putByteBuffers(Secs2ByteBuffersBuilder buffers) {
		buffers.put(bs);
	}
	
	@Override
	public String toString() {
		return "";
	}
	
	@Override
	protected String toStringValue() {
		return "";
	}
	
	@Override
	public String toJson() {
		return "{}";
	}
	
	@Override
	protected String toJsonValue() {
		return "";
	}
	
}
