package com.shimizukenta.secs;

/**
 * Time value Getter, Setter, Value-Change-Observer.
 * 
 * <p>
 * Not accept {@code null}.<br />
 * </p>
 * 
 * @author kenta-shimizu
 *
 */
public interface TimeProperty extends NumberProperty, ReadOnlyTimeProperty {
	
	public static TimeProperty newInstance(Number initial) {
		
		return new AbstractTimeProperty(initial) {
			private static final long serialVersionUID = -3059262099117306526L;
		};
	}
	
	public static TimeProperty newInstance(int initial) {
		return newInstance(Integer.valueOf(initial));
	}
	
	public static TimeProperty newInstance(long initial) {
		return newInstance(Long.valueOf(initial));
	}
	
	public static TimeProperty newInstance(float initial) {
		return newInstance(Float.valueOf(initial));
	}
	
	public static TimeProperty newInstance(double initial) {
		return newInstance(Double.valueOf(initial));
	}
	
}
