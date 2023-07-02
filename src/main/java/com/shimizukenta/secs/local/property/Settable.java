package com.shimizukenta.secs.local.property;

/**
 * Super Setter interface.
 * 
 * @author kenta-shimizu
 *
 * @param <T> Type
 */
public interface Settable<T> {
	
	/**
	 * Bind to observer.
	 * 
	 * @param observer to addListener observer 
	 * @return {@code true} if bind success, otherwise {@code false}.
	 * @see #unbind(Observable)
	 * @see Observable#addChangeListener(ChangeListener)
	 */
	public boolean bind(Observable<? extends T> observer);
	
	/**
	 * Unbind to observer.
	 * 
	 * @param observer to removeListener observer
	 * @return {@code true} if unbind success, otherwise {@code false}.
	 * @see #bind(Observable)
	 * @see Observable#removeChangeListener(ChangeListener)
	 */
	public boolean unbind(Observable<? extends T> observer);
	
}
