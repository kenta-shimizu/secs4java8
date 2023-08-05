package com.shimizukenta.secs.hsmsss;

import java.io.IOException;

import com.shimizukenta.secs.hsms.HsmsCommunicator;
import com.shimizukenta.secs.hsms.HsmsConnectionMode;
import com.shimizukenta.secs.hsms.HsmsConnectionModeIllegalStateException;
import com.shimizukenta.secs.hsmsss.impl.AbstractHsmsSsActiveCommunicator;
import com.shimizukenta.secs.hsmsss.impl.AbstractHsmsSsPassiveCommunicator;

/**
 * This interface is implementation of HSMS-SS (SEMI-E37.1).
 * 
 * <ul>
 * <li>To create newInstance, {@link #newInstance(HsmsSsCommunicatorConfig)}</li>
 * <li>To create newInstance and open, {@link #open(HsmsSsCommunicatorConfig)}</li>
 * </ul>
 * 
 * @author kenta-shimizu
 *
 */
public interface HsmsSsCommunicator extends HsmsCommunicator {
	
	/**
	 * create new HSMS-SS-Communicator instance.
	 * 
	 * @param config the HSMS-SS config
	 * @return new HSMS-SS-Communicator instance
	 */
	public static HsmsSsCommunicator newInstance(HsmsSsCommunicatorConfig config) {
		
		final HsmsConnectionMode mode = config.connectionMode().get();
		
		switch ( mode ) {
		case PASSIVE: {
			return new AbstractHsmsSsPassiveCommunicator(config) {};
			/* break; */
		}
		case ACTIVE: {
			return new AbstractHsmsSsActiveCommunicator(config) {};
			/* break; */
		}
		default: {
			
			throw new HsmsConnectionModeIllegalStateException("undefined connecton-mode: " + mode);
		}
		}
	}
	
	/**
	 * Create new HSMS-SS-Communicator instance and {@link #open()}.
	 * 
	 * @param config the HSMS-SS config
	 * @return new HSMS-SS-Communicator instance
	 * @throws IOException if open failed
	 */
	public static HsmsSsCommunicator open(HsmsSsCommunicatorConfig config) throws IOException {
		
		final HsmsSsCommunicator inst = newInstance(config);
		
		try {
			inst.open();
		}
		catch ( IOException e ) {
			
			try {
				inst.close();
			}
			catch ( IOException giveup ) {
			}
			
			throw e;
		}
		
		return inst;
	}
	
	/**
	 * Add Listener to receive Primary-Message.
	 * 
	 * <p>
	 * This Listener not receive Reply-Message.<br />
	 * </p>
	 * 
	 * @param biListener the HSMS Message ans HSMS-SS communicator listener
	 * @return {@code true} if add success
	 * @throws NullPointerException if biListener is null
	 */
	public boolean addHsmsMessageReceiveBiListener(HsmsSsMessageReceiveBiListener biListener);
	
	/**
	 * Remove listener.
	 * 
	 * @param biListener the HSMS Message ans HSMS-SS communicator listener
	 * @return {@code true} if remove success
	 * @throws NullPointerException if biListener is null
	 */
	public boolean removeHsmsMessageReceiveBiListener(HsmsSsMessageReceiveBiListener biListener);
	
	/**
	 * Add Listener to get communicate-state-changed.
	 * 
	 * <p>
	 * Blocking-Listener.<br />
	 * Pass through quickly.<br />
	 * </p>
	 * 
	 * @param biListener the state change listener
	 * @return {@code true} if add success
	 * @throws NullPointerException if biListener is null
	 */
	public boolean addHsmsCommunicateStateChangeBiListener(HsmsSsCommunicateStateChangeBiListener biListener);
	
	/**
	 * Remove listener.
	 * 
	 * @param biListener the state change listener
	 * @return {@code true} if remove success
	 * @throws NullPointerException if biListener is null
	 */
	public boolean removeHsmsCommunicateStateChangeBiListener(HsmsSsCommunicateStateChangeBiListener biListener);
	
	
	/**
	 * Add Listener to get HsmsMesssage before sending.
	 * 
	 * @param biListener the pass through message bi-listener
	 * @return true if add success
	 */
	public boolean addTrySendHsmsMessagePassThroughBiListener(HsmsSsMessagePassThroughBiListener biListener);
	
	/**
	 * Remove listener.
	 * 
	 * @param biListener the pass through message bi-listener
	 * @return true if remove success
	 */
	public boolean removeTrySendHsmsMessagePassThroughBiListener(HsmsSsMessagePassThroughBiListener biListener);	
	
	/**
	 * Add Listener to get HsmsMesssage sended.
	 * 
	 * @param biListener the pass through message bi-listener
	 * @return true if add success
	 */
	public boolean addSendedHsmsMessagePassThroughBiListener(HsmsSsMessagePassThroughBiListener biListener);
	
	/**
	 * Remove listener.
	 * 
	 * @param biListener the pass through message bi-listener
	 * @return true if remove success
	 */
	public boolean removeSendedHsmsMessagePassThroughBiListener(HsmsSsMessagePassThroughBiListener biListener);	
	
	/**
	 * Add Listener to receive both Primary and Reply Message.
	 * 
	 * @param biListener the pass through message bi-listener
	 * @return true if add success
	 */
	public boolean addReceiveHsmsMessagePassThroughBiListener(HsmsSsMessagePassThroughBiListener biListener);
	
	/**
	 * Remove listener.
	 * 
	 * @param biListener the pass through message bi-listener
	 * @return true if remove success
	 */
	public boolean removeReceiveHsmsMessagePassThroughBiListener(HsmsSsMessagePassThroughBiListener biListener);	
	
}
