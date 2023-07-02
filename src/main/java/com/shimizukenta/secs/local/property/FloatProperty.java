/**
 * 
 */
package com.shimizukenta.secs.local.property;

import com.shimizukenta.secs.local.property.impl.AbstractFloatProperty;

/**
 * Float value Property, includes Getter, Setter, Observer.
 * 
 * <ul>
 * <li>To build instance, {@link #newInstance(float)}</li>
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
 * <li>To set value, {@link #set(float)}</li>
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
 * @see FloatSettable
 * @see Float
 *
 */
public interface FloatProperty extends NumberProperty<Float>, FloatSettable {
	
	/**
	 * FloatProperty builder.
	 * 
	 * @param initial the float value
	 * @return new-instance
	 */
	public static FloatProperty newInstance(float initial) {
		return new AbstractFloatProperty(initial) {

			private static final long serialVersionUID = 8573727566044779674L;
		};
	}
	
}
