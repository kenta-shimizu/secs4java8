package secs.secs1OnTcpIp;

import java.net.SocketAddress;
import java.util.Optional;

import secs.secs1.Secs1CommunicatorConfig;

public class Secs1OnTcpIpCommunicatorConfig extends Secs1CommunicatorConfig {
	
	private SocketAddress socketAddr;
	private long reconnectSeconds;
	
	public Secs1OnTcpIpCommunicatorConfig() {
		super();
		
		socketAddr = null;
		reconnectSeconds = 5L;
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
	
	public void reconnectSeconds(long seconds) {
		this.reconnectSeconds = seconds;
	}
	
	public long reconnectSeconds() {
		return this.reconnectSeconds;
	}
}
