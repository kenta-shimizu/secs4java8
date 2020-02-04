package com.shimizukenta.secs.secs2;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

abstract public class Secs2Number<T extends Number> extends AbstractSecs2 {
	
	protected List<T> values;
	protected byte[] bytes;
	
	public Secs2Number() {
		super();
		
		this.values = null;
		this.bytes = null;
	}
	
	abstract protected T byteBufferGetter(ByteBuffer bf);
	abstract protected void byteBufferPutter(ByteBuffer bf, T value);
	
	protected synchronized List<T> values() throws Secs2Exception {
		
		if ( this.values == null ) {
			
			try {
				ByteBuffer bf = ByteBuffer.allocate(this.bytes.length);
				bf.put(this.bytes);
				((Buffer)bf).flip();
				
				List<T> vv = new ArrayList<>();
				while ( bf.hasRemaining() ) {
					vv.add(byteBufferGetter(bf));
				}
				
				this.values = vv;
			}
			catch ( BufferUnderflowException e ) {
				throw new Secs2Exception(e);
			}
		}
		
		return this.values;
	}
	
	protected synchronized byte[] bytes() throws Secs2Exception {
		
		if ( this.bytes == null ) {
			
			try {
				int size = secs2Item().size() * this.values.size();
				
				ByteBuffer bf = ByteBuffer.allocate(size);
				this.values.forEach(v -> { byteBufferPutter(bf, v); });
				((Buffer)bf).flip();
				
				this.bytes = new byte[size];
				bf.get(this.bytes);
			}
			catch (BufferOverflowException e) {
				throw new Secs2Exception(e);
			}
		}
		
		return this.bytes;
	}
	
	@Override
	public int size() {
		try {
			return values().size();
		}
		catch ( Secs2Exception e ) {
			return -1;
		}
	}
	
	@Override
	public byte[] secs2Bytes() throws Secs2Exception {
		
		try (
				ByteArrayOutputStream st = new ByteArrayOutputStream();
				) {
			
			byte[] bs = bytes();
			
			st.write(createHeadBytes(secs2Item(), bs.length));
			st.write(bs);
			
			return st.toByteArray();
		}
		catch ( IOException e ) {
			throw new Secs2Exception(e);
		}
	}
	
	@Override
	protected int getInt(int index) throws Secs2Exception {
		
		try {
			return values().get(index).intValue();
		}
		catch ( IndexOutOfBoundsException e ) {
			throw new Secs2IndexOutOfBoundsException(e);
		}
	}
	
	@Override
	protected long getLong(int index) throws Secs2Exception {
		
		try {
			return values().get(index).longValue();
		}
		catch ( IndexOutOfBoundsException e ) {
			throw new Secs2IndexOutOfBoundsException(e);
		}
	}
	
	@Override
	protected float getFloat(int index) throws Secs2Exception {
		
		try {
			return values().get(index).floatValue();
		}
		catch ( IndexOutOfBoundsException e ) {
			throw new Secs2IndexOutOfBoundsException(e);
		}
	}
	
	@Override
	protected double getDouble(int index) throws Secs2Exception {
		
		try {
			return values().get(index).doubleValue();
		}
		catch ( IndexOutOfBoundsException e ) {
			throw new Secs2IndexOutOfBoundsException(e);
		}
	}
	
	@Override
	protected String toJsonValue() {
		
		try {
			return values().stream()
					.map(Number::toString)
					.collect(Collectors.joining(",", "[", "]"));
		}
		catch ( Secs2Exception e ) {
			return "false";
		}
	}
	
	@Override
	protected String toStringValue() {
		try {
			return values().stream()
					.map(Number::toString)
					.collect(Collectors.joining(" "));
		}
		catch ( Secs2Exception e ) {
			return "PARSE_FAILED";
		}
	}
	
}
