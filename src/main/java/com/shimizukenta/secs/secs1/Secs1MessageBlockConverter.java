package com.shimizukenta.secs.secs1;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.secs2.Secs2BuildException;
import com.shimizukenta.secs.secs2.Secs2ByteBuffersBuilder;
import com.shimizukenta.secs.secs2.Secs2BytesParser;
import com.shimizukenta.secs.secs2.Secs2Exception;

public class Secs1MessageBlockConverter {
	
	private Secs1MessageBlockConverter() {
		/* Nothing */
	}
	
	private static class SingletonHolder {
		private static final Secs1MessageBlockConverter inst = new Secs1MessageBlockConverter();
	}
	
	public static Secs1MessageBlockConverter getInstance() {
		return SingletonHolder.inst;
	}
	
	public static List<Secs1MessageBlock> toBlocks(Secs1Message msg) throws Secs1SendMessageException {
		return getInstance()._toBlocks(msg);
	}
	
	public static Secs1Message toSecs1Message(List<Secs1MessageBlock> blocks) throws Secs2Exception {
		return getInstance()._toSecs1Message(blocks);
	}
	
	
	private List<Secs1MessageBlock> _toBlocks(Secs1Message msg) throws Secs1SendMessageException {
		
		try {
			List<Secs1MessageBlock> blocks = new ArrayList<>();
			
			byte[] head = msg.header10Bytes();
			
			if ( head.length != 10 ) {
				throw new IllegalArgumentException("head not 10 bytes");
			}
			
			Secs2ByteBuffersBuilder bb = Secs2ByteBuffersBuilder.build(244, msg.secs2());
			
			if ( bb.blocks() > 0x7FFF) {
				throw new Secs1TooBigSendMessageException(msg);
			}
			
			
			List<ByteBuffer> buffers = bb.getByteBuffers();
			
			int blockNum = Secs1MessageBlock.ONE;
			int m = buffers.size() - 1;
			
			for ( int i = 0; i < m; ++i ) {
				blocks.add(buildBlock(head, buffers.get(i), false, blockNum));
				++ blockNum;
			}
			
			blocks.add(buildBlock(head, buffers.get(m), true, blockNum));
			
			return blocks;
		}
		catch (Secs2BuildException e) {
			throw new Secs1SendMessageException(msg, e);
		}
	}
	
	private Secs1MessageBlock buildBlock(byte[] head, ByteBuffer buffer, boolean ebit, int blockNumber) {
		
		int len = head.length + buffer.remaining();
		
		int sum = 0;
		
		byte[] bs = new byte[len + 3];
		
		bs[0] = (byte)len;
		bs[1] = head[0];
		bs[2] = head[1];
		bs[3] = head[2];
		bs[4] = head[3];
		
		bs[5] = (byte)(blockNumber >> 8);
		if ( ebit ) {
			bs[5] |= (byte)0x80;
		}
		
		bs[6] = (byte)blockNumber;
		
		bs[7] = head[6];
		bs[8] = head[7];
		bs[9] = head[8];
		bs[10] = head[9];
		
		int i = 1;
		for (; i < 11; ++i) {
			sum += (int)(bs[i]) & 0xFF;
		}
		
		for (; buffer.hasRemaining(); ++i) {
			byte b = buffer.get();
			bs[i] = b;
			sum += ((int)b) & 0xFF;
		}
		
		bs[i] = (byte)(sum >> 8);
		bs[i + 1] = (byte)sum;
		
		return new Secs1MessageBlock(bs);
	}
	
	private Secs1Message _toSecs1Message(List<Secs1MessageBlock> blocks) throws Secs2Exception {
		
		byte[] head = Arrays.copyOfRange(blocks.get(blocks.size() - 1).getBytes(), 1, 11);
		
		List<byte[]> bss = blocks.stream()
				.map(Secs1MessageBlock::getBytes)
				.map(bs -> Arrays.copyOfRange(bs, 11, bs.length - 2))
				.collect(Collectors.toList());
		
		Secs2 body = Secs2BytesParser.getInstance().parse(bss);
		
		return new Secs1Message(head, body);
	}
	
}
