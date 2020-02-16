package com.shimizukenta.secs.secs1;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Secs1MessageBlock {
	
	private static final int ZERO = 0;
	private static final int ONE  = 1;
	
	private final byte[] bytes;
	
	public Secs1MessageBlock(byte[] bytes) {
		this.bytes = Arrays.copyOf(bytes, bytes.length);
	}
	
	public int deviceId() {
		return (((int)(bytes[1]) << 8) & 0x00007F00) | (bytes[2] & 0x000000FF);
	}
	
	public boolean ebit() {
		return (bytes[5] & 0x80) == 0x80;
	}
	
	public int blockNumber() {
		return (((int)(bytes[5]) << 8) & 0x00007F00) | ((int)(bytes[6]) & 0x000000FF);
	}
	
	public boolean isFirst() {
		int n = blockNumber();
		return n == ONE || n == ZERO;
	}
	
	public byte[] getBytes() {
		return Arrays.copyOf(bytes, bytes.length);
	}
	
	public Integer systemBytesKey() {
		int i = ((int)(bytes[7]) << 24) & 0xFF000000;
		i |= ((int)(bytes[8]) << 16) & 0x00FF0000;
		i |= ((int)(bytes[9]) <<  8) & 0x0000FF00;
		i |= (int)(bytes[10]) & 0x000000FF;
		return Integer.valueOf(i);
	}
	
	private int length() {
		return bytes[0] & 0xFF;
	}
	
	public boolean sumCheck() {
		
		try {
			int len = length();
			
			int sum = (((int)(bytes[len + 1]) << 8) & 0xFF00) | ((int)(bytes[len + 2]) & 0xFF);
			
			for ( int i = len; i > 0; --i ) {
				sum -= ((int)bytes[i]) & 0xFF;
			}
			
			return sum == 0;
		}
		catch ( IndexOutOfBoundsException e ) {
			return false;
		}
	}
	
	@Override
	public String toString() {
		
		try {
			return new StringBuilder()
					.append("[").append(String.format("%02X", bytes[1]))
					.append(" ").append(String.format("%02X", bytes[2]))
					.append("|").append(String.format("%02X", bytes[3]))
					.append(" ").append(String.format("%02X", bytes[4]))
					.append("|").append(String.format("%02X", bytes[5]))
					.append(" ").append(String.format("%02X", bytes[6]))
					.append("|").append(String.format("%02X", bytes[7]))
					.append(" ").append(String.format("%02X", bytes[8]))
					.append(" ").append(String.format("%02X", bytes[9]))
					.append(" ").append(String.format("%02X", bytes[10]))
					.append("] length: ").append(length())
					.toString();
		}
		catch ( IndexOutOfBoundsException e ) {
			return "#toString failed";
		}
	}
	
	public static List<Secs1MessageBlock> buildBlocks(byte[] head, List<ByteBuffer> buffers) {
		
		if ( head.length != 10 ) {
			throw new IllegalArgumentException("head not 10 bytes");
		}
		
		List<Secs1MessageBlock> blocks = new ArrayList<>();
		
		int bufferSize = buffers.size();
		int blockNum = ONE;
		
		for ( int i = 0, m = (bufferSize - 1); i < m; ++i ) {
			blocks.add(buildBlock(head, buffers.get(i), false, blockNum));
			++ blockNum;
		}
		
		blocks.add(buildBlock(head, buffers.get(bufferSize - 1), true, blockNum));
		
		return blocks;
	}
	
	private static Secs1MessageBlock buildBlock(byte[] head, ByteBuffer buffer, boolean ebit, int blockNumber) {
		
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

}
