package com.shimizukenta.secs.local.property;

import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.shimizukenta.secs.local.property.impl.CollectionUtils;

/**
 * Collection value Observer.
 * 
 * @author kenta-shimizu
 *
 * @param <E> Element
 * @param <T> Type of Collection
 * @see Observable
 * @see Collection
 * 
 */
public interface CollectionObservable<E, T extends Collection<E>> extends Observable<T> {
	
	/**
	 * Returns BooleanCompution instance of Collection#isEmpty.
	 * 
	 * @return BooleanCompution instance of Collection#isEmpty
	 * @see Collection#isEmpty()
	 * @see BooleanCompution
	 */
	default public BooleanCompution computeIsEmpty() {
		return CollectionUtils.computeIsEmpty(this);
	}
	
	/**
	 * Returns BooleanCompution instance of NOT Collection#isEmpty.
	 * 
	 * @return BooleanCompution instance of NOT Collection#isEmpty
	 * @see Collection#isEmpty()
	 * @see BooleanCompution
	 */
	default public BooleanCompution computeIsNotEmpty() {
		return CollectionUtils.computeIsNotEmpty(this);
	}
	
	/**
	 * Returns BooleanCompution of Collection#contains(Object).
	 * 
	 * @param o element whose presence in this collection is to be tested
	 * @return BooleanCompution of Collection#contains(Object)
	 * @see Collection#contains(Object)
	 * @see BooleanCompution
	 */
	default public BooleanCompution computeContains(Object o) {
		return CollectionUtils.computeContains(this, o);
	}
	
	/**
	 * Returns BooleanCompution of NOT Collection#contains(Object).
	 * 
	 * @param o element whose presence in this collection is to be tested
	 * @return BooleanCompution of NOT Collection#contains(Object)
	 * @see Collection#contains(Object)
	 * @see BooleanCompution
	 */
	default public BooleanCompution computeNotContains(Object o) {
		return CollectionUtils.computeNotContains(this, o);
	}
	
	/**
	 * Returns BooleanCompution of Collection#containsAll(Collection).
	 * 
	 * @param c collection to be checked for containment in this collection
	 * @return BooleanCompution of Collection#containsAll(Collection)
	 * @see Collection#containsAll(Collection)
	 * @see BooleanCompution
	 */
	default public BooleanCompution computeContainsAll(Collection<?> c) {
		return CollectionUtils.computeContainsAll(this, c);
	}
	
	/**
	 * Returns BooleanCompution of NOT Collection#containsAll(Collection).
	 * 
	 * @param c collection to be checked for containment in this collection
	 * @return BooleanCompution of NOT Collection#containsAll(Collection)
	 * @see Collection#containsAll(Collection)
	 * @see BooleanCompution
	 */
	default public BooleanCompution computeNotContainsAll(Collection<?> c) {
		return CollectionUtils.computeNotContainsAll(this, c);
	}
	
	/**
	 * Returns IntegerCompution of Collection#size().
	 * 
	 * @return IntegerCompution of Collection#size()
	 * @see Collection#size()
	 * @see IntegerCompution
	 */
	default public IntegerCompution computeSize() {
		return CollectionUtils.computeSize(this);
	}
	
	/**
	 * Waiting until Collection#isEmpty is true.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already Collection#isEmpty is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @throws InterruptedException if interrupted while waiting
	 * @see Collection#isEmpty()
	 */
	default public void waitUntilIsEmpty() throws InterruptedException {
		CollectionUtils.waitUntil(CollectionUtils.computeIsEmpty(this), this);
	}
	
	/**
	 * Waiting until Collection#isEmpty is true.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already Collection#isEmpty is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see Collection#isEmpty()
	 */
	default public void waitUntilIsEmpty(long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		CollectionUtils.waitUntil(CollectionUtils.computeIsEmpty(this), this, timeout, unit);
	}
	
	/**
	 * Waiting until Collection#isEmpty is true.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already Collection#isEmpty is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param p the TimeoutProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see Collection#isEmpty()
	 */
	default public void waitUntilIsEmpty(TimeoutGettable p) throws InterruptedException, TimeoutException {
		CollectionUtils.waitUntil(CollectionUtils.computeIsEmpty(this), this, p);
	}
	
	/**
	 * Waiting until Collection#isEmpty is false.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already Collection#isEmpty is false, pass through immediately.<br />
	 * </p>
	 * 
	 * @throws InterruptedException if interrupted while waiting
	 * @see Collection#isEmpty()
	 */
	default public void waitUntilIsNotEmpty() throws InterruptedException {
		CollectionUtils.waitUntil(CollectionUtils.computeIsNotEmpty(this), this);
	}
	
	/**
	 * Waiting until Collection#isEmpty is false.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already Collection#isEmpty is false, pass through immediately.<br />
	 * </p>
	 * 
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see Collection#isEmpty()
	 */
	default public void waitUntilIsNotEmpty(long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		CollectionUtils.waitUntil(CollectionUtils.computeIsNotEmpty(this), this, timeout, unit);
	}
	
	/**
	 * Waiting until Collection#isEmpty is false.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already Collection#isEmpty is false, pass through immediately.<br />
	 * </p>
	 * 
	 * @param p the TimeoutProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see Collection#isEmpty()
	 */
	default public void waitUntilIsNotEmpty(TimeoutGettable p) throws InterruptedException, TimeoutException {
		CollectionUtils.waitUntil(CollectionUtils.computeIsNotEmpty(this), this, p);
	}
	
	/**
	 * Waiting until Collection#contains(Object) is true.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already Collection#contains(Object) is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param o element whose presence in this collection is to be tested
	 * @throws InterruptedException if interrupted while waiting
	 * @see Collection#contains(Object)
	 */
	default public void waitUntilContains(Object o) throws InterruptedException {
		CollectionUtils.waitUntil(CollectionUtils.computeContains(this, o), this);
	}
	
	/**
	 * Waiting until Collection#contains(Object) is true.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already Collection#contains(Object) is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param o element whose presence in this collection is to be tested
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see Collection#contains(Object)
	 */
	default public void waitUntilContains(Object o, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		CollectionUtils.waitUntil(CollectionUtils.computeContains(this, o), this, timeout, unit);
	}
	
	/**
	 * Waiting until Collection#contains(Object) is true.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already Collection#contains(Object) is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param o element whose presence in this collection is to be tested
	 * @param p the TimeoutProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see Collection#contains(Object)
	 */
	default public void waitUntilContains(Object o, TimeoutGettable p) throws InterruptedException, TimeoutException {
		CollectionUtils.waitUntil(CollectionUtils.computeContains(this, o), this, p);
	}
	
	/**
	 * Waiting until Collection#contains(Object) is false.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already Collection#contains(Object) is false, pass through immediately.<br />
	 * </p>
	 * 
	 * @param o element whose presence in this collection is to be tested
	 * @throws InterruptedException if interrupted while waiting
	 * @see Collection#contains(Object)
	 */
	default public void waitUntilNotContains(Object o) throws InterruptedException {
		CollectionUtils.waitUntil(CollectionUtils.computeNotContains(this, o), this);
	}
	
	/**
	 * Waiting until Collection#contains(Object) is false.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already Collection#contains(Object) is false, pass through immediately.<br />
	 * </p>
	 * 
	 * @param o element whose presence in this collection is to be tested
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see Collection#contains(Object)
	 */
	default public void waitUntilNotContains(Object o, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		CollectionUtils.waitUntil(CollectionUtils.computeNotContains(this, o), this, timeout, unit);
	}
	
	/**
	 * Waiting until Collection#contains(Object) is false.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already Collection#contains(Object) is false, pass through immediately.<br />
	 * </p>
	 * 
	 * @param o element whose presence in this collection is to be tested
	 * @param p the TimeoutProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see Collection#contains(Object)
	 */
	default public void waitUntilNotContains(Object o, TimeoutGettable p) throws InterruptedException, TimeoutException {
		CollectionUtils.waitUntil(CollectionUtils.computeNotContains(this, o), this, p);
	}
	
	/**
	 * Waiting until Collection#containsAll(Collection) is true.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already Collection#containsAll(Collection) is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param c collection to be checked for containment in this collection
	 * @throws InterruptedException if interrupted while waiting
	 * @see Collection#containsAll(Collection)
	 */
	default public void waitUntilContainsAll(Collection<?> c) throws InterruptedException {
		CollectionUtils.waitUntil(CollectionUtils.computeContainsAll(this, c), this);
	}
	
	/**
	 * Waiting until Collection#containsAll(Collection) is true.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already Collection#containsAll(Collection) is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param c collection to be checked for containment in this collection
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see Collection#containsAll(Collection)
	 */
	default public void waitUntilContainsAll(Collection<?> c, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		CollectionUtils.waitUntil(CollectionUtils.computeContainsAll(this, c), this, timeout, unit);
	}
	
	/**
	 * Waiting until Collection#containsAll(Collection) is true.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already Collection#containsAll(Collection) is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param c collection to be checked for containment in this collection
	 * @param p the TimeoutProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see Collection#containsAll(Collection)
	 */
	default public void waitUntilContainsAll(Collection<?> c, TimeoutGettable p) throws InterruptedException, TimeoutException {
		CollectionUtils.waitUntil(CollectionUtils.computeContainsAll(this, c), this, p);
	}
	
	/**
	 * Waiting until Collection#containsAll(Collection) is false.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already Collection#containsAll(Collection) is false, pass through immediately.<br />
	 * </p>
	 * 
	 * @param c collection to be checked for containment in this collection
	 * @throws InterruptedException if interrupted while waiting
	 * @see Collection#containsAll(Collection)
	 */
	default public void waitUntilNotContainsAll(Collection<?> c) throws InterruptedException {
		CollectionUtils.waitUntil(CollectionUtils.computeNotContainsAll(this, c), this);
	}
	
	/**
	 * Waiting until Collection#containsAll(Collection) is false.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already Collection#containsAll(Collection) is false, pass through immediately.<br />
	 * </p>
	 * 
	 * @param c collection to be checked for containment in this collection
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see Collection#containsAll(Collection)
	 */
	default public void waitUntilNotContainsAll(Collection<?> c, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		CollectionUtils.waitUntil(CollectionUtils.computeNotContainsAll(this, c), this, timeout, unit);
	}
	
	/**
	 * Waiting until Collection#containsAll(Collection) is false.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already Collection#containsAll(Collection) is false, pass through immediately.<br />
	 * </p>
	 * 
	 * @param c collection to be checked for containment in this collection
	 * @param p the TimeoutProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see Collection#containsAll(Collection)
	 */
	default public void waitUntilNotContainsAll(Collection<?> c, TimeoutGettable p) throws InterruptedException, TimeoutException {
		CollectionUtils.waitUntil(CollectionUtils.computeNotContainsAll(this, c), this, p);
	}
	
}
