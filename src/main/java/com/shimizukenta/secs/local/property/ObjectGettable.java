package com.shimizukenta.secs.local.property;

import java.util.Optional;

/**
 * Object value Getter.
 * 
 * @author kenta-shimizu
 *
 * @param <T> Type
 * @see Gettable
 * 
 */
public interface ObjectGettable<T> extends Gettable<T> {
	
	/**
	 * Value getter.
	 * 
	 * @return value.
	 */
	public T get();
	
	/**
	 * Returns true if value is null, otherwise false.
	 * 
	 * @return true if value is null, otherwise false.
	 */
	default boolean isNull() {
		return this.get() == null;
	}
	
	/**
	 * Returns Optional value.
	 * 
	 * <p>
	 * Returns Optional#empty(), if value is null.
	 * </p>
	 * 
	 * @return Optional value.
	 * @see Optional
	 * @see Optional#of(Object)
	 * @see Optional#empty()
	 */
	default public Optional<T> optional() {
		T v = this.get();
		return v == null ? Optional.empty() : Optional.of(v);
	}
	
}
