package com.shimizukenta.secs.secs2.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Secs2BytesListBuilder {
	
	private final List<byte[]> bss = new ArrayList<>();
	private final int maxBytesSize;
	private byte[] lastbs;
	private int pos;
	
	public Secs2BytesListBuilder(int maxBytesSize) {
		this.maxBytesSize = maxBytesSize;
		this.lastbs = new byte[this.maxBytesSize];
		this.pos = 0;
	}
	
	public void put(byte b) {
		put(new byte[] {b});
	}
	
	public void put(byte[] bs) {
		
		for (int i = 0, m = bs.length; i < m; ) {
			
			if ((m - i) > (this.maxBytesSize - pos)) {
				
				for ( ; pos < this.maxBytesSize; ++pos, ++i) {
					this.lastbs[pos] = bs[i];
				}
				
				this.bss.add(Arrays.copyOf(this.lastbs, maxBytesSize));
				this.pos = 0;
				
			} else {
				
				for ( ; i < m; ++i, ++pos) {
					this.lastbs[pos] = bs[i];
				}
			}
		}
	}
	
	public List<byte[]> getBytesList() {
		List<byte[]> aa = new ArrayList<>();
		aa.addAll(this.bss);
		aa.add(Arrays.copyOf(this.lastbs, pos));
		return Collections.unmodifiableList(aa);
	}
	
}
