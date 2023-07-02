package com.shimizukenta.secs.local.property;

import com.shimizukenta.secs.local.property.impl.NumberUtils;
import com.shimizukenta.secs.local.property.impl.ObjectUtils;
import com.shimizukenta.secs.local.property.impl.StringUtils;

/**
 * Comparative Boolean Compution, includes Getter, Observer.
 * 
 * <p>
 * <strong>NOT</strong> includes Setter.<br />
 * </p>
 * <ul>
 * <li>To build ComparativeCompution instance,
 * <ul>
 * <li>{@link #isEqualTo(NumberObservable, NumberObservable)}</li>
 * <li>{@link #isNotEqualTo(NumberObservable, NumberObservable)}</li>
 * <li>{@link #isLessThan(NumberObservable, NumberObservable)}</li>
 * <li>{@link #isLessThanOrEqualTo(NumberObservable, NumberObservable)}</li>
 * <li>{@link #isGreaterThan(NumberObservable, NumberObservable)}</li>
 * <li>{@link #isGreaterThanOrEqualTo(NumberObservable, NumberObservable)}</li>
 * <li>{@link #isEqualTo(ObjectObservable, ObjectObservable)}</li>
 * <li>{@link #isNotEqualTo(ObjectObservable, ObjectObservable)}</li>
 * <li>{@link #isEqualTo(StringObservable, StringObservable)}</li>
 * <li>{@link #isNotEqualTo(StringObservable, StringObservable)}</li>
 * <li>{@link #isLessThan(StringObservable, StringObservable)}</li>
 * <li>{@link #isLessThanOrEqualTo(StringObservable, StringObservable)}</li>
 * <li>{@link #isGreaterThan(StringObservable, StringObservable)}</li>
 * <li>{@link #isGreaterThanOrEqualTo(StringObservable, StringObservable)}</li>
 * </ul>
 * </li>
 * <li>To get value, {@link #booleanValue()}</li>
 * <li>To detect value changed, {@link #addChangeListener(ChangeListener)}</li>
 * <li>To wait until condition is true,
 * <ul>
 * <li>{@link #waitUntil(boolean)}</li>
 * <li>{@link #waitUntil(boolean, long, java.util.concurrent.TimeUnit)}</li>
 * </ul>
 * </lI>
 * <li>To logical-compute,
 * <ul>
 * <li>{@link #and(BooleanObservable)}</li>
 * <li>{@link #or(BooleanObservable)}</li>
 * <li>{@link #not()}</li>
 * <li>{@link #xor(BooleanObservable)}</li>
 * <li>{@link #nand(BooleanObservable)}</li>
 * <li>{@link #nor(BooleanObservable)}</li>
 * </ul>
 * </li>
 * </ul>
 * 
 * @author kenta-shimizu
 * @see Boolean
 * @see BooleanCompution
 *
 */
public interface ComparativeCompution extends BooleanCompution {
	
	/**
	 * Returns {@code (left == right)} ComparativeCompution instance.
	 * 
	 * @param left the left observer
	 * @param right the right observer
	 * @return {@code (left == right)} ComparativeCompution instance.
	 */
	public static ComparativeCompution isEqualTo(NumberObservable<? extends Number> left, NumberObservable<? extends Number> right) {
		return NumberUtils.isEqualTo(left, right);
	}
	
	/**
	 * Returns {@code (left != right)} ComparativeCompution instance.
	 * 
	 * @param left the left observer
	 * @param right the right observer
	 * @return {@code (left != right)} ComparativeCompution instance.
	 */
	public static ComparativeCompution isNotEqualTo(NumberObservable<? extends Number> left, NumberObservable<? extends Number> right) {
		return NumberUtils.isNotEqualTo(left, right);
	}
	
	/**
	 * Returns {@code (left < right)} ComparativeCompution instance.
	 * 
	 * @param left the left observer
	 * @param right the right observer
	 * @return {@code (left < right)} ComparativeCompution instance.
	 */
	public static ComparativeCompution isLessThan(NumberObservable<? extends Number> left, NumberObservable<? extends Number> right) {
		return NumberUtils.isLessThan(left, right);
	}
	
	/**
	 * Returns {@code (left <= right)} ComparativeCompution instance.
	 * 
	 * @param left the left observer
	 * @param right the right observer
	 * @return {@code (left <= right)} ComparativeCompution instance.
	 */
	public static ComparativeCompution isLessThanOrEqualTo(NumberObservable<? extends Number> left, NumberObservable<? extends Number> right) {
		return NumberUtils.isLessThanOrEqualTo(left, right);
	}
	
	/**
	 * Returns {@code (left > right)} ComparativeCompution instance.
	 * 
	 * @param left the left observer
	 * @param right the right observer
	 * @return {@code (left > right)} ComparativeCompution instance.
	 */
	public static ComparativeCompution isGreaterThan(NumberObservable<? extends Number> left, NumberObservable<? extends Number> right) {
		return NumberUtils.isGreaterThan(left, right);
	}
	
	/**
	 * Returns {@code (left >= right)} ComparativeCompution instance.
	 * 
	 * @param left the left observer
	 * @param right the right observer
	 * @return {@code (left >= right)} ComparativeCompution instance.
	 */
	public static ComparativeCompution isGreaterThanOrEqualTo(NumberObservable<? extends Number> left, NumberObservable<? extends Number> right) {
		return NumberUtils.isGreaterThanOrEqualTo(left, right);
	}
	
	/**
	 * Returns equal object ComparativeCompution instance.
	 * 
	 * @param <T> Type
	 * @param <U> Type
	 * @param a the observer of a
	 * @param b the observer of b
	 * @return Object EqualTo ComparativeCompution
	 */
	public static <T, U> ComparativeCompution isEqualTo(ObjectObservable<T> a, ObjectObservable<U> b) {
		return ObjectUtils.computeIsEqualTo(a, b);
	}
	
	/**
	 * Returns NOT equal object ComparativeCompution instance.
	 * 
	 * @param <T> Type
	 * @param <U> Type
	 * @param a the observer of a
	 * @param b the observer of b
	 * @return Object NotEqualTo ComparativeCompution
	 */
	public static <T, U> ComparativeCompution isNotEqualTo(ObjectObservable<T> a, ObjectObservable<U> b) {
		return ObjectUtils.computeIsNotEqualTo(a, b);
	}
	
	/**
	 * Returns equal String ComparativeCompution instance.
	 * 
	 * @param a the observer of a
	 * @param b the observer of b
	 * @return equal String ComparativeCompution instance
	 */
	public static ComparativeCompution isEqualTo(StringObservable a, StringObservable b) {
		return StringUtils.computeIsEqualTo(a, b);
	}
	
	/**
	 * Returns NOT equal String ComparativeCompution instance.
	 * 
	 * @param a the observer of a
	 * @param b the observer of b
	 * @return NOT equal String ComparativeCompution instance
	 */
	public static ComparativeCompution isNotEqualTo(StringObservable a, StringObservable b) {
		return StringUtils.computeIsNotEqualTo(a, b);
	}
	
	/**
	 * Returns {@code (left < right)} ComparativeCompution instance.
	 * 
	 * @param left the left observer
	 * @param right the right observer
	 * @return {@code (left < right)} ComparativeCompution instance
	 */
	public static ComparativeCompution isLessThan(StringObservable left, StringObservable right) {
		return StringUtils.computeIsLessThan(left, right);
	}
	
	/**
	 * Returns {@code (left <= right)} ComparativeCompution instance.
	 * 
	 * @param left the left observer
	 * @param right the right observer
	 * @return {@code (left <= right)} ComparativeCompution instance
	 */
	public static ComparativeCompution isLessThanOrEqualTo(StringObservable left, StringObservable right) {
		return StringUtils.computeIsLessThanOrEqualTo(left, right);
	}
	
	/**
	 * Returns {@code (left > right)} ComparativeCompution instance.
	 * @param left the left observer
	 * @param right the right observer
	 * @return {@code (left > right)} ComparativeCompution instance
	 */
	public static ComparativeCompution isGreaterThan(StringObservable left, StringObservable right) {
		return StringUtils.computeIsGreaterThan(left, right);
	}
	
	/**
	 * Returns {@code (left >= right)} ComparativeCompution instance.
	 * @param left the left observer
	 * @param right the right observer
	 * @return {@code (left >= right)} ComparativeCompution instance
	 */
	public static ComparativeCompution isGreaterThanOrEqualTo(StringObservable left, StringObservable right) {
		return StringUtils.computeIsGreaterThanOrEqualTo(left, right);
	}
	
	/**
	 * Returns {@code a.equalsIgnoreCase(b)} Comparative Compution instance.
	 * @param a the observer of a
	 * @param b the observer of b
	 * @return {@code a.equalsIgnoreCase(b)} Comparative Compution instance
	 */
	public static ComparativeCompution isEqualToIgnoreCase(StringObservable a, StringObservable b) {
		return StringUtils.computeIsEqualToIgnoreCase(a, b);
	}
	
	/**
	 * Returns {@code (! a.equalsIgnoreCase(b))} Comparative Compution instance.
	 * @param a the observer of a
	 * @param b the observer of b
	 * @return {@code (! a.equalsIgnoreCase(b))} Comparative Compution instance
	 */
	public static ComparativeCompution isNotEqualToIgnoreCase(StringObservable a, StringObservable b) {
		return StringUtils.computeIsNotEqualToIgnoreCase(a, b);
	}
	
}
