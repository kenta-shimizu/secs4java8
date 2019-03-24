package secs.hsmsSs;

import java.util.concurrent.ExecutorService;

import secs.SecsMessageReplyManager;

public class HsmsSsMessageReplyManager extends SecsMessageReplyManager<HsmsSsMessage> {

	public HsmsSsMessageReplyManager(ExecutorService es) {
		super(es);
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
}
