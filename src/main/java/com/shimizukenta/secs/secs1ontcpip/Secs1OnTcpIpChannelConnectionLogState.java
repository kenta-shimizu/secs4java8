package com.shimizukenta.secs.secs1ontcpip;

/**
 * Secs1OnTcpIpChannelConnectionLogState.
 * 
 * @author kenta-shimizu
 *
 */
public enum Secs1OnTcpIpChannelConnectionLogState {
	
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
