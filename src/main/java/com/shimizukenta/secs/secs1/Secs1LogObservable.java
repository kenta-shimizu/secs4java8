package com.shimizukenta.secs.secs1;

import com.shimizukenta.secs.SecsLogListener;
import com.shimizukenta.secs.SecsLogObservable;

/**
 * SECS-I-Log Observable.
 * 
 * @author kenta-shimizu
 *
 */
public interface Secs1LogObservable extends SecsLogObservable {
	
	/**
	 * Returns {@code true} if add Try-send SECS-I message pass through log listener success, otherwise {@code false}, pass through SECS-I message.
	 * 
	 * @param listener the SECS-I message pass through log listener
	 * @return true if add success, othewise false
	 */
	public boolean addTrySendSecs1MessagePassThroughLogListener(SecsLogListener<? super Secs1MessagePassThroughLog> listener);
	
	/**
	 * Returns {@code true} if remove success, otherwise {@code false}.
	 * 
	 * @param listener the SECS-I message pass through log listener
	 * @return true if remove success, othewise false
	 */
	public boolean removeTrySendSecs1MessagePassThroughLogListener(SecsLogListener<? super Secs1MessagePassThroughLog> listener);
	
	/**
	 * Returns {@code true} if add Sended SECS-I message pass through log listener success, otherwise {@code false}, pass through SECS-I message.
	 * 
	 * @param listener the SECS-I message pass through log listener
	 * @return true if add success, othewise false
	 */
	public boolean addSendedSecs1MessagePassThroughLogListener(SecsLogListener<? super Secs1MessagePassThroughLog> listener);
	
	/**
	 * Returns {@code true} if remove success, otherwise {@code false}.
	 * 
	 * @param listener the SECS-I message pass through log listener
	 * @return true if remove success, othewise false
	 */
	public boolean removeSendedSecs1MessagePassThroughLogListener(SecsLogListener<? super Secs1MessagePassThroughLog> listener);
	
	/**
	 * Returns {@code true} if add Receive SECS-I message pass through log listener success, otherwise {@code false}, pass through SECS-I message.
	 * 
	 * @param listener the SECS-I message pass through log listener
	 * @return true if add success, othewise false
	 */
	public boolean addReceiveSecs1MessagePassThroughLogListener(SecsLogListener<? super Secs1MessagePassThroughLog> listener);
	
	/**
	 * Returns {@code true} if remove success, otherwise {@code false}.
	 * 
	 * @param listener the SECS-I message pass through log listener
	 * @return true if remove success, othewise false
	 */
	public boolean removeReceiveSecs1MessagePassThroughLogListener(SecsLogListener<? super Secs1MessagePassThroughLog> listener);
	
	/**
	 * Returns {@code true} if add Try-send SECS-I message block pass through log listener success, otherwise {@code false}, pass through SECS-I message block.
	 * 
	 * @param listener the SECS-I message block pass through log listener
	 * @return true if add success, othewise false
	 */
	public boolean addTrySendSecs1MessageBlockPassThroughLogListener(SecsLogListener<? super Secs1MessageBlockPassThroughLog> listener);
	
	/**
	 * Returns {@code true} if remove success, otherwise {@code false}.
	 * 
	 * @param listener the SECS-I message block log pass through log listener
	 * @return true if remove success, othewise false
	 */
	public boolean removeTrySendSecs1MessageBlockPassThroughLogListener(SecsLogListener<? super Secs1MessageBlockPassThroughLog> listener);
	
	/**
	 * Returns {@code true} if add Sended SECS-I message block pass through log listener success, otherwise {@code false}, pass through SECS-I message block.
	 * 
	 * @param listener the SECS-I message block pass through log listener
	 * @return true if add success, othewise false
	 */
	public boolean addSendedSecs1MessageBlockPassThroughLogListener(SecsLogListener<? super Secs1MessageBlockPassThroughLog> listener);
	
	/**
	 * Returns {@code true} if remove success, otherwise {@code false}.
	 * 
	 * @param listener the SECS-I message block log pass through log listener
	 * @return true if remove success, othewise false
	 */
	public boolean removeSendedSecs1MessageBlockPassThroughLogListener(SecsLogListener<? super Secs1MessageBlockPassThroughLog> listener);
	
	/**
	 * Returns {@code true} if add Receive SECS-I message block pass through log listener success, otherwise {@code false}, pass through SECS-I message block.
	 * 
	 * @param listener the SECS-I message block pass through log listener
	 * @return true if add success, othewise false
	 */
	public boolean addReceiveSecs1MessageBlockPassThroughLogListener(SecsLogListener<? super Secs1MessageBlockPassThroughLog> listener);
	
	/**
	 * Returns {@code true} if remove success, otherwise {@code false}.
	 * 
	 * @param listener the SECS-I message block log pass through log listener
	 * @return true if remove success, othewise false
	 */
	public boolean removeReceiveSecs1MessageBlockPassThroughLogListener(SecsLogListener<? super Secs1MessageBlockPassThroughLog> listener);
	
}
