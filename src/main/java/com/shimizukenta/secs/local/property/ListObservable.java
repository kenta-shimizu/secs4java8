package com.shimizukenta.secs.local.property;

import java.util.List;

/**
 * List value Observer.
 * 
 * @author kenta-shimizu
 *
 * @param <E> Element
 * @see List
 * @see CollectionObservable
 * 
 */
public interface ListObservable<E> extends CollectionObservable<E, List<E>> {
	
}
