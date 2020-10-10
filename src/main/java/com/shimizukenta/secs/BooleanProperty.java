package com.shimizukenta.secs;

/**
 * Boolean value Getter, Setter, Value-Change-Observer.
 * 
 * <p>
 * Not accept {@code null}.
 * </p>
 * 
 * @author kenta-shimizu
 *
 */
public interface BooleanProperty extends Property<Boolean>, ReadOnlyBooleanProperty, WritableBooleanValue {
	
	public static BooleanProperty newInstance(boolean initial) {
		
		return new AbstractBooleanProperty(initial) {
			private static final long serialVersionUID = 5083511805576401840L;
			
		};
	}
	
}
