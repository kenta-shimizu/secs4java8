package com.shimizukenta.secs;

/**
 * Number setter
 * 
 * @author kenta-shimizu
 *
 */
public interface WritableNumberValue extends WritableValue<Number> {
	
	/**
	 * setter<br />
	 * Not Accept null.
	 * 
	 */
	@Override
	public void set(Number v);
	
	/**
	 * int setter
	 * 
	 * @param v
	 */
	public void set(int v);
	
	/**
	 * long setter
	 * 
	 * @param v
	 */
	public void set(long v);
	
	/**
	 * float setter
	 * 
	 * @param v
	 */
	public void set(float v);
	
	/**
	 * double setter
	 * 
	 * @param v
	 */
	public void set(double v);
	
}
