package secs.secs2;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.List;

public class Secs2Int2 extends Secs2BigInteger {
	
	private static final Secs2Item secs2Item = Secs2Item.INT2;

	public Secs2Int2() {
		this(new BigInteger[0]);
	}
	
	public Secs2Int2(int... values) {
		super(values);
	}

	public Secs2Int2(long... values) {
		super(values);
	}

	public Secs2Int2(BigInteger... values) {
		super(values);
	}

	public Secs2Int2(List<? extends Number> values) {
		super(values);
	}

	protected Secs2Int2(byte[] bs) {
		super(bs);
	}

	@Override
	protected BigInteger byteBufferGetter(ByteBuffer bf) {
		return getBigInteger(bf, false);
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
