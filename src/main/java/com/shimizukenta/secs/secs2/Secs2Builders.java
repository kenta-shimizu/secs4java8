package com.shimizukenta.secs.secs2;

import java.math.BigInteger;
import java.util.List;

public final class Secs2Builders {

	private Secs2Builders() {
		/* Nothing */
	}
	
	private static class SingletonHolder {
		private static final Secs2Builder inst = new AbstractSecs2Builder() {};
	}
	
	public static Secs2Builder getInstance() {
		return SingletonHolder.inst;
	}
	
	/**
	 * Returns SECS-II-0-bytes.
	 * 
	 * @return Empty-Secs2
	 */
	public static Secs2RawBytes empty() {
		return getInstance().empty();
	}
	
	/**
	 * Equivalent to {@code getBuilder().raw(bs)}.
	 * 
	 * @param bs
	 * @return Secs2RawBytes
	 */
	public static Secs2RawBytes raw(byte[] bs) {
		return getInstance().raw(bs);
	}
	
	/**
	 * Equivalent to {@code getBuilder().list()}.
	 * 
	 * @return &lt;L[0] &gt;
	 */
	public static Secs2List list() {
		return getInstance().list();
	}
	
	/**
	 * Equivalent to {@code getBuilder().list(values)}.
	 * 
	 * @param values
	 * @return &lt;L[n] values&gt;
	 */
	public static Secs2List list(Secs2... values) {
		return getInstance().list(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().list(values)}.
	 * 
	 * @param values
	 * @return &lt;L[n] values&gt;
	 */
	public static Secs2List list(List<? extends Secs2> values) {
		return getInstance().list(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().ascii(ascii)}.
	 * 
	 * @param ascii
	 * @return &lt;A[n] "ascii"&gt;
	 */
	public static Secs2Ascii ascii(CharSequence ascii) {
		return getInstance().ascii(ascii);
	}
	
	/**
	 * Equivalent to {@code getBuilder().binary()}.
	 * 
	 * @return &lt;B[0] &gt;
	 */
	public static Secs2Binary binary() {
		return getInstance().binary();
	}
	
	/**
	 * Equivalent to {@code getBuilder().binary(bs)}.
	 * 
	 * @param bs
	 * @return &lt;B[n] bs&gt;
	 */
	public static Secs2Binary binary(byte... bs) {
		return getInstance().binary(bs);
	}
	
	/**
	 * Equivalent to {@code getBuilder().binary(bs)}.
	 * 
	 * @param bs
	 * @return &lt;B[n] bs&gt;
	 */
	public static Secs2Binary binary(List<Byte> bs) {
		return getInstance().binary(bs);
	}
	
	/**
	 * Equivalent to {@code getBuilder().bool()}.
	 * 
	 * @return &lt;BOOLEAN[0] &gt;
	 */
	public static Secs2Boolean bool() {
		return getInstance().bool();
	}
	
	/**
	 * Equivalent to {@code getBuilder().bool(bools)}.
	 * 
	 * @param bools
	 * @return &lt;BOOLEAN[n] bools&gt;
	 */
	public static Secs2Boolean bool(boolean... bools) {
		return getInstance().bool(bools);
	}
	
	/**
	 * Equivalent to {@code getBuilder().bool(bools)}.
	 * 
	 * @param bools
	 * @return &lt;BOOLEAN[n] bools&gt;
	 */
	public static Secs2Boolean bool(List<Boolean> bools) {
		return getInstance().bool(bools);
	}
	
	/**
	 * Equivalent to {@code getBuilder().int1()}.
	 * 
	 * @return &lt;I1[0] &gt;
	 */
	public static Secs2Int1 int1() {
		return getInstance().int1();
	}
	
	/**
	 * Equivalent to {@code getBuilder().int1(values)}.
	 * 
	 * @param values
	 * @return &lt;I1[n] values&gt;
	 */
	public static Secs2Int1 int1(int... values) {
		return getInstance().int1(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().int1(values)}.
	 * 
	 * @param values
	 * @return &lt;I1[n] values&gt;
	 */
	public static Secs2Int1 int1(long... values) {
		return getInstance().int1(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().int1(values)}.
	 * 
	 * @param values
	 * @return &lt;I1[n] values&gt;
	 */
	public static Secs2Int1 int1(BigInteger... values) {
		return getInstance().int1(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().int1(values)}.
	 * 
	 * @param values
	 * @return &lt;I1[n] values&gt;
	 */
	public static Secs2Int1 int1(List<? extends Number> values) {
		return getInstance().int1(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().int2()}.
	 * 
	 * @return &lt;I2[0] &gt;
	 */
	public static Secs2Int2 int2() {
		return getInstance().int2();
	}
	
	/**
	 * Equivalent to {@code getBuilder().int2(values)}.
	 * 
	 * @param values
	 * @return &lt;I2[n] values&gt;
	 */
	public static Secs2Int2 int2(int... values) {
		return getInstance().int2(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().int2(values)}.
	 * 
	 * @param values
	 * @return &lt;I2[n] values&gt;
	 */
	public static Secs2Int2 int2(long... values) {
		return getInstance().int2(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().int2(values)}.
	 * 
	 * @param values
	 * @return &lt;I2[n] values&gt;
	 */
	public static Secs2Int2 int2(BigInteger... values) {
		return getInstance().int2(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().int2(values)}.
	 * 
	 * @param values
	 * @return &lt;I2[n] values&gt;
	 */
	public static Secs2Int2 int2(List<? extends Number> values) {
		return getInstance().int2(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().int4()}.
	 * 
	 * @return &lt;I4[0] &gt;
	 */
	public static Secs2Int4 int4() {
		return getInstance().int4();
	}
	
	/**
	 * Equivalent to {@code getBuilder().int4(values)}.
	 * 
	 * @param values
	 * @return &lt;I4[n] values&gt;
	 */
	public static Secs2Int4 int4(int... values) {
		return getInstance().int4(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().int4(values)}.
	 * 
	 * @param values
	 * @return &lt;I4[n] values&gt;
	 */
	public static Secs2Int4 int4(long... values) {
		return getInstance().int4(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().int4(values)}.
	 * 
	 * @param values
	 * @return &lt;I4[n] values&gt;
	 */
	public static Secs2Int4 int4(BigInteger... values) {
		return getInstance().int4(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().int4(values)}.
	 * 
	 * @param values
	 * @return &lt;I4[n] values&gt;
	 */
	public static Secs2Int4 int4(List<? extends Number> values) {
		return getInstance().int4(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().int8()}.
	 * 
	 * @return &lt;I8[0] &gt;
	 */
	public static Secs2Int8 int8() {
		return getInstance().int8();
	}
	
	/**
	 * Equivalent to {@code getBuilder().int8(values)}.
	 * 
	 * @param values
	 * @return &lt;I8[n] values&gt;
	 */
	public static Secs2Int8 int8(int... values) {
		return getInstance().int8(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().int8(values)}.
	 * 
	 * @param values
	 * @return &lt;I8[n] values&gt;
	 */
	public static Secs2Int8 int8(long... values) {
		return getInstance().int8(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().int8(values)}.
	 * 
	 * @param values
	 * @return &lt;I8[n] values&gt;
	 */
	public static Secs2Int8 int8(BigInteger... values) {
		return getInstance().int8(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().int8(values)}.
	 * 
	 * @param values
	 * @return &lt;I8[n] values&gt;
	 */
	public static Secs2Int8 int8(List<? extends Number> values) {
		return getInstance().int8(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().uint1()}.
	 * 
	 * @return &lt;U1[0] &gt;
	 */
	public static Secs2Uint1 uint1() {
		return getInstance().uint1();
	}
	
	/**
	 * Equivalent to {@code getBuilder().uint1(values)}.
	 * 
	 * @param values
	 * @return &lt;U1[n] values&gt;
	 */
	public static Secs2Uint1 uint1(int... values) {
		return getInstance().uint1(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().uint1(values)}.
	 * 
	 * @param values
	 * @return &lt;U1[n] values&gt;
	 */
	public static Secs2Uint1 uint1(long... values) {
		return getInstance().uint1(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().uint1(values)}.
	 * 
	 * @param values
	 * @return &lt;U1[n] values&gt;
	 */
	public static Secs2Uint1 uint1(BigInteger... values) {
		return getInstance().uint1(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().uint1(values)}.
	 * 
	 * @param values
	 * @return &lt;U1[n] values&gt;
	 */
	public static Secs2Uint1 uint1(List<? extends Number> values) {
		return getInstance().uint1(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().uint2()}.
	 * 
	 * @return &lt;U2[0] &gt;
	 */
	public static Secs2Uint2 uint2() {
		return getInstance().uint2();
	}
	
	/**
	 * Equivalent to {@code getBuilder().uint2(values)}.
	 * 
	 * @param values
	 * @return &lt;U2[n] values&gt;
	 */
	public static Secs2Uint2 uint2(int... values) {
		return getInstance().uint2(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().uint2(values)}.
	 * 
	 * @param values
	 * @return &lt;U2[n] values&gt;
	 */
	public static Secs2Uint2 uint2(long... values) {
		return getInstance().uint2(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().uint2(values)}.
	 * 
	 * @param values
	 * @return &lt;U2[n] values&gt;
	 */
	public static Secs2Uint2 uint2(BigInteger... values) {
		return getInstance().uint2(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().uint2(values)}.
	 * 
	 * @param values
	 * @return &lt;U2[n] values&gt;
	 */
	public static Secs2Uint2 uint2(List<? extends Number> values) {
		return getInstance().uint2(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().uint4()}.
	 * 
	 * @return &lt;U4[0] &gt;
	 */
	public static Secs2Uint4 uint4() {
		return getInstance().uint4();
	}
	
	/**
	 * Equivalent to {@code getBuilder().uint4(values)}.
	 * 
	 * @param values
	 * @return &lt;U4[n] values&gt;
	 */
	public static Secs2Uint4 uint4(int... values) {
		return getInstance().uint4(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().uint4(values)}.
	 * 
	 * @param values
	 * @return &lt;U4[n] values&gt;
	 */
	public static Secs2Uint4 uint4(long... values) {
		return getInstance().uint4(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().uint4(values)}.
	 * 
	 * @param values
	 * @return &lt;U4[n] values&gt;
	 */
	public static Secs2Uint4 uint4(BigInteger... values) {
		return getInstance().uint4(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().uint4(values)}.
	 * 
	 * @param values
	 * @return &lt;U4[n] values&gt;
	 */
	public static Secs2Uint4 uint4(List<? extends Number> values) {
		return getInstance().uint4(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().uint8()}.
	 * 
	 * @return &lt;U8[0] &gt;
	 */
	public static Secs2Uint8 uint8() {
		return getInstance().uint8();
	}
	
	/**
	 * Equivalent to {@code getBuilder().uint8(values)}.
	 * 
	 * @param values
	 * @return &lt;U8[n] values&gt;
	 */
	public static Secs2Uint8 uint8(int... values) {
		return getInstance().uint8(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().uint8(values)}.
	 * 
	 * @param values
	 * @return &lt;U8[n] values&gt;
	 */
	public static Secs2Uint8 uint8(long... values) {
		return getInstance().uint8(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().uint8(values)}.
	 * 
	 * @param values
	 * @return &lt;U8[n] values&gt;
	 */
	public static Secs2Uint8 uint8(BigInteger... values) {
		return getInstance().uint8(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().uint8(values)}.
	 * 
	 * @param values
	 * @return &lt;U8[n] values&gt;
	 */
	public static Secs2Uint8 uint8(List<? extends Number> values) {
		return getInstance().uint8(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().float4()}.
	 * 
	 * @return &lt;F4[0] &gt;
	 */
	public static Secs2Float4 float4() {
		return getInstance().float4();
	}
	
	/**
	 * Equivalent to {@code getBuilder().float4(values)}.
	 * 
	 * @param values
	 * @return &lt;F4[n] values&gt;
	 */
	public static Secs2Float4 float4(float... values) {
		return getInstance().float4(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().float4(values)}.
	 * 
	 * @param values
	 * @return &lt;F4[n] values&gt;
	 */
	public static Secs2Float4 float4(List<? extends Number> values) {
		return getInstance().float4(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().float8()}.
	 * 
	 * @return &lt;F8[0] &gt;
	 */
	public static Secs2Float8 float8() {
		return getInstance().float8();
	}
	
	/**
	 * Equivalent to {@code getBuilder().float8(values)}.
	 * 
	 * @param values
	 * @return &lt;F8[n] values&gt;
	 */
	public static Secs2Float8 float8(double... values) {
		return getInstance().float8(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().float8(values)}.
	 * 
	 * @param values
	 * @return &lt;F8[n] values&gt;
	 */
	public static Secs2Float8 float8(List<? extends Number> values) {
		return getInstance().float8(values);
	}
	
}
