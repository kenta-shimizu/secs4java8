package com.shimizukenta.secs.secs2;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Secs2Float4 extends Secs2Number<Float> {
	
	private static final long serialVersionUID = -171221679009478611L;
	
	private static final Secs2Item secs2Item = Secs2Item.FLOAT4;
	
	public Secs2Float4() {
		this(new float[0]);
	}
	
	public Secs2Float4(float... values) {
		super();
		
		Objects.requireNonNull(values);
		
		this.values = new ArrayList<>();
		for ( float v : values ) {
			this.values.add(v);
		}
	}
	
	public Secs2Float4(List<? extends Number> values) {
		super();
		
		Objects.requireNonNull(values);
		
		this.values = values.stream()
				.map(Number::floatValue)
				.collect(Collectors.toList());
	}
	
	public Secs2Float4(byte[] bs) {
		super();
		
		Objects.requireNonNull(bs);
		
		this.bytes = Arrays.copyOf(bs, bs.length);
	}
	
	@Override
	protected Float byteBufferGetter(ByteBuffer bf) {
		return bf.getFloat();
	}

	@Override
	protected void byteBufferPutter(ByteBuffer bf, Float value) {
		bf.putFloat(value);
	}
	
	@Override
	public Secs2Item secs2Item() {
		return secs2Item;
	}
	
	@Override
	protected BigInteger getBigInteger(int index) throws Secs2Exception {
		return BigInteger.valueOf(this.getNumber(index).longValue());
	}
	
}
