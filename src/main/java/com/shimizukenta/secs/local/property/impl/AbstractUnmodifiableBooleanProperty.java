package com.shimizukenta.secs.local.property.impl;

import com.shimizukenta.secs.local.property.ChangeListener;
import com.shimizukenta.secs.local.property.Observable;

/**
 * 
 * @author kenta-shimizu
 *
 */
public abstract class AbstractUnmodifiableBooleanProperty extends AbstractBooleanProperty {
	
	private static final long serialVersionUID = 8195499034621846647L;
	
	/**
	 * Constructor.
	 * 
	 * @param initial is boolean
	 */
	public AbstractUnmodifiableBooleanProperty(boolean initial) {
		super(initial);
	}
	
	@Override
	public void set(boolean f) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public boolean addChangeListener(ChangeListener<? super Boolean> l) {
		l.changed(this._simpleGet());
		return true;
	}
	
	@Override
	public boolean removeChangeListener(ChangeListener<? super Boolean> l) {
		return true;
	}
	
	@Override
	public boolean bind(Observable<? extends Boolean> observer) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public boolean unbind(Observable<? extends Boolean> observer) {
		throw new UnsupportedOperationException();
	}

}
