package com.shimizukenta.secs.local.property;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.shimizukenta.secs.local.property.impl.BooleanUtils;

/**
 * Boolean value Observer.
 * 
 * @author kenta-shimizu
 * @see Boolean
 * @see Observable
 * @see LogicalCompution
 *
 */
public interface BooleanObservable extends Observable<Boolean> {
	
	/**
	 * Returns {@code (this && observer)} LogicalCompution instance.
	 * 
	 * @param observer the BooleanObserver
	 * @return {@code (this && observer)} LogicalCompution instance
	 * @see #and(boolean)
	 * @see LogicalCompution#and(BooleanObservable...)
	 */
	default public LogicalCompution and(BooleanObservable observer) {
		return LogicalCompution.and(this, observer);
	}
	
	/**
	 * Returns {@code (this || observer)} LogicalCompution instance.
	 * 
	 * @param observer the BooleanObserver
	 * @return {@code (this || observer)} LogicalCompution instance
	 * @see LogicalCompution#or(BooleanObservable...)
	 */
	default public LogicalCompution or(BooleanObservable observer) {
		return LogicalCompution.or(this, observer);
	}
	
	/**
	 * Returns (! this) LogicalCompution instance.
	 * 
	 * @return {@code (! this)} LogicalCompution instance
	 * @see LogicalCompution#not(BooleanObservable)
	 */
	default public LogicalCompution not() {
		return LogicalCompution.not(this);
	}
	
	/**
	 * Returns {@code (this ^ observer)} LogicalCompution instance.
	 * 
	 * @param observer the BooleanObserver
	 * @return {@code (this ^ observer)} LogicalCompution instance
	 * @see #xor(boolean)
	 * @see LogicalCompution#xor(BooleanObservable, BooleanObservable)
	 */
	default public LogicalCompution xor(BooleanObservable observer) {
		return LogicalCompution.xor(this, observer);
	}
	
	/**
	 * Returns {@code (! (this && observer))} LogicalCompution instance.
	 * 
	 * @param observer the BooleanObserver
	 * @return (! {@code (this && observer))} LogicalCompution instance
	 * @see LogicalCompution#nand(BooleanObservable...)
	 * 
	 */
	default public LogicalCompution nand(BooleanObservable observer) {
		return LogicalCompution.nand(this, observer);
	}
	
	/**
	 * Returns {@code (! (this || observer))}aaa LogicalCompution instance.
	 * 
	 * @param observer the BooleanObserver
	 * @return (! {@code (this || observer))} LogicalCompution instance.
	 * @see #nor(boolean)
	 * @see LogicalCompution#nor(BooleanObservable...)
	 */
	default public LogicalCompution nor(BooleanObservable observer) {
		return LogicalCompution.nor(this, observer);
	}
	
	/**
	 * Returns {@code (this && f)} LogicalCompution instance.
	 * 
	 * @param f the boolean
	 * @return {@code (this && f)} LogicalCompution instance
	 * @see #and(BooleanObservable)
	 * @see #and(BooleanObservable)
	 * @see LogicalCompution#and(BooleanObservable...)
	 */
	default public LogicalCompution and(boolean f) {
		return this.and(BooleanUtils.getUnmodifiableBoolean(f));
	}
	
	/**
	 * Returns {@code (this || f)} LogicalCompution instance.
	 * 
	 * @param f the boolean
	 * @return {@code (this || f)} LogicalCompution instance
	 * @see #or(BooleanObservable)
	 * @see LogicalCompution#or(BooleanObservable...)
	 */
	default public LogicalCompution or(boolean f) {
		return this.or(BooleanUtils.getUnmodifiableBoolean(f));
	}
	
	/**
	 * Returns {@code (this ^ f)} LogicalCompution instance.
	 * 
	 * @param f the boolean value
	 * @return {@code (this ^ f)} LogicalCompution instance
	 * @see #xor(BooleanObservable)
	 * @see LogicalCompution#xor(BooleanObservable, BooleanObservable)
	 */
	default public LogicalCompution xor(boolean f) {
		return this.xor(BooleanUtils.getUnmodifiableBoolean(f));
	}
	
	/**
	 * Returns {@code (! (this && f))} LogicalCompution instance.
	 * 
	 * @param f the boolean value
	 * @return {@code (! (this && f))} LogicalCompution instance
	 * @see #nand(BooleanObservable)
	 * @see LogicalCompution#nand(BooleanObservable...)
	 */
	default public LogicalCompution nand(boolean f) {
		return this.nand(BooleanUtils.getUnmodifiableBoolean(f));
	}
	
	/**
	 * Returns {@code (! (this || f))} LogicalCompution instance.
	 * 
	 * @param f the boolean value
	 * @return {@code (! (this || f))} LogicalCompution instance.
	 * @see #nor(BooleanObservable)
	 * @see LogicalCompution#nor(BooleanObservable...)
	 */
	default public LogicalCompution nor(boolean f) {
		return this.nor(BooleanUtils.getUnmodifiableBoolean(f));
	}
	
	/**
	 * Waiting until {@code (condtion == this.booleanValue())}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param condition the boolean value
	 * @throws InterruptedException if interrupted while waiting
	 * @see #waitUntil(boolean, long, TimeUnit)
	 */
	public void waitUntil(boolean condition) throws InterruptedException;
	
	/**
	 * Waiting until {@code (condition == this.booleanValue())}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * 
	 * @param condition the boolean value
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see #waitUntil(boolean)
	 */
	public void waitUntil(boolean condition, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException;
	
	/**
	 * Waiting until {@code (condition == this.booleanValue())}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param condition the boolean value
	 * @param p the TimeoutProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see #waitUntil(boolean, long, TimeUnit)
	 */
	default public void waitUntil(boolean condition, TimeoutGettable p) throws InterruptedException, TimeoutException {
		final TimeoutAndUnit a = p.get();
		this.waitUntil(condition, a.timeout(), a.unit());
	}
	
	/**
	 * Waiting until {@code (this.booleanValue() == true)}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already this.value is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @throws InterruptedException if interrupted while waiting
	 * @see #waitUntil(boolean)
	 * #see #waitUntilFalse()
	 */
	default public void waitUntilTrue() throws InterruptedException {
		this.waitUntil(true);
	}
	
	/**
	 * Waiting until {@code (this.booleanValue() == true)}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already this.value is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see #waitUntil(boolean, long, TimeUnit)
	 * @see #waitUntilFalse(long, TimeUnit)
	 */
	default public void waitUntilTrue(long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		this.waitUntil(true, timeout, unit);
	}
	
	/**
	 * Waiting until {@code (this.booleanValue() == true)}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already this.value is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param p the TimeoutProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see #waitUntil(boolean, TimeoutGettable)
	 * @see #waitUntilFalse(TimeoutGettable)
	 */
	default public void waitUntilTrue(TimeoutGettable p) throws InterruptedException, TimeoutException {
		this.waitUntil(true, p);
	}
	
	/**
	 * Waiting until {@code (this.booleanValue() == false)}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already this.value is false, pass through immediately.<br />
	 * </p>
	 * 
	 * @throws InterruptedException if interrupted while waiting
	 * @see #waitUntil(boolean)
	 * @see #waitUntilTrue()
	 */
	default public void waitUntilFalse() throws InterruptedException {
		this.waitUntil(false);
	}
	
	/**
	 * Waiting until {@code (this.booleanValue() == false)}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already this.value is false, pass through immediately.<br />
	 * </p>
	 * 
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see #waitUntil(boolean, long, TimeUnit)
	 * @see #waitUntilTrue(long, TimeUnit)
	 */
	default public void waitUntilFalse(long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		this.waitUntil(false, timeout, unit);
	}
	
	/**
	 * Waiting until {@code (this.booleanValue() == false)}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already this.value is false, pass through immediately.<br />
	 * </p>
	 * 
	 * @param p the TimeoutProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see #waitUntil(boolean, TimeoutGettable)
	 * @see #waitUntilTrue(TimeoutGettable)
	 */
	default public void waitUntilFalse(TimeoutGettable p) throws InterruptedException, TimeoutException {
		this.waitUntil(false, p);
	}
	
}
