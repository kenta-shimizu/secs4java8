package com.shimizukenta.secs.secs1ontcpip;

import java.net.SocketAddress;
import java.util.Objects;

import com.shimizukenta.secs.SocketAddressProperty;
import com.shimizukenta.secs.TimeProperty;
import com.shimizukenta.secs.secs1.Secs1CommunicatorConfig;

/**
 * This class is SECS-I-on-TCP/IP config.<br />
 * To set Connect SocketAddress, {@link #socketAddress(SocketAddress)}<br />
 * 
 * @author kenta-shimizu
 *
 */
public class Secs1OnTcpIpCommunicatorConfig extends Secs1CommunicatorConfig {
	
	private static final long serialVersionUID = -7468433384957790240L;
	
	private SocketAddressProperty socketAddr = new SocketAddressProperty(null);
	private TimeProperty reconnectSeconds = new TimeProperty(5.0F);
	
	public Secs1OnTcpIpCommunicatorConfig() {
		super();
	}
	
	/**
	 * Connect SocketAddress setter
	 * 
	 * @param socketAddress
	 */
	public void socketAddress(SocketAddress socketAddress) {
		this.socketAddr.set(Objects.requireNonNull(socketAddress));
	}
	
	/**
	 * Conenct SocketAddress getter
	 * 
	 * @return Connect SocketAddress
	 */
	public SocketAddressProperty socketAddress() {
		return this.socketAddr;
	}
	
	public void reconnectSeconds(float seconds) {
		this.reconnectSeconds.set(seconds);
	}
	
	public TimeProperty reconnectSeconds() {
		return this.reconnectSeconds;
	}
}
