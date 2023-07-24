package com.shimizukenta.secs.secs2.impl;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.shimizukenta.secs.secs2.Secs2Exception;
import com.shimizukenta.secs.secs2.Secs2Item;

public class Secs2Binary extends Secs2Number<Byte> {
	
	private static final long serialVersionUID = 7808005179649220096L;
	
	private static final Secs2Item secs2Item = Secs2Item.BINARY;

	public Secs2Binary() {
		this(new byte[0]);
	}
	
	public Secs2Binary(byte... bs) {
		super();
		
		Objects.requireNonNull(bs);
		
		this.bytes = Arrays.copyOf(bs, bs.length);
	}
	
	public Secs2Binary(List<Byte> values) {
		super();
		
		Objects.requireNonNull(values);
		
		this.values = Collections.unmodifiableList(values);
	}
	
	@Override
	protected Byte byteBufferGetter(ByteBuffer bf) {
		return bf.get();
	}
	
	@Override
	protected void byteBufferPutter(ByteBuffer bf, Byte v) {
		bf.put(v);
	}
	
	@Override
	protected byte getByte(int index) throws Secs2Exception {
		return this.getNumber(index).byteValue();
	}
	
	@Override
	public byte[] getBytes() throws Secs2Exception {
		byte[] bs = this.bytes();
		return Arrays.copyOf(bs, bs.length);
	}
	
	@Override
	public Secs2Item secs2Item() {
		return secs2Item;
	}
	
	@Override
	protected String toStringValue() {
		try {
			return this.values().stream()
					.map(b -> String.format("%02X", b))
					.map(s -> ("0x" + s))
					.collect(Collectors.joining(" "));
		}
		catch ( Secs2Exception e ) {
			return "PARSE_FAILED";
		}
	}
	
}
