package com.shimizukenta.secs;

/**
 * Boolean getter.
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
	 * Waiting until True.
	 * 
	 * <p>
	 * Blocking-method.<br />
	 * Waiting until {@code get().booleanValue() == true}.
	 * </p>
	 * 
	 * @throws InterruptedException
	 */
	public void waitUntilTrue() throws InterruptedException;
	
	/**
	 * Waiting until False.
	 * 
	 * <p>
	 * Blocking-method.<br />
	 * Waiting until {@code get().booleanValue() == false}.
	 * </p>
	 * 
	 * @throws InterruptedException
	 */
	public void waitUntilFalse() throws InterruptedException;
	
}
