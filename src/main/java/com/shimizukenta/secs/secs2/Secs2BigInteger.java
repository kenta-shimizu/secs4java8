package com.shimizukenta.secs.secs2;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

abstract public class Secs2BigInteger extends Secs2Number<BigInteger> {
	
	private static final long serialVersionUID = 6007516711524334157L;

	public Secs2BigInteger(int... values) {
		super();
		
		Objects.requireNonNull(values);
		
		this.values = IntStream.of(values)
				.mapToObj(BigInteger::valueOf)
				.collect(Collectors.toList());
	}
	
	public Secs2BigInteger(long... values) {
		super();
		
		Objects.requireNonNull(values);
		
		this.values = LongStream.of(values)
				.mapToObj(BigInteger::valueOf)
				.collect(Collectors.toList());
	}
	
	public Secs2BigInteger(BigInteger... values) {
		super();
		
		Objects.requireNonNull(values);
		
		this.values = Stream.of(values).collect(Collectors.toList());
	}
	
	public Secs2BigInteger(List<? extends Number> values) {
		super();
		
		Objects.requireNonNull(values);
		
		this.values = values.stream()
				.map(v -> {
					if ( v instanceof BigInteger ) {
						return (BigInteger)v;
					} else {
						return BigInteger.valueOf(v.longValue());
					}
				})
				.collect(Collectors.toList());
	}
	
	public Secs2BigInteger(byte[] bs) {
		super();
		
		Objects.requireNonNull(bs);
		
		this.bytes = Arrays.copyOf(bs, bs.length);
	}
	
	@Override
	protected BigInteger getBigInteger(int index) throws Secs2Exception {
		
		try {
			return values().get(index);
		}
		catch ( IndexOutOfBoundsException e ) {
			throw new Secs2IndexOutOfBoundsException(e);
		}
	}
	
	protected BigInteger getBigInteger(ByteBuffer bf, boolean unsigned) {
		byte[] val = new byte[secs2Item().size()];
		bf.get(val);
		return unsigned ? new BigInteger(1, val) : new BigInteger(val);
	}

}
