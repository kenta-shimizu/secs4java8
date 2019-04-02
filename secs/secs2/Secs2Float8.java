package secs.secs2;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Secs2Float8 extends Secs2Number<Double> {
	
	private static final Secs2Item secs2Item = Secs2Item.FLOAT8;
	
	public Secs2Float8(double... values) {
		super();
		
		Objects.requireNonNull(values);
		
		this.values = new ArrayList<>();
		for ( double v : values ) {
			this.values.add(v);
		}
	}
	
	public Secs2Float8(List<Number> values) {
		super();
		
		Objects.requireNonNull(values);
		
		this.values = values.stream()
				.map(Number::doubleValue)
				.collect(Collectors.toList());
	}
	
	protected Secs2Float8(byte[] bs) {
		super();
		
		Objects.requireNonNull(bs);
		
		this.bytes = Arrays.copyOf(bs, bs.length);
	}
	
	@Override
	protected Double byteBufferGetter(ByteBuffer bf) {
		return bf.getDouble();
	}

	@Override
	protected void byteBufferPutter(ByteBuffer bf, Double value) {
		bf.putDouble(value);
	}
	
	@Override
	public Secs2Item secs2Item() {
		return secs2Item;
	}
	
}
