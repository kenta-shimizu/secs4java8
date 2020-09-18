package com.shimizukenta.secs;

/**
 * String value Getter, Setter, Value-Change-Observer<br />
 * 
 * @author kenta-shimizu
 *
 */
public class StringProperty extends AbstractProperty<String> {
	
	private static final long serialVersionUID = 407471202314199210L;
	
	public StringProperty(CharSequence initial) {
		super(initial == null ? null : initial.toString());
	}
	
	/**
	 * setter
	 * 
	 * @param cs
	 */
	public void set(CharSequence cs) {
		super.set(cs == null ? null : cs.toString());
	}
	
}
