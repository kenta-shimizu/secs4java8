package com.shimizukenta.secs.local.property;

import java.util.Arrays;
import java.util.Collection;

import com.shimizukenta.secs.local.property.impl.NumberUtils;

/**
 * Number value Compution, includes Getter, Observer.
 * 
 * <p>
 * <strong>NOT</strong> includes Setter.<br />
 * </p>
 * <ul>
 * <li>To build NumberCompution instance,
 * <ul>
 * <li>{@link #sum(Collection)}.</li>
 * <li>{@link #max(Collection)}.</li>
 * <li>{@link #min(Collection)}.</li>
 * <li>{@link #multiply(Collection)}.</li>
 * <li>{@link #subtract(NumberObservable, NumberObservable)}.</li>
 * <li>{@link #negate(NumberObservable)}.</li>
 * </ul>
 * </li>
 * <li>To get value,
 * <ul>
 * <li>{@link #byteValue()}.</li>
 * <li>{@link #shortValue()}.</li>
 * <li>{@link #intValue()}.</li>
 * <li>{@link #longValue()}.</li>
 * <li>{@link #floatValue()}.</li>
 * <li>{@link #doubleValue()}.</li>
 * </ul>
 * </li>
 * <li>To detect value changed, {@link #addChangeListener(ChangeListener)}.</li>
 * <li>To number-compute,
 * <ul>
 * <li>{@link #add(NumberObservable)}.</li>
 * <li>{@link #subtract(NumberObservable)}.</li>
 * <li>{@link #multiply(NumberObservable)}.</li>
 * <li>{@link #negate()}.</li>
 * </ul>
 * </li>
 * <li>To comparative-compute,
 * <ul>
 * <li>{@link #computeIsEqualTo(NumberObservable)}.</li>
 * <li>{@link #computeIsNotEqualTo(NumberObservable)}.</li>
 * <li>{@link #computeIsLessThan(NumberObservable)}.</li>
 * <li>{@link #computeIsGreaterThan(NumberObservable)}.</li>
 * <li>{@link #computeIsLessThanOrEqualTo(NumberObservable)}.</li>
 * <li>{@link #computeIsGreaterThanOrEqualTo(NumberObservable)}.</li>
 * </ul>
 * <li>To wait until condition is true,
 * <ul>
 * <li>{@link #waitUntilEqualTo(NumberObservable)}.</li>
 * <li>{@link #waitUntilNotEqualTo(NumberObservable)}.</li>
 * <li>{@link #waitUntilLessThan(NumberObservable)}.</li>
 * <li>{@link #waitUntilGreaterThan(NumberObservable)}.</li>
 * <li>{@link #waitUntilLessThanOrEqualTo(NumberObservable)}.</li>
 * <li>{@link #waitUntilGreaterThanOrEqualTo(NumberObservable)}.</li>
 * </ul>
 * </li>
 * </ul>
 * 
 * @author kenta-shimizu
 * @see Number
 * @see Compution
 * @see NumberGettable
 * @see NumberObservable
 *
 */
public interface NumberCompution extends Compution<Number>, NumberGettable<Number>, NumberObservable<Number> {
	
	/**
	 * Returns NumberCompution instance of sum operation.
	 * 
	 * @param a is NumberObserver
	 * @param b is NumberObserver
	 * @return NumberCompution instance of sum operation.
	 */
	public static NumberCompution sum(
			NumberObservable<? extends Number> a,
			NumberObservable<? extends Number> b) {
		
		return sum(Arrays.asList(a, b));
	}
	
	/**
	 * Returns NumberCompution instance of sum operation.
	 * 
	 * @param a is NumberObserver
	 * @param b is NumberObserver
	 * @param c is NumberObserver
	 * @return NumberCompution instance of sum operation.
	 */
	public static NumberCompution sum(
			NumberObservable<? extends Number> a,
			NumberObservable<? extends Number> b,
			NumberObservable<? extends Number> c) {
		
		return sum(Arrays.asList(a, b, c));
	}
	
	/**
	 * Returns NumberCompution instance of sum operation.
	 * 
	 * @param a is NumberObserver
	 * @param b is NumberObserver
	 * @param c is NumberObserver
	 * @param d is NumberObserver
	 * @return NumberCompution instance of sum operation.
	 */
	public static NumberCompution sum(
			NumberObservable<? extends Number> a,
			NumberObservable<? extends Number> b,
			NumberObservable<? extends Number> c,
			NumberObservable<? extends Number> d) {
		
		return sum(Arrays.asList(a, b, c, d));
	}
	
	/**
	 * Returns NumberCompution instance of sum operation.
	 * 
	 * @param observers is NumberObservers
	 * @return NumberCompution instance of sum operation.
	 */
	public static NumberCompution sum(Collection<? extends NumberObservable<? extends Number>> observers) {
		return NumberUtils.sum(observers);
	}
	
	/**
	 * Returns NumberCompution instance of multiply operation.
	 * 
	 * @param a is NumberObserver
	 * @param b is NumberObserver
	 * @return NumberCompution instance of multiply operation.
	 */
	public static NumberCompution multiply(
			NumberObservable<? extends Number> a,
			NumberObservable<? extends Number> b) {
		
		return multiply(Arrays.asList(a, b));
	}
	
	/**
	 * Returns NumberCompution instance of multiply operation.
	 * 
	 * @param a is NumberObserver
	 * @param b is NumberObserver
	 * @param c is NumberObserver
	 * @return NumberCompution instance of multiply operation.
	 */
	public static NumberCompution multiply(
			NumberObservable<? extends Number> a,
			NumberObservable<? extends Number> b,
			NumberObservable<? extends Number> c) {
		
		return multiply(Arrays.asList(a, b, c));
	}
	
	/**
	 * Returns NumberCompution instance of multiply operation.
	 * 
	 * @param a is NumberObserver
	 * @param b is NumberObserver
	 * @param c is NumberObserver
	 * @param d is NumberObserver
	 * @return NumberCompution instance of multiply operation.
	 */
	public static NumberCompution multiply(
			NumberObservable<? extends Number> a,
			NumberObservable<? extends Number> b,
			NumberObservable<? extends Number> c,
			NumberObservable<? extends Number> d) {
		
		return multiply(Arrays.asList(a, b, c, d));
	}
	
	/**
	 * Returns NumberCompution instance of multiply operation.
	 * 
	 * @param observers is NumberObservers
	 * @return NumberCompution instance of multiply operation.
	 */
	public static NumberCompution multiply(Collection<? extends NumberObservable<? extends Number>> observers) {
		return NumberUtils.multiply(observers);
	}
	
	/**
	 * Returns NumberCompution instance of negate operation.
	 * 
	 * @param observer is NumberObserver
	 * @return NumberCompution instance of negate operation.
	 */
	public static NumberCompution negate(NumberObservable<? extends Number> observer) {
		return NumberUtils.negate(observer);
	}
	
	/**
	 * Returns NumberCompution instance of subtract operation.
	 * 
	 * @param left is NumberObserver
	 * @param right is NumberObserver
	 * @return NumberCompution instance of subtract operation.
	 */
	public static NumberCompution subtract(NumberObservable<? extends Number> left, NumberObservable<? extends Number> right) {
		return NumberUtils.subtract(left, right);
	}
	
	/**
	 * Returns NumberCompution instance of max operation.
	 * 
	 * @param a is NumberObserver
	 * @param b is NumberObserver
	 * @return NumberCompution instance of max operation.
	 */
	public static NumberCompution max(
			NumberObservable<? extends Number> a,
			NumberObservable<? extends Number> b) {
		
		return max(Arrays.asList(a, b));
	}
	
	/**
	 * Returns NumberCompution instance of max operation.
	 * 
	 * @param a is NumberObserver
	 * @param b is NumberObserver
	 * @param c is NumberObserver
	 * @return NumberCompution instance of max operation.
	 */
	public static NumberCompution max(
			NumberObservable<? extends Number> a,
			NumberObservable<? extends Number> b,
			NumberObservable<? extends Number> c) {
		
		return max(Arrays.asList(a, b, c));
	}
	
	/**
	 * Returns NumberCompution instance of max operation.
	 * 
	 * @param a is NumberObserver
	 * @param b is NumberObserver
	 * @param c is NumberObserver
	 * @param d is NumberObserver
	 * @return NumberCompution instance of max operation.
	 */
	public static NumberCompution max(
			NumberObservable<? extends Number> a,
			NumberObservable<? extends Number> b,
			NumberObservable<? extends Number> c,
			NumberObservable<? extends Number> d) {
		
		return max(Arrays.asList(a, b, c, d));
	}
	
	/**
	 * Returns NumberCompution instance of max operation.
	 * 
	 * @param observers is NumberObservers
	 * @return NumberCompution instance of max operation.
	 */
	public static NumberCompution max(Collection<? extends NumberObservable<? extends Number>> observers) {
		return NumberUtils.max(observers);
	}
	
	/**
	 * Returns NumberCompution instance of min operation.
	 * 
	 * @param a is NumberObserver
	 * @param b is NumberObserver
	 * @return NumberCompution instance of min operation.
	 */
	public static NumberCompution min(
			NumberObservable<? extends Number> a,
			NumberObservable<? extends Number> b) {
		
		return min(Arrays.asList(a, b));
	}
	
	/**
	 * Returns NumberCompution instance of min operation.
	 * 
	 * @param a is NumberObserver
	 * @param b is NumberObserver
	 * @param c is NumberObserver
	 * @return NumberCompution instance of min operation.
	 */
	public static NumberCompution min(
			NumberObservable<? extends Number> a,
			NumberObservable<? extends Number> b,
			NumberObservable<? extends Number> c) {
		
		return min(Arrays.asList(a, b, c));
	}
	
	/**
	 * Returns NumberCompution instance of min operation.
	 * 
	 * @param a is NumberObserver
	 * @param b is NumberObserver
	 * @param c is NumberObserver
	 * @param d is NumberObserver
	 * @return NumberCompution instance of min operation.
	 */
	public static NumberCompution min(
			NumberObservable<? extends Number> a,
			NumberObservable<? extends Number> b,
			NumberObservable<? extends Number> c,
			NumberObservable<? extends Number> d) {
		
		return min(Arrays.asList(a, b, c, d));
	}
	
	/**
	 * Returns NumberCompution instance of min operation.
	 * 
	 * @param observers is NumberObservers
	 * @return NumberCompution instance of min operation.
	 */
	public static NumberCompution min(Collection<? extends NumberObservable<? extends Number>> observers) {
		return NumberUtils.min(observers);
	}
	
}
