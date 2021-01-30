package com.shimizukenta.secs.secs1ontcpip;

import java.net.SocketAddress;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.shimizukenta.secs.AbstractSecsLog;

public final class Secs1OnTcpIpConnectionLog extends AbstractSecsLog {
	
	private static final long serialVersionUID = -6683232225197363974L;
	
	private final SocketAddress local;
	private final SocketAddress remote;
	
	private String cacheToStringValue;
	
	private Secs1OnTcpIpConnectionLog(CharSequence subject, LocalDateTime timestamp, SocketAddress local, SocketAddress remote) {
		super(subject, timestamp);
		this.local = local;
		this.remote = remote;
		this.cacheToStringValue = null;
	}

	private Secs1OnTcpIpConnectionLog(CharSequence subject, SocketAddress local, SocketAddress remote) {
		super(subject);
		this.local = local;
		this.remote = remote;
		this.cacheToStringValue = null;
	}
	
	public SocketAddress local() {
		return this.local;
	}
	
	public SocketAddress remote() {
		return this.remote;
	}
	
	@Override
	protected Optional<String> toStringValue() {
		synchronized ( this ) {
			if ( this.cacheToStringValue == null ) {
				List<String> ll = new ArrayList<>();
				
				if ( this.local != null ) {
					ll.add("local:" + local.toString());
				}
				
				if ( this.remote != null ) {
					ll.add("remote:" + remote.toString());
				}
				
				this.cacheToStringValue = ll.stream().collect(Collectors.joining(", "));
			}
			
			if ( this.cacheToStringValue.isEmpty() ) {
				return Optional.empty();
			} else {
				return Optional.of(this.cacheToStringValue);
			}
		}
	}
	
	private static final String subjectTryConnect = "SECS1-ON-TCP/IP Try-Connect";
	
	public static Secs1OnTcpIpConnectionLog tryConnect(SocketAddress remote) {
		return new Secs1OnTcpIpConnectionLog(subjectTryConnect, null, remote);
	}
	
	public static Secs1OnTcpIpConnectionLog tryConnect(SocketAddress remote, LocalDateTime timestamp) {
		return new Secs1OnTcpIpConnectionLog(subjectTryConnect, timestamp, null, remote);
	}
	
	private static final String subjectConnected = "SECS1-ON-TCP/IP Connected";
	
	public static Secs1OnTcpIpConnectionLog connected(SocketAddress local, SocketAddress remote) {
		return new Secs1OnTcpIpConnectionLog(subjectConnected, local, remote);
	}
	
	public static Secs1OnTcpIpConnectionLog connected(SocketAddress local, SocketAddress remote, LocalDateTime timestamp) {
		return new Secs1OnTcpIpConnectionLog(subjectConnected, timestamp, local, remote);
	}
	
	private static final String subjectDisconnected = "SECS1-ON-TCP/IP Disconnected";
	
	public static Secs1OnTcpIpConnectionLog disconnected(SocketAddress local, SocketAddress remote) {
		return new Secs1OnTcpIpConnectionLog(subjectDisconnected, local, remote);
	}
	
	public static Secs1OnTcpIpConnectionLog disconnected(SocketAddress local, SocketAddress remote, LocalDateTime timestamp) {
		return new Secs1OnTcpIpConnectionLog(subjectDisconnected, timestamp, local, remote);
	}
	
}
