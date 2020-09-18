package com.shimizukenta.secs;

import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class TimeProperty extends NumberProperty {
	
	private static final long serialVersionUID = -2905213245311975065L;
	
	private long milliSec;
	
	public TimeProperty(Number initial) {
		super(initial);
		setMilliSeconds(initial);
	}
	
	public TimeProperty(int initial) {
		this(Integer.valueOf(initial));
	}
	
	public TimeProperty(long initial) {
		this(Long.valueOf(initial));
	}
	
	public TimeProperty(float initial) {
		this(Float.valueOf(initial));
	}
	
	public TimeProperty(double initial) {
		this(Double.valueOf(initial));
	}
	
	@Override
	public void set(Number v) {
		synchronized ( this ) {
			setMilliSeconds(v);
			super.set(v);
		}
	}
	
	private void setMilliSeconds(Number v) {
		synchronized ( this ) {
			this.milliSec = (long)(Objects.requireNonNull(v).floatValue() * 1000.0F);
		}
	}
	
	public float getSeconds() {
		synchronized ( this ) {
			return floatValue();
		}
	}
	
	public long getMilliSeconds() {
		synchronized ( this ) {
			return milliSec;
		}
	}
	
	/**
	 * 
	 * @return true if > 0
	 */
	public boolean gtZero() {
		synchronized ( this ) {
			return milliSec > 0;
		}
	}
	
	/**
	 * 
	 * @return true if >= 0
	 */
	public boolean geZero() {
		synchronized ( this ) {
			return milliSec >= 0;
		}
	}
	
	public void sleep() throws InterruptedException {
		long t = getMilliSeconds();
		if ( t > 0 ) {
			TimeUnit.MILLISECONDS.sleep(t);
		}
	}
	
	public void wait(Object sync) throws InterruptedException {
		synchronized ( sync ) {
			long t = getMilliSeconds();
			if ( t >= 0 ) {
				sync.wait(t);
			} else {
				sync.wait();
			}
		}
	}
	
	public <T> T future(Future<T> f)
			throws InterruptedException, TimeoutException, ExecutionException {
		return f.get(getMilliSeconds(), TimeUnit.MILLISECONDS);
	}
	
	public <T> T poll(BlockingQueue<T> queue) throws InterruptedException {
		return queue.poll(getMilliSeconds(), TimeUnit.MILLISECONDS);
	}
	
}
