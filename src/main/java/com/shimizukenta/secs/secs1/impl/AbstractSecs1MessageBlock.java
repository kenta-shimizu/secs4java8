package com.shimizukenta.secs.secs1.impl;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

import com.shimizukenta.secs.secs1.Secs1MessageBlock;

public abstract class AbstractSecs1MessageBlock implements Secs1MessageBlock, Serializable {
	
	private static final long serialVersionUID = 1841349953959422187L;
	
	public static final int ZERO = 0;
	public static final int ONE = 1;
	
	private final Object sync = new Object();
	
	private final byte[] bs;
	private int length;
	private boolean valid;
	
	private final int deviceId;
	private final boolean ebit;
	private final int blockNumber;
	private final boolean isFirstBlock;
	
	private String cacheToString;
	
	public AbstractSecs1MessageBlock(byte[] bs) {
		
		Objects.requireNonNull(bs);
		
		this.bs = Arrays.copyOf(bs, bs.length);
		
		this.length = -1;
		
		if (bs.length >= 13 && bs.length <= 257) {
			
			this.length = (int)(bs[0]) & 0x000000FF;
			
			if (this.length >= 10 && this.length <= 254) {
				
				if ((this.length + 3) == bs.length) {
					
					int i = this.length;
					
					int v = ((int)(bs[i + 1]) << 8) & 0x0000FF00;
					v |= (int)(bs[i + 2]) & 0x000000FF;
					
					for (; i > 0; --i) {
						v -= (int)(bs[i]) & 0x000000FF;
					}
					
					this.valid = v == 0;
				}
			}
		}
		
		if (this.valid) {
			
			this.deviceId = (((int)(this.bs[1]) << 8) & 0x00007F00) | ((int)(this.bs[2]) & 0x000000FF);
			this.ebit = ((int)(this.bs[5]) & 0x80) == 0x80;;
			this.blockNumber = (((int)(this.bs[5]) << 8) & 0x00007F00) | ((int)(this.bs[6]) & 0x000000FF);;
			this.isFirstBlock = this.blockNumber == ZERO || this.blockNumber == ONE;
			
			this.cacheToString = null;
			
		} else {
			
			this.length = -1;
			this.deviceId = -1;
			this.ebit = false;
			this.blockNumber = -1;
			this.isFirstBlock = false;
			
			this.cacheToString = "INVALID BLOCK DATA. SIZE=[" + bs.length + "]";
		}
	}
	
	@Override
	public boolean isValid() {
		return this.valid;
	}
	
	@Override
	public int deviceId() {
		return this.deviceId;
	}
	
	@Override
	public boolean ebit() {
		return this.ebit;
	}
	
	@Override
	public int blockNumber() {
		return this.blockNumber;
	}
	
	@Override
	public boolean isFirstBlock() {
		return this.isFirstBlock;
	}
	
	@Override
	public int length() {
		return this.length;
	}
	
	@Override
	public byte[] getBytes() {
		return Arrays.copyOf(this.bs, this.bs.length);
	}
	
	@Override
	public boolean checkSum() {
		return this.isValid();
	}
	
	@Override
	public boolean equalsSystemBytes(Secs1MessageBlock otherBlock) {
		
		if (otherBlock != null
				&& this.isValid()
				&& otherBlock.isValid()) {
			
			byte[] o = otherBlock.getBytes();
			
			return o[7] == this.bs[7]
					&& o[8] == this.bs[8]
					&& o[9] == this.bs[9]
					&& o[10] == this.bs[10];
		}
		
		return false;
	}
	
	@Override
	public boolean isNextBlock(Secs1MessageBlock nextBlock) {
		
		if (nextBlock != null
				&& this.isValid()
				&& nextBlock.isValid()) {
			
			return nextBlock.blockNumber() == (this.blockNumber() + 1);
		}
		
		return false;
	}
	
	@Override
	public String toString() {
		
		synchronized (this.sync) {
			
			if (this.cacheToString == null) {
				
				this.cacheToString = new StringBuilder()
						.append("[").append(String.format("%02X", bs[1]))
						.append(" ").append(String.format("%02X", bs[2]))
						.append("|").append(String.format("%02X", bs[3]))
						.append(" ").append(String.format("%02X", bs[4]))
						.append("|").append(String.format("%02X", bs[5]))
						.append(" ").append(String.format("%02X", bs[6]))
						.append("|").append(String.format("%02X", bs[7]))
						.append(" ").append(String.format("%02X", bs[8]))
						.append(" ").append(String.format("%02X", bs[9]))
						.append(" ").append(String.format("%02X", bs[10]))
						.append("] length: ").append(length())
						.toString();
			}
			
			return this.cacheToString;
		}
	}
	
}
