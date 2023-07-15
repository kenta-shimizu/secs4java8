package com.shimizukenta.secs.local.property.impl;

import com.shimizukenta.secs.local.property.NumberProperty;

/**
 * 
 * @author kenta-shimizu
 *
 * @param <T> Type
 */
public abstract class AbstractNumberProperty<T extends Number> extends AbstractProperty<T> implements NumberProperty<T> {
	
	private static final long serialVersionUID = -5768297706611678591L;
	
	/**
	 * Constructor.
	 * 
	 * @param initial is extends Number
	 */
	public AbstractNumberProperty(T initial) {
		super(initial);
	}
	
	@Override
	public byte byteValue() {
		synchronized ( this._sync ) {
			return this._simpleGet().byteValue();
		}
	}
	
	@Override
	public short shortValue() {
		synchronized ( this._sync ) {
			return this._simpleGet().shortValue();
		}
	}
	
	@Override
	public int intValue() {
		synchronized ( this._sync ) {
			return this._simpleGet().intValue();
		}
	}
	
	@Override
	public long longValue() {
		synchronized ( this._sync ) {
			return this._simpleGet().longValue();
		}
	}
	
	@Override
	public float floatValue() {
		synchronized ( this._sync ) {
			return this._simpleGet().floatValue();
		}
	}
	
	@Override
	public double doubleValue() {
		synchronized ( this._sync ) {
			return this._simpleGet().doubleValue();
		}
	}
	
}
