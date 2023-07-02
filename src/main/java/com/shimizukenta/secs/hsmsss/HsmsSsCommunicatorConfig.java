package com.shimizukenta.secs.hsmsss;

import java.net.SocketAddress;
import java.util.Objects;

import com.shimizukenta.secs.hsms.AbstractHsmsCommunicatorConfig;
import com.shimizukenta.secs.local.property.IntegerProperty;
import com.shimizukenta.secs.local.property.ObjectProperty;

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
	
	private final IntegerProperty sessionId = IntegerProperty.newInstance(10);
	private final ObjectProperty<SocketAddress> socketAddr = ObjectProperty.newInstance(null);
	
	public HsmsSsCommunicatorConfig() {
		super();
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
	public ObjectProperty<SocketAddress> socketAddress() {
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
	public IntegerProperty sessionId() {
		return this.sessionId;
	}
	
	private static class SessionIdIllegalArgumentException extends IllegalArgumentException {
		
		private static final long serialVersionUID = -5233865584009807866L;
		
		public SessionIdIllegalArgumentException(int id) {
			super("SESSION-ID is in 0 - 32767, id=" + id);
		}
	}
	
}
