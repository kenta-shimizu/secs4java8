package secs.secs2;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public abstract class Secs2 implements Iterable<Secs2> {

	public Secs2() {
		/* Nothing */
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
	
	public final byte getByte( int... indices_plus1 ) throws Secs2Exception {
		LinkedList<Integer> list = createLinkedList(indices_plus1);
		int lastIndex = list.removeLast();
		return get(list).getByte(lastIndex);
	}
	
	protected byte getByte( int index ) throws Secs2Exception {
		throw new Secs2IrregalDataFormatException("Not Secs2Byte");
	}
	
	public final boolean getBoolean( int... indices_plus1 ) throws Secs2Exception {
		LinkedList<Integer> list = createLinkedList(indices_plus1);
		int lastIndex = list.removeLast();
		return get(list).getBoolean(lastIndex);
	}
	
	protected boolean getBoolean( int index ) throws Secs2Exception {
		throw new Secs2IrregalDataFormatException("Not Secs2Boolean");
	}
	
	public final int getInt( int... indices_plus1 ) throws Secs2Exception {
		LinkedList<Integer> list = createLinkedList(indices_plus1);
		int lastIndex = list.removeLast();
		return get(list).getInt(lastIndex);
	}
	
	protected int getInt(int index) throws Secs2Exception {
		throw new Secs2IrregalDataFormatException("Not Secs2Number");
	}
	
	public final long getLong( int... indices_plus1 ) throws Secs2Exception {
		LinkedList<Integer> list = createLinkedList(indices_plus1);
		int lastIndex = list.removeLast();
		return get(list).getLong(lastIndex);
	}
	
	protected long getLong(int index) throws Secs2Exception {
		throw new Secs2IrregalDataFormatException("Not Secs2Number");
	}
	
	public final BigInteger getBigInteger( int... indices_plus1 ) throws Secs2Exception {
		LinkedList<Integer> list = createLinkedList(indices_plus1);
		int lastIndex = list.removeLast();
		return get(list).getBigInteger(lastIndex);
	}
	
	protected BigInteger getBigInteger(int index) throws Secs2Exception {
		throw new Secs2IrregalDataFormatException("Not Secs2Number");
	}
	
	public final float getFloat( int... indices_plus1 ) throws Secs2Exception {
		LinkedList<Integer> list = createLinkedList(indices_plus1);
		int lastIndex = list.removeLast();
		return get(list).getFloat(lastIndex);
	}
	
	protected float getFloat(int index) throws Secs2Exception {
		throw new Secs2IrregalDataFormatException("Not Secs2Float");
	}
	
	public final double getDouble( int... indices_plus1 ) throws Secs2Exception {
		LinkedList<Integer> list = createLinkedList(indices_plus1);
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


}
