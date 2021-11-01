package com.shimizukenta.secs.secs1;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.shimizukenta.secs.ReadOnlyTimeProperty;

public class Secs1TransactionManager<T extends AbstractSecs1Message, U extends AbstractSecs1MessageBlock> {
	
	private final Map<Integer, Pack> map = new HashMap<>();
	
	public Secs1TransactionManager() {
		/* Nothing */
	}
	
	public void clear() {
		synchronized ( map ) {
			this.map.clear();
		}
	}
	
	public void enter(T primaryMsg) {
		synchronized ( map ) {
			this.map.put(primaryMsg.systemBytesKey(), new Pack());
		}
	}
	
	public void exit(T primaryMsg) {
		synchronized ( map ) {
			this.map.remove(primaryMsg.systemBytesKey());
		}
	}
	
	private Pack getPack(Integer key) {
		synchronized ( map ) {
			return this.map.get(key);
		}
	}
	
	private Pack getPack(T msg) {
		return this.getPack(msg.systemBytesKey());
	}
	
	public T waitReply(T msg, ReadOnlyTimeProperty timeout) throws InterruptedException {
		return waitReply(msg, timeout.getMilliSeconds(), TimeUnit.MILLISECONDS);
	}
	
	public T waitReply(T msg, long timeout, TimeUnit unit) throws InterruptedException {
		
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
				
				unit.timedWait(p, timeout);
				
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
	
	public void resetTimer(AbstractSecs1MessageBlock block) {
		
		Pack p = getPack(block.systemBytesKey());
		
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
