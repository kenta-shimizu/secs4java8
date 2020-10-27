package com.shimizukenta.secs;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Collection value Getter, Setter, Value-Change-Observer.
 * 
 * @author kenta-shimizu
 *
 * @param <T>
 */
public interface CollectionProperty<T>
		extends ReadOnlyCollectionProperty<T>,
		WritableCollectionValue<T> {
	
	public static <T> CollectionProperty<T> newList() {
		
		return new AbstractCollectionProperty<T>(new ArrayList<>()) {
			private static final long serialVersionUID = -2632352478183516719L;
		};
	}
	
	public static <T> CollectionProperty<T> newSet() {
		
		return new AbstractCollectionProperty<T>(new HashSet<>()) {
			private static final long serialVersionUID = -2680409238179687587L;
		};
	}
	
}
