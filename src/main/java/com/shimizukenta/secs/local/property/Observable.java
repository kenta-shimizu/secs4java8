package com.shimizukenta.secs.local.property;

import com.shimizukenta.secs.local.property.impl.StringUtils;

/**
 * Super Observer interface.
 * 
 * @author kenta-shimizu
 *
 * @param <T> Type
 */
public interface Observable<T> {
	
	/**
	 * Add change-listener.
	 * 
	 * @param l listener
	 * @return true if add success.
	 * @see #removeChangeListener(ChangeListener)
	 */
	public boolean addChangeListener(ChangeListener<? super T> l);
	
	/**
	 * Remove change-listener.
	 * 
	 * @param l listener
	 * @return true if remove success.
	 * @see #addChangeListener(ChangeListener)
	 */
	public boolean removeChangeListener(ChangeListener<? super T> l);
	
	/**
	 * Returns StringCompution instance of #toString.
	 * 
	 * @return StringCompution instance of #toString
	 * @see Object#toString()
	 */
	default public StringObservable computeToString() {
		return StringUtils.computeToString(this);
	}
	
}
