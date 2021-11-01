package com.shimizukenta.secs.hsms;

import com.shimizukenta.secs.SecsMessage;

public interface HsmsMessage extends SecsMessage {
	
	public HsmsMessageType messageType();
	
	public boolean isDataMessage();
	
	public byte pType();
	
	public byte sType();
	
}
