package com.shimizukenta.secs;

/**
 * Boolean getter
 * 
 * @author kenta-shimizu
 *
 */
public interface ReadOnlyBooleanProperty extends ReadOnlyProperty<Boolean> {
	
	/**
	 * getter
	 * 
	 * @return get().booleanValue()
	 */
	public boolean booleanValue();
	
	/**
	 * Blocking-method.<br />
	 * Waiting until get().booleanValue() == true.
	 * 
	 * @throws InterruptedException
	 */
	public void waitUntilTrue() throws InterruptedException;
	
	/**
	 * Blocking-method.<br />
	 * Waiting until get().booleanValue() == false.
	 * 
	 * @throws InterruptedException
	 */
	public void waitUntilFalse() throws InterruptedException;
	
}
