package com.shimizukenta.secs;

import java.net.SocketAddress;

/**
 * SokectAddress value Getter, Setter, Value-Change-Observer<br />
 * 
 * @author kenta-shimizu
 *
 */
public abstract class AbstractSocketAddressProperty extends AbstractProperty<SocketAddress>
		implements ReadOnlySocketAddressProperty {
	
	private static final long serialVersionUID = -7185437946139606493L;
	
	public AbstractSocketAddressProperty(SocketAddress initial) {
		super(initial);
	}
	
	@Override
	public SocketAddress getSocketAddress() {
		synchronized ( this ) {
			SocketAddress a = get();
			if ( a == null ) {
				throw new IllegalStateException("SocketAddress not setted");
			}
			return a;
		}
	}

}
