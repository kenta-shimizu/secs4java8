package com.shimizukenta.secs.secs1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class Secs1MessageBlockTest {

	@Test
	@DisplayName("Secs1MessageBlock#of valid-header-only")
	void testValidHeaderOnly() {
		
		byte[] bs = new byte[] {
				(byte)0x0A,
				(byte)0x00, (byte)0x0A,
				(byte)0x81, (byte)0x01,
				(byte)0x80, (byte)0x01,
				(byte)0x00, (byte)0x0A, (byte)0x00, (byte)0x01,
				(byte)0x01, (byte)0x18
		};
		
		Secs1MessageBlock block = Secs1MessageBlock.of(bs);
		
		assertEquals(block.isValid(), true);
		assertEquals(block.checkSum(), true);
		assertEquals(block.length(), 10);
		assertEquals(block.deviceId(), 10);
		assertEquals(block.ebit(), true);
		assertEquals(block.blockNumber(), 1);
		assertEquals(block.isFirstBlock(), true);
	}
	
	@Test
	@DisplayName("Secs1MessageBlock#of valid-List0")
	void testValidList0() {
		
		byte[] bs = new byte[] {
				(byte)0x0C,
				(byte)0x80, (byte)0x0B,
				(byte)0x81, (byte)0x02,
				(byte)0x00, (byte)0x02,
				(byte)0x00, (byte)0x0A, (byte)0x00, (byte)0x01,
				(byte)0x01, (byte)0x00,
				(byte)0x01, (byte)0x1C
		};
		
		Secs1MessageBlock block = Secs1MessageBlock.of(bs);
		
		assertEquals(block.isValid(), true);
		assertEquals(block.checkSum(), true);
		assertEquals(block.length(), 12);
		assertEquals(block.deviceId(), 11);
		assertEquals(block.ebit(), false);
		assertEquals(block.blockNumber(), 2);
		assertEquals(block.isFirstBlock(), false);
	}
	
	@Test
	@DisplayName("Secs1MessageBlock#of invalid")
	void testInvalidList0() {
		
		{
			// bytes.length < 13
			Secs1MessageBlock block = Secs1MessageBlock.of(new byte[12]);
			assertEquals(block.isValid(), false);
		}
		{
			// bytes.length > 257
			Secs1MessageBlock block = Secs1MessageBlock.of(new byte[258]);
			assertEquals(block.isValid(), false);
		}
		{
			// length-byte < 10
			byte[] bs = new byte[13];
			bs[0] = (byte)0x09;
			Secs1MessageBlock block = Secs1MessageBlock.of(bs);
			assertEquals(block.isValid(), false);
		}
		{
			// length-byte > 254
			byte[] bs = new byte[13];
			bs[0] = (byte)0xFF;
			Secs1MessageBlock block = Secs1MessageBlock.of(bs);
			assertEquals(block.isValid(), false);
		}
		{
			// (length-byte + 3) == bytes.length
			byte[] bs = new byte[] {
					(byte)0x0A,
					(byte)0x00, (byte)0x0A,
					(byte)0x81, (byte)0x01,
					(byte)0x80, (byte)0x01,
					(byte)0x00, (byte)0x0A, (byte)0x00, (byte)0x01,
					(byte)0x01, (byte)0x18,
					(byte)0x00
			};
			
			Secs1MessageBlock block = Secs1MessageBlock.of(bs);
			
			assertEquals(block.isValid(), false);
		}
		{
			// sum-check
			byte[] bs = new byte[] {
					(byte)0x0C,
					(byte)0x80, (byte)0x0B,
					(byte)0x81, (byte)0x02,
					(byte)0x00, (byte)0x02,
					(byte)0x00, (byte)0x0A, (byte)0x00, (byte)0x01,
					(byte)0x01, (byte)0x00,
					(byte)0x01, (byte)0x1D
			};
			
			Secs1MessageBlock block = Secs1MessageBlock.of(bs);
			
			assertEquals(block.isValid(), false);
			assertEquals(block.checkSum(), false);
			assertEquals(block.length(), -1);
			assertEquals(block.deviceId(), -1);
			assertEquals(block.ebit(), false);
			assertEquals(block.blockNumber(), -1);
			assertEquals(block.isFirstBlock(), false);
		}
	}
	
	@Test
	@DisplayName("Secs1MessageBlock#equalSystemBytes")
	void testEqualSystemBytes() {
		
		byte[] baseBytes = new byte[] {
				(byte)0x0A,
				(byte)0x00, (byte)0x0A,
				(byte)0x81, (byte)0x01,
				(byte)0x00, (byte)0x01,
				(byte)0x00, (byte)0x0A, (byte)0x00, (byte)0x01,
				(byte)0x00, (byte)0x98
		};
		
		byte[] nextBytes = new byte[] {
				(byte)0x0A,
				(byte)0x00, (byte)0x0A,
				(byte)0x81, (byte)0x01,
				(byte)0x00, (byte)0x02,
				(byte)0x00, (byte)0x0A, (byte)0x00, (byte)0x01,
				(byte)0x00, (byte)0x99
		};
		
		byte[] otherBytes = new byte[] {
				(byte)0x0A,
				(byte)0x00, (byte)0x0A,
				(byte)0x81, (byte)0x01,
				(byte)0x00, (byte)0x01,
				(byte)0x00, (byte)0x0A, (byte)0x00, (byte)0x02,
				(byte)0x00, (byte)0x99
		};
		
		Secs1MessageBlock baseBlock = Secs1MessageBlock.of(baseBytes);
		Secs1MessageBlock nextBlock = Secs1MessageBlock.of(nextBytes);
		Secs1MessageBlock otherBlock = Secs1MessageBlock.of(otherBytes);
		Secs1MessageBlock invalidBlock = Secs1MessageBlock.of(new byte[0]);
		
		assertTrue(baseBlock.equalsSystemBytes(nextBlock));
		assertFalse(baseBlock.equalsSystemBytes(otherBlock));
		assertFalse(baseBlock.equalsSystemBytes(invalidBlock));
		assertFalse(invalidBlock.equalsSystemBytes(baseBlock));
	}
	
	@Test
	@DisplayName("Secs1MessageBlock#isNextBlock")
	void testIsNextBlock() {
		
		byte[] baseBytes = new byte[] {
				(byte)0x0A,
				(byte)0x00, (byte)0x0A,
				(byte)0x81, (byte)0x01,
				(byte)0x00, (byte)0x01,
				(byte)0x00, (byte)0x0A, (byte)0x00, (byte)0x01,
				(byte)0x00, (byte)0x98
		};
		
		byte[] nextBytes = new byte[] {
				(byte)0x0A,
				(byte)0x00, (byte)0x0A,
				(byte)0x81, (byte)0x01,
				(byte)0x00, (byte)0x02,
				(byte)0x00, (byte)0x0A, (byte)0x00, (byte)0x01,
				(byte)0x00, (byte)0x99
		};
		
		byte[] otherBytes = new byte[] {
				(byte)0x0A,
				(byte)0x00, (byte)0x0A,
				(byte)0x81, (byte)0x01,
				(byte)0x00, (byte)0x01,
				(byte)0x00, (byte)0x0A, (byte)0x00, (byte)0x02,
				(byte)0x00, (byte)0x99
		};
		
		Secs1MessageBlock baseBlock = Secs1MessageBlock.of(baseBytes);
		Secs1MessageBlock nextBlock = Secs1MessageBlock.of(nextBytes);
		Secs1MessageBlock otherBlock = Secs1MessageBlock.of(otherBytes);
		Secs1MessageBlock invalidBlock = Secs1MessageBlock.of(new byte[0]);
		
		assertTrue(baseBlock.isNextBlock(nextBlock));
		assertFalse(baseBlock.isNextBlock(otherBlock));
		assertFalse(baseBlock.isNextBlock(invalidBlock));
		assertFalse(invalidBlock.isNextBlock(baseBlock));
	}
	
}
