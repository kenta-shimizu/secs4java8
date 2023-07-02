package com.shimizukenta.secs.local.property;

/**
 * Float value Compution, includes Getter, Observer.
 * 
 * <p>
 * <strong>NOT</strong> includes Setter.<br />
 * </p>
 * <p>
 * This instance is built from other Property or Compution.<br />
 * </p>
 * <ul>
 * <li>To get value,
 * <ul>
 * <li>{@link #byteValue()}</li>
 * <li>{@link #shortValue()}</li>
 * <li>{@link #intValue()}</li>
 * <li>{@link #longValue()}</li>
 * <li>{@link #floatValue()}</li>
 * <li>{@link #doubleValue()}</li>
 * </ul>
 * </li>
 * <li>To detect value changed, {@link #addChangeListener(ChangeListener)}</li>
 * <li>To number-compute,
 * <ul>
 * <li>{@link #add(NumberObservable)}</li>
 * <li>{@link #subtract(NumberObservable)}</li>
 * <li>{@link #multiply(NumberObservable)}</li>
 * <li>{@link #negate()}.</li>
 * </ul>
 * </li>
 * <li>To comparative-compute,
 * <ul>
 * <li>{@link #computeIsEqualTo(NumberObservable)}</li>
 * <li>{@link #computeIsNotEqualTo(NumberObservable)}</li>
 * <li>{@link #computeIsLessThan(NumberObservable)}</li>
 * <li>{@link #computeIsGreaterThan(NumberObservable)}</li>
 * <li>{@link #computeIsLessThanOrEqualTo(NumberObservable)}</li>
 * <li>{@link #computeIsGreaterThanOrEqualTo(NumberObservable)}</li>
 * </ul>
 * <li>To wait until condition is true,
 * <ul>
 * <li>{@link #waitUntilEqualTo(NumberObservable)}</li>
 * <li>{@link #waitUntilNotEqualTo(NumberObservable)}</li>
 * <li>{@link #waitUntilLessThan(NumberObservable)}</li>
 * <li>{@link #waitUntilGreaterThan(NumberObservable)}</li>
 * <li>{@link #waitUntilLessThanOrEqualTo(NumberObservable)}</li>
 * <li>{@link #waitUntilGreaterThanOrEqualTo(NumberObservable)}</li>
 * </ul>
 * </li>
 * </ul>

 * @author kenta-shimizu
 * @see NumberCompution
 * @see Float
 *
 */
public interface FloatCompution extends NumberCompution {

}
