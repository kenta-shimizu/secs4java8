package com.shimizukenta.secs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

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
	 * Waiting until True.
	 * 
	 * <p>
	 * Blocking-method.<br />
	 * Waiting until {@code get().booleanValue() == true}.<br />
	 * </p>
	 * 
	 * @param timeout
	 * @param unit
	 * @throws InterruptedException
	 * @throws TimeoutException
	 */
	public void waitUntilTrue(long timeout, TimeUnit unit) throws InterruptedException, TimeoutException;
	
	/**
	 * Waiting until True.
	 * 
	 * <p>
	 * Blocking-method.<br />
	 * Waiting until {@code get().booleanValue() == true}.<br />
	 * </p>
	 * 
	 * @param tp
	 * @throws InterruptedException
	 * @throws TimeoutException
	 */
	public void waitUntilTrue(ReadOnlyTimeProperty tp) throws InterruptedException, TimeoutException;
	
	/**
	 * Waiting until False.
	 * 
	 * <p>
	 * Blocking-method.<br />
	 * Waiting until {@code get().booleanValue() == false}.<br />
	 * </p>
	 * 
	 * @throws InterruptedException
	 */
	public void waitUntilFalse() throws InterruptedException;
	
	/**
	 * Waiting until False.
	 * 
	 * <p>
	 * Blocking-method.<br />
	 * Waiting until {@code get().booleanValue() == false}.<br />
	 * </p>
	 * 
	 * @param timeout
	 * @param unit
	 * @throws InterruptedException
	 * @throws TimeoutException
	 */
	public void waitUntilFalse(long timeout, TimeUnit unit) throws InterruptedException, TimeoutException;
	
	/**
	 * Waiting until False.
	 * 
	 * <p>
	 * Blocking-method.<br />
	 * Waiting until {@code get().booleanValue() == false}.<br />
	 * </p>
	 * 
	 * @param tp
	 * @throws InterruptedException
	 * @throws TimeoutException
	 */
	public void waitUntilFalse(ReadOnlyTimeProperty tp) throws InterruptedException, TimeoutException;
	
}
