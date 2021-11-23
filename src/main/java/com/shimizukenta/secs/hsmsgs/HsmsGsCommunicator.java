package com.shimizukenta.secs.hsmsgs;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

import com.shimizukenta.secs.OpenAndCloseable;
import com.shimizukenta.secs.SecsCommunicatableStateChangeBiListener;
import com.shimizukenta.secs.SecsException;
import com.shimizukenta.secs.SecsLogListener;
import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.SecsMessageReceiveBiListener;
import com.shimizukenta.secs.SecsSendMessageException;
import com.shimizukenta.secs.SecsWaitReplyMessageException;
import com.shimizukenta.secs.hsms.HsmsConnectionMode;
import com.shimizukenta.secs.hsms.HsmsMessagePassThroughListener;
import com.shimizukenta.secs.hsms.HsmsSession;
import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.sml.SmlMessage;

/**
 * 
 * 
 * @author kenta-shimizu
 *
 */
public interface HsmsGsCommunicator extends OpenAndCloseable {
	
	public static HsmsGsCommunicator newInstance(HsmsGsCommunicatorConfig config) {
		
		final HsmsConnectionMode mode = config.connectionMode().get();
		
		switch (mode) {
		case PASSIVE: {
			
			//TODO
			
			return null;
			/* break; */
		}
		case ACTIVE: {
			
			//TODO
			
			return null;
			/* break; */
		}
		default: {
			
			throw new IllegalStateException("undefined connecton-mode: " + mode);
		}
		}
	}
	
	public static HsmsGsCommunicator open(HsmsGsCommunicatorConfig config) throws IOException {
		
		final HsmsGsCommunicator inst = newInstance(config);
		
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
	
	public Set<HsmsSession> getSessions();
	
	public HsmsSession getSession(int sessionId) throws HsmsGsUnknownSessionIdException;
	
	public boolean existSession(int sessionId);
	
	public Optional<SecsMessage> send(
			int sessionId,
			int strm,
			int func,
			boolean wbit)
					throws SecsSendMessageException,
					SecsWaitReplyMessageException,
					SecsException,
					InterruptedException;
	
	public Optional<SecsMessage> send(
			int sessionId,
			int strm,
			int func,
			boolean wbit,
			Secs2 secs2)
					throws SecsSendMessageException,
					SecsWaitReplyMessageException,
					SecsException,
					InterruptedException;
	
	public Optional<SecsMessage> send(
			int sessionId,
			SecsMessage primaryMsg,
			int strm,
			int func,
			boolean wbit)
					throws SecsSendMessageException,
					SecsWaitReplyMessageException,
					SecsException,
					InterruptedException;
	
	public Optional<SecsMessage> send(
			int sessionId,
			SecsMessage primaryMsg,
			int strm,
			int func,
			boolean wbit,
			Secs2 secs2)
					throws SecsSendMessageException,
					SecsWaitReplyMessageException,
					SecsException,
					InterruptedException;
	
	public Optional<SecsMessage> send(
			int sessionId,
			SmlMessage sml)
					throws SecsSendMessageException,
					SecsWaitReplyMessageException,
					SecsException,
					InterruptedException;
	
	public Optional<? extends SecsMessage> send(
			int sessionId,
			SecsMessage primaryMsg,
			SmlMessage sml)
					throws SecsSendMessageException,
					SecsWaitReplyMessageException,
					SecsException,
					InterruptedException;
	
	/**
	 * Add Listener to receive Primary-Message.
	 * 
	 * <p>
	 * This Listener not receive Reply-Message.<br />
	 * </p>
	 * 
	 * @param lstnr Not accept {@code null}
	 * @return {@code true} if add success in all sessions
	 */
	public boolean addSecsMessageReceiveListener(SecsMessageReceiveBiListener lstnr);
	
	/**
	 * Remove Listener.
	 * 
	 * @param lstnr Not accept {@code null}
	 * @return {@code true} if remove success in all sessions
	 */
	public boolean removeSecsMessageReceiveListener(SecsMessageReceiveBiListener lstnr);
	
	/**
	 * Add Listener to get communicate-state-changed.
	 * 
	 * <p>
	 * Blocking-Listener.<br />
	 * Pass through quickly.<br />
	 * </p>
	 * 
	 * @param lstnr Not accept {@code null}
	 * @return {@code true} if add success in all sessions
	 */
	public boolean addSecsCommunicatableStateChangeListener(SecsCommunicatableStateChangeBiListener lstnr);
	
	/**
	 * Remove Listener.
	 * 
	 * @param lstnr Not accept {@code null}
	 * @return {@code true} if remove success in all sessions
	 */
	public boolean removeSecsCommunicatableStateChangeListener(SecsCommunicatableStateChangeBiListener lstnr);
	
	/**
	 * Add Listener to log Communicating.
	 * 
	 * @param lstnr Not accept {@code null}
	 * @return {@code true} if add success
	 */
	public boolean addSecsLogListener(SecsLogListener lstnr);
	
	/**
	 * Remove Listener
	 * 
	 * @param lstnr Not accept {@code null}
	 * @return {@code true} if remove success
	 */
	public boolean removeSecsLogListener(SecsLogListener lstnr);
	
	
	public boolean addTrySendHsmsMessagePassThroughListener(HsmsMessagePassThroughListener lstnr);
	public boolean removeTrySendHsmsMessagePassThroughListener(HsmsMessagePassThroughListener lstnr);	
	
	public boolean addSendedHsmsMessagePassThroughListener(HsmsMessagePassThroughListener lstnr);
	public boolean removeSendedHsmsMessagePassThroughListener(HsmsMessagePassThroughListener lstnr);	
	
	public boolean addReceiveHsmsMessagePassThroughListener(HsmsMessagePassThroughListener lstnr);
	public boolean removeReceiveHsmsMessagePassThroughListener(HsmsMessagePassThroughListener lstnr);
	
}
