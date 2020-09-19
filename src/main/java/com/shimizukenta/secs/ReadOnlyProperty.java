package com.shimizukenta.secs;

/**
 * Getter, Value-Change-Observer
 * 
 * @author kenta-shimizu
 *
 * @param <T>
 */
public interface ReadOnlyProperty<T> {

	/**
	 * value getter
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
	 * Remove Value-Change-Listener
	 * 
	 * @param listener
	 * @return true if success
	 */
	public boolean removeChangeListener(PropertyChangeListener<? super T> listener);
	
	/**
	 * Blocking-method.<br />
	 * Blocking until (Objects.equals(changedValue, v) == true).
	 * 
	 * @param v
	 * @throws InterruptedException
	 */
	public void waitUntil(T v) throws InterruptedException;
	
	/**
	 * Blocking-method.<br />
	 * Blocking until (Objects.equals(changedValue, v) == false).
	 * 
	 * @param v
	 * @throws InterruptedException
	 */
	public void waitUntilNot(T v) throws InterruptedException;
	
}
