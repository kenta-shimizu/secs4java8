package com.shimizukenta.secs.local.property;

import java.util.Collection;

/**
 * Collection value Setter.
 * 
 * @author kenta-shimizu
 *
 * @param <E> Element
 * @param <T> Type of Collection
 * @see Collection
 * @see Settable
 */
public interface CollectionSettable<E, T extends Collection<E>> extends Settable<T> {
	
}
