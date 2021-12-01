package com.shimizukenta.secs.hsmsss;

import java.nio.channels.AsynchronousSocketChannel;

import com.shimizukenta.secs.ReadOnlyTimeProperty;
import com.shimizukenta.secs.hsms.AbstractHsmsAsyncSocketChannel;
import com.shimizukenta.secs.hsms.AbstractHsmsMessage;
import com.shimizukenta.secs.hsms.HsmsException;
import com.shimizukenta.secs.hsms.HsmsMessageBuilder;
import com.shimizukenta.secs.hsms.HsmsSendMessageException;
import com.shimizukenta.secs.hsms.HsmsTransactionManager;
import com.shimizukenta.secs.hsms.HsmsWaitReplyMessageException;

public abstract class AbstractHsmsSsAsyncSocketChannel extends AbstractHsmsAsyncSocketChannel {
	
	private final AbstractHsmsSsCommunicator comm;
	private final AbstractHsmsSsLinktest linktest;
	
	public AbstractHsmsSsAsyncSocketChannel(
			AsynchronousSocketChannel channel,
			AbstractHsmsSsCommunicator communicator
			) {
		
		super(channel);
		this.comm = communicator;
		this.linktest = new AbstractHsmsSsLinktest(this, comm) {};
	}
	
	@Override
	public void linktesting()
			throws HsmsSendMessageException,
			HsmsWaitReplyMessageException,
			HsmsException,
			InterruptedException {
		
		this.linktest.testing();
	}
	
	@Override
	protected HsmsMessageBuilder messageBuilder() {
		return this.comm.msgBuilder();
	}
	
	private final HsmsTransactionManager<AbstractHsmsMessage> transMgr = new HsmsTransactionManager<>();
	
	@Override
	protected HsmsTransactionManager<AbstractHsmsMessage> transactionManager() {
		return this.transMgr;
	}
	
	@Override
	protected ReadOnlyTimeProperty timeoutT3() {
		return this.comm.config().timeout().t3();
	}
	
	@Override
	protected ReadOnlyTimeProperty timeoutT6() {
		return this.comm.config().timeout().t6();
	}
	
	@Override
	protected ReadOnlyTimeProperty timeoutT8() {
		return this.comm.config().timeout().t8();
	}
	
	@Override
	protected void resetLinktestTimer() {
		this.linktest.resetTimer();
	}
	
}
