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
	
	public boolean addTrySendSecs1MessagePassThroughLogListener(SecsLogListener<? super Secs1MessagePassThroughLog> listener);
	
	public boolean removeTrySendSecs1MessagePassThroughLogListener(SecsLogListener<? super Secs1MessagePassThroughLog> listener);
	
	public boolean addSendedSecs1MessagePassThroughLogListener(SecsLogListener<? super Secs1MessagePassThroughLog> listener);
	
	public boolean removeSendedSecs1MessagePassThroughLogListener(SecsLogListener<? super Secs1MessagePassThroughLog> listener);
	
	public boolean addReceiveSecs1MessagePassThroughLogListener(SecsLogListener<? super Secs1MessagePassThroughLog> listener);
	
	public boolean removeReceiveSecs1MessagePassThroughLogListener(SecsLogListener<? super Secs1MessagePassThroughLog> listener);
	
	public boolean addTrySendSecs1MessageBlockPassThroughLogListener(SecsLogListener<? super Secs1MessageBlockPassThroughLog> listener);
	
	public boolean removeTrySendSecs1MessageBlockPassThroughLogListener(SecsLogListener<? super Secs1MessageBlockPassThroughLog> listener);
	
	public boolean addSendedSecs1MessageBlockPassThroughLogListener(SecsLogListener<? super Secs1MessageBlockPassThroughLog> listener);
	
	public boolean removeSendedSecs1MessageBlockPassThroughLogListener(SecsLogListener<? super Secs1MessageBlockPassThroughLog> listener);
	
	public boolean addReceiveSecs1MessageBlockPassThroughLogListener(SecsLogListener<? super Secs1MessageBlockPassThroughLog> listener);
	
	public boolean removeReceiveSecs1MessageBlockPassThroughLogListener(SecsLogListener<? super Secs1MessageBlockPassThroughLog> listener);
	
}
