package com.shimizukenta.secs.local.property;

import java.util.concurrent.TimeUnit;

import com.shimizukenta.secs.local.property.impl.AbstractTimeoutAndUnit;

/**
 * This interface instance is a pair of timeout and TimeUnit.
 * 
 * @author kenta-shimizu
 * @see TimeUnit
 *
 */
public interface TimeoutAndUnit {
	
	/**
	 * Returns timeout.
	 * 
	 * @return timeout.
	 */
	public long timeout();

	/**
	 * Returns TimeUnit.
	 * 
	 * @return TImeUnit.
	 */
	public TimeUnit unit();
	
	/**
	 * Returns Milli-Seconds.
	 * 
	 * @return Milli-Seconds.
	 */
	default public long getMilliSeconds() {
		return this.unit().toMillis(this.timeout());
	}
	
	/**
	 * Instance builder.
	 * 
	 * @param timeout the long value
	 * @param unit the TimeUnit
	 * @return new-instance
	 */
	public static TimeoutAndUnit of(long timeout, TimeUnit unit) {
		return new AbstractTimeoutAndUnit(timeout, unit) {

			private static final long serialVersionUID = -1751240708640861755L;
		};
	}
	
	/**
	 * Instance builder.
	 * 
	 * @param seconds the int value
	 * @return new-instance
	 */
	public static TimeoutAndUnit of(int seconds) {
		return of((long)seconds, TimeUnit.SECONDS);
	}
	
	/**
	 * Instance builder.
	 * 
	 * @param seconds the long value
	 * @return new-instance
	 */
	public static TimeoutAndUnit of(long seconds) {
		return of(seconds, TimeUnit.SECONDS);
	}
	
	/**
	 * Instance builder.
	 * 
	 * @param seconds the float value
	 * @return new-instance
	 */
	public static TimeoutAndUnit of(float seconds) {
		return of((long)(seconds * 1000.0F), TimeUnit.MILLISECONDS);
	}
	
	/**
	 * Instance builder.
	 * 
	 * @param seconds the double value
	 * @return new-instance
	 */
	public static TimeoutAndUnit of(double seconds) {
		return of((long)(seconds * 1000000.0D), TimeUnit.MICROSECONDS);
	}
	
}
