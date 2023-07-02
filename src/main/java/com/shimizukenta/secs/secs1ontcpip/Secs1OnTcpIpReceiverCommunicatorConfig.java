package com.shimizukenta.secs.secs1ontcpip;

import java.net.SocketAddress;

import com.shimizukenta.secs.local.property.ObjectProperty;
import com.shimizukenta.secs.local.property.TimeoutProperty;
import com.shimizukenta.secs.secs1.AbstractSecs1CommunicatorConfig;

/**
 * This class is config of SECS-I-on-TCP/IP-Receiver-Communicator.
 * 
 * <ul>
 * <li>To set Bind SocketAddress, {@link #socketAddress(SocketAddress)}</li>
 * </ul>
 * 
 * @author kenta-shimizu
 *
 */
public class Secs1OnTcpIpReceiverCommunicatorConfig extends AbstractSecs1CommunicatorConfig {
	
	private static final long serialVersionUID = 6842392464950831424L;
	
	private ObjectProperty<SocketAddress> socketAddr = ObjectProperty.newInstance(null);
	private TimeoutProperty rebindSeconds = TimeoutProperty.newInstance(5.0F);
	
	public Secs1OnTcpIpReceiverCommunicatorConfig() {
		super();
	}
	
	/**
	 * Bind SocketAddress setter.
	 * 
	 * <p>
	 * Not accept {@code null}
	 * </p>
	 * 
	 * @param socketAddress
	 */
	public void socketAddress(SocketAddress socketAddress) {
		this.socketAddr.set(socketAddress);
	}
	
	/**
	 * Bind SocketAddress getter.
	 * 
	 * @return Connect SocketAddress
	 */
	public ObjectProperty<SocketAddress> socketAddress() {
		return this.socketAddr;
	}
	
	/**
	 * Rebind seconds setter.
	 * 
	 * @param seconds
	 */
	public void rebindSeconds(float seconds) {
		this.rebindSeconds.set(seconds);
	}
	
	/**
	 * Rebind seconds getter.
	 * 
	 * @return Rebind-Seconds
	 */
	public TimeoutProperty rebindSeconds() {
		return this.rebindSeconds;
	}
	
}
