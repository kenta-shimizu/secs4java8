package com.shimizukenta.secs;

/**
 * Setter
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
