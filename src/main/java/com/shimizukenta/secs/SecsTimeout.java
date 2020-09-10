package com.shimizukenta.secs;

import java.io.Serializable;

public class SecsTimeout implements Serializable {
	
	private static final long serialVersionUID = -3240180901934372535L;
	
	private class FloatProperty extends AbstractProperty<Float> {

		public FloatProperty(float initial) {
			super(Float.valueOf(initial));
		}
	}
	
	private final Property<Float> t1 = new FloatProperty( 1.0F);
	private final Property<Float> t2 = new FloatProperty(15.0F);
	private final Property<Float> t3 = new FloatProperty(45.0F);
	private final Property<Float> t4 = new FloatProperty(45.0F);
	private final Property<Float> t5 = new FloatProperty(10.0F);
	private final Property<Float> t6 = new FloatProperty( 5.0F);
	private final Property<Float> t7 = new FloatProperty(10.0F);
	private final Property<Float> t8 = new FloatProperty( 6.0F);
	
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
	public Property<Float> t1() {
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
	public Property<Float> t2() {
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
	public Property<Float> t3() {
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
	public Property<Float> t4() {
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
	public Property<Float> t5() {
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
	public Property<Float> t6() {
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
	public Property<Float> t7() {
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
	public Property<Float> t8() {
		return t8;
	}
	
}
