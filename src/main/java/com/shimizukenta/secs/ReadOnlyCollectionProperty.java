package com.shimizukenta.secs;

import java.util.Collection;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.stream.Stream;

public interface ReadOnlyCollectionProperty<T> extends ReadOnlyProperty<Collection<T>> {
	
	/**
	 * Getter by {@link Collection#size()}
	 * 
	 * @return the number of elements in this collection
	 */
	public int size();
	
	/**
	 * Getter by {@link Collection#isEmpty()}
	 * 
	 * @return true if this collection contains no elements
	 */
	public boolean isEmpty();
	
	/**
	 * Getter by {@link Collection#contains(Object)}
	 * 
	 * @param o
	 * @return true if this collection contains the specified element
	 */
	public boolean contains(Object o);
	
	/**
	 * Getter by {@link Collection#containsAll(Collection)}
	 * 
	 * @param c
	 * @return true if this collection contains all of the elements in the specified collection
	 */
	public boolean containsAll(Collection<?> c);
	
	/**
	 * Getter by {@link Collection#iterator()}
	 * 
	 * @return an Iterator over the elements in this collection
	 */
	public Iterator<T> iterator();
	
	/**
	 * Getter by {@link Collection#toArray()}
	 * 
	 * @return an array, whose runtime component type is Object, containing all of the elements in this collection
	 */
	public Object[] toArray();
	
	/**
	 * Getter by {@link Collection#toArray(Object[])}
	 * 
	 * @param <U>
	 * @param a
	 * @return a the array into which the elements of this collection are to be stored, if it is big enough; otherwise, a new array of the same runtime type is allocated for this purpose.
	 */
	public <U> U[] toArray(U[] a);
	
	/**
	 * Getter by {@link Collection#toArray(IntFunction)}
	 * 
	 * @param <U>
	 * @param generator
	 * @return an array containing all of the elements in this collection
	 */
	public <U> U[] toArray(IntFunction<U[]> generator);
	
	/**
	 * Getter by {@link Collection#spliterator()}
	 * 
	 * @return a Spliterator over the elements in this collection
	 */
	public Spliterator<T> spliterator();
	
	/**
	 * Getter by {@link Collection#stream()}
	 * 
	 * @return a sequential Stream over the elements in this collection
	 */
	public Stream<T> stream();
	
	/**
	 * Getter by {@link Collection#parallelStream()}
	 * 
	 * @return a possibly parallel Stream over the elements in this collection
	 */
	public Stream<T> parallelStream();
	
	/**
	 * Getter by {@link Collection#forEach(Consumer)}
	 * 
	 * @param action
	 */
	public void forEach(Consumer<? super T> action);

}
