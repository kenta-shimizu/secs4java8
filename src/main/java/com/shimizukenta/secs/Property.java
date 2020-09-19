package com.shimizukenta.secs;

/**
 * Getter, Setter, Value-Change-Observer
 * 
 * @author kenta-shimizu
 *
 * @param <T>
 */
public interface Property<T> extends ReadOnlyProperty<T>, WritableValue<T> {
	
}
