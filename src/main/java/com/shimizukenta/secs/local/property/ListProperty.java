package com.shimizukenta.secs.local.property;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.shimizukenta.secs.local.property.impl.AbstractListProperty;

/**
 * List value Property, include List-methods, Setter, Observer.
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
 * @see List
 * @see CollectionProperty
 * @see ListObservable
 * @see ListSettable
 */
public interface ListProperty<E> extends List<E>, CollectionProperty<E, List<E>>, ListObservable<E>, ListSettable<E> {
	
	/**
	 * Instance builder.
	 * 
	 * @param <E> Element
	 * @return new-instance.
	 */
	public static <E> ListProperty<E> newInstance() {
		return newInstance(Collections.emptyList());
	}
	
	/**
	 * Instance builder.
	 * 
	 * @param <E> Element
	 * @param initial the Collection
	 * @return new-instance.
	 */
	public static <E> ListProperty<E> newInstance(Collection<? extends E> initial) {
		return new AbstractListProperty<E>(new ArrayList<>(initial)) {
			
			private static final long serialVersionUID = -6240774667722761630L;
		};
	}

}
