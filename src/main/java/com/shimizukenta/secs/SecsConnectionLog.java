package com.shimizukenta.secs;

import java.net.SocketAddress;

/**
 * This inteface implemnts Local-SocketAddress, Remote-SocketAddress of connection.
 * 
 * <p>
 * To get Local-SocketAddress, {@link #local()}.<br />
 * To get Remote-SocketAddress, {@link #remote()}.<br />
 * To get is-connecting, {@link #isConnecting()}.<br />
 * </p>
 * 
 * @author kenta-shimizu
 *
 */
public interface SecsConnectionLog extends SecsLog {
	
	/**
	 * Returns Local-SocketAddress if allocated, otherwise null.
	 * 
	 * @return Local-SocketAddress if allocated, otherwise null
	 */
	public SocketAddress local();
	
	/**
	 * Returns Remote-SocketAddress if allocated, otherwise null.
	 * 
	 * @return Remote-SocketAddress if allocated, otherwise null
	 */
	public SocketAddress remote();
	
	/**
	 * Return {@code true} if connecting.
	 * 
	 * @return {@code true} if connecting
	 */
	public boolean isConnecting();
	
}
