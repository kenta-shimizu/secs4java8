package com.shimizukenta.secs.local.property;

import com.shimizukenta.secs.local.property.impl.AbstractBooleanProperty;

/**
 * Boolean value Property, includes Getter, Setter, Observer.
 * 
 * <ul>
 * <li>To build instance, {@link #newInstance(boolean)}</li>
 * <li>To get value, {@link #booleanValue()}</li>
 * <li>To set value, {@link #set(boolean)}</li>
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
 * @see BooleanGettable
 * @see BooleanSettable
 * @see BooleanObservable
 * @see Property
 * @see BooleanCompution
 *
 */
public interface BooleanProperty extends Property<Boolean>, BooleanGettable, BooleanSettable, BooleanObservable {
	
	/**
	 * BooleanProperty builder.
	 * 
	 * @param initial the boolean value
	 * @return new-instance.
	 */
	public static BooleanProperty newInstance(boolean initial) {
		
		return new AbstractBooleanProperty(initial) {
			
			private static final long serialVersionUID = 6612758414318404939L;
		};
	}
}
