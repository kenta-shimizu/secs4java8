package com.shimizukenta.secs.secs1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.secs2.Secs2Item;

class Secs1MessageTest {

	@Test
	@DisplayName("Secs1Message#of empty")
	void testOfEmpty() {
		
		final byte[] header10Bytes = new byte[] {
				(byte)0x00, (byte)0x0A,
				(byte)0x81, (byte)0x01,
				(byte)0x00, (byte)0x00,
				(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x01
		};
		
		Secs1Message msg = Secs1Message.of(header10Bytes);
		
		int strm = msg.getStream();
		int func = msg.getFunction();
		boolean wbit = msg.wbit();
		Secs2 body = msg.secs2();
		
		boolean rbit = msg.rbit();
		int deviceId = msg.deviceId();
		
		assertEquals(msg.isValidBlocks(), true);
		
		assertEquals(strm, 1);
		assertEquals(func, 1);
		assertEquals(wbit, true);
		
		assertEquals(body.isEmpty(), true);
		
		assertEquals(rbit, false);
		assertEquals(deviceId, 10);
		
		final List<Secs1MessageBlock> blocks = msg.toBlocks();
		{
			assertEquals(blocks.isEmpty(), false);
			assertEquals(blocks.size(), 1);
			
			{
				Secs1MessageBlock block = blocks.get(0);
				byte[] bs = block.getBytes();
				
				assertEquals(bs[0], (byte)0xA);
				assertEquals(bs[1], header10Bytes[0]);
				assertEquals(bs[2], header10Bytes[1]);
				assertEquals(bs[3], header10Bytes[2]);
				assertEquals(bs[4], header10Bytes[3]);
				assertEquals(bs[5], (byte)0x80);
				assertEquals(bs[6], (byte)0x01);
				assertEquals(bs[7], header10Bytes[6]);
				assertEquals(bs[8], header10Bytes[7]);
				assertEquals(bs[9], header10Bytes[8]);
				assertEquals(bs[10], header10Bytes[9]);
				
				assertEquals(block.isValid(), true);
				assertEquals(block.isFirstBlock(), true);
				assertEquals(block.ebit(), true);
			}
			
		}
	}
	
	@Test
	@DisplayName("Secs1Message#of list")
	void testOfList() {
		
		final byte[] header10Bytes = new byte[] {
				(byte)0x80, (byte)0x0A,
				(byte)0x01, (byte)0x02,
				(byte)0x00, (byte)0x00,
				(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x01
		};
		
		Secs1Message msg = Secs1Message.of(header10Bytes, Secs2.list());
		
		int strm = msg.getStream();
		int func = msg.getFunction();
		boolean wbit = msg.wbit();
		Secs2 body = msg.secs2();
		
		boolean rbit = msg.rbit();
		int deviceId = msg.deviceId();
		
		assertEquals(msg.isValidBlocks(), true);
		
		assertEquals(strm, 1);
		assertEquals(func, 2);
		assertEquals(wbit, false);
		
		assertEquals(body.isEmpty(), false);
		assertEquals(body.secs2Item(), Secs2Item.LIST);
		assertEquals(body.size(), 0);
		
		assertEquals(rbit, true);
		assertEquals(deviceId, 10);
		
		final List<Secs1MessageBlock> blocks = msg.toBlocks();
		{
			assertEquals(blocks.isEmpty(), false);
			assertEquals(blocks.size(), 1);
			
			{
				Secs1MessageBlock block = blocks.get(0);
				byte[] bs = block.getBytes();
				
				assertEquals(bs[0], (byte)0xC);
				assertEquals(bs[1], header10Bytes[0]);
				assertEquals(bs[2], header10Bytes[1]);
				assertEquals(bs[3], header10Bytes[2]);
				assertEquals(bs[4], header10Bytes[3]);
				assertEquals(bs[5], (byte)0x80);
				assertEquals(bs[6], (byte)0x01);
				assertEquals(bs[7], header10Bytes[6]);
				assertEquals(bs[8], header10Bytes[7]);
				assertEquals(bs[9], header10Bytes[8]);
				assertEquals(bs[10], header10Bytes[9]);
				assertEquals(bs[11], (byte)0x01);
				assertEquals(bs[12], (byte)0x00);
				
				assertEquals(block.isValid(), true);
				assertEquals(block.isFirstBlock(), true);
				assertEquals(block.ebit(), true);
			}
			
		}
	}
	
	@Test
	@DisplayName("Secs1Message#of Single-blocks")
	void testOfSingleBlocks() {
		
		Secs1MessageBlock block = Secs1MessageBlock.of(new byte[] {
				(byte)0x0A,
				(byte)0x00, (byte)0x0A,
				(byte)0x81, (byte)0x01,
				(byte)0x80, (byte)0x01,
				(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x01,
				(byte)0x01, (byte)0x0E
		});
		
		assertEquals(block.isValid(), true);
		
		Secs1Message msg = Secs1Message.of(Arrays.asList(block));
		
		int strm = msg.getStream();
		int func = msg.getFunction();
		boolean wbit = msg.wbit();
		Secs2 body = msg.secs2();
		
		boolean rbit = msg.rbit();
		int deviceId = msg.deviceId();
		
		assertEquals(msg.isValidBlocks(), true);
		
		assertEquals(strm, 1);
		assertEquals(func, 1);
		assertEquals(wbit, true);
		
		assertEquals(body.isEmpty(), true);
		
		assertEquals(rbit, false);
		assertEquals(deviceId, 10);
		
		assertEquals(msg.toBlocks().size(), 1);
	}
	
	@Test
	@DisplayName("Secs1Message#of Multi-blocks")
	void testOfMultiBlocks() {
		
		Secs1MessageBlock block1 = Secs1MessageBlock.of(new byte[] {
				(byte)0x0B,
				(byte)0x80, (byte)0x0A,
				(byte)0x01, (byte)0x02,
				(byte)0x00, (byte)0x01,
				(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x01,
				(byte)0x01,
				(byte)0x00, (byte)0x90
		});
		
		Secs1MessageBlock block2 = Secs1MessageBlock.of(new byte[] {
				(byte)0x0B,
				(byte)0x80, (byte)0x0A,
				(byte)0x01, (byte)0x02,
				(byte)0x80, (byte)0x02,
				(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x01,
				(byte)0x00,
				(byte)0x01, (byte)0x10
		});
		
		assertEquals(block1.isValid(), true);
		assertEquals(block1.ebit(), false);
		assertEquals(block1.isFirstBlock(), true);
		assertEquals(block2.isValid(), true);
		assertEquals(block2.ebit(), true);
		assertEquals(block2.isFirstBlock(), false);
		
		Secs1Message msg = Secs1Message.of(Arrays.asList(block1, block2));
		
		int strm = msg.getStream();
		int func = msg.getFunction();
		boolean wbit = msg.wbit();
		Secs2 body = msg.secs2();
		
		boolean rbit = msg.rbit();
		int deviceId = msg.deviceId();
		
		assertEquals(msg.isValidBlocks(), true);
		
		assertEquals(strm, 1);
		assertEquals(func, 2);
		assertEquals(wbit, false);
		
		assertEquals(msg.isValidBlocks(), true);
		
		assertEquals(body.isEmpty(), false);
		assertEquals(body.secs2Item(), Secs2Item.LIST);
		assertEquals(body.size(), 0);
		
		assertEquals(rbit, true);
		assertEquals(deviceId, 10);
		
		assertEquals(msg.toBlocks().size(), 2);
	}
	
}
