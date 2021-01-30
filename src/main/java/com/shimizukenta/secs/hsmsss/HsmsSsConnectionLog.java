package com.shimizukenta.secs.hsmsss;

import java.net.SocketAddress;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.shimizukenta.secs.AbstractSecsLog;

public final class HsmsSsConnectionLog extends AbstractSecsLog {
	
	private static final long serialVersionUID = -8628845793017772982L;
	
	private final SocketAddress local;
	private final SocketAddress remote;
	
	private String cacheValueString;
	
	private HsmsSsConnectionLog(CharSequence subject, LocalDateTime timestamp, SocketAddress local, SocketAddress remote) {
		super(subject, timestamp);
		this.local = local;
		this.remote = remote;
		this.cacheValueString = null;
	}
	
	private HsmsSsConnectionLog(CharSequence subject, SocketAddress local, SocketAddress remote) {
		super(subject);
		this.local = local;
		this.remote = remote;
		this.cacheValueString = null;
	}
	
	@Override
	protected Optional<String> toStringValue() {
		
		synchronized ( this ) {
			
			if ( this.cacheValueString == null ) {
				
				List<String> ll = new ArrayList<>();
				
				if ( this.local != null ) {
					ll.add("local:" + this.local.toString());
				}
				
				if ( this.remote != null ) {
					ll.add("remote:" + this.remote.toString());
				}
				
				this.cacheValueString = ll.stream().collect(Collectors.joining(", "));
			}
			
			if ( this.cacheValueString.isEmpty() ) {
				return Optional.empty();
			} else {
				return Optional.of(this.cacheValueString);
			}
		}
	}
	
	public SocketAddress local() {
		return this.local;
	}
	
	public SocketAddress remote() {
		return this.remote;
	}
	
	private static final String subjectTryBind = "HSMS-SS Try-Bind";
	
	public static HsmsSsConnectionLog tryBind(SocketAddress local) {
		return new HsmsSsConnectionLog(subjectTryBind, local, null);
	}
	
	public static HsmsSsConnectionLog tryBind(SocketAddress local, LocalDateTime timestamp) {
		return new HsmsSsConnectionLog(subjectTryBind, timestamp, local, null);
	}
	
	private static final String subjectBinded = "HSMS-SS Binded";
	
	public static HsmsSsConnectionLog binded(SocketAddress local) {
		return new HsmsSsConnectionLog(subjectBinded, local, null);
	}
	
	public static HsmsSsConnectionLog binded(SocketAddress local, LocalDateTime timestamp) {
		return new HsmsSsConnectionLog(subjectBinded, timestamp, local, null);
	}
	
	private static final String subjectAccepted = "HSMS-SS Accepted";
	
	public static HsmsSsConnectionLog accepted(SocketAddress local, SocketAddress remote) {
		return new HsmsSsConnectionLog(subjectAccepted, local, remote);
	}
	
	public static HsmsSsConnectionLog accepted(SocketAddress local, SocketAddress remote, LocalDateTime timestamp) {
		return new HsmsSsConnectionLog(subjectAccepted, timestamp, local, remote);
	}
	
	private static final String subjectTryConnect = "HSMS-SS Try-Connect";
	
	public static HsmsSsConnectionLog tryConnect(SocketAddress remote) {
		return new HsmsSsConnectionLog(subjectTryConnect, null, remote);
	}
	
	public static HsmsSsConnectionLog tryConnect(SocketAddress remote, LocalDateTime timestamp) {
		return new HsmsSsConnectionLog(subjectTryConnect, timestamp, null, remote);
	}
	
	private static final String subjectConnected = "HSMS-SS Connected";
	
	public static HsmsSsConnectionLog connected(SocketAddress local, SocketAddress remote) {
		return new HsmsSsConnectionLog(subjectConnected, local, remote);
	}
	
	public static HsmsSsConnectionLog connected(SocketAddress local, SocketAddress remote, LocalDateTime timestamp) {
		return new HsmsSsConnectionLog(subjectConnected, timestamp, local, remote);
	}
	
	private static final String subjectChannelClosed = "HSMS-SS Channel-Closed";
	
	public static HsmsSsConnectionLog closed(SocketAddress local, SocketAddress remote) {
		return new HsmsSsConnectionLog(subjectChannelClosed, local, remote);
	}
	
	public static HsmsSsConnectionLog closed(SocketAddress local, SocketAddress remote, LocalDateTime timestamp) {
		return new HsmsSsConnectionLog(subjectChannelClosed, timestamp, local, remote);
	}
	
}
