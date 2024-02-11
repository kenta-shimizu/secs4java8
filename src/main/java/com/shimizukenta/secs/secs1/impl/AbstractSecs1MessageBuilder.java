package com.shimizukenta.secs.secs1.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.impl.AbstractSecsMessageBuilder;
import com.shimizukenta.secs.secs1.Secs1Communicator;
import com.shimizukenta.secs.secs1.Secs1MessageBlock;
import com.shimizukenta.secs.secs1.Secs1TooBigMessageBodyException;
import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.secs2.Secs2BytesParseException;
import com.shimizukenta.secs.secs2.impl.Secs2BytesParsers;

public abstract class AbstractSecs1MessageBuilder extends AbstractSecsMessageBuilder<AbstractSecs1Message, Secs1Communicator> implements Secs1MessageBuilder {
	
	public AbstractSecs1MessageBuilder() {
		super();
	}
	
	@Override
	protected byte[] device2Bytes(Secs1Communicator communicator) {
		int n = communicator.deviceId();
		return new byte[] {
				(byte)((n >> 8) & 0x7F),
				(byte)n
		};
	}
	
	@Override
	public AbstractSecs1Message buildDataMessage(
			Secs1Communicator communicator,
			int strm,
			int func,
			boolean wbit,
			Secs2 body) {
		
		byte[] dd = this.device2Bytes(communicator);
		byte[] ssss = this.system4Bytes(communicator);
		
		byte[] header = new byte[] {
				dd[0],
				dd[1],
				(byte)(strm & 0x7F),
				(byte)func,
				(byte)0,
				(byte)0,
				ssss[0],
				ssss[1],
				ssss[2],
				ssss[3]
		};
		
		if ( this.isEquip(communicator) ) {
			header[0] |= (byte)0x80;
		}
		
		if ( wbit ) {
			header[2] |= (byte)0x80;
		}
		
		return buildDataMessage(header, body);
	}
	
	@Override
	public AbstractSecs1Message buildDataMessage(
			Secs1Communicator communicator,
			SecsMessage primaryMsg,
			int strm,
			int func,
			boolean wbit,
			Secs2 body) {
		
		byte[] dd = this.device2Bytes(communicator);
		byte[] ppbb = primaryMsg.header10Bytes();
		
		byte[] header = new byte[] {
				dd[0],
				dd[1],
				(byte)(strm & 0x7F),
				(byte)func,
				(byte)0,
				(byte)0,
				ppbb[6],
				ppbb[7],
				ppbb[8],
				ppbb[9]
		};
		
		if ( this.isEquip(communicator) ) {
			header[0] |= (byte)0x80;
		}
		
		if ( wbit ) {
			header[2] |= (byte)0x80;
		}
		
		return buildDataMessage(header, body);
	}
	
	public static AbstractSecs1Message buildDataMessage(byte[] header) {
		return AbstractSecs1MessageBuilder.buildDataMessage(header, Secs2.empty());
	}
	
	public static AbstractSecs1Message buildDataMessage(byte[] header, Secs2 body) {
		
		final List<Secs1MessageBlock> blocks = new ArrayList<>();
		
		final List<byte[]> ll = body.getBytesList(244);
		
		if ( ll.size() > 0x7FFE) {
			throw new Secs1TooBigMessageBodyException();
		}
		
		int blockNum = AbstractSecs1MessageBlock.ONE;
		int m = ll.size() - 1;
		
		for ( int i = 0; i < m; ++i ) {
			blocks.add(buildBlock(header, ll.get(i), false, blockNum));
			++ blockNum;
		}
		
		blocks.add(buildBlock(header, ll.get(m), true, blockNum));
		
		return new Secs1ValidMessage(header, body, blocks);
	}
	
	private static Secs1MessageBlock buildBlock(byte[] header, byte[] body, boolean ebit, int blockNumber) {
		
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
		
		return Secs1MessageBlock.of(bs);
	}
	
	public static AbstractSecs1Message buildFromBlocks(List<? extends Secs1MessageBlock> blocks) {
		
		if (isValidBlocks(blocks)) {
			
			final List<byte[]> bss = new ArrayList<>();
			
			Secs1MessageBlock bufBlock = blocks.get(0);
			
			{
				byte[] bs = bufBlock.getBytes();
				bss.add(Arrays.copyOfRange(bs, 11, (bs.length - 2)));
			}
			
			final int m = blocks.size();
			
			for (int i = 1; i < m; ++i) {
				
				final Secs1MessageBlock block = blocks.get(i);
				
				if (bufBlock.isNextBlock(block)) {
					
					byte[] bs = block.getBytes();
					bss.add(Arrays.copyOfRange(bs, 11, (bs.length - 2)));
					
					bufBlock = block;
				}
			}
			
			try {
				final Secs2 body = Secs2BytesParsers.parse(bss);
				
				return new Secs1ValidMessage(
						Arrays.copyOfRange(blocks.get(m - 1).getBytes(), 1, 11),
						body,
						blocks);
			}
			catch (Secs2BytesParseException parseFailed) {
				/* failed */
			}
		}
		
		return new Secs1InvalidMessage(blocks);
	}
	
	private static boolean isValidBlocks(List<? extends Secs1MessageBlock> blocks) {
		
		for (Secs1MessageBlock block : blocks) {
			if (! block.isValid()) {
				return false;
			}
		}
		
		final Secs1MessageBlock firstBlock = blocks.get(0);
		if (! firstBlock.isFirstBlock()) {
			return false;
		}
		
		final int m = blocks.size();
		
		if (! blocks.get(m - 1).ebit()) {
			return false;
		}
		
		Secs1MessageBlock bufBlock = firstBlock;
		byte[] refBytes = firstBlock.getBytes();
		int eBitCount = firstBlock.ebit() ? 1 : 0;
		
		for (int i = 1; i < m; ++i) {
			
			Secs1MessageBlock block = blocks.get(i);
			
			if (! bufBlock.equalsSystemBytes(block)) {
				return false;
			}
			
			byte[] bs = block.getBytes();
			
			if (! bufBlock.isNextBlock(block)) {
				if (bufBlock.blockNumber() != block.blockNumber()) {
					return false;
				}
			}
			
			if (refBytes[1] != bs[1]
				|| refBytes[2] != bs[2]
				|| refBytes[3] != bs[3]
				|| refBytes[4] != bs[4]) {
				
				return false;
			}
			
			if (block.ebit()) {
				++ eBitCount;
				if (eBitCount > 1) {
					return false;
				}
			}
			
			bufBlock = block;
			refBytes = bs;
		}
		
		return true;
	}
	
}
