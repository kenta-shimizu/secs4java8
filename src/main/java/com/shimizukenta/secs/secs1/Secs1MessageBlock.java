package com.shimizukenta.secs.secs1;

import java.util.Arrays;

public class Secs1MessageBlock {
	
	private static final int HEAD_SIZE = 10;
	
	private static final int ZERO = 0;
	private static final int ONE  = 1;
	
	private final byte[] bytes;
	
	public Secs1MessageBlock(byte[] bs) {
		this.bytes = Arrays.copyOf(bs, bs.length);
	}
	
	protected Secs1MessageBlock(byte[] head10bytes, byte[] body) {
		
		int len = head10bytes.length + body.length;
		
		this.bytes = new byte[len + 3];
		
		/* length-byte */
		this.bytes[0] = (byte)len;
		
		int sum = 0;
		
		for ( int i = 0 ; i < HEAD_SIZE ; ++i ) {
			byte b = head10bytes[i];
			this.bytes[i + 1] = b;
			sum += ((int)b & 0xFF);
		}
		
		for ( int i = 0, m = body.length; i < m ; ++i ) {
			byte b = body[i];
			this.bytes[i + 11] = b;
			sum += ((int)b & 0xFF);
		}
		
		this.bytes[len + 1] = (byte)(sum >> 8);
		this.bytes[len + 2] = (byte)sum;
	}
	
	public byte[] bytes() {
		return bytes;
	}
	
	public int deviceId() {
		return (((int)(bytes[1]) << 8) & 0x7F00) | (bytes[2] & 0xFF);
	}
	
	public int getStream() {
		return bytes[3] & 0x7F;
	}
	
	public int getFunction() {
		return bytes[4] & 0xFF;
	}
	
	public boolean rbit() {
		return (bytes[1] & 0x80) == 0x80;
	}
	
	public boolean wbit() {
		return (bytes[3] & 0x80) == 0x80;
	}
	
	public boolean ebit() {
		return (bytes[5] & 0x80) == 0x80;
	}
	
	public int blockNumber() {
		return (((int)(bytes[5]) << 8) & 0x7F00) | (bytes[6] & 0xFF);
	}
	
	public boolean isFirstBlock() {
		
		int n = blockNumber();
		
		if ( ebit() ) {
			
			return n == ZERO || n == ONE;
			
		} else {
			
			return n == ONE;
		}
	}
	
	public boolean isPrimaryBlock() {
		return ! isReplyBlock();
	}
	
	public boolean isReplyBlock() {
		return getFunction() % 2 == 0;
	}
	
	public Integer systemBytesKey() {
		
		int key;
		key =  ((int)(bytes[7]) << 24) & 0xFF000000;
		key |= ((int)(bytes[8]) << 16) & 0x00FF0000;
		key |= ((int)(bytes[9]) <<  8) & 0x0000FF00;
		key |= ((int)(bytes[10])     ) & 0x000000FF;
		
		return Integer.valueOf(key);
	}
	
	public boolean equalsSystemBytesKey(Secs1MessageBlock ref) {
		
		return ref.systemBytesKey().equals(systemBytesKey());
	}
	
	public boolean sameBlock(Secs1MessageBlock ref) {
		
		return (ref.bytes[1] == bytes[1]
				&& ref.bytes[2]  == bytes[2]
				&& ref.bytes[3]  == bytes[3]
				&& ref.bytes[4]  == bytes[4]
				&& ref.bytes[5]  == bytes[5]
				&& ref.bytes[6]  == bytes[6]
				&& ref.bytes[7]  == bytes[7]
				&& ref.bytes[8]  == bytes[8]
				&& ref.bytes[9]  == bytes[9]
				&& ref.bytes[10] == bytes[10]);
	}
	
	public boolean expectBlock(Secs1MessageBlock ref) {
		
		int expectBlockNumber = blockNumber() + 1;
		
		if ( ref.blockNumber() != expectBlockNumber ) {
			return false;
		}
		
		return (ref.bytes[1] == bytes[1]
				&& ref.bytes[2]  == bytes[2]
				&& ref.bytes[3]  == bytes[3]
				&& ref.bytes[4]  == bytes[4]
				&& ref.bytes[7]  == bytes[7]
				&& ref.bytes[8]  == bytes[8]
				&& ref.bytes[9]  == bytes[9]
				&& ref.bytes[10] == bytes[10]);
	}
	
	protected String toHeaderBytesString() {
		
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
				.append("] length: ").append(((int)bytes[0] & 0xFF))
				.toString();
	}
	
}
