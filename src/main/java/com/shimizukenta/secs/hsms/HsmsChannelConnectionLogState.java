package com.shimizukenta.secs.hsms;

/**
 * HSMS-channel-connection state.
 * 
 * @author kenta-shimizu
 *
 */
public enum HsmsChannelConnectionLogState {
	
	/**
	 * Try-Bind.
	 */
	TryBind,
	
	/**
	 * Binded.
	 */
	Binded,
	
	/**
	 * Bind-closed.
	 */
	BindClosed,
	
	/**
	 * Accepted.
	 */
	Accepted,
	
	/**
	 * Accept-closed.
	 */
	AcceptClosed,
	
	/**
	 * Try-connect.
	 */
	TryConnect,
	
	/**
	 * Connected.
	 */
	Connected,
	
	/**
	 * Connect-closed.
	 */
	ConnectClosed,
	;
	
}
