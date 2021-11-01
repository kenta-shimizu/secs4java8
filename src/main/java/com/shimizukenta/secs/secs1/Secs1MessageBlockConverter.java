package com.shimizukenta.secs.secs1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.secs2.Secs2BuildException;
import com.shimizukenta.secs.secs2.Secs2BytesPackBuilder;
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
	
	public static List<Secs1MessageBlock> toBlocks(AbstractSecs1Message msg) throws Secs1SendMessageException {
		return getInstance()._toBlocks(msg);
	}
	
	public static AbstractSecs1Message toSecs1Message(List<? extends Secs1MessageBlock> blocks) throws Secs2Exception {
		return getInstance()._toSecs1Message(blocks);
	}
	
	
	private List<SimpleSecs1MessageBlock> _toBlocks(SimpleSecs1Message msg) throws Secs1SendMessageException {
		
		try {
			final List<SimpleSecs1MessageBlock> blocks = new ArrayList<>();
			
			byte[] head = msg.header10Bytes();
			
			if ( head.length != 10 ) {
				throw new IllegalArgumentException("head not 10 bytes");
			}
			
			final List<byte[]> ll = Secs2BytesPackBuilder.build(244, msg.secs2()).getBytes();
			
			if ( ll.size() > 0x7FFE) {
				throw new Secs1TooBigSendMessageException(msg);
			}
			
			int blockNum = SimpleSecs1MessageBlock.ONE;
			int m = ll.size() - 1;
			
			for ( int i = 0; i < m; ++i ) {
				blocks.add(buildBlock(head, ll.get(i), false, blockNum));
				++ blockNum;
			}
			
			blocks.add(buildBlock(head, ll.get(m), true, blockNum));
			
			return blocks;
		}
		catch (Secs2BuildException e) {
			throw new Secs1SendMessageException(msg, e);
		}
	}
	
	private SimpleSecs1MessageBlock buildBlock(byte[] head, byte[] body, boolean ebit, int blockNumber) {
		
		int len = head.length + body.length;
		
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
		
		int pos = 1;
		for (; pos < 11; ++pos) {
			sum += (int)(bs[pos]) & 0xFF;
		}
		
		for (int i = 0, m = body.length; i < m; ++pos, ++i) {
			byte b = body[i];
			bs[pos] = b;
			sum += ((int)b) & 0xFF;
		}
		
		bs[pos] = (byte)(sum >> 8);
		bs[pos + 1] = (byte)sum;
		
		return new SimpleSecs1MessageBlock(bs);
	}
	
	private SimpleSecs1Message _toSecs1Message(List<SimpleSecs1MessageBlock> blocks) throws Secs2Exception {
		
		byte[] head = Arrays.copyOfRange(blocks.get(blocks.size() - 1).getBytes(), 1, 11);
		
		List<byte[]> bss = blocks.stream()
				.map(SimpleSecs1MessageBlock::getBytes)
				.map(bs -> Arrays.copyOfRange(bs, 11, bs.length - 2))
				.collect(Collectors.toList());
		
		Secs2 body = Secs2BytesParser.getInstance().parse(bss);
		
		return new SimpleSecs1Message(head, body);
	}
	
}
