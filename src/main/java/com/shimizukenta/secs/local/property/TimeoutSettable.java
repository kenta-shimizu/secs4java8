package com.shimizukenta.secs.local.property;

import java.util.concurrent.TimeUnit;

/**
 * TimeoutAndUnit value Setter.
 * 
 * @author kenta-shimizu
 * @see TimeUnit
 * @see TimeoutAndUnit
 * @see Settable
 *
 */
public interface TimeoutSettable extends Settable<TimeoutAndUnit> {
	
	/**
	 * Value setter.
	 * 
	 * @param value the TimeoutAndUnit
	 */
	public void set(TimeoutAndUnit value);
	
	/**
	 * Value setter.
	 * 
	 * @param seconds the int value
	 */
	public void set(int seconds);
	
	/**
	 * Value setter.
	 * 
	 * @param seconds the long value
	 */
	public void set(long seconds);
	
	/**
	 * Value setter.
	 * 
	 * @param seconds the float value
	 */
	public void set(float seconds);
	
	/**
	 * Value setter.
	 * 
	 * @param seconds the double value
	 */
	public void set(double seconds);
	
	/**
	 * Value setter.
	 * 
	 * @param timeout the long value
	 * @param unit the TimeUnit
	 */
	public void set(long timeout, TimeUnit unit);
	
}
