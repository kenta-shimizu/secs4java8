package com.shimizukenta.secs.local.property;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.shimizukenta.secs.local.property.impl.ObjectUtils;

/**
 * Object value Observer.
 * 
 * @author kenta-shimizu
 *
 * @param <T> Type
 * @see Observable
 * 
 */
public interface ObjectObservable<T> extends Observable<T> {
	
	/**
	 * Returns ComparativeCompution instance of isEqualTo.
	 * 
	 * @param <U> Type
	 * @param observer the ObjectObserver
	 * @return ComparativeCompution instance of isEqualTo
	 * @see ComparativeCompution
	 */
	default public <U> ComparativeCompution computeIsEqualTo(ObjectObservable<U> observer) {
		return ObjectUtils.computeIsEqualTo(this, observer);
	}
	
	/**
	 * Returns ComparativeCompution instance of isEqualTo.
	 * 
	 * @param <U> Type
	 * @param ref the Object value
	 * @return ComparativeCompution instance of isEqualTo
	 * @see ComparativeCompution
	 */
	default public <U> ComparativeCompution computeIsEqualTo(U ref) {
		return this.computeIsEqualTo(ObjectUtils.newUnmodifiable(ref));
	}
	
	/**
	 * Returns ComparativeCompution instance of isNotEqualTo.
	 * 
	 * @param <U> Type
	 * @param observer the ObjectObserver
	 * @return ComparativeCompution instance of isNotEqualTo
	 * @see ComparativeCompution
	 */
	default public <U> ComparativeCompution computeIsNotEqualTo(ObjectObservable<U> observer) {
		return ObjectUtils.computeIsNotEqualTo(this, observer);
	}
	
	/**
	 * Returns ComparativeCompution instance of isNotEqualTo.
	 * 
	 * @param <U> Type
	 * @param ref the Object value
	 * @return ComparativeCompution instance of isNotEqualTo
	 * @see ComparativeCompution
	 */
	default public <U> ComparativeCompution computeIsNotEqualTo(U ref) {
		return this.computeIsNotEqualTo(ObjectUtils.newUnmodifiable(ref));
	}
	
	/**
	 * Waiting until value is equal.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already value is equal, pass through immediately.<br />
	 * </p>
	 * 
	 * @param <U> Type
	 * @param observer the ObjectObserver
	 * @throws InterruptedException if interrupted while waiting
	 */
	default public <U> void waitUntilEqualTo(
			ObjectObservable<U> observer) throws InterruptedException {
		
		ObjectUtils.waitUntilEqualTo(this, observer);
	}
	
	/**
	 * Waiting until value is equal.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already value is equal, pass through immediately.<br />
	 * </p>
	 * 
	 * @param <U> Type
	 * @param observer the ObjectObserver
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public <U> void waitUntilEqualTo(
			ObjectObservable<U> observer,
			long timeout,
			TimeUnit unit) throws InterruptedException, TimeoutException {
		
		ObjectUtils.waitUntilEqualTo(this, observer, timeout, unit);
	}
	
	/**
	 * Waiting until value is equal.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already value is equal, pass through immediately.<br />
	 * </p>
	 * 
	 * @param <U> Type
	 * @param observer the ObjectObserver
	 * @param p the TimeoutProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public <U> void waitUntilEqualTo(
			ObjectObservable<U> observer,
			TimeoutGettable p) throws InterruptedException, TimeoutException {
		
		ObjectUtils.waitUntilEqualTo(this, observer, p);
	}
	
	/**
	 * Waiting until value is equal.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already value is equal, pass through immediately.<br />
	 * </p>
	 * 
	 * @param <U> Type
	 * @param ref the Object value
	 * @throws InterruptedException if interrupted while waiting
	 */
	default public <U> void waitUntilEqualTo(
			U ref) throws InterruptedException {
		
		this.waitUntilEqualTo(ObjectUtils.newUnmodifiable(ref));
	}
	
	/**
	 * Waiting until value is equal.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already value is equal, pass through immediately.<br />
	 * </p>
	 * 
	 * @param <U> Type
	 * @param ref the Object value
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public <U> void waitUntilEqualTo(
			U ref,
			long timeout,
			TimeUnit unit) throws InterruptedException, TimeoutException {
		
		this.waitUntilEqualTo(ObjectUtils.newUnmodifiable(ref), timeout, unit);
	}

	/**
	 * Waiting until value is equal.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already value is equal, pass through immediately.<br />
	 * </p>
	 * 
	 * @param <U> Type
	 * @param ref the Object value
	 * @param p the TimeoutProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public <U> void waitUntilEqualTo(
			
			U ref,
			TimeoutGettable p) throws InterruptedException, TimeoutException {
		
		this.waitUntilEqualTo(ObjectUtils.newUnmodifiable(ref), p);
	}
	
	/**
	 * Waiting until value is NOT equal.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already value is <strong>NOT</strong> equal, pass through immediately.<br />
	 * </p>
	 * 
	 * @param <U> Type
	 * @param observer the ObjectObserver
	 * @throws InterruptedException if interrupted while waiting
	 */
	default public <U> void waitUntilNotEqualTo(
			ObjectObservable<U> observer) throws InterruptedException {
		
		ObjectUtils.waitUntilNotEqualTo(this, observer);
	}
	
	/**
	 * Waiting until value is NOT equal.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already value is <strong>NOT</strong> equal, pass through immediately.<br />
	 * </p>
	 * 
	 * @param <U> Type
	 * @param observer the Object value
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public <U> void waitUntilNotEqualTo(
			ObjectObservable<U> observer,
			long timeout,
			TimeUnit unit) throws InterruptedException, TimeoutException {
		
		ObjectUtils.waitUntilNotEqualTo(this, observer, timeout, unit);
	}
	
	/**
	 * Waiting until value is NOT equal.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already value is <strong>NOT</strong> equal, pass through immediately.<br />
	 * </p>
	 * 
	 * @param <U> Type
	 * @param observer the ObjectObserver
	 * @param p the TimeoutProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public <U> void waitUntilNotEqualTo(
			ObjectObservable<U> observer,
			TimeoutGettable p) throws InterruptedException, TimeoutException {
		
		ObjectUtils.waitUntilNotEqualTo(this, observer, p);
	}
	
	/**
	 * Waiting until value is NOT equal.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already value is <strong>NOT</strong> equal, pass through immediately.<br />
	 * </p>
	 * 
	 * @param <U> Type
	 * @param ref the Object value
	 * @throws InterruptedException if interrupted while waiting
	 */
	default public <U> void waitUntilNotEqualTo(
			U ref) throws InterruptedException {
		
		this.waitUntilNotEqualTo(ObjectUtils.newUnmodifiable(ref));
	}
	
	/**
	 * Waiting until value is NOT equal.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already value is <strong>NOT</strong> equal, pass through immediately.<br />
	 * </p>
	 * 
	 * @param <U> Type
	 * @param ref the Object value
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public <U> void waitUntilNotEqualTo(
			U ref,
			long timeout,
			TimeUnit unit) throws InterruptedException, TimeoutException {
		
		this.waitUntilNotEqualTo(ObjectUtils.newUnmodifiable(ref), timeout, unit);
	}
	
	/**
	 * Waiting until value is NOT equal.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already value is <strong>NOT</strong> equal, pass through immediately.<br />
	 * </p>
	 * 
	 * @param <U> Type
	 * @param ref the Object value
	 * @param p the TimeoutProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public <U> void waitUntilNotEqualTo(
			U ref,
			TimeoutGettable p) throws InterruptedException, TimeoutException {
		
		this.waitUntilNotEqualTo(ObjectUtils.newUnmodifiable(ref), p);
	}
	
	/**
	 * Returns BooleanCompution instance of isEqualTo null.
	 * 
	 * @return BooleanCompution instance of isEqualTo null
	 * @see BooleanCompution
	 */
	default public BooleanCompution computeIsNull() {
		return ObjectUtils.computeIsNull(this);
	}
	
	/**
	 * Returns BooleanCompution instance of isNotEqualTo null.
	 * 
	 * @return BooleanCompution instance of isNotEqualTo null
	 * @see BooleanCompution
	 */
	default public BooleanCompution computeIsNotNull() {
		return ObjectUtils.computeIsNotNull(this);
	}
	
	/**
	 * Waiting until value is NOT null, and return value.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already value is <strong>NOT</strong> null, return value immediately.<br />
	 * </p>
	 * 
	 * @return value
	 * @throws InterruptedException if interrupted while waiting
	 */
	default public T waitUntilNotNullAndGet() throws InterruptedException {
		return ObjectUtils.waitUntilNotNullAndGet(this);
	}
	
	/**
	 * Waiting until value is NOT null, and return value.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already value is <strong>NOT</strong> null, return value immediately.<br />
	 * </p>
	 * 
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @return value
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public T waitUntilNotNullAndGet(long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		return ObjectUtils.waitUntilNotNullAndGet(this, timeout, unit);
	}
	
	/**
	 * Waiting until value is NOT null, and return value.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already value is <strong>NOT</strong> null, return value immediately.<br />
	 * </p>
	 * 
	 * @param p the TimeoutProperty
	 * @return value
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public T waitUntilNotNullAndGet(TimeoutGettable p) throws InterruptedException, TimeoutException {
		return ObjectUtils.waitUntilNotNullAndGet(this, p);
	}
	
	/**
	 * Waiting until value is null.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already value is null, pass through immediately.<br />
	 * </p>
	 * 
	 * @throws InterruptedException if interrupted while waiting
	 */
	default public void waitUntilNull() throws InterruptedException {
		ObjectUtils.waitUntilNull(this);
	}
	
	/**
	 * Waiting until value is null.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already value is null, pass through immediately.<br />
	 * </p>
	 * 
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public void waitUntilNull(long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		ObjectUtils.waitUntilNull(this, timeout, unit);
	}
	
	/**
	 * Waiting until value is null.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already value is null, pass through immediately.<br />
	 * </p>
	 * 
	 * @param p the TimeoutProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public void waitUntilNull(TimeoutGettable p) throws InterruptedException, TimeoutException {
		ObjectUtils.waitUntilNull(this, p);
	}
	
}
