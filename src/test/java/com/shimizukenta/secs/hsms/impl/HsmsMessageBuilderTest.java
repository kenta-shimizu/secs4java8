package com.shimizukenta.secs.hsms.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.shimizukenta.secs.hsms.HsmsMessage;
import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.secs2.Secs2BytesParseException;
import com.shimizukenta.secs.secs2.Secs2Item;

class HsmsMessageBuilderTest {
	
	@Test
	@DisplayName("HsmsMessageBuilder#build Header-only")
	void testBuildHeaderOnly() {
		
		byte[] header10Bytes = new byte[] {
				(byte)0x00, (byte)0x0A,
				(byte)0x81, (byte)0x01,
				(byte)0x00, (byte)0x00,
				(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x01
		};
		
		HsmsMessage msg = HsmsMessageBuilder.buildMessage(header10Bytes);
		
		assertEquals(msg.getStream(), 1);
		assertEquals(msg.getFunction(), 1);
		assertEquals(msg.wbit(), true);
		assertEquals(msg.secs2().isEmpty(), true);
	}
	
	@Test
	@DisplayName("HsmsMessageBuilder#build with LIST0")
	void testBuildWithList0() {
		
		byte[] header10Bytes = new byte[] {
				(byte)0x00, (byte)0x0A,
				(byte)0x01, (byte)0x02,
				(byte)0x00, (byte)0x00,
				(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x01
		};
		
		HsmsMessage msg = HsmsMessageBuilder.buildMessage(header10Bytes, Secs2.list());
		
		assertEquals(msg.getStream(), 1);
		assertEquals(msg.getFunction(), 2);
		assertEquals(msg.wbit(), false);
		assertEquals(msg.secs2().isEmpty(), false);
		assertEquals(msg.secs2().secs2Item(), Secs2Item.LIST);
	}
	
	@Test
	@DisplayName("HsmsMessageBuilder#build with LIST0")
	void testBuildFromListBytes() {
		
		byte[] header10Bytes = new byte[] {
				(byte)0x00, (byte)0x0A,
				(byte)0x01, (byte)0x02,
				(byte)0x00, (byte)0x00,
				(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x02
		};
		
		List<byte[]> bodies = Arrays.asList(
				new byte[] {(byte)0x01},
				new byte[] {(byte)0x00}
				);
		
		try {
			HsmsMessage msg = HsmsMessageBuilder.buildFromBytes(header10Bytes, bodies);
			
			assertEquals(msg.getStream(), 1);
			assertEquals(msg.getFunction(), 2);
			assertEquals(msg.wbit(), false);
			assertEquals(msg.secs2().isEmpty(), false);
			assertEquals(msg.secs2().secs2Item(), Secs2Item.LIST);
		}
		catch (Secs2BytesParseException e) {
			fail(e);
		}
	}
	
}
