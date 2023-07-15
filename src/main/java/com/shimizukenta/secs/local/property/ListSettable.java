package com.shimizukenta.secs.local.property;

import java.util.List;

/**
 * List value Setter.
 * 
 * @author kenta-shimizu
 * 
 * @param <E> Element
 * @see List
 * @see CollectionSettable
 * 
 */
public interface ListSettable<E> extends CollectionSettable<E, List<E>> {
	
}
