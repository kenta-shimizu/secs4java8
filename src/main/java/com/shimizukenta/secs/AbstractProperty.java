package com.shimizukenta.secs;

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

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
	public String toString() {
		return Objects.toString(get());
	}
	
	
}
