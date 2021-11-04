package com.shimizukenta.secs.secs1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.secs2.Secs2BuildException;
import com.shimizukenta.secs.secs2.Secs2BytesPackBuilder;
import com.shimizukenta.secs.secs2.Secs2BytesParser;
import com.shimizukenta.secs.secs2.Secs2Exception;

public class Secs1MessageBuilder {

	private Secs1MessageBuilder() {
		/* Nothng */
	}
	
	private static class SingletonHolder {
		private static final Secs1MessageBuilder inst = new Secs1MessageBuilder();
	}
	
	public static Secs1MessageBuilder getInstance() {
		return SingletonHolder.inst;
	}
	
	public AbstractSecs1Message build(byte[] header) throws Secs1SendMessageException {
		return this.build(header, Secs2.empty());
	}
	
	public AbstractSecs1Message build(byte[] header, Secs2 body) throws Secs1SendMessageException {
		
		if ( header.length != 10 ) {
			throw new IllegalArgumentException("head not 10 bytes");
		}
		
		final List<AbstractSecs1MessageBlock> blocks = new ArrayList<>();
		
		final AbstractSecs1Message o = new AbstractSecs1Message(header, body) {
			
			private static final long serialVersionUID = -49852860369722269L;
			
			@Override
			public List<AbstractSecs1MessageBlock> toAbstractBlocks() {
				return Collections.unmodifiableList(blocks);
			}
		};
		
		try {
			
			final List<byte[]> ll = Secs2BytesPackBuilder.build(244, body).getBytes();
			
			if ( ll.size() > 0x7FFE) {
				throw new Secs1TooBigSendMessageException(o);
			}
			
			int blockNum = AbstractSecs1MessageBlock.ONE;
			int m = ll.size() - 1;
			
			for ( int i = 0; i < m; ++i ) {
				blocks.add(buildBlock(header, ll.get(i), false, blockNum));
				++ blockNum;
			}
			
			blocks.add(buildBlock(header, ll.get(m), true, blockNum));
			
			return o;
		}
		catch (Secs2BuildException e) {
			throw new Secs1SendMessageException(o, e);
		}
	}
	
	private static AbstractSecs1MessageBlock buildBlock(byte[] header, byte[] body, boolean ebit, int blockNumber) {
		
		int len = header.length + body.length;
		
		int sum = 0;
		
		byte[] bs = new byte[len + 3];
		
		bs[0] = (byte)len;
		bs[1] = header[0];
		bs[2] = header[1];
		bs[3] = header[2];
		bs[4] = header[3];
		
		bs[5] = (byte)(blockNumber >> 8);
		if ( ebit ) {
			bs[5] |= (byte)0x80;
		}
		
		bs[6] = (byte)blockNumber;
		
		bs[7] = header[6];
		bs[8] = header[7];
		bs[9] = header[8];
		bs[10] = header[9];
		
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
		
		return new AbstractSecs1MessageBlock(bs) {
			
			private static final long serialVersionUID = -3592481525438106452L;
		};
	}
	
	public AbstractSecs1Message fromBlocks(List<? extends AbstractSecs1MessageBlock> blocks) throws Secs2Exception {
		
		final List<AbstractSecs1MessageBlock> blks = new ArrayList<>(blocks);
		
		byte[] header = Arrays.copyOfRange(blocks.get(blocks.size() - 1).getBytes(), 1, 11);
		
		List<byte[]> bss = blocks.stream()
				.map(AbstractSecs1MessageBlock::getBytes)
				.map(bs -> Arrays.copyOfRange(bs, 11, bs.length - 2))
				.collect(Collectors.toList());
		
		Secs2 body = Secs2BytesParser.getInstance().parse(bss);
		
		return new AbstractSecs1Message(header, body) {
			
			private static final long serialVersionUID = 1819111393090408516L;
			
			@Override
			public List<AbstractSecs1MessageBlock> toAbstractBlocks() {
				return Collections.unmodifiableList(blks);
			}
		};
	}
	
}
