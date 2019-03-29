package secs.hsmsSs;

import java.util.Optional;
import java.util.concurrent.ExecutorService;

import secs.SecsMessageSendReplyManager;
import secs.SecsSendMessageConsumer;
import secs.SecsSendMessageException;
import secs.SecsTimeout;
import secs.SecsWaitReplyMessageException;

public class HsmsSsMessageSendReplyManager extends SecsMessageSendReplyManager<HsmsSsMessage> {

	protected HsmsSsMessageSendReplyManager(ExecutorService execServ
			, SecsTimeout timeout
			, SecsSendMessageConsumer<HsmsSsMessage> sendMessageConsumer) {
		
		super(execServ, timeout, sendMessageConsumer);
	}
	
	@Override
	protected boolean isWaitReplyMessage(HsmsSsMessage msg) {
		
		HsmsSsMessageType type = HsmsSsMessageType.get(msg);
		
		switch (type) {
		case SELECT_REQ:
		case LINKTEST_REQ: {
			
			return true;
			/* break; */
		}
		case DATA: {
			
			return super.isWaitReplyMessage(msg);
			/* break; */
		}
		default: {
			
			return false;
		}
		}
	}
	
	protected float getTimeoutByMessageType(HsmsSsMessage msg) {
		
		HsmsSsMessageType mt = HsmsSsMessageType.get(msg);
		
		switch ( mt ) {
		case DATA: {
			
			return secsTimeout().t3();
			/* break; */
		}
		case SELECT_REQ:
		case LINKTEST_REQ: {
			
			return secsTimeout().t6();
			/* break; */
		}
		default: {
			
			throw new IllegalArgumentException(mt.toString() + " is not required reply-message");
		}
		}
	}
	
	protected void throwSecsSendMessageException(HsmsSsMessage msg)
			throws SecsSendMessageException {
		
		throw new HsmsSsSendMessageException(msg);
	}
	
	protected void throwSecsWaitReplyMessageException(HsmsSsMessage msg)
			throws SecsWaitReplyMessageException {
		
		HsmsSsMessageType mt = HsmsSsMessageType.get(msg);
		
		switch ( mt ) {
		case DATA: {
			
			throw new HsmsSsTimeoutT3Exception(msg);
			/* break; */
		}
		case SELECT_REQ:
		case LINKTEST_REQ: {
			
			throw new HsmsSsTimeoutT6Exception(msg);
			/* break; */
		}
		default: {
			
			throw new IllegalArgumentException(mt.toString() + " is not required reply-message");
		}
		}
	}
	
	protected Optional<HsmsSsMessage> getOptionalOrThrow(HsmsSsMessage primary, HsmsSsMessage reply)
			throws SecsWaitReplyMessageException {
		
		HsmsSsMessageType mt = HsmsSsMessageType.get(reply);
		
		switch ( mt ) {
		case REJECT_REQ: {
			
			throw new HsmsSsRejectException(primary);
			/* break; */
		}
		default : {
			
			return Optional.of(reply);
		}
		}
	}
	
}
