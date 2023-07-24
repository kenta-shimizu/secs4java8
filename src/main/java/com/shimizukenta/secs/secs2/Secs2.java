package com.shimizukenta.secs.secs2;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import com.shimizukenta.secs.secs2.impl.Secs2Builders;

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
	 * @param indices indeices of list
	 * @return Secs2
	 * @throws Secs2Exception if parse failed
	 */
	public Secs2 get(int... indices) throws Secs2Exception;
	
	/**
	 * Returns nested String by indices.
	 * 
	 * <p>
	 * Available if type is "A".
	 * </p>
	 * 
	 * @param indices indicies of list
	 * @return ASCII-String
	 * @throws Secs2Exception if parse failed
	 */
	public String getAscii(int... indices) throws Secs2Exception;
	
	/**
	 * Returns String,
	 * 
	 * <p>
	 * Available String if type is "A".
	 * </p>
	 * 
	 * @return ASCII-String
	 * @throws Secs2Exception if parse failed
	 */
	public String getAscii() throws Secs2Exception;
	
	/**
	 * Returns nested byte-value by indices,
	 * 
	 * <p>
	 * Available if type is "B".
	 * </p>
	 * 
	 * @param indices the indicies
	 * @return byte-value
	 * @throws Secs2Exception if parse failed
	 */
	public byte getByte(int... indices) throws Secs2Exception;
	
	/**
	 * Returns nested bytes by indices.
	 * 
	 * <p>
	 * Available if type is "B".
	 * </p>
	 * 
	 * @param indices indices of list
	 * @return bytes
	 * @throws Secs2Exception if parse failed
	 */
	public byte[] getBytes(int... indices) throws Secs2Exception;
	
	/**
	 * Returns byte array,
	 * 
	 * <p>
	 * Available byte array if type is "B".
	 * </p>
	 * 
	 * @return bytes
	 * @throws Secs2Exception if not binary
	 */
	public byte[] getBytes() throws Secs2Exception;
	
	/**
	 * Returns nested boolean-value by indices,
	 * 
	 * <p>
	 * Available if type is "BOOLEAN".
	 * </p>
	 * 
	 * @param indices the indicies
	 * @return boolean-value
	 * @throws Secs2Exception if parse failed
	 */
	public boolean getBoolean(int... indices) throws Secs2Exception;
	
	/**
	 * Returns nested Numeric-value by indices.
	 * 
	 * <p>
	 * Available if type is "I1","I2","I4","I8","F4","F8","U1","U2","U4","U8"
	 * </p>
	 * 
	 * @param indices the indicies
	 * @return (int)value
	 * @throws Secs2Exception if parse failed
	 */
	public int getInt( int... indices ) throws Secs2Exception;
	
	/**
	 * Returns nested Numeric-value by indices,
	 * 
	 * <p>
	 * Available if type is "I1","I2","I4","I8","F4","F8","U1","U2","U4","U8"
	 * </p>
	 * 
	 * @param indices the indicies
	 * @return (long)value
	 * @throws Secs2Exception if parse failed
	 */
	public long getLong( int... indices ) throws Secs2Exception;
	
	/**
	 * Returns nested Numeric-value by indices.
	 * 
	 * <p>
	 * Available if type is "I1","I2","I4","I8","F4","F8","U1","U2","U4","U8"
	 * </p>
	 * 
	 * @param indices the indicies
	 * @return BigInteger.valueOf(value)
	 * @throws Secs2Exception if parse failed
	 */
	public BigInteger getBigInteger( int... indices ) throws Secs2Exception;
	
	/**
	 * Returns nested Numeric-value by indices.
	 * 
	 * <p>
	 * Available if type is "I1","I2","I4","I8","F4","F8","U1","U2","U4","U8"
	 * </p>
	 * 
	 * @param indices the indicies
	 * @return (float)value
	 * @throws Secs2Exception if parse failed
	 */
	public float getFloat( int... indices ) throws Secs2Exception;
	
	/**
	 * Returns nested Numeric-value by indices.
	 * 
	 * <p>
	 * Available if type is "I1","I2","I4","I8","F4","F8","U1","U2","U4","U8"
	 * </p>
	 * 
	 * @param indices the indicies
	 * @return (double)value
	 * @throws Secs2Exception if parse failed
	 */
	public double getDouble( int... indices ) throws Secs2Exception;

	/**
	 * Returns nested Numeric-value by indices,
	 * 
	 * <p>
	 * Available if type is "I1","I2","I4","I8","F4","F8","U1","U2","U4","U8"
	 * </p>
	 * 
	 * @param indices the indicies
	 * @return (Number)value
	 * @throws Secs2Exception if parse failed
	 */
	public Number getNumber( int... indices ) throws Secs2Exception;
	
	
	/* Optionals */
	
	/**
	 * Optional valuei is present if ASCII, otherwise empty.
	 * 
	 * @return Optional
	 */
	public Optional<String> optionalAscii();
	
	
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
	public static Secs2 empty() {
		return getBuilder().empty();
	}
	
	/**
	 * Equivalent to {@code getBuilder().raw(bs)}.
	 * 
	 * @param bs the bytes
	 * @return Secs2RawBytes
	 */
	public static Secs2 raw(byte[] bs) {
		return getBuilder().raw(bs);
	}
	
	/**
	 * Equivalent to {@code getBuilder().list()}.
	 * 
	 * @return {@code <L[0]}
	 */
	public static Secs2 list() {
		return getBuilder().list();
	}
	
	/**
	 * Equivalent to {@code getBuilder().list(values)}.
	 * 
	 * @param values the values
	 * @return {@code <L[n]>}
	 */
	public static Secs2 list(Secs2... values) {
		return getBuilder().list(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().list(values)}.
	 * 
	 * @param values the values.
	 * @return {@code <L[n]>}
	 */
	public static Secs2 list(List<? extends Secs2> values) {
		return getBuilder().list(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().ascii(ascii)}.
	 * 
	 * @param ascii ascii string
	 * @return {@code <A[n] "ascii">}
	 */
	public static Secs2 ascii(CharSequence ascii) {
		return getBuilder().ascii(ascii);
	}
	
	/**
	 * Equivalent to {@code getBuilder().binary()}.
	 * 
	 * @return {@code <B[0]>}
	 */
	public static Secs2 binary() {
		return getBuilder().binary();
	}
	
	/**
	 * Equivalent to {@code getBuilder().binary(bs)}.
	 * 
	 * @param bs the bytes
	 * @return {@code <B[n] b...>}
	 */
	public static Secs2 binary(byte... bs) {
		return getBuilder().binary(bs);
	}
	
	/**
	 * Equivalent to {@code getBuilder().binary(bs)}.
	 * 
	 * @param bs the bytes
	 * @return {@code <B[n] b...>}
	 */
	public static Secs2 binary(List<Byte> bs) {
		return getBuilder().binary(bs);
	}
	
	/**
	 * Equivalent to {@code getBuilder().bool()}.
	 * 
	 * @return {@code <BOOLEAN[0]>}
	 */
	public static Secs2 bool() {
		return getBuilder().bool();
	}
	
	/**
	 * Equivalent to {@code getBuilder().bool(bools)}.
	 * 
	 * @param bools the Booleans
	 * @return {@code <BOOLEAN[n] bools...}
	 */
	public static Secs2 bool(boolean... bools) {
		return getBuilder().bool(bools);
	}
	
	/**
	 * Equivalent to {@code getBuilder().bool(bools)}.
	 * 
	 * @param bools the Booleans
	 * @return {@code <BOOLEAN[n] bool...>}
	 */
	public static Secs2 bool(List<Boolean> bools) {
		return getBuilder().bool(bools);
	}
	
	/**
	 * Equivalent to {@code getBuilder().int1()}.
	 * 
	 * @return {@code <I1[0]>}
	 */
	public static Secs2 int1() {
		return getBuilder().int1();
	}
	
	/**
	 * Equivalent to {@code getBuilder().int1(values)}.
	 * 
	 * @param values the int values
	 * @return {@code <I1[n] n...>}
	 */
	public static Secs2 int1(int... values) {
		return getBuilder().int1(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().int1(values)}.
	 * 
	 * @param values the long values
	 * @return {@code <I1[n] n...>}
	 */
	public static Secs2 int1(long... values) {
		return getBuilder().int1(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().int1(values)}.
	 * 
	 * @param values the BigInteger values.
	 * @return {@code <I1[n] n...>}
	 */
	public static Secs2 int1(BigInteger... values) {
		return getBuilder().int1(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().int1(values)}.
	 * 
	 * @param values the Number values.
	 * @return {@code <I1[n] n...>}
	 */
	public static Secs2 int1(List<? extends Number> values) {
		return getBuilder().int1(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().int2()}.
	 * 
	 * @return {@code <I2[0]>}
	 */
	public static Secs2 int2() {
		return getBuilder().int2();
	}
	
	/**
	 * Equivalent to {@code getBuilder().int2(values)}.
	 * 
	 * @param values the int values
	 * @return {@code <I2[n] v...}
	 */
	public static Secs2 int2(int... values) {
		return getBuilder().int2(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().int2(values)}.
	 * 
	 * @param values the long values
	 * @return {@code <I2[n] n...>}
	 */
	public static Secs2 int2(long... values) {
		return getBuilder().int2(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().int2(values)}.
	 * 
	 * @param values the BigInteger values
	 * @return {@code <I2[n] n...>}
	 */
	public static Secs2 int2(BigInteger... values) {
		return getBuilder().int2(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().int2(values)}.
	 * 
	 * @param values List of Number
	 * @return {@code <I2[n] n...>}
	 */
	public static Secs2 int2(List<? extends Number> values) {
		return getBuilder().int2(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().int4()}.
	 * 
	 * @return {@code <L4[0]>}
	 */
	public static Secs2 int4() {
		return getBuilder().int4();
	}
	
	/**
	 * Equivalent to {@code getBuilder().int4(values)}.
	 * 
	 * @param values the int values
	 * @return {@code <I4[n] n...>}
	 */
	public static Secs2 int4(int... values) {
		return getBuilder().int4(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().int4(values)}.
	 * 
	 * @param values the long values
	 * @return {@code <I4[n] n...>}
	 */
	public static Secs2 int4(long... values) {
		return getBuilder().int4(values);
	}
	
	/**
	 * Equivalent to {@code getBuilder().int4(values)}.
	 * 
	 * @param values the BigInteger values
	 * @return {@code <I4[n] n...>}
	 */
	public static Secs2 int4(BigInteger... values) {
		return getBuilder().int4(values);
	}
	
	/**
	 * Same as Secs2Builder#int4(List).
	 * 
	 * @param values List of Numbers
	 * @return {@code <I4[n] n...>}
	 * @see Secs2Builder#int4(List)
	 */
	public static Secs2 int4(List<? extends Number> values) {
		return getBuilder().int4(values);
	}
	
	/**
	 * Same as Secs2Builder#int8().
	 * 
	 * @return {@code <I8[0]>}
	 * @see Secs2Builder#int8()
	 */
	public static Secs2 int8() {
		return getBuilder().int8();
	}
	
	/**
	 * Same as Secs2Builder#int8(int...).
	 * 
	 * @param values the int values
	 * @return {@code <I8[n] n...>}
	 * @see Secs2Builder#int8(int...)
	 */
	public static Secs2 int8(int... values) {
		return getBuilder().int8(values);
	}
	
	/**
	 * Same as Secs2Builder#int8(long...).
	 * 
	 * @param values the long values
	 * @return {@code <I8[n] n...>}
	 * @see Secs2Builder#int8(long...)
	 */
	public static Secs2 int8(long... values) {
		return getBuilder().int8(values);
	}
	
	/**
	 * Same as Secs2Builder#int8(BigInteger...).
	 * 
	 * @param values the BigInteger values
	 * @return {@code <I8[n] n...>}
	 * @see Secs2Builder#int8(BigInteger...)
	 */
	public static Secs2 int8(BigInteger... values) {
		return getBuilder().int8(values);
	}
	
	/**
	 * Same as Secs2Builder#int8(List).
	 * 
	 * @param values list of Numbers
	 * @return {@code <I8[n] n...>}
	 * @see Secs2Builder#int8(List)
	 */
	public static Secs2 int8(List<? extends Number> values) {
		return getBuilder().int8(values);
	}
	
	/**
	 * Same as Secs2Builder#uint1().
	 * 
	 * @return {@code <U1[0]>}
	 * @see Secs2Builder#uint1()
	 */
	public static Secs2 uint1() {
		return getBuilder().uint1();
	}
	
	/**
	 * Same as Secs2Builder#uint1(int...).
	 * 
	 * @param values the int values
	 * @return {@code <U1[n] n...>}
	 * @see Secs2Builder#uint1(int...)
	 */
	public static Secs2 uint1(int... values) {
		return getBuilder().uint1(values);
	}
	
	/**
	 * Same as Secs2Builder#uint1(long...).
	 * 
	 * @param values the long values
	 * @return {@code <U1[n] n...>}
	 * @see Secs2Builder#uint1(long...)
	 */
	public static Secs2 uint1(long... values) {
		return getBuilder().uint1(values);
	}
	
	/**
	 * Same as Secs2Builder#uint1(BigInteger...).
	 * 
	 * @param values the BigInteger values
	 * @return {@code <U1[n] n...>}
	 * @see Secs2Builder#uint1(BigInteger...)
	 */
	public static Secs2 uint1(BigInteger... values) {
		return getBuilder().uint1(values);
	}
	
	/**
	 * Same as Secs2Builder#uint1(List).
	 * 
	 * @param values list of Numbers
	 * @return {@code <U1[n] n...>}
	 * @see Secs2Builder#uint1(List)
	 */
	public static Secs2 uint1(List<? extends Number> values) {
		return getBuilder().uint1(values);
	}
	
	/**
	 * Same as Secs2Builder#uint2().
	 * 
	 * @return {@code <U2[0]>}
	 * @see Secs2Builder#uint2()
	 */
	public static Secs2 uint2() {
		return getBuilder().uint2();
	}
	
	/**
	 * Same as Secs2Builder#uint2(int...).
	 * 
	 * @param values the int values
	 * @return {@code <U2[n] n...>}
	 * @see Secs2Builder#uint2(int...)
	 */
	public static Secs2 uint2(int... values) {
		return getBuilder().uint2(values);
	}
	
	/**
	 * Same as Secs2Builder#uint2(long...).
	 * 
	 * @param values the long values
	 * @return {@code <U2[n] n...>}
	 * @see Secs2Builder#uint2(long...)
	 */
	public static Secs2 uint2(long... values) {
		return getBuilder().uint2(values);
	}
	
	/**
	 * Same as Secs2Builder#uint2(BigInteger...).
	 * 
	 * @param values the BigInteger values
	 * @return {@code <U2[n] n...>}
	 * @see Secs2Builder#uint2(BigInteger...)
	 */
	public static Secs2 uint2(BigInteger... values) {
		return getBuilder().uint2(values);
	}
	
	/**
	 * Same as Secs2Builder#uint2(List).
	 * 
	 * @param values list of Numbers
	 * @return {@code <U2[n] n...>}
	 * @see Secs2Builder#uint2(List)
	 */
	public static Secs2 uint2(List<? extends Number> values) {
		return getBuilder().uint2(values);
	}
	
	/**
	 * Same as Secs2Builder#uint4().
	 * 
	 * @return {@code <U4[0]>}
	 * @see Secs2Builder#uint4()
	 */
	public static Secs2 uint4() {
		return getBuilder().uint4();
	}
	
	/**
	 * Same as Secs2Builder#uint4(int...).
	 * 
	 * @param values the int values
	 * @return {@code <U4[n] n...>}
	 * @see Secs2Builder#uint4(int...)
	 */
	public static Secs2 uint4(int... values) {
		return getBuilder().uint4(values);
	}
	
	/**
	 * Same as Secs2Builder#uint4(long...).
	 * 
	 * @param values the long values
	 * @return {@code <U4[n] n...>}
	 * @see Secs2Builder#uint4(long...)
	 */
	public static Secs2 uint4(long... values) {
		return getBuilder().uint4(values);
	}
	
	/**
	 * Same as Secs2Builder#uint4(BigInteger...).
	 * 
	 * @param values the BigInteger values
	 * @return {@code <U4[n] n...>}
	 * @see Secs2Builder#uint4(BigInteger...)
	 */
	public static Secs2 uint4(BigInteger... values) {
		return getBuilder().uint4(values);
	}
	
	/**
	 * Same as Secs2Builder#uint4(List).
	 * 
	 * @param values list of Numbers
	 * @return {@code <U4[n] n...>}
	 * @see Secs2Builder#uint4(List)
	 */
	public static Secs2 uint4(List<? extends Number> values) {
		return getBuilder().uint4(values);
	}
	
	/**
	 * Same as Secs2Builder#uint8().
	 * 
	 * @return {@code <U8[0]>}
	 * @see Secs2Builder#uint8()
	 */
	public static Secs2 uint8() {
		return getBuilder().uint8();
	}
	
	/**
	 * Same as Secs2Builder#uint8(int...).
	 * 
	 * @param values the int values
	 * @return {@code <U8[n] n...>}
	 * @see Secs2Builder#uint8(int...)
	 */
	public static Secs2 uint8(int... values) {
		return getBuilder().uint8(values);
	}
	
	/**
	 * Same as Secs2Builder#uint8(long...).
	 * 
	 * @param values the long values
	 * @return {@code <U8[n] n...>}
	 * @see Secs2Builder#uint8(long...)
	 */
	public static Secs2 uint8(long... values) {
		return getBuilder().uint8(values);
	}
	
	/**
	 * Same as Secs2Builder#uint8(BigInteger...).
	 * 
	 * @param values the BigInteger values
	 * @return {@code <U8[n] n...>}
	 * @see Secs2Builder#uint8(BigInteger...)
	 */
	public static Secs2 uint8(BigInteger... values) {
		return getBuilder().uint8(values);
	}
	
	/**
	 * Same as Secs2Builder#uint8(List).
	 * 
	 * @param values list of Numbers
	 * @return {@code <U8[n] n...>}
	 * @see Secs2Builder#uint8(List)
	 */
	public static Secs2 uint8(List<? extends Number> values) {
		return getBuilder().uint8(values);
	}
	
	/**
	 * Same as Secs2Builder#float4().
	 * 
	 * @return {@code <F4[0]>}
	 * @see Secs2Builder#float4()
	 */
	public static Secs2 float4() {
		return getBuilder().float4();
	}
	
	/**
	 * Same as Secs2Builder#float4(float...).
	 * 
	 * @param values the float values
	 * @return {@code <F4[n] n...>}
	 * @see Secs2Builder#float4(float...)
	 */
	public static Secs2 float4(float... values) {
		return getBuilder().float4(values);
	}
	
	/**
	 * Same as Secs2Builder#float4(List).
	 * 
	 * @param values list of Numbers
	 * @return {@code <F4[n] n...>}
	 * @see Secs2Builder#float4(List)
	 */
	public static Secs2 float4(List<? extends Number> values) {
		return getBuilder().float4(values);
	}
	
	/**
	 * Same as Secs2Builder#float8().
	 * 
	 * @return {@code <F8[0]>}
	 * @see Secs2Builder#float8()
	 */
	public static Secs2 float8() {
		return getBuilder().float8();
	}
	
	/**
	 * Same as Secs2Builder#float8(double...).
	 * 
	 * @param values the double values
	 * @return {@code <F8[n] n...>}
	 * @see Secs2Builder#float8(double...)
	 */
	public static Secs2 float8(double... values) {
		return getBuilder().float8(values);
	}
	
	/**
	 * Same as Secs2Builder#float8(List).
	 * 
	 * @param values list of Numbers
	 * @return {@code <F8[n] n...>}
	 * @see Secs2Builder#float8(List)
	 */
	public static Secs2 float8(List<? extends Number> values) {
		return getBuilder().float8(values);
	}

}
