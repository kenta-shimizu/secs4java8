package com.shimizukenta.secs.secs2;

import java.math.BigInteger;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public interface Secs2 extends Iterable<Secs2> {
	
	default public boolean isEmpty() {
		return false;
	}
	
	default public Stream<Secs2> stream() {
		return Stream.empty();
	}
	
	@Override
	default public Iterator<Secs2> iterator() {
		return Collections.emptyIterator();
	}
	
	/**
	 * 
	 * @return item-count size
	 */
	public int size();
	
	public Secs2Item secs2Item();
	
	public String toJson();
	
	
	public Secs2 get();
	public Secs2 get(int... indices) throws Secs2Exception;
	public String getAscii(int... indices) throws Secs2Exception;
	public String getAscii() throws Secs2Exception;
	public byte getByte( int... indices ) throws Secs2Exception;
	public boolean getBoolean( int... indices ) throws Secs2Exception;
	public int getInt( int... indices ) throws Secs2Exception;
	public long getLong( int... indices ) throws Secs2Exception;
	public BigInteger getBigInteger( int... indices ) throws Secs2Exception;
	public float getFloat( int... indices ) throws Secs2Exception;
	public double getDouble( int... indices1 ) throws Secs2Exception;

	
	/* builder */
	
	public static Secs2Builder getBuilder() {
		return Secs2Builder.getInstance();
	}
	
	public static Secs2RawBytes empty() {
		return getBuilder().empty();
	}
	
	public static Secs2RawBytes raw(byte[] bs) {
		return getBuilder().raw(bs);
	}
	
	public static Secs2List list() {
		return getBuilder().list();
	}
	
	public static Secs2List list(Secs2... values) {
		return getBuilder().list(values);
	}
	
	public static Secs2List list(List<? extends Secs2> values) {
		return getBuilder().list(values);
	}
	
	public static Secs2Ascii ascii(CharSequence cs) {
		return getBuilder().ascii(cs);
	}
	
	public static Secs2Binary binary() {
		return getBuilder().binary();
	}
	
	public static Secs2Binary binary(byte... bs) {
		return getBuilder().binary(bs);
	}
	
	public static Secs2Binary binary(List<Byte> bs) {
		return getBuilder().binary(bs);
	}
	
	public static AbstractSecs2 bool() {
		return getBuilder().bool();
	}
	
	public static Secs2Boolean bool(boolean... bools) {
		return getBuilder().bool(bools);
	}
	
	public static Secs2Boolean bool(List<Boolean> bools) {
		return getBuilder().bool(bools);
	}
	
	public static Secs2Int1 int1() {
		return getBuilder().int1();
	}
	
	public static Secs2Int1 int1(int... values) {
		return getBuilder().int1(values);
	}
	
	public static Secs2Int1 int1(long... values) {
		return getBuilder().int1(values);
	}
	
	public static Secs2Int1 int1(BigInteger... values) {
		return getBuilder().int1(values);
	}

	public static Secs2Int1 int1(List<? extends Number> values) {
		return getBuilder().int1(values);
	}
	
	public static Secs2Int2 int2() {
		return getBuilder().int2();
	}
	
	public static Secs2Int2 int2(int... values) {
		return getBuilder().int2(values);
	}
	
	public static Secs2Int2 int2(long... values) {
		return getBuilder().int2(values);
	}
	
	public static Secs2Int2 int2(BigInteger... values) {
		return getBuilder().int2(values);
	}

	public static Secs2Int2 int2(List<? extends Number> values) {
		return getBuilder().int2(values);
	}
	
	public static Secs2Int4 int4() {
		return getBuilder().int4();
	}
	
	public static Secs2Int4 int4(int... values) {
		return getBuilder().int4(values);
	}
	
	public static Secs2Int4 int4(long... values) {
		return getBuilder().int4(values);
	}
	
	public static Secs2Int4 int4(BigInteger... values) {
		return getBuilder().int4(values);
	}

	public static Secs2Int4 int4(List<? extends Number> values) {
		return getBuilder().int4(values);
	}
	
	public static Secs2Int8 int8() {
		return getBuilder().int8();
	}
	
	public static Secs2Int8 int8(int... values) {
		return getBuilder().int8(values);
	}
	
	public static Secs2Int8 int8(long... values) {
		return getBuilder().int8(values);
	}
	
	public static Secs2Int8 int8(BigInteger... values) {
		return getBuilder().int8(values);
	}

	public static Secs2Int8 int8(List<? extends Number> values) {
		return getBuilder().int8(values);
	}
	
	public static Secs2Uint1 uint1() {
		return getBuilder().uint1();
	}
	
	public static Secs2Uint1 uint1(int... values) {
		return getBuilder().uint1(values);
	}
	
	public static Secs2Uint1 uint1(long... values) {
		return getBuilder().uint1(values);
	}
	
	public static Secs2Uint1 uint1(BigInteger... values) {
		return getBuilder().uint1(values);
	}

	public static Secs2Uint1 uint1(List<? extends Number> values) {
		return getBuilder().uint1(values);
	}

	public static Secs2Uint2 uint2() {
		return getBuilder().uint2();
	}
	
	public static Secs2Uint2 uint2(int... values) {
		return getBuilder().uint2(values);
	}
	
	public static Secs2Uint2 uint2(long... values) {
		return getBuilder().uint2(values);
	}
	
	public static Secs2Uint2 uint2(BigInteger... values) {
		return getBuilder().uint2(values);
	}

	public static Secs2Uint2 uint2(List<? extends Number> values) {
		return getBuilder().uint2(values);
	}

	public static Secs2Uint4 uint4() {
		return getBuilder().uint4();
	}
	
	public static Secs2Uint4 uint4(int... values) {
		return getBuilder().uint4(values);
	}
	
	public static Secs2Uint4 uint4(long... values) {
		return getBuilder().uint4(values);
	}
	
	public static Secs2Uint4 uint4(BigInteger... values) {
		return getBuilder().uint4(values);
	}

	public static Secs2Uint4 uint4(List<? extends Number> values) {
		return getBuilder().uint4(values);
	}

	public static Secs2Uint8 uint8() {
		return getBuilder().uint8();
	}
	
	public static Secs2Uint8 uint8(int... values) {
		return getBuilder().uint8(values);
	}
	
	public static Secs2Uint8 uint8(long... values) {
		return getBuilder().uint8(values);
	}
	
	public static Secs2Uint8 uint8(BigInteger... values) {
		return getBuilder().uint8(values);
	}

	public static Secs2Uint8 uint8(List<? extends Number> values) {
		return getBuilder().uint8(values);
	}
	
	public static Secs2Float4 float4() {
		return getBuilder().float4();
	}
	
	public static Secs2Float4 float4(float... values) {
		return getBuilder().float4(values);
	}
	
	public static Secs2Float4 float4(List<? extends Number> values) {
		return getBuilder().float4(values);
	}
	
	public static Secs2Float8 float8() {
		return getBuilder().float8();
	}
	
	public static Secs2Float8 float8(double... values) {
		return getBuilder().float8(values);
	}
	
	public static Secs2Float8 float8(List<? extends Number> values) {
		return getBuilder().float8(values);
	}

}
