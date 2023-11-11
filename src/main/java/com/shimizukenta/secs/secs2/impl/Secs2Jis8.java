package com.shimizukenta.secs.secs2.impl;

import java.util.Arrays;
import java.util.Objects;

import com.shimizukenta.secs.secs2.Secs2Item;

public class Secs2Jis8 extends AbstractSecs2 {
	
	private static final long serialVersionUID = 4496230020131931885L;

	private static final Secs2Item secs2Item = Secs2Item.JIS8;
	
	private byte[] bytes;
	
	public Secs2Jis8(byte[] bs) {
		super();
		
		Objects.requireNonNull(bs);
		
		this.bytes = Arrays.copyOf(bs, bs.length);
	}
	
	@Override
	public int size() {
		return this.bytes.length;
	}
	
	@Override
	protected void putBytesPack(Secs2BytesListBuilder builder) {
		this.putHeadAndBodyBytesToBytesPack(builder, this.bytes);
	}
	
	@Override
	public Secs2Item secs2Item() {
		return secs2Item;
	}
	
	@Override
	protected String toJsonValue() {
		return "false";
	}
	
	@Override
	protected String toStringValue() {
		return "NOT_SUPPORT";
	}
	
}
