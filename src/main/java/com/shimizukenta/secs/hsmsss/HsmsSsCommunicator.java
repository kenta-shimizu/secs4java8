package com.shimizukenta.secs.hsmsss;

import java.io.IOException;
import java.util.Optional;

import com.shimizukenta.secs.SecsCommunicator;
import com.shimizukenta.secs.SecsException;
import com.shimizukenta.secs.SecsSendMessageException;
import com.shimizukenta.secs.SecsWaitReplyMessageException;
import com.shimizukenta.secs.secs2.Secs2;

/**
 * This interface is implementation of HSMS-SS (SEMI-E37.1)<br />
 * To create newInstance, {@link #newInstance(HsmsSsCommunicatorConfig)}<br />
 * To create newInstance and open, {@link #open(HsmsSsCommunicatorConfig)}<br />
 * To linktest, {@link #linktest()}
 * 
 * @author kenta-shimizu
 *
 */
public interface HsmsSsCommunicator extends SecsCommunicator {
	
	/**
	 * create new HSMS-SS-Communicator instance.
	 * 
	 * @param config
	 * @return new HSMS-SS-Communicator instance
	 */
	public static HsmsSsCommunicator newInstance(HsmsSsCommunicatorConfig config) {
		
		switch ( config.protocol().get() ) {
		case PASSIVE: {
			
			if ( config.rebindIfPassive().getMilliSeconds() >= 0L ) {
				
				return new AbstractHsmsSsRebindPassiveCommunicator(config) {};
				
			} else {
				
				return new AbstractHsmsSsPassiveCommunicator(config) {};
			}
			/* break; */
		}
		case ACTIVE: {
			
			return new AbstractHsmsSsActiveCommunicator(config) {};
			/* break; */
		}
		default: {
			
			throw new IllegalStateException("undefined protocol: " + config.protocol());
		}
		}
	}
	
	/**
	 * create new HSMS-SS-Communicator instance and {@link #open()}
	 * 
	 * @param config
	 * @return new HSMS-SS-Communicator instance
	 * @throws IOException
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
	 * Blocking-method<br />
	 * 
	 * @return true if success
	 * @throws InterruptedException
	 * @throws SecsException
	 */
	public boolean linktest() throws InterruptedException;
	
	/**
	 * Blocking-method.<br />
	 * send Primary-HsmsSsMessage,<br />
	 * wait until Reply-HsmsSsMessage if exist.
	 * 
	 * @param msg
	 * @return reply-HsmsSsMessage if exist
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<HsmsSsMessage> send(HsmsSsMessage msg)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException,
			InterruptedException;
	
	/**
	 * Create header-only HsmsSsMessage.
	 * 
	 * @param header
	 * @return HsmsSsMessage
	 */
	public HsmsSsMessage createHsmsSsMessage(byte[] header);
	
	/**
	 * Create HsmsSsMessage.
	 * 
	 * @param header
	 * @param body
	 * @return HsmsSsMessage
	 */
	public HsmsSsMessage createHsmsSsMessage(byte[] header, Secs2 body);
	
	/**
	 * Create Select-Request.
	 * 
	 * @return Select-Request-HsmsSsMessage
	 */
	public HsmsSsMessage createSelectRequest();
	
	/**
	 * Create Select-Response.
	 * 
	 * @param primary-message
	 * @param select-status
	 * @return Select-Response-HsmsSsMessage
	 */
	public HsmsSsMessage createSelectResponse(HsmsSsMessage primary, HsmsSsMessageSelectStatus status);
	
	/**
	 * Create Deselect-Request.
	 * 
	 * @return Deselect-Request-HsmsSsMessage
	 */
	public HsmsSsMessage createDeselectRequest();
	
	/**
	 * Create Deselect-Response.
	 * 
	 * @param primary-message
	 * @return Deselect-Response-HsmsSsMessage
	 */
	public HsmsSsMessage createDeselectResponse(HsmsSsMessage primary);
	
	/**
	 * Create Linktest-Request.
	 * 
	 * @return Linktest-Request-HsmsSsMessage
	 */
	public HsmsSsMessage createLinktestRequest();
	
	/**
	 * Create Linktest-Response.
	 * 
	 * @param primary-message
	 * @return Linktest-Response-HsmsSsMessage
	 */
	public HsmsSsMessage createLinktestResponse(HsmsSsMessage primary);
	
	/**
	 * Create Reject-Request.
	 * 
	 * @param ref
	 * @param reason
	 * @return Reject-Request-HsmsSsMessage
	 */
	public HsmsSsMessage createRejectRequest(HsmsSsMessage ref, HsmsSsMessageRejectReason reason);
	
	/**
	 * Create Separate-Request.
	 * 
	 * @return Separate-Request-HsmsSsMessage
	 */
	public HsmsSsMessage createSeparateRequest();

}
