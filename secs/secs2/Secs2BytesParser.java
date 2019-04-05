package secs.secs2;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class Secs2BytesParser extends Secs2 {
	
	private static final String PARSE_FAILED = "<PARSE_FAILED [0] >";
	
	private final byte[] bytes;
	private Secs2 parsed;
	
	protected Secs2BytesParser() {
		this(new byte[0]);
	}
	
	protected Secs2BytesParser(byte[] bs) {
		super();
		
		Objects.requireNonNull(bs);
		
		this.bytes = Arrays.copyOf(bs, bs.length);
		this.parsed = null;
	}
	
	private static class Result {
		
		private final Secs2 secs2;
		private final int endIndex;
		
		private Result(Secs2 secs2, int endIndex) {
			this.secs2 = secs2;
			this.endIndex = endIndex;
		}
	}
	
	private synchronized Secs2 parseBytes() throws Secs2Exception {
		
		if ( this.parsed == null ) {
			
			try {
				Result r = parseBytesToSecs2(bytes, 0);
				this.parsed = r.secs2;
			}
			catch (IndexOutOfBoundsException e ) {
				throw new Secs2BytesParseException(e);
			}
		}
		
		return this.parsed;
	}
	
	private static Result parseBytesToSecs2(byte[] bs, int offset) throws Secs2Exception {
		
		Secs2Item s2i = Secs2Item.get(bs[offset]);
		int lengthBits = bs[offset] & 0x03;
		int dataSize = 0;
		
		if ( lengthBits == 3 ) {
			
			dataSize |= ((int)(bs[offset + 1]) << 16) & 0xFF0000;
			dataSize |= ((int)(bs[offset + 2]) <<  8) & 0x00FF00;
			dataSize |= ((int)(bs[offset + 3])      ) & 0x0000FF;
			
		} else if ( lengthBits == 2 ) {
			
			dataSize |= ((int)(bs[offset + 1]) << 8) & 0xFF00;
			dataSize |= ((int)(bs[offset + 2])     ) & 0x00FF;
			
		} else if ( lengthBits == 1 ) {
			
			dataSize |= (int)(bs[offset + 1]) & 0xFF;
		}
		
		int startIndex = offset + 1 + lengthBits;
		
		switch ( s2i ) {
		case LIST: {
			
			int endIndex = startIndex;
			
			List<Secs2> values = new ArrayList<>();
			
			for ( int i = 0 ; i < dataSize ; ++ i ) {
				
				Result r = parseBytesToSecs2(bs, endIndex);
				
				values.add(r.secs2);
				endIndex = r.endIndex;
			}
			
			return new Result(new Secs2List(values), endIndex);
			/* break; */
		}
		default: {
			
			int endIndex   = startIndex + dataSize;
			byte[] values = Arrays.copyOfRange(bs, startIndex, endIndex);
			
			switch ( s2i ) {
			case ASCII: {
				
				return new Result(new Secs2Ascii(values), endIndex);
				/* break; */
			}
			case BINARY: {
				
				return new Result(new Secs2Binary(values), endIndex);
				/* break; */
			}
			case BOOLEAN: {
				
				return new Result(new Secs2Boolean(values), endIndex);
				/* break; */
			}
			case INT1: {
				
				return new Result(new Secs2Int1(values), endIndex);
				/* break; */
			}
			case INT2: {
				
				return new Result(new Secs2Int2(values), endIndex);
				/* break; */
			}
			case INT4: {
				
				return new Result(new Secs2Int4(values), endIndex);
				/* break; */
			}
			case INT8: {
				
				return new Result(new Secs2Int8(values), endIndex);
				/* break; */
			}
			case UINT1: {
				
				return new Result(new Secs2Uint1(values), endIndex);
				/* break; */
			}
			case UINT2: {
				
				return new Result(new Secs2Uint2(values), endIndex);
				/* break; */
			}
			case UINT4: {
				
				return new Result(new Secs2Uint4(values), endIndex);
				/* break; */
			}
			case UINT8: {
				
				return new Result(new Secs2Uint8(values), endIndex);
				/* break; */
			}
			case FLOAT4: {
				
				return new Result(new Secs2Float4(values), endIndex);
				/* break; */
			}
			case FLOAT8: {
				
				return new Result(new Secs2Float8(values), endIndex);
				/* break; */
			}
			case JIS8: {
				
				return new Result(new Secs2Jis8(values), endIndex);
				/* break; */
			}
			case UNICODE: {
				
				return new Result(new Secs2Unicode(values), endIndex);
				/* break; */
			}
			default: {
				
				throw new Secs2IrregalDataFormatException("\"" + s2i.symbol() + "\" unknown symbol");
			}
			}
		}
		}
	}
	
	@Override
	public Iterator<Secs2> iterator() {
		
		try {
			return parseBytes().iterator();
		}
		catch ( Secs2Exception e ) {
			return super.iterator();
		}
	}
	
	@Override
	public Stream<Secs2> stream() {
		
		try {
			return parseBytes().stream();
		}
		catch ( Secs2Exception e ) {
			return super.stream();
		}
	}
	
	@Override
	public int size() {
		try {
			return parseBytes().size();
		}
		catch ( Secs2Exception e ) {
			return -1;
		}
	}
	
	@Override
	public byte[] secs2Bytes() throws Secs2Exception {
		return bytes;
	}
	
	@Override
	public Secs2Item secs2Item() {
		try {
			return parseBytes().secs2Item();
		}
		catch ( Secs2Exception e ) {
			return Secs2Item.UNDEFINED;
		}
	}
	
	@Override
	protected Secs2 get( LinkedList<Integer> list ) throws Secs2Exception {
		return parseBytes().get(list);
	}
	
	@Override
	public String getAscii() throws Secs2Exception {
		return parseBytes().getAscii();
	}
	
	@Override
	protected byte getByte( int index ) throws Secs2Exception {
		return parseBytes().getByte(index);
	}
	
	@Override
	protected boolean getBoolean( int index ) throws Secs2Exception {
		return parseBytes().getBoolean(index);
	}

	@Override
	protected int getInt(int index) throws Secs2Exception {
		return parseBytes().getInt(index);
	}

	@Override
	protected long getLong(int index) throws Secs2Exception {
		return parseBytes().getLong(index);
	}
	
	@Override
	protected BigInteger getBigInteger(int index) throws Secs2Exception {
		return parseBytes().getBigInteger(index);
	}
	
	@Override
	protected float getFloat(int index) throws Secs2Exception {
		return parseBytes().getFloat(index);
	}

	@Override
	protected double getDouble(int index) throws Secs2Exception {
		return parseBytes().getDouble(index);
	}
	
	@Override
	public String toString() {
		
		if ( bytes.length > 0 ) {
			
			try {
				return parseBytes().toString();
			}
			catch ( Secs2Exception e ) {
				return PARSE_FAILED;
			}
			
		} else {
			
			return "";
		}
	}
	
	@Override
	protected String toStringValue() {
		return "";
	}

}
