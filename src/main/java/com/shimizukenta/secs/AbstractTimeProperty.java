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
public abstract class AbstractTimeProperty extends AbstractNumberProperty
		implements TimeProperty {
	
	private static final long serialVersionUID = 4647118881525451997L;
	
	private final Object sync = new Object();
	
	private long milliSec;
	
	public AbstractTimeProperty(Number initial) {
		super(initial);
		setMilliSeconds(initial);
	}
	
	public AbstractTimeProperty(int initial) {
		this(Integer.valueOf(initial));
	}
	
	public AbstractTimeProperty(long initial) {
		this(Long.valueOf(initial));
	}
	
	public AbstractTimeProperty(float initial) {
		this(Float.valueOf(initial));
	}
	
	public AbstractTimeProperty(double initial) {
		this(Double.valueOf(initial));
	}
	
	@Override
	public void set(Number v) {
		synchronized ( sync ) {
			setMilliSeconds(v);
			super.set(v);
		}
	}
	
	private void setMilliSeconds(Number v) {
		synchronized ( sync ) {
			this.milliSec = (long)(Objects.requireNonNull(v).floatValue() * 1000.0F);
		}
	}
	
	public float getSeconds() {
		synchronized ( sync ) {
			return floatValue();
		}
	}
	
	@Override
	public long getMilliSeconds() {
		synchronized ( sync ) {
			return milliSec;
		}
	}
	
	@Override
	public boolean gtZero() {
		synchronized ( sync ) {
			return milliSec > 0;
		}
	}
	
	@Override
	public boolean geZero() {
		synchronized ( sync ) {
			return milliSec >= 0;
		}
	}
	
	@Override
	public void sleep() throws InterruptedException {
		long t = getMilliSeconds();
		if ( t > 0 ) {
			TimeUnit.MILLISECONDS.sleep(t);
		}
	}
	
	@Override
	public void wait(Object syncObj) throws InterruptedException {
		synchronized ( syncObj ) {
			long t = getMilliSeconds();
			if ( t >= 0 ) {
				syncObj.wait(t);
			} else {
				syncObj.wait();
			}
		}
	}
	
	@Override
	public <T> T future(Future<T> f)
			throws InterruptedException, TimeoutException, ExecutionException {
		return f.get(getMilliSeconds(), TimeUnit.MILLISECONDS);
	}
	
	@Override
	public <T> T poll(BlockingQueue<T> queue) throws InterruptedException {
		return queue.poll(getMilliSeconds(), TimeUnit.MILLISECONDS);
	}
	
}
