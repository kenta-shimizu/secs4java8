package com.shimizukenta.secs.hsmsss;

import java.net.SocketAddress;
import java.util.Objects;

import com.shimizukenta.secs.AbstractSecsCommunicatorConfig;
import com.shimizukenta.secs.Property;
import com.shimizukenta.secs.ReadOnlyNumberProperty;
import com.shimizukenta.secs.ReadOnlyProperty;
import com.shimizukenta.secs.ReadOnlySocketAddressProperty;
import com.shimizukenta.secs.ReadOnlyTimeProperty;
import com.shimizukenta.secs.SocketAddressProperty;
import com.shimizukenta.secs.TimeProperty;

/**
 * This class is config of HSMS-SS-Communicator.<br />
 * To set Active or Passive protocol, {@link #protocol(HsmsSsProtocol)}<br />
 * To set Connect or Bind SocketAddress, {@link #socketAddress(SocketAddress)}<br />
 * To set Session-ID, {@link #sessionId(int)}<br />
 * 
 * @author kenta-shimizu
 *
 */
public class HsmsSsCommunicatorConfig extends AbstractSecsCommunicatorConfig {
	
	private static final long serialVersionUID = -5737187045438763249L;
	
	private final Property<HsmsSsProtocol> protocol = Property.newInstance(HsmsSsProtocol.PASSIVE);
	private final SocketAddressProperty sockAddr = SocketAddressProperty.newInstance(null);
	private final TimeProperty linktest = TimeProperty.newInstance(-1.0F);
	private final TimeProperty rebindIfPassive = TimeProperty.newInstance(10.0F);
	
	public HsmsSsCommunicatorConfig() {
		super();
	}
	
	/**
	 * ACTIVE or PASSIVE protocol setter
	 * 
	 * @param protocol
	 */
	public void protocol(HsmsSsProtocol protocol) {
		this.protocol.set(Objects.requireNonNull(protocol));
	}
	
	/**
	 * Protocol getter
	 * 
	 * @return protocol
	 */
	public ReadOnlyProperty<HsmsSsProtocol> protocol() {
		return protocol;
	}
	
	/**
	 * Connect or bind SocketAddress setter
	 * 
	 * @param socketAddress of PASSIVE/ACTIVE
	 */
	public void socketAddress(SocketAddress socketAddress) {
		this.sockAddr.set(Objects.requireNonNull(socketAddress));
	}
	
	/**
	 * Connect or bind SocketAddress getter
	 * 
	 * @return socketAddress of PASSIVE/ACTIVE
	 */
	public ReadOnlySocketAddressProperty socketAddress() {
		return sockAddr;
	}
	
	/**
	 * Session-ID setter
	 * 
	 * @param sessionId
	 */
	public void sessionId(int sessionId) {
		deviceId(sessionId);
	}
	
	/**
	 * Session-ID getter
	 * 
	 * @return session-id
	 */
	public ReadOnlyNumberProperty sessionId() {
		return deviceId();
	}
	
	/*
	 * Set Not-Linktest
	 * 
	 */
	public void notLinktest() {
		this.linktest.set(-1.0F);
	}
	
	/**
	 * Linktest cycle time setter
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
	 * Linktest cycle getter
	 * 
	 * @return seconds. Not-linktest if < 0.
	 */
	public ReadOnlyTimeProperty linktest() {
		return linktest;
	}
	
	/**
	 * Set not rebind if Passive-protocol
	 * 
	 */
	public void notRebindIfPassive() {
		this.rebindIfPassive.set(-1.0F);
	}
	
	/**
	 * Rebind if Passive-Protocol.<br />
	 * If bind failed, then rebind after this time.
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
	 * rebind time getter.
	 * 
	 * @return seconds. Not rebind if < 0.
	 */
	public ReadOnlyTimeProperty rebindIfPassive() {
		return rebindIfPassive;
	}
	
}
