package com.shimizukenta.secs;

/**
 * Boolean value Getter, Setter, Value-Change-Observer<br />
 * Not accept null.
 * 
 * @author kenta-shimizu
 *
 */
public class SimpleBooleanProperty extends AbstractBooleanProperty {
	
	private static final long serialVersionUID = 7692767686764189808L;

	public SimpleBooleanProperty(boolean initial) {
		super(initial);
	}
	
}
