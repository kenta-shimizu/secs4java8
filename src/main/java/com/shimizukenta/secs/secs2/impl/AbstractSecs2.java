package com.shimizukenta.secs.secs2.impl;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.secs2.Secs2BuildException;
import com.shimizukenta.secs.secs2.Secs2Exception;
import com.shimizukenta.secs.secs2.Secs2IllegalDataFormatException;
import com.shimizukenta.secs.secs2.Secs2LengthByteOutOfRangeException;

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
		LinkedList<Integer> ll = createLinkedList(indices);
		int lastIndex = ll.removeLast();
		return get(ll).getByte(lastIndex);
	}
	
	protected byte getByte(int index) throws Secs2Exception {
		throw new Secs2IllegalDataFormatException("Not Secs2Byte");
	}
	
	@Override
	public byte[] getBytes(int... indices) throws Secs2Exception {
		return get(indices).getBytes();
	}
	
	@Override
	public byte[] getBytes() throws Secs2Exception {
		throw new Secs2IllegalDataFormatException("Not Secs2Byte");
	}
	
	@Override
	public final boolean getBoolean(int... indices) throws Secs2Exception {
		LinkedList<Integer> ll = createLinkedList(indices);
		int lastIndex = ll.removeLast();
		return get(ll).getBoolean(lastIndex);
	}
	
	protected boolean getBoolean(int index) throws Secs2Exception {
		throw new Secs2IllegalDataFormatException("Not Secs2Boolean");
	}
	
	@Override
	public final int getInt(int... indices) throws Secs2Exception {
		return getNumber(indices).intValue();
	}
	
	@Override
	public final long getLong(int... indices) throws Secs2Exception {
		return getNumber(indices).longValue();
	}
	
	@Override
	public final BigInteger getBigInteger(int... indices) throws Secs2Exception {
		LinkedList<Integer> ll = createLinkedList(indices);
		int lastIndex = ll.removeLast();
		return get(ll).getBigInteger(lastIndex);
	}
	
	protected BigInteger getBigInteger(int index) throws Secs2Exception {
		throw new Secs2IllegalDataFormatException("Not Secs2BigInteger");
	}
	
	@Override
	public final float getFloat(int... indices) throws Secs2Exception {
		return getNumber(indices).floatValue();
	}
	
	@Override
	public final double getDouble(int... indices) throws Secs2Exception {
		return getNumber(indices).doubleValue();
	}
	
	@Override
	public Number getNumber( int... indices ) throws Secs2Exception {
		LinkedList<Integer> ll = createLinkedList(indices);
		int lastIndex = ll.removeLast();
		return get(ll).getNumber(lastIndex);
	}
	
	protected Number getNumber(int index) throws Secs2Exception {
		throw new Secs2IllegalDataFormatException("Not Secs2Number");
	}
	
	
	@Override
	public Optional<Secs2> optional() {
		return Optional.of(this);
	}
	
	public Optional<Secs2> optional(int... indices) {
		return optional(createLinkedList(indices)).map(x -> (Secs2)x);
	}
	
	protected Optional<AbstractSecs2> optional(LinkedList<Integer> list) {
		if ( list.isEmpty() ) {
			return Optional.of(this);
		} else {
			return Optional.empty();
		}
	}
	
	@Override
	public Optional<String> optionalAscii(int... indices) {
		return optional(createLinkedList(indices)).flatMap(AbstractSecs2::optionalAscii);
	}
	
	@Override
	public Optional<String> optionalAscii() {
		return Optional.empty();
	}
	
	@Override
	public Optional<Byte> optionalByte(int... indices) {
		final LinkedList<Integer> ll = createLinkedList(indices);
		final int lastIndex = ll.removeLast();
		return optional(ll).flatMap(x -> x.optionalByte(lastIndex));
	}
	
	protected Optional<Byte> optionalByte(int index) {
		return Optional.empty();
	}
	
	@Override
	public Optional<byte[]> optionalBytes(int... indices) {
		return optional(createLinkedList(indices)).flatMap(AbstractSecs2::optionalBytes);
	}
	
	@Override
	public Optional<byte[]> optionalBytes() {
		return Optional.empty();
	}
	
	@Override
	public Optional<Boolean> optionalBoolean(int... indices) {
		final LinkedList<Integer> ll = createLinkedList(indices);
		final int lastIndex = ll.removeLast();
		return optional(ll).flatMap(x -> x.optionalBoolean(lastIndex));
	}
	
	protected Optional<Boolean> optionalBoolean(int index) {
		return Optional.empty();
	}
	
	@Override
	public OptionalInt optionalInt(int... indices) {
		Optional<Number> opNum = this.optionalNumber(indices);
		return opNum.isPresent() ? OptionalInt.of(opNum.get().intValue()) : OptionalInt.empty();
	}
	
	@Override
	public OptionalLong optionalLong(int... indices) {
		Optional<Number> opNum = this.optionalNumber(indices);
		return opNum.isPresent() ? OptionalLong.of(opNum.get().longValue()) : OptionalLong.empty();
	}
	
	@Override
	public Optional<BigInteger> optionalBigInteger(int... indices) {
		final LinkedList<Integer> ll = createLinkedList(indices);
		final int lastIndex = ll.removeLast();
		return optional(ll).flatMap(x -> x.optionalBigInteger(lastIndex));
	}
	
	protected Optional<BigInteger> optionalBigInteger(int index) {
		return Optional.empty();
	}
	
	@Override
	public OptionalDouble optionalDouble(int... indices) {
		Optional<Number> opNum = this.optionalNumber(indices);
		return opNum.isPresent() ? OptionalDouble.of(opNum.get().doubleValue()) : OptionalDouble.empty();
	}
	
	@Override
	public Optional<Number> optionalNumber(int... indices) {
		final LinkedList<Integer> ll = createLinkedList(indices);
		final int lastIndex = ll.removeLast();
		return optional(ll).flatMap(x -> x.optionalNumber(lastIndex));
	}
	
	protected Optional<Number> optionalNumber(int index) {
		return Optional.empty();
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
