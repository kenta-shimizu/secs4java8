package com.shimizukenta.secs.local.property;

import com.shimizukenta.secs.local.property.impl.AbstractIntegerProperty;

/**
 * Integer value Property, includes Getter, Setter, Observer.
 * 
 * <ul>
 * <li>To build instance, {@link #newInstance(int)}.</li>
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
 * <li>To set value, {@link #set(int)}</li>
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
 * 
 * @author kenta-shimizu
 * @see NumberProperty
 * @see IntegerSettable
 * @see Integer
 *
 */
public interface IntegerProperty extends NumberProperty<Integer>, IntegerSettable {
	
	/**
	 * IntegerProperty builder.
	 * 
	 * @param initial is int
	 * @return new-instance
	 */
	public static IntegerProperty newInstance(int initial) {
		return new AbstractIntegerProperty(initial) {
			
			private static final long serialVersionUID = 3586226547691845115L;
		};
	}
	
}
