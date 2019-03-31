package secs.secs2;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Secs2Float4 extends Secs2Number<Float> {
	
	private static final Secs2Item secs2Item = Secs2Item.FLOAT4;
	
	public Secs2Float4(float... values) {
		super();
		
		this.values = new ArrayList<>();
		for ( float v : values ) {
			this.values.add(v);
		}
	}
	
	public Secs2Float4(List<Float> values) {
		super();
		
		this.values = Collections.unmodifiableList(values);
	}
	
	protected Secs2Float4(byte[] bs) {
		super();
		
		this.bytes = bs;
	}
	
	@Override
	protected Float byteBufferGetter(ByteBuffer bf) {
		return bf.getFloat();
	}

	@Override
	protected void byteBufferPutter(ByteBuffer bf, Float value) {
		bf.putFloat(value);
	}
	
	@Override
	public Secs2Item secs2Item() {
		return secs2Item;
	}
	
}
