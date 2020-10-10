package com.shimizukenta.secs;

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
	 * Wait until {@code (Objects.equals(changedValue, v) == false)}.
	 * 
	 * Blocking-method.<br />
	 * Blocking until {@code (Objects.equals(changedValue, v) == false)}
	 * 
	 * @param v
	 * @throws InterruptedException
	 */
	public void waitUntilNot(T v) throws InterruptedException;
	
}
