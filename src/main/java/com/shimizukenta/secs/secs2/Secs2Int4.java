package com.shimizukenta.secs.secs2;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.List;

public class Secs2Int4 extends Secs2BigInteger {
	
	private static final long serialVersionUID = -540841183401916929L;
	
	private static final Secs2Item secs2Item = Secs2Item.INT4;

	public Secs2Int4() {
		this(new BigInteger[0]);
	}
	
	public Secs2Int4(int... values) {
		super(values);
	}

	public Secs2Int4(long... values) {
		super(values);
	}

	public Secs2Int4(BigInteger... values) {
		super(values);
	}

	public Secs2Int4(List<? extends Number> values) {
		super(values);
	}

	public Secs2Int4(byte[] bs) {
		super(bs);
	}

	@Override
	protected BigInteger byteBufferGetter(ByteBuffer bf) {
		return getBigInteger(bf, false);
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
