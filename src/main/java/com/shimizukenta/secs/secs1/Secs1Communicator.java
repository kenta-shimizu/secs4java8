package com.shimizukenta.secs.secs1;

import com.shimizukenta.secs.SecsCommunicator;

/**
 * This interface is implementation of SECS-I (SEMI-E4).
 * 
 * @author kenta-shimizu
 *
 */
public interface Secs1Communicator extends SecsCommunicator, Secs1GemAccessor, Secs1MessageObservable {
	
}
