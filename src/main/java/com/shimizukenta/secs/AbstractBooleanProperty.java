package com.shimizukenta.secs;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Boolean value Getter, Setter, Value-Change-Observer.
 * 
 * <p>
 * Not accept {@code null}
 * </p>
 * 
 * @author kenta-shimizu
 *
 */
public abstract class AbstractBooleanProperty extends AbstractProperty<Boolean>
		implements BooleanProperty {
	
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
		return this.get().booleanValue();
	}
	
	@Override
	public void waitUntilTrue() throws InterruptedException {
		this.waitUntil(Boolean.TRUE);
	}
	
	@Override
	public void waitUntilTrue(long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		this.waitUntil(Boolean.TRUE, timeout, unit);
	}
	
	@Override
	public void waitUntilTrue(ReadOnlyTimeProperty tp) throws InterruptedException, TimeoutException {
		this.waitUntil(Boolean.TRUE, tp);
	}
	
	@Override
	public void waitUntilFalse() throws InterruptedException {
		this.waitUntil(Boolean.FALSE);
	}
	
	@Override
	public void waitUntilFalse(long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		this.waitUntil(Boolean.FALSE, timeout, unit);
	}
	
	@Override
	public void waitUntilFalse(ReadOnlyTimeProperty tp) throws InterruptedException, TimeoutException {
		this.waitUntil(Boolean.FALSE, tp);
	}
	
}
