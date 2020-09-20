package com.shimizukenta.secs;

/**
 * Getter, Setter, Value-Change-Observer
 * 
 * @author kenta-shimizu
 *
 * @param <T>
 */
public interface Property<T> extends ReadOnlyProperty<T>, WritableValue<T> {
	
	public static <T> Property<T> newInstance(T v) {
		
		return new AbstractProperty<T>(v) {
			private static final long serialVersionUID = 1L;
		};
	}
}
