package com.shimizukenta.secs;

/**
 * String value Getter, Setter, Value-Change-Observer.
 * 
 * @author kenta-shimizu
 *
 */
public interface StringProperty extends Property<String>, WritableStringValue {
	
	public static StringProperty newInstance(CharSequence initial) {
		
		return new AbstractStringProperty(initial) {
			private static final long serialVersionUID = -2385258740337484595L;
		};
	};
}
