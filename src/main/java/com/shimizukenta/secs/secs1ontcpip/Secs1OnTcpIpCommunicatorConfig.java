package com.shimizukenta.secs.secs1ontcpip;

import java.net.SocketAddress;
import java.util.Objects;

import com.shimizukenta.secs.local.property.ObjectProperty;
import com.shimizukenta.secs.local.property.TimeoutProperty;
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
	
	/**
	 * SocketAddress.
	 * 
	 */
	private ObjectProperty<SocketAddress> socketAddr = ObjectProperty.newInstance(null);
	
	/**
	 * reconnectSeconds.
	 * 
	 */
	private TimeoutProperty reconnectSeconds = TimeoutProperty.newInstance(5.0F);
	
	/**
	 * Constructor.
	 * 
	 */
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
	 * @param socketAddress the Connect SocketAddress
	 */
	public void socketAddress(SocketAddress socketAddress) {
		this.socketAddr.set(Objects.requireNonNull(socketAddress));
	}
	
	/**
	 * Conenct SocketAddress getter.
	 * 
	 * @return Connect SocketAddress
	 */
	public ObjectProperty<SocketAddress> socketAddress() {
		return this.socketAddr;
	}
	
	/**
	 * Reconnect seconds setter.
	 * 
	 * @param seconds the seconds
	 */
	public void reconnectSeconds(float seconds) {
		this.reconnectSeconds.set(seconds);
	}
	
	/**
	 * Reconnect seconds getter.
	 * 
	 * @return Reconnect-Seconds
	 */
	public TimeoutProperty reconnectSeconds() {
		return this.reconnectSeconds;
	}
	
}
