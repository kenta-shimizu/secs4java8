package secs.hsmsSs;

import java.net.SocketAddress;
import java.util.Objects;
import java.util.Optional;

import secs.SecsCommunicatorConfig;

public class HsmsSsCommunicatorConfig extends SecsCommunicatorConfig {
	
	private HsmsSsProtocol protocol;
	private SocketAddress sockAddr;
	private float linktest;
	
	public HsmsSsCommunicatorConfig() {
		super();
		
		protocol = HsmsSsProtocol.PASSIVE;
		sockAddr = null;
		linktest = -1.0F;
	}
	
	public void protocol(HsmsSsProtocol protocol) {
		
		Objects.requireNonNull(protocol);
		
		synchronized ( this ) {
			this.protocol = protocol;
		}
	}
	
	public HsmsSsProtocol protocol() {
		synchronized ( this ) {
			return protocol;
		}
	}
	
	/**
	 * 
	 * @param socketAddress of PASSIVE/ACTIVE
	 */
	public void socketAddress(SocketAddress socketAddress) {
		
		Objects.requireNonNull(socketAddress);
		
		synchronized ( this ) {
			this.sockAddr = socketAddress;
		}
	}
	
	/**
	 * 
	 * @return socketAddress of PASSIVE/ACTIVE
	 */
	public Optional<SocketAddress> socketAddress() {
		synchronized ( this ) {
			return sockAddr == null ? Optional.empty() : Optional.of(sockAddr);
		}
	}
	
	/**
	 * Alias of deviceId
	 * 
	 * @param sessionId
	 */
	public void sessionId(int sessionId) {
		deviceId(sessionId);
	}
	
	/**
	 * Alias of deviceId
	 * 
	 * @return session-id
	 */
	public int sessionId() {
		return deviceId();
	}
	
	/*
	 * Not-Linktest
	 * 
	 */
	public void notLinktest() {
		synchronized ( this ) {
			this.linktest = -1.0F;
		}
	}
	
	/**
	 * 
	 * @param linktest-cycle-seconds. value >= 0
	 */
	public void linktest(float v) {
		
		if ( v < 0.0F ) {
			throw new IllegalArgumentException("linktest value is >= 0.0F");
		}
		
		synchronized ( this ) {
			this.linktest = v;
		}
	}
	
	/**
	 * 
	 * @return seconds
	 */
	public Optional<Float> linktest() {
		synchronized ( this ) {
			return linktest >= 0.0F ? Optional.of(linktest) : Optional.empty();
		}
	}
	
}
