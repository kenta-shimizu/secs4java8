package com.shimizukenta.secs;

import java.net.SocketAddress;

/**
 * SocketAddress Getter.
 * 
 * @author kenta-shimizu
 *
 */
public interface ReadOnlySocketAddressProperty extends ReadOnlyProperty<SocketAddress> {
	
	/**
	 * SocketAddress getter.
	 * 
	 * <p>
	 * if set {@code null}, throw IllegalStateException.<br />
	 * </p>
	 * 
	 * @return SocketAddress
	 * @throws IllegalStateException
	 */
	public SocketAddress getSocketAddress();
	
}
