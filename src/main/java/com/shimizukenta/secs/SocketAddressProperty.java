package com.shimizukenta.secs;

import java.net.SocketAddress;

/**
 * SocketAddress value Getter, Setter, Value-Change-Observer<br />
 * Setter is not accept null.
 * 
 * @author kenta-shimizu
 *
 */
public interface SocketAddressProperty extends Property<SocketAddress>, ReadOnlySocketAddressProperty {
	
	public static SocketAddressProperty newInstance(SocketAddress initial) {
		
		return new AbstractSocketAddressProperty(initial) {
			private static final long serialVersionUID = -460713208491670175L;
		};
	}
	
}
