package com.shimizukenta.secs;

import java.net.SocketAddress;
import java.util.Objects;

/**
 * SokectAddress value Getter, Setter, Value-Change-Observer<br />
 * 
 * @author kenta-shimizu
 *
 */
public abstract class AbstractSocketAddressProperty extends AbstractProperty<SocketAddress>
		implements SocketAddressProperty {
	
	private static final long serialVersionUID = -7185437946139606493L;
	
	public AbstractSocketAddressProperty(SocketAddress initial) {
		super(initial);
	}
	
	/**
	 * setter<br />
	 * Not Accept null.
	 * 
	 */
	@Override
	public void set(SocketAddress v) {
		super.set(Objects.requireNonNull(v));
	}
	
	@Override
	public SocketAddress getSocketAddress() {
		SocketAddress a = get();
		if ( a == null ) {
			throw new IllegalStateException("SocketAddress not setted");
		}
		return a;
	}

}
