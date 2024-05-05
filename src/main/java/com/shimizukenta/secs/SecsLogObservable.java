package com.shimizukenta.secs;

/**
 * SecsLog Observable, add/remove listeners.
 * 
 * @author kenta-shimizu
 *
 */
public interface SecsLogObservable {
	
	/**
	 * Returns {@code true} if add SecsLog listener success, otherwise {@code false}, receive all SecsLog.
	 * 
	 * @param listener the SecsLogListener
	 * @return true if add success, otherwise false
	 */
	public boolean addSecsLogListener(SecsLogListener<? super SecsLog> listener);
	
	/**
	 * Returns {@code true} if remove success, otherwise false.
	 * 
	 * @param listener the SecsLog listener
	 * @return true if remove success, otherwise false.
	 */
	public boolean removeSecsLogListener(SecsLogListener<? super SecsLog> listener);
	
	/**
	 * Returns {@code true} if add SecsThrowableLog listener success, otherwise {@code false}, receive SecsThrowableLog.
	 * 
	 * @param listener the SecsThrowableLog listener
	 * @return true if add success, otherwise false
	 */
	public boolean addSecsThrowableLogListener(SecsLogListener<? super SecsThrowableLog> listener);
	
	/**
	 * Returns {@code true} if remove success, otherwise {@code false}.
	 * 
	 * @param listener the SecsThrowableLog listener
	 * @return true if remove success, otherwise false
	 */
	public boolean removeSecsThrowableLogListener(SecsLogListener<? super SecsThrowableLog> listener);
	
	/**
	 * Returns {@code true} if add TrySend SecsMessage pass through log listener success, otherwise {@code false}, receive Try-Send SecsMessage pass through log.
	 * 
	 * @param listener the SecsMessage pass through listener
	 * @return true
	 */
	public boolean addTrySendSecsMessagePassThroughLogListener(SecsLogListener<? super SecsMessagePassThroughLog> listener);
	
	
	/**
	 * Returns {@code true} if remove success, otherwise false.
	 * 
	 * @param listener the SecsMessage pass through log listener
	 * @return true if remove success, otherwise false
	 */
	public boolean removeTrySendSecsMessagePassThroughLogListener(SecsLogListener<? super SecsMessagePassThroughLog> listener);
	
	/**
	 * Returns {@code true} if add Sended SecsMessage pass through log listener, otherwise {@code false}, receive Sended SecsMessage pass through log.
	 * 
	 * @param listener the SecsMessage pass through log listener
	 * @return {@code true} if add success, otherwise false
	 */
	public boolean addSendedSecsMessagePassThroughLogListener(SecsLogListener<? super SecsMessagePassThroughLog> listener);
	
	/**
	 * Returns {@code trues} if remove success, otherwise {@code false}.
	 * 
	 * @param listener the SecsMessage pass through log listener
	 * @return true if add success, otherwise false
	 */
	public boolean removeSendedSecsMessagePassThroughLogListener(SecsLogListener<? super SecsMessagePassThroughLog> listener);
	
	/**
	 * Returns {@code true} if add Receive SecsMessage pass through log listener, otherwise {@code false}, receive Receive SecsMessage pass through log.
	 * 
	 * @param listener the SecsMessage pass through log listener
	 * @return true if add success, otherwise false
	 */
	public boolean addReceiveSecsMessagePassThroughLogListener(SecsLogListener<? super SecsMessagePassThroughLog> listener);
	
	/**
	 * Returns {@code true} if remove success, otherwise {@code false}.
	 * 
	 * @param listener the SecsMessage pass through log listener
	 * @return true if remove success, otherwise false
	 */
	public boolean removeReceiveSecsMessagePassThroughLogListener(SecsLogListener<? super SecsMessagePassThroughLog> listener);

}
