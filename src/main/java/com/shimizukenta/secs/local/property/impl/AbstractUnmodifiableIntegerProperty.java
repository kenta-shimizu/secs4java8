package com.shimizukenta.secs.local.property.impl;

import com.shimizukenta.secs.local.property.ChangeListener;
import com.shimizukenta.secs.local.property.Observable;

/**
 * 
 * @author kenta-shimizu
 *
 */
public abstract class AbstractUnmodifiableIntegerProperty extends AbstractIntegerProperty {
	
	private static final long serialVersionUID = -5048333628118693243L;
	
	/**
	 * Constructor.
	 * 
	 * @param initial is int
	 */
	public AbstractUnmodifiableIntegerProperty(int initial) {
		super(initial);
	}
	
	@Override
	public void set(int value) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public boolean addChangeListener(ChangeListener<? super Integer> l) {
		l.changed(this._simpleGet());
		return true;
	}
	
	@Override
	public boolean removeChangeListener(ChangeListener<? super Integer> l) {
		return true;
	}
	
	@Override
	public boolean bind(Observable<? extends Integer> observer) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public boolean unbind(Observable<? extends Integer> observer) {
		throw new UnsupportedOperationException();
	}

}
