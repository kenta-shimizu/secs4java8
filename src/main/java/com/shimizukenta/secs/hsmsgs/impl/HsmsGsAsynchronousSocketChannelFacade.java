package com.shimizukenta.secs.hsmsgs.impl;

import java.nio.channels.AsynchronousSocketChannel;

import com.shimizukenta.secs.hsms.AbstractHsmsCommunicatorConfig;
import com.shimizukenta.secs.hsms.HsmsMessage;
import com.shimizukenta.secs.hsms.impl.AbstractHsmsAsynchronousSocketChannelFacade;
import com.shimizukenta.secs.hsms.impl.AbstractHsmsMessage;

public class HsmsGsAsynchronousSocketChannelFacade extends AbstractHsmsAsynchronousSocketChannelFacade {
	
	private final AbstractHsmsGsCommunicator communicator;
	
	public HsmsGsAsynchronousSocketChannelFacade(AbstractHsmsCommunicatorConfig config,
			AsynchronousSocketChannel channel,
			AbstractHsmsGsCommunicator communicator) {
		
		super(config, channel);
		
		this.communicator = communicator;
	}

	@Override
	protected void notifyTrySendHsmsMessagePassThrough(HsmsMessage msg) throws InterruptedException {
		this.communicator.notifyTrySendHsmsMessagePassThrough(msg);
	}

	@Override
	protected void notifySendedHsmsMessagePassThrough(HsmsMessage msg) throws InterruptedException {
		this.communicator.notifySendedHsmsMessagePassThrough(msg);
	}

	@Override
	protected void notifyReceiveHsmsMessagePassThrough(HsmsMessage msg) throws InterruptedException {
		this.communicator.notifyReceiveHsmsMessagePassThrough(msg);
	}
	
	@Override
	protected AbstractHsmsMessage buildLinktestHsmsMessage() {
		return this.communicator.getHsmsGsMessageBuilder().buildLinktestRequest();
	}

	@Override
	protected boolean offerThrowableToLog(Throwable t) {
		return this.communicator.offerThrowableToLog(t);
	}
	
}
