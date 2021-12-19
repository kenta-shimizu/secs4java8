package com.shimizukenta.secs.secs1ontcpip;

import java.net.SocketAddress;
import java.util.Objects;

import com.shimizukenta.secs.ReadOnlySocketAddressProperty;
import com.shimizukenta.secs.ReadOnlyTimeProperty;
import com.shimizukenta.secs.SocketAddressProperty;
import com.shimizukenta.secs.TimeProperty;
import com.shimizukenta.secs.secs1.AbstractSecs1CommunicatorConfig;

/**
 * This class is confit of SECS-I-on-TCP/IP-Communicator.
 * 
 * <ul>
 * <li>To set Connect SocketAddress, {@link #socketAddress(SocketAddress)}</li>
 * </ul>
 * 
 * @author kenta-shimizu
 *
 */
public class Secs1OnTcpIpCommunicatorConfig extends AbstractSecs1CommunicatorConfig {
	
	private static final long serialVersionUID = -7468433384957790240L;
	
	private SocketAddressProperty socketAddr = SocketAddressProperty.newInstance(null);
	private TimeProperty reconnectSeconds = TimeProperty.newInstance(5.0F);
	
	public Secs1OnTcpIpCommunicatorConfig() {
		super();
	}
	
	/**
	 * Connect SocketAddress setter.
	 * 
	 * <p>
	 * Not accept {@code null}
	 * </p>
	 * 
	 * @param socketAddress
	 */
	public void socketAddress(SocketAddress socketAddress) {
		this.socketAddr.set(Objects.requireNonNull(socketAddress));
	}
	
	/**
	 * Conenct SocketAddress getter.
	 * 
	 * @return Connect SocketAddress
	 */
	public ReadOnlySocketAddressProperty socketAddress() {
		return this.socketAddr;
	}
	
	/**
	 * Reconnect seconds setter.
	 * 
	 * @param seconds
	 */
	public void reconnectSeconds(float seconds) {
		this.reconnectSeconds.set(seconds);
	}
	
	/**
	 * Reconnect seconds getter.
	 * 
	 * @return Reconnect-Seconds
	 */
	public ReadOnlyTimeProperty reconnectSeconds() {
		return this.reconnectSeconds;
	}
	
}
