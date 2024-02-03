package com.shimizukenta.secs.hsms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

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
	
	@Test
	@DisplayName("HsmsMessage#of SELECT.req")
	void testStaticBuildSelectRequest() {
		
		byte[] header10Bytes = new byte[] {
				(byte)0xFF, (byte)0xFF,
				(byte)0x00, (byte)0x00,
				(byte)0x00, (byte)0x01,
				(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x01
		};
		
		HsmsMessage msg = HsmsMessage.of(header10Bytes);
		
		assertEquals(msg.isDataMessage(), false);
		assertEquals(msg.messageType(), HsmsMessageType.SELECT_REQ);
	}
	
	@Test
	@DisplayName("HsmsMessage#of SELECT.rsp")
	void testStaticBuildSelectResponse() {
		
		byte[] header10Bytes = new byte[] {
				(byte)0xFF, (byte)0xFF,
				(byte)0x00, (byte)0x00,
				(byte)0x00, (byte)0x02,
				(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x01
		};
		
		HsmsMessage msg = HsmsMessage.of(header10Bytes);
		
		assertEquals(msg.isDataMessage(), false);
		assertEquals(msg.messageType(), HsmsMessageType.SELECT_RSP);
		assertEquals(HsmsMessageSelectStatus.get(msg), HsmsMessageSelectStatus.SUCCESS);
	}
	
	@Test
	@DisplayName("HsmsMessage#of DESELECT.req")
	void testStaticBuildDeselectRequest() {
		
		byte[] header10Bytes = new byte[] {
				(byte)0xFF, (byte)0xFF,
				(byte)0x00, (byte)0x00,
				(byte)0x00, (byte)0x03,
				(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x03
		};
		
		HsmsMessage msg = HsmsMessage.of(header10Bytes);
		
		assertEquals(msg.isDataMessage(), false);
		assertEquals(msg.messageType(), HsmsMessageType.DESELECT_REQ);
	}
	
	@Test
	@DisplayName("HsmsMessage#of DESELECT.rsp")
	void testStaticBuildDeselectResponse() {
		
		byte[] header10Bytes = new byte[] {
				(byte)0xFF, (byte)0xFF,
				(byte)0x00, (byte)0x00,
				(byte)0x00, (byte)0x04,
				(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x04
		};
		
		HsmsMessage msg = HsmsMessage.of(header10Bytes);
		
		assertEquals(msg.isDataMessage(), false);
		assertEquals(msg.messageType(), HsmsMessageType.DESELECT_RSP);
		assertEquals(HsmsMessageDeselectStatus.get(msg), HsmsMessageDeselectStatus.SUCCESS);
	}
	
	@Test
	@DisplayName("HsmsMessage#of LINKTEST.req")
	void testStaticBuildLinktestRequest() {
		
		byte[] header10Bytes = new byte[] {
				(byte)0xFF, (byte)0xFF,
				(byte)0x00, (byte)0x00,
				(byte)0x00, (byte)0x05,
				(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x05
		};
		
		HsmsMessage msg = HsmsMessage.of(header10Bytes);
		
		assertEquals(msg.isDataMessage(), false);
		assertEquals(msg.messageType(), HsmsMessageType.LINKTEST_REQ);
	}
	
	@Test
	@DisplayName("HsmsMessage#of LINKTEST.rsp")
	void testStaticBuildLinktestResponse() {
		
		byte[] header10Bytes = new byte[] {
				(byte)0xFF, (byte)0xFF,
				(byte)0x00, (byte)0x00,
				(byte)0x00, (byte)0x06,
				(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x06
		};
		
		HsmsMessage msg = HsmsMessage.of(header10Bytes);
		
		assertEquals(msg.isDataMessage(), false);
		assertEquals(msg.messageType(), HsmsMessageType.LINKTEST_RSP);
	}
	
	
	@Test
	@DisplayName("HsmsMessage#of REJECT.rsp")
	void testStaticBuildRejectRequest() {
		
		byte[] header10Bytes = new byte[] {
				(byte)0xFF, (byte)0xFF,
				(byte)0x00, (byte)0x04,
				(byte)0x00, (byte)0x07,
				(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x07
		};
		
		HsmsMessage msg = HsmsMessage.of(header10Bytes);
		
		assertEquals(msg.isDataMessage(), false);
		assertEquals(msg.messageType(), HsmsMessageType.REJECT_REQ);
		assertEquals(HsmsMessageRejectReason.get(msg), HsmsMessageRejectReason.NOT_SELECTED);
	}
	
	
	@Test
	@DisplayName("HsmsMessage#of SEPARATE.rsp")
	void testStaticBuildSeparateRequest() {
		
		byte[] header10Bytes = new byte[] {
				(byte)0xFF, (byte)0xFF,
				(byte)0x00, (byte)0x00,
				(byte)0x00, (byte)0x09,
				(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x09
		};
		
		HsmsMessage msg = HsmsMessage.of(header10Bytes);
		
		assertEquals(msg.isDataMessage(), false);
		assertEquals(msg.messageType(), HsmsMessageType.SEPARATE_REQ);
	}
	
	@Test
	@DisplayName("HsmsMessage#of build-fail")
	void testBuildFail() {
		
		try {
			byte[] header3bytes = new byte[3];
			
			HsmsMessage msg = HsmsMessage.of(header3bytes);
			fail(msg.toString());
		}
		catch (HsmsMessageHeaderByteLengthIllegalArgumentException e) {
			/* SUCCESS */
		}
		
		try {
			HsmsMessage msg = HsmsMessage.of(null);
			fail(msg.toString());
		}
		catch (NullPointerException e) {
			/* SUCCESS */
		}
		
		try {
			byte[] header10bytes = new byte[10];
			
			HsmsMessage msg = HsmsMessage.of(header10bytes, null);
			fail(msg.toString());
		}
		catch (NullPointerException e) {
			/* SUCCESS */
		}
	}
}
