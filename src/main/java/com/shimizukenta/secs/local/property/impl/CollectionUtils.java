package com.shimizukenta.secs.local.property.impl;

import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Predicate;

import com.shimizukenta.secs.local.property.ChangeListener;
import com.shimizukenta.secs.local.property.CollectionObservable;
import com.shimizukenta.secs.local.property.Observable;
import com.shimizukenta.secs.local.property.TimeoutGettable;

/**
 * 
 * @author kenta-shimizu
 *
 */
public class CollectionUtils {

	private CollectionUtils() {
		/* Nothing */
	}
	
	/**
	 * Inner Collection.
	 * 
	 * @author kenta-shimizu
	 *
	 * @param <E> Element
	 * @param <T> Type
	 */
	private static class InnerCollection<E, T extends Collection<E>> extends AbstractPredicateCompution<T> {
		
		private static final long serialVersionUID = 111127328521430450L;
		
		public InnerCollection(Predicate<? super T> compute) {
			super(compute);
		}
		
		@Override
		protected void changedValue(T c) {
			synchronized ( this._sync ) {
				this._syncSetAndNotifyChanged(Boolean.valueOf(this._compute.test(c)));
			}
		}
	}
	
	private static <E, T extends Collection<E>> InnerCollection<E, T> buildInnerCollection(
			Observable<T> observer,
			Predicate<? super T> compute) {
		
		final InnerCollection<E, T> i = new InnerCollection<>(compute);
		i.bind(observer);
		return i;
	}
	
	/**
	 * Returns Contains PredicateCompution.
	 * 
	 * @param <E> Element
	 * @param <T> Type
	 * @param observer is extends {@code Collection<E>}
	 * @param o element whose presence in this collection is to be tested
	 * @return Contains PredicateCompution
	 * @see Collection#contains(Object)
	 */
	public static <E, T extends Collection<E>> AbstractPredicateCompution<T> computeContains(
			Observable<T> observer,
			Object o) {
		
		return buildInnerCollection(
				observer,
				x -> (x == null ? false : x.contains(o)));
	}
	
	/**
	 * Returns NOT Contains PredicateCompution.
	 * 
	 * @param <E> Element
	 * @param <T> Type
	 * @param observer Collection observer
	 * @param o element whose presence in this collection is to be tested
	 * @return NOT Contains PredicateCompution
	 * @see Collection#contains(Object)
	 */
	public static <E, T extends Collection<E>> AbstractPredicateCompution<T> computeNotContains(
			Observable<T> observer,
			Object o) {
		
		return buildInnerCollection(
				observer,
				x -> (x == null ? false : (! x.contains(o))));
	}
	
	/**
	 * Returns ContainsAll Compution.
	 * 
	 * @param <E> Key
	 * @param <T> Type
	 * @param observer is Collection observer
	 * @param c collection to be checked for containment in this collection
	 * @return ContainsAll Compution
	 * @see Collection#containsAll(Collection)
	 */
	public static <E, T extends Collection<E>> AbstractPredicateCompution<T> computeContainsAll(
			Observable<T> observer,
			Collection<?> c) {
		
		return buildInnerCollection(
				observer,
				x -> (x == null ? false : x.containsAll(c)));
	}
	
	/**
	 * Returns NOT ContainsAll Compution.
	 * 
	 * @param <E> Key
	 * @param <T> Type
	 * @param observer Collection observer
	 * @param c collection to be checked for containment in this collection
	 * @return NOT ContainsAll Compution
	 * @see Collection#containsAll(Collection)
	 */
	public static <E, T extends Collection<E>> AbstractPredicateCompution<T> computeNotContainsAll(
			Observable<T> observer,
			Collection<?> c) {
		
		return buildInnerCollection(
				observer,
				x -> (x == null ? false : (! x.containsAll(c))));
	}
	
	/**
	 * Returns IsEmpty PredicateCompution.
	 * 
	 * @param <E> Element
	 * @param <T> Type
	 * @param observer Collection observer
	 * @return IsEmpty PredicateCompution
	 * @see Collection#isEmpty()
	 */
	public static <E, T extends Collection<E>> AbstractPredicateCompution<T> computeIsEmpty(
			Observable<T> observer) {
		
		return buildInnerCollection(
				observer,
				x -> (x == null ? false : x.isEmpty()));
	}
	
	/**
	 * Returns NOT IsEmpty PredicateCompution.
	 * 
	 * @param <E> Element
	 * @param <T> Type
	 * @param observer Collection observer
	 * @return NOT IsEmpty PredicateCompution
	 * @see Collection#isEmpty()
	 */
	public static <E, T extends Collection<E>> AbstractPredicateCompution<T> computeIsNotEmpty(
			Observable<T> observer) {
		
		return buildInnerCollection(
				observer,
				x -> (x == null ? false : (! x.isEmpty())));
	}
	
	/**
	 * Waiting until condition is true.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param <E> Key
	 * @param <T> Value
	 * @param i Compution
	 * @param observer Observer
	 * @throws InterruptedException if interrupted while waiting
	 */
	public static <E, T extends Collection<E>> void waitUntil(
			AbstractPredicateCompution<T> i,
			Observable<T> observer) throws InterruptedException {
		
		try {
			i.waitUntilTrue();
		}
		finally {
			i.unbind(observer);
		}
	}
	
	/**
	 * Waiting until condition is true.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param <E> Key
	 * @param <T> Value
	 * @param i Compution
	 * @param observer Observer
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	public static <E, T extends Collection<E>> void waitUntil(
			AbstractPredicateCompution<T> i,
			Observable<T> observer,
			long timeout,
			TimeUnit unit) throws InterruptedException, TimeoutException {
		
		try {
			i.waitUntilTrue(timeout, unit);
		}
		finally {
			i.unbind(observer);
		}
	}
	
	/**
	 * Waiting until condition is true.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param <E> Key
	 * @param <T> Value
	 * @param i Compution
	 * @param observer Observer
	 * @param p is TimeoutProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	public static <E, T extends Collection<E>> void waitUntil(
			AbstractPredicateCompution<T> i,
			Observable<T> observer,
			TimeoutGettable p) throws InterruptedException, TimeoutException {
		
		try {
			i.waitUntilTrue(p);
		}
		finally {
			i.unbind(observer);
		}
	}
	
	/**
	 * Inner Size.
	 * 
	 * @author kenta-shimizu
	 *
	 * @param <E> Element
	 * @param <T> Type
	 */
	private static class InnerSize<E, T extends Collection<E>> extends AbstractIntegerCompution {
		
		private static final long serialVersionUID = 6647649394326147795L;
		
		public InnerSize() {
			super();
		}
		
		private void changedCollection(T c) {
			synchronized ( this._sync ) {
				this._syncSetAndNotifyChanged(Integer.valueOf(c.size()));
			}
		}
		
		private final ChangeListener<T> bindLstnr = this::changedCollection;
		
		public boolean bindCollection(CollectionObservable<E, T> observer) {
			return observer.addChangeListener(this.bindLstnr);
		}
	}
	
	/**
	 * Returns Size Integer Compution.
	 * 
	 * @param <E> Element
	 * @param <T> Type
	 * @param observer Collection observer
	 * @return Size Integer Compution
	 */
	public static <E, T extends Collection<E>> AbstractIntegerCompution computeSize(CollectionObservable<E, T> observer) {
		final InnerSize<E, T> i = new InnerSize<>();
		i.bindCollection(observer);
		return i;
	}
	
}
