package com.shimizukenta.secs.secs1;

import java.util.HashMap;
import java.util.Map;

import com.shimizukenta.secs.SecsException;

public final class Secs1SendMessageManager {
	
	private final Map<Integer, Result> resultMap = new HashMap<>();
	
	public Secs1SendMessageManager() {
		/* Nothing */
	}
	
	public void clear() {
		synchronized ( resultMap ) {
			resultMap.clear();
		}
	}
	
	public void entry(Secs1Message msg) {
		synchronized ( resultMap ) {
			resultMap.put(msg.systemBytesKey(), new Result());
		}
	}
	
	public void exit(Secs1Message msg) {
		synchronized ( resultMap ) {
			resultMap.remove(msg.systemBytesKey());
		}
	}
	
	public Result result(Secs1Message msg) {
		synchronized ( resultMap ) {
			return resultMap.get(msg.systemBytesKey());
		}
	}
	
	public void waitUntilSended(Secs1Message msg)
			throws SecsException, InterruptedException {
		
		final Result r = result(msg);
		
		synchronized ( r ) {
			
			for ( ;; ) {
				
				if ( r.isSended() ) {
					return;
				}
				
				SecsException e = r.except();
				if ( e != null ) {
					throw e;
				}
				
				r.wait();
			}
			
		}
	}
	
	public void putSended(Secs1Message msg) throws InterruptedException {
		
		final Result r = result(msg);
		
		synchronized ( r ) {
			r.setSended();
			r.notifyAll();
		}
	}
	
	public void putException(Secs1Message msg, SecsException e) throws InterruptedException {
		
		final Result r = result(msg);
		
		synchronized ( r ) {
			r.setExcept(e);
			r.notifyAll();
		}
	}
	
	private static class Result {
		
		private boolean sended;
		private SecsException except;
		
		public Result() {
			this.sended = false;
			this.except = null;
		}
		
		public void setSended() {
			synchronized ( this ) {
				this.sended = true;
			}
		}
		
		public boolean isSended() {
			synchronized ( this ) {
				return this.sended;
			}
		}
		
		public void setExcept(SecsException e) {
			synchronized ( this ) {
				this.except = e;
			}
		}
		
		public SecsException except() {
			synchronized ( this ) {
				return this.except;
			}
		}
	}

	
}
