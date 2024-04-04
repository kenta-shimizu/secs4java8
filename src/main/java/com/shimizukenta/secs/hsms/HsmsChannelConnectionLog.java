package com.shimizukenta.secs.hsms;

import java.net.SocketAddress;
import java.util.Optional;

import com.shimizukenta.secs.SecsLog;

/**
 * HSMS-channel-connection Log.
 * 
 * @author kenta-shimizu
 *
 */
public interface HsmsChannelConnectionLog extends SecsLog {
	
	/**
	 * Returns HsmsChannelConnectionLog-State.
	 * 
	 * @return HsmsChannelConnectionLog-State
	 */
	public HsmsChannelConnectionLogState state();
	
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
