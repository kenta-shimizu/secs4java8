package com.shimizukenta.secs.secs1ontcpip;

import com.shimizukenta.secs.SecsLogListener;
import com.shimizukenta.secs.secs1.Secs1LogObservable;

/**
 * Secs1OnTcpIpLogObservable.
 * 
 * @author kenta-shimizu
 *
 */
public interface Secs1OnTcpIpLogObservable extends Secs1LogObservable {
	
	/**
	 * Returns {@code true} if add Secs1OnTcpIp channelconnection log listener success, otherwise {@code false}, receive channel connection log.
	 * 
	 * @param listener the Secs1OnTcpIp channel connection log listener
	 * @return true if add success, otherwise false
	 */
	public boolean addSecs1OnTcpIpChannelConnectionLogListener(SecsLogListener<? super Secs1OnTcpIpChannelConnectionLog> listener);
	
	/**
	 * Returns {@code true} if remove success, otherwise {@code false}.
	 * 
	 * @param listener the Secs1OnTcpIp channel connection log listener
	 * @return true if remove success, otherwise false
	 */
	public boolean removeSecs1OnTcpIpChannelConnectionLogListener(SecsLogListener<? super Secs1OnTcpIpChannelConnectionLog> listener);
	
}
