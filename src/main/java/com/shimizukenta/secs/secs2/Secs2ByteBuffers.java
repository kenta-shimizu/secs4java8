package com.shimizukenta.secs.secs2;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.LinkedList;

public class Secs2ByteBuffers {
	
	private final int byteBufferSize;
	private final LinkedList<ByteBuffer> buffers = new LinkedList<>();
	
	private ByteBuffer[] proxyBuffers;
	private long length;
	private int blocks;
	
	private Secs2ByteBuffers(int byteBufferSize) {
		this.byteBufferSize = byteBufferSize;
		this.buffers.add(ByteBuffer.allocate(byteBufferSize));
		proxyBuffers = null;
	}
	
	public static Secs2ByteBuffers build(int byteBufferSize, Secs2 secs2) throws Secs2BuildException {
		
		Secs2ByteBuffers inst = new Secs2ByteBuffers(byteBufferSize);
		
		if ( secs2 instanceof AbstractSecs2 ) {
			
			((AbstractSecs2)secs2).putByteBuffers(inst);
			return inst;
			
		} else {
			
			throw new Secs2BuildException("cast failed");
		}
	}
	
	protected void put(int v) {
		byte b = (byte)v;
		ByteBuffer buffer = buffers.getLast();
		if ( buffer.hasRemaining() ) {
			buffer.put(b);
		} else {
			ByteBuffer nextBf = ByteBuffer.allocate(byteBufferSize);
			nextBf.put(b);
			buffers.add(nextBf);
		}
	}
	
	protected void put(byte[] bs) {
		for ( byte b : bs ) {
			put(b);
		}
	}
	
	public ByteBuffer[] getByteBuffers() {
		synchronized ( this ) {
			if ( proxyBuffers == null ) {
				build();
			}
			return proxyBuffers;
		}
	}
	
	public long lentgh() {
		synchronized ( this ) {
			if ( proxyBuffers == null ) {
				build();
			}
			return length;
		}
	}
	
	public int blocks() {
		synchronized ( this ) {
			if ( proxyBuffers == null ) {
				build();
			}
			return blocks;
		}
	}
	
	private void build() {
		
		this.blocks = buffers.size();
		
		buffers.forEach(bf -> {
			((Buffer)bf).flip();
		});
		
		this.proxyBuffers = new ByteBuffer[this.blocks];
		
		for ( int i = 0; i < this.blocks; ++i ) {
			this.proxyBuffers[i] = buffers.get(i);
		}
		
		this.length = buffers.stream()
				.mapToLong(bf -> bf.remaining())
				.sum();
	}
}
