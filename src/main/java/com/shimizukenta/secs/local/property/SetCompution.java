package com.shimizukenta.secs.local.property;

import java.util.Collection;
import java.util.Set;

/**
 * Set value Compution, includes Set-methods, Observer.
 * 
 * <p>
 * Unsupport Set-methods to change value.<br />
 * </p>
 * <p>
 * This instance is built from other Property or Compution.<br />
 * </p>
 * <ul>
 * <li>To detect value changed, {@link #addChangeListener(ChangeListener)}</li>
 * <li>To compute,
 * <ul>
 * <li>{@link #computeIsEmpty()}</li>
 * <li>{@link #computeIsNotEmpty()}</li>
 * <li>{@link #computeContains(Object)}</li>
 * <li>{@link #computeNotContains(Object)}</li>
 * <li>{@link #computeContainsAll(Collection)}</li>
 * <li>{@link #computeNotContainsAll(Collection)}</li>
 * <li>{@link #computeSize()}</li>
 * </ul>
 * </li>
 * <li>To wait until condition is true,
 * <ul>
 * <li>{@link #waitUntilIsEmpty()}</li>
 * <li>{@link #waitUntilIsNotEmpty()}</li>
 * <li>{@link #waitUntilContains(Object)}</li>
 * <li>{@link #waitUntilNotContains(Object)}</li>
 * <li>{@link #waitUntilContainsAll(Collection)}</li>
 * <li>{@link #waitUntilNotContainsAll(Collection)}</li>
 * </ul>
 * </li>
 * </ul>
 * 
 * @author kenta-shimizu
 *
 * @param <E> Element
 * @see Set
 * @see CollectionCompution
 * @see SetObservable
 */
public interface SetCompution<E> extends Set<E>, CollectionCompution<E, Set<E>>, SetObservable<E> {
	
}
