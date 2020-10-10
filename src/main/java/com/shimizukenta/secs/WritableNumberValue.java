package com.shimizukenta.secs;

/**
 * Number setter.
 * 
 * @author kenta-shimizu
 *
 */
public interface WritableNumberValue extends WritableValue<Number> {
	
	/**
	 * setter.
	 * 
	 * <p>
	 * Not Accept {@code null}.<br />
	 * </p>
	 * 
	 */
	@Override
	public void set(Number v);
	
	/**
	 * int setter.
	 * 
	 * @param v
	 */
	public void set(int v);
	
	/**
	 * long setter.
	 * 
	 * @param v
	 */
	public void set(long v);
	
	/**
	 * float setter.
	 * 
	 * @param v
	 */
	public void set(float v);
	
	/**
	 * double setter.
	 * 
	 * @param v
	 */
	public void set(double v);
	
}
