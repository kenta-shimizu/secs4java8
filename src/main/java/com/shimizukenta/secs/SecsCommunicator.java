package com.shimizukenta.secs;

import java.io.Closeable;
import java.io.IOException;
import java.util.Optional;

import com.shimizukenta.secs.gem.Gem;
import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.sml.SmlMessage;

public interface SecsCommunicator extends Closeable {
	
	public void open() throws IOException;
	
	public boolean isOpened();
	public boolean isClosed();
	
	public Gem gem();
	public int deviceId();
	public boolean isEquip();
	
	
	/**
	 * Blocking-method.<br />
	 * send Primary-Message,<br />
	 * wait until Reply-Message if exist.
	 * 
	 * @param strm
	 * @param func
	 * @param wbit
	 * @return reply-message if exist
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> send(int strm, int func, boolean wbit)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException;
	
	/**
	 * Blocking-method.<br />
	 * send Primary-Message,<br />
	 * wait until Reply-Message if exist.
	 * 
	 * @param strm
	 * @param func
	 * @param wbit
	 * @param secs2
	 * @return reply-message if exist
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> send(int strm, int func, boolean wbit, Secs2 secs2)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException;
	
	/**
	 * send Reply-Message.
	 * 
	 * @param primary (primary-message)
	 * @param strm
	 * @param func
	 * @param wbit
	 * @return Optional.empty()
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> send(SecsMessage primary, int strm, int func, boolean wbit)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException;
	
	/**
	 * send Reply-Message.
	 * 
	 * @param primary (primary-message)
	 * @param strm
	 * @param func
	 * @param wbit
	 * @param secs2
	 * @return Optional.empty()
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> send(SecsMessage primary, int strm, int func, boolean wbit, Secs2 secs2)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException;
	
	/**
	 * Blocking-method.<br />
	 * send Primary-Message,<br />
	 * wait until Reply-Message if exist.
	 * 
	 * @param sml
	 * @return reply-message if exist.
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> send(SmlMessage sml)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException;
	
	/**
	 * send Reply-Message.
	 * 
	 * @param primary-message
	 * @param sml
	 * @return Optional.empty()
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
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
	 * Add Listener to recieve Primary-Message.<br />
	 * This Listener not receive Reply-Message.
	 * 
	 * @param lstnr
	 * @return true if add success.
	 */
	public boolean addSecsMessageReceiveListener(SecsMessageReceiveListener lstnr);
	
	/**
	 * 
	 * @param lstnr
	 * @return true if remove success.
	 */
	public boolean removeSecsMessageReceiveListener(SecsMessageReceiveListener lstnr);
	
	
	/* Secs-Log Receive Listener */
	
	/**
	 * Add Listener to get Communicator-Log.
	 * 
	 * @param lstnr
	 * @return true if add success.
	 */
	public boolean addSecsLogListener(SecsLogListener lstnr);
	
	/**
	 * 
	 * @param lstnr
	 * @return true if remove success.
	 */
	public boolean removeSecsLogListener(SecsLogListener lstnr);
	
	
	/* Secs-Communicatable-State-Changed-Listener */
	
	/**
	 * Blocking-Listener.<br />
	 * Pass through quickly.
	 * 
	 * @param lstnr
	 * @return true if add success.
	 */
	public boolean addSecsCommunicatableStateChangeListener(SecsCommunicatableStateChangeListener lstnr);
	
	/**
	 * 
	 * @param lstnr
	 * @return true if remove success.
	 */
	public boolean removeSecsCommunicatableStateChangeListener(SecsCommunicatableStateChangeListener lstnr);	
	
	
	/* Try-Send Secs-Message Pass-through Listener */
	
	/**
	 * Add Listener to get SecsMesssage before sending.
	 * 
	 * @param lstnr
	 * @return true if add success.
	 */
	public boolean addTrySendMessagePassThroughListener(SecsMessagePassThroughListener lstnr);
	
	/**
	 * 
	 * @param lstnr
	 * @return true if remove success.
	 */
	public boolean removeTrySendMessagePassThroughListener(SecsMessagePassThroughListener lstnr);	
	
	
	/* Sended Secs-Message Pass-through Listener */
	
	/**
	 * Add Listener to get sended SecsMesssage.
	 * 
	 * @param lstnr
	 * @return true if add success.
	 */
	public boolean addSendedMessagePassThroughListener(SecsMessagePassThroughListener lstnr);
	
	/**
	 * 
	 * @param lstnr
	 * @return true if remove success.
	 */
	public boolean removeSendedMessagePassThroughListener(SecsMessagePassThroughListener lstnr);	
	
	
	/* Receive Secs-Message Pass-through Listener */
	
	/**
	 * Add Listener to receive both Primary and Reply Message.
	 * 
	 * @param lstnr
	 * @return true if add success.
	 */
	public boolean addReceiveMessagePassThroughListener(SecsMessagePassThroughListener lstnr);
	
	/**
	 * 
	 * @param lstnr
	 * @return true if remove success.
	 */
	public boolean removeReceiveMessagePassThroughListener(SecsMessagePassThroughListener lstnr);

}
