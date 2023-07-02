package com.shimizukenta.secs.local.property;

import java.io.Serializable;
import java.util.Collection;

/**
 * Collection value Compution, includes Collection-methods, Observer.
 * 
 * <p>
 * Unsupport Collection-methods to change value.<br />
 * </p>
 * 
 * @author kenta-shimizu
 *
 * @param <E> Element
 * @param <T> Type of Collection
 * @see Collection
 * @see CollectionObservable
 * 
 */
public interface CollectionCompution<E, T extends Collection<E>> extends Collection<E>, CollectionObservable<E, T>, Serializable {
	
}
