package com.shimizukenta.secs;

import java.io.Serializable;

public class SecsTimeout implements Serializable {
	
	private static final long serialVersionUID = -510498497880412809L;
	
	private float t1;
	private float t2;
	private float t3;
	private float t4;
	private float t5;
	private float t6;
	private float t7;
	private float t8;

	protected SecsTimeout() {
		t1 =  1.0F;
		t2 = 15.0F;
		t3 = 45.0F;
		t4 = 45.0F;
		t5 = 10.0F;
		t6 =  5.0F;
		t7 = 10.0F;
		t8 =  6.0F;
	}
	
	/**
	 * 
	 * @param T1-timeout seconds
	 */
	public void t1(float v) {
		synchronized ( this ) {
			if ( v <= 0 ) {
				throw new IllegalArgumentException("T1-timeout is >0");
			}
			t1 = v;
		}
	}
	
	/**
	 * 
	 * @return seconds
	 */
	public float t1() {
		synchronized ( this ) {
			return t1;
		}
	}
	
	/**
	 * 
	 * @param T2-timeout seconds
	 */
	public void t2(float v) {
		synchronized ( this ) {
			if ( v <= 0 ) {
				throw new IllegalArgumentException("T2-timeout is >0");
			}
			t2 = v;
		}
	}
	
	/**
	 * 
	 * @return seconds
	 */
	public float t2() {
		synchronized ( this ) {
			return t2;
		}
	}
	
	/**
	 * 
	 * @param T3-timeout seconds
	 */
	public void t3(float v) {
		synchronized ( this ) {
			if ( v <= 0 ) {
				throw new IllegalArgumentException("T3-timeout is >0");
			}
			t3 = v;
		}
	}
	
	/**
	 * 
	 * @return seconds
	 */
	public float t3() {
		synchronized ( this ) {
			return t3;
		}
	}
	
	/**
	 * 
	 * @param T4-timeout seconds
	 */
	public void t4(float v) {
		synchronized ( this ) {
			if ( v <= 0 ) {
				throw new IllegalArgumentException("T4-timeout is >0");
			}
			t4 = v;
		}
	}
	
	/**
	 * 
	 * @return seconds
	 */
	public float t4() {
		synchronized ( this ) {
			return t4;
		}
	}
	
	/**
	 * 
	 * @param T5-timeout seconds
	 */
	public void t5(float v) {
		synchronized ( this ) {
			if ( v <= 0 ) {
				throw new IllegalArgumentException("T5-timeout is >0");
			}
			t5 = v;
		}
	}
	
	/**
	 * 
	 * @return seconds
	 */
	public float t5() {
		synchronized ( this ) {
			return t5;
		}
	}
	
	/**
	 * 
	 * @param T6-timeout seconds
	 */
	public void t6(float v) {
		synchronized ( this ) {
			if ( v <= 0 ) {
				throw new IllegalArgumentException("T6-timeout is >0");
			}
			t6 = v;
		}
	}
	
	/**
	 * 
	 * @return seconds
	 */
	public float t6() {
		synchronized ( this ) {
			return t6;
		}
	}
	
	/**
	 * 
	 * @param T7-timeout seconds
	 */
	public void t7(float v) {
		synchronized ( this ) {
			if ( v <= 0 ) {
				throw new IllegalArgumentException("T7-timeout is >0");
			}
			t7 = v;
		}
	}
	
	/**
	 * 
	 * @return seconds
	 */
	public float t7() {
		synchronized ( this ) {
			return t7;
		}
	}
	
	/**
	 * 
	 * @param T8-timeout seconds
	 */
	public void t8(float v) {
		synchronized ( this ) {
			if ( v <= 0 ) {
				throw new IllegalArgumentException("T8-timeout is >0");
			}
			t8 = v;
		}
	}
	
	/**
	 * 
	 * @return seconds
	 */
	public float t8() {
		synchronized ( this ) {
			return t8;
		}
	}
	
}
