package com.shimizukenta.secs.hsmsgs;

import java.net.SocketAddress;
import java.util.Objects;

import com.shimizukenta.secs.hsms.AbstractHsmsCommunicatorConfig;
import com.shimizukenta.secs.local.property.BooleanProperty;
import com.shimizukenta.secs.local.property.ObjectProperty;
import com.shimizukenta.secs.local.property.SetProperty;
import com.shimizukenta.secs.local.property.TimeoutProperty;

/**
 * This class is config of HSMS-GS-Communicator.
 * 
 * <ul>
 * <li>To add SESSION-ID, {@link #addSessionId(int)}.</li>
 * <li>To set Connect or Bind SocketAddress, {@link #socketAddress(SocketAddress)}}.</li>
 * <li>To set not try-SELECT.REQ communicator, {@link #notTrySelectRequest}.</li>
 * <li>To set retry-SELECT.REQ timeout, {@link #retrySelectRequestTimeout(float)}.</li>
 * </ul>
 * 
 * @author kenta-shimizu
 *
 */
public class HsmsGsCommunicatorConfig extends AbstractHsmsCommunicatorConfig {
	
	private static final long serialVersionUID = -5254158215625876513L;
	
	/**
	 * Constructor.
	 * 
	 */
	public HsmsGsCommunicatorConfig() {
		super();
	}
	
	/**
	 * SessionIds.
	 * 
	 */
	private final SetProperty<Integer> sessionIds = SetProperty.newInstance();
	
	/**
	 * Returns true if add success, otherwise false.
	 * 
	 * @param sessionId Session-ID
	 * @return true if add success, otherwise false
	 */
	public boolean addSessionId(int sessionId) {
		if ( sessionId < 0 || sessionId > 0xFFFF ) {
			throw new AddSessionIdIllegalArgumentException(sessionId);
		}
		return this.sessionIds.add(Integer.valueOf(sessionId));
	}
	
	/**
	 * Returns true if remove success, otherwise false.
	 * 
	 * @param sessionId Session-ID
	 * @return true if remove success, otherwise false
	 */
	public boolean removeSessionId(int sessionId) {
		return this.sessionIds.remove(Integer.valueOf(sessionId));
	}
	
	/**
	 * Returns Session-ID set.
	 * 
	 * @return Session-ID set
	 */
	public SetProperty<Integer> sessionIds() {
		return this.sessionIds;
	}
	
	/**
	 * SocketAddress.
	 * 
	 */
	private final ObjectProperty<SocketAddress> socketAddr = ObjectProperty.newInstance(null);
	
	/**
	 * Connect or bind SocketAddress setter
	 * 
	 * @param socketAddress PASSIVE/ACTIVE SocketAddress
	 */
	public void socketAddress(SocketAddress socketAddress) {
		this.socketAddr.set(Objects.requireNonNull(socketAddress));
	}
	
	/**
	 * Connect or bind SocketAddress getter
	 * 
	 * @return socketAddress PASSIVE/ACTIVE SocketAddress
	 */
	public ObjectProperty<SocketAddress> socketAddress() {
		return socketAddr;
	}
	
	/**
	 * Sync-Object TrySelectRequest.
	 * 
	 */
	private final Object syncTrySelectRequest = new Object();
	
	/**
	 * isTrySelectRequest.
	 * 
	 */
	private final BooleanProperty isTrySelectRequest = BooleanProperty.newInstance(false);
	
	/**
	 * retrySelectRequestTimeout.
	 * 
	 */
	private final TimeoutProperty retrySelectRequestTimeout = TimeoutProperty.newInstance(10.0F);
	
	/**
	 * Set Not Try SELECT.REQ.
	 * 
	 */
	public void notTrySelectRequest() {
		synchronized ( this.syncTrySelectRequest ) {
			this.isTrySelectRequest.setFalse();
		}
	}
	
	/**
	 * ReadOnlyBooleanProperty of is-try-SELECT.REQ getter.
	 * 
	 * @return ReadOnlyProperty of is-try-SELECT.REQ
	 */
	public BooleanProperty isTrySelectRequest() {
		return this.isTrySelectRequest;
	}
	
	/**
	 * SELECT.REQ retry-timeout setter.
	 * 
	 * @param seconds Timeout seconds
	 */
	public void retrySelectRequestTimeout(float seconds) {
		synchronized ( this.syncTrySelectRequest ) {
			this.isTrySelectRequest.setTrue();
			this.retrySelectRequestTimeout.set(seconds);
		}
	}
	
	/**
	 * ReadOnlyTimeProperty of SELECT.REQ Timeout getter.
	 * 
	 * @return ReadOnlyTimeProperty
	 */
	public TimeoutProperty retrySelectRequestTimeout() {
		return this.retrySelectRequestTimeout;
	}
	
}
