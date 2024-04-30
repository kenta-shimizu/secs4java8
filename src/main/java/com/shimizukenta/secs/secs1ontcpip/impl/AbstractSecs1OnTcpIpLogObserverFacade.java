package com.shimizukenta.secs.secs1ontcpip.impl;

import java.net.SocketAddress;
import java.util.concurrent.Executor;

import com.shimizukenta.secs.SecsLogListener;
import com.shimizukenta.secs.impl.AbstractQueueObserver;
import com.shimizukenta.secs.secs1.AbstractSecs1CommunicatorConfig;
import com.shimizukenta.secs.secs1.impl.AbstractSecs1LogObserverFacade;
import com.shimizukenta.secs.secs1ontcpip.Secs1OnTcpIpChannelConnectionLog;
import com.shimizukenta.secs.secs1ontcpip.Secs1OnTcpIpLogObservable;

public abstract class AbstractSecs1OnTcpIpLogObserverFacade extends AbstractSecs1LogObserverFacade
		implements Secs1OnTcpIpLogObservable {
	
	private class Secs1OnTcpIpChannelConnectionLogObserver extends AbstractQueueObserver<SecsLogListener<? super Secs1OnTcpIpChannelConnectionLog>, Secs1OnTcpIpChannelConnectionLog> {
		
		public Secs1OnTcpIpChannelConnectionLogObserver(Executor executor) {
			super(executor);
		}
		
		@Override
		protected void notifyValueToListener(SecsLogListener<? super Secs1OnTcpIpChannelConnectionLog> listener, Secs1OnTcpIpChannelConnectionLog value) {
			listener.received(value);
		}
	}
	
	private final AbstractSecs1CommunicatorConfig config;
	
	private final Secs1OnTcpIpChannelConnectionLogObserver channelConnectionLogObserver;
	
	public AbstractSecs1OnTcpIpLogObserverFacade(AbstractSecs1CommunicatorConfig config, Executor executor) {
		super(config, executor);
		
		this.config = config;
		
		this.channelConnectionLogObserver = new Secs1OnTcpIpChannelConnectionLogObserver(executor);
	}
	
	private boolean offerSecs1OnTcpIpChannelConnection(AbstractSecs1OnTcpIpChannelConnectionLog log) {
		log.subjectHeader(this.config.logSubjectHeader().toString());
		boolean f = this.channelConnectionLogObserver.offer(log);
		this.offerToAllLog(log);
		return f;
	}
	
	public boolean offerHsmsChannelConnectionTryBind(SocketAddress local) {
		return this.offerSecs1OnTcpIpChannelConnection(AbstractSecs1OnTcpIpChannelConnectionLog.tryBind(local));
	}
	
	public boolean offerSecs1OnTcpIpChannelConnectionBinded(SocketAddress local) {
		return this.offerSecs1OnTcpIpChannelConnection(AbstractSecs1OnTcpIpChannelConnectionLog.binded(local));
	}
	
	public boolean offerSecs1OnTcpIpChannelConnectionBindClosed(SocketAddress local) {
		return this.offerSecs1OnTcpIpChannelConnection(AbstractSecs1OnTcpIpChannelConnectionLog.bindClosed(local));
	}
	
	public boolean offerSecs1OnTcpIpChannelConnectionAccepted(SocketAddress local, SocketAddress remote) {
		return this.offerSecs1OnTcpIpChannelConnection(AbstractSecs1OnTcpIpChannelConnectionLog.accepted(local, remote));
	}
	
	public boolean offerSecs1OnTcpIpChannelConnectionAcceptClosed(SocketAddress local, SocketAddress remote) {
		return this.offerSecs1OnTcpIpChannelConnection(AbstractSecs1OnTcpIpChannelConnectionLog.acceptClosed(local, remote));
	}
	
	public boolean offerSecs1OnTcpIpChannelConnectionTryConnect(SocketAddress remote) {
		return this.offerSecs1OnTcpIpChannelConnection(AbstractSecs1OnTcpIpChannelConnectionLog.tryConnect(remote));
	}
	
	public boolean offerSecs1OnTcpIpChannelConnectionConnected(SocketAddress local, SocketAddress remote) {
		return this.offerSecs1OnTcpIpChannelConnection(AbstractSecs1OnTcpIpChannelConnectionLog.connected(local, remote));
	}

	public boolean offerSecs1OnTcpIpChannelConnectionConnectClosed(SocketAddress local, SocketAddress remote) {
		return this.offerSecs1OnTcpIpChannelConnection(AbstractSecs1OnTcpIpChannelConnectionLog.connectClosed(local, remote));
	}

}
