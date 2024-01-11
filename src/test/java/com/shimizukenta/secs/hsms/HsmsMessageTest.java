package com.shimizukenta.secs.hsms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.secs2.Secs2Item;

class HsmsMessageTest {

	@Test
	@DisplayName("HsmsMessage#of DATA-HeaderOnly")
	void testDataHeaderOnly() {
		
		byte[] header10bytes = new byte[] {
			(byte)0x01, (byte)0x2C,
			(byte)0x81, (byte)0x01,
			(byte)0x00, (byte)0x00,
			(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x01
		};
		
		HsmsMessage msg = HsmsMessage.of(header10bytes);
		
		assertEquals(msg.sessionId(), 300);
		assertEquals(msg.deviceId(), 300);
		assertEquals(msg.getStream(), 1);
		assertEquals(msg.getFunction(), 1);
		assertTrue(msg.wbit());
		assertEquals(msg.messageType(), HsmsMessageType.DATA);
		assertTrue(msg.isDataMessage());
		assertTrue(msg.secs2().isEmpty());
	}
	
	@Test
	@DisplayName("HsmsMessage#of DATA-WithBody")
	void testDataWithBody() {
		
		byte[] header10bytes = new byte[] {
			(byte)0x01, (byte)0x2C,
			(byte)0x01, (byte)0x02,
			(byte)0x00, (byte)0x00,
			(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x01
		};
		Secs2 body = Secs2.list();
		
		HsmsMessage msg = HsmsMessage.of(header10bytes, body);
		
		assertEquals(msg.sessionId(), 300);
		assertEquals(msg.deviceId(), 300);
		assertEquals(msg.getStream(), 1);
		assertEquals(msg.getFunction(), 2);
		assertFalse(msg.wbit());
		assertEquals(msg.messageType(), HsmsMessageType.DATA);
		assertTrue(msg.isDataMessage());
		assertFalse(msg.secs2().isEmpty());
		assertEquals(msg.secs2().secs2Item(), Secs2Item.LIST);
	}
	
	
}
