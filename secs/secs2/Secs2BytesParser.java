package secs.secs2;

import java.util.Arrays;
import java.util.Iterator;
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
		
		this.bytes = Arrays.copyOf(bs, bs.length);
		this.parsed = null;
	}
	
	private synchronized Secs2 parseBytes() throws Secs2Exception {
		
		if ( this.parsed == null ) {
			
			//TODO
			
			
		}
		
		return this.parsed;
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
	
	
	//TODO
	//ascii
	//binary
	//numbers
	
	

	@Override
	protected String toStringValue() {
		if ( bytes.length > 0 ) {
			
			try {
				return parseBytes().toStringValue();
			}
			catch ( Secs2Exception e ) {
				return PARSE_FAILED;
			}
			
		} else {
			
			return "";
		}
	}

}
