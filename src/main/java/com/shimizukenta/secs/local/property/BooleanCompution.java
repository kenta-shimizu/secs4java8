package com.shimizukenta.secs.local.property;

/**
 * Boolean value compution, includes Getter and Observer.
 * 
 * <p>
 * <strong>NOT</strong> includes Setter.<br />
 * </p>
 * <p>
 * This instance is built from other Property or Compution.<br />
 * </p>
 * <ul>
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
 * @see BooleanGettable
 * @see BooleanObservable
 * @see Compution
 * @see BooleanProperty
 *
 */
public interface BooleanCompution extends Compution<Boolean>, BooleanGettable, BooleanObservable {
	
}
