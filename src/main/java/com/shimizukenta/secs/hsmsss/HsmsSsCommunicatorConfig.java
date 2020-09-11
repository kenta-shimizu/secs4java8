package com.shimizukenta.secs.hsmsss;

import java.net.SocketAddress;
import java.util.Objects;

import com.shimizukenta.secs.AbstractProperty;
import com.shimizukenta.secs.AbstractSecsCommunicatorConfig;
import com.shimizukenta.secs.IntegerProperty;
import com.shimizukenta.secs.Property;
import com.shimizukenta.secs.SocketAddressProperty;
import com.shimizukenta.secs.TimeProperty;

public class HsmsSsCommunicatorConfig extends AbstractSecsCommunicatorConfig {
	
	private static final long serialVersionUID = -5737187045438763249L;
	
	private class ProtocolProperty extends AbstractProperty<HsmsSsProtocol> {

		public ProtocolProperty(HsmsSsProtocol initial) {
			super(initial);
		}
	}
	
	
	private final Property<HsmsSsProtocol> protocol = new ProtocolProperty(HsmsSsProtocol.PASSIVE);
	private final SocketAddressProperty sockAddr = new SocketAddressProperty(null);
	private final TimeProperty linktest = new TimeProperty(-1.0F);
	private final TimeProperty rebindIfPassive = new TimeProperty(-1.0F);
	
	public HsmsSsCommunicatorConfig() {
		super();
	}
	
	public void protocol(HsmsSsProtocol protocol) {
		this.protocol.set(Objects.requireNonNull(protocol));
	}
	
	public Property<HsmsSsProtocol> protocol() {
		return protocol;
	}
	
	/**
	 * 
	 * @param socketAddress of PASSIVE/ACTIVE
	 */
	public void socketAddress(SocketAddress socketAddress) {
		this.sockAddr.set(Objects.requireNonNull(socketAddress));
	}
	
	/**
	 * 
	 * @return socketAddress of PASSIVE/ACTIVE
	 */
	public SocketAddressProperty socketAddress() {
		return sockAddr;
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
	public IntegerProperty sessionId() {
		return deviceId();
	}
	
	/*
	 * Not-Linktest
	 * 
	 */
	public void notLinktest() {
		this.linktest.set(-1.0F);
	}
	
	/**
	 * 
	 * @param linktest-cycle-seconds. value >= 0
	 */
	public void linktest(float v) {
		if ( v < 0.0F ) {
			throw new IllegalArgumentException("linktest value is >= 0.0F");
		}
		this.linktest.set(v);
	}
	
	/**
	 * 
	 * @return seconds
	 */
	public TimeProperty linktest() {
		return linktest;
	}
	
	/**
	 * 
	 * Not rebind if Passive-protocol
	 * 
	 */
	public void notRebindIfPassive() {
		this.rebindIfPassive.set(-1.0F);
	}
	
	/**
	 * 
	 * @param rebind after this time if Passive-protocol. value >= 0
	 */
	public void rebindIfPassive(float v) {
		if ( v < 0.0F ) {
			throw new IllegalArgumentException("rebindIfPassive value is >= 0.0F");
		}
		this.rebindIfPassive.set(v);
	}
	
	/**
	 * 
	 * @return seconds
	 */
	public TimeProperty rebindIfPassive() {
		return rebindIfPassive;
	}
	
}
