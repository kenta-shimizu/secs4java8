package com.shimizukenta.secs.local.property;

import java.util.Set;

/**
 * Set value Setter.
 * 
 * @author kenta-shimizu
 *
 * @param <E> Element
 * @see Set
 * @see CollectionSettable
 * 
 */
public interface SetSettable<E> extends CollectionSettable<E, Set<E>> {
	
}
