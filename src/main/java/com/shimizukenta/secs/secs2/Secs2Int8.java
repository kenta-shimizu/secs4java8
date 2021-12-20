package com.shimizukenta.secs.secs2;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.List;

public class Secs2Int8 extends Secs2BigInteger {
	
	private static final long serialVersionUID = 3193235846279817791L;
	
	private static final Secs2Item secs2Item = Secs2Item.INT8;

	public Secs2Int8() {
		this(new BigInteger[0]);
	}
	
	public Secs2Int8(int... values) {
		super(values);
	}

	public Secs2Int8(long... values) {
		super(values);
	}

	public Secs2Int8(BigInteger... values) {
		super(values);
	}

	public Secs2Int8(List<? extends Number> values) {
		super(values);
	}

	public Secs2Int8(byte[] bs) {
		super(bs);
	}

	@Override
	protected BigInteger byteBufferGetter(ByteBuffer bf) {
		return getBigInteger(bf, false);
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
