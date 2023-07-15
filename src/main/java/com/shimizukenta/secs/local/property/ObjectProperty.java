package com.shimizukenta.secs.local.property;

import com.shimizukenta.secs.local.property.impl.AbstractObjectProperty;

/**
 * Object value Property, includes Getter, Setter, Observer.
 * 
 * <ul>
 * <li>To build instance, {@link #newInstance(Object)}</li>
 * <li>To get value, {@link #get()}</li>
 * <li>To get Optional, {@link #optional()}</li>
 * <li>To set value, {@link #set(Object)}</li>
 * <li>To detect value changed, {@link #addChangeListener(ChangeListener)}</li>
 * <li>To compute,
 * <ul>
 * <li>{@link #computeIsEqualTo(ObjectObservable)}</li>
 * <li>{@link #computeIsNotEqualTo(ObjectObservable)}</li>
 * </ul>
 * </li>
 * <li>To wait until condition is true,
 * <ul>
 * <li>{@link #waitUntilEqualTo(ObjectObservable)}</li>
 * <li>{@link #waitUntilNotEqualTo(ObjectObservable)}</li>
 * <li>{@link #waitUntilNotNullAndGet()}</li>
 * <li>{@link #waitUntilNull()}</li>
 * </ul>
 * </li>
 * </ul>
 * 
 * @author kenta-shimizu
 *
 * @param <T> Type
 * @see ObjectGettable
 * @see ObjectSettable
 * @see ObjectObservable
 * @see Property
 * 
 */
public interface ObjectProperty<T> extends Property<T>, ObjectGettable<T>, ObjectSettable<T>, ObjectObservable<T> {
	
	/**
	 * Instance builder.
	 * 
	 * @param <T> Type
	 * @param initial the {@code <T>} value
	 * @return new-instance
	 */
	public static <T> ObjectProperty<T> newInstance(T initial) {
		return new AbstractObjectProperty<T>(initial) {
			
			private static final long serialVersionUID = 5265321993153340447L;
		};
	}
}
