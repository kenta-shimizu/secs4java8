package com.shimizukenta.secs.local.property;

import java.io.Serializable;

/**
 * Super Property interface, includes Getter, Setter, Observer.
 * 
 * @author kenta-shimizu
 *
 * @param <T> Type
 * @see Gettable
 * @see Settable
 * @see Observable
 */
public interface Property<T> extends Gettable<T>, Settable<T>, Observable<T>, Serializable {
	
}
