package com.shimizukenta.secs.hsmsss;

import java.net.SocketAddress;
import java.util.Objects;
import java.util.Optional;

import com.shimizukenta.secs.AbstractSecsCommunicatorConfig;

public class HsmsSsCommunicatorConfig extends AbstractSecsCommunicatorConfig {
	
	private static final long serialVersionUID = -5737187045438763249L;
	
	private HsmsSsProtocol protocol;
	private SocketAddress sockAddr;
	private float linktest;
	private float rebindIfPassive;
	
	public HsmsSsCommunicatorConfig() {
		super();
		
		protocol = HsmsSsProtocol.PASSIVE;
		sockAddr = null;
		linktest = -1.0F;
		rebindIfPassive = -1.0F;
	}
	
	public void protocol(HsmsSsProtocol protocol) {
		
		Objects.requireNonNull(protocol);
		
		synchronized ( this ) {
			this.protocol = Objects.requireNonNull(protocol);
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
		
		synchronized ( this ) {
			this.sockAddr = Objects.requireNonNull(socketAddress);
		}
	}
	
	/**
	 * 
	 * @return socketAddress of PASSIVE/ACTIVE
	 */
	public SocketAddress socketAddress() {
		synchronized ( this ) {
			
			if ( sockAddr == null ) {
				throw new IllegalStateException("SocketAddress not setted");
			}
			
			return sockAddr;
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
		
		synchronized ( this ) {
			if ( v < 0.0F ) {
				throw new IllegalArgumentException("linktest value is >= 0.0F");
			}
			
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
	
	/**
	 * 
	 * Not rebind if Passive-protocol
	 * 
	 */
	public void notRebindIfPassive() {
		synchronized ( this ) {
			this.rebindIfPassive = -1.0F;
		}
	}
	
	/**
	 * 
	 * @param rebind after this time if Passive-protocol. value >= 0
	 */
	public void rebindIfPassive(float v) {
		
		synchronized ( this ) {
			if ( v < 0.0F ) {
				throw new IllegalArgumentException("rebindIfPassive value is >= 0.0F");
			}
			
			this.rebindIfPassive = v;
		}
	}
	
	/**
	 * 
	 * @return seconds
	 */
	public Optional<Float> rebindIfPassive() {
		synchronized ( this ) {
			return rebindIfPassive >= 0 ? Optional.of(rebindIfPassive) : Optional.empty();
		}
	}
	
}
