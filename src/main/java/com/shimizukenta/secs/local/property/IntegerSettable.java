package com.shimizukenta.secs.local.property;

/**
 * Integer value Setter.
 * 
 * @author kenta-shimizu
 * @see Integer
 * @see NumberSettable
 *
 */
public interface IntegerSettable extends NumberSettable<Integer> {
	
	/**
	 * Value setter.
	 * 
	 * @param value the int value
	 */
	public void set(int value);
	
}
