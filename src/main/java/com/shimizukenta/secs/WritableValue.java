package com.shimizukenta.secs;

/**
 * Setter, super interface of WritableValue.
 * 
 * @author kenta-shimizu
 *
 * @param <T>
 */
public interface WritableValue<T> {
	
	/**
	 * value setter.
	 * 
	 * @param v
	 */
	public void set(T v);
	
}
