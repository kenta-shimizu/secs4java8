package com.shimizukenta.secs.local.property.impl;

import com.shimizukenta.secs.local.property.DoubleProperty;

/**
 * 
 * @author kenta-shimizu
 *
 */
public abstract class AbstractDoubleProperty extends AbstractNumberProperty<Double> implements DoubleProperty {
	
	private static final long serialVersionUID = 887820836308020057L;
	
	/**
	 * Constructor.
	 * 
	 * @param initial is double
	 */
	public AbstractDoubleProperty(double initial) {
		super(Double.valueOf(initial));
	}
	
	@Override
	public void set(double value) {
		this._syncSetAndNotifyChanged(Double.valueOf(value));
	}
	
	@Override
	public boolean isDouble() {
		return true;
	}
	
}
