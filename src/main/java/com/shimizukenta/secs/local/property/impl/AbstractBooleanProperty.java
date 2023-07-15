package com.shimizukenta.secs.local.property.impl;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.shimizukenta.secs.local.property.BooleanProperty;

/**
 * @author kenta-shimizu
 *
 */
public abstract class AbstractBooleanProperty extends AbstractProperty<Boolean> implements BooleanProperty {
	
	private static final long serialVersionUID = 3470538189679094943L;
	
	/**
	 * Constructor.
	 * 
	 * @param initial is boolean
	 */
	public AbstractBooleanProperty(boolean initial) {
		super(Boolean.valueOf(initial));
	}
	
	@Override
	public boolean booleanValue() {
		synchronized ( this._sync ) {
			return this._simpleGet().booleanValue();
		}
	}
	
	@Override
	public void set(boolean value) {
		this._syncSetAndNotifyChanged(Boolean.valueOf(value));
	}
	
	@Override
	public void waitUntil(boolean condition) throws InterruptedException {
		synchronized ( this._sync ) {
			if ( this.booleanValue() != condition ) {
				this._sync.wait();
			}
		}
	}
	
	@Override
	public void waitUntil(boolean condition, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		synchronized ( this._sync ) {
			if ( this.booleanValue() != condition ) {
				unit.timedWait(this._sync, timeout);
				if ( this.booleanValue() != condition) {
					throw new TimeoutException();
				}
			}
		}
	}
	
	@Override
	protected void _notifyChanged(Boolean value) {
		super._notifyChanged(value);
		synchronized ( this._sync ) {
			this._sync.notifyAll();
		}
	}
	
}
