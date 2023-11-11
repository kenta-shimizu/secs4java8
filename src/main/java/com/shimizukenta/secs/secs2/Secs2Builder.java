package com.shimizukenta.secs.secs2;

import java.math.BigInteger;
import java.util.List;

import com.shimizukenta.secs.secs2.impl.Secs2Builders;

/**
 * This interface is implements of building SECS-II (SEMI-E5) Data.
 * 
 * @author kenta-shimizu
 *
 */
public interface Secs2Builder {
	
	/**
	 * Secs2Builder instance getter.
	 * 
	 * @return Secs2Builder instance
	 */
	public static Secs2Builder getInstance() {
		return Secs2Builders.getInstance();
	}
	
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
	 * @return Secs2 of 0 bytes
	 */
	public Secs2 empty();

	/**
	 * Returns SECS-II Data from receiving bytes data.
	 * 
	 * <p>
	 * Used in receiving bytes data.
	 * </p>
	 * 
	 * @param bs bytes
	 * @return SECS-II Data from receiving bytes data
	 */
	public Secs2 raw(byte[] bs);

	/**
	 * Returns empty SECS-II-List Empty Data.
	 * 
	 * <p>
	 * This instance is Singleton-pattern.
	 * </p>
	 * 
	 * @return Secs2 of L[0]
	 */
	public Secs2 list();

	/**
	 * Returns SECS-II-List Data of values.
	 * 
	 * @param values the Secs2 values
	 * @return Secs2 of List
	 * @throws NullPointerException if values is null.
	 * @throws Secs2LengthByteOutOfRangeException if length-byte-size {@code >0x00FFFFFF}.
	 */
	public Secs2 list(Secs2... values);

	/**
	 * Returns SECS-II-List Data of values.
	 * 
	 * @param values list of Secs2
	 * @return Secs2 of List
	 * @throws NullPointerException if values is null.
	 * @throws Secs2LengthByteOutOfRangeException if length-byte-size {@code >0x00FFFFFF}.
	 */
	public Secs2 list(List<? extends Secs2> values);

	/**
	 * Returns SECS-II-String Data of cs.
	 * 
	 * <p>
	 * Not accept {@code null}.<br />
	 * </p>
	 * 
	 * @param cs the CharSequence
	 * @return Secs2 of Ascii
	 * @throws NullPointerException if values is null.
	 * @throws Secs2LengthByteOutOfRangeException if length-byte-size {@code >0x00FFFFFF}.
	 */
	public Secs2 ascii(CharSequence cs);

	/**
	 * Returns SECS-II-Binary Empty Data.
	 * 
	 * @return Secs2 of B[0]
	 */
	public Secs2 binary();

	/**
	 * Returns SECS-II-Binary Data.
	 * 
	 * @param bs the bytes
	 * @return Secs of Binary
	 * @throws NullPointerException if bs is null.
	 * @throws Secs2LengthByteOutOfRangeException if length-byte-size {@code >0x00FFFFFF}.
	 */
	public Secs2 binary(byte... bs);

	/**
	 * Returns SECS-II-Binary Data.
	 * 
	 * @param bs List of Byte
	 * @return Secs2 of binary
	 * @throws NullPointerException if bs is null.
	 * @throws Secs2LengthByteOutOfRangeException if length-byte-size {@code >0x00FFFFFF}.
	 */
	public Secs2 binary(List<Byte> bs);

	/**
	 * Returns SECS-II-Boolean Empty Data.
	 * 
	 * @return Secs2 of BOOLEAN[0]
	 */
	public Secs2 bool();
	
	/**
	 * Returns SECS-II-Boolean Data.
	 * 
	 * @param bools booleans
	 * @return Secs2 of BOOLEAN
	 * @throws NullPointerException if bools is null.
	 * @throws Secs2LengthByteOutOfRangeException if length-byte-size {@code >0x00FFFFFF}.
	 */
	public Secs2 bool(boolean... bools);
	
	/**
	 * Returns SECS-II-Boolean Data.
	 * 
	 * @param bools list of boolean
	 * @return Secs2 of boolean
	 * @throws NullPointerException if bools is null.
	 * @throws Secs2LengthByteOutOfRangeException if length-byte-size {@code >0x00FFFFFF}.
	 */
	public Secs2 bool(List<Boolean> bools);

	/**
	 * Returns SECS-II-INT1 Empty Data.
	 * 
	 * @return Secs2 of {@code <I1[0]>}
	 */
	public Secs2 int1();

	/**
	 * Returns SECS-II-INT1 Data.
	 * 
	 * @param values the int values
	 * @return Secs2 of {@code <I1[n] n...>}
	 * @throws NullPointerException if values is null.
	 * @throws Secs2LengthByteOutOfRangeException if length-byte-size {@code >0x00FFFFFF}.
	 */
	public Secs2 int1(int... values);

	/**
	 * Returns SECS-II-INT1 Data.
	 * 
	 * @param values the long values
	 * @return Secs2 of {@code <I1[n] n...>}
	 * @throws NullPointerException if values is null.
	 * @throws Secs2LengthByteOutOfRangeException if length-byte-size {@code >0x00FFFFFF}.
	 */
	public Secs2 int1(long... values);

	/**
	 * Returns SECS-II-INT1 Data.
	 * 
	 * @param values the BigInteger values
	 * @return Secs2 of {@code <I1[n] n...>}
	 * @throws NullPointerException if values is null.
	 * @throws Secs2LengthByteOutOfRangeException if length-byte-size {@code >0x00FFFFFF}.
	 */
	public Secs2 int1(BigInteger... values);

	/**
	 * Returns SECS-II-INT1 Data.
	 * 
	 * @param values list of Number
	 * @return Secs2 of {@code <I1[n] n...>}
	 * @throws NullPointerException if values is null.
	 * @throws Secs2LengthByteOutOfRangeException if length-byte-size {@code >0x00FFFFFF}.
	 */
	public Secs2 int1(List<? extends Number> values);

	/**
	 * Returns SECS-II-INT2 Empty Data.
	 * 
	 * @return Secs2 of {@code <I2[0]>}
	 */
	public Secs2 int2();

	/**
	 * Returns SECS-II-INT2 Data.
	 * 
	 * @param values the int values
	 * @return Secs2 of {@code <I2[n] n...>}
	 * @throws NullPointerException if values is null.
	 * @throws Secs2LengthByteOutOfRangeException if length-byte-size {@code >0x00FFFFFF}.
	 */
	public Secs2 int2(int... values);

	/**
	 * Returns SECS-II-INT2 Data.
	 * 
	 * @param values the long values
	 * @return Secs2 of {@code <I2[n] n...>}
	 * @throws NullPointerException if values is null.
	 * @throws Secs2LengthByteOutOfRangeException if length-byte-size {@code >0x00FFFFFF}.
	 */
	public Secs2 int2(long... values);

	/**
	 * Returns SECS-II-INT2 Data.
	 * 
	 * @param values the BigInteger values
	 * @return Secs2 of {@code <I2[n] n...>}
	 * @throws NullPointerException if values is null.
	 * @throws Secs2LengthByteOutOfRangeException if length-byte-size {@code >0x00FFFFFF}.
	 */
	public Secs2 int2(BigInteger... values);

	/**
	 * Returns SECS-II-INT2 Data.
	 * 
	 * @param values list of Numbers
	 * @return Secs2 of {@code <I2[n] n...>}
	 * @throws NullPointerException if values is null.
	 * @throws Secs2LengthByteOutOfRangeException if length-byte-size {@code >0x00FFFFFF}.
	 */
	public Secs2 int2(List<? extends Number> values);

	/**
	 * Returns SECS-II-INT4 Empty Data.
	 * 
	 * @return Secs2 of {@code <I4[0]>}
	 */
	public Secs2 int4();

	/**
	 * Returns SECS-II-INT4 Data.
	 * 
	 * @param values the int values
	 * @return Secs2 of {@code <I4[n] n...>}
	 * @throws NullPointerException if values is null.
	 * @throws Secs2LengthByteOutOfRangeException if length-byte-size {@code >0x00FFFFFF}.
	 */
	public Secs2 int4(int... values);

	/**
	 * Returns SECS-II-INT4 Data.
	 * 
	 * @param values the long values
	 * @return Secs2 of {@code <I4[n] n...>}
	 * @throws NullPointerException if values is null.
	 * @throws Secs2LengthByteOutOfRangeException if length-byte-size {@code >0x00FFFFFF}.
	 */
	public Secs2 int4(long... values);

	/**
	 * Returns SECS-II-INT4 Data.
	 * 
	 * @param values the BigInteger values
	 * @return Secs2 of {@code <I4[n] n...>}
	 * @throws NullPointerException if values is null.
	 * @throws Secs2LengthByteOutOfRangeException if length-byte-size {@code >0x00FFFFFF}.
	 */
	public Secs2 int4(BigInteger... values);

	/**
	 * Returns SECS-II-INT4 Data.
	 * 
	 * @param values list of Numbers
	 * @return Secs2 of {@code <I4[n] n...>}
	 * @throws NullPointerException if values is null.
	 * @throws Secs2LengthByteOutOfRangeException if length-byte-size {@code >0x00FFFFFF}.
	 */
	public Secs2 int4(List<? extends Number> values);

	/**
	 * Returns SECS-II-INT8 Empty Data.
	 * 
	 * @return Secs2 of {@code <I8[0]>}
	 */
	public Secs2 int8();

	/**
	 * Returns SECS-II-INT8 Data.
	 * 
	 * @param values the int values
	 * @return Secs2 of {@code <I8[n] n...>}
	 * @throws NullPointerException if values is null.
	 * @throws Secs2LengthByteOutOfRangeException if length-byte-size {@code >0x00FFFFFF}.
	 */
	public Secs2 int8(int... values);

	/**
	 * Returns SECS-II-INT8 Data.
	 * 
	 * @param values the long values
	 * @return Secs2 of {@code <I8[n] n...>}
	 * @throws NullPointerException if values is null.
	 * @throws Secs2LengthByteOutOfRangeException if length-byte-size {@code >0x00FFFFFF}.
	 */
	public Secs2 int8(long... values);

	/**
	 * Returns SECS-II-INT8 Data.
	 * 
	 * @param values the BigInteger values
	 * @return Secs2 of {@code <I8[n] n...>}
	 * @throws NullPointerException if values is null.
	 * @throws Secs2LengthByteOutOfRangeException if length-byte-size {@code >0x00FFFFFF}.
	 */
	public Secs2 int8(BigInteger... values);

	/**
	 * Returns SECS-II-INT8 Data.
	 * 
	 * @param values list of Numbers
	 * @return Secs2 of {@code <I8[n] n...>}
	 * @throws NullPointerException if values is null.
	 * @throws Secs2LengthByteOutOfRangeException if length-byte-size {@code >0x00FFFFFF}.
	 */
	public Secs2 int8(List<? extends Number> values);

	/**
	 * Returns SECS-II-UINT1 Empty Data.
	 * 
	 * @return Secs2 of {@code <U1[0]>}
	 */
	public Secs2 uint1();

	/**
	 * Returns SECS-II-UINT1 Data.
	 * 
	 * @param values the int values
	 * @return Secs2 of {@code <U1[n] n...>}
	 * @throws NullPointerException if values is null.
	 * @throws Secs2LengthByteOutOfRangeException if length-byte-size {@code >0x00FFFFFF}.
	 */
	public Secs2 uint1(int... values);

	/**
	 * Returns SECS-II-UINT1 Data.
	 * 
	 * @param values the long values
	 * @return Secs2 of {@code <U1[n] n...>}
	 * @throws NullPointerException if values is null.
	 * @throws Secs2LengthByteOutOfRangeException if length-byte-size {@code >0x00FFFFFF}.
	 */
	public Secs2 uint1(long... values);

	/**
	 * Returns SECS-II-UINT1 Data.
	 * 
	 * @param values the BigInteger values
	 * @return Secs2 of {@code <U1[n] n...>}
	 * @throws NullPointerException if values is null.
	 * @throws Secs2LengthByteOutOfRangeException if length-byte-size {@code >0x00FFFFFF}.
	 */
	public Secs2 uint1(BigInteger... values);

	/**
	 * Returns SECS-II-UINT1 Data.
	 * 
	 * @param values list of Numbers
	 * @return Secs2 of {@code <U1[n] n...>}
	 * @throws NullPointerException if values is null.
	 * @throws Secs2LengthByteOutOfRangeException if length-byte-size {@code >0x00FFFFFF}.
	 */
	public Secs2 uint1(List<? extends Number> values);

	/**
	 * Returns SECS-II-UINT2 Empty Data.
	 * 
	 * @return Secs2 of {@code <U2[0]>}
	 */
	public Secs2 uint2();

	/**
	 * Returns SECS-II-UINT2 Data.
	 * 
	 * @param values the int values
	 * @return Secs2 of {@code <U2[n] n...>}
	 * @throws NullPointerException if values is null.
	 * @throws Secs2LengthByteOutOfRangeException if length-byte-size {@code >0x00FFFFFF}.
	 */
	public Secs2 uint2(int... values);

	/**
	 * Returns SECS-II-UINT2 Data.
	 * 
	 * @param values the long values
	 * @return Secs2 of {@code <U2[n] n...>}
	 * @throws NullPointerException if values is null.
	 * @throws Secs2LengthByteOutOfRangeException if length-byte-size {@code >0x00FFFFFF}.
	 */
	public Secs2 uint2(long... values);

	/**
	 * Returns SECS-II-UINT2 Data.
	 * 
	 * @param values the BigInteger values
	 * @return Secs2 of {@code <U2[n] n...>}
	 * @throws NullPointerException if values is null.
	 * @throws Secs2LengthByteOutOfRangeException if length-byte-size {@code >0x00FFFFFF}.
	 */
	public Secs2 uint2(BigInteger... values);

	/**
	 * Returns SECS-II-UINT2 Data.
	 * 
	 * @param values list of Numbers
	 * @return Secs2 of {@code <U2[n] n...>}
	 * @throws NullPointerException if values is null.
	 * @throws Secs2LengthByteOutOfRangeException if length-byte-size {@code >0x00FFFFFF}.
	 */
	public Secs2 uint2(List<? extends Number> values);

	/**
	 * Returns SECS-II-UINT4 Empty Data.
	 * 
	 * @return Secs2 of {@code <U4[0]>}
	 */
	public Secs2 uint4();

	/**
	 * Returns SECS-II-UINT4 Data.
	 * 
	 * @param values the int values
	 * @return Secs2 of {@code <U4[n] n...>}
	 * @throws NullPointerException if values is null.
	 * @throws Secs2LengthByteOutOfRangeException if length-byte-size {@code >0x00FFFFFF}.
	 */
	public Secs2 uint4(int... values);

	/**
	 * Returns SECS-II-UINT4 Data.
	 * 
	 * @param values the long values
	 * @return Secs2 of {@code <U4[n] n...>}
	 * @throws NullPointerException if values is null.
	 * @throws Secs2LengthByteOutOfRangeException if length-byte-size {@code >0x00FFFFFF}.
	 */
	public Secs2 uint4(long... values);

	/**
	 * Returns SECS-II-UINT4 Data.
	 * 
	 * @param values the BigInteger values
	 * @return Secs2 of {@code <U4[n] n...>}
	 * @throws NullPointerException if values is null.
	 * @throws Secs2LengthByteOutOfRangeException if length-byte-size {@code >0x00FFFFFF}.
	 */
	public Secs2 uint4(BigInteger... values);

	/**
	 * Returns SECS-II-UINT4 Data.
	 * 
	 * @param values list of Numbers
	 * @return Secs2 of {@code <U4[n] n...>}
	 * @throws NullPointerException if values is null.
	 * @throws Secs2LengthByteOutOfRangeException if length-byte-size {@code >0x00FFFFFF}.
	 */
	public Secs2 uint4(List<? extends Number> values);

	/**
	 * Returns SECS-II-UINT8 Empty Data.
	 * 
	 * @return Secs2 of {@code <U8[0]>}
	 */
	public Secs2 uint8();

	/**
	 * Returns SECS-II-UINT8 Data.
	 * 
	 * @param values the int values
	 * @return Secs2 of {@code <U8[n] n...>}
	 * @throws NullPointerException if values is null.
	 * @throws Secs2LengthByteOutOfRangeException if length-byte-size {@code >0x00FFFFFF}.
	 */
	public Secs2 uint8(int... values);

	/**
	 * Returns SECS-II-UINT8 Data.
	 * 
	 * @param values the long values
	 * @return Secs2 of {@code <U8[n] n...>}
	 * @throws NullPointerException if values is null.
	 * @throws Secs2LengthByteOutOfRangeException if length-byte-size {@code >0x00FFFFFF}.
	 */
	public Secs2 uint8(long... values);

	/**
	 * Returns SECS-II-UINT8 Data.
	 * 
	 * @param values the BigInteger values
	 * @return Secs2 of {@code <U8[n] n...>}
	 * @throws NullPointerException if values is null.
	 * @throws Secs2LengthByteOutOfRangeException if length-byte-size {@code >0x00FFFFFF}.
	 */
	public Secs2 uint8(BigInteger... values);

	/**
	 * Returns SECS-II-UINT8 Data.
	 * 
	 * @param values list of Numbers
	 * @return Secs2 of {@code <U8[n] n...>}
	 * @throws NullPointerException if values is null.
	 * @throws Secs2LengthByteOutOfRangeException if length-byte-size {@code >0x00FFFFFF}.
	 */
	public Secs2 uint8(List<? extends Number> values);

	/**
	 * Returns SECS-II-FLOAT4 Empty  Data.
	 * 
	 * @return Secs2 of {@code <F4[0]>}
	 */
	public Secs2 float4();

	/**
	 * Returns SECS-II-FLOAT4 Data.
	 * 
	 * @param values the float values
	 * @return Secs2 of {@code <F4[n] n...>}
	 * @throws NullPointerException if values is null.
	 * @throws Secs2LengthByteOutOfRangeException if length-byte-size {@code >0x00FFFFFF}.
	 */
	public Secs2 float4(float... values);

	/**
	 * Returns SECS-II-FLOAT4 Data.
	 * 
	 * @param values list of Numbers
	 * @return Secs2 of {@code <F4[n] n...>}
	 * @throws NullPointerException if values is null.
	 * @throws Secs2LengthByteOutOfRangeException if length-byte-size {@code >0x00FFFFFF}.
	 */
	public Secs2 float4(List<? extends Number> values);

	/**
	 * Returns SECS-II-FLOAT8 Empty  Data.
	 * 
	 * @return {@code <F8[0]>}
	 */
	public Secs2 float8();

	/**
	 * Returns SECS-II-FLOAT8 Data.
	 * 
	 * @param values the double values
	 * @return Secs2 of {@code <F8[n] n...>}
	 * @throws NullPointerException if values is null.
	 * @throws Secs2LengthByteOutOfRangeException if length-byte-size {@code >0x00FFFFFF}.
	 */
	public Secs2 float8(double... values);

	/**
	 * Returns SECS-II-FLOAT8 Data.
	 * 
	 * @param values list of Numbers
	 * @return Secs2 of {@code <F8[n] n...>}
	 * @throws NullPointerException if values is null.
	 * @throws Secs2LengthByteOutOfRangeException if length-byte-size {@code >0x00FFFFFF}.
	 */
	public Secs2 float8(List<? extends Number> values);
	
	
}
