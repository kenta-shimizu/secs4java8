package com.shimizukenta.secs;

/**
 * Number getter.
 * 
 * @author kenta-shimizu
 *
 */
public interface ReadOnlyNumberProperty extends ReadOnlyProperty<Number> {
	
	/**
	 * int getter
	 * 
	 * @return int-value from get().intValue()
	 */
	public int intValue();
	
	/**
	 * long getter
	 * 
	 * @return int-value from get().longValue()
	 */
	public long longValue();
	
	/**
	 * float getter
	 * 
	 * @return int-value from get().floatValue()
	 */
	public float floatValue();
	
	/**
	 * double getter
	 * 
	 * @return int-value from get().doubleValue()
	 */
	public double doubleValue();
	
}
