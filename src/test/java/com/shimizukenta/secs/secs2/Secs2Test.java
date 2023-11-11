package com.shimizukenta.secs.secs2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.function.Function;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class Secs2Test {
	
	private static final byte[] byteValues = new byte[] {
			(byte)0x1,
			(byte)0x2,
			(byte)0x3};
	private static final List<Byte> listBytes = Arrays.asList(
			Byte.valueOf(byteValues[0]),
			Byte.valueOf(byteValues[1]),
			Byte.valueOf(byteValues[2]));
	
	private static final boolean[] booleanValues = new boolean[] {
			false,
			true};
	private static final List<Boolean> listBooleans = Arrays.asList(
			Boolean.valueOf(booleanValues[0]),
			Boolean.valueOf(booleanValues[1]));
	
	private static final long[] longValues = new long[] {1L, 2L, 3L};
	private static final int[] intValues = new int[] {
			(int)longValues[0],
			(int)longValues[1],
			(int)longValues[2]};
	private static final BigInteger[] bigIntegerValues = new BigInteger[] {
			BigInteger.valueOf(intValues[0]),
			BigInteger.valueOf(intValues[1]),
			BigInteger.valueOf(intValues[2])
	};
	private static final List<Number> listNumbers = Arrays.asList(bigIntegerValues);
	
	private static final float[] floatValues = new float[] {10.0F, 20.0F, 30.0F};
	private static final List<Float> listFloats = Arrays.asList(
			Float.valueOf(floatValues[0]),
			Float.valueOf(floatValues[1]),
			Float.valueOf(floatValues[2]));
	
	private static final double[] doubleValues = new double[] {100.0D, 200.0D, 300.0D};
	private static final List<Double> listDoubles = Arrays.asList(
			Double.valueOf(doubleValues[0]),
			Double.valueOf(doubleValues[1]),
			Double.valueOf(doubleValues[2]));
	
	
	/* Empty */
	@Test
	@DisplayName("Build Empty")
	public void testBuildEmpty() {
		
		final Secs2 s2 = Secs2.empty();
		
		assertTrue(s2.isEmpty());
	}
	
	/* LIST */
	@Test
	@DisplayName("Build LIST")
	public void testBuildList() {
		
		final Secs2Item s2item = Secs2Item.LIST;
		
		assertNull((Secs2[] ss) -> Secs2.list(ss));
		assertNull((List<Secs2> ss) -> Secs2.list(ss));
		
		assertSecs2List(Secs2.list(), s2item);
		assertSecs2List(Secs2.list(new Secs2[0]), s2item);
		assertSecs2List(Secs2.list(Collections.emptyList()), s2item);
		
		{
			Secs2 ss = Secs2.list(
					Secs2.list(
							Secs2.list()
							),
					Secs2.binary(),
					Secs2.bool(),
					Secs2.ascii(""),
					Secs2.int1(),
					Secs2.int2(),
					Secs2.int4(),
					Secs2.int8(),
					Secs2.uint1(),
					Secs2.uint2(),
					Secs2.uint4(),
					Secs2.uint8(),
					Secs2.float4(),
					Secs2.float8()
					);
			
			assertSecs2List(ss, s2item);
		}
	}
	
	/* BINARY */
	@Test
	@DisplayName("Build Binary")
	public void testBuildBinary() {
		
		final Secs2Item s2item = Secs2Item.BINARY;
		
		assertNull((byte[] bs) -> Secs2.binary(bs));
		assertNull((List<Byte> bs) -> Secs2.binary(bs));
		
		assertSecs2Binary(Secs2.binary(), s2item, new byte[0]);
		assertSecs2Binary(Secs2.binary(new byte[0]), s2item, new byte[0]);
		assertSecs2Binary(Secs2.binary(Collections.emptyList()), s2item, new byte[0]);

		assertSecs2Binary(Secs2.binary(byteValues), s2item, byteValues);
		assertSecs2Binary(Secs2.binary(listBytes), s2item, byteValues);
	}
	
	/* BOOLEAN values */
	@Test
	@DisplayName("Build Boolean")
	public void testBuildBoolean() {
		
		final Secs2Item s2item = Secs2Item.BOOLEAN;
		
		assertNull((boolean[] bools) -> Secs2.bool(bools));
		assertNull((List<Boolean> bools) -> Secs2.bool(bools));
		
		assertSecs2Boolean(Secs2.bool(), s2item, new boolean[0]);
		assertSecs2Boolean(Secs2.bool(new boolean[0]), s2item, new boolean[0]);
		assertSecs2Boolean(Secs2.bool(Collections.emptyList()), s2item, new boolean[0]);
		
		assertSecs2Boolean(Secs2.bool(booleanValues), s2item, booleanValues);
		assertSecs2Boolean(Secs2.bool(listBooleans), s2item, booleanValues);
	}
	
	/* ASCII */
	@Test
	@DisplayName("Build Ascii")
	public void testBuildAscii() {
		
		final Secs2Item s2item = Secs2Item.ASCII;
		
		assertNull((CharSequence cs) -> Secs2.ascii(cs));
		
		{
			final String str = "";
			assertSecs2Ascii(Secs2.ascii(str), s2item, str);
		}
		{
			final String str = "ASCII";
			assertSecs2Ascii(Secs2.ascii(str), s2item, str);
		}
	}
	
	/* I1 */
	@Test
	@DisplayName("Build I1")
	public void testBui1dInt1() {
		
		final Secs2Item s2item = Secs2Item.INT1;
		
		assertNull((int[] vv) -> Secs2.int1(vv));
		assertNull((long[] vv) -> Secs2.int1(vv));
		assertNull((BigInteger[] vv) -> Secs2.int1(vv));
		assertNull((List<Number> vv) -> Secs2.int1(vv));
		
		assertSecs2BigInteger(Secs2.int1(new int[0]), s2item, new BigInteger[0]);
		assertSecs2BigInteger(Secs2.int1(new long[0]), s2item, new BigInteger[0]);
		assertSecs2BigInteger(Secs2.int1(new BigInteger[0]), s2item, new BigInteger[0]);
		assertSecs2BigInteger(Secs2.int1(Collections.emptyList()), s2item, new BigInteger[0]);
		
		assertSecs2BigInteger(Secs2.int1(intValues), s2item, bigIntegerValues);
		assertSecs2BigInteger(Secs2.int1(longValues), s2item, bigIntegerValues);
		assertSecs2BigInteger(Secs2.int1(bigIntegerValues), s2item, bigIntegerValues);
		assertSecs2BigInteger(Secs2.int1(listNumbers), s2item, bigIntegerValues);
	}
	
	@Test
	@DisplayName("Build I2")
	public void testBuildInt2() {
		
		final Secs2Item s2item = Secs2Item.INT2;
		
		assertNull((int[] vv) -> Secs2.int2(vv));
		assertNull((long[] vv) -> Secs2.int2(vv));
		assertNull((BigInteger[] vv) -> Secs2.int2(vv));
		assertNull((List<Number> vv) -> Secs2.int2(vv));
		
		assertSecs2BigInteger(Secs2.int2(new int[0]), s2item, new BigInteger[0]);
		assertSecs2BigInteger(Secs2.int2(new long[0]), s2item, new BigInteger[0]);
		assertSecs2BigInteger(Secs2.int2(new BigInteger[0]), s2item, new BigInteger[0]);
		assertSecs2BigInteger(Secs2.int2(Collections.emptyList()), s2item, new BigInteger[0]);
		
		assertSecs2BigInteger(Secs2.int2(intValues), s2item, bigIntegerValues);
		assertSecs2BigInteger(Secs2.int2(longValues), s2item, bigIntegerValues);
		assertSecs2BigInteger(Secs2.int2(bigIntegerValues), s2item, bigIntegerValues);
		assertSecs2BigInteger(Secs2.int2(listNumbers), s2item, bigIntegerValues);
	}
	
	@Test
	@DisplayName("Build I4")
	public void testBuildInt4() {
		
		final Secs2Item s2item = Secs2Item.INT4;
		
		assertNull((int[] vv) -> Secs2.int4(vv));
		assertNull((long[] vv) -> Secs2.int4(vv));
		assertNull((BigInteger[] vv) -> Secs2.int4(vv));
		assertNull((List<Number> vv) -> Secs2.int4(vv));
		
		assertSecs2BigInteger(Secs2.int4(new int[0]), s2item, new BigInteger[0]);
		assertSecs2BigInteger(Secs2.int4(new long[0]), s2item, new BigInteger[0]);
		assertSecs2BigInteger(Secs2.int4(new BigInteger[0]), s2item, new BigInteger[0]);
		assertSecs2BigInteger(Secs2.int4(Collections.emptyList()), s2item, new BigInteger[0]);
		
		assertSecs2BigInteger(Secs2.int4(intValues), s2item, bigIntegerValues);
		assertSecs2BigInteger(Secs2.int4(longValues), s2item, bigIntegerValues);
		assertSecs2BigInteger(Secs2.int4(bigIntegerValues), s2item, bigIntegerValues);
		assertSecs2BigInteger(Secs2.int4(listNumbers), s2item, bigIntegerValues);
	}
	
	@Test
	@DisplayName("Build I8")
	public void testBuildInt8() {
		
		final Secs2Item s2item = Secs2Item.INT8;
		
		assertNull((int[] vv) -> Secs2.int8(vv));
		assertNull((long[] vv) -> Secs2.int8(vv));
		assertNull((BigInteger[] vv) -> Secs2.int8(vv));
		assertNull((List<Number> vv) -> Secs2.int8(vv));
		
		assertSecs2BigInteger(Secs2.int8(new int[0]), s2item, new BigInteger[0]);
		assertSecs2BigInteger(Secs2.int8(new long[0]), s2item, new BigInteger[0]);
		assertSecs2BigInteger(Secs2.int8(new BigInteger[0]), s2item, new BigInteger[0]);
		assertSecs2BigInteger(Secs2.int8(Collections.emptyList()), s2item, new BigInteger[0]);
		
		assertSecs2BigInteger(Secs2.int8(intValues), s2item, bigIntegerValues);
		assertSecs2BigInteger(Secs2.int8(longValues), s2item, bigIntegerValues);
		assertSecs2BigInteger(Secs2.int8(bigIntegerValues), s2item, bigIntegerValues);
		assertSecs2BigInteger(Secs2.int8(listNumbers), s2item, bigIntegerValues);
	}
	
	@Test
	@DisplayName("Build U1")
	public void testBuildUint1() {
		
		final Secs2Item s2item = Secs2Item.UINT1;
		
		assertNull((int[] vv) -> Secs2.uint1(vv));
		assertNull((long[] vv) -> Secs2.uint1(vv));
		assertNull((BigInteger[] vv) -> Secs2.uint1(vv));
		assertNull((List<Number> vv) -> Secs2.uint1(vv));
		
		assertSecs2BigInteger(Secs2.uint1(new int[0]), s2item, new BigInteger[0]);
		assertSecs2BigInteger(Secs2.uint1(new long[0]), s2item, new BigInteger[0]);
		assertSecs2BigInteger(Secs2.uint1(new BigInteger[0]), s2item, new BigInteger[0]);
		assertSecs2BigInteger(Secs2.uint1(Collections.emptyList()), s2item, new BigInteger[0]);
		
		assertSecs2BigInteger(Secs2.uint1(intValues), s2item, bigIntegerValues);
		assertSecs2BigInteger(Secs2.uint1(longValues), s2item, bigIntegerValues);
		assertSecs2BigInteger(Secs2.uint1(bigIntegerValues), s2item, bigIntegerValues);
		assertSecs2BigInteger(Secs2.uint1(listNumbers), s2item, bigIntegerValues);
	}
	
	@Test
	@DisplayName("Build U2")
	public void testBuildUint2() {
		
		final Secs2Item s2item = Secs2Item.UINT2;
		
		assertNull((int[] vv) -> Secs2.uint2(vv));
		assertNull((long[] vv) -> Secs2.uint2(vv));
		assertNull((BigInteger[] vv) -> Secs2.uint2(vv));
		assertNull((List<Number> vv) -> Secs2.uint2(vv));
		
		assertSecs2BigInteger(Secs2.uint2(intValues), s2item, bigIntegerValues);
		assertSecs2BigInteger(Secs2.uint2(longValues), s2item, bigIntegerValues);
		assertSecs2BigInteger(Secs2.uint2(bigIntegerValues), s2item, bigIntegerValues);
		assertSecs2BigInteger(Secs2.uint2(listNumbers), s2item, bigIntegerValues);
	}
	
	@Test
	@DisplayName("Build U4 Value")
	public void testBuildUint4Value() {
		
		final Secs2Item s2item = Secs2Item.UINT4;
		
		assertNull((int[] vv) -> Secs2.uint4(vv));
		assertNull((long[] vv) -> Secs2.uint4(vv));
		assertNull((BigInteger[] vv) -> Secs2.uint4(vv));
		assertNull((List<Number> vv) -> Secs2.uint4(vv));
		
		assertSecs2BigInteger(Secs2.uint4(intValues), s2item, bigIntegerValues);
		assertSecs2BigInteger(Secs2.uint4(longValues), s2item, bigIntegerValues);
		assertSecs2BigInteger(Secs2.uint4(bigIntegerValues), s2item, bigIntegerValues);
		assertSecs2BigInteger(Secs2.uint4(listNumbers), s2item, bigIntegerValues);
	}
	
	@Test
	@DisplayName("Build U8")
	public void testBuildUint8() {
		
		final Secs2Item s2item = Secs2Item.UINT8;
		
		assertNull((int[] vv) -> Secs2.uint8(vv));
		assertNull((long[] vv) -> Secs2.uint8(vv));
		assertNull((BigInteger[] vv) -> Secs2.uint8(vv));
		assertNull((List<Number> vv) -> Secs2.uint8(vv));
		
		assertSecs2BigInteger(Secs2.uint8(intValues), s2item, bigIntegerValues);
		assertSecs2BigInteger(Secs2.uint8(longValues), s2item, bigIntegerValues);
		assertSecs2BigInteger(Secs2.uint8(bigIntegerValues), s2item, bigIntegerValues);
		assertSecs2BigInteger(Secs2.uint8(listNumbers), s2item, bigIntegerValues);
	}
	
	/* F4 */
	@Test
	@DisplayName("Build F4")
	public void testBuildFloat4() {
		
		final Secs2Item s2item = Secs2Item.FLOAT4;
		
		assertNull((float[] vv) -> Secs2.float4(vv));
		assertNull((List<Float> vv) -> Secs2.float4(vv));
		
		assertSecs2Float4(Secs2.float4(), s2item, new float[0]);
		assertSecs2Float4(Secs2.float4(new float[0]), s2item, new float[0]);
		assertSecs2Float4(Secs2.float4(Collections.emptyList()), s2item, new float[0]);
		
		assertSecs2Float4(Secs2.float4(floatValues), s2item, floatValues);
		assertSecs2Float4(Secs2.float4(listFloats), s2item, floatValues);
	}
	
	/* F8 */
	@Test
	@DisplayName("Build F8")
	public void testBuildFloat8() {
		
		final Secs2Item s2item = Secs2Item.FLOAT8;
		
		assertNull((double[] vv) -> Secs2.float8(vv));
		assertNull((List<Double> vv) -> Secs2.float8(vv));
		
		assertSecs2Float8(Secs2.float8(), s2item, new double[0]);
		assertSecs2Float8(Secs2.float8(new double[0]), s2item, new double[0]);
		assertSecs2Float8(Secs2.float8(Collections.emptyList()), s2item, new double[0]);
		
		assertSecs2Float8(Secs2.float8(doubleValues), s2item, doubleValues);
		assertSecs2Float8(Secs2.float8(listDoubles), s2item, doubleValues);
	}
	
	
	private static interface Secs2Supplier<T> {
		public T get() throws Secs2Exception;
	}
	
	private static interface Secs2Function<T> {
		public T get(int[] indices) throws Secs2Exception;
	}
	
	private <T> T assertSecs2GetSuccess(Secs2Supplier<T> getter) {
		try {
			return getter.get();
			/* success */
		}
		catch (Secs2Exception e) {
			fail(e);
		}
		return null;
	}
	
	private <T> T assertSecs2GetFail(Secs2Supplier<T> getter) {
		try {
			T v = getter.get();
			fail(Objects.toString(v));
		}
		catch (Secs2Exception e) {
			/* success */
		}
		return null;
	}
	
	private <T> T assertSecs2GetSuccess(Secs2Function<T> getter, int... indices) {
		try {
			return getter.get(indices);
			/* success */
		}
		catch (Secs2Exception e) {
			fail(e);
		}
		return null;
	}
	
	private <T> T assertSecs2GetFail(Secs2Function<T> getter, int... indices) {
		try {
			T v = getter.get(indices);
			fail(Objects.toString(v));
		}
		catch (Secs2Exception e) {
			/* success */
		}
		return null;
	}
	
	private static interface Secs2OptionalSupplier<T> {
		public Optional<T> optional();
	}
	
	private static interface Secs2OptionalFunction<T> {
		public Optional<T> optional(int[] indices);
	}
	
	private static interface Secs2OptionalIntFunction {
		public OptionalInt optionalInt(int[] indices);
	}
	
	private static interface Secs2OptionalLongFunction {
		public OptionalLong optionalLong(int[] indices);
	}
	
	private static interface Secs2OptionalDoubleFunction {
		public OptionalDouble optionalDouble(int[] indices);
	}
	
	private <T> Optional<T> assertSecs2OptionalSuccess(Secs2OptionalSupplier<T> getter) {
		Optional<T> op = getter.optional();
		assertTrue(op.isPresent());
		return op;
	}
	
	private <T> Optional<T> assertSecs2OptionalSuccess(Secs2OptionalFunction<T> getter, int... indices) {
		Optional<T> op = getter.optional(indices);
		assertTrue(op.isPresent());
		return op;
	}
	
	private OptionalInt assertSecs2OptionalIntSuccess(Secs2OptionalIntFunction getter, int... indices) {
		OptionalInt op = getter.optionalInt(indices);
		assertTrue(op.isPresent());
		return op;
	}
	
	private OptionalLong assertSecs2OptionalLongSuccess(Secs2OptionalLongFunction getter, int... indices) {
		OptionalLong op = getter.optionalLong(indices);
		assertTrue(op.isPresent());
		return op;
	}
	
	private OptionalDouble assertSecs2OptionalDoubleSuccess(Secs2OptionalDoubleFunction getter, int... indices) {
		OptionalDouble op = getter.optionalDouble(indices);
		assertTrue(op.isPresent());
		return op;
	}
	
	private <T> Optional<T> assertSecs2OptionalFail(Secs2OptionalSupplier<T> getter) {
		Optional<T> op = getter.optional();
		assertFalse(op.isPresent());
		return op;
	}
	
	private <T> Optional<T> assertSecs2OptionalFail(Secs2OptionalFunction<T> getter, int... indices) {
		Optional<T> op = getter.optional(indices);
		assertFalse(op.isPresent());
		return op;
	}
	
	private OptionalInt assertSecs2OptionalIntFail(Secs2OptionalIntFunction getter, int... indices) {
		OptionalInt op = getter.optionalInt(indices);
		assertFalse(op.isPresent());
		return op;
	}
	
	private OptionalLong assertSecs2OptionalLongFail(Secs2OptionalLongFunction getter, int... indices) {
		OptionalLong op = getter.optionalLong(indices);
		assertFalse(op.isPresent());
		return op;
	}
	
	private OptionalDouble assertSecs2OptionalDoubleFail(Secs2OptionalDoubleFunction getter, int... indices) {
		OptionalDouble op = getter.optionalDouble(indices);
		assertFalse(op.isPresent());
		return op;
	}
	
	private <T> void assertNull(Function<T, Secs2> func) {
		try {
			Secs2 s2 = func.apply(null);
			fail(Objects.toString(s2));
		}
		catch (NullPointerException e) {
			/* success */
		}
	}
	
	private void assertSecs2List(Secs2 s2, Secs2Item s2item) {
		
		assertEquals(s2.secs2Item(), s2item);
		
		assertSecs2GetFail(s2::getByte, 0);
		assertSecs2GetFail(s2::getBytes);
		assertSecs2GetFail(s2::getBoolean, 0);
		assertSecs2GetFail(s2::getAscii);
		assertSecs2GetFail(s2::getInt, 0);
		assertSecs2GetFail(s2::getLong, 0);
		assertSecs2GetFail(s2::getBigInteger, 0);
		assertSecs2GetFail(s2::getFloat, 0);
		assertSecs2GetFail(s2::getDouble, 0);
		assertSecs2GetFail(s2::getNumber, 0);
		
		assertSecs2OptionalFail(s2::optionalByte, 0);
		assertSecs2OptionalFail(s2::optionalBytes);
		assertSecs2OptionalFail(s2::optionalBoolean, 0);
		assertSecs2OptionalFail(s2::optionalAscii);
		assertSecs2OptionalIntFail(s2::optionalInt, 0);
		assertSecs2OptionalLongFail(s2::optionalLong, 0);
		assertSecs2OptionalDoubleFail(s2::optionalDouble, 0);
		assertSecs2OptionalFail(s2::optionalBigInteger, 0);
		assertSecs2OptionalFail(s2::optionalNumber, 0);
		
		for (int i = 0, m = s2.size(); i < m; ++i) {
			
			final Secs2 ss = assertSecs2GetSuccess(s2::get, i);
			
			if (ss.secs2Item() == s2item) {
				assertSecs2List(ss, s2item);
			}
		}
	}
	
	private void assertSecs2Boolean(Secs2 s2, Secs2Item s2item, boolean[] ref) {
		
		assertEquals(s2.secs2Item(), s2item);
		
		assertSecs2GetSuccess(s2::get);
		assertSecs2GetFail(s2::get, 0);
		
		assertSecs2GetFail(s2::getByte, 0);
		assertSecs2GetFail(s2::getByte, 0, 0);
		
		assertSecs2GetFail(s2::getBytes);
		assertSecs2GetFail(s2::getBytes, 0);
		
		assertSecs2GetFail(s2::getAscii);
		assertSecs2GetFail(s2::getAscii, 0);
		
		assertSecs2GetFail(s2::getInt, 0);
		assertSecs2GetFail(s2::getInt, 0, 0);
		
		assertSecs2GetFail(s2::getLong, 0);
		assertSecs2GetFail(s2::getLong, 0, 0);
		
		assertSecs2GetFail(s2::getBigInteger, 0);
		assertSecs2GetFail(s2::getBigInteger, 0, 0);
		
		assertSecs2GetFail(s2::getFloat, 0);
		assertSecs2GetFail(s2::getFloat, 0, 0);
		
		assertSecs2GetFail(s2::getDouble, 0);
		assertSecs2GetFail(s2::getDouble, 0, 0);
		
		assertSecs2GetFail(s2::getNumber, 0);
		assertSecs2GetFail(s2::getNumber, 0, 0);
		
		assertSecs2OptionalSuccess(s2::optional);
		assertSecs2OptionalFail(s2::optional, 0);
		assertSecs2OptionalFail(s2::optional, 0, 0);
		
		assertSecs2OptionalFail(s2::optionalByte, 0);
		assertSecs2OptionalFail(s2::optionalByte, 0, 0);
		
		assertSecs2OptionalFail(s2::optionalBytes);
		assertSecs2OptionalFail(s2::optionalBytes, 0);
		
		assertSecs2OptionalFail(s2::optionalAscii);
		assertSecs2OptionalFail(s2::optionalAscii, 0);
		
		assertSecs2OptionalIntFail(s2::optionalInt, 0);
		assertSecs2OptionalIntFail(s2::optionalInt, 0, 0);
		
		assertSecs2OptionalLongFail(s2::optionalLong, 0);
		assertSecs2OptionalLongFail(s2::optionalLong, 0, 0);
		
		assertSecs2OptionalFail(s2::optionalBigInteger, 0);
		assertSecs2OptionalFail(s2::optionalBigInteger, 0, 0);
		
		assertSecs2OptionalDoubleFail(s2::optionalDouble, 0);
		assertSecs2OptionalDoubleFail(s2::optionalDouble, 0, 0);
		
		assertSecs2OptionalFail(s2::optionalNumber, 0);
		assertSecs2OptionalFail(s2::optionalNumber, 0, 0);
		
		for (int i = 0, m = s2.size(); i < m; ++i) {
			
			assertEquals(
					assertSecs2GetSuccess(s2::getBoolean, i),
					ref[i]);
			assertSecs2GetFail(s2::getBoolean, 0, i);
			
			assertEquals(
					assertSecs2OptionalSuccess(s2::optionalBoolean, i).get().booleanValue(),
					ref[i]);
			assertSecs2OptionalFail(s2::optionalBoolean, 0, i);
		}
	}
	
	private void assertSecs2Ascii(Secs2 s2, Secs2Item s2item, CharSequence ref) {
		
		assertEquals(s2.secs2Item(), s2item);
		
		assertSecs2GetSuccess(s2::get);
		assertSecs2GetFail(s2::get, 0);
		
		assertSecs2GetFail(s2::getByte, 0);
		assertSecs2GetFail(s2::getByte, 0, 0);
		
		assertSecs2GetFail(s2::getBytes);
		assertSecs2GetFail(s2::getBytes, 0);
		
		assertSecs2GetFail(s2::getBoolean, 0);
		assertSecs2GetFail(s2::getBoolean, 0, 0);
		
		assertEquals(assertSecs2GetSuccess(s2::getAscii), ref.toString());
		assertSecs2GetFail(s2::getAscii, 0);
		
		assertSecs2GetFail(s2::getInt, 0);
		assertSecs2GetFail(s2::getInt, 0, 0);
		
		assertSecs2GetFail(s2::getLong, 0);
		assertSecs2GetFail(s2::getLong, 0, 0);
		
		assertSecs2GetFail(s2::getBigInteger, 0);
		assertSecs2GetFail(s2::getBigInteger, 0, 0);
		
		assertSecs2GetFail(s2::getFloat, 0);
		assertSecs2GetFail(s2::getFloat, 0, 0);
		
		assertSecs2GetFail(s2::getDouble, 0);
		assertSecs2GetFail(s2::getDouble, 0, 0);
		
		assertSecs2GetFail(s2::getNumber, 0);
		assertSecs2GetFail(s2::getNumber, 0, 0);
		
		assertSecs2OptionalSuccess(s2::optional);
		assertSecs2OptionalFail(s2::optional, 0);
		assertSecs2OptionalFail(s2::optional, 0, 0);
		
		assertSecs2OptionalFail(s2::optionalByte, 0);
		assertSecs2OptionalFail(s2::optionalByte, 0, 0);
		
		assertSecs2OptionalFail(s2::optionalBytes);
		assertSecs2OptionalFail(s2::optionalBytes, 0);
		
		assertSecs2OptionalFail(s2::optionalBoolean, 0);
		assertSecs2OptionalFail(s2::optionalBoolean, 0, 0);
		
		assertEquals(assertSecs2OptionalSuccess(s2::optionalAscii).orElse(null), ref.toString());
		assertSecs2OptionalFail(s2::optionalAscii, 0);
		
		assertSecs2OptionalIntFail(s2::optionalInt, 0);
		assertSecs2OptionalIntFail(s2::optionalInt, 0, 0);
		
		assertSecs2OptionalLongFail(s2::optionalLong, 0);
		assertSecs2OptionalLongFail(s2::optionalLong, 0, 0);
		
		assertSecs2OptionalFail(s2::optionalBigInteger, 0);
		assertSecs2OptionalFail(s2::optionalBigInteger, 0, 0);
		
		assertSecs2OptionalDoubleFail(s2::optionalDouble, 0);
		assertSecs2OptionalDoubleFail(s2::optionalDouble, 0, 0);
		
		assertSecs2OptionalFail(s2::optionalNumber, 0);
		assertSecs2OptionalFail(s2::optionalNumber, 0, 0);
	}
	
	private void assertSecs2Number(Secs2 s2, Secs2Item s2item) {
		
		assertEquals(s2.secs2Item(), s2item);
		
		assertSecs2GetSuccess(s2::get);
		assertSecs2GetFail(s2::get, 0);
		
		assertSecs2GetFail(s2::getBoolean, 0);
		assertSecs2GetFail(s2::getBoolean, 0, 0);
		
		assertSecs2GetFail(s2::getAscii);
		assertSecs2GetFail(s2::getAscii, 0);
		
		assertSecs2OptionalSuccess(s2::optional);
		assertSecs2OptionalFail(s2::optional, 0);
		
		assertSecs2OptionalFail(s2::optionalBoolean, 0);
		assertSecs2OptionalFail(s2::optionalBoolean, 0, 0);
		
		assertSecs2OptionalFail(s2::optionalAscii);
		assertSecs2OptionalFail(s2::optionalAscii, 0);
		
		for (int i = 0, m = s2.size(); i < m; ++i) {
			
			assertSecs2GetSuccess(s2::getInt, i);
			assertSecs2GetFail(s2::getInt, 0, i);
			
			assertSecs2GetSuccess(s2::getLong, i);
			assertSecs2GetFail(s2::getLong, 0, i);
			
			assertSecs2GetSuccess(s2::getFloat, i);
			assertSecs2GetFail(s2::getFloat, 0, i);
			
			assertSecs2GetSuccess(s2::getDouble, i);
			assertSecs2GetFail(s2::getDouble, 0, i);

			assertSecs2GetSuccess(s2::getNumber, i);
			assertSecs2GetFail(s2::getNumber, 0, i);
			
			assertSecs2OptionalIntSuccess(s2::optionalInt, i);
			assertSecs2OptionalIntFail(s2::optionalInt, 0, i);
			
			assertSecs2OptionalLongSuccess(s2::optionalLong, i);
			assertSecs2OptionalLongFail(s2::optionalLong, 0, i);
			
			assertSecs2OptionalDoubleSuccess(s2::optionalDouble, i);
			assertSecs2OptionalDoubleFail(s2::optionalDouble, 0, i);
			
			assertSecs2OptionalSuccess(s2::optionalNumber, i);
			assertSecs2OptionalFail(s2::optionalNumber, 0, i);
		}
	}
	
	private void assertSecs2Binary(Secs2 s2, Secs2Item s2item, byte[] ref) {
		
		assertSecs2Number(s2, s2item);
		
		assertSecs2GetFail(s2::getBigInteger, 0);
		assertSecs2GetFail(s2::getBigInteger, 0, 0);
		
		assertSecs2OptionalFail(s2::optionalBigInteger, 0);
		assertSecs2OptionalFail(s2::optionalBigInteger, 0, 0);
		
		for (int i = 0, m = s2.size(); i < m; ++i) {
			
			assertEquals(
					assertSecs2GetSuccess(s2::getByte, i),
					ref[i]);
			assertSecs2GetFail(s2::getByte, 0, i);
			
			assertEquals(
					assertSecs2OptionalSuccess(s2::optionalByte, i).get().byteValue(),
					ref[i]);
			assertSecs2OptionalFail(s2::optionalByte, 0, i);
		}
		
		assertTrue(
				Objects.deepEquals(
						assertSecs2GetSuccess(s2::getBytes),
						ref));
		assertSecs2GetFail(s2::getBytes, 0);
		
		assertTrue(
				Objects.deepEquals(
						assertSecs2OptionalSuccess(s2::optionalBytes).orElse(null),
						ref));
		assertSecs2OptionalFail(s2::optionalBytes, 0);
	}
	
	private void assertSecs2BigInteger(Secs2 s2, Secs2Item s2item, BigInteger[] ref) {
		
		assertSecs2Number(s2, s2item);
		
		assertSecs2GetFail(s2::getByte, 0);
		assertSecs2GetFail(s2::getByte, 0, 0);
		
		assertSecs2GetFail(s2::getBytes);
		assertSecs2GetFail(s2::getBytes, 0);
		
		assertSecs2OptionalFail(s2::optionalByte, 0);
		assertSecs2OptionalFail(s2::optionalByte, 0, 0);
		
		assertSecs2OptionalFail(s2::optionalBytes);
		assertSecs2OptionalFail(s2::optionalBytes, 0);
		
		for (int i = 0, m = s2.size(); i < m; ++i) {
			
			assertEquals(
					assertSecs2GetSuccess(s2::getInt, i),
					ref[i].intValue());
			assertSecs2GetFail(s2::getInt, 0, i);
			
			assertEquals(
					assertSecs2GetSuccess(s2::getLong, i),
					ref[i].longValue());
			assertSecs2GetFail(s2::getLong, 0, i);
			
			assertEquals(
					assertSecs2GetSuccess(s2::getBigInteger, i),
					ref[i]);
			assertSecs2GetFail(s2::getBigInteger, 0, i);
			
			assertEquals(
					assertSecs2OptionalIntSuccess(s2::optionalInt, i).getAsInt(),
					ref[i].intValue());
			assertSecs2OptionalIntFail(s2::optionalInt, 0, i);
			
			assertEquals(
					assertSecs2OptionalLongSuccess(s2::optionalLong, i).getAsLong(),
					ref[i].longValue());
			assertSecs2OptionalLongFail(s2::optionalLong, 0, i);
			
			assertEquals(
					assertSecs2OptionalSuccess(s2::optionalBigInteger, i).get(),
					ref[i]);
			assertSecs2OptionalFail(s2::optionalBigInteger, 0, i);
		}
	}
	
	private void assertSecs2Float4(Secs2 s2, Secs2Item s2item, float[] ref) {
		
		assertSecs2Number(s2, s2item);
		
		assertSecs2GetFail(s2::getByte, 0);
		assertSecs2GetFail(s2::getByte, 0, 0);
		
		assertSecs2GetFail(s2::getBytes);
		assertSecs2GetFail(s2::getBytes, 0);
		
		assertSecs2GetFail(s2::getBigInteger, 0);
		assertSecs2GetFail(s2::getBigInteger, 0, 0);
		
		assertSecs2OptionalFail(s2::optionalByte, 0);
		assertSecs2OptionalFail(s2::optionalByte, 0, 0);
		
		assertSecs2OptionalFail(s2::optionalBytes);
		assertSecs2OptionalFail(s2::optionalBytes, 0);
		
		assertSecs2OptionalFail(s2::optionalBigInteger, 0);
		assertSecs2OptionalFail(s2::optionalBigInteger, 0, 0);
		
		for (int i = 0, m = s2.size(); i < m; ++i) {
			
			assertEquals(assertSecs2GetSuccess(s2::getFloat, i), ref[i]);
			assertSecs2GetFail(s2::getFloat, 0, i);
			
			assertEquals(
					assertSecs2OptionalSuccess(s2::optionalNumber, i).get().floatValue(),
					ref[i]);
			assertSecs2OptionalFail(s2::optionalNumber, 0, i);
		}
	}
	
	private void assertSecs2Float8(Secs2 s2, Secs2Item s2item, double[] ref) {
		
		assertSecs2Number(s2, s2item);
		
		assertSecs2GetFail(s2::getByte, 0);
		assertSecs2GetFail(s2::getByte, 0, 0);
		
		assertSecs2GetFail(s2::getBytes);
		assertSecs2GetFail(s2::getBytes, 0);
		
		assertSecs2GetFail(s2::getBigInteger, 0);
		assertSecs2GetFail(s2::getBigInteger, 0, 0);
		
		assertSecs2OptionalFail(s2::optionalByte, 0);
		assertSecs2OptionalFail(s2::optionalByte, 0, 0);
		
		assertSecs2OptionalFail(s2::optionalBytes);
		assertSecs2OptionalFail(s2::optionalBytes, 0);
		
		assertSecs2OptionalFail(s2::optionalBigInteger, 0);
		assertSecs2OptionalFail(s2::optionalBigInteger, 0, 0);
		
		for (int i = 0, m = s2.size(); i < m; ++i) {
			
			assertEquals(assertSecs2GetSuccess(s2::getDouble, i), ref[i]);
			assertSecs2GetFail(s2::getDouble, 0, i);
			
			assertEquals(
					assertSecs2OptionalDoubleSuccess(s2::optionalDouble, i).getAsDouble(),
					ref[i]);
			assertSecs2OptionalDoubleFail(s2::optionalDouble, 0, i);
		}
	}
	
}
