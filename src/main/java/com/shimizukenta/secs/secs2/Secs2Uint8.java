package com.shimizukenta.secs.secs2;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.List;

public class Secs2Uint8 extends Secs2BigInteger {
	
	private static final long serialVersionUID = -4704109221530483584L;
	
	private static final Secs2Item secs2Item = Secs2Item.UINT8;

	public Secs2Uint8() {
		this(new BigInteger[0]);
	}
	
	public Secs2Uint8(int... values) {
		super(values);
	}

	public Secs2Uint8(long... values) {
		super(values);
	}

	public Secs2Uint8(BigInteger... values) {
		super(values);
	}

	public Secs2Uint8(List<? extends Number> values) {
		super(values);
	}

	public Secs2Uint8(byte[] bs) {
		super(bs);
	}

	@Override
	protected BigInteger byteBufferGetter(ByteBuffer bf) {
		return getBigInteger(bf, true);
	}

	@Override
	protected void byteBufferPutter(ByteBuffer bf, BigInteger value) {
		bf.putLong(value.longValue());
	}

	@Override
	public Secs2Item secs2Item() {
		return secs2Item;
	}

}
