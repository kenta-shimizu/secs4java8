package com.shimizukenta.secs;

import java.io.Serializable;

/**
 * Secs Timeout config.<br />
 * T1-T8 setter and getter.
 * 
 * @author kenta-shimizu
 *
 */
public class SecsTimeout implements Serializable {
	
	private static final long serialVersionUID = -3240180901934372535L;
	
	private final AbstractTimeProperty t1 = new SimpleTimeProperty( 1.0F);
	private final AbstractTimeProperty t2 = new SimpleTimeProperty(15.0F);
	private final AbstractTimeProperty t3 = new SimpleTimeProperty(45.0F);
	private final AbstractTimeProperty t4 = new SimpleTimeProperty(45.0F);
	private final AbstractTimeProperty t5 = new SimpleTimeProperty(10.0F);
	private final AbstractTimeProperty t6 = new SimpleTimeProperty( 5.0F);
	private final AbstractTimeProperty t7 = new SimpleTimeProperty(10.0F);
	private final AbstractTimeProperty t8 = new SimpleTimeProperty( 6.0F);
	
	protected SecsTimeout() {
		/* Nothing */
	}
	
	/**
	 * T1-Timeout setter.
	 * 
	 * @param T1-timeout seconds
	 */
	public void t1(float v) {
		if ( v <= 0 ) {
			throw new IllegalArgumentException("T1-timeout is >0");
		}
		t1.set(v);
	}
	
	/**
	 * T1-Timeout getter
	 * 
	 * @return seconds
	 */
	public ReadOnlyTimeProperty t1() {
		return t1;
	}
	
	/**
	 * T2-Timeout setter
	 * 
	 * @param T2-timeout seconds
	 */
	public void t2(float v) {
		if ( v <= 0 ) {
			throw new IllegalArgumentException("T2-timeout is >0");
		}
		t2.set(v);
	}
	
	/**
	 * T2-Timeout getter
	 * 
	 * @return seconds
	 */
	public ReadOnlyTimeProperty t2() {
		return t2;
	}
	
	/**
	 * T3-Timeout setter
	 * 
	 * @param T3-timeout seconds
	 */
	public void t3(float v) {
		if ( v <= 0 ) {
			throw new IllegalArgumentException("T3-timeout is >0");
		}
		t3.set(v);
	}
	
	/**
	 * T3-Timeout getter
	 * 
	 * @return seconds
	 */
	public ReadOnlyTimeProperty t3() {
		return t3;
	}
	
	/**
	 * T4-Timeout setter
	 * 
	 * @param T4-timeout seconds
	 */
	public void t4(float v) {
		if ( v <= 0 ) {
			throw new IllegalArgumentException("T4-timeout is >0");
		}
		t4.set(v);
	}
	
	/**
	 * T4-Timeout getter
	 * 
	 * @return seconds
	 */
	public ReadOnlyTimeProperty t4() {
		return t4;
	}
	
	/**
	 * T5-Timeout setter
	 * 
	 * @param T5-timeout seconds
	 */
	public void t5(float v) {
		if ( v <= 0 ) {
			throw new IllegalArgumentException("T5-timeout is >0");
		}
		t5.set(v);
	}
	
	/**
	 * T5-Timeout getter
	 * 
	 * @return seconds
	 */
	public ReadOnlyTimeProperty t5() {
		return t5;
	}
	
	/**
	 * T6-Timeout setter
	 * 
	 * @param T6-timeout seconds
	 */
	public void t6(float v) {
		if ( v <= 0 ) {
			throw new IllegalArgumentException("T6-timeout is >0");
		}
		t6.set(v);
	}
	
	/**
	 * T6-Timeout getter
	 * 
	 * @return seconds
	 */
	public ReadOnlyTimeProperty t6() {
		return t6;
	}
	
	/**
	 * T7-Timeout setter
	 * 
	 * @param T7-timeout seconds
	 */
	public void t7(float v) {
		if ( v <= 0 ) {
			throw new IllegalArgumentException("T7-timeout is >0");
		}
		t7.set(v);
	}
	
	/**
	 * T7-Timeout getter
	 * 
	 * @return seconds
	 */
	public ReadOnlyTimeProperty t7() {
		return t7;
	}
	
	/**
	 * T8-Timeout setter
	 * 
	 * @param T8-timeout seconds
	 */
	public void t8(float v) {
		if ( v <= 0 ) {
			throw new IllegalArgumentException("T8-timeout is >0");
		}
		t8.set(v);
	}
	
	/**
	 * T8-Timeout getter
	 * 
	 * @return seconds
	 */
	public ReadOnlyTimeProperty t8() {
		return t8;
	}
	
}
