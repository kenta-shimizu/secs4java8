package com.shimizukenta.secs.impl;

import java.io.Serializable;

import com.shimizukenta.secs.SecsTimeout;
import com.shimizukenta.secs.local.property.TimeoutProperty;

/**
 * Secs Timeout config, T1-T8 setter and getter.
 * 
 * @author kenta-shimizu
 *
 */
public class SecsTimeoutImpl implements Serializable, SecsTimeout {
	
	private static final long serialVersionUID = -2801047913739285106L;
	
	private final TimeoutProperty t1 = TimeoutProperty.newInstance(1.0F);
	private final TimeoutProperty t2 = TimeoutProperty.newInstance(15.0F);
	private final TimeoutProperty t3 = TimeoutProperty.newInstance(45.0F);
	private final TimeoutProperty t4 = TimeoutProperty.newInstance(45.0F);
	private final TimeoutProperty t5 = TimeoutProperty.newInstance(10.0F);
	private final TimeoutProperty t6 = TimeoutProperty.newInstance( 5.0F);
	private final TimeoutProperty t7 = TimeoutProperty.newInstance(10.0F);
	private final TimeoutProperty t8 = TimeoutProperty.newInstance( 6.0F);
	
	public SecsTimeoutImpl() {
		/* Nothing */
	}
	
	@Override
	public void t1(float seconds) {
		t1.set(seconds);
	}
	
	@Override
	public TimeoutProperty t1() {
		return t1;
	}
	
	@Override
	public void t2(float seconds) {
		t2.set(seconds);
	}
	
	@Override
	public TimeoutProperty t2() {
		return t2;
	}
	
	@Override
	public void t3(float seconds) {
		t3.set(seconds);
	}
	
	@Override
	public TimeoutProperty t3() {
		return t3;
	}
	
	@Override
	public void t4(float seconds) {
		t4.set(seconds);
	}
	
	@Override
	public TimeoutProperty t4() {
		return t4;
	}
	
	@Override
	public void t5(float seconds) {
		t5.set(seconds);
	}
	
	@Override
	public TimeoutProperty t5() {
		return t5;
	}
	
	@Override
	public void t6(float seconds) {
		t6.set(seconds);
	}
	
	@Override
	public TimeoutProperty t6() {
		return t6;
	}
	
	@Override
	public void t7(float seconds) {
		t7.set(seconds);
	}
	
	@Override
	public TimeoutProperty t7() {
		return t7;
	}
	
	@Override
	public void t8(float seconds) {
		t8.set(seconds);
	}
	
	@Override
	public TimeoutProperty t8() {
		return t8;
	}
	
}
