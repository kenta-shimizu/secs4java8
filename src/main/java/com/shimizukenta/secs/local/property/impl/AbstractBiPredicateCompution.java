package com.shimizukenta.secs.local.property.impl;

import java.util.Objects;
import java.util.function.BiPredicate;

import com.shimizukenta.secs.local.property.ChangeListener;
import com.shimizukenta.secs.local.property.ComparativeCompution;
import com.shimizukenta.secs.local.property.Observable;

/**
 * 
 * @author kenta-shimizu
 *
 * @param <T> Type of left
 * @param <U> Type of right
 */
public abstract class AbstractBiPredicateCompution<T, U> extends AbstractBooleanCompution implements ComparativeCompution {
	
	private static final long serialVersionUID = -8029429736383936055L;
	
	/**
	 * Immutable BiPredicate.
	 */
	private final BiPredicate<? super T, ? super U> compute;
	
	/**
	 * Mutable left value.
	 */
	private T left;
	
	/**
	 * Mutable right value.
	 */
	private U right;
	
	/**
	 * Constructor.
	 * 
	 * @param compute is BiPredicate
	 * @param leftInitial is {@code <T>}
	 * @param rightInitial is {@code <U>}
	 */
	public AbstractBiPredicateCompution(
			BiPredicate<? super T, ? super U> compute,
			T leftInitial,
			U rightInitial) {
		
		super(Boolean.valueOf(compute.test(leftInitial, rightInitial)));
		
		this.compute = compute;
		this.left = leftInitial;
		this.right = rightInitial;
	}
	
	/**
	 * Constructor.
	 * 
	 * @param compute is BiPredicate
	 */
	public AbstractBiPredicateCompution(
			BiPredicate<? super T, ? super U> compute) {
		this(compute, null, null);
	}
	
	/**
	 * Left value set and notify if value changed.
	 * 
	 * @param v to Left
	 */
	protected void _leftChanged(T v) {
		synchronized ( this._sync ) {
			if (! Objects.equals(v, this.left)) {
				this.left = v;
				this._syncSetAndNotifyChanged(Boolean.valueOf(this.compute.test(this.left, this.right)));
			}
		}
	}
	
	/**
	 * Right value set and notify if value changed.
	 * 
	 * @param v to right
	 */
	protected void _rightChanged(U v) {
		synchronized ( this._sync ) {
			if (! Objects.equals(v, this.right)) {
				this.right = v;
				this._syncSetAndNotifyChanged(Boolean.valueOf(this.compute.test(this.left, this.right)));
			}
		}
	}
	
	/**
	 * Bind left listener.
	 */
	private final ChangeListener<T> leftLstnr = this::_leftChanged;
	
	/**
	 * Bind right listener.
	 */
	private final ChangeListener<U> rightLstnr = this::_rightChanged;
	
	/**
	 * To add listener to left observer.
	 * 
	 * @param observer to add listener to left
	 * @return true if bind success
	 */
	public boolean bindLeft(Observable<? extends T> observer) {
		return observer.addChangeListener(this.leftLstnr);
	}
	
	/**
	 * To remove listener to left observer.
	 * 
	 * @param observer to remove listener to left
	 * @return true if unbind success
	 */
	public boolean unbindLeft(Observable<? extends T> observer) {
		return observer.removeChangeListener(this.leftLstnr);
	}
	
	/**
	 * To add listener to right observer.
	 * 
	 * @param observer to add listener to right
	 * @return true if bind success
	 */
	public boolean bindRight(Observable<? extends U> observer) {
		return observer.addChangeListener(this.rightLstnr);
	}
	
	/**
	 * To remove listener to right observer.
	 * 
	 * @param observer to remove listener to right
	 * @return true if unbind success
	 */
	public boolean unbindRight(Observable<? extends U> observer) {
		return observer.removeChangeListener(this.rightLstnr);
	}
	
}
