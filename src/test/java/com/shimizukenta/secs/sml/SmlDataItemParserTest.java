package com.shimizukenta.secs.sml;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.math.BigInteger;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.secs2.Secs2Exception;
import com.shimizukenta.secs.secs2.Secs2Item;

class SmlDataItemParserTest {
	
	private static final String sml2Layer = "<L" +
											"  <L>" +
											"  <B 0x0>" +
											"  <A \"ASCII\">" +
											"  <I1 -1>" +
											"  <I2 -10>" +
											"  <I4 -100>" +
											"  <I8 -1000>" +
											"  <U1 1>" +
											"  <U2 10>" +
											"  <U4 100>" +
											"  <U8 1000>" +
											"  <F4 123.0>" +
											"  <F8 1234.0>" +
											"  <BOOLEAN FALSE>" +
											"  <BOOLEAN TRUE>" +
											">";
	
	private static final String sml3Layer = "<L" +
											"  <L>" +
											"  <L" +
											"    <L>" +
											"    <B 0x0>" +
											"    <A \"ASCII\">" +
											"    <I1 -1>" +
											"    <I2 -10>" +
											"    <I4 -100>" +
											"    <I8 -1000>" +
											"    <U1 1>" +
											"    <U2 10>" +
											"    <U4 100>" +
											"    <U8 1000>" +
											"    <F4 123.0>" +
											"    <F8 1234.0>" +
											"    <BOOLEAN FALSE>" +
											"    <BOOLEAN TRUE>" +
											"  >" +
											">";
	
	private static final SmlDataItemParser parser = SmlDataItemParser.newInstance();
	
	private static Secs2 parseSml(CharSequence sml) {
		try {
			return parser.parse(sml);
		}
		catch (SmlParseException e) {
			fail(e);
		}
		return null;
	}
	
	private static void assertParseFailed(CharSequence sml) {
		try {
			parser.parse(sml);
			fail("not reach");
		}
		catch (SmlParseException e) {
			/* success */
		}
	}
	
	private static void assertSecs2GetFailed(Secs2 ss, int... indices) {
		try {
			Secs2 r = ss.get(indices);
			fail(r.toString());
		}
		catch (Secs2Exception e) {
			/* success */
		}
	}
	
	@Test
	@DisplayName("parse success")
	void testParseSUccess() {
		
		try {
			
			{
				Secs2 ss = parseSml("");
				assertEquals(ss.isEmpty(), true);
			}
			{
				Secs2 ss = parseSml("<L>");
				assertEquals(ss.isEmpty(), false);
				assertEquals(ss.secs2Item(), Secs2Item.LIST);
				assertEquals(ss.size(), 0);
			}
			{
				Secs2 ss = parseSml("<L <L>>");
				assertEquals(ss.isEmpty(), false);
				assertEquals(ss.secs2Item(), Secs2Item.LIST);
				assertEquals(ss.size(), 1);
			}
			{
				Secs2 ss = parseSml("<L[2] <L><L>>");
				assertEquals(ss.isEmpty(), false);
				assertEquals(ss.secs2Item(), Secs2Item.LIST);
				assertEquals(ss.size(), 2);
			}
			
			{
				Secs2 ss = parseSml("<B>");
				assertEquals(ss.isEmpty(), false);
				assertEquals(ss.secs2Item(), Secs2Item.BINARY);
				assertEquals(ss.size(), 0);
			}
			{
				Secs2 ss = parseSml("<B 0>");
				assertEquals(ss.isEmpty(), false);
				assertEquals(ss.secs2Item(), Secs2Item.BINARY);
				assertEquals(ss.size(), 1);
				assertEquals(ss.getByte(0), (byte)0x00);
			}
			{
				Secs2 ss = parseSml("<B[2] 1 0xFF>");
				assertEquals(ss.isEmpty(), false);
				assertEquals(ss.secs2Item(), Secs2Item.BINARY);
				assertEquals(ss.size(), 2);
				assertEquals(ss.getByte(0), (byte)0x01);
				assertEquals(ss.getByte(1), (byte)0xFF);
			}
			
			{
				Secs2 ss = parseSml("<BOOLEAN>");
				assertEquals(ss.isEmpty(), false);
				assertEquals(ss.secs2Item(), Secs2Item.BOOLEAN);
			}
			{
				Secs2 ss = parseSml("<BOOLEAN FALSE>");
				assertEquals(ss.isEmpty(), false);
				assertEquals(ss.secs2Item(), Secs2Item.BOOLEAN);
				assertEquals(ss.size(), 1);
				assertEquals(ss.getBoolean(0), false);
			}
			{
				Secs2 ss = parseSml("<BOOLEAN TRUE>");
				assertEquals(ss.isEmpty(), false);
				assertEquals(ss.secs2Item(), Secs2Item.BOOLEAN);
				assertEquals(ss.size(), 1);
				assertEquals(ss.getBoolean(0), true);
			}
			{
				Secs2 ss = parseSml("<BOOLEAN FALSE F 0x00>");
				assertEquals(ss.isEmpty(), false);
				assertEquals(ss.secs2Item(), Secs2Item.BOOLEAN);
				assertEquals(ss.size(), 3);
				assertEquals(ss.getBoolean(0), false);
				assertEquals(ss.getBoolean(1), false);
				assertEquals(ss.getBoolean(2), false);
			}
			{
				Secs2 ss = parseSml("<BOOLEAN TRUE T 0xFF>");
				assertEquals(ss.isEmpty(), false);
				assertEquals(ss.secs2Item(), Secs2Item.BOOLEAN);
				assertEquals(ss.size(), 3);
				assertEquals(ss.getBoolean(0), true);
				assertEquals(ss.getBoolean(1), true);
				assertEquals(ss.getBoolean(2), true);
			}
			
			{
				Secs2 ss = parseSml("<A \"\">");
				assertEquals(ss.isEmpty(), false);
				assertEquals(ss.secs2Item(), Secs2Item.ASCII);
				assertEquals(ss.size(), 0);
			}
			{
				Secs2 ss = parseSml("<A \"ASCII\">");
				assertEquals(ss.isEmpty(), false);
				assertEquals(ss.secs2Item(), Secs2Item.ASCII);
				assertEquals(ss.size(), 5);
				assertEquals(ss.getAscii(), "ASCII");
			}
			{
				Secs2 ss = parseSml("<A \"A\" 0x42 \"C\">");
				assertEquals(ss.isEmpty(), false);
				assertEquals(ss.secs2Item(), Secs2Item.ASCII);
				assertEquals(ss.size(), 3);
				assertEquals(ss.getAscii(), "ABC");
			}
			
			{
				Secs2 ss = parseSml("<I1>");
				assertEquals(ss.isEmpty(), false);
				assertEquals(ss.secs2Item(), Secs2Item.INT1);
				assertEquals(ss.size(), 0);
			}
			{
				Secs2 ss = parseSml("<I1 0>");
				assertEquals(ss.isEmpty(), false);
				assertEquals(ss.secs2Item(), Secs2Item.INT1);
				assertEquals(ss.size(), 1);
				assertEquals(ss.getInt(0), 0);
			}
			{
				Secs2 ss = parseSml("<I1 -1 2>");
				assertEquals(ss.isEmpty(), false);
				assertEquals(ss.secs2Item(), Secs2Item.INT1);
				assertEquals(ss.size(), 2);
				assertEquals(ss.getInt(0), -1);
				assertEquals(ss.getInt(1), 2);
			}
			
			{
				Secs2 ss = parseSml("<I2>");
				assertEquals(ss.isEmpty(), false);
				assertEquals(ss.secs2Item(), Secs2Item.INT2);
				assertEquals(ss.size(), 0);
			}
			{
				Secs2 ss = parseSml("<I2 0>");
				assertEquals(ss.isEmpty(), false);
				assertEquals(ss.secs2Item(), Secs2Item.INT2);
				assertEquals(ss.size(), 1);
				assertEquals(ss.getInt(0), 0);
			}
			{
				Secs2 ss = parseSml("<I2 -1 2>");
				assertEquals(ss.isEmpty(), false);
				assertEquals(ss.secs2Item(), Secs2Item.INT2);
				assertEquals(ss.size(), 2);
				assertEquals(ss.getInt(0), -1);
				assertEquals(ss.getInt(1), 2);
			}
			
			{
				Secs2 ss = parseSml("<I4>");
				assertEquals(ss.isEmpty(), false);
				assertEquals(ss.secs2Item(), Secs2Item.INT4);
				assertEquals(ss.size(), 0);
			}
			{
				Secs2 ss = parseSml("<I4 0>");
				assertEquals(ss.isEmpty(), false);
				assertEquals(ss.secs2Item(), Secs2Item.INT4);
				assertEquals(ss.size(), 1);
				assertEquals(ss.getInt(0), 0);
			}
			{
				Secs2 ss = parseSml("<I4 -1 2>");
				assertEquals(ss.isEmpty(), false);
				assertEquals(ss.secs2Item(), Secs2Item.INT4);
				assertEquals(ss.size(), 2);
				assertEquals(ss.getInt(0), -1);
				assertEquals(ss.getInt(1), 2);
			}

			{
				Secs2 ss = parseSml("<I8>");
				assertEquals(ss.isEmpty(), false);
				assertEquals(ss.secs2Item(), Secs2Item.INT8);
				assertEquals(ss.size(), 0);
			}
			{
				Secs2 ss = parseSml("<I8 0>");
				assertEquals(ss.isEmpty(), false);
				assertEquals(ss.secs2Item(), Secs2Item.INT8);
				assertEquals(ss.size(), 1);
				assertEquals(ss.getLong(0), 0L);
			}
			{
				Secs2 ss = parseSml("<I8 -1 2>");
				assertEquals(ss.isEmpty(), false);
				assertEquals(ss.secs2Item(), Secs2Item.INT8);
				assertEquals(ss.size(), 2);
				assertEquals(ss.getLong(0), -1L);
				assertEquals(ss.getLong(1), 2L);
			}
			
			{
				Secs2 ss = parseSml("<U1>");
				assertEquals(ss.isEmpty(), false);
				assertEquals(ss.secs2Item(), Secs2Item.UINT1);
				assertEquals(ss.size(), 0);
			}
			{
				Secs2 ss = parseSml("<U1 0>");
				assertEquals(ss.isEmpty(), false);
				assertEquals(ss.secs2Item(), Secs2Item.UINT1);
				assertEquals(ss.size(), 1);
				assertEquals(ss.getInt(0), 0);
			}
			{
				Secs2 ss = parseSml("<U1 1 2>");
				assertEquals(ss.isEmpty(), false);
				assertEquals(ss.secs2Item(), Secs2Item.UINT1);
				assertEquals(ss.size(), 2);
				assertEquals(ss.getInt(0), 1);
				assertEquals(ss.getInt(1), 2);
			}
			
			{
				Secs2 ss = parseSml("<U2>");
				assertEquals(ss.isEmpty(), false);
				assertEquals(ss.secs2Item(), Secs2Item.UINT2);
				assertEquals(ss.size(), 0);
			}
			{
				Secs2 ss = parseSml("<U2 0>");
				assertEquals(ss.isEmpty(), false);
				assertEquals(ss.secs2Item(), Secs2Item.UINT2);
				assertEquals(ss.size(), 1);
				assertEquals(ss.getInt(0), 0);
			}
			{
				Secs2 ss = parseSml("<U2 1 2>");
				assertEquals(ss.isEmpty(), false);
				assertEquals(ss.secs2Item(), Secs2Item.UINT2);
				assertEquals(ss.size(), 2);
				assertEquals(ss.getInt(0), 1);
				assertEquals(ss.getInt(1), 2);
			}
			
			{
				Secs2 ss = parseSml("<U4>");
				assertEquals(ss.isEmpty(), false);
				assertEquals(ss.secs2Item(), Secs2Item.UINT4);
				assertEquals(ss.size(), 0);
			}
			{
				Secs2 ss = parseSml("<U4 0>");
				assertEquals(ss.isEmpty(), false);
				assertEquals(ss.secs2Item(), Secs2Item.UINT4);
				assertEquals(ss.size(), 1);
				assertEquals(ss.getLong(0), 0L);
			}
			{
				Secs2 ss = parseSml("<U4 1 2>");
				assertEquals(ss.isEmpty(), false);
				assertEquals(ss.secs2Item(), Secs2Item.UINT4);
				assertEquals(ss.size(), 2);
				assertEquals(ss.getLong(0), 1L);
				assertEquals(ss.getLong(1), 2L);
			}
			
			{
				Secs2 ss = parseSml("<U8>");
				assertEquals(ss.isEmpty(), false);
				assertEquals(ss.secs2Item(), Secs2Item.UINT8);
				assertEquals(ss.size(), 0);
			}
			{
				Secs2 ss = parseSml("<U8 0>");
				assertEquals(ss.isEmpty(), false);
				assertEquals(ss.secs2Item(), Secs2Item.UINT8);
				assertEquals(ss.size(), 1);
				assertEquals(ss.getBigInteger(0), BigInteger.valueOf(0L));
			}
			{
				Secs2 ss = parseSml("<U8 1 2>");
				assertEquals(ss.isEmpty(), false);
				assertEquals(ss.secs2Item(), Secs2Item.UINT8);
				assertEquals(ss.size(), 2);
				assertEquals(ss.getBigInteger(0), BigInteger.valueOf(1L));
				assertEquals(ss.getBigInteger(1), BigInteger.valueOf(2L));
			}
			
			{
				Secs2 ss = parseSml("<F4>");
				assertEquals(ss.isEmpty(), false);
				assertEquals(ss.secs2Item(), Secs2Item.FLOAT4);
				assertEquals(ss.size(), 0);
			}
			{
				Secs2 ss = parseSml("<F4 0.0>");
				assertEquals(ss.isEmpty(), false);
				assertEquals(ss.secs2Item(), Secs2Item.FLOAT4);
				assertEquals(ss.size(), 1);
				assertEquals(ss.getFloat(0), 0.0F);
			}
			{
				Secs2 ss = parseSml("<F4 -1.0 2.0>");
				assertEquals(ss.isEmpty(), false);
				assertEquals(ss.secs2Item(), Secs2Item.FLOAT4);
				assertEquals(ss.size(), 2);
				assertEquals(ss.getFloat(0), -1.0F);
				assertEquals(ss.getFloat(1), 2.0F);
			}
			
			{
				Secs2 ss = parseSml("<F8>");
				assertEquals(ss.isEmpty(), false);
				assertEquals(ss.secs2Item(), Secs2Item.FLOAT8);
				assertEquals(ss.size(), 0);
			}
			{
				Secs2 ss = parseSml("<F8 0.0>");
				assertEquals(ss.isEmpty(), false);
				assertEquals(ss.secs2Item(), Secs2Item.FLOAT8);
				assertEquals(ss.size(), 1);
				assertEquals(ss.getDouble(0), 0.0D);
			}
			{
				Secs2 ss = parseSml("<F8 -1.0 2.0>");
				assertEquals(ss.isEmpty(), false);
				assertEquals(ss.secs2Item(), Secs2Item.FLOAT8);
				assertEquals(ss.size(), 2);
				assertEquals(ss.getDouble(0), -1.0D);
				assertEquals(ss.getDouble(1), 2.0D);
			}
			
			{
				Secs2 ss = parseSml(sml2Layer);
				assertEquals(ss.size(), 15);
				assertEquals(ss.get(0).secs2Item(), Secs2Item.LIST);
				assertEquals(ss.get(0).size(), 0);
				assertEquals(ss.get(1).secs2Item(), Secs2Item.BINARY);
				assertEquals(ss.getByte(1, 0), (byte)0x0);
				assertEquals(ss.get(3).secs2Item(), Secs2Item.INT1);
				assertEquals(ss.getInt(3, 0), -1);
				assertEquals(ss.get(4).secs2Item(), Secs2Item.INT2);
				assertEquals(ss.getInt(4, 0), -10);
				assertEquals(ss.get(5).secs2Item(), Secs2Item.INT4);
				assertEquals(ss.getInt(5, 0), -100);
				assertEquals(ss.get(6).secs2Item(), Secs2Item.INT8);
				assertEquals(ss.getInt(6, 0), -1000L);
				assertEquals(ss.get(7).secs2Item(), Secs2Item.UINT1);
				assertEquals(ss.getInt(7, 0), 1);
				assertEquals(ss.get(8).secs2Item(), Secs2Item.UINT2);
				assertEquals(ss.getInt(8, 0), 10);
				assertEquals(ss.get(9).secs2Item(), Secs2Item.UINT4);
				assertEquals(ss.getLong(9, 0), 100L);
				assertEquals(ss.get(10).secs2Item(), Secs2Item.UINT8);
				assertEquals(ss.getLong(10, 0), 1000L);
				assertEquals(ss.get(11).secs2Item(), Secs2Item.FLOAT4);
				assertEquals(ss.getFloat(11, 0), 123.0F);
				assertEquals(ss.get(12).secs2Item(), Secs2Item.FLOAT8);
				assertEquals(ss.getDouble(12, 0), 1234.0D);
				assertEquals(ss.get(13).secs2Item(), Secs2Item.BOOLEAN);
				assertEquals(ss.getBoolean(13, 0), false);
				assertEquals(ss.get(14).secs2Item(), Secs2Item.BOOLEAN);
				assertEquals(ss.getBoolean(14, 0), true);
			}
			
			
			{
				Secs2 ss = parseSml(sml3Layer);
				assertEquals(ss.get(1).size(), 15);
				assertEquals(ss.get(1, 0).secs2Item(), Secs2Item.LIST);
				assertEquals(ss.get(1, 0).size(), 0);
				assertEquals(ss.get(1, 1).secs2Item(), Secs2Item.BINARY);
				assertEquals(ss.getByte(1, 1, 0), (byte)0x0);
				assertEquals(ss.get(1, 3).secs2Item(), Secs2Item.INT1);
				assertEquals(ss.getInt(1, 3, 0), -1);
				assertEquals(ss.get(1, 4).secs2Item(), Secs2Item.INT2);
				assertEquals(ss.getInt(1, 4, 0), -10);
				assertEquals(ss.get(1, 5).secs2Item(), Secs2Item.INT4);
				assertEquals(ss.getInt(1, 5, 0), -100);
				assertEquals(ss.get(1, 6).secs2Item(), Secs2Item.INT8);
				assertEquals(ss.getInt(1, 6, 0), -1000L);
				assertEquals(ss.get(1, 7).secs2Item(), Secs2Item.UINT1);
				assertEquals(ss.getInt(1, 7, 0), 1);
				assertEquals(ss.get(1, 8).secs2Item(), Secs2Item.UINT2);
				assertEquals(ss.getInt(1, 8, 0), 10);
				assertEquals(ss.get(1, 9).secs2Item(), Secs2Item.UINT4);
				assertEquals(ss.getLong(1, 9, 0), 100L);
				assertEquals(ss.get(1, 10).secs2Item(), Secs2Item.UINT8);
				assertEquals(ss.getLong(1, 10, 0), 1000L);
				assertEquals(ss.get(1, 11).secs2Item(), Secs2Item.FLOAT4);
				assertEquals(ss.getFloat(1, 11, 0), 123.0F);
				assertEquals(ss.get(1, 12).secs2Item(), Secs2Item.FLOAT8);
				assertEquals(ss.getDouble(1, 12, 0), 1234.0D);
				assertEquals(ss.get(1, 13).secs2Item(), Secs2Item.BOOLEAN);
				assertEquals(ss.getBoolean(1, 13, 0), false);
				assertEquals(ss.get(1, 14).secs2Item(), Secs2Item.BOOLEAN);
				assertEquals(ss.getBoolean(1, 14, 0), true);
			}
		}
		catch (Secs2Exception e) {
			fail(e);
		}
	}
	
	@Test
	@DisplayName("parse failed")
	void testParseFailed() {
		
		assertParseFailed("<");
		assertParseFailed(">");
		assertParseFailed("<>");
		assertParseFailed("<X>");
		assertParseFailed("<L \"LIST\">");
		assertParseFailed("<L 100>");
		assertParseFailed("<B \"BINARY\">");
		assertParseFailed("<BOOLEAN X>");
		assertParseFailed("<A FAILED\">");
		assertParseFailed("<A \"FAILED>");
		assertParseFailed("<I1 \"100\">");
		assertParseFailed("<I2 \"100\">");
		assertParseFailed("<I4 \"100\">");
		assertParseFailed("<I8 \"100\">");
		assertParseFailed("<U1 \"100\">");
		assertParseFailed("<U2 \"100\">");
		assertParseFailed("<U4 \"100\">");
		assertParseFailed("<U8 \"100\">");
		assertParseFailed("<F4 \"100.0\">");
		assertParseFailed("<F8 \"1000.0\">");
		assertParseFailed("<BOOLEAN X>");
		
		{
			Secs2 ss = parseSml(sml2Layer);
			assertSecs2GetFailed(ss, 15);
			assertSecs2GetFailed(ss, 0, 0);
			assertSecs2GetFailed(ss, 0, 1);
			assertSecs2GetFailed(ss, 0, 0, 0);
		}
		
		{
			Secs2 ss = parseSml(sml3Layer);
			assertSecs2GetFailed(ss, 2);
			assertSecs2GetFailed(ss, 1, 15);
			assertSecs2GetFailed(ss, 1, 0, 0);
			assertSecs2GetFailed(ss, 1, 0, 1);
			assertSecs2GetFailed(ss, 1, 0, 0, 0);
		}
	}

}
