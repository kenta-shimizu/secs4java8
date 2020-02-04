package com.shimizukenta.secs.secs2;

import java.math.BigInteger;
import java.util.List;

public class Secs2Builder {

	private Secs2Builder() {
		/* Nothing */
	}
	
	private static class SingletonHolder {
		private static Secs2Builder inst = new Secs2Builder();
	}
	
	public static Secs2Builder getInstance() {
		return SingletonHolder.inst;
	}
	
	public Secs2RawBytes empty() {
		return new Secs2RawBytes();
	}
	
	public Secs2RawBytes raw(byte[] bs) {
		return new Secs2RawBytes(bs);
	}
	
	public Secs2List list() {
		return new Secs2List();
	}
	
	public Secs2List list(Secs2... values) {
		return new Secs2List(values);
	}
	
	public Secs2List list(List<? extends Secs2> values) {
		return new Secs2List(values);
	}
	
	public Secs2Ascii ascii(CharSequence cs) {
		return new Secs2Ascii(cs);
	}
	
	public Secs2Binary binary() {
		return new Secs2Binary();
	}
	
	public Secs2Binary binary(byte... bs) {
		return new Secs2Binary(bs);
	}
	
	public Secs2Binary binary(List<Byte> bs) {
		return new Secs2Binary(bs);
	}
	
	public AbstractSecs2 bool() {
		return new Secs2Boolean();
	}
	
	public Secs2Boolean bool(boolean... bools) {
		return new Secs2Boolean(bools);
	}
	
	public Secs2Boolean bool(List<Boolean> bools) {
		return new Secs2Boolean(bools);
	}
	
	public Secs2Int1 int1() {
		return new Secs2Int1();
	}
	
	public Secs2Int1 int1(int... values) {
		return new Secs2Int1(values);
	}
	
	public Secs2Int1 int1(long... values) {
		return new Secs2Int1(values);
	}
	
	public Secs2Int1 int1(BigInteger... values) {
		return new Secs2Int1(values);
	}

	public Secs2Int1 int1(List<? extends Number> values) {
		return new Secs2Int1(values);
	}
	
	public Secs2Int2 int2() {
		return new Secs2Int2();
	}
	
	public Secs2Int2 int2(int... values) {
		return new Secs2Int2(values);
	}
	
	public Secs2Int2 int2(long... values) {
		return new Secs2Int2(values);
	}
	
	public Secs2Int2 int2(BigInteger... values) {
		return new Secs2Int2(values);
	}

	public Secs2Int2 int2(List<? extends Number> values) {
		return new Secs2Int2(values);
	}
	
	public Secs2Int4 int4() {
		return new Secs2Int4();
	}
	
	public Secs2Int4 int4(int... values) {
		return new Secs2Int4(values);
	}
	
	public Secs2Int4 int4(long... values) {
		return new Secs2Int4(values);
	}
	
	public Secs2Int4 int4(BigInteger... values) {
		return new Secs2Int4(values);
	}

	public Secs2Int4 int4(List<? extends Number> values) {
		return new Secs2Int4(values);
	}
	
	public Secs2Int8 int8() {
		return new Secs2Int8();
	}
	
	public Secs2Int8 int8(int... values) {
		return new Secs2Int8(values);
	}
	
	public Secs2Int8 int8(long... values) {
		return new Secs2Int8(values);
	}
	
	public Secs2Int8 int8(BigInteger... values) {
		return new Secs2Int8(values);
	}

	public Secs2Int8 int8(List<? extends Number> values) {
		return new Secs2Int8(values);
	}
	
	public Secs2Uint1 uint1() {
		return new Secs2Uint1();
	}
	
	public Secs2Uint1 uint1(int... values) {
		return new Secs2Uint1(values);
	}
	
	public Secs2Uint1 uint1(long... values) {
		return new Secs2Uint1(values);
	}
	
	public Secs2Uint1 uint1(BigInteger... values) {
		return new Secs2Uint1(values);
	}

	public Secs2Uint1 uint1(List<? extends Number> values) {
		return new Secs2Uint1(values);
	}

	public Secs2Uint2 uint2() {
		return new Secs2Uint2();
	}
	
	public Secs2Uint2 uint2(int... values) {
		return new Secs2Uint2(values);
	}
	
	public Secs2Uint2 uint2(long... values) {
		return new Secs2Uint2(values);
	}
	
	public Secs2Uint2 uint2(BigInteger... values) {
		return new Secs2Uint2(values);
	}

	public Secs2Uint2 uint2(List<? extends Number> values) {
		return new Secs2Uint2(values);
	}

	public Secs2Uint4 uint4() {
		return new Secs2Uint4();
	}
	
	public Secs2Uint4 uint4(int... values) {
		return new Secs2Uint4(values);
	}
	
	public Secs2Uint4 uint4(long... values) {
		return new Secs2Uint4(values);
	}
	
	public Secs2Uint4 uint4(BigInteger... values) {
		return new Secs2Uint4(values);
	}

	public Secs2Uint4 uint4(List<? extends Number> values) {
		return new Secs2Uint4(values);
	}

	public Secs2Uint8 uint8() {
		return new Secs2Uint8();
	}
	
	public Secs2Uint8 uint8(int... values) {
		return new Secs2Uint8(values);
	}
	
	public Secs2Uint8 uint8(long... values) {
		return new Secs2Uint8(values);
	}
	
	public Secs2Uint8 uint8(BigInteger... values) {
		return new Secs2Uint8(values);
	}

	public Secs2Uint8 uint8(List<? extends Number> values) {
		return new Secs2Uint8(values);
	}
	
	public Secs2Float4 float4() {
		return new Secs2Float4();
	}
	
	public Secs2Float4 float4(float... values) {
		return new Secs2Float4(values);
	}
	
	public Secs2Float4 float4(List<? extends Number> values) {
		return new Secs2Float4(values);
	}
	
	public Secs2Float8 float8() {
		return new Secs2Float8();
	}
	
	public Secs2Float8 float8(double... values) {
		return new Secs2Float8(values);
	}
	
	public Secs2Float8 float8(List<? extends Number> values) {
		return new Secs2Float8(values);
	}


}
