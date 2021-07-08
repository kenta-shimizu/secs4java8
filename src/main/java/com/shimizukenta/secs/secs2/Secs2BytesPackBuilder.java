package com.shimizukenta.secs.secs2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Secs2BytesPackBuilder {
	
	private final List<byte[]> bss = new ArrayList<>();
	private final int bytesSize;
	private byte[] lastbs;
	private int pos;
	
	private Secs2BytesPackBuilder(int bytesSize) {
		this.bytesSize = bytesSize;
		this.lastbs = new byte[bytesSize];
		this.pos = 0;
	}
	
	public void put(byte b) {
		put(new byte[] {b});
	}
	
	public void put(byte[] bs) {
		
		for (int i = 0, m = bs.length ; i < m; ) {
			
			if ((m - i) > (this.bytesSize - pos)) {
				
				for ( ; pos < this.bytesSize; ++pos, ++i) {
					this.lastbs[pos] = bs[i];
				}
				
				this.bss.add(Arrays.copyOf(this.lastbs, bytesSize));
				this.pos = 0;
				
			} else {
				
				for ( ; i < m; ++i, ++pos) {
					this.lastbs[pos] = bs[i];
				}
			}
		}
	}
	
	private List<byte[]> getBytes() {
		List<byte[]> aa = new ArrayList<>();
		aa.addAll(this.bss);
		aa.add(Arrays.copyOf(this.lastbs, pos));
		return aa;
	}
	
	public static Secs2BytesPack build(int bytesSize, Secs2 secs2) throws Secs2BuildException {
		
		final Secs2BytesPackBuilder bb = new Secs2BytesPackBuilder(bytesSize);
		
		if ( secs2 instanceof AbstractSecs2 ) {
			
			((AbstractSecs2)secs2).putBytesPack(bb);
			return new Secs2BytesPack(bb.getBytes());
			
		} else {
			
			throw new Secs2BuildException("cast failed");
		}
	}

}
