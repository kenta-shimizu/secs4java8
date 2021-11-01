package com.shimizukenta.secs.secs1;

import java.io.Serializable;
import java.util.Arrays;

public class AbstractSecs1MessageBlock implements Secs1MessageBlock, Serializable {
	
	private static final long serialVersionUID = 1841349953959422187L;
	
	public static final int ZERO = 0;
	public static final int ONE = 1;
	
	private final Object sync = new Object();
	
	private final byte[] bs;
	private Integer cacheSystemBytesKey;
	
	public AbstractSecs1MessageBlock(byte[] bs) {
		this.bs = Arrays.copyOf(bs, bs.length);
		this.cacheSystemBytesKey = null;
	}
	
	@Override
	public int deviceId() {
		return (((int)(this.bs[1]) << 8) & 0x00007F00) | ((int)(this.bs[2]) & 0x000000FF);
	}
	
	@Override
	public boolean ebit() {
		return ((int)(this.bs[5]) & 0x80) == 0x80;
	}
	
	@Override
	public int blockNumber() {
		return (((int)(this.bs[5]) << 8) & 0x00007F00) | ((int)(this.bs[6]) & 0x000000FF);
	}
	
	@Override
	public boolean isFirstBlock() {
		int n = this.blockNumber();
		return n == ZERO || n == ONE;
	}
	
	@Override
	public int length() {
		return (int)(this.bs[0]) & 0x000000FF;
	}
	
	@Override
	public byte[] getBytes() {
		return Arrays.copyOf(this.bs, this.bs.length);
	}
	
	@Override
	public boolean checkSum() {
		
		int i = this.length();
		
		int v = ((int)(this.bs[i + 1]) << 8) & 0x0000FF00;
		v |= (int)(this.bs[i + 2]) & 0x000000FF;
		
		for (; i > 0; --i) {
			v -= (int)(this.bs[i]) & 0x000000FF;
		}
		
		return v == 0;
	}
	
	@Override
	public boolean equalsSystemBytes(Secs1MessageBlock otherBlock) {
		
		final byte[] o = otherBlock.getBytes();
		
		return o[7] == this.bs[7]
				&& o[8] == this.bs[8]
				&& o[9] == this.bs[9]
				&& o[10] == this.bs[10];
	}
	
	@Override
	public boolean isNextBlock(Secs1MessageBlock nextBlock) {
		
		int n = this.blockNumber();
		
		if ( n == ZERO ) {
			
			return false;
			
		} else {
			
			return nextBlock.blockNumber() == n + 1;
		}
	}
	
	public Integer systemBytesKey() {
		synchronized ( sync ) {
			if ( this.cacheSystemBytesKey == null ) {
				
				int n = (((int)(this.bs[7]) << 24) & 0xFF000000)
						| (((int)(this.bs[8]) << 16) & 0x00FF0000)
						| (((int)(this.bs[9]) <<  8) & 0x0000FF00)
						| ((int)(this.bs[10]) & 0x000000FF);
				
				this.cacheSystemBytesKey = Integer.valueOf(n);
			}
			return this.cacheSystemBytesKey;
		}
	}
	
}
