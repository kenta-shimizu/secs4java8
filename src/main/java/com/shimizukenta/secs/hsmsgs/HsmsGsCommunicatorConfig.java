package com.shimizukenta.secs.hsmsgs;

import java.net.SocketAddress;
import java.util.Objects;

import com.shimizukenta.secs.BooleanProperty;
import com.shimizukenta.secs.CollectionProperty;
import com.shimizukenta.secs.ReadOnlyBooleanProperty;
import com.shimizukenta.secs.ReadOnlyCollectionProperty;
import com.shimizukenta.secs.ReadOnlySocketAddressProperty;
import com.shimizukenta.secs.ReadOnlyTimeProperty;
import com.shimizukenta.secs.SocketAddressProperty;
import com.shimizukenta.secs.TimeProperty;
import com.shimizukenta.secs.hsms.AbstractHsmsCommunicatorConfig;

public class HsmsGsCommunicatorConfig extends AbstractHsmsCommunicatorConfig {
	
	private static final long serialVersionUID = -5254158215625876513L;
	
	public HsmsGsCommunicatorConfig() {
		super();
	}
	
	private final CollectionProperty<Integer> sessionIds = CollectionProperty.newSet();
	
	public boolean addSessionId(int id) {
		if ( id < 0 || id > 0xFFFF ) {
			throw new IllegalArgumentException("Session-ID is in 0 - 65535, id=" + id);
		}
		return this.sessionIds.add(Integer.valueOf(id));
	}
	
	public boolean removeSessionId(int id) {
		return this.sessionIds.remove(Integer.valueOf(id));
	}
	
	public ReadOnlyCollectionProperty<Integer> sessionIds() {
		return this.sessionIds;
	}
	
	private final SocketAddressProperty socketAddr = SocketAddressProperty.newInstance(null);
	
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
	public ReadOnlyBooleanProperty isTrySelectRequest() {
		return this.isTrySelectRequest;
	}
	
	private final TimeProperty retrySelectRequestTimeout = TimeProperty.newInstance(10.0F);
	
	/**
	 * SELECT.REQ retry-timeout setter.
	 * 
	 * @param v > 0.0F
	 */
	public void retrySelectRequestTimeout(float v) {
		if ( v <= 0.0F ) {
			throw new IllegalArgumentException("retrySelectRequestTimeout value requires 0.0F");
		}
		this.retrySelectRequestTimeout.set(v);
	}
	
	/**
	 * ReadOnlyTimeProperty of SELECT.REQ Timeout getter.
	 * 
	 * @return ReadOnlyTimeProperty
	 */
	public ReadOnlyTimeProperty retrySelectRequestTimeout() {
		return this.retrySelectRequestTimeout;
	}
	
}
