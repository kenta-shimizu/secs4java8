package com.shimizukenta.secs.secs1ontcpip;

import java.net.SocketAddress;
import java.util.Optional;

import com.shimizukenta.secs.SecsLog;

/**
 * Secs1OnTcpIpChannelConnectionLog.
 * 
 * @author kenta-shimizu
 *
 */
public interface Secs1OnTcpIpChannelConnectionLog extends SecsLog {
	
	/**
	 * Returns Secs1OnTcpIpChannelConnectionLog-State.
	 * 
	 * @return Secs1OnTcpIpChannelConnectionLog-State
	 */
	public Secs1OnTcpIpChannelConnectionLogState state();
	
	/**
	 * Returns Local-SocketAddresss if exsit, otherwise empty.
	 * 
	 * @return Local-SocketAddresss if exsit, otherwise empty.
	 */
	public Optional<SocketAddress> optionalLocalSocketAddress();
	
	/**
	 * Returns Remote-SocketAddresss if exsit, otherwise empty.
	 * 
	 * @return Remote-SocketAddresss if exsit, otherwise empty.
	 */
	public Optional<SocketAddress> optionslRemoteSocketAddress();

}
