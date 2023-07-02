package com.shimizukenta.secs.local.property;

import java.util.Set;

/**
 * Set value Observer.
 * 
 * @author kenta-shimizu
 *
 * @param <E> Element
 * @see Set
 * @see CollectionObservable
 * 
 */
public interface SetObservable<E> extends CollectionObservable<E, Set<E>> {
	
}
