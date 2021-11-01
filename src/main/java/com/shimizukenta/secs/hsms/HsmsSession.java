package com.shimizukenta.secs.hsms;

import java.util.Optional;

import com.shimizukenta.secs.SecsCommunicator;

public interface HsmsSession extends SecsCommunicator {
	
	public int sessionId();
	
	public Optional<HsmsMessage> send(HsmsMessage msg) throws InterruptedException;
	
}
