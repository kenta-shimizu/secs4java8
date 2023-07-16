package com.shimizukenta.secs.hsmsgs.impl;

import java.util.Optional;

import com.shimizukenta.secs.hsms.HsmsException;
import com.shimizukenta.secs.hsms.HsmsMessage;
import com.shimizukenta.secs.hsms.HsmsSendMessageException;
import com.shimizukenta.secs.hsms.HsmsWaitReplyMessageException;
import com.shimizukenta.secs.hsms.impl.AbstractHsmsAsyncSocketChannel;
import com.shimizukenta.secs.hsms.impl.AbstractHsmsLinktest;
import com.shimizukenta.secs.hsms.impl.AbstractHsmsSession;
import com.shimizukenta.secs.local.property.BooleanProperty;
import com.shimizukenta.secs.local.property.TimeoutProperty;

public abstract class AbstractHsmsGsLinktest extends AbstractHsmsLinktest {
	
	private final AbstractHsmsAsyncSocketChannel asyncChannel;
	private final AbstractHsmsGsCommunicator comm;
	
	public AbstractHsmsGsLinktest(
			AbstractHsmsAsyncSocketChannel asyncChannel,
			AbstractHsmsGsCommunicator communicator
			) {
		
		super();
		this.asyncChannel = asyncChannel;
		this.comm = communicator;
	}
	
	@Override
	protected TimeoutProperty timer() {
		return this.comm.config().linktestTime();
	}
	
	@Override
	protected BooleanProperty doLinktest() {
		return this.comm.config().doLinkTest();
	}
	
	@Override
	protected Optional<HsmsMessage> send()
			throws HsmsSendMessageException,
			HsmsWaitReplyMessageException,
			HsmsException,
			InterruptedException {
		
		for (AbstractHsmsSession s : this.comm.getAbstractHsmsSessions()) {
			return this.asyncChannel.sendLinktestRequest(s);
		}
		
		return Optional.empty();
	}
	
}
