package com.shimizukenta.secs.local.property.impl;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import com.shimizukenta.secs.local.property.TimeoutAndUnit;
import com.shimizukenta.secs.local.property.TimeoutProperty;

/**
 * 
 * @author kenta-shimizu
 *
 */
public abstract class AbstractTimeoutProperty extends AbstractProperty<TimeoutAndUnit> implements TimeoutProperty {
	
	private static final long serialVersionUID = -8510457745679120175L;
	
	/**
	 * Constructor.
	 * 
	 * @param initial is TimeoutAndUnit
	 */
	public AbstractTimeoutProperty(TimeoutAndUnit initial) {
		super(initial);
	}
	
	@Override
	public void set(TimeoutAndUnit value) {
		this._syncSetAndNotifyChanged(Objects.requireNonNull(value));
	}
	
	@Override
	public void set(int seconds) {
		this._syncSetAndNotifyChanged(TimeoutAndUnit.of(seconds));
	}
	
	@Override
	public void set(long seconds) {
		this._syncSetAndNotifyChanged(TimeoutAndUnit.of(seconds));
	}
	
	@Override
	public void set(float seconds) {
		this._syncSetAndNotifyChanged(TimeoutAndUnit.of(seconds));
	}
	
	@Override
	public void set(double seconds) {
		this._syncSetAndNotifyChanged(TimeoutAndUnit.of(seconds));
	}
	
	@Override
	public void set(long timeout, TimeUnit unit) {
		this._syncSetAndNotifyChanged(TimeoutAndUnit.of(timeout, unit));
	}

	
	@Override
	public TimeoutAndUnit get() {
		return this._simpleGet();
	}
	
}
