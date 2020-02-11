package com.shimizukenta.secs.secs2;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Secs2ByteBuffersBuilder {
	
	private final int byteBufferSize;
	private final LinkedList<ByteBuffer> buffers = new LinkedList<>();
	
	private List<ByteBuffer> proxyBuffers;
	private long size;
	private int blocks;
	
	private Secs2ByteBuffersBuilder(int byteBufferSize) {
		this.byteBufferSize = byteBufferSize;
		this.buffers.add(ByteBuffer.allocate(byteBufferSize));
		proxyBuffers = null;
	}
	
	public static Secs2ByteBuffersBuilder build(int byteBufferSize, Secs2 secs2) throws Secs2BuildException {
		
		Secs2ByteBuffersBuilder inst = new Secs2ByteBuffersBuilder(byteBufferSize);
		
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
	
	public List<ByteBuffer> getByteBuffers() {
		synchronized ( this ) {
			if ( proxyBuffers == null ) {
				build();
			}
			return proxyBuffers;
		}
	}
	
	public long size() {
		synchronized ( this ) {
			if ( proxyBuffers == null ) {
				build();
			}
			return size;
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
		
		this.proxyBuffers = Collections.unmodifiableList(buffers);
		
		this.size = buffers.stream()
				.mapToLong(bf -> bf.remaining())
				.sum();
	}
}
