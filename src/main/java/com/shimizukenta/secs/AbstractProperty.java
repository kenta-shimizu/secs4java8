package com.shimizukenta.secs;

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Value Getter, Setter, Value-Change-Observer<br />
 * 
 * @author kenta-shimizu
 *
 * @param <T>
 */
public abstract class AbstractProperty<T> implements Property<T>, Serializable {
	
	private static final long serialVersionUID = -7897962203686565003L;
	
	private final Object sync = new Object();
	
	private T present;
	
	public AbstractProperty(T initial) {
		this.present = initial;
	}
	
	@Override
	public T get() {
		synchronized ( sync ) {
			return this.present;
		}
	}
	
	@Override
	public void set(T v) {
		synchronized ( sync ) {
			if ( ! Objects.equals(v, this.present) ) {
				this.present = v;
				notifyChanged();
			}
		}
	}
	
	private final Collection<PropertyChangeListener<? super T>> listeners = new CopyOnWriteArrayList<>();
	
	@Override
	public boolean addChangeListener(PropertyChangeListener<? super T> l) {
		synchronized ( sync ) {
			boolean f = listeners.add(l);
			if ( f ) {
				l.changed(get());
			}
			return f;
		}
	}
	
	@Override
	public boolean removeChangeListener(PropertyChangeListener<? super T> l) {
		synchronized ( sync ) {
			return listeners.remove(l);
		}
	}
	
	protected void notifyChanged() {
		synchronized ( sync ) {
			listeners.forEach(l -> {
				l.changed(get());
			});
			sync.notifyAll();
		}
	}
	
	@Override
	public void waitUntil(T v) throws InterruptedException {
		synchronized ( sync ) {
			for ( ;; ) {
				if ( Objects.equals(get(), v) ) {
					return;
				}
				sync.wait();
			}
		}
	}
	
	@Override
	public void waitUntil(T v, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		this.waitWithTimeout(() -> {
			this.waitUntil(v);
		}, timeout, unit);
	}
	
	@Override
	public void waitUntil(T v, ReadOnlyTimeProperty tp) throws InterruptedException, TimeoutException {
		this.waitUntil(v, tp.getMilliSeconds(), TimeUnit.MILLISECONDS);
	}
	
	@Override
	public void waitUntilNot(T v) throws InterruptedException {
		synchronized ( sync ) {
			for ( ;; ) {
				if ( ! Objects.equals(get(), v)) {
					return;
				}
				sync.wait();
			}
		}
	}
	
	@Override
	public void waitUntilNot(T v, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		this.waitWithTimeout(() -> {
			this.waitUntilNot(v);
		}, timeout, unit);
	}
	
	@Override
	public void waitUntilNot(T v, ReadOnlyTimeProperty tp) throws InterruptedException, TimeoutException {
		this.waitUntilNot(v, tp.getMilliSeconds(), TimeUnit.MILLISECONDS);
	}
	
	private void waitWithTimeout(InterruptableRunnable r, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		
		final Future<T> f = new FutureTask<>(() -> {
			try {
				r.run();
			}
			catch ( InterruptedException ignore ) {
			}
		}, null);
		
		try {
			f.get(timeout, unit);
		}
		catch ( InterruptedException e ) {
			f.cancel(true);
			throw e;
		}
		catch ( TimeoutException e ) {
			f.cancel(true);
			throw e;
		}
		catch ( ExecutionException e ) {
			Throwable t = e.getCause();
			if ( t instanceof RuntimeException ) {
				throw (RuntimeException)t;
			} else {
				throw new RuntimeException(t);
			}
		}
	}
	
	@Override
	public String toString() {
		return Objects.toString(get());
	}
	
}
