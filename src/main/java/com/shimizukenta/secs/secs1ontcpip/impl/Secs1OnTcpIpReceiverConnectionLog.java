package com.shimizukenta.secs.secs1ontcpip.impl;

import java.net.SocketAddress;
import java.time.LocalDateTime;

import com.shimizukenta.secs.impl.AbstractSecsConnectionLog;

public class Secs1OnTcpIpReceiverConnectionLog extends AbstractSecsConnectionLog {
	
	private static final long serialVersionUID = -3465160390661274918L;
	
	private Secs1OnTcpIpReceiverConnectionLog(CharSequence subject, LocalDateTime timestamp, SocketAddress local, SocketAddress remote, boolean isConnecting) {
		super(subject, timestamp, local, remote, isConnecting);
	}

	private Secs1OnTcpIpReceiverConnectionLog(CharSequence subject, SocketAddress local, SocketAddress remote, boolean isConnecting) {
		super(subject, local, remote, isConnecting);
	}
	
	private static final String subjectTryBind = "SECS1-ON-TCP/IP-Receiver Try-Bind";
	
	public static Secs1OnTcpIpReceiverConnectionLog tryBInd(SocketAddress local) {
		return new Secs1OnTcpIpReceiverConnectionLog(subjectTryBind, local, null, false);
	}
	
	public static Secs1OnTcpIpReceiverConnectionLog tryBind(SocketAddress local, LocalDateTime timestamp) {
		return new Secs1OnTcpIpReceiverConnectionLog(subjectTryBind, timestamp, local, null, false);
	}
	
	private static final String subjectBinded = "SECS1-ON-TCP/IP-Receiver Binded";
	
	public static Secs1OnTcpIpReceiverConnectionLog binded(SocketAddress local) {
		return new Secs1OnTcpIpReceiverConnectionLog(subjectBinded, local, null, false);
	}
	
	public static Secs1OnTcpIpReceiverConnectionLog binded(SocketAddress local, LocalDateTime timestamp) {
		return new Secs1OnTcpIpReceiverConnectionLog(subjectBinded, timestamp, local, null, false);
	}
	
	private static final String subjectAccepted = "SECS1-ON-TCP/IP-Receiver Accepted";
	
	public static Secs1OnTcpIpReceiverConnectionLog accepted(SocketAddress local, SocketAddress remote) {
		return new Secs1OnTcpIpReceiverConnectionLog(subjectAccepted, local, remote, true);
	}
	
	public static Secs1OnTcpIpReceiverConnectionLog accepted(SocketAddress local, SocketAddress remote, LocalDateTime timestamp) {
		return new Secs1OnTcpIpReceiverConnectionLog(subjectAccepted, timestamp, local, remote, true);
	}
	
	private static final String subjectChannelClosed = "SECS1-ON-TCP/IP-Receiver Channel-Close";
	
	public static Secs1OnTcpIpReceiverConnectionLog channelClosed(SocketAddress local, SocketAddress remote) {
		return new Secs1OnTcpIpReceiverConnectionLog(subjectChannelClosed, local, remote, false);
	}
	
	public static Secs1OnTcpIpReceiverConnectionLog channelClosed(SocketAddress local, SocketAddress remote, LocalDateTime timestamp) {
		return new Secs1OnTcpIpReceiverConnectionLog(subjectChannelClosed, timestamp, local, remote, false);
	}
	
	private static final String subjectServerClosed = "SECS1-ON-TCP/IP-Receiver Server-Close";
	
	public static Secs1OnTcpIpReceiverConnectionLog serverClosed(SocketAddress local) {
		return new Secs1OnTcpIpReceiverConnectionLog(subjectServerClosed, local, null, false);
	}
	
	public static Secs1OnTcpIpReceiverConnectionLog serverClosed(SocketAddress local, LocalDateTime timestamp) {
		return new Secs1OnTcpIpReceiverConnectionLog(subjectServerClosed, timestamp, local, null, false);
	}
	
}
