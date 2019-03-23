package secs.hsmsSs;

import java.util.concurrent.ExecutorService;

import secs.SecsMessageAnswerManager;

public class HsmsSsMessageAnswerManager extends SecsMessageAnswerManager<HsmsSsMessage> {

	public HsmsSsMessageAnswerManager(ExecutorService es) {
		super(es);
	}
	
	@Override
	protected boolean isWaitAnswerMessage(HsmsSsMessage msg) {
		
		//TODO
		//message-type
		
		return msg.wbit();
	}
}
