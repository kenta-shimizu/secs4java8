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
		implements ReadOnlyTimeProperty {
	
	private static final long serialVersionUID = 4647118881525451997L;
	
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
	
	@Override
	public long getMilliSeconds() {
		synchronized ( this ) {
			return milliSec;
		}
	}
	
	@Override
	public boolean gtZero() {
		synchronized ( this ) {
			return milliSec > 0;
		}
	}
	
	@Override
	public boolean geZero() {
		synchronized ( this ) {
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
