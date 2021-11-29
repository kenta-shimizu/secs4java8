package com.shimizukenta.secs;

import java.io.IOException;
import java.util.Optional;

import com.shimizukenta.secs.gem.Gem;
import com.shimizukenta.secs.hsmsss.HsmsSsCommunicator;
import com.shimizukenta.secs.hsmsss.HsmsSsCommunicatorConfig;
import com.shimizukenta.secs.secs1ontcpip.Secs1OnTcpIpCommunicator;
import com.shimizukenta.secs.secs1ontcpip.Secs1OnTcpIpCommunicatorConfig;
import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.sml.SmlMessage;

/**
 * This interface is implementation of SECS-Communicating, open/close communicating, receive/send SECS-Message.<br />
 * 
 * <p>
 * To create HSMS-SS-Communicator instance,
 * {@link HsmsSsCommunicator#newInstance(HsmsSsCommunicatorConfig)}<br />
 * To create SECS-I-on-TCP/IP-Communicator instance,
 * {@link Secs1OnTcpIpCommunicator#newInstance(Secs1OnTcpIpCommunicatorConfig)}<br />
 * </p>
 * <p>
 * To open communicating,
 * {@link #open()}<br />
 * To close communicating,
 * {@link #close()}<br />
 * </p>
 * <p>
 * To receive Primary-Message,
 * {@link #addSecsMessageReceiveListener(SecsMessageReceiveListener)}<br />
 * </p>
 * <p>
 * To send Primary-Message and receive Reply-Message, 
 * {@link #send(int, int, boolean, Secs2)}<br />
 * To send Primary-(Header-only)-Message and receive Reply-Message,
 * {@link #send(int, int, boolean)}<br />
 * To send Primary-Message by SML and receive Reply-Message,
 * {@link #send(SmlMessage)}<br />
 * To send Reply-Message,
 * {@link #send(SecsMessage, int, int, boolean, Secs2)}<br />
 * To send Reply-(Header-only)-Message,
 * {@link #send(SecsMessage, int, int, boolean)}<br />
 * To send Reply-Message by SML,
 * {@link #send(SecsMessage, SmlMessage)}<br />
 * </p>
 * <p>
 * To get communicate-state-changed,
 * {@link #addSecsCommunicatableStateChangeListener(SecsCommunicatableStateChangeListener)}<br />
 * To wait until communicatable, {@link #waitUntilCommunicatable()}<br />
 * To wait until not communicatable, {@link #waitUntilNotCommunicatable()}<br />
 * </p>
 * <p>
 * To access GEM-interface, {@link #gem()}<br />
 * </p>
 * <p>
 * To log communicating,
 * {@link #addSecsLogListener(SecsLogListener)}<br />
 * </p>
 * 
 * @author kenta-shimizu
 *
 */
public interface SecsCommunicator extends OpenAndCloseable {
	
	/**
	 * Returns GEM-interface.
	 * 
	 * @return GEM-interface-instance
	 */
	public Gem gem();
	
	
	/**
	 * Returns true if communicatable.
	 * 
	 * <p>
	 * Communicatable is send and receive message.
	 * </p>
	 * 
	 * @return true if communicatable
	 */
	public boolean isCommunicatable();
	
	/**
	 * Wait until communicatable.
	 * 
	 * <p>
	 * Blocking-method.<br />
	 * If Already communicatable, do nothing.<br />
	 * </p>
	 * 
	 * @throws InterruptedException
	 */
	public void waitUntilCommunicatable() throws InterruptedException;
	
	/**
	 * Wait until <strong>NOT</strong> communicatable.
	 * 
	 * <p>
	 * Blocking-method.<br />
	 * If Already not communicatable, do nothing.<br />
	 * </p>
	 * 
	 * @throws InterruptedException
	 */
	public void waitUntilNotCommunicatable() throws InterruptedException;
	
	/**
	 * Open and wait until communicatable.
	 * 
	 * <p>
	 * Blocking-method.<br />
	 * If Already opened, do nothing.<br />
	 * If Already communicatable, do nothing.<br />
	 * </p>
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void openAndWaitUntilCommunicatable() throws IOException, InterruptedException;
	
	/**
	 * Is equipment getter.
	 * 
	 * @return {@code true} if communicator is equipment.
	 */
	public boolean isEquip();
	
	/**
	 * Returns Communicator DEVICE-ID.
	 * 
	 * <p>
	 * Returns -1 if not support DEVICE-ID communicator.<br />
	 * </p>
	 * 
	 * @return DEVICE-ID
	 */
	public int deviceId();
	
	/**
	 * Returns Communicator SESSION-ID.
	 * 
	 * <p>
	 * Returns {@code -1} if not support SESSION-ID communicator.<br />
	 * </p>
	 * 
	 * @return SESSION-ID
	 */
	public int sessionId();
	
	/**
	 * Send Primary-(Header-only)-Message and receive Reply-Message.
	 * 
	 * <p>
	 * Blocking-method.<br />
	 * Wait until sended Primay-Message and received Reply-Message if exist.<br />
	 * </p>
	 * 
	 * @param strm SECS-II-Stream-Number
	 * @param func SECS-II-Function-Number
	 * @param wbit SECS-II-WBit, set {@code true} if w-bit is 1
	 * @return Reply-Message if exist
	 * @throws SecsSendMessageException if send failed
	 * @throws SecsWaitReplyMessageException if receive message failed, e.g. Timeout-T3
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> send(int strm, int func, boolean wbit)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException;
	
	/**
	 * Send Primary-Message and receive Reply-Message.
	 * 
	 * <p>
	 * Blocking-method.<br />
	 * Wait until sended Primay-Message and received Reply-Message if exist.<br />
	 * </p>
	 * 
	 * @param strm SECS-II-Stream-Number
	 * @param func SECS-II-Function-Number
	 * @param wbit SECS-II-WBit, set {@code true} if w-bit is 1
	 * @param secs2 SECS-II-data, Not accept {@code null}
	 * @return Reply-Message if exist
	 * @throws SecsSendMessageException if send failed
	 * @throws SecsWaitReplyMessageException if receive message failed, e.g. Timeout-T3
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> send(int strm, int func, boolean wbit, Secs2 secs2)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException;
	
	/**
	 * Send Reply-(Header-only)-Message.
	 * 
	 * <p>
	 * Blocking-method.<br />
	 * Wait until sended.<br />
	 * </p>
	 * 
	 * @param primaryMsg Primary-Message
	 * @param strm SECS-II-Stream-Number
	 * @param func SECS-II-Function-Number
	 * @param wbit SECS-II-WBit, set {@code false}
	 * @return {@code Optional.empty()}
	 * @throws SecsSendMessageException if send failed
	 * @throws SecsWaitReplyMessageException if receive message failed, e.g. Timeout-T3
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> send(SecsMessage primaryMsg, int strm, int func, boolean wbit)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException;
	
	/**
	 * Send Reply-Message.
	 * 
	 * <p>
	 * Blocking-method.<br />
	 * Wait until sended.<br />
	 * </p>
	 * 
	 * @param primaryMsg Primary-Message
	 * @param strm SECS-II-Stream-Number
	 * @param func SECS-II-Function-Number
	 * @param wbit SECS-II-WBit, set {@code false}
	 * @param secs2 SECS-II-data, Not accept {@code null}
	 * @return {@code Optional.empty()}
	 * @throws SecsSendMessageException if send failed
	 * @throws SecsWaitReplyMessageException if receive message failed, e.g. Timeout-T3
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> send(SecsMessage primaryMsg, int strm, int func, boolean wbit, Secs2 secs2)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException;
	
	/**
	 * Send Primary-Message by SML and receive Reply-Message<br />
	 * 
	 * <p>
	 * Blocking-method.<br />
	 * Wait until sended Primay-Message and received Reply-Message if exist.<br />
	 * </p>
	 * 
	 * @param sml
	 * @return Reply-Message if exist
	 * @throws SecsSendMessageException if send failed
	 * @throws SecsWaitReplyMessageException if receive message failed, e.g. Timeout-T3
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> send(SmlMessage sml)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException;
	
	/**
	 * Send Reply-Message by SML.
	 * 
	 * <p>
	 * Blocking-method.<br />
	 * Wait until sended.<br />
	 * </p>
	 * 
	 * @param primaryMsg Primary-Message.
	 * @param sml
	 * @return {@code Optional.empty()}
	 * @throws SecsSendMessageException if send failed
	 * @throws SecsWaitReplyMessageException if receive message failed, e.g. Timeout-T3
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> send(SecsMessage primaryMsg, SmlMessage sml)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException;
	
	
	/* Secs-Message Receive Listener */
	
	/**
	 * Add Listener to receive Primary-Message.
	 * 
	 * <p>
	 * This Listener not receive Reply-Message.<br />
	 * </p>
	 * 
	 * @param lstnr Not accept {@code null}
	 * @return {@code true} if add success
	 */
	public boolean addSecsMessageReceiveListener(SecsMessageReceiveListener lstnr);
	
	/**
	 * Remove Listener.
	 * 
	 * @param lstnr Not accept {@code null}
	 * @return {@code true} if remove success
	 */
	public boolean removeSecsMessageReceiveListener(SecsMessageReceiveListener lstnr);
	
	
	/**
	 * Add Listener to receive Primary-Message.
	 * 
	 * <p>
	 * This Listener not receive Reply-Message.<br />
	 * </p>
	 * 
	 * @param lstnr Not accept {@code null}
	 * @return {@code true} if add success
	 */
	public boolean addSecsMessageReceiveListener(SecsMessageReceiveBiListener lstnr);
	
	/**
	 * Remove Listener.
	 * 
	 * @param lstnr Not accept {@code null}
	 * @return {@code true} if remove success
	 */
	public boolean removeSecsMessageReceiveListener(SecsMessageReceiveBiListener lstnr);
	
	
	/* Secs-Log Receive Listener */
	
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
	
	
	/* Secs-Communicatable-State-Changed-Listener */
	
	/**
	 * Add Listener to get communicate-state-changed.
	 * 
	 * <p>
	 * Blocking-Listener.<br />
	 * Pass through quickly.<br />
	 * </p>
	 * 
	 * @param lstnr Not accept {@code null}
	 * @return {@code true} if add success
	 */
	public boolean addSecsCommunicatableStateChangeListener(SecsCommunicatableStateChangeListener lstnr);
	
	/**
	 * Remove Listener.
	 * 
	 * @param lstnr Not accept {@code null}
	 * @return {@code true} if remove success
	 */
	public boolean removeSecsCommunicatableStateChangeListener(SecsCommunicatableStateChangeListener lstnr);	
	
	/**
	 * Add Listener to get communicate-state-changed.
	 * 
	 * <p>
	 * Blocking-Listener.<br />
	 * Pass through quickly.<br />
	 * </p>
	 * 
	 * @param lstnr Not accept {@code null}
	 * @return {@code true} if add success
	 */
	public boolean addSecsCommunicatableStateChangeListener(SecsCommunicatableStateChangeBiListener lstnr);
	
	/**
	 * Remove Listener.
	 * 
	 * @param lstnr Not accept {@code null}
	 * @return {@code true} if remove success
	 */
	public boolean removeSecsCommunicatableStateChangeListener(SecsCommunicatableStateChangeBiListener lstnr);

	/* Try-Send Secs-Message Pass-through Listener */
	
	/**
	 * Add Listener to get SecsMesssage before sending.
	 * 
	 * @param lstnr Not accept {@code null}
	 * @return {@code true} if add success.
	 */
	public boolean addTrySendMessagePassThroughListener(SecsMessagePassThroughListener lstnr);
	
	/**
	 * Remove Listener.
	 * 
	 * @param lstnr Not accept {@code null}
	 * @return {@code true} if remove success
	 */
	public boolean removeTrySendMessagePassThroughListener(SecsMessagePassThroughListener lstnr);	
	
	
	/* Sended Secs-Message Pass-through Listener */
	
	/**
	 * Add Listener to get sended SecsMesssage.
	 * 
	 * @param lstnr Not accept {@code null}
	 * @return {@code true} if add success
	 */
	public boolean addSendedMessagePassThroughListener(SecsMessagePassThroughListener lstnr);
	
	/**
	 * Remove Listener.
	 * 
	 * @param lstnr Not accept {@code null}
	 * @return {@code true} if remove success
	 */
	public boolean removeSendedMessagePassThroughListener(SecsMessagePassThroughListener lstnr);	
	
	
	/* Receive Secs-Message Pass-through Listener */
	
	/**
	 * Add Listener to receive both Primary and Reply Message.
	 * 
	 * @param lstnr Not accept {@code null}
	 * @return {@code true} if add success
	 */
	public boolean addReceiveMessagePassThroughListener(SecsMessagePassThroughListener lstnr);
	
	/**
	 * Remove Listener.
	 * 
	 * @param lstnr Not accept {@code null}
	 * @return {@code true} if remove success
	 */
	public boolean removeReceiveMessagePassThroughListener(SecsMessagePassThroughListener lstnr);
	
}
