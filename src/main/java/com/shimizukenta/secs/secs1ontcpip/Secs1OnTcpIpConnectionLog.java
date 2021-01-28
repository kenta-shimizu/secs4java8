package com.shimizukenta.secs.secs1ontcpip;

import java.time.LocalDateTime;

import com.shimizukenta.secs.AbstractSecsLog;

public final class Secs1OnTcpIpConnectionLog extends AbstractSecsLog {
	
	private static final long serialVersionUID = -6683232225197363974L;
	
	private Secs1OnTcpIpConnectionLog(CharSequence subject, LocalDateTime timestamp, Object value) {
		super(subject, timestamp, value);
	}

	private Secs1OnTcpIpConnectionLog(CharSequence subject, Object value) {
		super(subject, value);
	}
	
	private static final String subjectTryConnect = "SECS1-ON-TCP/IP Try-Connect";
	
	public static Secs1OnTcpIpConnectionLog tryConnect(Object connectionString) {
		return new Secs1OnTcpIpConnectionLog(subjectTryConnect, connectionString);
	}
	
	public static Secs1OnTcpIpConnectionLog tryConnect(Object connectionString, LocalDateTime timestamp) {
		return new Secs1OnTcpIpConnectionLog(subjectTryConnect, timestamp, connectionString);
	}
	
	private static final String subjectConnected = "SECS1-ON-TCP/IP Connected";
	
	public static Secs1OnTcpIpConnectionLog connected(Object connectionString) {
		return new Secs1OnTcpIpConnectionLog(subjectConnected, connectionString);
	}
	
	public static Secs1OnTcpIpConnectionLog connected(Object connectionString, LocalDateTime timestamp) {
		return new Secs1OnTcpIpConnectionLog(subjectConnected, timestamp, connectionString);
	}
	
	private static final String subjectDisconnected = "SECS1-ON-TCP/IP Disconnected";
	
	public static Secs1OnTcpIpConnectionLog disconnected(Object connectionString) {
		return new Secs1OnTcpIpConnectionLog(subjectDisconnected, connectionString);
	}
	
	public static Secs1OnTcpIpConnectionLog disconnected(Object connectionString, LocalDateTime timestamp) {
		return new Secs1OnTcpIpConnectionLog(subjectDisconnected, timestamp, connectionString);
	}
	
}
