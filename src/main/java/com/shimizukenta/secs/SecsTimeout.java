package com.shimizukenta.secs;

import java.io.Serializable;

/**
 * Secs Timeout config, T1-T8 setter and getter.
 * 
 * @author kenta-shimizu
 *
 */
public class SecsTimeout implements Serializable {
	
	private static final long serialVersionUID = -3240180901934372535L;
	
	private final TimeProperty t1 = TimeProperty.newInstance( 1.0F);
	private final TimeProperty t2 = TimeProperty.newInstance(15.0F);
	private final TimeProperty t3 = TimeProperty.newInstance(45.0F);
	private final TimeProperty t4 = TimeProperty.newInstance(45.0F);
	private final TimeProperty t5 = TimeProperty.newInstance(10.0F);
	private final TimeProperty t6 = TimeProperty.newInstance( 5.0F);
	private final TimeProperty t7 = TimeProperty.newInstance(10.0F);
	private final TimeProperty t8 = TimeProperty.newInstance( 6.0F);
	
	protected SecsTimeout() {
		/* Nothing */
	}
	
	/**
	 * T1-Timeout setter.
	 * 
	 * @param v T1-timeout seconds, set value of >0
	 */
	public void t1(float v) {
		if ( v <= 0 ) {
			throw new T1TimeoutIllegalArgumentException(v);
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
	 * @param v T2-timeout seconds, set value of >0
	 */
	public void t2(float v) {
		if ( v <= 0 ) {
			throw new T2TimeoutIllegalArgumentException(v);
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
	 * @param v T3-timeout seconds, set value of >0
	 */
	public void t3(float v) {
		if ( v <= 0 ) {
			throw new T3TimeoutIllegalArgumentException(v);
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
	 * @param v T4-timeout seconds, set value of >0
	 */
	public void t4(float v) {
		if ( v <= 0 ) {
			throw new T4TimeoutIllegalArgumentException(v);
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
	 * @param v T5-timeout seconds, set value of >0
	 */
	public void t5(float v) {
		if ( v <= 0 ) {
			throw new T5TimeoutIllegalArgumentException(v);
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
	 * @param v T6-timeout seconds, set value of >0
	 */
	public void t6(float v) {
		if ( v <= 0 ) {
			throw new T6TimeoutIllegalArgumentException(v);
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
	 * @param v T7-timeout seconds, set value of >0
	 */
	public void t7(float v) {
		if ( v <= 0 ) {
			throw new T7TimeoutIllegalArgumentException(v);
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
	 * @param v T8-timeout seconds, set value of >0
	 */
	public void t8(float v) {
		if ( v <= 0 ) {
			throw new T8TimeoutIllegalArgumentException(v);
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
	
	private static class T1TimeoutIllegalArgumentException extends IllegalArgumentException {
		
		private static final long serialVersionUID = -2581208452653867273L;
		
		public T1TimeoutIllegalArgumentException(float value) {
			super("T1-timeout is >0, value=" + value);
		}
	}
	
	private static class T2TimeoutIllegalArgumentException extends IllegalArgumentException {
		
		private static final long serialVersionUID = 3486222709254763543L;
		
		public T2TimeoutIllegalArgumentException(float value) {
			super("T2-timeout is >0, value=" + value);
		}
	}
	
	private static class T3TimeoutIllegalArgumentException extends IllegalArgumentException {
		
		private static final long serialVersionUID = 3839307398543951387L;
		
		public T3TimeoutIllegalArgumentException(float value) {
			super("T3-timeout is >0, value=" + value);
		}
	}

	private static class T4TimeoutIllegalArgumentException extends IllegalArgumentException {
		
		private static final long serialVersionUID = -203078702388901633L;
		
		public T4TimeoutIllegalArgumentException(float value) {
			super("T4-timeout is >0, value=" + value);
		}
	}
	
	private static class T5TimeoutIllegalArgumentException extends IllegalArgumentException {
		
		private static final long serialVersionUID = -2021716924907606047L;
		
		public T5TimeoutIllegalArgumentException(float value) {
			super("T5-timeout is >0, value=" + value);
		}
	}
	
	private static class T6TimeoutIllegalArgumentException extends IllegalArgumentException {
		
		private static final long serialVersionUID = 7122891591407469696L;
		
		public T6TimeoutIllegalArgumentException(float value) {
			super("T6-timeout is >0, value=" + value);
		}
	}
	
	private static class T7TimeoutIllegalArgumentException extends IllegalArgumentException {
		
		private static final long serialVersionUID = -350165616843221603L;
		
		public T7TimeoutIllegalArgumentException(float value) {
			super("T7-timeout is >0, value=" + value);
		}
	}
	
	private static class T8TimeoutIllegalArgumentException extends IllegalArgumentException {
		
		private static final long serialVersionUID = 633542395990124660L;
		
		public T8TimeoutIllegalArgumentException(float value) {
			super("T8-timeout is >0, value=" + value);
		}
	}
	
}
