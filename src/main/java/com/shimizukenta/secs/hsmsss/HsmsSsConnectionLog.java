package com.shimizukenta.secs.hsmsss;

import java.net.SocketAddress;
import java.time.LocalDateTime;

import com.shimizukenta.secs.impl.AbstractSecsConnectionLog;

public final class HsmsSsConnectionLog extends AbstractSecsConnectionLog {
	
	private static final long serialVersionUID = -8628845793017772982L;
	
	private HsmsSsConnectionLog(CharSequence subject, LocalDateTime timestamp, SocketAddress local, SocketAddress remote, boolean isConnecting) {
		super(subject, timestamp, local, remote, isConnecting);
	}
	
	private HsmsSsConnectionLog(CharSequence subject, SocketAddress local, SocketAddress remote, boolean isConnecting) {
		super(subject, local, remote, isConnecting);
	}
	
	private static final String subjectAccepted = "HSMS-SS Accepted";
	
	public static HsmsSsConnectionLog accepted(SocketAddress local, SocketAddress remote) {
		return new HsmsSsConnectionLog(subjectAccepted, local, remote, true);
	}
	
	public static HsmsSsConnectionLog accepted(SocketAddress local, SocketAddress remote, LocalDateTime timestamp) {
		return new HsmsSsConnectionLog(subjectAccepted, timestamp, local, remote, true);
	}
	
	private static final String subjectTryConnect = "HSMS-SS Try-Connect";
	
	public static HsmsSsConnectionLog tryConnect(SocketAddress remote) {
		return new HsmsSsConnectionLog(subjectTryConnect, null, remote, false);
	}
	
	public static HsmsSsConnectionLog tryConnect(SocketAddress remote, LocalDateTime timestamp) {
		return new HsmsSsConnectionLog(subjectTryConnect, timestamp, null, remote, false);
	}
	
	private static final String subjectConnected = "HSMS-SS Connected";
	
	public static HsmsSsConnectionLog connected(SocketAddress local, SocketAddress remote) {
		return new HsmsSsConnectionLog(subjectConnected, local, remote, true);
	}
	
	public static HsmsSsConnectionLog connected(SocketAddress local, SocketAddress remote, LocalDateTime timestamp) {
		return new HsmsSsConnectionLog(subjectConnected, timestamp, local, remote, true);
	}
	
	private static final String subjectChannelClosed = "HSMS-SS Channel-Closed";
	
	public static HsmsSsConnectionLog closed(SocketAddress local, SocketAddress remote) {
		return new HsmsSsConnectionLog(subjectChannelClosed, local, remote, false);
	}
	
	public static HsmsSsConnectionLog closed(SocketAddress local, SocketAddress remote, LocalDateTime timestamp) {
		return new HsmsSsConnectionLog(subjectChannelClosed, timestamp, local, remote, false);
	}
	
}
