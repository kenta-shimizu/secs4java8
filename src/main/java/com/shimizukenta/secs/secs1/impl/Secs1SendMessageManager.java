package com.shimizukenta.secs.secs1.impl;

import java.util.HashMap;
import java.util.Map;

import com.shimizukenta.secs.secs1.Secs1Exception;
import com.shimizukenta.secs.secs1.Secs1Message;
import com.shimizukenta.secs.secs1.Secs1SendMessageException;

public final class Secs1SendMessageManager {
	
	private final Map<Integer, Result> map = new HashMap<>();
	
	public Secs1SendMessageManager() {
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
	
	public void clear() {
		synchronized ( this.map ) {
			this.map.clear();
		}
	}
	
	public void enter(Secs1Message msg) {
		synchronized ( this.map ) {
			this.map.put(systemBytesKey(msg), new Result());
		}
	}
	
	public void exit(Secs1Message msg) {
		synchronized ( this.map ) {
			this.map.remove(systemBytesKey(msg));
		}
	}
	
	private Result getResult(Integer key) {
		synchronized ( this.map ) {
			return this.map.get(key);
		}
	}
	
	private Result getResult(Secs1Message msg) {
		return this.getResult(systemBytesKey(msg));
	}
	
	public void waitUntilSended(Secs1Message msg)
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
	
	public void putSended(Secs1Message msg) {
		
		final Result r = this.getResult(msg);
		
		if ( r != null ) {
			r.setSended();
		}
	}
	
	public void putException(Secs1Message msg, Secs1Exception e) {
		
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
