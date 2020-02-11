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
	 * Blocking-method<br />
	 * send primary-message,<br />
	 * wait until reply-message if exist
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
	default public Optional<SecsMessage> send(int strm, int func, boolean wbit)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException
			, InterruptedException {
		
		return send(strm, func, wbit, Secs2.empty());
	}
	
	/**
	 * Blocking-method<br />
	 * send primary-message,<br />
	 * wait until reply-message if exist
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
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException
			, InterruptedException;
	
	/**
	 * send reply-message
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
	default public Optional<SecsMessage> send(SecsMessage primary, int strm, int func, boolean wbit)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException
			, InterruptedException {
		
		return send(primary, strm, func, wbit, Secs2.empty());
	}
	
	/**
	 * send reply-message
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
	abstract public Optional<SecsMessage> send(SecsMessage primary, int strm, int func, boolean wbit, Secs2 secs2)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException
			, InterruptedException;
	
	/**
	 * Blocking-method<br />
	 * send primary-message,<br />
	 * wait until reply-message if exist
	 * 
	 * @param sml
	 * @return reply-message if exist
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	default public Optional<SecsMessage> send(SmlMessage sml)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException
			, InterruptedException {
		
		return send(sml.getStream(), sml.getFunction(), sml.wbit(), sml.secs2());
	}
	
	/**
	 * send reply-message
	 * 
	 * @param primary-message
	 * @param sml
	 * @return Optional.empty()
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	default public Optional<SecsMessage> send(SecsMessage primary, SmlMessage sml)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException
			, InterruptedException {
		
		return send(primary, sml.getStream(), sml.getFunction(), sml.wbit(), sml.secs2());
	}
	
	
	/* Secs-Message Receive Listener */
	public boolean addSecsMessageReceiveListener(SecsMessageReceiveListener lstnr);
	public boolean removeSecsMessageReceiveListener(SecsMessageReceiveListener lstnr);
	
	/* Secs-Log Receive Listener */
	public boolean addSecsLogListener(SecsLogListener lstnr);
	public boolean removeSecsLogListener(SecsLogListener lstnr);
	
	/* Secs-Communicatable-State-Changed-Listener */
	/**
	 * Blocking-Listener<br />
	 * Pass through quickly.
	 * 
	 * @param lstnr
	 * @return true if success.
	 */
	public boolean addSecsCommunicatableStateChangeListener(SecsCommunicatableStateChangeListener lstnr);
	public boolean removeSecsCommunicatableStateChangeListener(SecsCommunicatableStateChangeListener lstnr);	
	
	/* Try-Send Secs-Message Pass-through Listener */
	public boolean addTrySendMessagePassThroughListener(SecsMessagePassThroughListener lstnr);
	public boolean removeTrySendMessagePassThroughListener(SecsMessagePassThroughListener lstnr);	
	
	/* Sended Secs-Message Pass-through Listener */
	public boolean addSendedMessagePassThroughListener(SecsMessagePassThroughListener lstnr);	
	public boolean removeSendedMessagePassThroughListener(SecsMessagePassThroughListener lstnr);	
	
	/* Receive Secs-Message Pass-through Listener */
	public boolean addReceiveMessagePassThroughListener(SecsMessagePassThroughListener lstnr);
	public boolean removeReceiveMessagePassThroughListener(SecsMessagePassThroughListener lstnr);

}
