package com.shimizukenta.secs.secs1.impl;

import java.util.HashMap;
import java.util.Map;

import com.shimizukenta.secs.local.property.TimeoutProperty;
import com.shimizukenta.secs.secs1.Secs1Message;
import com.shimizukenta.secs.secs1.Secs1MessageBlock;

public class Secs1TransactionManager<T extends Secs1Message, U extends Secs1MessageBlock> {
	
	private final Map<Integer, Pack> map = new HashMap<>();
	
	public Secs1TransactionManager() {
		/* Nothing */
	}
	
	private static Integer systemBytesKey(Secs1Message msg) {
		byte[] bs = msg.header10Bytes();
		int i = (((int)(bs[6]) << 24) & 0xFF000000)
				| (((int)(bs[7]) << 16) & 0x00FF0000)
				| (((int)(bs[8]) <<  8) & 0x0000FF00)
				| (((int)(bs[9])      ) & 0x000000FF);
		return Integer.valueOf(i);
	}
	
	private static Integer systemBytesKey(Secs1MessageBlock block) {
		byte[] bs = block.getBytes();
		int i = (((int)(bs[7]) << 24) & 0xFF000000)
				| (((int)(bs[8]) << 16) & 0x00FF0000)
				| (((int)(bs[9]) <<  8) & 0x0000FF00)
				| (((int)(bs[10])     ) & 0x000000FF);
		return Integer.valueOf(i);
	}
	
	public void clear() {
		synchronized ( map ) {
			this.map.clear();
		}
	}
	
	public void enter(T primaryMsg) {
		synchronized ( map ) {
			this.map.put(systemBytesKey(primaryMsg), new Pack());
		}
	}
	
	public void exit(T primaryMsg) {
		synchronized ( map ) {
			this.map.remove(systemBytesKey(primaryMsg));
		}
	}
	
	private Pack getPack(Integer key) {
		synchronized ( map ) {
			return this.map.get(key);
		}
	}
	
	private Pack getPack(T msg) {
		return this.getPack(systemBytesKey(msg));
	}
	
	public T waitReply(T msg, TimeoutProperty timeout) throws InterruptedException {
		
		final Pack p = getPack(msg);
		
		if ( p == null ) {
			return null;
		}
		
		synchronized ( p ) {
			
			{
				T r = p.replyMsg();
				if ( r != null ) {
					return r;
				}
			}
			
			for ( ;; ) {
				
				timeout.wait(p);
				
				T r = p.replyMsg();
				
				if ( r != null ) {
					return r;
				}
				
				if ( ! p.isTimerResetted() ) {
					return null;
				}
			}
		}
	}
	
	public T put(T msg) {
		
		final Pack p = this.getPack(msg);
		
		if ( p == null ) {
			
			return msg;
			
		} else {
			
			p.putReplyMsg(msg);
			return null;
		}
	}
	
	public void resetTimer(Secs1MessageBlock block) {
		
		Pack p = getPack(systemBytesKey(block));
		
		if ( p != null ) {
			p.resetTimer();
		}
	}
	
	private final class Pack {
		
		private boolean timerResetted;
		private T replyMsg;
		
		public Pack() {
			this.timerResetted = false;
			this.replyMsg = null;
		}
		
		public void resetTimer() {
			synchronized ( this ) {
				this.timerResetted = true;
				this.notifyAll();
			}
		}
		
		public boolean isTimerResetted() {
			synchronized ( this ) {
				boolean f = this.timerResetted;
				this.timerResetted = false;
				return f;
			}
		}
		
		public void putReplyMsg(T msg) {
			synchronized ( this ) {
				this.replyMsg = msg;
				this.notifyAll();
			}
		}
		
		public T replyMsg() {
			synchronized ( this ) {
				return this.replyMsg;
			}
		}
	}
	
}
