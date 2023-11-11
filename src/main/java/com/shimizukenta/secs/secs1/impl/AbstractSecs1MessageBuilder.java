package com.shimizukenta.secs.secs1.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.secs1.Secs1Message;
import com.shimizukenta.secs.secs1.Secs1MessageBlock;
import com.shimizukenta.secs.secs1.Secs1TooBigSendMessageException;
import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.secs2.Secs2BytesParseException;
import com.shimizukenta.secs.secs2.impl.Secs2BytesParsers;

public abstract class AbstractSecs1MessageBuilder implements Secs1MessageBuilder {
	
	private final AbstractSecs1Communicator comm;
	
	public AbstractSecs1MessageBuilder(AbstractSecs1Communicator communicator) {
		this.comm = communicator;
	}
	
	private final AtomicInteger autoNumber = new AtomicInteger(0);
	
	protected byte[] getAutoNumber2Bytes() {
		int n = autoNumber.incrementAndGet();
		return new byte[] {
				(byte)(n >> 8),
				(byte)n
		};
	}
	
	protected byte[] getDeviceId2Bytes() {
		int n = this.comm.deviceId();
		return new byte[] {
				(byte)((n >> 8) & 0x7F),
				(byte)n
		};
	}
	
	protected byte[] getSystem4Bytes() {
		
		byte[] aa = this.getAutoNumber2Bytes();
		
		if ( this.comm.isEquip() ) {
			
			byte[] ss = this.getDeviceId2Bytes();
			
			return new byte[] {
					ss[0],
					ss[1],
					aa[0],
					aa[1]
			};
			
		} else {
			
			return new byte[] {
					(byte)0x0,
					(byte)0x0,
					aa[0],
					aa[1]
			};
		}
	}
	
	@Override
	public AbstractSecs1Message build(
			int strm,
			int func,
			boolean wbit)
					throws Secs1TooBigSendMessageException {
		
		return this.build(strm, func, wbit, Secs2.empty());
	}

	@Override
	public AbstractSecs1Message build(
			int strm,
			int func,
			boolean wbit,
			Secs2 body)
					throws Secs1TooBigSendMessageException {
		
		byte[] dd = this.getDeviceId2Bytes();
		byte[] ssss = this.getSystem4Bytes();
		
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
		
		if ( this.comm.isEquip() ) {
			header[0] |= (byte)0x80;
		}
		
		if ( wbit ) {
			header[2] |= (byte)0x80;
		}
		
		return build(header, body);
	}

	@Override
	public AbstractSecs1Message build(
			SecsMessage primaryMsg,
			int strm,
			int func,
			boolean wbit)
					throws Secs1TooBigSendMessageException {
		
		return this.build(primaryMsg, strm, func, wbit, Secs2.empty());
	}

	@Override
	public AbstractSecs1Message build(
			SecsMessage primaryMsg,
			int strm,
			int func,
			boolean wbit,
			Secs2 body)
					throws Secs1TooBigSendMessageException {
		
		byte[] dd = this.getDeviceId2Bytes();
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
		
		if ( this.comm.isEquip() ) {
			header[0] |= (byte)0x80;
		}
		
		if ( wbit ) {
			header[2] |= (byte)0x80;
		}
		
		return build(header, body);
	}
	
	public static AbstractSecs1Message build(byte[] header)
			throws Secs1TooBigSendMessageException {
		
		return AbstractSecs1MessageBuilder.build(header, Secs2.empty());
	}
	
	public static AbstractSecs1Message build(byte[] header, Secs2 body)
			throws Secs1TooBigSendMessageException {
		
		final List<Secs1MessageBlock> blocks = new ArrayList<>();
		
		final List<byte[]> ll = body.getBytesList(244);
		
		if ( ll.size() > 0x7FFE) {
			throw new Secs1TooBigSendMessageException(exceptRefMessage(header, body));
		}
		
		int blockNum = AbstractSecs1MessageBlock.ONE;
		int m = ll.size() - 1;
		
		for ( int i = 0; i < m; ++i ) {
			blocks.add(buildBlock(header, ll.get(i), false, blockNum));
			++ blockNum;
		}
		
		blocks.add(buildBlock(header, ll.get(m), true, blockNum));
		
		return Secs1s.newMessage(header, body, blocks);
	}
	
	private static AbstractSecs1Message exceptRefMessage(byte[] header10Bytes, Secs2 body) {
		return Secs1s.newMessage(header10Bytes, body, Collections.emptyList());
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
		
		return Secs1s.newMessageBlock(bs);
	}
	
	public static AbstractSecs1Message fromBlocks(
			List<? extends Secs1MessageBlock> blocks)
					throws Secs2BytesParseException {
		
		byte[] header10Bytes = Arrays.copyOfRange(blocks.get(blocks.size() - 1).getBytes(), 1, 11);
		
		List<byte[]> bss = blocks.stream()
				.map(Secs1MessageBlock::getBytes)
				.map(bs -> Arrays.copyOfRange(bs, 11, bs.length - 2))
				.collect(Collectors.toList());
		
		Secs2 body = Secs2BytesParsers.parse(bss);
		
		return Secs1s.newMessage(header10Bytes, body, blocks);
	}
	
	public static AbstractSecs1Message fromMessage(Secs1Message message) throws Secs1TooBigSendMessageException {
		return Secs1s.castOrNewMessage(message);
	}
	
}
