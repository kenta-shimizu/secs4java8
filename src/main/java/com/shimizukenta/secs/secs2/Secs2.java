package com.shimizukenta.secs.secs2;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Stream;

/**
 * This interface is implementation of SECS-II (SEMI-E5)<br />
 * Used for build and parse SECS-II data.<br />
 * Support item-type: L, B, BOOLEAN, A, I1, I2, I4, I8, U1, U2, U4, U8, F4, F8<br />
 * Instances of this class are immutable.
 * 
 * @author kenta-shimizu
 *
 */
public interface Secs2 extends Iterable<Secs2> {
	
	/**
	 * 
	 * @return true if 0 bytes body.
	 */
	public boolean isEmpty();
	
	public Stream<Secs2> stream();
	
	/**
	 * 
	 * @return item-count size. -1 if Secs2 is empty.
	 */
	public int size();
	
	public Secs2Item secs2Item();
	
	public String toJson();
	
	/**
	 * return this
	 * 
	 * @return this
	 */
	public Secs2 get();
	
	/**
	 * get nested Secs2 by indices
	 * 
	 * @param indices
	 * @return Secs2
	 * @throws Secs2Exception
	 */
	public Secs2 get(int... indices) throws Secs2Exception;
	
	/**
	 * get nested String by indices.<br />
	 * get if type is "A".
	 * 
	 * @param indices
	 * @return ASCII-String
	 * @throws Secs2Exception
	 */
	public String getAscii(int... indices) throws Secs2Exception;
	
	/**
	 * get String if type is "A".
	 * 
	 * @return ASCII-String
	 * @throws Secs2Exception
	 */
	public String getAscii() throws Secs2Exception;
	
	/**
	 * get nested byte-value by indices.<br />
	 * get if type is "B".
	 * 
	 * @param indices
	 * @return byte-value
	 * @throws Secs2Exception
	 */
	public byte getByte( int... indices ) throws Secs2Exception;
	
	/**
	 * get nested boolean-value by indices.<br />
	 * get if type is "BOOLEAN".
	 * 
	 * @param indices
	 * @return boolean-value
	 * @throws Secs2Exception
	 */
	public boolean getBoolean( int... indices ) throws Secs2Exception;
	
	/**
	 * get nested Numeric-value by indices.<br />
	 * get if type is "I1","I2","I4","I8","F4","F8","U1","U2","U4","U8"
	 * 
	 * @param indices
	 * @return (int)value
	 * @throws Secs2Exception
	 */
	public int getInt( int... indices ) throws Secs2Exception;
	
	/**
	 * get nested Numeric-value by indices.<br />
	 * get if type is "I1","I2","I4","I8","F4","F8","U1","U2","U4","U8"
	 * 
	 * @param indices
	 * @return (long)value
	 * @throws Secs2Exception
	 */
	public long getLong( int... indices ) throws Secs2Exception;
	
	/**
	 * get nested Numeric-value by indices.<br />
	 * get if type is "I1","I2","I4","I8","F4","F8","U1","U2","U4","U8"
	 * 
	 * @param indices
	 * @return BigInteger.valueOf(value)
	 * @throws Secs2Exception
	 */
	public BigInteger getBigInteger( int... indices ) throws Secs2Exception;
	
	/**
	 * get nested Numeric-value by indices.<br />
	 * get if type is "I1","I2","I4","I8","F4","F8","U1","U2","U4","U8"
	 * 
	 * @param indices
	 * @return (float)value
	 * @throws Secs2Exception
	 */
	public float getFloat( int... indices ) throws Secs2Exception;
	
	/**
	 * get nested Numeric-value by indices.<br />
	 * get if type is "I1","I2","I4","I8","F4","F8","U1","U2","U4","U8"
	 * 
	 * @param indices
	 * @return (double)value
	 * @throws Secs2Exception
	 */
	public double getDouble( int... indices1 ) throws Secs2Exception;

	
	/* builder */
	
	/**
	 * 
	 * @return Secs2Builder.getInstance()
	 */
	public static Secs2Builder getBuilder() {
		return Secs2Builder.getInstance();
	}
	
	/**
	 * 
	 * @return Empty-Secs2
	 */
	public static Secs2RawBytes empty() {
		return getBuilder().empty();
	}
	
	/**
	 * 
	 * @param bs
	 * @return Secs2RawBytes build from Raw-bytes
	 */
	public static Secs2RawBytes raw(byte[] bs) {
		return getBuilder().raw(bs);
	}
	
	/**
	 * 
	 * @return &lt;L[0] &gt;
	 */
	public static Secs2List list() {
		return getBuilder().list();
	}
	
	/**
	 * 
	 * @param values
	 * @return &lt;L[n] values&gt;
	 */
	public static Secs2List list(Secs2... values) {
		return getBuilder().list(values);
	}
	
	/**
	 * 
	 * @param values
	 * @return &lt;L[n] values&gt;
	 */
	public static Secs2List list(List<? extends Secs2> values) {
		return getBuilder().list(values);
	}
	
	/**
	 * 
	 * @param ascii
	 * @return &lt;A[n] "ascii"&gt;
	 */
	public static Secs2Ascii ascii(CharSequence ascii) {
		return getBuilder().ascii(ascii);
	}
	
	/**
	 * 
	 * @return &lt;B[0] &gt;
	 */
	public static Secs2Binary binary() {
		return getBuilder().binary();
	}
	
	/**
	 * 
	 * @param bs
	 * @return &lt;B[n] bs&gt;
	 */
	public static Secs2Binary binary(byte... bs) {
		return getBuilder().binary(bs);
	}
	
	/**
	 * 
	 * @param bs
	 * @return &lt;B[n] bs&gt;
	 */
	public static Secs2Binary binary(List<Byte> bs) {
		return getBuilder().binary(bs);
	}
	
	/**
	 * 
	 * @return &lt;BOOLEAN[0] &gt;
	 */
	public static AbstractSecs2 bool() {
		return getBuilder().bool();
	}
	
	/**
	 * 
	 * @param bools
	 * @return &lt;BOOLEAN[n] bools&gt;
	 */
	public static Secs2Boolean bool(boolean... bools) {
		return getBuilder().bool(bools);
	}
	
	/**
	 * 
	 * @param bools
	 * @return &lt;BOOLEAN[n] bools&gt;
	 */
	public static Secs2Boolean bool(List<Boolean> bools) {
		return getBuilder().bool(bools);
	}
	
	/**
	 * 
	 * @return &lt;I1[0] &gt;
	 */
	public static Secs2Int1 int1() {
		return getBuilder().int1();
	}
	
	/**
	 * 
	 * @param values
	 * @return &lt;I1[n] values&gt;
	 */
	public static Secs2Int1 int1(int... values) {
		return getBuilder().int1(values);
	}
	
	/**
	 * 
	 * @param values
	 * @return &lt;I1[n] values&gt;
	 */
	public static Secs2Int1 int1(long... values) {
		return getBuilder().int1(values);
	}
	
	/**
	 * 
	 * @param values
	 * @return &lt;I1[n] values&gt;
	 */
	public static Secs2Int1 int1(BigInteger... values) {
		return getBuilder().int1(values);
	}
	
	/**
	 * 
	 * @param values
	 * @return &lt;I1[n] values&gt;
	 */
	public static Secs2Int1 int1(List<? extends Number> values) {
		return getBuilder().int1(values);
	}
	
	/**
	 * 
	 * @return &lt;I2[0] &gt;
	 */
	public static Secs2Int2 int2() {
		return getBuilder().int2();
	}
	
	/**
	 * 
	 * @param values
	 * @return &lt;I2[n] values&gt;
	 */
	public static Secs2Int2 int2(int... values) {
		return getBuilder().int2(values);
	}
	
	/**
	 * 
	 * @param values
	 * @return &lt;I2[n] values&gt;
	 */
	public static Secs2Int2 int2(long... values) {
		return getBuilder().int2(values);
	}
	
	/**
	 * 
	 * @param values
	 * @return &lt;I2[n] values&gt;
	 */
	public static Secs2Int2 int2(BigInteger... values) {
		return getBuilder().int2(values);
	}
	
	/**
	 * 
	 * @param values
	 * @return &lt;I2[n] values&gt;
	 */
	public static Secs2Int2 int2(List<? extends Number> values) {
		return getBuilder().int2(values);
	}
	
	/**
	 * 
	 * @return &lt;I4[0] &gt;
	 */
	public static Secs2Int4 int4() {
		return getBuilder().int4();
	}
	
	/**
	 * 
	 * @param values
	 * @return &lt;I4[n] values&gt;
	 */
	public static Secs2Int4 int4(int... values) {
		return getBuilder().int4(values);
	}
	
	/**
	 * 
	 * @param values
	 * @return &lt;I4[n] values&gt;
	 */
	public static Secs2Int4 int4(long... values) {
		return getBuilder().int4(values);
	}
	
	/**
	 * 
	 * @param values
	 * @return &lt;I4[n] values&gt;
	 */
	public static Secs2Int4 int4(BigInteger... values) {
		return getBuilder().int4(values);
	}
	
	/**
	 * 
	 * @param values
	 * @return &lt;I4[n] values&gt;
	 */
	public static Secs2Int4 int4(List<? extends Number> values) {
		return getBuilder().int4(values);
	}
	
	/**
	 * 
	 * @return &lt;I8[0] &gt;
	 */
	public static Secs2Int8 int8() {
		return getBuilder().int8();
	}
	
	/**
	 * 
	 * @param values
	 * @return &lt;I8[n] values&gt;
	 */
	public static Secs2Int8 int8(int... values) {
		return getBuilder().int8(values);
	}
	
	/**
	 * 
	 * @param values
	 * @return &lt;I8[n] values&gt;
	 */
	public static Secs2Int8 int8(long... values) {
		return getBuilder().int8(values);
	}
	
	/**
	 * 
	 * @param values
	 * @return &lt;I8[n] values&gt;
	 */
	public static Secs2Int8 int8(BigInteger... values) {
		return getBuilder().int8(values);
	}
	
	/**
	 * 
	 * @param values
	 * @return &lt;I8[n] values&gt;
	 */
	public static Secs2Int8 int8(List<? extends Number> values) {
		return getBuilder().int8(values);
	}
	
	/**
	 * 
	 * @return &lt;U1[0] &gt;
	 */
	public static Secs2Uint1 uint1() {
		return getBuilder().uint1();
	}
	
	/**
	 * 
	 * @param values
	 * @return &lt;U1[n] values&gt;
	 */
	public static Secs2Uint1 uint1(int... values) {
		return getBuilder().uint1(values);
	}
	
	/**
	 * 
	 * @param values
	 * @return &lt;U1[n] values&gt;
	 */
	public static Secs2Uint1 uint1(long... values) {
		return getBuilder().uint1(values);
	}
	
	/**
	 * 
	 * @param values
	 * @return &lt;U1[n] values&gt;
	 */
	public static Secs2Uint1 uint1(BigInteger... values) {
		return getBuilder().uint1(values);
	}
	
	/**
	 * 
	 * @param values
	 * @return &lt;U1[n] values&gt;
	 */
	public static Secs2Uint1 uint1(List<? extends Number> values) {
		return getBuilder().uint1(values);
	}
	
	/**
	 * 
	 * @return &lt;U2[0] &gt;
	 */
	public static Secs2Uint2 uint2() {
		return getBuilder().uint2();
	}
	
	/**
	 * 
	 * @param values
	 * @return &lt;U2[n] values&gt;
	 */
	public static Secs2Uint2 uint2(int... values) {
		return getBuilder().uint2(values);
	}
	
	/**
	 * 
	 * @param values
	 * @return &lt;U2[n] values&gt;
	 */
	public static Secs2Uint2 uint2(long... values) {
		return getBuilder().uint2(values);
	}
	
	/**
	 * 
	 * @param values
	 * @return &lt;U2[n] values&gt;
	 */
	public static Secs2Uint2 uint2(BigInteger... values) {
		return getBuilder().uint2(values);
	}
	
	/**
	 * 
	 * @param values
	 * @return &lt;U2[n] values&gt;
	 */
	public static Secs2Uint2 uint2(List<? extends Number> values) {
		return getBuilder().uint2(values);
	}
	
	/**
	 * 
	 * @return &lt;U4[0] &gt;
	 */
	public static Secs2Uint4 uint4() {
		return getBuilder().uint4();
	}
	
	/**
	 * 
	 * @param values
	 * @return &lt;U4[n] values&gt;
	 */
	public static Secs2Uint4 uint4(int... values) {
		return getBuilder().uint4(values);
	}
	
	/**
	 * 
	 * @param values
	 * @return &lt;U4[n] values&gt;
	 */
	public static Secs2Uint4 uint4(long... values) {
		return getBuilder().uint4(values);
	}
	
	/**
	 * 
	 * @param values
	 * @return &lt;U4[n] values&gt;
	 */
	public static Secs2Uint4 uint4(BigInteger... values) {
		return getBuilder().uint4(values);
	}
	
	/**
	 * 
	 * @param values
	 * @return &lt;U4[n] values&gt;
	 */
	public static Secs2Uint4 uint4(List<? extends Number> values) {
		return getBuilder().uint4(values);
	}
	
	/**
	 * 
	 * @return &lt;U8[0] &gt;
	 */
	public static Secs2Uint8 uint8() {
		return getBuilder().uint8();
	}
	
	/**
	 * 
	 * @param values
	 * @return &lt;U8[n] values&gt;
	 */
	public static Secs2Uint8 uint8(int... values) {
		return getBuilder().uint8(values);
	}
	
	/**
	 * 
	 * @param values
	 * @return &lt;U8[n] values&gt;
	 */
	public static Secs2Uint8 uint8(long... values) {
		return getBuilder().uint8(values);
	}
	
	/**
	 * 
	 * @param values
	 * @return &lt;U8[n] values&gt;
	 */
	public static Secs2Uint8 uint8(BigInteger... values) {
		return getBuilder().uint8(values);
	}
	
	/**
	 * 
	 * @param values
	 * @return &lt;U8[n] values&gt;
	 */
	public static Secs2Uint8 uint8(List<? extends Number> values) {
		return getBuilder().uint8(values);
	}
	
	/**
	 * 
	 * @return &lt;F4[0] &gt;
	 */
	public static Secs2Float4 float4() {
		return getBuilder().float4();
	}
	
	/**
	 * 
	 * @param values
	 * @return &lt;F4[n] values&gt;
	 */
	public static Secs2Float4 float4(float... values) {
		return getBuilder().float4(values);
	}
	
	/**
	 * 
	 * @param values
	 * @return &lt;F4[n] values&gt;
	 */
	public static Secs2Float4 float4(List<? extends Number> values) {
		return getBuilder().float4(values);
	}
	
	/**
	 * 
	 * @return &lt;F8[0] &gt;
	 */
	public static Secs2Float8 float8() {
		return getBuilder().float8();
	}
	
	/**
	 * 
	 * @param values
	 * @return &lt;F8[n] values&gt;
	 */
	public static Secs2Float8 float8(double... values) {
		return getBuilder().float8(values);
	}
	
	/**
	 * 
	 * @param values
	 * @return &lt;F8[n] values&gt;
	 */
	public static Secs2Float8 float8(List<? extends Number> values) {
		return getBuilder().float8(values);
	}

}
