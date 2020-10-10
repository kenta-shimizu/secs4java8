package com.shimizukenta.secs;

/**
 * Boolean Setter.
 * 
 * @author kenta-shimizu
 *
 */
public interface WritableBooleanValue extends WritableValue<Boolean> {
	
	/**
	 * setter.
	 * 
	 * @param v
	 */
	public void set(boolean v);
}
