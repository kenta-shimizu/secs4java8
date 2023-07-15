package com.shimizukenta.secs.local.property.impl;

import com.shimizukenta.secs.local.property.ChangeListener;
import com.shimizukenta.secs.local.property.Observable;

/**
 * 
 * @author kenta-shimizu
 *
 * @param <T> Type
 */
public abstract class AbstractUnmodifiableObjectProperty<T> extends AbstractObjectProperty<T> {
	
	private static final long serialVersionUID = 1122655569059560817L;
	
	/**
	 * Constructor.
	 * 
	 * @param initial is {@code <T>}
	 */
	public AbstractUnmodifiableObjectProperty(T initial) {
		super(initial);
	}
	
	@Override
	public void set(T v) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public boolean addChangeListener(ChangeListener<? super T> l) {
		l.changed(this._simpleGet());
		return true;
	}
	
	@Override
	public boolean removeChangeListener(ChangeListener<? super T> l) {
		return true;
	}
	
	@Override
	public boolean bind(Observable<? extends T> observer) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public boolean unbind(Observable<? extends T> observer) {
		throw new UnsupportedOperationException();
	}
	
}
