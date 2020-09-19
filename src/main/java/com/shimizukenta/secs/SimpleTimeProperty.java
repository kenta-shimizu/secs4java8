package com.shimizukenta.secs;

/**
 * Time value Getter, Setter, Value-Change-Observer<br />
 * Not accept null.
 * 
 * @author kenta-shimizu
 *
 */
public class SimpleTimeProperty extends AbstractTimeProperty {
	
	private static final long serialVersionUID = -8362988595969736936L;
	
	public SimpleTimeProperty(Number initial) {
		super(initial);
	}

	public SimpleTimeProperty(int initial) {
		super(initial);
	}

	public SimpleTimeProperty(long initial) {
		super(initial);
	}

	public SimpleTimeProperty(float initial) {
		super(initial);
	}

	public SimpleTimeProperty(double initial) {
		super(initial);
	}

}
