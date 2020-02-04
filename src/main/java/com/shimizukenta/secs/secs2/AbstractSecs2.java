package com.shimizukenta.secs.secs2;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class AbstractSecs2 implements Secs2 {

	public AbstractSecs2() {
		/* Nothing */
	}
	
	
	abstract protected void putByteBuffers(Secs2ByteBuffers buffers) throws Secs2BuildException;
	
	protected void putHeaderBytesToByteBuffers(Secs2ByteBuffers buffers, int length) throws Secs2BuildException {
		
		if ( length > 0xFFFFFF || length < 0 ) {
			throw new Secs2LengthByteOutOfRangeException("length: " + length);
		}
		
		byte b = secs2Item().code();
		
		if ( length > 0xFFFF ) {
			
			buffers.put( b | 0x3 );
			buffers.put(length >> 16);
			buffers.put(length >> 8);
			buffers.put(length);
			
		} else if ( length > 0xFF) {
			
			buffers.put( b | 0x2 );
			buffers.put(length >> 8);
			buffers.put(length);
			
		} else {
			
			buffers.put( b | 0x1 );
			buffers.put(length);
		}
	}
	
	
	private static LinkedList<Integer> createLinkedList(int... indices) {
		return IntStream.of(indices).boxed()
				.collect(Collectors.toCollection(LinkedList<Integer>::new));
	}
	
	@Override
	public Secs2 get() {
		return this;
	}
	
	@Override
	public final Secs2 get(int... indices) throws Secs2Exception {
		return get(createLinkedList(indices));
	}
	
	protected Secs2 get(LinkedList<Integer> list) throws Secs2Exception {
		if ( list.isEmpty() ) {
			return get();
		} else {
			throw new Secs2IrregalDataFormatException("Not Secs2List");
		}
	}
	
	@Override
	public final String getAscii(int... indices) throws Secs2Exception {
		return get(indices).getAscii();
	}
	
	@Override
	public String getAscii() throws Secs2Exception {
		throw new Secs2IrregalDataFormatException("Not Secs2Ascii");
	}
	
	@Override
	public final byte getByte(int... indices) throws Secs2Exception {
		LinkedList<Integer> list = createLinkedList(indices);
		int lastIndex = list.removeLast();
		return get(list).getByte(lastIndex);
	}
	
	protected byte getByte(int index) throws Secs2Exception {
		throw new Secs2IrregalDataFormatException("Not Secs2Byte");
	}
	
	@Override
	public final boolean getBoolean(int... indices) throws Secs2Exception {
		LinkedList<Integer> list = createLinkedList(indices);
		int lastIndex = list.removeLast();
		return get(list).getBoolean(lastIndex);
	}
	
	protected boolean getBoolean(int index) throws Secs2Exception {
		throw new Secs2IrregalDataFormatException("Not Secs2Boolean");
	}
	
	@Override
	public final int getInt(int... indices) throws Secs2Exception {
		LinkedList<Integer> list = createLinkedList(indices);
		int lastIndex = list.removeLast();
		return get(list).getInt(lastIndex);
	}
	
	protected int getInt(int index) throws Secs2Exception {
		throw new Secs2IrregalDataFormatException("Not Secs2Number");
	}
	
	@Override
	public final long getLong(int... indices) throws Secs2Exception {
		LinkedList<Integer> list = createLinkedList(indices);
		int lastIndex = list.removeLast();
		return get(list).getLong(lastIndex);
	}
	
	protected long getLong(int index) throws Secs2Exception {
		throw new Secs2IrregalDataFormatException("Not Secs2Number");
	}
	
	@Override
	public final BigInteger getBigInteger(int... indices) throws Secs2Exception {
		LinkedList<Integer> list = createLinkedList(indices);
		int lastIndex = list.removeLast();
		return get(list).getBigInteger(lastIndex);
	}
	
	protected BigInteger getBigInteger(int index) throws Secs2Exception {
		throw new Secs2IrregalDataFormatException("Not Secs2Number");
	}
	
	@Override
	public final float getFloat(int... indices) throws Secs2Exception {
		LinkedList<Integer> list = createLinkedList(indices);
		int lastIndex = list.removeLast();
		return get(list).getFloat(lastIndex);
	}
	
	protected float getFloat(int index) throws Secs2Exception {
		throw new Secs2IrregalDataFormatException("Not Secs2Float");
	}
	
	@Override
	public final double getDouble(int... indices) throws Secs2Exception {
		LinkedList<Integer> list = createLinkedList(indices);
		int lastIndex = list.removeLast();
		return get(list).getDouble(lastIndex);
	}
	
	protected double getDouble(int index) throws Secs2Exception {
		throw new Secs2IrregalDataFormatException("Not Secs2Double");
	}
	
	@Override
	public String toString() {
		
		return new StringBuilder("<")
				.append(secs2Item().symbol())
				.append(" [")
				.append(toStringSize())
				.append("] ")
				.append(toStringValue())
				.append(">")
				.toString();
	}
	
	protected int toStringSize() {
		return size();
	}
	
	protected abstract String toStringValue();
	
	
	@Override
	public String toJson() {
		return "{\"f\":\"" + secs2Item().symbol() +  "\",\"v\":" + toJsonValue() + "}";
	}
	
	abstract protected String toJsonValue();
	
	
}
