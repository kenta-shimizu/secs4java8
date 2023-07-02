package com.shimizukenta.secs;

import com.shimizukenta.secs.local.property.TimeoutProperty;

/**
 * Secs Timeout config, T1-T8 Setter, Getter and Observer.
 * 
 * @author kenta-shimizu
 */
public interface SecsTimeout {

	/**
	 * T1-Timeout setter.
	 * 
	 * @param seconds T1-timeout seconds, set value is >0
	 */
	public void t1(float seconds);

	/**
	 * Returns T1-TimeoutProperty.
	 * 
	 * @return T1-Timeout-Peoperty
	 */
	public TimeoutProperty t1();

	/**
	 * T2-Timeout setter.
	 * 
	 * @param seconds T2-timeout seconds, set value is >0
	 */
	public void t2(float seconds);

	/**
	 * Returns T2-Timeout-Property.
	 * 
	 * @return T2-Timeout-Property
	 */
	public TimeoutProperty t2();

	/**
	 * T3-Timeout setter.
	 * 
	 * @param seconds T3-timeout seconds, set value is >0
	 */
	public void t3(float seconds);

	/**
	 * Returns T3-Timeout-Property.
	 * 
	 * @return T3-Timeout-Property
	 */
	public TimeoutProperty t3();

	/**
	 * T4-Timeout setter.
	 * 
	 * @param seconds T4-timeout seconds, set value is >0
	 */
	public void t4(float seconds);

	/**
	 * Returns T4-Timeout-Property.
	 * 
	 * @return T4-Timeout-Property.
	 */
	public TimeoutProperty t4();

	/**
	 * T5-Timeout setter.
	 * 
	 * @param seconds T5-timeout seconds, set value is >0
	 */
	public void t5(float seconds);

	/**
	 * Returns T5-Timeout-Property.
	 * 
	 * @return T5-Timeout-Property
	 */
	public TimeoutProperty t5();

	/**
	 * T6-Timeout setter.
	 * 
	 * @param seconds T6-timeout seconds, set value is >0
	 */
	public void t6(float seconds);

	/**
	 * Returns T6-Timeout-Property.
	 * 
	 * @return T6-Timeout-Property
	 */
	public TimeoutProperty t6();

	/**
	 * T7-Timeout setter.
	 * 
	 * @param seconds T7-timeout seconds, set value is >0
	 */
	public void t7(float seconds);

	/**
	 * Returns T7-Timeout-Property.
	 * 
	 * @return T7-Timeout-Property
	 */
	public TimeoutProperty t7();

	/**
	 * T8-Timeout setter.
	 * 
	 * @param seconds T8-timeout seconds, set value is >0
	 */
	public void t8(float seconds);

	/**
	 * Returns T8-Timeout-Property.
	 * 
	 * @return T8-Timeout-Property
	 */
	public TimeoutProperty t8();

}