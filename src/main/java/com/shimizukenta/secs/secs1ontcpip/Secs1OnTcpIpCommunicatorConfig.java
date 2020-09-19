package com.shimizukenta.secs.secs1ontcpip;

import java.net.SocketAddress;
import java.util.Objects;

import com.shimizukenta.secs.AbstractSocketAddressProperty;
import com.shimizukenta.secs.AbstractTimeProperty;
import com.shimizukenta.secs.ReadOnlySocketAddressProperty;
import com.shimizukenta.secs.ReadOnlyTimeProperty;
import com.shimizukenta.secs.SimpleSocketAddressProperty;
import com.shimizukenta.secs.SimpleTimeProperty;
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
	
	private AbstractSocketAddressProperty socketAddr = new SimpleSocketAddressProperty(null);
	private AbstractTimeProperty reconnectSeconds = new SimpleTimeProperty(5.0F);
	
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
	public ReadOnlySocketAddressProperty socketAddress() {
		return this.socketAddr;
	}
	
	public void reconnectSeconds(float seconds) {
		this.reconnectSeconds.set(seconds);
	}
	
	public ReadOnlyTimeProperty reconnectSeconds() {
		return this.reconnectSeconds;
	}
}
