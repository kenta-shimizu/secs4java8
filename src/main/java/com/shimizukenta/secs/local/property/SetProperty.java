package com.shimizukenta.secs.local.property;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.shimizukenta.secs.local.property.impl.AbstractSetProperty;

/**
 * Set value Property, include Set-methods, Setter, Observer.
 * 
 * <ul>
 * <li>To build instance, {@link #newInstance()}</li>
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
 * @see CollectionProperty
 * @see SetSettable
 * @see SetObservable
 * 
 */
public interface SetProperty<E> extends Set<E>, CollectionProperty<E, Set<E>>, SetObservable<E>, SetSettable<E> {
	
	/**
	 * Instance builder.
	 * 
	 * @param <E> Element
	 * @return new-instance
	 */
	public static <E> SetProperty<E> newInstance() {
		return newInstance(Collections.emptySet());
	}
	
	/**
	 * Instance builder.
	 * 
	 * @param <E> Element
	 * @param initial the {@code Collection<? extends E>} value
	 * @return new-instance
	 */
	public static <E> SetProperty<E> newInstance(Collection<? extends E> initial) {
		return new AbstractSetProperty<E>(new HashSet<>(initial)) {
			
			private static final long serialVersionUID = 1830279783393865791L;
		};
	}
}
