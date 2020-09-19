package com.shimizukenta.secs;

import java.net.SocketAddress;

/**
 * SocketAddress Getter
 * 
 * @author kenta-shimizu
 *
 */
public interface ReadOnlySocketAddressProperty extends ReadOnlyProperty<SocketAddress> {
	
	/**
	 * SocketAddress getter<br />
	 * if set null, throw IllegalStateException.
	 * 
	 * @throws IllegalStateException
	 * @return SocketAddress
	 */
	public SocketAddress getSocketAddress();
	
}
