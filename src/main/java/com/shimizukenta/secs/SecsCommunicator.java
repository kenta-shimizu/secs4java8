package com.shimizukenta.secs;

import java.io.Closeable;
import java.io.IOException;
import java.util.Optional;

import com.shimizukenta.secs.hsmsss.HsmsSsCommunicator;
import com.shimizukenta.secs.hsmsss.HsmsSsCommunicatorConfig;
import com.shimizukenta.secs.secs1ontcpip.Secs1OnTcpIpCommunicator;
import com.shimizukenta.secs.secs1ontcpip.Secs1OnTcpIpCommunicatorConfig;
import com.shimizukenta.secs.gem.Gem;
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
public interface SecsCommunicator extends Closeable {
	
	/**
	 * open communicating.
	 * 
	 * @throws IOException
	 */
	public void open() throws IOException;
	
	/**
	 * Returns is-open.
	 * 
	 * @return {@code true} if opened and not closed
	 */
	public boolean isOpen();
	
	/**
	 * Returns is-closed.
	 * 
	 * @return {@code true} if closed
	 */
	public boolean isClosed();
	
	/**
	 * Returns GEM-interface.
	 * 
	 * @return GEM-interface-instance
	 */
	public Gem gem();
	
	/**
	 * Returns communicator device-id.
	 * 
	 * @return communicator device-id
	 */
	public int deviceId();
	
	/**
	 * Returns is-equip.
	 * 
	 * @return {@code true} if Equipment
	 */
	public boolean isEquip();
	
	
	/**
	 * {@link #open()} and wait until communicating.
	 * 
	 * <p>
	 * Blocking-method.<br />
	 * </p>
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void openAndWaitUntilCommunicating() throws IOException, InterruptedException;
	
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
	 * @param primary Primary-Message
	 * @param strm SECS-II-Stream-Number
	 * @param func SECS-II-Function-Number
	 * @param wbit SECS-II-WBit, set {@code false}
	 * @return {@code Optional.empty()}
	 * @throws SecsSendMessageException if send failed
	 * @throws SecsWaitReplyMessageException if receive message failed, e.g. Timeout-T3
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> send(SecsMessage primary, int strm, int func, boolean wbit)
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
	 * @param primary Primary-Message
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
	public Optional<SecsMessage> send(SecsMessage primary, int strm, int func, boolean wbit, Secs2 secs2)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException;
	
	/**
	 * Send Primary-Message by SML and receive Reply-Message<br />
	 * 
	 * <p>
	 * Blocking-method.<br />
	 * Wait until sended Primay-Message and received Reply-Message if exist.
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
	 * @param primary Primary-Message.
	 * @param sml
	 * @return {@code Optional.empty()}
	 * @throws SecsSendMessageException if send failed
	 * @throws SecsWaitReplyMessageException if receive message failed, e.g. Timeout-T3
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> send(SecsMessage primary, SmlMessage sml)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException;
	
	
	/* Secs-Message Receive Listener */
	
	/**
	 * Add Listener to receive Primary-Message.
	 * 
	 * <p>
	 * This Listener not receive Reply-Message.
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
