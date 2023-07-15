package com.shimizukenta.secs.local.property;

/**
 * Long value Setter.
 * 
 * @author kenta-shimizu
 * @see Long
 * @see NumberSettable
 *
 */
public interface LongSettable extends NumberSettable<Long> {
	
	/**
	 * Value setter.
	 * 
	 * @param value the long value
	 */
	public void set(long value);
	
}
