package secs.secs2;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Secs2Float8 extends Secs2Number<Double> {
	
	private static final Secs2Item secs2Item = Secs2Item.FLOAT8;
	
	public Secs2Float8(double... values) {
		super();
		
		this.values = new ArrayList<>();
		for ( double v : values ) {
			this.values.add(v);
		}
	}
	
	public Secs2Float8(List<Double> values) {
		super();
		
		this.values = Collections.unmodifiableList(values);
	}
	
	protected Secs2Float8(byte[] bs) {
		super();
		
		this.bytes = bs;
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
