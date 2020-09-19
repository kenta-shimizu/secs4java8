package com.shimizukenta.secs;

/**
 * Number value Getter, Setter, Value-Change-Observer<br />
 * Not accept null.
 * 
 * @author shimizukenta
 *
 */
public class SimpleNumberProperty extends AbstractNumberProperty {
	
	private static final long serialVersionUID = 2485714842556975640L;
	
	public SimpleNumberProperty(Number initial) {
		super(initial);
	}

	public SimpleNumberProperty(int initial) {
		super(initial);
	}

	public SimpleNumberProperty(long initial) {
		super(initial);
	}

	public SimpleNumberProperty(float initial) {
		super(initial);
	}

	public SimpleNumberProperty(double initial) {
		super(initial);
	}
	
}
