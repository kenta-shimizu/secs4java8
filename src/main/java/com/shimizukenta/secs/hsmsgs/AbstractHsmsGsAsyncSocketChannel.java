package com.shimizukenta.secs.hsmsgs;

import java.nio.channels.AsynchronousSocketChannel;
import java.util.List;

import com.shimizukenta.secs.AbstractSecsLog;
import com.shimizukenta.secs.ReadOnlyTimeProperty;
import com.shimizukenta.secs.hsms.AbstractHsmsAsyncSocketChannel;
import com.shimizukenta.secs.hsms.AbstractHsmsLinktest;
import com.shimizukenta.secs.hsms.AbstractHsmsMessage;
import com.shimizukenta.secs.hsms.HsmsException;
import com.shimizukenta.secs.hsms.HsmsMessage;
import com.shimizukenta.secs.hsms.HsmsMessageBuilder;
import com.shimizukenta.secs.hsms.HsmsSendMessageException;
import com.shimizukenta.secs.hsms.HsmsTransactionManager;
import com.shimizukenta.secs.hsms.HsmsWaitReplyMessageException;
import com.shimizukenta.secs.secs2.Secs2BytesParseException;

public abstract class AbstractHsmsGsAsyncSocketChannel extends AbstractHsmsAsyncSocketChannel {

	private final AbstractHsmsGsCommunicator comm;
	
	private final AbstractHsmsLinktest linktest;
	
	public AbstractHsmsGsAsyncSocketChannel(
			AsynchronousSocketChannel channel,
			AbstractHsmsGsCommunicator communicator
			) {
		
		super(channel);
		this.comm = communicator;
		
		this.linktest = new AbstractHsmsGsLinktest(this, communicator) {};
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
	
	@Override
	protected AbstractHsmsMessage buildMessageFromBytes(byte[] header, List<byte[]> bodies) throws Secs2BytesParseException {
		return HsmsGsMessageBuilder.fromBytes(header, bodies);
	}
	
	@Override
	protected AbstractHsmsMessage buildMessageFromMessage(HsmsMessage message) {
		return HsmsGsMessageBuilder.fromMessage(message);
	}
	
	private final HsmsTransactionManager<AbstractHsmsMessage> transMgr = new HsmsTransactionManager<>();
	
	@Override
	protected HsmsTransactionManager<AbstractHsmsMessage> transactionManager() {
		return this.transMgr;
	}
	
	@Override
	protected void notifyLog(AbstractSecsLog log) throws InterruptedException {
		this.comm.notifyLog(log);
	}
	
	@Override
	protected void notifyTrySendHsmsMsgPassThrough(HsmsMessage msg) throws InterruptedException {
		this.comm.notifyTrySendHsmsMessagePassThrough(msg);
	}
	
	@Override
	protected void notifySendedHsmsMsgPassThrough(HsmsMessage msg) throws InterruptedException {
		this.comm.notifySendedHsmsMessagePassThrough(msg);
	}
	
	@Override
	protected void notifyReceiveHsmsMessagePassThrough(HsmsMessage msg) throws InterruptedException {
		this.comm.notifyReceiveHsmsMessagePassThrough(msg);
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
