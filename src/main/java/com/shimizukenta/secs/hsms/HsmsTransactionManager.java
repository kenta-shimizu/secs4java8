package com.shimizukenta.secs.hsms;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.shimizukenta.secs.ReadOnlyTimeProperty;

public class HsmsTransactionManager<T extends AbstractHsmsMessage> {
	
	private final Map<Integer, Pack> map = new HashMap<>();
	
	public HsmsTransactionManager() {
		/* Nothing */
	}
	
	public void clear() {
		synchronized ( map ) {
			map.clear();
		}
	}
	
	public void enter(T primaryMsg) {
		synchronized ( map ) {
			map.put(primaryMsg.systemBytesKey(), new Pack());
		}
	}
	
	public void exit(T primaryMsg) {
		synchronized ( map ) {
			map.remove(primaryMsg.systemBytesKey());
		}
	}
	
	private Pack getPack(Integer key) {
		synchronized ( map ) {
			return map.get(key);
		}
	}
	
	private Pack getPack(T msg) {
		return getPack(msg.systemBytesKey());
	}
	
	public T reply(T msg, long timeout, TimeUnit unit) throws InterruptedException {
		
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
			
			unit.timedWait(p, timeout);
			
			return p.replyMsg();
		}
	}
	
	public T reply(T msg, ReadOnlyTimeProperty timeout) throws InterruptedException {
		return reply(msg, timeout.getMilliSeconds(), TimeUnit.MILLISECONDS);
	}
	
	public T put(T msg) {
		
		final Pack p = getPack(msg);
		
		if ( p == null ) {
			
			return msg;
			
		} else {
			
			p.putReplyMsg(msg);
			return null;
		}
	}
	
	private class Pack {
		
		private T replyMsg;
		
		public Pack() {
			this.replyMsg = null;
		}
		
		public void putReplyMsg(T replyMsg) {
			synchronized ( this ) {
				this.replyMsg = replyMsg;
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
