package com.shimizukenta.secs.hsmsgs;

import java.util.Optional;

import com.shimizukenta.secs.ReadOnlyTimeProperty;
import com.shimizukenta.secs.hsms.AbstractHsmsAsyncSocketChannel;
import com.shimizukenta.secs.hsms.AbstractHsmsLinktest;
import com.shimizukenta.secs.hsms.AbstractHsmsSession;
import com.shimizukenta.secs.hsms.HsmsException;
import com.shimizukenta.secs.hsms.HsmsMessage;
import com.shimizukenta.secs.hsms.HsmsSendMessageException;
import com.shimizukenta.secs.hsms.HsmsWaitReplyMessageException;

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
	protected ReadOnlyTimeProperty timer() {
		return this.comm.config().linktest();
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
