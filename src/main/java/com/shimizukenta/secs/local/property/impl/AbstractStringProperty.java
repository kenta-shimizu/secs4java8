package com.shimizukenta.secs.local.property.impl;

import com.shimizukenta.secs.local.property.StringProperty;

/**
 * 
 * @author kenta-shimizu
 *
 */
public abstract class AbstractStringProperty extends AbstractProperty<String> implements StringProperty {
	
	private static final long serialVersionUID = -8258206504955302710L;
	
	/**
	 * Constructor
	 * 
	 * <p>
	 * if value is null, set empty string("").<br />
	 * </p>
	 * 
	 * @param initial initial-value
	 */
	public AbstractStringProperty(CharSequence initial) {
		super(initial == null ? "" : initial.toString());
	}
	
	@Override
	public void set(CharSequence value) {
		this._syncSetAndNotifyChanged(value == null ? "" : value.toString());
	}
	
}
