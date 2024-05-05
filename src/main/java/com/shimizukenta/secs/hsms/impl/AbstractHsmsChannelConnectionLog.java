package com.shimizukenta.secs.hsms.impl;

import java.net.SocketAddress;
import java.util.Optional;

import com.shimizukenta.secs.hsms.HsmsChannelConnectionLog;
import com.shimizukenta.secs.hsms.HsmsChannelConnectionLogState;
import com.shimizukenta.secs.impl.AbstractSecsLog;

public abstract class AbstractHsmsChannelConnectionLog extends AbstractSecsLog implements HsmsChannelConnectionLog {
	
	private static final long serialVersionUID = -2774290662928055819L;
	
	private final HsmsChannelConnectionLogState state;
	private final SocketAddress local;
	private final SocketAddress remote;
	
	private final Object sync = new Object();
	private String cacheToValueString;
	
	public AbstractHsmsChannelConnectionLog(
			HsmsChannelConnectionLogState state,
			SocketAddress local,
			SocketAddress remote) {
		
		super("HSMS-Communicator " + state.toString());
		this.state = state;
		this.local = local;
		this.remote = remote;
		
		this.cacheToValueString = null;
	}
	
	@Override
	public HsmsChannelConnectionLogState state() {
		return this.state;
	}

	@Override
	public Optional<SocketAddress> optionalLocalSocketAddress() {
		return this.local == null ? Optional.empty() : Optional.of(this.local);
	}

	@Override
	public Optional<SocketAddress> optionslRemoteSocketAddress() {
		return this.remote == null ? Optional.empty() : Optional.of(this.remote);
	}
	
	@Override
	public Optional<String> optionalValueString() {
		
		synchronized (this.sync) {
			
			if (this.cacheToValueString == null) {
				
				final StringBuilder sb = new StringBuilder();
				sb.append("{\"state\":\"")
				.append(this.state)
				.append("\"");

				if (this.local != null) {
					sb.append(",\"local\":\"")
					.append(this.local)
					.append("\"");
				}
				
				if (this.remote != null) {
					sb.append(",\"remote\":\"")
					.append(this.remote)
					.append("\"");
				}
				
				sb.append("}");
				
				this.cacheToValueString = sb.toString();
			}
			
			return Optional.of(this.cacheToValueString);
		}
	}
	
	private static AbstractHsmsChannelConnectionLog buildInstance(
			HsmsChannelConnectionLogState state,
			SocketAddress local,
			SocketAddress remote) {
		
		return new AbstractHsmsChannelConnectionLog(state, local, remote) {

			private static final long serialVersionUID = 9206421073531866207L;
			
		};
	}
	
	public static AbstractHsmsChannelConnectionLog tryBind(SocketAddress local) {
		return buildInstance(HsmsChannelConnectionLogState.TryBind, local, null);
	}
	
	public static AbstractHsmsChannelConnectionLog binded(SocketAddress local) {
		return buildInstance(HsmsChannelConnectionLogState.Binded, local, null);
	}
	
	public static AbstractHsmsChannelConnectionLog bindClosed(SocketAddress local) {
		return buildInstance(HsmsChannelConnectionLogState.BindClosed, local, null);
	}
	
	public static AbstractHsmsChannelConnectionLog accepted(SocketAddress local, SocketAddress remote) {
		return buildInstance(HsmsChannelConnectionLogState.Accepted, local, remote);
	}
	
	public static AbstractHsmsChannelConnectionLog acceptClosed(SocketAddress local, SocketAddress remote) {
		return buildInstance(HsmsChannelConnectionLogState.AcceptClosed, local, remote);
	}
	
	public static AbstractHsmsChannelConnectionLog tryConnect(SocketAddress remote) {
		return buildInstance(HsmsChannelConnectionLogState.TryConnect, null, remote);
	}
	
	public static AbstractHsmsChannelConnectionLog connected(SocketAddress local, SocketAddress remote) {
		return buildInstance(HsmsChannelConnectionLogState.Connected, local, remote);
	}
	
	public static AbstractHsmsChannelConnectionLog connectClosed(SocketAddress local, SocketAddress remote) {
		return buildInstance(HsmsChannelConnectionLogState.ConnectClosed, local, remote);
	}

}
