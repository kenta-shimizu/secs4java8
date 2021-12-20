package com.shimizukenta.secs.secs2;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.List;

public class Secs2Uint2 extends Secs2BigInteger {
	
	private static final long serialVersionUID = -894268942001332594L;
	
	private static final Secs2Item secs2Item = Secs2Item.UINT2;

	public Secs2Uint2() {
		this(new BigInteger[0]);
	}
	
	public Secs2Uint2(int... values) {
		super(values);
	}

	public Secs2Uint2(long... values) {
		super(values);
	}

	public Secs2Uint2(BigInteger... values) {
		super(values);
	}

	public Secs2Uint2(List<? extends Number> values) {
		super(values);
	}

	public Secs2Uint2(byte[] bs) {
		super(bs);
	}

	@Override
	protected BigInteger byteBufferGetter(ByteBuffer bf) {
		return getBigInteger(bf, true);
	}

	@Override
	protected void byteBufferPutter(ByteBuffer bf, BigInteger value) {
		bf.putShort(value.shortValue());
	}

	@Override
	public Secs2Item secs2Item() {
		return secs2Item;
	}

}
