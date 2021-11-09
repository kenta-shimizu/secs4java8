package com.shimizukenta.secs.hsmsgs;

import java.io.Closeable;
import java.io.IOException;
import java.util.Optional;
import java.util.Set;

import com.shimizukenta.secs.SecsCommunicatableStateChangeBiListener;
import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.SecsMessageReceiveBiListener;
import com.shimizukenta.secs.hsms.HsmsException;
import com.shimizukenta.secs.hsms.HsmsSendMessageException;
import com.shimizukenta.secs.hsms.HsmsSession;
import com.shimizukenta.secs.hsms.HsmsWaitReplyMessageException;
import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.sml.SmlMessage;

public interface HsmsGsCommunicator extends Closeable {
	
	public void open() throws IOException;
	
	public Set<HsmsSession> getSessions();
	
	public HsmsSession getSession(int sessionId);
	
	public Optional<? extends SecsMessage> send(
			int sessionId,
			int strm,
			int func,
			boolean wbit)
					throws HsmsSendMessageException,
					HsmsWaitReplyMessageException,
					HsmsException,
					InterruptedException;
	
	public Optional<? extends SecsMessage> send(
			int sessionId,
			int strm,
			int func,
			boolean wbit,
			Secs2 secs2)
					throws HsmsSendMessageException,
					HsmsWaitReplyMessageException,
					HsmsException,
					InterruptedException;
	
	public Optional<? extends SecsMessage> send(
			int sessionId,
			SecsMessage primaryMsg,
			int strm,
			int func,
			boolean wbit)
					throws HsmsSendMessageException,
					HsmsWaitReplyMessageException,
					HsmsException,
					InterruptedException;
	
	public Optional<? extends SecsMessage> send(
			int sessionId,
			SecsMessage primaryMsg,
			int strm,
			int func,
			boolean wbit,
			Secs2 secs2)
					throws HsmsSendMessageException,
					HsmsWaitReplyMessageException,
					HsmsException,
					InterruptedException;
	
	public Optional<? extends SecsMessage> send(
			int sessionId,
			SmlMessage sml)
					throws HsmsSendMessageException,
					HsmsWaitReplyMessageException,
					HsmsException,
					InterruptedException;
	
	public Optional<? extends SecsMessage> send(
			int sessionId,
			SecsMessage primaryMsg,
			SmlMessage sml)
					throws HsmsSendMessageException,
					HsmsWaitReplyMessageException,
					HsmsException,
					InterruptedException;
	
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
	
}
