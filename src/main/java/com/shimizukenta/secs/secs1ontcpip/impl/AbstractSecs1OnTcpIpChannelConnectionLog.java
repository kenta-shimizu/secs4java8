package com.shimizukenta.secs.secs1ontcpip.impl;

import java.net.SocketAddress;
import java.util.Optional;

import com.shimizukenta.secs.impl.AbstractSecsLog;
import com.shimizukenta.secs.secs1ontcpip.Secs1OnTcpIpChannelConnectionLog;
import com.shimizukenta.secs.secs1ontcpip.Secs1OnTcpIpChannelConnectionLogState;

public abstract class AbstractSecs1OnTcpIpChannelConnectionLog extends AbstractSecsLog
		implements Secs1OnTcpIpChannelConnectionLog {
	
	private static final long serialVersionUID = -4455313872043658978L;
	
	private final Secs1OnTcpIpChannelConnectionLogState state;
	private final SocketAddress local;
	private final SocketAddress remote;
	
	private final Object sync = new Object();
	private String cacheToValueString;
	
	public AbstractSecs1OnTcpIpChannelConnectionLog(
			Secs1OnTcpIpChannelConnectionLogState state,
			SocketAddress local,
			SocketAddress remote) {
		
		super("SECS1-onTCP/IP-Communicator " + state.toString());
		
		this.state = state;
		this.local = local;
		this.remote = remote;
		
		this.cacheToValueString = null;
	}

	@Override
	public Secs1OnTcpIpChannelConnectionLogState state() {
		return this.state;
	}

	@Override
	public Optional<SocketAddress> optionalLocalSocketAddress() {
		return (this.local == null) ? Optional.empty() : Optional.of(this.local);
	}

	@Override
	public Optional<SocketAddress> optionslRemoteSocketAddress() {
		return (this.remote == null) ? Optional.empty() : Optional.of(this.remote);
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
	
	private static AbstractSecs1OnTcpIpChannelConnectionLog buildInstance(
			Secs1OnTcpIpChannelConnectionLogState state,
			SocketAddress local,
			SocketAddress remote) {
		
		return new AbstractSecs1OnTcpIpChannelConnectionLog(state, local, remote) {

			private static final long serialVersionUID = 4155952935707826416L;
		};
	}
	
	public static AbstractSecs1OnTcpIpChannelConnectionLog tryBind(SocketAddress local) {
		return buildInstance(Secs1OnTcpIpChannelConnectionLogState.TryBind, local, null);
	}
	
	public static AbstractSecs1OnTcpIpChannelConnectionLog binded(SocketAddress local) {
		return buildInstance(Secs1OnTcpIpChannelConnectionLogState.Binded, local, null);
	}
	
	public static AbstractSecs1OnTcpIpChannelConnectionLog bindClosed(SocketAddress local) {
		return buildInstance(Secs1OnTcpIpChannelConnectionLogState.BindClosed, local, null);
	}
	
	public static AbstractSecs1OnTcpIpChannelConnectionLog accepted(SocketAddress local, SocketAddress remote) {
		return buildInstance(Secs1OnTcpIpChannelConnectionLogState.Accepted, local, remote);
	}
	
	public static AbstractSecs1OnTcpIpChannelConnectionLog acceptClosed(SocketAddress local, SocketAddress remote) {
		return buildInstance(Secs1OnTcpIpChannelConnectionLogState.AcceptClosed, local, remote);
	}
	
	public static AbstractSecs1OnTcpIpChannelConnectionLog tryConnect(SocketAddress remote) {
		return buildInstance(Secs1OnTcpIpChannelConnectionLogState.TryConnect, null, remote);
	}
	
	public static AbstractSecs1OnTcpIpChannelConnectionLog connected(SocketAddress local, SocketAddress remote) {
		return buildInstance(Secs1OnTcpIpChannelConnectionLogState.Connected, local, remote);
	}
	
	public static AbstractSecs1OnTcpIpChannelConnectionLog connectClosed(SocketAddress local, SocketAddress remote) {
		return buildInstance(Secs1OnTcpIpChannelConnectionLogState.ConnectClosed, local, remote);
	}
	
}
