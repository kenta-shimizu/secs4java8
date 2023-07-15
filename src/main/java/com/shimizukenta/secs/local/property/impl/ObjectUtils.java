package com.shimizukenta.secs.local.property.impl;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import com.shimizukenta.secs.local.property.ObjectProperty;
import com.shimizukenta.secs.local.property.Observable;
import com.shimizukenta.secs.local.property.TimeoutGettable;

/**
 * 
 * @author kenta-shimizu
 *
 */
public final class ObjectUtils {
	
	private ObjectUtils() {
		/* Nothing */
	}
	
	/**
	 * Returns Unmodifiable Object Property.
	 * 
	 * @param <T> Type
	 * @param v value
	 * @return Unmodifiable Object Property
	 */
	public static <T> ObjectProperty<T> newUnmodifiable(T v) {
		return new AbstractUnmodifiableObjectProperty<T>(v) {
			
			private static final long serialVersionUID = -3491034584153413061L;
		};
	}
	
	private static final ObjectProperty<Object> singleNullObj = newUnmodifiable(null);
	
	private static <T> AbstractPredicateCompution<T> buildPredicateCompution(
			Observable<T> observer,
			Predicate<? super T> compute) {
		
		final AbstractPredicateCompution<T> i = new AbstractPredicateCompution<T>(compute) {
			
			private static final long serialVersionUID = 5868891255220930302L;
		};
		
		i.bind(observer);
		
		return i;
	}
	
	/**
	 * Returns Unmodifiable Null Object Property.
	 * 
	 * @return Unmodifiable Null Object Property
	 */
	public static ObjectProperty<Object> getUnmodifiableNull() {
		return singleNullObj;
	}
	
	/**
	 * Returns IsNull PredicateCompution.
	 * 
	 * @param <T> Type
	 * @param observer Observer
	 * @return IsNull Predicate Compution
	 */
	public static <T> AbstractPredicateCompution<T> computeIsNull(
			Observable<T> observer) {
		
		return buildPredicateCompution(observer, v -> v == null);
	}
	
	/**
	 * Returns IsNotNull PredicateCompution.
	 * 
	 * @param <T> Type
	 * @param observer Observer
	 * @return IsNotNull PredicateCompution
	 */
	public static <T> AbstractPredicateCompution<T> computeIsNotNull(
			Observable<T> observer) {
		
		return buildPredicateCompution(observer, v -> v != null);
	}
	
	private static <T> T waitUntilPredicate(
			AbstractPredicateCompution<T> i,
			Observable<T> observer) throws InterruptedException {
		
		try {
			return i.waitUntilTrueAndGet();
		}
		finally {
			i.unbind(observer);
		}
	}
	
	private static <T> T waitUntilPredicate(
			AbstractPredicateCompution<T> i,
			Observable<T> observer,
			long timeout,
			TimeUnit unit) throws InterruptedException, TimeoutException {
		
		try {
			return i.waitUntilTrueAndGet(timeout, unit);
		}
		finally {
			i.unbind(observer);
		}
	}
	
	private static <T> T waitUntilPredicate(
			AbstractPredicateCompution<T> i,
			Observable<T> observer,
			TimeoutGettable p) throws InterruptedException, TimeoutException {
		
		try {
			return i.waitUntilTrueAndGet(p);
		}
		finally {
			i.unbind(observer);
		}
	}
	
	/**
	 * Waiting until value is not null, and return value.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already value is not null, return value immediately.<br />
	 * </p>
	 * 
	 * @param <T> Type
	 * @param observer ObjectObserver
	 * @return value
	 * @throws InterruptedException if interrupted while waiting
	 */
	public static <T> T waitUntilNotNullAndGet(
			Observable<T> observer) throws InterruptedException {
		
		return waitUntilPredicate(computeIsNotNull(observer), observer);
	}
	
	/**
	 * Waiting until value is not null, and return value.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already value is not null, return value immediately.<br />
	 * </p>
	 * 
	 * @param <T> Type
	 * @param observer ObjectObserver
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @return value
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	public static <T> T waitUntilNotNullAndGet(
			Observable<T> observer,
			long timeout,
			TimeUnit unit) throws InterruptedException, TimeoutException {
		
		return waitUntilPredicate(computeIsNotNull(observer), observer, timeout, unit);
	}
	
	/**
	 * Waiting until value is not null, and return value.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already value is not null, return value immediately.<br />
	 * </p>
	 * 
	 * @param <T> Type
	 * @param observer ObjectObserver
	 * @param p is TimeoutProperty
	 * @return value
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	public static <T> T waitUntilNotNullAndGet(
			Observable<T> observer,
			TimeoutGettable p) throws InterruptedException, TimeoutException {
		
		return waitUntilPredicate(computeIsNotNull(observer), observer, p);
	}
	
	/**
	 * Waiting until value is null.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already value is null, pass through immediately.<br />
	 * </p>
	 * 
	 * @param <T> Type
	 * @param observer ObjectObserver
	 * @throws InterruptedException if interrupted while waiting
	 */
	public static <T> void waitUntilNull(
			Observable<T> observer) throws InterruptedException {
		
		waitUntilPredicate(computeIsNull(observer), observer);
	}
	
	/**
	 * Waiting until value is null.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already value is null, pass through immediately.<br />
	 * </p>
	 * 
	 * @param <T> Type
	 * @param observer ObjectObserver
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	public static <T> void waitUntilNull(
			Observable<T> observer,
			long timeout,
			TimeUnit unit) throws InterruptedException, TimeoutException {
		
		waitUntilPredicate(computeIsNull(observer), observer, timeout, unit);
	}
	
	/**
	 * Waiting until value is null.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already value is null, pass through immediately.<br />
	 * </p>
	 * 
	 * @param <T> Type
	 * @param observer ObjectObserver
	 * @param p is TimeoutProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	public static <T> void waitUntilNull(
			Observable<T> observer,
			TimeoutGettable p) throws InterruptedException, TimeoutException {
		
		waitUntilPredicate(computeIsNull(observer), observer, p);
	}
	
	private static <T, U> AbstractBiPredicateCompution<T, U> buildBiPredicateCompution(
			Observable<T> left,
			Observable<U> right,
			BiPredicate<? super T, ? super U> compute) {
		
		final AbstractBiPredicateCompution<T, U> i = new AbstractBiPredicateCompution<T, U>(compute) {
			
			private static final long serialVersionUID = 1L;
		};
		
		if ( left != null ) {
			i.bindLeft(left);
		}
		
		if ( right != null ) {
			i.bindRight(right);
		}
		
		return i;
	}
	
	/**
	 * Returns IsEqualTo PredicateCompution.
	 * 
	 * @param <T> Type
	 * @param <U> TYpe
	 * @param left left observer
	 * @param right right observer
	 * @return IsEqualTo Predicate Compution
	 */
	public static <T, U> AbstractBiPredicateCompution<T, U> computeIsEqualTo(
			Observable<T> left,
			Observable<U> right) {
		
		return buildBiPredicateCompution(left, right, (Object a, Object b) -> Objects.equals(a, b));
	}
	
	/**
	 * Returns IsNotEqualTo PredicateCompution.
	 * 
	 * @param <T> Type
	 * @param <U> TYpe
	 * @param left left observer
	 * @param right right observer
	 * @return IsNotEqualTo Predicate Compution
	 */
	public static <T, U> AbstractBiPredicateCompution<T, U> computeIsNotEqualTo(
			Observable<T> left,
			Observable<U> right) {
		
		return buildBiPredicateCompution(left, right, (Object a, Object b) -> ! Objects.equals(a, b));
	}
	
	private static <T, U> void waitUntilBiPredicate(
			AbstractBiPredicateCompution<T, U> i,
			Observable<T> left,
			Observable<U> right) throws InterruptedException {
		
		try {
			i.waitUntilTrue();
		}
		finally {
			if ( left != null ) {
				i.unbindLeft(left);
			}
			if ( right != null ) {
				i.unbindRight(right);
			}
		}
	}
	
	private static <T, U> void waitUntilBiPredicate(
			AbstractBiPredicateCompution<T, U> i,
			Observable<T> left,
			Observable<U> right,
			long timeout,
			TimeUnit unit) throws InterruptedException, TimeoutException {
		
		try {
			i.waitUntilTrue(timeout, unit);
		}
		finally {
			if ( left != null ) {
				i.unbindLeft(left);
			}
			if ( right != null ) {
				i.unbindRight(right);
			}
		}
	}

	private static <T, U> void waitUntilBiPredicate(
			AbstractBiPredicateCompution<T, U> i,
			Observable<T> left,
			Observable<U> right,
			TimeoutGettable p) throws InterruptedException, TimeoutException {
		
		try {
			i.waitUntilTrue(p);
		}
		finally {
			if ( left != null ) {
				i.unbindLeft(left);
			}
			if ( right != null ) {
				i.unbindRight(right);
			}
		}
	}
	
	/**
	 * Waiting until {@code (a.value == b.value)}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already {@code (a.value == b.value)}, pass through immediately.<br />
	 * </p>
	 * 
	 * @param <T> Type
	 * @param <U> Type
	 * @param a observer
	 * @param b observer
	 * @throws InterruptedException if interrupted while waiting
	 */
	public static <T, U> void waitUntilEqualTo(
			Observable<T> a,
			Observable<U> b) throws InterruptedException {
		
		waitUntilBiPredicate(computeIsEqualTo(a, b), a, b);
	}
	
	/**
	 * Waiting until {@code (a.value == b.value)}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already {@code (a.value == b.value)}, pass through immediately.<br />
	 * </p>
	 * 
	 * @param <T> Type
	 * @param <U> Type
	 * @param a observer
	 * @param b observer
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	public static <T, U> void waitUntilEqualTo(
			Observable<T> a,
			Observable<U> b,
			long timeout,
			TimeUnit unit) throws InterruptedException, TimeoutException {
		
		waitUntilBiPredicate(computeIsEqualTo(a, b), a, b, timeout, unit);
	}
	
	/**
	 * Waiting until {@code (a.value == b.value)}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already {@code (a.value == b.value)}, pass through immediately.<br />
	 * </p>
	 * 
	 * @param <T> Type
	 * @param <U> Type
	 * @param a observer
	 * @param b observer
	 * @param p is TimeoutProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	public static <T, U> void waitUntilEqualTo(
			Observable<T> a,
			Observable<U> b,
			TimeoutGettable p) throws InterruptedException, TimeoutException {
		
		waitUntilBiPredicate(computeIsEqualTo(a, b), a, b, p);
	}
	
	/**
	 * Waiting until {@code (a.value != b.value)}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already {@code (a.value != b.value)}, pass through immediately.<br />
	 * </p>
	 * 
	 * @param <T> Type
	 * @param <U> Type
	 * @param a observer
	 * @param b observer
	 * @throws InterruptedException if interrupted while waiting
	 */
	public static <T, U> void waitUntilNotEqualTo(
			Observable<T> a,
			Observable<U> b) throws InterruptedException {
		
		waitUntilBiPredicate(computeIsNotEqualTo(a, b), a, b);
	}
	
	/**
	 * Waiting until {@code (a.value != b.value)}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already {@code (a.value != b.value)}, pass through immediately.<br />
	 * </p>
	 * 
	 * @param <T> Type
	 * @param <U> Type
	 * @param a observer
	 * @param b observer
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	public static <T, U> void waitUntilNotEqualTo(
			Observable<T> a,
			Observable<U> b,
			long timeout,
			TimeUnit unit) throws InterruptedException, TimeoutException {
		
		waitUntilBiPredicate(computeIsNotEqualTo(a, b), a, b, timeout, unit);
	}
	
	/**
	 * Waiting until {@code (a.value != b.value)}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already {@code (a.value != b.value)}, pass through immediately.<br />
	 * </p>
	 * 
	 * @param <T> Type
	 * @param <U> Type
	 * @param a observer
	 * @param b observer
	 * @param p is TimeoutProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	public static <T, U> void waitUntilNotEqualTo(
			Observable<T> a,
			Observable<U> b,
			TimeoutGettable p) throws InterruptedException, TimeoutException {
		
		waitUntilBiPredicate(computeIsNotEqualTo(a, b), a, b, p);
	}

}
