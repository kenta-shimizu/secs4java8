package com.shimizukenta.secs.hsmsss;

import java.time.LocalDateTime;

import com.shimizukenta.secs.AbstractSecsLog;

public final class HsmsSsConnectionLog extends AbstractSecsLog {
	
	private static final long serialVersionUID = -8628845793017772982L;
	
	private HsmsSsConnectionLog(CharSequence subject, LocalDateTime timestamp, Object value) {
		super(subject, timestamp, value);
	}

	private HsmsSsConnectionLog(CharSequence subject, Object value) {
		super(subject, value);
	}
	
	private static final String subjectTryBind = "HSMS-SS Try-Bind";
	
	public static HsmsSsConnectionLog tryBind(Object socketAddressString) {
		return new HsmsSsConnectionLog(subjectTryBind, socketAddressString);
	}
	
	public static HsmsSsConnectionLog tryBind(Object socketAddressString, LocalDateTime timestamp) {
		return new HsmsSsConnectionLog(subjectTryBind, timestamp, socketAddressString);
	}
	
	private static final String subjectBinded = "HSMS-SS Binded";
	
	public static HsmsSsConnectionLog binded(Object socketAddressString) {
		return new HsmsSsConnectionLog(subjectBinded, socketAddressString);
	}
	
	public static HsmsSsConnectionLog binded(Object socketAddressString, LocalDateTime timestamp) {
		return new HsmsSsConnectionLog(subjectBinded, timestamp, socketAddressString);
	}
	
	private static final String subjectAccepted = "HSMS-SS Accepted";
	
	public static HsmsSsConnectionLog accepted(Object channelString) {
		return new HsmsSsConnectionLog(subjectAccepted, channelString);
	}
	
	public static HsmsSsConnectionLog accepted(Object channelString, LocalDateTime timestamp) {
		return new HsmsSsConnectionLog(subjectAccepted, timestamp, channelString);
	}
	
	private static final String subjectTryConnect = "HSMS-SS Try-Connect";
	
	public static HsmsSsConnectionLog tryConnect(Object channelString) {
		return new HsmsSsConnectionLog(subjectTryConnect, channelString);
	}
	
	public static HsmsSsConnectionLog tryConnect(Object channelString, LocalDateTime timestamp) {
		return new HsmsSsConnectionLog(subjectTryConnect, timestamp, channelString);
	}
	
	private static final String subjectConnected = "HSMS-SS Connected";
	
	public static HsmsSsConnectionLog connected(Object channelString) {
		return new HsmsSsConnectionLog(subjectConnected, channelString);
	}
	
	public static HsmsSsConnectionLog connected(Object channelString, LocalDateTime timestamp) {
		return new HsmsSsConnectionLog(subjectConnected, timestamp, channelString);
	}
	
	private static final String subjectChannelClosed = "HSMS-SS Channel-Closed";
	
	public static HsmsSsConnectionLog closed(Object channelString) {
		return new HsmsSsConnectionLog(subjectChannelClosed, channelString);
	}
	
	public static HsmsSsConnectionLog closed(Object channelString, LocalDateTime timestamp) {
		return new HsmsSsConnectionLog(subjectChannelClosed, timestamp, channelString);
	}
	
}
