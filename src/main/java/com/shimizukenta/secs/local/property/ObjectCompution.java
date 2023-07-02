package com.shimizukenta.secs.local.property;

/**
 * Object value Compution, includes Getter, Observer.
 * 
 * <p>
 * <strong>NOT</strong> includes Setter.<br />
 * </p>
 * <p>
 * This instance is built from other Property or Compution.<br />
 * </p>
 * <ul>
 * <li>To get value, {@link #get()}</li>
 * <li>To get Optional, {@link #optional()}</li>
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
 * @author kentashimizu
 *
 * @param <T> Type
 * @see ObjectGettable
 * @see ObjectObservable
 * @see Compution
 * 
 */
public interface ObjectCompution<T> extends Compution<T>, ObjectGettable<T>, ObjectObservable<T> {
	
}
