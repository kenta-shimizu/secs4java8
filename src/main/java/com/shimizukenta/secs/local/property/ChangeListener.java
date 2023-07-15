package com.shimizukenta.secs.local.property;

import java.util.EventListener;

/**
 * Change Listener, used in Observer.
 * 
 * @author kenta-shimizu
 *
 * @param <T> Type
 * @see EventListener
 * @see Observable
 */
public interface ChangeListener<T> extends EventListener {
	
	/**
	 * Changed.
	 * 
	 * @param v changed value
	 */
	public void changed(T v);
}
