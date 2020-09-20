package com.shimizukenta.secs;

/**
 * Number value Getter, Setter, Value-Change-Observer<br />
 * Not accept null.
 * 
 * @author kenta-shimizu
 *
 */
public interface NumberProperty extends Property<Number>, ReadOnlyNumberProperty, WritableNumberValue {
	
	public static NumberProperty newInstance(Number initial) {
		
		return new AbstractNumberProperty(initial) {
			private static final long serialVersionUID = 8783334196281463984L;
		};
	}
	
	public static NumberProperty newInstance(int initial) {
		return newInstance(Integer.valueOf(initial));
	}
	
	public static NumberProperty newInstance(long initial) {
		return newInstance(Long.valueOf(initial));
	}
	
	public static NumberProperty newInstance(float initial) {
		return newInstance(Float.valueOf(initial));
	}
	
	public static NumberProperty newInstance(double initial) {
		return newInstance(Double.valueOf(initial));
	}
	
}
