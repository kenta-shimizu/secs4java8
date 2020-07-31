package com.shimizukenta.secs;

import java.util.concurrent.ExecutorService;

public abstract class AbstractSecsInnerManager {
	
	private final AbstractSecsCommunicator parent;
	
	public AbstractSecsInnerManager(AbstractSecsCommunicator parent) {
		this.parent = parent;
	}
	
	protected ExecutorService executorService() {
		return parent.executorService();
	}
	
	protected void notifyReceiveMessage(SecsMessage msg) {
		parent.notifyReceiveMessage(msg);
	}
	
	protected void notifyLog(SecsLog log) {
		parent.notifyLog(log);
	}
	
	protected void notifyLog(CharSequence subject) {
		parent.notifyLog(subject);
	}
	
	protected void notifyLog(CharSequence subject, Object value) {
		parent.notifyLog(subject, value);
	}
	
	protected void notifyLog(Throwable t) {
		parent.notifyLog(t);
	}
	
	protected void notifyTrySendMessagePassThrough(SecsMessage msg) {
		parent.notifyTrySendMessagePassThrough(msg);
	}
	
	protected void notifySendedMessagePassThrough(SecsMessage msg) {
		parent.notifySendedMessagePassThrough(msg);
	}
	
	protected void notifyReceiveMessagePassThrough(SecsMessage msg) {
		parent.notifyReceiveMessagePassThrough(msg);
	}
	
}
