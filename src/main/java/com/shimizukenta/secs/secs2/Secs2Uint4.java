package com.shimizukenta.secs.secs2;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.List;

public class Secs2Uint4 extends Secs2BigInteger {
	
	private static final long serialVersionUID = 2291601309633566297L;
	
	private static final Secs2Item secs2Item = Secs2Item.UINT4;

	public Secs2Uint4() {
		this(new BigInteger[0]);
	}
	
	public Secs2Uint4(int... values) {
		super(values);
	}

	public Secs2Uint4(long... values) {
		super(values);
	}

	public Secs2Uint4(BigInteger... values) {
		super(values);
	}

	public Secs2Uint4(List<? extends Number> values) {
		super(values);
	}

	public Secs2Uint4(byte[] bs) {
		super(bs);
	}

	@Override
	protected BigInteger byteBufferGetter(ByteBuffer bf) {
		return getBigInteger(bf, true);
	}

	@Override
	protected void byteBufferPutter(ByteBuffer bf, BigInteger value) {
		bf.putInt(value.intValue());
	}

	@Override
	public Secs2Item secs2Item() {
		return secs2Item;
	}

}
