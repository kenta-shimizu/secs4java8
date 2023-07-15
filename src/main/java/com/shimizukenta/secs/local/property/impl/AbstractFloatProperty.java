package com.shimizukenta.secs.local.property.impl;

import com.shimizukenta.secs.local.property.FloatProperty;

/**
 * 
 * @author kenta-shimizu
 *
 */
public abstract class AbstractFloatProperty extends AbstractNumberProperty<Float> implements FloatProperty {

	private static final long serialVersionUID = 7439939403442859342L;
	
	/**
	 * Constructor.
	 * 
	 * @param initial is float
	 */
	public AbstractFloatProperty(float initial) {
		super(Float.valueOf(initial));
	}
	
	@Override
	public void set(float value) {
		this._syncSetAndNotifyChanged(Float.valueOf(value));
	}
	
	@Override
	public boolean isFloat() {
		return true;
	}

}
