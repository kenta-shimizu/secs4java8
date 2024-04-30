package com.shimizukenta.secs.impl;

import java.util.Optional;

import com.shimizukenta.secs.SecsException;
import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.SecsMessageSendable;
import com.shimizukenta.secs.SecsSendMessageException;
import com.shimizukenta.secs.SecsWaitReplyMessageException;
import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.sml.SmlMessage;

public interface SecsMessageSendableImpl extends SecsMessageSendable {
	
	@Override
	default public Optional<SecsMessage> send(int strm, int func, boolean wbit)
			throws SecsSendMessageException,
			SecsWaitReplyMessageException,
			SecsException,
			InterruptedException {
		
		return this.send(strm, func, wbit, Secs2.empty());
	}
	
	@Override
	default public Optional<SecsMessage> send(SecsMessage primaryMsg, int strm, int func, boolean wbit)
			throws SecsSendMessageException,
			SecsWaitReplyMessageException,
			SecsException,
			InterruptedException {
		
		return this.send(primaryMsg, strm, func, wbit, Secs2.empty());
	}
	
	@Override
	default public Optional<SecsMessage> send(SmlMessage sml)
			throws SecsSendMessageException,
			SecsWaitReplyMessageException,
			SecsException,
			InterruptedException {
		
		return this.send(sml.getStream(), sml.getFunction(), sml.wbit(), sml.secs2());
	}
	
	@Override
	default public Optional<SecsMessage> send(SecsMessage primaryMsg, SmlMessage sml)
			throws SecsSendMessageException,
			SecsWaitReplyMessageException,
			SecsException,
			InterruptedException {
		
		return this.send(primaryMsg, sml.getStream(), sml.getFunction(), sml.wbit(), sml.secs2());
	}

}
