package com.shimizukenta.secs;

/**
 * String setter.
 * 
 * @author kenta-shimizu
 *
 */
public interface WritableStringValue extends WritableValue<String> {
	
	/**
	 * setter.
	 * 
	 * @param cs
	 */
	public void set(CharSequence cs);
	
}
