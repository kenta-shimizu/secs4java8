package com.shimizukenta.secs.hsmsss;

import java.net.SocketAddress;
import java.util.Objects;

import com.shimizukenta.secs.AbstractProperty;
import com.shimizukenta.secs.AbstractSecsCommunicatorConfig;
import com.shimizukenta.secs.Property;

public class HsmsSsCommunicatorConfig extends AbstractSecsCommunicatorConfig {
	
	private static final long serialVersionUID = -5737187045438763249L;
	
	private class ProtocolProperty extends AbstractProperty<HsmsSsProtocol> {

		public ProtocolProperty(HsmsSsProtocol initial) {
			super(initial);
		}
	}
	
	private class SocketAddressProperty extends AbstractProperty<SocketAddress> {

		public SocketAddressProperty(SocketAddress initial) {
			super(initial);
		}
	}
	
	private class FloatProperty extends AbstractProperty<Float> {

		public FloatProperty(float initial) {
			super(Float.valueOf(initial));
		}
	}
	
	private final Property<HsmsSsProtocol> protocol = new ProtocolProperty(HsmsSsProtocol.PASSIVE);
	private final Property<SocketAddress> sockAddr = new SocketAddressProperty(null);
	private final Property<Float> linktest = new FloatProperty(-1.0F);
	private final Property<Float> rebindIfPassive = new FloatProperty(-1.0F);
	
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
	public Property<SocketAddress> socketAddress() {
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
	public Property<Integer> sessionId() {
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
	public Property<Float> linktest() {
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
	public Property<Float> rebindIfPassive() {
		return rebindIfPassive;
	}
	
}
