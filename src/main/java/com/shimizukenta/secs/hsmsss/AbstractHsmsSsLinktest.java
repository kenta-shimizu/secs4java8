package com.shimizukenta.secs.hsmsss;

import java.util.Optional;

import com.shimizukenta.secs.local.property.BooleanProperty;
import com.shimizukenta.secs.local.property.TimeoutProperty;
import com.shimizukenta.secs.hsms.AbstractHsmsAsyncSocketChannel;
import com.shimizukenta.secs.hsms.AbstractHsmsLinktest;
import com.shimizukenta.secs.hsms.HsmsException;
import com.shimizukenta.secs.hsms.HsmsMessage;
import com.shimizukenta.secs.hsms.HsmsSendMessageException;
import com.shimizukenta.secs.hsms.HsmsWaitReplyMessageException;

public abstract class AbstractHsmsSsLinktest extends AbstractHsmsLinktest {
	
	private final AbstractHsmsAsyncSocketChannel asyncChannel;
	private final AbstractHsmsSsCommunicator comm;
	
	public AbstractHsmsSsLinktest(
			AbstractHsmsAsyncSocketChannel asyncChannel,
			AbstractHsmsSsCommunicator communicator
			) {
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
		
		return this.asyncChannel.sendLinktestRequest(this.comm.getSession());
	}
	
}
