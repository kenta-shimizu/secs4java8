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
import com.shimizukenta.secs.hsmsgs.impl.AbstractHsmsGsActiveCommunicator;
import com.shimizukenta.secs.hsmsgs.impl.AbstractHsmsGsPassiveCommunicator;
import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.sml.SmlMessage;

/**
 * This interface is implementation of HSMS-GS (SEMI-E37.2).
 * 
 * <ul>
 * <li>To create newInstance, {@link #newInstance(HsmsGsCommunicatorConfig)}</li>
 * <li>To create newInstance and open, {@link #open(HsmsGsCommunicatorConfig)}</li>
 * </ul>
 * <ul>
 * <li>To get all sessions, {@link #getSessions()}</li>
 * <li>To get session by id, {@link #getSession(int)}</li>
 * </ul>
 * <ul>
 * <li>To log communicating, {@link #addSecsLogListener(SecsLogListener)}</li>
 * </ul>
 * 
 * @author kenta-shimizu
 *
 */
public interface HsmsGsCommunicator extends OpenAndCloseable {
	
	/**
	 * create new HSMS-GS-Communicator instance.
	 * 
	 * @param config the HSMS-GS config
	 * @return new HSMS-GS-Communicator instance
	 */
	public static HsmsGsCommunicator newInstance(HsmsGsCommunicatorConfig config) {
		
		final HsmsConnectionMode mode = config.connectionMode().get();
		
		switch (mode) {
		case PASSIVE: {
			return new AbstractHsmsGsPassiveCommunicator(config) {};
			/* break; */
		}
		case ACTIVE: {
			return new AbstractHsmsGsActiveCommunicator(config) {};
			/* break; */
		}
		default: {
			
			throw new IllegalStateException("undefined connecton-mode: " + mode);
		}
		}
	}
	
	/**
	 * Create new HSMS-GS-Communicator instance and {@link #open()}.
	 * 
	 * @param config the HSMS-GS config
	 * @return new HSMS-GS-Communicator instance
	 * @throws IOException if open failed
	 */
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
	
	/**
	 * Returns Sessions.
	 * 
	 * @return sessions
	 */
	public Set<HsmsSession> getSessions();
	
	/**
	 * Return Session by Id.
	 * 
	 * @param sessionId the Session-ID
	 * @return Session
	 * @throws HsmsGsUnknownSessionIdException if Session-ID unknown
	 */
	public HsmsSession getSession(int sessionId) throws HsmsGsUnknownSessionIdException;
	
	/**
	 * Returns true if exist.
	 * 
	 * @param sessionId the Session-ID
	 * @return true if exist.
	 */
	public boolean existSession(int sessionId);
	
	/**
	 * send shortcut.
	 * 
	 * @param sessionId the Session-ID
	 * @param strm SECS-II-Stream-Number
	 * @param func SECS-II-Function-Number
	 * @param wbit SECS-II-WBit, set true if w-bit is 1
	 * @return Reply-Message if exist
	 * @throws SecsSendMessageException if send failed
	 * @throws SecsWaitReplyMessageException if receive message failed, e.g. Timeout-T3
	 * @throws SecsException if SECS communicate failed
	 * @throws InterruptedException if interrupted
	 */
	public Optional<SecsMessage> send(
			int sessionId,
			int strm,
			int func,
			boolean wbit)
					throws SecsSendMessageException,
					SecsWaitReplyMessageException,
					SecsException,
					InterruptedException;
	
	/**
	 * send shortcut
	 * 
	 * @param sessionId the Session-ID
	 * @param strm SECS-II-Stream-Number
	 * @param func SECS-II-Function-Number
	 * @param wbit SECS-II-WBit, set true if w-bit is 1
	 * @param secs2 SECS-II-data, Not accept null
	 * @return Reply-Message if exist
	 * @throws SecsSendMessageException if send failed
	 * @throws SecsWaitReplyMessageException if receive message failed, e.g. Timeout-T3
	 * @throws SecsException if SECS communicate failed
	 * @throws InterruptedException if interrupted
	 */
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
	
	/**
	 * send 
	 * @param sessionId the Session-ID
	 * @param primaryMsg the primary message
	 * @param strm SECS-II-Stream-Number
	 * @param func SECS-II-Function-Number
	 * @param wbit SECS-II-WBit, set true if w-bit is 1
	 * @return Reply-Message if exist
	 * @throws SecsSendMessageException if send failed
	 * @throws SecsWaitReplyMessageException if receive message failed, e.g. Timeout-T3
	 * @throws SecsException if SECS communicate failed
	 * @throws InterruptedException if interrupted
	 */
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
	
	/**
	 * send shortcut.
	 * 
	 * @param sessionId the Session-ID
	 * @param primaryMsg the primary message
	 * @param strm SECS-II-Stream-Number
	 * @param func SECS-II-Function-Number
	 * @param wbit SECS-II-WBit, set true if w-bit is 1
	 * @param secs2 SECS-II-data, Not accept null
	 * @return Reply-Message if exist
	 * @throws SecsSendMessageException if send failed
	 * @throws SecsWaitReplyMessageException if receive message failed, e.g. Timeout-T3
	 * @throws SecsException if SECS communicate failed
	 * @throws InterruptedException if interrupted
	 */
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
	
	/**
	 * send shortcut
	 * 
	 * @param sessionId the Session-ID
	 * @param sml the SML Message
	 * @return Reply-Message if exist
	 * @throws SecsSendMessageException if send failed
	 * @throws SecsWaitReplyMessageException if receive message failed, e.g. Timeout-T3
	 * @throws SecsException if SECS communicate failed
	 * @throws InterruptedException if interrupted
	 */
	public Optional<SecsMessage> send(
			int sessionId,
			SmlMessage sml)
					throws SecsSendMessageException,
					SecsWaitReplyMessageException,
					SecsException,
					InterruptedException;
	
	/**
	 * send shortcut.
	 * 
	 * @param sessionId the Session-ID
	 * @param primaryMsg the primary message
	 * @param sml the SML Message
	 * @return Reply-Message if exist
	 * @throws SecsSendMessageException if send failed
	 * @throws SecsWaitReplyMessageException if receive message failed, e.g. Timeout-T3
	 * @throws SecsException if SECS communicate failed
	 * @throws InterruptedException if interrupted
	 */
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
	
	/**
	 * Add Listener to get SecsMesssage before sending.
	 * 
	 * @param lstnr Not accept {@code null}
	 * @return {@code true} if add success.
	 */
	public boolean addTrySendHsmsMessagePassThroughListener(HsmsMessagePassThroughListener lstnr);
	
	/**
	 * Remove Listener.
	 * 
	 * @param lstnr Not accept {@code null}
	 * @return {@code true} if remove success
	 */
	public boolean removeTrySendHsmsMessagePassThroughListener(HsmsMessagePassThroughListener lstnr);	
	
	/**
	 * Add Listener to get sended SecsMesssage.
	 * 
	 * @param lstnr Not accept {@code null}
	 * @return {@code true} if add success
	 */
	public boolean addSendedHsmsMessagePassThroughListener(HsmsMessagePassThroughListener lstnr);
	
	/**
	 * Remove Listener.
	 * 
	 * @param lstnr Not accept {@code null}
	 * @return {@code true} if remove success
	 */
	public boolean removeSendedHsmsMessagePassThroughListener(HsmsMessagePassThroughListener lstnr);	
	
	/**
	 * Add Listener to receive both Primary and Reply Message.
	 * 
	 * @param lstnr Not accept {@code null}
	 * @return {@code true} if add success
	 */
	public boolean addReceiveHsmsMessagePassThroughListener(HsmsMessagePassThroughListener lstnr);
	
	/**
	 * Remove Listener.
	 * 
	 * @param lstnr Not accept {@code null}
	 * @return {@code true} if remove success
	 */
	public boolean removeReceiveHsmsMessagePassThroughListener(HsmsMessagePassThroughListener lstnr);
	
}
