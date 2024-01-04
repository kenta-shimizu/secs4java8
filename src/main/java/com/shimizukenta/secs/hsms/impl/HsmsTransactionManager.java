package com.shimizukenta.secs.hsms.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.shimizukenta.secs.hsms.HsmsMessage;
import com.shimizukenta.secs.local.property.TimeoutProperty;

public class HsmsTransactionManager<T extends HsmsMessage> {
	
	private final Map<Integer, Pack> map = new HashMap<>();
	
	public HsmsTransactionManager() {
		/* Nothing */
	}
	
	public void clear() {
		synchronized ( map ) {
			map.clear();
		}
	}
	
	protected static Integer systemBytesKey(HsmsMessage msg) {
		byte[] bs = msg.header10Bytes();
		int i = ((((int)(bs[6])) << 24) & 0xFF000000)
				| ((((int)(bs[7])) << 16) & 0x00FF0000)
				| ((((int)(bs[8])) << 8) & 0x0000FF00)
				| (((int)(bs[9])) & 0x000000FF);
		return Integer.valueOf(i);
	}
	
	public void enter(T primaryMsg) {
		synchronized ( map ) {
			map.put(systemBytesKey(primaryMsg), new Pack());
		}
	}
	
	public void exit(T primaryMsg) {
		synchronized ( map ) {
			map.remove(systemBytesKey(primaryMsg));
		}
	}
	
	private Pack getPack(Integer key) {
		synchronized ( map ) {
			return map.get(key);
		}
	}
	
	private Pack getPack(T msg) {
		return getPack(systemBytesKey(msg));
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
	
	public T reply(T msg, TimeoutProperty tp) throws InterruptedException {
		return reply(msg, tp.getTimeout(), tp.getTimeUnit());
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
