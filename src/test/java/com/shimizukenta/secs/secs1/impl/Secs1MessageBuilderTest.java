package com.shimizukenta.secs.secs1.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.shimizukenta.secs.secs1.Secs1Communicator;
import com.shimizukenta.secs.secs1.Secs1Message;
import com.shimizukenta.secs.secs1.Secs1MessageBlock;
import com.shimizukenta.secs.secs1ontcpip.Secs1OnTcpIpCommunicatorConfig;
import com.shimizukenta.secs.secs1ontcpip.impl.AbstractSecs1OnTcpIpCommunicator;
import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.secs2.Secs2Item;

class Secs1MessageBuilderTest {
	
	private static Secs1OnTcpIpCommunicatorConfig buildConfig(int deviceId, boolean isEquip) {
		Secs1OnTcpIpCommunicatorConfig config = new Secs1OnTcpIpCommunicatorConfig();
		config.socketAddress(new InetSocketAddress("127.0.0.1", 0));
		config.deviceId(deviceId);
		config.isEquip(isEquip);
		return config;
	}
	
	private static AbstractSecs1OnTcpIpCommunicator buildCommunicator(Secs1OnTcpIpCommunicatorConfig config) {
		return new AbstractSecs1OnTcpIpCommunicator(config) {};
	}
	
	private static Secs1MessageBuilder getBuilder() {
		return new AbstractSecs1MessageBuilder() {};
	}
	
	@Test
	@DisplayName("EquipPrimaryMessage")
	void testEquipPrimaryMessage() {
		final int deviceId = 10;
		final boolean isEquip = true;
		final int strm = 1;
		final int func = 1;
		final boolean wbit = true;
		
		final Secs1Communicator equipComm = buildCommunicator(buildConfig(deviceId, isEquip));
		final Secs1MessageBuilder builder = getBuilder();
		
		Secs1Message msg = builder.build(equipComm, strm, func, wbit);
		
		assertEquals(msg.getStream(), strm);
		assertEquals(msg.getFunction(), func);
		assertEquals(msg.wbit(), wbit);
		assertEquals(msg.secs2().isEmpty(), true);
		assertEquals(msg.rbit(), isEquip);
		assertEquals(msg.deviceId(), deviceId);
		
		{
			byte[] header10Bytes = msg.header10Bytes();
			assertEquals((byte)((deviceId >> 8) & 0x7F), header10Bytes[6]);
			assertEquals((byte)deviceId, header10Bytes[7]);
		}
	}
	
	@Test
	@DisplayName("HostPrimaryMessage")
	void testHostPrimaryMessage() {
		final int deviceId = 200;
		final boolean isEquip = false;
		final int strm = 55;
		final int func = 99;
		final boolean wbit = false;
		final Secs2 body = Secs2.list();
		
		final Secs1Communicator hostComm = buildCommunicator(buildConfig(deviceId, isEquip));
		final Secs1MessageBuilder builder = getBuilder();
		
		Secs1Message msg = builder.build(hostComm, strm, func, wbit, body);
		
		assertEquals(msg.getStream(), strm);
		assertEquals(msg.getFunction(), func);
		assertEquals(msg.wbit(), wbit);
		assertEquals(msg.secs2().isEmpty(), false);
		assertEquals(msg.rbit(), isEquip);
		assertEquals(msg.deviceId(), deviceId);
		
		{
			byte[] header10Bytes = msg.header10Bytes();
			assertEquals((byte)0x00, header10Bytes[6]);
			assertEquals((byte)0x00, header10Bytes[7]);
		}
	}
	
	@Test
	@DisplayName("EquipRepryMessage")
	void testEquipReplyMessage() {
		final int deviceId = 2000;
		final int strm = 99;
		final int func = 111;
		
		final Secs1Communicator hostComm = buildCommunicator(buildConfig(deviceId, false));
		final Secs1MessageBuilder hostBuilder = getBuilder();
		
		final Secs1Communicator equipComm = buildCommunicator(buildConfig(deviceId, true));
		final Secs1MessageBuilder equipBuilder = getBuilder();
		
		final Secs1Message primaryMsg = hostBuilder.build(hostComm, strm, func, true);
		
		final int replyFunc = func + 1;
		
		final Secs1Message replyMsg = equipBuilder.build(equipComm, primaryMsg, strm, replyFunc, false);
		
		assertEquals(replyMsg.getStream(), strm);
		assertEquals(replyMsg.getFunction(), replyFunc);
		assertEquals(replyMsg.wbit(), false);
		assertEquals(replyMsg.secs2().isEmpty(), true);
		assertEquals(replyMsg.rbit(), true);
		
		{
			byte[] hostHeader10Bytes = primaryMsg.header10Bytes();
			byte[] equipHeader10Bytes = replyMsg.header10Bytes();
			
			assertEquals(hostHeader10Bytes[6], equipHeader10Bytes[6]);
			assertEquals(hostHeader10Bytes[7], equipHeader10Bytes[7]);
			assertEquals(hostHeader10Bytes[8], equipHeader10Bytes[8]);
			assertEquals(hostHeader10Bytes[9], equipHeader10Bytes[9]);
		}
	}
	
	@Test
	@DisplayName("HostRepryMessage")
	void testHostReplyMessage() {
		final int deviceId = 30000;
		final int strm = 111;
		final int func = 199;
		
		final Secs1Communicator equipComm = buildCommunicator(buildConfig(deviceId, true));
		final Secs1MessageBuilder equipBuilder = getBuilder();
		
		final Secs1Communicator hostComm = buildCommunicator(buildConfig(deviceId, false));
		final Secs1MessageBuilder hostBuilder = getBuilder();
		
		final Secs1Message primaryMsg = equipBuilder.build(equipComm, strm, func, true);
		
		final int replyFunc = func + 1;
		final Secs2 body = Secs2.list();
		
		final Secs1Message replyMsg = hostBuilder.build(hostComm, primaryMsg, strm, replyFunc, false, body);
		
		assertEquals(replyMsg.getStream(), strm);
		assertEquals(replyMsg.getFunction(), replyFunc);
		assertEquals(replyMsg.wbit(), false);
		assertEquals(replyMsg.secs2().isEmpty(), false);
		assertEquals(replyMsg.secs2().secs2Item(), Secs2Item.LIST);
		assertEquals(replyMsg.rbit(), false);
		
		{
			byte[] hostHeader10Bytes = primaryMsg.header10Bytes();
			byte[] equipHeader10Bytes = replyMsg.header10Bytes();
			
			assertEquals(hostHeader10Bytes[6], equipHeader10Bytes[6]);
			assertEquals(hostHeader10Bytes[7], equipHeader10Bytes[7]);
			assertEquals(hostHeader10Bytes[8], equipHeader10Bytes[8]);
			assertEquals(hostHeader10Bytes[9], equipHeader10Bytes[9]);
		}
	}
	
	
	@Test
	@DisplayName("Secs1MessageBuilder#build empty")
	void testStaticBuildEmpty() {
		
		final byte[] header10Bytes = new byte[] {
				(byte)0x00, (byte)0x0A,
				(byte)0x81, (byte)0x01,
				(byte)0x00, (byte)0x00,
				(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x01
		};
		
		Secs1Message msg = Secs1MessageBuilder.build(header10Bytes);
		
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
	@DisplayName("Secs1MessageBuilder#build list")
	void testStaticBuildList() {
		
		final byte[] header10Bytes = new byte[] {
				(byte)0x80, (byte)0x0A,
				(byte)0x01, (byte)0x02,
				(byte)0x00, (byte)0x00,
				(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x01
		};
		
		Secs1Message msg = Secs1MessageBuilder.build(header10Bytes, Secs2.list());
		
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
	@DisplayName("Secs1MessageBuilder#fromBlocks Single-blocks")
	void testStaticFromSingleBlocks() {
		
		Secs1MessageBlock block = Secs1MessageBlock.of(new byte[] {
				(byte)0x0A,
				(byte)0x00, (byte)0x0A,
				(byte)0x81, (byte)0x01,
				(byte)0x80, (byte)0x01,
				(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x01,
				(byte)0x01, (byte)0x0E
		});
		
		assertEquals(block.isValid(), true);
		
		Secs1Message msg = Secs1MessageBuilder.fromBlocks(Arrays.asList(block));
		
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
	@DisplayName("Secs1MessageBuilder#fromBlocks Multi-blocks")
	void testStaticromMultiBlocks() {
		
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
		
		Secs1Message msg = Secs1MessageBuilder.fromBlocks(Arrays.asList(block1, block2));
		
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
