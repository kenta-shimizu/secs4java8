package com.shimizukenta.secs.secs2.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.shimizukenta.secs.secs2.Secs2Exception;
import com.shimizukenta.secs.secs2.Secs2IndexOutOfBoundsException;
import com.shimizukenta.secs.secs2.Secs2Item;
import com.shimizukenta.secs.secs2.Secs2LengthByteOutOfRangeException;

public class Secs2Boolean extends AbstractSecs2 {
	
	private static final long serialVersionUID = -7190678080526078554L;
	
	private static Secs2Item secs2Item = Secs2Item.BOOLEAN;
	
	private static final byte BYTE_TRUE = (byte)0xFF;
	private static final byte BYTE_FALSE = (byte)0x00;
	
	private static final String STRING_TRUE = "TRUE";
	private static final String STRING_FALSE = "FALSE";
	
	private List<Boolean> bools;
	private byte[] bytes;
	
	public Secs2Boolean() {
		this(new boolean[0]);
	}
	
	public Secs2Boolean(boolean... bools) {
		super();
		
		Objects.requireNonNull(bools);
		
		if (bools.length > 0x00FFFFFF) {
			throw new Secs2LengthByteOutOfRangeException();
		}
		
		this.bools = new ArrayList<>();
		for ( boolean b : bools ) {
			this.bools.add(b);
		}
		
		this.bytes = null;
	}

	public Secs2Boolean(List<Boolean> bools) {
		super();
		
		Objects.requireNonNull(bools);
		
		if (bools.size() > 0x00FFFFFF) {
			throw new Secs2LengthByteOutOfRangeException();
		}
		
		this.bools = new ArrayList<>(bools);
		
		this.bytes = null;
	}
	
	public Secs2Boolean(byte[] bs) {
		super();
		
		Objects.requireNonNull(bs);
		
		this.bools = null;
		this.bytes = Arrays.copyOf(bs, bs.length);
	}
	
	@Override
	public int size() {
		return bools().size();
	}
	
	
	@Override
	protected void putBytesPack(Secs2BytesListBuilder builder) {
		this.putHeadAndBodyBytesToBytesPack(builder, bytes());
	}
	
	private final Object sync = new Object();
	
	private List<Boolean> bools() {
		
		synchronized ( this.sync ) {
			
			if ( this.bools == null ) {
				
				this.bools = new ArrayList<>();
				for ( byte b : bytes() ) {
					this.bools.add(b != BYTE_FALSE);
				}
			}
			
			return this.bools;
		}
	}
	
	private byte[] bytes() {
		
		synchronized ( this.sync ) {
			
			if (this.bytes == null) {
				
				List<Boolean> bs = bools();
				this.bytes = new byte[bs.size()];
				int i = 0;
				for ( Boolean b : bs ) {
					this.bytes[i] = b ? BYTE_TRUE : BYTE_FALSE;
					++i;
				}
			}
			
			return this.bytes;
		}
	}
	
	@Override
	protected boolean getBoolean( int index ) throws Secs2Exception {
		
		try {
			return bools().get(index);
		}
		catch ( IndexOutOfBoundsException e ) {
			throw new Secs2IndexOutOfBoundsException(e);
		}
	}
	
	@Override
	protected Optional<Boolean> optionalBoolean(int index) {
		if ( index >= 0 && index < this.size() ) {
			return Optional.of(this.bools().get(index));
		} else {
			return Optional.empty();
		}
	}
	
	@Override
	public Secs2Item secs2Item() {
		return secs2Item;
	}
	
	@Override
	protected String toJsonValue() {
		
		return bools().stream()
				.map(f -> (f ? "true" : "false"))
				.collect(Collectors.joining(",", "[", "]"));
	}
	
	@Override
	protected String toStringValue() {
		
		return bools().stream()
				.map(b -> (b ? STRING_TRUE : STRING_FALSE))
				.collect(Collectors.joining(" "));
	}

}
