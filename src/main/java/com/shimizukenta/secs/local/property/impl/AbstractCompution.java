package com.shimizukenta.secs.local.property.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

import com.shimizukenta.secs.local.property.ChangeListener;
import com.shimizukenta.secs.local.property.Compution;

/**
 * 
 * @author kenta-shimizu
 *
 * @param <T> Type
 */
public abstract class AbstractCompution<T> implements Compution<T> {
	
	private static final long serialVersionUID = -3295956325648873642L;
	
	/**
	 * Mutable value.
	 */
	private T v;
	
	/**
	 * Protected synchronized object.
	 * 
	 */
	protected final Object _sync = new Object();
	
	/**
	 * Constructor 
	 * 
	 * @param initial is any
	 */
	public AbstractCompution(T initial) {
		this.v = initial;
	}
	
	/**
	 * synchronized set and notify if value changed.
	 * 
	 * @param value is {@code <T>}
	 */
	protected void _syncSetAndNotifyChanged(T value) {
		synchronized ( this._sync ) {
			if ( ! Objects.equals(this.v, value) ) {
				this._simpleSet(value);
				this._notifyChanged(this.v);
			}
		}
	}
	
	/**
	 * Vale simple Setter.
	 * 
	 * @param value is {@code <T>}
	 */
	protected final void _simpleSet(T value) {
		this.v = value;
	}
	
	/**
	 * Returns Value simple getter.
	 * 
	 * @return value
	 */
	protected final T _simpleGet() {
		return this.v;
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
	 * Notify value to listeners.
	 * 
	 * @param value is {@code <T>}
	 */
	protected void _notifyChanged(T value) {
		for ( ChangeListener<? super T> l : this.changeLstnrs ) {
			l.changed(v);
		}
	}
	
	@Override
	public String toString() {
		synchronized ( this._sync ) {
			return Objects.toString(this._simpleGet());
		}
	}
	
}
