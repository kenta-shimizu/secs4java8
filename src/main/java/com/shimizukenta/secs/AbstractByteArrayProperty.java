package com.shimizukenta.secs;

import java.util.Objects;

/**
 * Bytes Array value Getter, Setter, Value-Change-Observer
 * 
 * <p>
 * Not accept {@code null}
 * </p>
 * 
 * @author kenta-shimizu
 *
 */
public abstract class AbstractByteArrayProperty extends AbstractProperty<byte[]>
		implements ByteArrayProperty {
	
	private static final long serialVersionUID = -3387589927771782849L;

	public AbstractByteArrayProperty(byte[] initial) {
		super(Objects.requireNonNull(initial));
	}
	
	
	/**
	 * Setter.
	 * 
	 * <p>
	 * Not accept {@code null}
	 * </p>
	 */
	@Override
	public void set(byte[] v) {
		super.set(Objects.requireNonNull(v));
	}
	
}
