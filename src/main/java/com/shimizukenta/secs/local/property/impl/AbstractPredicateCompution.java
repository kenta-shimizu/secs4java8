package com.shimizukenta.secs.local.property.impl;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Predicate;

import com.shimizukenta.secs.local.property.ChangeListener;
import com.shimizukenta.secs.local.property.Observable;
import com.shimizukenta.secs.local.property.TimeoutGettable;

/**
 * 
 * @author kenta-shimizu
 *
 * @param <T> Type
 */
public abstract class AbstractPredicateCompution<T> extends AbstractBooleanCompution {
	
	private static final long serialVersionUID = -9033466111311749543L;
	
	/**
	 * Immutable Compute predicate.
	 */
	protected final Predicate<? super T> _compute;
	
	/**
	 * Last value.
	 */
	private T last;
	
	/**
	 * Constructor.
	 * 
	 * @param compute is {@code Predicate<? super T>}
	 * @param initial is {@code <T>}
	 */
	public AbstractPredicateCompution(
			Predicate<? super T> compute,
			T initial) {
		
		super(Boolean.valueOf(compute.test(initial)));
		
		this._compute = compute;
		this.last = initial;
	}
	
	/**
	 * Constructor.
	 * 
	 * @param compute is {@code Predicate<? super T>}
	 */
	public AbstractPredicateCompution(Predicate<? super T> compute) {
		this(compute, null);
	}
	
	/**
	 * Returns last value.
	 * 
	 * @return last-value
	 */
	public T getLastValue() {
		synchronized ( this._sync ) {
			return this.last;
		}
	}
	
	/**
	 * Notify if value changed.
	 * 
	 * @param v is value
	 */
	protected void changedValue(T v) {
		synchronized ( this._sync ) {
			if ( ! Objects.equals(v, this.last) ) {
				this.last = v;
				this._syncSetAndNotifyChanged(Boolean.valueOf(this._compute.test(v)));
			}
		}
	}
	
	/**
	 * Bind listener.
	 */
	private final ChangeListener<T> bindLstnr = this::changedValue;
	
	/**
	 * To add listener to observer.
	 * 
	 * @param observer to add listener
	 * @return true if bind success
	 */
	public boolean bind(Observable<? extends T> observer) {
		return observer.addChangeListener(this.bindLstnr);
	}
	
	/**
	 * To remove listener to observer.
	 * 
	 * @param observer to remove listener
	 * @return true if unbind success
	 */
	public boolean unbind(Observable<? extends T> observer) {
		return observer.removeChangeListener(this.bindLstnr);
	}
	
	/**
	 * Waiting until {@code (condtion == this.booleanValue())}, and return last value.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, return last value immediately.<br />
	 * </p>
	 * 
	 * @param condition is boolean.
	 * @return last value
	 * @throws InterruptedException if interrupted while waiting
	 */
	public T waitUntilAndGet(boolean condition) throws InterruptedException {
		synchronized ( this._sync ) {
			this.waitUntil(condition);
			return this.getLastValue();
		}
	}
	
	/**
	 * Waiting until {@code (condtion == this.booleanValue())}, and return last value.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, return last value immediately.<br />
	 * </p>
	 * 
	 * @param condition is boolean.
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @return last value
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	public T waitUntilAndGet(boolean condition, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		synchronized ( this._sync ) {
			this.waitUntil(condition, timeout, unit);
			return this.getLastValue();
		}
	}
	
	/**
	 * Waiting until {@code (condtion == this.booleanValue())}, and return last value.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, return last value immediately.<br />
	 * </p>
	 * 
	 * @param condition is boolean.
	 * @param p is TimeoutProperty
	 * @return last value
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	public T waitUntilAndGet(boolean condition, TimeoutGettable p) throws InterruptedException, TimeoutException {
		synchronized ( this._sync ) {
			this.waitUntil(condition, p);
			return this.getLastValue();
		}
	}
	
	/**
	 * Waiting until {@code (this.booleanValue() == true)}, and return last value.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, return last value immediately.<br />
	 * </p>
	 * 
	 * @return last value
	 * @throws InterruptedException if interrupted while waiting
	 */
	public T waitUntilTrueAndGet() throws InterruptedException {
		return this.waitUntilAndGet(true);
	}
	
	/**
	 * Waiting until {@code (this.booleanValue() == true)}, and return last value.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, return last value immediately.<br />
	 * </p>
	 * 
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @return last value
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	public T waitUntilTrueAndGet(long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		return this.waitUntilAndGet(true, timeout, unit);
	}
	
	/**
	 * Waiting until {@code (this.booleanValue() == true)}, and return last value.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, return last value immediately.<br />
	 * </p>
	 * 
	 * @param p is TimeoutProperty
	 * @return last value
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	public T waitUntilTrueAndGet(TimeoutGettable p) throws InterruptedException, TimeoutException {
		return this.waitUntilAndGet(true, p);
	}
	
	/**
	 * Waiting until {@code (this.booleanValue() == false)}, and return last value.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, return last value immediately.<br />
	 * </p>
	 * 
	 * @return last value
	 * @throws InterruptedException if interrupted while waiting
	 */
	public T waitUntilFalseAndGet() throws InterruptedException {
		return this.waitUntilAndGet(false);
	}
	
	/**
	 * Waiting until {@code (this.booleanValue() == false)}, and return last value.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, return last value immediately.<br />
	 * </p>
	 * 
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @return last value
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	public T waitUntilFalseAndGet(long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		return this.waitUntilAndGet(false, timeout, unit);
	}
	
	/**
	 * Waiting until {@code (this.booleanValue() == false)}, and return last value.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, return last value immediately.<br />
	 * </p>
	 * 
	 * @param p is TimeoutProperty
	 * @return last value
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	public T waitUntilFalseAndGet(TimeoutGettable p) throws InterruptedException, TimeoutException {
		return this.waitUntilAndGet(false, p);
	}

}
