package com.shimizukenta.secs.secs1;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import com.shimizukenta.secs.ReadOnlyTimeProperty;

public final class Secs1ReplyMessageManager {
	
	private final Map<Integer, Pack> packMap = new HashMap<>();
	
	public Secs1ReplyMessageManager() {
		/* Nothing */
	}
	
	public void clear() {
		synchronized ( packMap ) {
			packMap.clear();
		}
	}
	
	public void entry(Secs1Message primaryMsg) throws InterruptedException {
		synchronized ( packMap ) {
			packMap.put(primaryMsg.systemBytesKey(), new Pack());
		}
	}
	
	public void exit(Secs1Message primaryMsg) throws InterruptedException {
		synchronized ( packMap ) {
			packMap.remove(primaryMsg.systemBytesKey());
		}
	}
	
	private Pack getPack(Secs1Message msg) {
		synchronized ( packMap ) {
			return getPack(msg.systemBytesKey());
		}
	}
	
	private Pack getPack(Integer key) {
		synchronized ( packMap ) {
			return packMap.get(key);
		}
	}
	
	public Optional<Secs1Message> reply(Secs1Message primaryMsg, long timeout, TimeUnit unit) throws InterruptedException {
		
		final Pack p = getPack(primaryMsg);
		
		if ( p == null ) {
			return Optional.empty();
		}
		
		synchronized ( p ) {
			
			for ( ;; ) {
				
				final Secs1Message r = p.replyMsg();
				
				if ( r != null ) {
					return Optional.of(r);
				}
				
				unit.timedWait(p, timeout);
				
				if ( ! p.isTimerResetted() ) {
					return Optional.empty();
				}
			}
		}
	}
	
	public Optional<Secs1Message> reply(Secs1Message primaryMsg, ReadOnlyTimeProperty timeout) throws InterruptedException {
		return this.reply(primaryMsg, timeout.getMilliSeconds(), TimeUnit.MILLISECONDS);
	}
	
	public void resetTimer(Secs1MessageBlock block) throws InterruptedException {
		
		final Integer key = block.systemBytesKey();
		
		synchronized ( packMap ) {
			
			final Pack p = getPack(key);
			
			if ( p != null ) {
				
				synchronized ( p ) {
					p.resetTimer();
					p.notifyAll();
				}
			}
		}
	}
	
	/**
	 * Return {@code Optional.empty()} if has primaryMsg, otherwise Optional has value.
	 * 
	 * @param msg
	 * @return Return {@code Optional.empty()} if has primaryMsg, otherwise Optional has value
	 * @throws InterruptedException
	 */
	public Optional<Secs1Message> put(Secs1Message msg) throws InterruptedException {
		
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
	
	private static final class Pack {
		
		private boolean timerResetted;
		private Secs1Message replyMsg;
		
		public Pack() {
			this.timerResetted = false;
			this.replyMsg = null;
		}
		
		public void resetTimer() {
			synchronized ( this ) {
				this.timerResetted = true;
			}
		}
		
		public boolean isTimerResetted() {
			synchronized ( this ) {
				boolean f = this.timerResetted;
				this.timerResetted = false;
				return f;
			}
		}
		
		public void putReplyMsg(Secs1Message msg) {
			synchronized ( this ) {
				this.timerResetted = true;
				this.replyMsg = msg;
			}
		}
		
		public Secs1Message replyMsg() {
			synchronized ( this ) {
				return this.replyMsg;
			}
		}
		
	}
	
}
