package com.shimizukenta.secs.hsmsss;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import com.shimizukenta.secs.ReadOnlyTimeProperty;

public class HsmsSsReplyMessageManager {
	
	private final Map<Integer, Pack> packMap = new HashMap<>();
	
	public HsmsSsReplyMessageManager() {
		/* Nothing */
	}
	
	public void clear() {
		synchronized ( this ) {
			this.packMap.clear();
		}
	}
	
	public void entry(HsmsSsMessage primaryMsg) {
		synchronized ( this ) {
			this.packMap.put(primaryMsg.systemBytesKey(), new Pack());
		}
	}
	
	public void exit(HsmsSsMessage primaryMsg) {
		synchronized ( this ) {
			this.packMap.remove(primaryMsg.systemBytesKey());
		}
	}
	
	private Pack getPack(HsmsSsMessage msg) {
		synchronized ( this ) {
			return getPack(msg.systemBytesKey());
		}
	}
	
	private Pack getPack(Integer key) {
		synchronized ( this ) {
			return this.packMap.get(key);
		}
	}
	
	public Optional<HsmsSsMessage> reply(HsmsSsMessage primaryMsg, long timeout, TimeUnit unit) throws InterruptedException {
		
		final Pack p = getPack(primaryMsg);
		
		if ( p != null ) {
		
			synchronized ( p ) {
				
				{
					HsmsSsMessage r = p.replyMsg();
					if ( r != null ) {
						return Optional.of(r);
					}
				}
				
				unit.timedWait(p, timeout);
				
				{
					HsmsSsMessage r = p.replyMsg();
					if ( r != null ) {
						return Optional.of(r);
					}
				}
			}
		}
		
		return Optional.empty();
	}
	
	public Optional<HsmsSsMessage> reply(HsmsSsMessage primaryMsg, ReadOnlyTimeProperty timeout) throws InterruptedException {
		return this.reply(primaryMsg, timeout.getMilliSeconds(), TimeUnit.MILLISECONDS);
	}
	
	/**
	 * Return {@code Optional.empty()} if has primaryMsg, otherwise Optional has value.
	 * 
	 * @param msg
	 * @return Return {@code Optional.empty()} if has primaryMsg, otherwise Optional has value
	 */
	public Optional<HsmsSsMessage> put(HsmsSsMessage msg) {
		
		final Pack p = getPack(msg);
		
		if ( p == null ) {
			
			return Optional.of(msg);
			
		} else {
			
			synchronized ( p ) {
				
				p.putReplyMsg(msg);
				p.notifyAll();
				
				return Optional.empty();
			}
		}
	}
	
	private class Pack {
		
		private HsmsSsMessage replyMsg;
		
		public Pack() {
			this.replyMsg = null;
		}
		
		public void putReplyMsg(HsmsSsMessage msg) {
			synchronized ( this ) {
				this.replyMsg = msg;
				this.notifyAll();
			}
		}
		
		public HsmsSsMessage replyMsg() {
			synchronized ( this ) {
				return this.replyMsg;
			}
		}
	}
}
