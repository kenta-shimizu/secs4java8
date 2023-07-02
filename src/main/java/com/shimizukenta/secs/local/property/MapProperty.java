package com.shimizukenta.secs.local.property;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.shimizukenta.secs.local.property.impl.AbstractMapProperty;

/**
 * Map value Property, includes Getter, Setter, Observer.
 * 
 * <ul>
 * <li>To build instance, {@link #newInstance()}</li>
 * <li>To detect value changed, {@link #addChangeListener(ChangeListener)}</li>
 * <li>To compute,
 * <ul>
 * <li>{@link #computeIsEmpty()}</li>
 * <li>{@link #computeIsNotEmpty()}</li>
 * <li>{@link #computeContainsKey(Object)}</li>
 * <li>{@link #computeNotContainsKey(Object)}</li>
 * <li>{@link #computeKeySet()}</li>
 * <li>{@link #computeSize()}</li>
 * </ul>
 * </li>
 * <li>To wait until condition is true,
 * <ul>
 * <li>{@link #waitUntilIsEmpty()}</li>
 * <li>{@link #waitUntilIsNotEmpty()}</li>
 * <li>{@link #waitUntilContainsKeyAndGet(Object)}</li>
 * <li>{@link #waitUntilNotContainsKey(Object)}</li>
 * </ul>
 * </li>
 * </ul>
 * 
 * @author kenta-shimizu
 *
 * @param <K> Key Type
 * @param <V> Value Type
 * @see MapSettable
 * @see MapObservable
 * @see Map
 * 
 */
public interface MapProperty<K, V> extends Map<K, V>, MapSettable<K, V>, MapObservable<K, V>, Serializable {
	
	/**
	 * Instance builder.
	 * 
	 * @param <K> Key Type
	 * @param <V> Value Type
	 * @return new-instance.
	 */
	public static <K, V> MapProperty<K, V> newInstance() {
		return newInstance(Collections.emptyMap());
	}
	
	/**
	 * Instance builder.
	 * 
	 * @param <K> Key Type
	 * @param <V> Value Type
	 * @param initial the initial {@code Map<K, V>}
	 * @return new-instance.
	 */
	public static <K, V> MapProperty<K, V> newInstance(Map<? extends K, ? extends V> initial) {
		return new AbstractMapProperty<K, V>(new HashMap<K, V>(initial)){

			private static final long serialVersionUID = -1165018901559222491L;
		};
	}
	
}
