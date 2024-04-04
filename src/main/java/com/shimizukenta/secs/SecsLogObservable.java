package com.shimizukenta.secs;

/**
 * SecsLog Observable, add/remove listeners.
 * 
 * @author kenta-shimizu
 *
 */
public interface SecsLogObservable {
	
	public boolean addSecsLogListener(SecsLogListener<? super SecsLog> listener);
	
	public boolean removeSecsLogListener(SecsLogListener<? super SecsLog> listener);
	
	public boolean addSecsThrowableLogListener(SecsLogListener<? super SecsThrowableLog> listener);
	
	public boolean removeSecsThrowableLogListener(SecsLogListener<? super SecsThrowableLog> listener);
	
	public boolean addTrySendSecsMessagePassThroughLogListener(SecsLogListener<? super SecsMessagePassThroughLog> listener);
	
	public boolean removeTrySendSecsMessagePassThroughLogListener(SecsLogListener<? super SecsMessagePassThroughLog> listener);
	
	public boolean addSendedSecsMessagePassThroughLogListener(SecsLogListener<? super SecsMessagePassThroughLog> listener);
	
	public boolean removeSendedSecsMessagePassThroughLogListener(SecsLogListener<? super SecsMessagePassThroughLog> listener);
	
	public boolean addReceiveSecsMessagePassThroughLogListener(SecsLogListener<? super SecsMessagePassThroughLog> listener);
	
	public boolean removeReceiveSecsMessagePassThroughLogListener(SecsLogListener<? super SecsMessagePassThroughLog> listener);

}
