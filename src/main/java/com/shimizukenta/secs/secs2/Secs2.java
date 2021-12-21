package com.shimizukenta.secs.secs2;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Stream;

/**
 * This interface is implementation of SECS-II (SEMI-E5).
 * 
 * <p>
 * Used for build and parse SECS-II data.<br />
 * Support item-type: L, B, BOOLEAN, A, I1, I2, I4, I8, U1, U2, U4, U8, F4, F8<br />
 * </p>
 * <p>
 * Instances of this class are immutable.<br />
 * </p>
 * 
 * @author kenta-shimizu
 *
 */
public interface Secs2 extends Iterable<Secs2> {
	
	/**
	 * Returns is-empty.
	 * 
	 * @return true if 0 bytes body.
	 */
	public boolean isEmpty();
	
	/**
	 * Returns java.util.stream.Stream.
	 * 
	 * @return Stream
	 */
	public Stream<Secs2> stream();
	
	/**
	 * Returns item-count size.
	 * 
	 * @return item-count size. -1 if Secs2 is empty.
	 */
	public int size();
	
	/**
	 * Returns SECS-II-Item-type.
	 * 
	 * @return Secs2Item
	 */
	public Secs2Item secs2Item();
	
	/**
	 * Returns parsed JSON-String.
	 * 
	 * @return json-string
	 */
	public String toJson();
	
	/**
	 * return this
	 * 
	 * @return this
	 */
	public Secs2 get();
	
	/**
	 * Returns nested Secs2 by indices,
	 * Available if type is "A".
	 * 
	 * @param indices
	 * @return Secs2
	 * @throws Secs2Exception if parse failed
	 */
	public Secs2 get(int... indices) throws Secs2Exception;
	
	/**
	 * Returns nested String by indices,
	 * Available if type is "A".
	 * 
	 * @param indices
	 * @return ASCII-String
	 * @throws Secs2Exception if parse failed
	 */
	public String getAscii(int... indices) throws Secs2Exception;
	
	/**
	 * Returns String,
	 * Available String if type is "A".
	 * 
	 * @return ASCII-String
	 * @throws Secs2Exception if parse failed
	 */
	public String getAscii() throws Secs2Exception;
	
	/**
	 * Returns nested byte-value by indices,
	 * Available if type is "B".
	 * 
	 * @param indices
	 * @return byte-value
	 * @throws Secs2Exception if parse failed
	 */
	public byte getByte( int... indices ) throws Secs2Exception;
	
	/**
	 * Returns nested boolean-value by indices,
	 * Available if type is "BOOLEAN".
	 * 
	 * @param indices
	 * @return boolean-value
	 * @throws Secs2Exception if parse failed
	 */
	public boolean getBoolean( int... indices ) throws Secs2Exception;
	
	/**
	 * Returns nested Numeric-value by indices,
	 * Available if type is "I1","I2","I4","I8","F4","F8","U1","U2","U4","U8"
	 * 
	 * @param indices
	 * @return (int)value
	 * @throws Secs2Exception if parse failed
	 */
	public int getInt( int... indices ) throws Secs2Exception;
	
	/**
	 * Returns nested Numeric-value by indices,
	 * Available if type is "I1","I2","I4","I8","F4","F8","U1","U2","U4","U8"
	 * 
	 * @param indices
	 * @return (long)value
	 * @throws Secs2Exception if parse failed
	 */
	public long getLong( int... indices ) throws Secs2Exception;
	
	/**
	 * Returns nested Numeric-value by indices,
	 * Available if type is "I1","I2","I4","I8","F4","F8","U1","U2","U4","U8"
	 * 
	 * @param indices
	 * @return BigInteger.valueOf(value)
	 * @throws Secs2Exception if parse failed
	 */
	public BigInteger getBigInteger( int... indices ) throws Secs2Exception;
	
	/**
	 * Returns nested Numeric-value by indices,
	 * Available if type is "I1","I2","I4","I8","F4","F8","U1","U2","U4","U8"
	 * 
	 * @param indices
	 * @return (float)value
	 * @throws Secs2Exception if parse failed
	 */
	public float getFloat( int... indices ) throws Secs2Exception;
	
	/**
	 * Returns nested Numeric-value by indices,
	 * Available if type is "I1","I2","I4","I8","F4","F8","U1","U2","U4","U8"
	 * 
	 * @param indices
	 * @return (double)value
	 * @throws Secs2Exception if parse failed
	 */
	public double getDouble( int... indices ) throws Secs2Exception;

	/**
	 * Returns nested Numeric-value by indices,
	 * Available if type is "I1","I2","I4","I8","F4","F8","U1","U2","U4","U8"
	 * 
	 * @param indices
	 * @return (Number)value
	 * @throws Secs2Exception if parse failed
	 */
	public Number getNumber( int... indices ) throws Secs2Exception;
	
	
	/* builder */
	
	/**
	 * Returns builder instance.
	 * 
	 * @return Secs2Builder.getInstance()
	 */
	public static Secs2Builder getBuilder() {
		return Secs2Builders.getInstance();
	}
	
	/**
	 * Returns SECS-II-0-bytes.
	 * 
	 * @return Empty-Secs2
	 */
	public static Secs2RawBytes empty() {
		return Secs2Builders.empty();
	}
	
	/**
	 * Equivalent to {@code getBuilder().raw(bs)}.
	 * 
	 * @param bs
	 * @return Secs2RawBytes
	 */
	public static Secs2RawBytes raw(byte[] bs) {
		return Secs2Builders.raw(bs);
	}
	
	/**
	 * Equivalent to {@code getBuilder().list()}.
	 * 
	 * @return &lt;L[0] &gt;
	 */
	public static Secs2List list() {
		return Secs2Builders.list();
	}
	
	/**
	 * Equivalent to {@code getBuilder().list(values)}.
	 * 
	 * @param values
	 * @return &lt;L[n] values&gt;
	 */
	public static Secs2List list(Secs2... values) {
		return Secs2Builders.list(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().list(values)}.
	 * 
	 * @param values
	 * @return &lt;L[n] values&gt;
	 */
	public static Secs2List list(List<? extends Secs2> values) {
		return Secs2Builders.list(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().ascii(ascii)}.
	 * 
	 * @param ascii
	 * @return &lt;A[n] "ascii"&gt;
	 */
	public static Secs2Ascii ascii(CharSequence ascii) {
		return Secs2Builders.ascii(ascii);
	}
	
	/**
	 * Equivalent to {@code getBuilder().binary()}.
	 * 
	 * @return &lt;B[0] &gt;
	 */
	public static Secs2Binary binary() {
		return Secs2Builders.binary();
	}
	
	/**
	 * Equivalent to {@code getBuilder().binary(bs)}.
	 * 
	 * @param bs
	 * @return &lt;B[n] bs&gt;
	 */
	public static Secs2Binary binary(byte... bs) {
		return Secs2Builders.binary(bs);
	}
	
	/**
	 * Equivalent to {@code getBuilder().binary(bs)}.
	 * 
	 * @param bs
	 * @return &lt;B[n] bs&gt;
	 */
	public static Secs2Binary binary(List<Byte> bs) {
		return Secs2Builders.binary(bs);
	}
	
	/**
	 * Equivalent to {@code getBuilder().bool()}.
	 * 
	 * @return &lt;BOOLEAN[0] &gt;
	 */
	public static Secs2Boolean bool() {
		return Secs2Builders.bool();
	}
	
	/**
	 * Equivalent to {@code getBuilder().bool(bools)}.
	 * 
	 * @param bools
	 * @return &lt;BOOLEAN[n] bools&gt;
	 */
	public static Secs2Boolean bool(boolean... bools) {
		return Secs2Builders.bool(bools);
	}
	
	/**
	 * Equivalent to {@code getBuilder().bool(bools)}.
	 * 
	 * @param bools
	 * @return &lt;BOOLEAN[n] bools&gt;
	 */
	public static Secs2Boolean bool(List<Boolean> bools) {
		return Secs2Builders.bool(bools);
	}
	
	/**
	 * Equivalent to {@code getBuilder().int1()}.
	 * 
	 * @return &lt;I1[0] &gt;
	 */
	public static Secs2Int1 int1() {
		return Secs2Builders.int1();
	}
	
	/**
	 * Equivalent to {@code getBuilder().int1(values)}.
	 * 
	 * @param values
	 * @return &lt;I1[n] values&gt;
	 */
	public static Secs2Int1 int1(int... values) {
		return Secs2Builders.int1(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().int1(values)}.
	 * 
	 * @param values
	 * @return &lt;I1[n] values&gt;
	 */
	public static Secs2Int1 int1(long... values) {
		return Secs2Builders.int1(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().int1(values)}.
	 * 
	 * @param values
	 * @return &lt;I1[n] values&gt;
	 */
	public static Secs2Int1 int1(BigInteger... values) {
		return Secs2Builders.int1(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().int1(values)}.
	 * 
	 * @param values
	 * @return &lt;I1[n] values&gt;
	 */
	public static Secs2Int1 int1(List<? extends Number> values) {
		return Secs2Builders.int1(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().int2()}.
	 * 
	 * @return &lt;I2[0] &gt;
	 */
	public static Secs2Int2 int2() {
		return Secs2Builders.int2();
	}
	
	/**
	 * Equivalent to {@code getBuilder().int2(values)}.
	 * 
	 * @param values
	 * @return &lt;I2[n] values&gt;
	 */
	public static Secs2Int2 int2(int... values) {
		return Secs2Builders.int2(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().int2(values)}.
	 * 
	 * @param values
	 * @return &lt;I2[n] values&gt;
	 */
	public static Secs2Int2 int2(long... values) {
		return Secs2Builders.int2(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().int2(values)}.
	 * 
	 * @param values
	 * @return &lt;I2[n] values&gt;
	 */
	public static Secs2Int2 int2(BigInteger... values) {
		return Secs2Builders.int2(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().int2(values)}.
	 * 
	 * @param values
	 * @return &lt;I2[n] values&gt;
	 */
	public static Secs2Int2 int2(List<? extends Number> values) {
		return Secs2Builders.int2(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().int4()}.
	 * 
	 * @return &lt;I4[0] &gt;
	 */
	public static Secs2Int4 int4() {
		return Secs2Builders.int4();
	}
	
	/**
	 * Equivalent to {@code getBuilder().int4(values)}.
	 * 
	 * @param values
	 * @return &lt;I4[n] values&gt;
	 */
	public static Secs2Int4 int4(int... values) {
		return Secs2Builders.int4(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().int4(values)}.
	 * 
	 * @param values
	 * @return &lt;I4[n] values&gt;
	 */
	public static Secs2Int4 int4(long... values) {
		return Secs2Builders.int4(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().int4(values)}.
	 * 
	 * @param values
	 * @return &lt;I4[n] values&gt;
	 */
	public static Secs2Int4 int4(BigInteger... values) {
		return Secs2Builders.int4(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().int4(values)}.
	 * 
	 * @param values
	 * @return &lt;I4[n] values&gt;
	 */
	public static Secs2Int4 int4(List<? extends Number> values) {
		return Secs2Builders.int4(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().int8()}.
	 * 
	 * @return &lt;I8[0] &gt;
	 */
	public static Secs2Int8 int8() {
		return Secs2Builders.int8();
	}
	
	/**
	 * Equivalent to {@code getBuilder().int8(values)}.
	 * 
	 * @param values
	 * @return &lt;I8[n] values&gt;
	 */
	public static Secs2Int8 int8(int... values) {
		return Secs2Builders.int8(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().int8(values)}.
	 * 
	 * @param values
	 * @return &lt;I8[n] values&gt;
	 */
	public static Secs2Int8 int8(long... values) {
		return Secs2Builders.int8(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().int8(values)}.
	 * 
	 * @param values
	 * @return &lt;I8[n] values&gt;
	 */
	public static Secs2Int8 int8(BigInteger... values) {
		return Secs2Builders.int8(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().int8(values)}.
	 * 
	 * @param values
	 * @return &lt;I8[n] values&gt;
	 */
	public static Secs2Int8 int8(List<? extends Number> values) {
		return Secs2Builders.int8(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().uint1()}.
	 * 
	 * @return &lt;U1[0] &gt;
	 */
	public static Secs2Uint1 uint1() {
		return Secs2Builders.uint1();
	}
	
	/**
	 * Equivalent to {@code getBuilder().uint1(values)}.
	 * 
	 * @param values
	 * @return &lt;U1[n] values&gt;
	 */
	public static Secs2Uint1 uint1(int... values) {
		return Secs2Builders.uint1(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().uint1(values)}.
	 * 
	 * @param values
	 * @return &lt;U1[n] values&gt;
	 */
	public static Secs2Uint1 uint1(long... values) {
		return Secs2Builders.uint1(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().uint1(values)}.
	 * 
	 * @param values
	 * @return &lt;U1[n] values&gt;
	 */
	public static Secs2Uint1 uint1(BigInteger... values) {
		return Secs2Builders.uint1(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().uint1(values)}.
	 * 
	 * @param values
	 * @return &lt;U1[n] values&gt;
	 */
	public static Secs2Uint1 uint1(List<? extends Number> values) {
		return Secs2Builders.uint1(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().uint2()}.
	 * 
	 * @return &lt;U2[0] &gt;
	 */
	public static Secs2Uint2 uint2() {
		return Secs2Builders.uint2();
	}
	
	/**
	 * Equivalent to {@code getBuilder().uint2(values)}.
	 * 
	 * @param values
	 * @return &lt;U2[n] values&gt;
	 */
	public static Secs2Uint2 uint2(int... values) {
		return Secs2Builders.uint2(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().uint2(values)}.
	 * 
	 * @param values
	 * @return &lt;U2[n] values&gt;
	 */
	public static Secs2Uint2 uint2(long... values) {
		return Secs2Builders.uint2(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().uint2(values)}.
	 * 
	 * @param values
	 * @return &lt;U2[n] values&gt;
	 */
	public static Secs2Uint2 uint2(BigInteger... values) {
		return Secs2Builders.uint2(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().uint2(values)}.
	 * 
	 * @param values
	 * @return &lt;U2[n] values&gt;
	 */
	public static Secs2Uint2 uint2(List<? extends Number> values) {
		return Secs2Builders.uint2(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().uint4()}.
	 * 
	 * @return &lt;U4[0] &gt;
	 */
	public static Secs2Uint4 uint4() {
		return Secs2Builders.uint4();
	}
	
	/**
	 * Equivalent to {@code getBuilder().uint4(values)}.
	 * 
	 * @param values
	 * @return &lt;U4[n] values&gt;
	 */
	public static Secs2Uint4 uint4(int... values) {
		return Secs2Builders.uint4(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().uint4(values)}.
	 * 
	 * @param values
	 * @return &lt;U4[n] values&gt;
	 */
	public static Secs2Uint4 uint4(long... values) {
		return Secs2Builders.uint4(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().uint4(values)}.
	 * 
	 * @param values
	 * @return &lt;U4[n] values&gt;
	 */
	public static Secs2Uint4 uint4(BigInteger... values) {
		return Secs2Builders.uint4(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().uint4(values)}.
	 * 
	 * @param values
	 * @return &lt;U4[n] values&gt;
	 */
	public static Secs2Uint4 uint4(List<? extends Number> values) {
		return Secs2Builders.uint4(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().uint8()}.
	 * 
	 * @return &lt;U8[0] &gt;
	 */
	public static Secs2Uint8 uint8() {
		return Secs2Builders.uint8();
	}
	
	/**
	 * Equivalent to {@code getBuilder().uint8(values)}.
	 * 
	 * @param values
	 * @return &lt;U8[n] values&gt;
	 */
	public static Secs2Uint8 uint8(int... values) {
		return Secs2Builders.uint8(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().uint8(values)}.
	 * 
	 * @param values
	 * @return &lt;U8[n] values&gt;
	 */
	public static Secs2Uint8 uint8(long... values) {
		return Secs2Builders.uint8(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().uint8(values)}.
	 * 
	 * @param values
	 * @return &lt;U8[n] values&gt;
	 */
	public static Secs2Uint8 uint8(BigInteger... values) {
		return Secs2Builders.uint8(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().uint8(values)}.
	 * 
	 * @param values
	 * @return &lt;U8[n] values&gt;
	 */
	public static Secs2Uint8 uint8(List<? extends Number> values) {
		return Secs2Builders.uint8(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().float4()}.
	 * 
	 * @return &lt;F4[0] &gt;
	 */
	public static Secs2Float4 float4() {
		return Secs2Builders.float4();
	}
	
	/**
	 * Equivalent to {@code getBuilder().float4(values)}.
	 * 
	 * @param values
	 * @return &lt;F4[n] values&gt;
	 */
	public static Secs2Float4 float4(float... values) {
		return Secs2Builders.float4(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().float4(values)}.
	 * 
	 * @param values
	 * @return &lt;F4[n] values&gt;
	 */
	public static Secs2Float4 float4(List<? extends Number> values) {
		return Secs2Builders.float4(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().float8()}.
	 * 
	 * @return &lt;F8[0] &gt;
	 */
	public static Secs2Float8 float8() {
		return Secs2Builders.float8();
	}
	
	/**
	 * Equivalent to {@code getBuilder().float8(values)}.
	 * 
	 * @param values
	 * @return &lt;F8[n] values&gt;
	 */
	public static Secs2Float8 float8(double... values) {
		return Secs2Builders.float8(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().float8(values)}.
	 * 
	 * @param values
	 * @return &lt;F8[n] values&gt;
	 */
	public static Secs2Float8 float8(List<? extends Number> values) {
		return Secs2Builders.float8(values);
	}

}
