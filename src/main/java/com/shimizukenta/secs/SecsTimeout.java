package com.shimizukenta.secs;

import java.io.Serializable;

public class SecsTimeout implements Serializable {
	
	private static final long serialVersionUID = -3240180901934372535L;
	
	private final TimeProperty t1 = new TimeProperty( 1.0F);
	private final TimeProperty t2 = new TimeProperty(15.0F);
	private final TimeProperty t3 = new TimeProperty(45.0F);
	private final TimeProperty t4 = new TimeProperty(45.0F);
	private final TimeProperty t5 = new TimeProperty(10.0F);
	private final TimeProperty t6 = new TimeProperty( 5.0F);
	private final TimeProperty t7 = new TimeProperty(10.0F);
	private final TimeProperty t8 = new TimeProperty( 6.0F);
	
	protected SecsTimeout() {
		/* Nothing */
	}
	
	/**
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
	 * 
	 * @return seconds
	 */
	public TimeProperty t1() {
		return t1;
	}
	
	/**
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
	 * 
	 * @return seconds
	 */
	public TimeProperty t2() {
		return t2;
	}
	
	/**
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
	 * 
	 * @return seconds
	 */
	public TimeProperty t3() {
		return t3;
	}
	
	/**
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
	 * 
	 * @return seconds
	 */
	public TimeProperty t4() {
		return t4;
	}
	
	/**
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
	 * 
	 * @return seconds
	 */
	public TimeProperty t5() {
		return t5;
	}
	
	/**
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
	 * 
	 * @return seconds
	 */
	public TimeProperty t6() {
		return t6;
	}
	
	/**
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
	 * 
	 * @return seconds
	 */
	public TimeProperty t7() {
		return t7;
	}
	
	/**
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
	 * 
	 * @return seconds
	 */
	public TimeProperty t8() {
		return t8;
	}
	
}
