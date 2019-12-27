package secs.secs2;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public abstract class Secs2 implements Iterable<Secs2> {

	public Secs2() {
		/* Nothing */
	}
	
	public boolean isEmpty() {
		return false;
	}
	
	@Override
	public Iterator<Secs2> iterator() {
		return Collections.emptyIterator();
	}
	
	public Stream<Secs2> stream() {
		return Stream.empty();
	}
	
	/**
	 * 
	 * @return item-count size
	 */
	public abstract int size();
	
	/**
	 * 
	 * @return Secs2-bytes
	 * @throws Secs2Exception
	 */
	public abstract byte[] secs2Bytes() throws Secs2Exception;
	
	private static LinkedList<Integer> createLinkedList( int... indices ) {
		return IntStream.of(indices).boxed()
				.collect(Collectors.toCollection(LinkedList<Integer>::new));
	}
	
	protected static byte[] createHeadBytes( Secs2Item s2i, int size ) throws Secs2Exception {
		
		if ( size > 0xFFFFFF || size < 0 ) {
			throw new Secs2LengthByteOutOfRangeException("length: " + size);
		}
		
		try (
				ByteArrayOutputStream st = new ByteArrayOutputStream();
				) {
			
			byte c = s2i.code();
			
			if ( size > 0xFFFF ) {
				
				st.write( c | 0x3 );
				st.write(size >> 16);
				st.write(size >> 8);
				st.write(size);
				
			} else if ( size > 0xFF) {
				
				st.write( c | 0x2 );
				st.write(size >> 8);
				st.write(size);
				
			} else {
				
				st.write( c | 0x1 );
				st.write(size);
			}
			
			return st.toByteArray();
		}
		catch ( IOException e ) {
			throw new Secs2Exception(e);
		}
	}
	
	public Secs2 get() {
		return this;
	}
	
	public final Secs2 get( int... indices ) throws Secs2Exception {
		return get(createLinkedList(indices));
	}
	
	protected Secs2 get( LinkedList<Integer> list ) throws Secs2Exception {
		if ( list.isEmpty() ) {
			return get();
		} else {
			throw new Secs2IrregalDataFormatException("Not Secs2List");
		}
	}
	
	public final String getAscii(int... indices) throws Secs2Exception {
		return get(indices).getAscii();
	}
	
	public String getAscii() throws Secs2Exception {
		throw new Secs2IrregalDataFormatException("Not Secs2Ascii");
	}
	
	public final byte getByte( int... indices ) throws Secs2Exception {
		LinkedList<Integer> list = createLinkedList(indices);
		int lastIndex = list.removeLast();
		return get(list).getByte(lastIndex);
	}
	
	protected byte getByte( int index ) throws Secs2Exception {
		throw new Secs2IrregalDataFormatException("Not Secs2Byte");
	}
	
	public final boolean getBoolean( int... indices ) throws Secs2Exception {
		LinkedList<Integer> list = createLinkedList(indices);
		int lastIndex = list.removeLast();
		return get(list).getBoolean(lastIndex);
	}
	
	protected boolean getBoolean( int index ) throws Secs2Exception {
		throw new Secs2IrregalDataFormatException("Not Secs2Boolean");
	}
	
	public final int getInt( int... indices ) throws Secs2Exception {
		LinkedList<Integer> list = createLinkedList(indices);
		int lastIndex = list.removeLast();
		return get(list).getInt(lastIndex);
	}
	
	protected int getInt(int index) throws Secs2Exception {
		throw new Secs2IrregalDataFormatException("Not Secs2Number");
	}
	
	public final long getLong( int... indices ) throws Secs2Exception {
		LinkedList<Integer> list = createLinkedList(indices);
		int lastIndex = list.removeLast();
		return get(list).getLong(lastIndex);
	}
	
	protected long getLong(int index) throws Secs2Exception {
		throw new Secs2IrregalDataFormatException("Not Secs2Number");
	}
	
	public final BigInteger getBigInteger( int... indices ) throws Secs2Exception {
		LinkedList<Integer> list = createLinkedList(indices);
		int lastIndex = list.removeLast();
		return get(list).getBigInteger(lastIndex);
	}
	
	protected BigInteger getBigInteger(int index) throws Secs2Exception {
		throw new Secs2IrregalDataFormatException("Not Secs2Number");
	}
	
	public final float getFloat( int... indices ) throws Secs2Exception {
		LinkedList<Integer> list = createLinkedList(indices);
		int lastIndex = list.removeLast();
		return get(list).getFloat(lastIndex);
	}
	
	protected float getFloat(int index) throws Secs2Exception {
		throw new Secs2IrregalDataFormatException("Not Secs2Float");
	}
	
	public final double getDouble( int... indices1 ) throws Secs2Exception {
		LinkedList<Integer> list = createLinkedList(indices1);
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
	
	public abstract Secs2Item secs2Item();
	
	protected abstract String toStringValue();
	
	
	public String parseToJson() {
		return "{\"f\":\"" + secs2Item().symbol() +  "\",\"v\":" + parsedJsonValue() + "}";
	}
	
	abstract protected String parsedJsonValue();
	
	
	/* builder */
	public static Secs2 empty() {
		return new Secs2BytesParser();
	}
	
	public static Secs2 parse(byte[] bs) {
		return new Secs2BytesParser(bs);
	}
	
	public static Secs2 list() {
		return new Secs2List();
	}
	
	public static Secs2 list(Secs2... values) {
		return new Secs2List(values);
	}
	
	public static Secs2 list(List<? extends Secs2> values) {
		return new Secs2List(values);
	}
	
	public static Secs2 ascii(CharSequence cs) {
		return new Secs2Ascii(cs);
	}
	
	public static Secs2 binary() {
		return new Secs2Binary();
	}
	
	public static Secs2 binary(byte... bs) {
		return new Secs2Binary(bs);
	}
	
	public static Secs2 binary(List<Byte> bs) {
		return new Secs2Binary(bs);
	}
	
	public static Secs2 bool() {
		return new Secs2Boolean();
	}
	
	public static Secs2 bool(boolean... bools) {
		return new Secs2Boolean(bools);
	}
	
	public static Secs2 bool(List<Boolean> bools) {
		return new Secs2Boolean(bools);
	}
	
	public static Secs2 int1() {
		return new Secs2Int1();
	}
	
	public static Secs2 int1(int... values) {
		return new Secs2Int1(values);
	}
	
	public static Secs2 int1(long... values) {
		return new Secs2Int1(values);
	}
	
	public static Secs2 int1(BigInteger... values) {
		return new Secs2Int1(values);
	}

	public static Secs2 int1(List<? extends Number> values) {
		return new Secs2Int1(values);
	}
	
	public static Secs2 int2() {
		return new Secs2Int2();
	}
	
	public static Secs2 int2(int... values) {
		return new Secs2Int2(values);
	}
	
	public static Secs2 int2(long... values) {
		return new Secs2Int2(values);
	}
	
	public static Secs2 int2(BigInteger... values) {
		return new Secs2Int2(values);
	}

	public static Secs2 int2(List<? extends Number> values) {
		return new Secs2Int2(values);
	}
	
	public static Secs2 int4() {
		return new Secs2Int4();
	}
	
	public static Secs2 int4(int... values) {
		return new Secs2Int4(values);
	}
	
	public static Secs2 int4(long... values) {
		return new Secs2Int4(values);
	}
	
	public static Secs2 int4(BigInteger... values) {
		return new Secs2Int4(values);
	}

	public static Secs2 int4(List<? extends Number> values) {
		return new Secs2Int4(values);
	}
	
	public static Secs2 int8() {
		return new Secs2Int8();
	}
	
	public static Secs2 int8(int... values) {
		return new Secs2Int8(values);
	}
	
	public static Secs2 int8(long... values) {
		return new Secs2Int8(values);
	}
	
	public static Secs2 int8(BigInteger... values) {
		return new Secs2Int8(values);
	}

	public static Secs2 int8(List<? extends Number> values) {
		return new Secs2Int8(values);
	}
	
	public static Secs2 uint1() {
		return new Secs2Uint1();
	}
	
	public static Secs2 uint1(int... values) {
		return new Secs2Uint1(values);
	}
	
	public static Secs2 uint1(long... values) {
		return new Secs2Uint1(values);
	}
	
	public static Secs2 uint1(BigInteger... values) {
		return new Secs2Uint1(values);
	}

	public static Secs2 uint1(List<? extends Number> values) {
		return new Secs2Uint1(values);
	}

	public static Secs2 uint2() {
		return new Secs2Uint2();
	}
	
	public static Secs2 uint2(int... values) {
		return new Secs2Uint2(values);
	}
	
	public static Secs2 uint2(long... values) {
		return new Secs2Uint2(values);
	}
	
	public static Secs2 uint2(BigInteger... values) {
		return new Secs2Uint2(values);
	}

	public static Secs2 uint2(List<? extends Number> values) {
		return new Secs2Uint2(values);
	}

	public static Secs2 uint4() {
		return new Secs2Uint4();
	}
	
	public static Secs2 uint4(int... values) {
		return new Secs2Uint4(values);
	}
	
	public static Secs2 uint4(long... values) {
		return new Secs2Uint4(values);
	}
	
	public static Secs2 uint4(BigInteger... values) {
		return new Secs2Uint4(values);
	}

	public static Secs2 uint4(List<? extends Number> values) {
		return new Secs2Uint4(values);
	}

	public static Secs2 uint8() {
		return new Secs2Uint8();
	}
	
	public static Secs2 uint8(int... values) {
		return new Secs2Uint8(values);
	}
	
	public static Secs2 uint8(long... values) {
		return new Secs2Uint8(values);
	}
	
	public static Secs2 uint8(BigInteger... values) {
		return new Secs2Uint8(values);
	}

	public static Secs2 uint8(List<? extends Number> values) {
		return new Secs2Uint8(values);
	}
	
	public static Secs2 float4() {
		return new Secs2Float4();
	}
	
	public static Secs2 float4(float... values) {
		return new Secs2Float4(values);
	}
	
	public static Secs2 float4(List<? extends Number> values) {
		return new Secs2Float4(values);
	}
	
	public static Secs2 float8() {
		return new Secs2Float8();
	}
	
	public static Secs2 float8(double... values) {
		return new Secs2Float8(values);
	}
	
	public static Secs2 float8(List<? extends Number> values) {
		return new Secs2Float8(values);
	}
	
}
