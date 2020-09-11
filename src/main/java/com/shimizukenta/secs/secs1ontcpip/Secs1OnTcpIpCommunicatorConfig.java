package com.shimizukenta.secs.secs1ontcpip;

import java.net.SocketAddress;
import java.util.Objects;

import com.shimizukenta.secs.SocketAddressProperty;
import com.shimizukenta.secs.TimeProperty;
import com.shimizukenta.secs.secs1.Secs1CommunicatorConfig;

public class Secs1OnTcpIpCommunicatorConfig extends Secs1CommunicatorConfig {
	
	private static final long serialVersionUID = -7468433384957790240L;
	
	private SocketAddressProperty socketAddr = new SocketAddressProperty(null);
	private TimeProperty reconnectSeconds = new TimeProperty(5.0F);
	
	public Secs1OnTcpIpCommunicatorConfig() {
		super();
	}
	
	public void socketAddress(SocketAddress socketAddr) {
		this.socketAddr.set(Objects.requireNonNull(socketAddr));
	}
	
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
