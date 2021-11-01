package com.shimizukenta.secs.secs1;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.shimizukenta.secs.ReadOnlyTimeProperty;

public final class ByteAndSecs1MessageQueue {
	
	private final Object sync = new Object();
	private final BlockingQueue<Byte> bb = new LinkedBlockingQueue<>();
	private final Queue<Secs1MessageBlockPack> mm = new LinkedList<>();
	
	public ByteAndSecs1MessageQueue() {
		/* Nothing */
	}
	
	public void clear() {
		synchronized ( sync ) {
			bb.clear();
			mm.clear();
		}
	}
	
	public void putByte(byte b) throws InterruptedException {
		synchronized ( sync ) {
			bb.add(b);
			sync.notifyAll();
		}
	}
	
	public void putBytes(byte[] bs) throws InterruptedException {
		synchronized ( sync ) {
			for ( byte b : bs ) {
				bb.add(b);
			}
			sync.notifyAll();
		}
	}
	
	public void putSecs1Message(AbstractSecs1Message msg) throws Secs1SendMessageException, InterruptedException {
		synchronized ( sync ) {
			mm.add(new Secs1MessageBlockPack(msg));
			sync.notifyAll();
		}
	}
	
	public ByteOrSecs1Message takeByteOrSecs1Message() throws InterruptedException {
		
		synchronized ( sync ) {
			
			for ( ;; ) {
				
				Secs1MessageBlockPack msgPack = mm.poll();
				if ( msgPack != null ) {
					return new ByteOrSecs1Message(null, msgPack);
				}
				
				Byte b = pollByte();
				if ( b != null ) {
					return new ByteOrSecs1Message(b, null);
				}
				
				sync.wait();
			}
		}
	}
	
	public Byte pollByte() {
		return bb.poll();
	}
	
	public Byte pollByte(long timeout, TimeUnit unit) throws InterruptedException {
		return bb.poll(timeout, unit);
	}
	
	public Byte pollByte(ReadOnlyTimeProperty v) throws InterruptedException {
		return v.poll(bb);
	}
	
	/**
	 * input to bytes.
	 * 
	 * @param bs
	 * @param pos start position
	 * @param len size of limit
	 * @param timeout
	 * @param unit
	 * @return size of inputs
	 * @throws InterruptedException
	 */
	public int pollBytes(byte[] bs, int pos, int len, long timeout, TimeUnit unit) throws InterruptedException {
		
		int r = 0;
		
		for ( int i = pos; i < len; ++i ) {
			
			Byte b = bb.poll(timeout, unit);
			
			if ( b == null ) {
				
				break;
				
			} else {
				
				bs[i] = b.byteValue();
			}
			
			++ r;
		}
		
		return r;
	}
	
	/**
	 * input to bytes.
	 * 
	 * @param bs
	 * @param pos start position
	 * @param len size of limit
	 * @param timeout ReadOnlyTimeProperty
	 * @return size of inputs
	 * @throws InterruptedException
	 */
	public int pollBytes(byte[] bs, int pos, int len, ReadOnlyTimeProperty timeout) throws InterruptedException {
		return pollBytes(bs, pos, len, timeout.getMilliSeconds(), TimeUnit.MILLISECONDS);
	}
	
	public void garbageBytes(long timeout, TimeUnit unit) throws InterruptedException {
		this.bb.clear();
		for ( ;; ) {
			Byte b = bb.poll(timeout, unit);
			if ( b == null ) {
				return;
			}
		}
	}
	
	public void garbageBytes(ReadOnlyTimeProperty timeout) throws InterruptedException {
		this.garbageBytes(timeout.getMilliSeconds(), TimeUnit.MILLISECONDS);
	}
	
}
