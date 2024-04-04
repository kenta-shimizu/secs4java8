package com.shimizukenta.secs.hsmsss.impl;

import java.nio.channels.AsynchronousSocketChannel;

import com.shimizukenta.secs.hsms.AbstractHsmsCommunicatorConfig;
import com.shimizukenta.secs.hsms.HsmsMessage;
import com.shimizukenta.secs.hsms.impl.AbstractHsmsAsynchronousSocketChannelFacade;
import com.shimizukenta.secs.hsms.impl.AbstractHsmsMessage;

public class HsmsSsAsynchronousSocketChannelFacade extends AbstractHsmsAsynchronousSocketChannelFacade {
	
	private final AbstractHsmsSsCommunicator communicator;
	
	public HsmsSsAsynchronousSocketChannelFacade(AbstractHsmsCommunicatorConfig config,
			AsynchronousSocketChannel channel,
			AbstractHsmsSsCommunicator communicator) {
		
		super(config, channel);
		this.communicator = communicator;
	}

	@Override
	protected void notifyTrySendHsmsMessagePassThrough(HsmsMessage msg) throws InterruptedException {
		this.communicator.notifyTrySendHsmsMessagePassThrough(msg);
		
		// notifyLog
	}

	@Override
	protected void notifySendedHsmsMessagePassThrough(HsmsMessage msg) throws InterruptedException {
		this.communicator.notifySendedHsmsMessagePassThrough(msg);
		
		// notifyLog
	}

	@Override
	protected void notifyReceiveHsmsMessagePassThrough(HsmsMessage msg) throws InterruptedException {
		this.communicator.notifyReceiveHsmsMessagePassThrough(msg);
		
		// notifyLog
	}

	@Override
	protected AbstractHsmsMessage buildLinktestHsmsMessage() {
		return this.communicator.getHsmsSmMessageBuilder().buildLinktestRequest(this.communicator.getSession());
	}
	
	@Override
	protected boolean offerThrowableToLog(Throwable t) {
		
		//TODO
		
		try {
			this.communicator.notifyLog(t);
		}
		catch (InterruptedException ignore) {
		}
		
		return true;
	}
	
}
