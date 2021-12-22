package com.shimizukenta.secs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Getter, Value-Change-Observer.
 * 
 * @author kenta-shimizu
 *
 * @param <T>
 */
public interface ReadOnlyProperty<T> {
	
	/**
	 * value getter.
	 * 
	 * @return value
	 */
	public T get();
	
	/**
	 * Add Value-Change-Listener
	 * 
	 * @param listener
	 * @return true if success
	 */
	public boolean addChangeListener(PropertyChangeListener<? super T> listener);
	
	/**
	 * Remove Value-Change-Listener.
	 * 
	 * @param listener
	 * @return true if success
	 */
	public boolean removeChangeListener(PropertyChangeListener<? super T> listener);
	
	/**
	 * Wait until {@code (Objects.equals(changedValue, v) == true)}.
	 * 
	 * <p>
	 * Blocking-method.<br />
	 * Blocking until {@code (Objects.equals(changedValue, v) == true)}
	 * </p>
	 * 
	 * @param v
	 * @throws InterruptedException
	 */
	public void waitUntil(T v) throws InterruptedException;
	
	/**
	 * Wait until {@code (Objects.equals(changedValue, v) == true)}.
	 * 
	 * <p>
	 * Blocking-method.<br />
	 * Blocking until {@code (Objects.equals(changedValue, v) == true)}
	 * </p>
	 * 
	 * @param v
	 * @param timeout
	 * @param unit
	 * @throws InterruptedException
	 * @throws TimeoutException
	 */
	public void waitUntil(T v, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException;
	
	/**
	 * Wait until {@code (Objects.equals(changedValue, v) == true)}.
	 * 
	 * <p>
	 * Blocking-method.<br />
	 * Blocking until {@code (Objects.equals(changedValue, v) == true)}
	 * </p>
	 * 
	 * @param v
	 * @param tp
	 * @throws InterruptedException
	 * @throws TimeoutException
	 */
	public void waitUntil(T v, ReadOnlyTimeProperty tp) throws InterruptedException, TimeoutException;
	
	/**
	 * Wait until {@code (Objects.equals(changedValue, v) == false)}.
	 * 
	 * <p>
	 * Blocking-method.<br />
	 * Blocking until {@code (Objects.equals(changedValue, v) == false)}
	 * </p>
	 * 
	 * @param v
	 * @throws InterruptedException
	 */
	public void waitUntilNot(T v) throws InterruptedException;
	
	/**
	 * Wait until {@code (Objects.equals(changedValue, v) == false)}.
	 * 
	 * <p>
	 * Blocking-method.<br />
	 * Blocking until {@code (Objects.equals(changedValue, v) == false)}
	 * </p>
	 * 
	 * @param v
	 * @param timeout
	 * @param unit
	 * @throws InterruptedException
	 * @throws TimeoutException
	 */
	public void waitUntilNot(T v, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException;
	
	/**
	 * Wait until {@code (Objects.equals(changedValue, v) == false)}.
	 * 
	 * <p>
	 * Blocking-method.<br />
	 * Blocking until {@code (Objects.equals(changedValue, v) == false)}
	 * </p>
	 * 
	 * @param v
	 * @param tp
	 * @throws InterruptedException
	 * @throws TimeoutException
	 */
	public void waitUntilNot(T v, ReadOnlyTimeProperty tp) throws InterruptedException, TimeoutException;
	
}
