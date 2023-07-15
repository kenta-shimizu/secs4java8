package com.shimizukenta.secs.local.property;

import java.io.Serializable;
import java.util.Collection;

/**
 * Collection value Property, includes Collection-methods, Setter, Observer.
 * 
 * @author kenta-shimizu
 *
 * @param <E> Element
 * @param <T> Type of Collection
 * @see Collection
 * @see CollectionObservable
 * @see CollectionSettable
 * 
 */
public interface CollectionProperty<E, T extends Collection<E>> extends Collection<E>, CollectionObservable<E, T>, CollectionSettable<E, T>, Serializable {

}
