package secs.secs2;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Secs2Binary extends Secs2Number<Byte> {
	
	private static final Secs2Item secs2Item = Secs2Item.BINARY;

	public Secs2Binary(byte... bs) {
		super();
		
		Objects.requireNonNull(bs);
		
		this.bytes = Arrays.copyOf(bs, bs.length);
	}
	
	public Secs2Binary(List<Byte> values) {
		super();
		
		Objects.requireNonNull(values);
		
		this.values = Collections.unmodifiableList(values);
	}
	
	@Override
	protected Byte byteBufferGetter(ByteBuffer bf) {
		return bf.get();
	}
	
	@Override
	protected void byteBufferPutter(ByteBuffer bf, Byte v) {
		bf.put(v);
	}
	
	@Override
	protected byte getByte(int index) throws Secs2Exception {
		
		try {
			return values().get(index).byteValue();
		}
		catch ( IndexOutOfBoundsException e ) {
			throw new Secs2IndexOutOfBoundsException(e);
		}
	}
	
	@Override
	public Secs2Item secs2Item() {
		return secs2Item;
	}
	
	@Override
	protected String toStringValue() {
		try {
			return values().stream()
					.map(b -> String.format("%02X", b))
					.map(s -> ("0x" + s))
					.collect(Collectors.joining(" "));
		}
		catch ( Secs2Exception e ) {
			return "PARSE_FAILED";
		}
	}
	
}
