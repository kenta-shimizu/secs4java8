package com.shimizukenta.secs;

import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Time value Getter, Setter, Value-Change-Observer<br />
 * Not accept null.
 * 
 * @author kenta-shimizu
 *
 */
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
	
	/**
	 * Seconds float getter
	 * 
	 * @return Seconds from get().floatValue()
	 */
	public float getSeconds() {
		synchronized ( this ) {
			return floatValue();
		}
	}
	
	/**
	 * MilliSeconds long getter.
	 * 
	 * @return MilliSeconds.
	 */
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
	
	/**
	 * Thread#sleep
	 * 
	 * @throws InterruptedException
	 */
	public void sleep() throws InterruptedException {
		long t = getMilliSeconds();
		if ( t > 0 ) {
			TimeUnit.MILLISECONDS.sleep(t);
		}
	}
	
	/**
	 * Synchronized wait
	 * 
	 * @param sync-object
	 * @throws InterruptedException
	 */
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
	
	/**
	 * Future#get
	 * 
	 * @param <T>
	 * @param f
	 * @return future#get result
	 * @throws InterruptedException
	 * @throws TimeoutException
	 * @throws ExecutionException
	 */
	public <T> T future(Future<T> f)
			throws InterruptedException, TimeoutException, ExecutionException {
		return f.get(getMilliSeconds(), TimeUnit.MILLISECONDS);
	}
	
	/**
	 * BlockingQueue#poll
	 * 
	 * @param <T>
	 * @param queue
	 * @return BlockingQueue#poll result
	 * @throws InterruptedException
	 */
	public <T> T poll(BlockingQueue<T> queue) throws InterruptedException {
		return queue.poll(getMilliSeconds(), TimeUnit.MILLISECONDS);
	}
	
}
