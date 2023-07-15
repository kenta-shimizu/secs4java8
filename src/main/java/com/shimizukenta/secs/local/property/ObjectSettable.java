package com.shimizukenta.secs.local.property;

/**
 * Object value Setter.
 * 
 * @author kenta-shimizu
 *
 * @param <T> Type
 * @see Settable
 * 
 */
public interface ObjectSettable<T> extends Settable<T> {
	
	/**
	 * Value setter.
	 * 
	 * @param value the {@code <T>} Object
	 */
	public void set(T value);
	
}
