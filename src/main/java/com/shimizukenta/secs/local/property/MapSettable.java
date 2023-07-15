package com.shimizukenta.secs.local.property;

import java.util.Map;

/**
 * Map value Setter.
 * 
 * @author kenta-shimizu
 *
 * @param <K> Key Type
 * @param <V> Value Type
 * @see Map
 * @see Settable
 */
public interface MapSettable<K, V> extends Settable<Map<K, V>>{
	
}
