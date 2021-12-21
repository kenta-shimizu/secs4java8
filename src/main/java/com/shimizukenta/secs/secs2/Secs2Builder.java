package com.shimizukenta.secs.secs2;

import java.math.BigInteger;
import java.util.List;

/**
 * This interface is implements of building SECS-II (SEMI-E5) Data.
 * 
 * @author kenta-shimizu
 *
 */
public interface Secs2Builder {

	/**
	 * Returns empty SECS-II Data.
	 * 
	 * <p>
	 * Used in Header-only-SECS-Message.
	 * </p>
	 * <p>
	 * This instance is Singleton-pattern.
	 * </p>
	 * 
	 * @return empty SECS-II Data
	 */
	public Secs2RawBytes empty();

	/**
	 * Returns SECS-II Data from receiving bytes data.
	 * 
	 * <p>
	 * Used in receiving bytes data.
	 * </p>
	 * 
	 * @param bs
	 * @return SECS-II Data from receiving bytes data
	 */
	public Secs2RawBytes raw(byte[] bs);

	/**
	 * Returns empty SECS-II-List Data.
	 * 
	 * <p>
	 * This instance is Singleton-pattern.
	 * </p>
	 * 
	 * @return &lt;L[0] &gt;
	 */
	public Secs2List list();

	/**
	 * Returns SECS-II-List Data of values.
	 * 
	 * @param values
	 * @return &lt;L[n] values &gt;
	 */
	public Secs2List list(Secs2... values);

	/**
	 * Returns SECS-II-List Data of values.
	 * 
	 * @param values
	 * @return &lt;L[n] values &gt;
	 */
	public Secs2List list(List<? extends Secs2> values);

	/**
	 * Returns SECS-II-String Data of cs.
	 * 
	 * <p>
	 * Not accept {@code null}.<br />
	 * </p>
	 * 
	 * @param cs
	 * @return &lt;A[n] cs &gt;
	 */
	public Secs2Ascii ascii(CharSequence cs);

	/**
	 * Returns SECS-II-Binary Data.
	 * 
	 * @return &lt;B[0] &gt;
	 */
	public Secs2Binary binary();

	/**
	 * Returns SECS-II-Binary Data.
	 * 
	 * @return &lt;B[n] bs &gt;
	 */
	public Secs2Binary binary(byte... bs);

	/**
	 * Returns SECS-II-Binary Data.
	 * 
	 * @return &lt;B[n] bs &gt;
	 */
	public Secs2Binary binary(List<Byte> bs);

	/**
	 * Returns SECS-II-Boolean Data.
	 * 
	 * @return &lt;BOOLEAN[0] &gt;
	 */
	public Secs2Boolean bool();

	/**
	 * Returns SECS-II-Boolean Data.
	 * 
	 * @return &lt;BOOLEAN[n] bools &gt;
	 */
	public Secs2Boolean bool(boolean... bools);

	/**
	 * Returns SECS-II-Boolean Data.
	 * 
	 * @return &lt;BOOLEAN[n] bools &gt;
	 */
	public Secs2Boolean bool(List<Boolean> bools);

	/**
	 * Returns SECS-II-INT1 Data.
	 * 
	 * @return &lt;I1[0] &gt;
	 */
	public Secs2Int1 int1();

	/**
	 * Returns SECS-II-INT1 Data.
	 * 
	 * @return &lt;I1[n] values &gt;
	 */
	public Secs2Int1 int1(int... values);

	/**
	 * Returns SECS-II-INT1 Data.
	 * 
	 * @return &lt;I1[n] values &gt;
	 */
	public Secs2Int1 int1(long... values);

	/**
	 * Returns SECS-II-INT1 Data.
	 * 
	 * @return &lt;I1[n] values &gt;
	 */
	public Secs2Int1 int1(BigInteger... values);

	/**
	 * Returns SECS-II-INT1 Data.
	 * 
	 * @return &lt;I1[n] values &gt;
	 */
	public Secs2Int1 int1(List<? extends Number> values);

	/**
	 * Returns SECS-II-INT2 Data.
	 * 
	 * @return &lt;I2[0] &gt;
	 */
	public Secs2Int2 int2();

	/**
	 * Returns SECS-II-INT2 Data.
	 * 
	 * @return &lt;I2[n] values &gt;
	 */
	public Secs2Int2 int2(int... values);

	/**
	 * Returns SECS-II-INT2 Data.
	 * 
	 * @return &lt;I2[n] values &gt;
	 */
	public Secs2Int2 int2(long... values);

	/**
	 * Returns SECS-II-INT2 Data.
	 * 
	 * @return &lt;I2[n] values &gt;
	 */
	public Secs2Int2 int2(BigInteger... values);

	/**
	 * Returns SECS-II-INT2 Data.
	 * 
	 * @return &lt;I2[n] values &gt;
	 */
	public Secs2Int2 int2(List<? extends Number> values);

	/**
	 * Returns SECS-II-INT4 Data.
	 * 
	 * @return &lt;I4[0] &gt;
	 */
	public Secs2Int4 int4();

	/**
	 * Returns SECS-II-INT4 Data.
	 * 
	 * @return &lt;I4[n] values &gt;
	 */
	public Secs2Int4 int4(int... values);

	/**
	 * Returns SECS-II-INT4 Data.
	 * 
	 * @return &lt;I4[n] values &gt;
	 */
	public Secs2Int4 int4(long... values);

	/**
	 * Returns SECS-II-INT4 Data.
	 * 
	 * @return &lt;I4[n] values &gt;
	 */
	public Secs2Int4 int4(BigInteger... values);

	/**
	 * Returns SECS-II-INT4 Data.
	 * 
	 * @return &lt;I4[n] values &gt;
	 */
	public Secs2Int4 int4(List<? extends Number> values);

	/**
	 * Returns SECS-II-INT8 Data.
	 * 
	 * @return &lt;I8[0] &gt;
	 */
	public Secs2Int8 int8();

	/**
	 * Returns SECS-II-INT8 Data.
	 * 
	 * @return &lt;I8[n] values &gt;
	 */
	public Secs2Int8 int8(int... values);

	/**
	 * Returns SECS-II-INT8 Data.
	 * 
	 * @return &lt;I8[n] values &gt;
	 */
	public Secs2Int8 int8(long... values);

	/**
	 * Returns SECS-II-INT8 Data.
	 * 
	 * @return &lt;I8[n] values &gt;
	 */
	public Secs2Int8 int8(BigInteger... values);

	/**
	 * Returns SECS-II-INT8 Data.
	 * 
	 * @return &lt;I8[n] values &gt;
	 */
	public Secs2Int8 int8(List<? extends Number> values);

	/**
	 * Returns SECS-II-UINT1 Data.
	 * 
	 * @return &lt;U1[0] &gt;
	 */
	public Secs2Uint1 uint1();

	/**
	 * Returns SECS-II-UINT1 Data.
	 * 
	 * @return &lt;U1[n] values &gt;
	 */
	public Secs2Uint1 uint1(int... values);

	/**
	 * Returns SECS-II-UINT1 Data.
	 * 
	 * @return &lt;U1[n] values &gt;
	 */
	public Secs2Uint1 uint1(long... values);

	/**
	 * Returns SECS-II-UINT1 Data.
	 * 
	 * @return &lt;U1[n] values &gt;
	 */
	public Secs2Uint1 uint1(BigInteger... values);

	/**
	 * Returns SECS-II-UINT1 Data.
	 * 
	 * @return &lt;U1[n] values &gt;
	 */
	public Secs2Uint1 uint1(List<? extends Number> values);

	/**
	 * Returns SECS-II-UINT2 Data.
	 * 
	 * @return &lt;U2[0] &gt;
	 */
	public Secs2Uint2 uint2();

	/**
	 * Returns SECS-II-UINT2 Data.
	 * 
	 * @return &lt;U2[n] values &gt;
	 */
	public Secs2Uint2 uint2(int... values);

	/**
	 * Returns SECS-II-UINT2 Data.
	 * 
	 * @return &lt;U2[n] values &gt;
	 */
	public Secs2Uint2 uint2(long... values);

	/**
	 * Returns SECS-II-UINT2 Data.
	 * 
	 * @return &lt;U2[n] values &gt;
	 */
	public Secs2Uint2 uint2(BigInteger... values);

	/**
	 * Returns SECS-II-UINT2 Data.
	 * 
	 * @return &lt;U2[n] values &gt;
	 */
	public Secs2Uint2 uint2(List<? extends Number> values);

	/**
	 * Returns SECS-II-UINT4 Data.
	 * 
	 * @return &lt;U4[0] &gt;
	 */
	public Secs2Uint4 uint4();

	/**
	 * Returns SECS-II-UINT4 Data.
	 * 
	 * @return &lt;U4[n] values &gt;
	 */
	public Secs2Uint4 uint4(int... values);

	/**
	 * Returns SECS-II-UINT4 Data.
	 * 
	 * @return &lt;U4[n] values &gt;
	 */
	public Secs2Uint4 uint4(long... values);

	/**
	 * Returns SECS-II-UINT4 Data.
	 * 
	 * @return &lt;U4[n] values &gt;
	 */
	public Secs2Uint4 uint4(BigInteger... values);

	/**
	 * Returns SECS-II-UINT4 Data.
	 * 
	 * @return &lt;U4[n] values &gt;
	 */
	public Secs2Uint4 uint4(List<? extends Number> values);

	/**
	 * Returns SECS-II-UINT8 Data.
	 * 
	 * @return &lt;U8[0] &gt;
	 */
	public Secs2Uint8 uint8();

	/**
	 * Returns SECS-II-UINT8 Data.
	 * 
	 * @return &lt;U8[n] values &gt;
	 */
	public Secs2Uint8 uint8(int... values);

	/**
	 * Returns SECS-II-UINT8 Data.
	 * 
	 * @return &lt;U8[n] values &gt;
	 */
	public Secs2Uint8 uint8(long... values);

	/**
	 * Returns SECS-II-UINT8 Data.
	 * 
	 * @return &lt;U8[n] values &gt;
	 */
	public Secs2Uint8 uint8(BigInteger... values);

	/**
	 * Returns SECS-II-UINT8 Data.
	 * 
	 * @return &lt;U8[n] values &gt;
	 */
	public Secs2Uint8 uint8(List<? extends Number> values);

	/**
	 * Returns SECS-II-FLOAT4 Data.
	 * 
	 * @return &lt;F4[0] &gt;
	 */
	public Secs2Float4 float4();

	/**
	 * Returns SECS-II-FLOAT4 Data.
	 * 
	 * @return &lt;F4[n] values &gt;
	 */
	public Secs2Float4 float4(float... values);

	/**
	 * Returns SECS-II-FLOAT4 Data.
	 * 
	 * @return &lt;F4[n] values &gt;
	 */
	public Secs2Float4 float4(List<? extends Number> values);

	/**
	 * Returns SECS-II-FLOAT8 Data.
	 * 
	 * @return &lt;F8[0] &gt;
	 */
	public Secs2Float8 float8();

	/**
	 * Returns SECS-II-FLOAT8 Data.
	 * 
	 * @return &lt;F8[n] values &gt;
	 */
	public Secs2Float8 float8(double... values);

	/**
	 * Returns SECS-II-FLOAT8 Data.
	 * 
	 * @return &lt;F8[n] values &gt;
	 */
	public Secs2Float8 float8(List<? extends Number> values);
	
	
	/**
	 * Secs2Builder instance getter.
	 * 
	 * @return Secs2Builder instance
	 */
	public static Secs2Builder getInstance() {
		return Secs2Builders.getInstance();
	}
	
}
