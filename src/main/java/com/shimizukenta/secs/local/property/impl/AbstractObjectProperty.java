package com.shimizukenta.secs.local.property.impl;

import com.shimizukenta.secs.local.property.ObjectProperty;

/**
 * 
 * @author kenta-shimizu
 *
 * @param <T> Type
 */
public abstract class AbstractObjectProperty<T> extends AbstractProperty<T> implements ObjectProperty<T> {
	
	private static final long serialVersionUID = -8210275327751090005L;
	
	/**
	 * Constructor.
	 * 
	 * @param initial is extends Object
	 */
	public AbstractObjectProperty(T initial) {
		super(initial);
	}
	
	@Override
	public void set(T value) {
		this._syncSetAndNotifyChanged(value);
	}
	
	@Override
	public T get() {
		synchronized ( this._sync ) {
			return this._simpleGet();
		}
	}
	
}
