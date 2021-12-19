package com.shimizukenta.secs.hsmsss;

import java.net.SocketAddress;
import java.util.Objects;

import com.shimizukenta.secs.NumberProperty;
import com.shimizukenta.secs.ReadOnlyNumberProperty;
import com.shimizukenta.secs.ReadOnlyProperty;
import com.shimizukenta.secs.ReadOnlySocketAddressProperty;
import com.shimizukenta.secs.SocketAddressProperty;
import com.shimizukenta.secs.hsms.AbstractHsmsCommunicatorConfig;
import com.shimizukenta.secs.hsms.HsmsConnectionMode;

/**
 * This class is config of HSMS-SS-Communicator.
 * 
 * <ul>
 * <li>To set Session-ID, {@link #sessionId(int)}</li>
 * <li>To set Connect or Bind SocketAddress, {@link #socketAddress(SocketAddress)}</li>
 * </ul>
 * 
 * @author kenta-shimizu
 *
 */
public class HsmsSsCommunicatorConfig extends AbstractHsmsCommunicatorConfig {
	
	private static final long serialVersionUID = 71663164318605890L;
	
	private final NumberProperty sessionId = NumberProperty.newInstance(10);
	private final SocketAddressProperty socketAddr = SocketAddressProperty.newInstance(null);
	
	public HsmsSsCommunicatorConfig() {
		super();
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
		this.socketAddr.set(Objects.requireNonNull(socketAddress));
	}
	
	/**
	 * Connect or bind SocketAddress getter
	 * 
	 * @return socketAddress of PASSIVE/ACTIVE
	 */
	public ReadOnlySocketAddressProperty socketAddress() {
		return socketAddr;
	}
	
	/**
	 * Session-ID setter
	 * 
	 * @param id
	 */
	public void sessionId(int id) {
		if ( id < 0 || id > 0x7FFF ) {
			throw new SessionIdIllegalArgumentException(id);
		}
		this.sessionId.set(id);
	}
	
	/**
	 * Session-ID getter
	 * 
	 * @return session-id
	 */
	public ReadOnlyNumberProperty sessionId() {
		return this.sessionId;
	}
	
	private static class SessionIdIllegalArgumentException extends IllegalArgumentException {
		
		private static final long serialVersionUID = -5233865584009807866L;
		
		public SessionIdIllegalArgumentException(int id) {
			super("SESSION-ID is in 0 - 32767, id=" + id);
		}
	}
	
}
