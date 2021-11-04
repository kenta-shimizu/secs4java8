package com.shimizukenta.secs.secs1;

import java.util.HashMap;
import java.util.Map;

public final class Secs1SendMessageManager {
	
	private final Map<Integer, Result> map = new HashMap<>();
	
	public Secs1SendMessageManager() {
		/* Nothing */
	}
	
	public void clear() {
		synchronized ( this.map ) {
			this.map.clear();
		}
	}
	
	public void enter(AbstractSecs1Message msg) {
		synchronized ( this.map ) {
			this.map.put(msg.systemBytesKey(), new Result());
		}
	}
	
	public void exit(AbstractSecs1Message msg) {
		synchronized ( this.map ) {
			this.map.remove(msg.systemBytesKey());
		}
	}
	
	private Result getResult(Integer key) {
		synchronized ( this.map ) {
			return this.map.get(key);
		}
	}
	
	private Result getResult(AbstractSecs1Message msg) {
		return this.getResult(msg.systemBytesKey());
	}
	
	public void waitUntilSended(AbstractSecs1Message msg)
			throws Secs1SendMessageException, InterruptedException {
		
		final Result r = getResult(msg);
		
		if ( r == null ) {
			throw new IllegalStateException("message not enter");
		}
		
		synchronized ( r ) {
			
			for ( ;; ) {
				
				if ( r.isSended() ) {
					return;
				}
				
				Secs1Exception e = r.except();
				if ( e != null ) {
					throw new Secs1SendMessageException(msg, e);
				}
				
				r.wait();
			}
			
		}
	}
	
	public void putSended(AbstractSecs1Message msg) {
		
		final Result r = this.getResult(msg);
		
		if ( r != null ) {
			r.setSended();
		}
	}
	
	public void putException(AbstractSecs1Message msg, Secs1Exception e) {
		
		final Result r = this.getResult(msg);
		
		if ( r != null ) {
			r.setExcept(e);
		}
	}
	
	private class Result {
		
		private boolean sended;
		private Secs1Exception except;
		
		public Result() {
			this.sended = false;
			this.except = null;
		}
		
		public void setSended() {
			synchronized ( this ) {
				this.sended = true;
				this.notifyAll();
			}
		}
		
		public boolean isSended() {
			synchronized ( this ) {
				return this.sended;
			}
		}
		
		public void setExcept(Secs1Exception e) {
			synchronized ( this ) {
				this.except = e;
				this.notifyAll();
			}
		}
		
		public Secs1Exception except() {
			synchronized ( this ) {
				return this.except;
			}
		}
	}
	
}
