package com.shimizukenta.secs;

public class StringProperty extends AbstractProperty<String> {
	
	private static final long serialVersionUID = 407471202314199210L;
	
	public StringProperty(CharSequence initial) {
		super(initial == null ? null : initial.toString());
	}
	
	public void set(CharSequence cs) {
		super.set(cs == null ? null : cs.toString());
	}
	
}
