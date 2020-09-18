package com.shimizukenta.secs;

import java.util.Objects;

/**
 * Boolean value Getter, Setter, Value-Change-Observer
 * 
 * @author kenta-shimizu
 *
 */
public class BooleanProperty extends AbstractProperty<Boolean> {
	
	private static final long serialVersionUID = 3179529862036582480L;
	
	public BooleanProperty(boolean initial) {
		super(Boolean.valueOf(initial));
	}
	
	@Override
	public void set(Boolean v) {
		super.set(Objects.requireNonNull(v));
	}
	
	/**
	 * setter
	 * 
	 * @param v
	 */
	public void set(boolean v) {
		super.set(Boolean.valueOf(v));
	}
	
	/**
	 * getter
	 * 
	 * @return get().booleanValue()
	 */
	public boolean booleanValue() {
		return get().booleanValue();
	}
	
	/**
	 * Blocking-method.<br />
	 * Waiting until get().booleanValue() == true.
	 * 
	 * @throws InterruptedException
	 */
	public void waitUntilTrue() throws InterruptedException {
		waitUntil(Boolean.TRUE);
	}
	
	/**
	 * Blocking-method.<br />
	 * Waiting until get().booleanValue() == false.
	 * 
	 * @throws InterruptedException
	 */
	public void waitUntilFalse() throws InterruptedException {
		waitUntil(Boolean.FALSE);
	}
}
