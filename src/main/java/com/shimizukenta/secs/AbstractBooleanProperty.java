package com.shimizukenta.secs;

import java.util.Objects;

/**
 * Boolean value Getter, Setter, Value-Change-Observer<br />
 * Not accept null.
 * 
 * @author kenta-shimizu
 *
 */
public abstract class AbstractBooleanProperty extends AbstractProperty<Boolean>
		implements ReadOnlyBooleanProperty, WritableBooleanValue {
	
	private static final long serialVersionUID = -1707441122073604603L;

	public AbstractBooleanProperty(boolean initial) {
		super(Boolean.valueOf(initial));
	}

	@Override
	public void set(Boolean v) {
		super.set(Objects.requireNonNull(v));
	}

	@Override
	public void set(boolean v) {
		super.set(Boolean.valueOf(v));
	}

	@Override
	public boolean booleanValue() {
		return get().booleanValue();
	}

	@Override
	public void waitUntilTrue() throws InterruptedException {
		waitUntil(Boolean.TRUE);
	}

	@Override
	public void waitUntilFalse() throws InterruptedException {
		waitUntil(Boolean.FALSE);
	}

}
