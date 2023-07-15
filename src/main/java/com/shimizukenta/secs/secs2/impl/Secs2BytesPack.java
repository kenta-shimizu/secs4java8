package com.shimizukenta.secs.secs2.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Secs2BytesPack {
	
	private final List<byte[]> bytes;
	private long cacheSize;
	
	public Secs2BytesPack(List<byte[]> bytes) {
		this.bytes = new ArrayList<>(bytes);
		this.cacheSize = -1;
	}
	
	public List<byte[]> getBytes() {
		return Collections.unmodifiableList(this.bytes);
	}
	
	public long size() {
		
		synchronized ( this ) {
			
			if ( this.cacheSize < 0 ) {
				
				this.cacheSize = this.bytes.stream()
						.mapToLong(bs -> (long)(bs.length))
						.sum();
			}
			
			return this.cacheSize;
		}
	}
}
