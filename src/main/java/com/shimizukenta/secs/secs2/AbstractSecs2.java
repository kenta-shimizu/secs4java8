package com.shimizukenta.secs.secs2;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public abstract class AbstractSecs2 implements Secs2, Serializable {
	
	private static final long serialVersionUID = 7168919889159900080L;

	public AbstractSecs2() {
		/* Nothing */
	}
	
	abstract protected void putBytesPack(Secs2BytesPackBuilder builder) throws Secs2BuildException;
	
	protected void putHeadAndBodyBytesToBytesPack(Secs2BytesPackBuilder builder, byte[] body) throws Secs2BuildException {
		putHeaderBytesToBytesPack(builder, body.length);
		builder.put(body);
	}
	
	protected void putHeaderBytesToBytesPack(Secs2BytesPackBuilder builder, int length) throws Secs2BuildException {
		
		if ( length > 0xFFFFFF || length < 0 ) {
			throw new Secs2LengthByteOutOfRangeException("length: " + length);
		}
		
		byte b = secs2Item().code();
		
		if ( length > 0xFFFF ) {
			
			builder.put(new byte[] {
					(byte)(b | 0x3),
					(byte)(length >> 16),
					(byte)(length >> 8),
					(byte)(length)
			});
			
		} else if ( length > 0xFF) {
			
			builder.put(new byte[] {
					(byte)(b | 0x2),
					(byte)(length >> 8),
					(byte)(length)
			});
			
		} else {
			
			builder.put(new byte[] {
					(byte)(b | 0x1),
					(byte)(length)
			});
		}
	}
	
	private static LinkedList<Integer> createLinkedList(int... indices) {
		LinkedList<Integer> ll = new LinkedList<>();
		IntStream.of(indices).boxed().forEach(ll::add);
		return ll;
	}
	
	@Override
	public boolean isEmpty() {
		return false;
	}
	
	@Override
	public Stream<Secs2> stream() {
		return Stream.empty();
	}

	@Override
	public Iterator<Secs2> iterator() {
		return Collections.emptyIterator();
	}

	@Override
	public Secs2 get() {
		return this;
	}
	
	@Override
	public final Secs2 get(int... indices) throws Secs2Exception {
		return get(createLinkedList(indices));
	}
	
	protected AbstractSecs2 get(LinkedList<Integer> list) throws Secs2Exception {
		if ( list.isEmpty() ) {
			return this;
		} else {
			throw new Secs2IllegalDataFormatException("Not Secs2List");
		}
	}
	
	@Override
	public final String getAscii(int... indices) throws Secs2Exception {
		return get(indices).getAscii();
	}
	
	@Override
	public String getAscii() throws Secs2Exception {
		throw new Secs2IllegalDataFormatException("Not Secs2Ascii");
	}
	
	@Override
	public final byte getByte(int... indices) throws Secs2Exception {
		LinkedList<Integer> list = createLinkedList(indices);
		int lastIndex = list.removeLast();
		return get(list).getByte(lastIndex);
	}
	
	protected byte getByte(int index) throws Secs2Exception {
		throw new Secs2IllegalDataFormatException("Not Secs2Byte");
	}
	
	@Override
	public final boolean getBoolean(int... indices) throws Secs2Exception {
		LinkedList<Integer> list = createLinkedList(indices);
		int lastIndex = list.removeLast();
		return get(list).getBoolean(lastIndex);
	}
	
	protected boolean getBoolean(int index) throws Secs2Exception {
		throw new Secs2IllegalDataFormatException("Not Secs2Boolean");
	}
	
	@Override
	public final int getInt(int... indices) throws Secs2Exception {
		LinkedList<Integer> list = createLinkedList(indices);
		int lastIndex = list.removeLast();
		return get(list).getInt(lastIndex);
	}
	
	protected int getInt(int index) throws Secs2Exception {
		throw new Secs2IllegalDataFormatException("Not Secs2Number");
	}
	
	@Override
	public final long getLong(int... indices) throws Secs2Exception {
		LinkedList<Integer> list = createLinkedList(indices);
		int lastIndex = list.removeLast();
		return get(list).getLong(lastIndex);
	}
	
	protected long getLong(int index) throws Secs2Exception {
		throw new Secs2IllegalDataFormatException("Not Secs2Number");
	}
	
	@Override
	public final BigInteger getBigInteger(int... indices) throws Secs2Exception {
		LinkedList<Integer> list = createLinkedList(indices);
		int lastIndex = list.removeLast();
		return get(list).getBigInteger(lastIndex);
	}
	
	protected BigInteger getBigInteger(int index) throws Secs2Exception {
		throw new Secs2IllegalDataFormatException("Not Secs2Number");
	}
	
	@Override
	public final float getFloat(int... indices) throws Secs2Exception {
		LinkedList<Integer> list = createLinkedList(indices);
		int lastIndex = list.removeLast();
		return get(list).getFloat(lastIndex);
	}
	
	protected float getFloat(int index) throws Secs2Exception {
		throw new Secs2IllegalDataFormatException("Not Secs2Float");
	}
	
	@Override
	public final double getDouble(int... indices) throws Secs2Exception {
		LinkedList<Integer> list = createLinkedList(indices);
		int lastIndex = list.removeLast();
		return get(list).getDouble(lastIndex);
	}
	
	protected double getDouble(int index) throws Secs2Exception {
		throw new Secs2IllegalDataFormatException("Not Secs2Double");
	}
	
	@Override
	public Number getNumber( int... indices ) throws Secs2Exception {
		LinkedList<Integer> list = createLinkedList(indices);
		int lastIndex = list.removeLast();
		return get(list).getNumber(lastIndex);
	}
	
	protected Number getNumber(int index) throws Secs2Exception {
		throw new Secs2IllegalDataFormatException("Not Secs2Number");
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
	
	@Override
	public int hashCode() {
		return toJson().hashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		if (o != null && (o instanceof AbstractSecs2)) {
			return ((AbstractSecs2)o).toJson().equals(toJson());
		}
		return false;
	}
	
}
