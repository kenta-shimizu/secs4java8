package com.shimizukenta.secs;

/**
 * String value Getter, Setter, Value-Change-Observer<br />
 * 
 * @author kenta-shimizu
 *
 */
public abstract class AbstractStringProperty extends AbstractProperty<String> implements WritableStringValue {
	
	private static final long serialVersionUID = -4106916755486494299L;
	
	public AbstractStringProperty(CharSequence initial) {
		super(initial == null ? null : initial.toString());
	}

	@Override
	public void set(CharSequence cs) {
		super.set(cs == null ? null : cs.toString());
	}

}
