package com.shimizukenta.secs.local.property.impl;

import com.shimizukenta.secs.local.property.IntegerProperty;

/**
 * 
 * @author kenta-shimizu
 *
 */
public abstract class AbstractIntegerProperty extends AbstractNumberProperty<Integer> implements IntegerProperty {
	
	private static final long serialVersionUID = -305818540574009744L;
	
	/**
	 * Constructor.
	 * 
	 * @param initial is int
	 */
	public AbstractIntegerProperty(int initial) {
		super(Integer.valueOf(initial));
	}
	
	@Override
	public void set(int value) {
		this._syncSetAndNotifyChanged(Integer.valueOf(value));
	}
	
	@Override
	public boolean isInteger() {
		return true;
	}
	
}
