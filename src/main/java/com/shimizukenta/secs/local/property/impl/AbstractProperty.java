package com.shimizukenta.secs.local.property.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

import com.shimizukenta.secs.local.property.ChangeListener;
import com.shimizukenta.secs.local.property.Observable;
import com.shimizukenta.secs.local.property.Property;

/**
 * 
 * @author kenta-shimizu
 *
 * @param <T> Type
 */
public abstract class AbstractProperty<T> implements Property<T> {
	
	private static final long serialVersionUID = 673883739488369977L;
	
	/**
	 * Mutable value.
	 */
	private T v;
	
	/**
	 * Constructor.
	 * 
	 * @param initial is {@code <T>}
	 */
	public AbstractProperty(T initial) {
		this.v = initial;
	}
	
	/**
	 * Protected synchronized object.
	 * 
	 */
	protected final Object _sync = new Object();
	
	/**
	 * synchronized set and notify if value changed.
	 * 
	 * @param value is {@code <T>}
	 */
	protected void _syncSetAndNotifyChanged(T value) {
		synchronized ( this._sync ) {
			if ( ! Objects.equals(value, this._simpleGet()) ) {
				this._simpleSet(value);
				this._notifyChanged(this.v);
			}
		}
	}
	
	/**
	 * Value simple setter.
	 * 
	 * @param value is {@code <T>}
	 */
	protected final void _simpleSet(T value) {
		this.v = value;
	}
	
	/**
	 * Value simple getter.
	 * 
	 * @return value
	 */
	protected final T _simpleGet() {
		return v;
	}
	
	/**
	 * Change listeners.
	 */
	private final Collection<ChangeListener<? super T>> changeLstnrs = new HashSet<>();
	
	@Override
	public boolean addChangeListener(ChangeListener<? super T> l) {
		synchronized ( this._sync ) {
			boolean f = this.changeLstnrs.add(l);
			if ( f ) {
				l.changed(this._simpleGet());
			}
			return f;
		}
	}
	
	@Override
	public boolean removeChangeListener(ChangeListener<? super T> l) {
		synchronized ( this._sync ) {
			return this.changeLstnrs.remove(l);
		}
	}
	
	/**
	 * Bind listener.
	 */
	private final ChangeListener<T> bindLstnr = this::_syncSetAndNotifyChanged;
	
	@Override
	public boolean bind(Observable<? extends T> observer) {
		return observer.addChangeListener(bindLstnr);
	}
	
	@Override
	public boolean unbind(Observable<? extends T> observer) {
		return observer.removeChangeListener(bindLstnr);
	}
	
	/**
	 * Notify to listeners.
	 * 
	 * @param v is {@code <T>}
	 */
	protected void _notifyChanged(T v) {
		synchronized ( this._sync ) {
			for ( ChangeListener<? super T> l : this.changeLstnrs ) {
				l.changed(v);
			}
		}
	}
	
	@Override
	public String toString() {
		synchronized ( this._sync ) {
			return Objects.toString(this._simpleGet());
		}
	}
	
}
