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
import com.shimizukenta.secs.hsms.HsmsConnectionMode;

/**
 * This class is config of HSMS-SS-Communicator.
 * 
 * <p>
 * To set Active or Passive protocol, {@link #protocol(HsmsSsProtocol)}<br />
 * To set Connect or Bind SocketAddress, {@link #socketAddress(SocketAddress)}<br />
 * To set Session-ID, {@link #sessionId(int)}<br />
 * </p>
 * 
 * @author kenta-shimizu
 *
 */
public class HsmsSsCommunicatorConfig extends AbstractSecsCommunicatorConfig {
	
	private static final long serialVersionUID = -5737187045438763249L;
	
	private final Property<HsmsConnectionMode> connectionMode = Property.newInstance(HsmsConnectionMode.PASSIVE);
	private final SocketAddressProperty sockAddr = SocketAddressProperty.newInstance(null);
	private final TimeProperty linktest = TimeProperty.newInstance(-1.0F);
	private final TimeProperty rebindIfPassive = TimeProperty.newInstance(10.0F);
	
	public HsmsSsCommunicatorConfig() {
		super();
	}
	
	/**
	 * ACTIVE or PASSIVE Connection-Mode setter
	 * 
	 * @param mode
	 */
	public void connectionMode(HsmsConnectionMode mode) {
		this.connectionMode.set(Objects.requireNonNull(mode));
	}
	
	/**
	 * Connection-Mode getter
	 * 
	 * @return connection-mode
	 */
	public ReadOnlyProperty<HsmsConnectionMode> connectionMode() {
		return this.connectionMode;
	}
	
	/**
	 * ACTIVE or PASSIVE protocol setter
	 * 
	 * @param protocol
	 */
	@Deprecated
	public void protocol(HsmsSsProtocol protocol) {
		switch ( protocol ) {
		case PASSIVE: {
			this.connectionMode(HsmsConnectionMode.PASSIVE);
			break;
		}
		case ACTIVE: {
			this.connectionMode(HsmsConnectionMode.ACTIVE);
			break;
		}
		}
	}
	
	/**
	 * Protocol getter
	 * 
	 * @return protocol
	 */
	@Deprecated
	public ReadOnlyProperty<HsmsConnectionMode> protocol() {
		return this.connectionMode();
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
	 * @param v linktest-cycle-seconds. value is {@code >= 0}
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
	 * @return seconds. Not-linktest if {@code <0}
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
	 * Rebind if Passive-Protocol, if bind failed, then rebind after this time.
	 * 
	 * @param v rebind after this time if Passive-protocol. value {@code >=0}
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
	 * @return seconds. Not rebind if {@code <0}
	 */
	public ReadOnlyTimeProperty rebindIfPassive() {
		return rebindIfPassive;
	}
	
}
