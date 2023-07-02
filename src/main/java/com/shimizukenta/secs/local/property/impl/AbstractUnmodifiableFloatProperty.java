package com.shimizukenta.secs.local.property.impl;

import com.shimizukenta.secs.local.property.ChangeListener;
import com.shimizukenta.secs.local.property.Observable;

/**
 * 
 * @author kenta-shimizu
 *
 */
public abstract class AbstractUnmodifiableFloatProperty extends AbstractFloatProperty {
	
	private static final long serialVersionUID = -3898415749998088493L;
	
	/**
	 * Constructor.
	 * 
	 * @param initial is float
	 */
	public AbstractUnmodifiableFloatProperty(float initial) {
		super(initial);
	}
	
	@Override
	public void set(float value) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public boolean addChangeListener(ChangeListener<? super Float> l) {
		l.changed(this._simpleGet());
		return true;
	}
	
	@Override
	public boolean removeChangeListener(ChangeListener<? super Float> l) {
		return true;
	}
	
	@Override
	public boolean bind(Observable<? extends Float> observer) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public boolean unbind(Observable<? extends Float> observer) {
		throw new UnsupportedOperationException();
	}

}
