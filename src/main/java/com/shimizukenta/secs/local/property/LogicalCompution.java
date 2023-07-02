package com.shimizukenta.secs.local.property;

import java.util.Arrays;
import java.util.Collection;

import com.shimizukenta.secs.local.property.impl.BooleanUtils;

/**
 * Logical Boolean compution, includes Getter, Observer.
 * 
 * <p>
 * <strong>NOT</strong> includes Setter.<br />
 * </p>
 * <ul>
 * <li>To build LogicalCompution instance,
 * <ul>
 * <li>AND{@code (&&)} Compution, {@link #and(BooleanObservable...)}.</li>
 * <li>OR{@code (||)} Compution, {@link #or(BooleanObservable...)}.</li>
 * <li>NOT{@code (!)} Compution, {@link #not(BooleanObservable)}.</li>
 * <li>XOR{@code (^)} Compution, {@link #xor(BooleanObservable, BooleanObservable)}.</li>
 * <li>NAND Compution, {@link #nand(BooleanObservable...)}.</li>
 * <li>NOR Compution, {@link #nor(BooleanObservable...)}.</li>
 * </ul>
 * </li>
 * <li>To get value, {@link #booleanValue()}.</li>
 * <li>To detect value changed, {@link #addChangeListener(ChangeListener)}.</li>
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
 * @see BooleanCompution
 * @see Boolean
 *
 */
public interface LogicalCompution extends BooleanCompution {
	
	/**
	 * Returns AND{@code (&&)} Logical Compution instance.
	 * 
	 * @param observers is BooleanObserver
	 * @return AND{@code (&&}) Logical Compution instance
	 * @see #and(Collection)
	 */
	public static LogicalCompution and(BooleanObservable... observers) {
		return and(Arrays.asList(observers));
	}
	
	/**
	 * Returns AND{@code (&&)} Logical Compution instance.
	 * 
	 * @param observers is BooleanObservers
	 * @return AND{@code (&&)} Logical Compution instance
	 * @see #and(BooleanObservable...)
	 */
	public static LogicalCompution and(Collection<? extends BooleanObservable> observers) {
		return BooleanUtils.and(observers);
	}
	
	/**
	 * Returns OR{@code (||)} Logical Compution instance.
	 * 
	 * @param observers is BooleanObservers
	 * @return OR{@code (||)} Logical Compution instance
	 * @see #or(Collection)
	 */
	public static LogicalCompution or(BooleanObservable... observers) {
		return or(Arrays.asList(observers));
	}
	
	/**
	 * Returns OR{@code (||)} Logical Compution instance.
	 * 
	 * @param observers is BooleanObservers
	 * @return OR{@code (||)} Logical Compution instance
	 * @see #or(BooleanObservable...)
	 */
	public static LogicalCompution or(Collection<? extends BooleanObservable> observers) {
		return BooleanUtils.or(observers);
	}
	
	/**
	 * Returns NOT{@code (!)} Logical Compution instance.
	 * 
	 * @param observer is BooleanObserver
	 * @return NOT{@code (!)} Logical Compution instance
	 */
	public static LogicalCompution not(BooleanObservable observer) {
		return BooleanUtils.not(observer);
	}
	
	/**
	 * Returns XOR{@code (^)} Logical Compution instance.
	 * 
	 * @param a is BooleanObserver
	 * @param b is BooleanObserver
	 * @return XOR{@code (^)} Logical Compution instance
	 */
	public static LogicalCompution xor(BooleanObservable a, BooleanObservable b) {
		return BooleanUtils.xor(a, b);
	}
	
	/**
	 * Returns NAND Logical Compution instance.
	 * 
	 * <p>
	 * Returns NOT(AND) compution.<br />
	 * </p>
	 * 
	 * @param observers is BooleanObservers
	 * @return NAND Logical Compution instance
	 * @see #nand(Collection)
	 */
	public static LogicalCompution nand(BooleanObservable... observers) {
		return nand(Arrays.asList(observers));
	}
	
	/**
	 * Returns NAND Logical Compution instance.
	 * 
	 * <p>
	 * Returns NOT(AND) compution.<br />
	 * </p>
	 * 
	 * @param observers is BooleanObservers
	 * @return NAND Logical Compution instance
	 * @see #nand(BooleanObservable...)
	 */
	public static LogicalCompution nand(Collection<? extends BooleanObservable> observers) {
		return BooleanUtils.nand(observers);
	}
	
	/**
	 * Returns NOR Logical Compution instance.
	 * 
	 * <p>
	 * Returns NOT(OR) compution.<br />
	 * </p>
	 * 
	 * @param observers is BooleanObservers
	 * @return NOR Logical Compution instance
	 * @see #nor(Collection)
	 */
	public static LogicalCompution nor(BooleanObservable... observers) {
		return nor(Arrays.asList(observers));
	}
	
	/**
	 * Returns NOR Logical Compution instance
	 * 
	 * <p>
	 * Returns NOT(OR) compution.<br />
	 * </p>
	 * 
	 * @param observers is BooleanObservers
	 * @return NOR Logical Compution instance.
	 * @see #nor(BooleanObservable...)
	 */
	public static LogicalCompution nor(Collection<? extends BooleanObservable> observers) {
		return BooleanUtils.nor(observers);
	}
	
}
