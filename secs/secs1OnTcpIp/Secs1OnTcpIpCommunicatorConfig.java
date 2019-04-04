package secs.secs1OnTcpIp;

import java.net.SocketAddress;
import java.util.Optional;

import secs.secs1.Secs1CommunicatorConfig;

public class Secs1OnTcpIpCommunicatorConfig extends Secs1CommunicatorConfig {
	
	private SocketAddress socketAddr;
	private float reconnectSeconds;
	
	public Secs1OnTcpIpCommunicatorConfig() {
		super();
		
		socketAddr = null;
		reconnectSeconds = 5.0F;
	}
	
	public void socketAddress(SocketAddress socketAddr) {
		synchronized ( this ) {
			this.socketAddr = socketAddr;
		}
	}
	
	public Optional<SocketAddress> socketAddress() {
		synchronized ( this ) {
			return socketAddr == null ? Optional.empty() : Optional.of(socketAddr);
		}
	}
	
	public void reconnectSeconds(float seconds) {
		this.reconnectSeconds = seconds;
	}
	
	public float reconnectSeconds() {
		return this.reconnectSeconds;
	}
}
