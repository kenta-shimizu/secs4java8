package com.shimizukenta.secs.hsms;

import java.util.HashMap;
import java.util.Map;

public class HsmsReplyMessageManager<T extends HsmsMessage> {
	
	private final Map<Integer, Pack<T>> map = new HashMap<>();
	
	public HsmsReplyMessageManager() {
		/* Nothing */
	}
	
	public void clear() {
		synchronized ( map ) {
			map.clear();
		}
	}
	
	public void entry(T primaryMsg) {
		synchronized ( map ) {
			map.put(primaryMsg.systemBytesKey(), new Pack<T>());
		}
	}
	
	public void exit(T primaryMsg) {
		synchronized ( map ) {
			map.remove(primaryMsg.systemBytesKey());
		}
	}
	
	private static class Pack<T> {
		
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
