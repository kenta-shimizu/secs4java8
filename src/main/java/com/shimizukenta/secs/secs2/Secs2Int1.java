package com.shimizukenta.secs.secs2;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.List;

public class Secs2Int1 extends Secs2BigInteger {
	
	private static final long serialVersionUID = -6438314140181526163L;
	
	private static final Secs2Item secs2Item = Secs2Item.INT1;
	
	public Secs2Int1() {
		this(new BigInteger[0]);
	}
	
	public Secs2Int1(int... values) {
		super(values);
	}

	public Secs2Int1(long... values) {
		super(values);
	}

	public Secs2Int1(BigInteger... values) {
		super(values);
	}

	public Secs2Int1(List<? extends Number> values) {
		super(values);
	}
	
	public Secs2Int1(byte[] bs) {
		super(bs);
	}

	@Override
	protected BigInteger byteBufferGetter(ByteBuffer bf) {
		return getBigInteger(bf, false);
	}

	@Override
	protected void byteBufferPutter(ByteBuffer bf, BigInteger value) {
		bf.put(value.byteValue());
	}

	@Override
	public Secs2Item secs2Item() {
		return secs2Item;
	}

}
