package com.shimizukenta.secs.secs2.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.shimizukenta.secs.secs2.Secs2Item;

public class Secs2RawBytes extends AbstractSecs2 {
	
	private static final long serialVersionUID = 696538383179762711L;
	
	private final byte[] bs;
	
	public Secs2RawBytes() {
		this.bs = new byte[]{};
	}
	
	public Secs2RawBytes(byte[] bs) {
		this.bs = Arrays.copyOf(bs, bs.length);
	}
	
	@Override
	public boolean isEmpty() {
		return bs.length == 0;
	}
	
	@Override
	public int size() {
		return -1;
	}
	
	@Override
	public Secs2Item secs2Item() {
		return Secs2Item.UNDEFINED;
	}
	
	private static final List<byte[]> emptyBytesList = Collections.singletonList(new byte[0]);
	
	@Override
	public List<byte[]> getBytesList(int maxBytesSize) {
		if ( this.isEmpty() ) {
			return emptyBytesList;
		} else {
			return super.getBytesList(maxBytesSize);
		}
	}
	
	@Override
	protected void putBytesPack(Secs2BytesListBuilder builder) {
		builder.put(bs);
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
