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
 * <li>To add SESSION-ID, {@link #addSessionId(int)}</li>
 * <li>To set Connect or Bind SocketAddress, {@link #socketAddress(SocketAddress)}}</li>
 * <li>To set try-SELECT.REQ communicator, {@link #isTrySelectRequest(boolean)}</li>
 * </ul>
 * 
 * @author kenta-shimizu
 *
 */
public class HsmsGsCommunicatorConfig extends AbstractHsmsCommunicatorConfig {
	
	private static final long serialVersionUID = -5254158215625876513L;
	
	public HsmsGsCommunicatorConfig() {
		super();
	}
	
	private final SetProperty<Integer> sessionIds = SetProperty.newInstance();
	
	public boolean addSessionId(int id) {
		if ( id < 0 || id > 0xFFFF ) {
			throw new AddSessionIdIllegalArgumentException(id);
		}
		return this.sessionIds.add(Integer.valueOf(id));
	}
	
	public boolean removeSessionId(int id) {
		return this.sessionIds.remove(Integer.valueOf(id));
	}
	
	public SetProperty<Integer> sessionIds() {
		return this.sessionIds;
	}
	
	private final ObjectProperty<SocketAddress> socketAddr = ObjectProperty.newInstance(null);
	
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
	
	private final BooleanProperty isTrySelectRequest = BooleanProperty.newInstance(false);
	
	/**
	 * Try SELECT.REQ setter.
	 * 
	 * @param true if try SELECT.REQ
	 */
	public void isTrySelectRequest(boolean f) {
		this.isTrySelectRequest.set(f);
	}
	
	/**
	 * ReadOnlyBooleanProperty of is-try-SELECT.REQ getter.
	 * 
	 * @return ReadOnlyProperty of is-try-SELECT.REQ
	 */
	public BooleanProperty isTrySelectRequest() {
		return this.isTrySelectRequest;
	}
	
	private final TimeoutProperty retrySelectRequestTimeout = TimeoutProperty.newInstance(10.0F);
	
	/**
	 * SELECT.REQ retry-timeout setter.
	 * 
	 * @param v > 0.0F
	 */
	public void retrySelectRequestTimeout(float v) {
		if ( v <= 0.0F ) {
			throw new RetrySelectRequestTimeoutIllegalArgumentException(v);
		}
		this.retrySelectRequestTimeout.set(v);
	}
	
	/**
	 * ReadOnlyTimeProperty of SELECT.REQ Timeout getter.
	 * 
	 * @return ReadOnlyTimeProperty
	 */
	public TimeoutProperty retrySelectRequestTimeout() {
		return this.retrySelectRequestTimeout;
	}
	
	private static class AddSessionIdIllegalArgumentException extends IllegalArgumentException {
		
		private static final long serialVersionUID = -9211783480973747830L;
		
		public AddSessionIdIllegalArgumentException(int id) {
			super("Session-ID is in 0 - 65535, id=" + id);
		}
	}
	
	private static class RetrySelectRequestTimeoutIllegalArgumentException extends IllegalArgumentException {
		
		private static final long serialVersionUID = -2444363506021169418L;
		
		public RetrySelectRequestTimeoutIllegalArgumentException(float value) {
			super("retrySelectRequestTimeout value requires 0.0F, value=" + value);
		}
	}
	
}
