package com.shimizukenta.secs.hsms;

import java.util.Optional;

import com.shimizukenta.secs.SecsMessageSendable;

public interface HsmsMessageSendable extends SecsMessageSendable {
	
	public Optional<HsmsMessage> send(HsmsMessage msg) throws InterruptedException;
}
