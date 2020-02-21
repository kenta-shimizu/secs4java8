package com.shimizukenta.secs.secs1;

import java.io.Serializable;
import java.util.Arrays;

public class Secs1MessageBlock implements Serializable {
	
	private static final long serialVersionUID = -4733761372402097825L;
	
	public static final int ZERO = 0;
	public static final int ONE  = 1;
	
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
	
	public boolean sameSystemBytes(Secs1MessageBlock block) {
		return block.systemBytesKey().equals(systemBytesKey());
	}
	
	public boolean isNextBlock(Secs1MessageBlock block) {
		return block.blockNumber() == (this.blockNumber() + 1);
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
	
}
