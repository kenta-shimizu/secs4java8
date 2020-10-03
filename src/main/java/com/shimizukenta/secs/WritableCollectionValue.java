package com.shimizukenta.secs;

import java.util.Collection;
import java.util.function.Predicate;

public interface WritableCollectionValue<T> extends WritableValue<Collection<? extends T>> {
	
	/**
	 * Setter by {@link Collection#add(Object)}
	 * 
	 * @param e
	 * @return {@code true} if this collection changed as a result of the call
	 */
	public boolean add(T e);
	
	/**
	 * Setter by {@link Collection#remove(Object)}
	 * 
	 * @param o
	 * @return {@code true} if an element was removed as a result of this call
	 */
	public boolean remove(Object o);
	
	/**
	 * Setter by {@link Collection#addAll(Collection)}
	 * 
	 * @param c
	 * @return {@code true} if this collection changed as a result of the call
	 */
	public boolean addAll(Collection<? extends T> c);
	
	/**
	 * Setter by {@link Collection#removeAll(Collection)}
	 * 
	 * @param c
	 * @return {@code true} if this collection changed as a result of the call
	 */
	public boolean removeAll(Collection<?> c);
	
	/**
	 * Setter by {@link Collection#retainAll(Collection)}
	 * 
	 * @param c
	 * @return {@code true} if this collection changed as a result of the call
	 */
	public boolean retainAll(Collection<?> c);
	
	/**
	 * Setter by {@link Collection#clear()}
	 * 
	 */
	public void clear();
	
	/**
	 * Setter by {@link Collection#removeIf(Predicate)}
	 * 
	 * @param filter
	 * @return {@code true} if any elements were removed
	 */
	public boolean removeIf(Predicate<? super T> filter);
	
}
