package com.shimizukenta.secs;

import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class AbstractProperty<T> implements Property<T> {
	
	private T present;
	
	public AbstractProperty(T initial) {
		this.present = initial;
	}

	@Override
	public void set(T v) {
		synchronized ( this ) {
			if ( ! Objects.equals(v, this.present) ) {
				this.present = v;
				listeners.forEach(l -> {
					l.changed(v);
				});
			}
		}
	}

	@Override
	public T get() {
		synchronized ( this ) {
			return this.present;
		}
	}
	
	private final Collection<PropertyChangeListener<? super T>> listeners = new CopyOnWriteArrayList<>();
	
	@Override
	public boolean addChangeListener(PropertyChangeListener<? super T> l) {
		synchronized ( this ) {
			boolean f = listeners.add(l);
			if ( f ) {
				l.changed(get());
			}
			return f;
		}
	}

	@Override
	public boolean removeChangeListener(PropertyChangeListener<? super T> l) {
		synchronized ( this ) {
			return listeners.remove(l);
		}
	}

}
