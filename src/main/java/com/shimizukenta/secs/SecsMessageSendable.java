package com.shimizukenta.secs;

import java.util.Optional;

import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.sml.SmlMessage;

/**
 * This interface is implementation of send SECS-Message.
 * 
 * <ul>
 * <li>To send Primary-Message and receive Reply-Message, 
 * {@link #send(int, int, boolean, Secs2)}</li>
 * <li>To send Primary-(Header-only)-Message and receive Reply-Message,
 * {@link #send(int, int, boolean)}</li>
 * <li>To send Primary-Message by SML and receive Reply-Message,
 * {@link #send(SmlMessage)}</li>
 * <li>To send Reply-Message,
 * {@link #send(SecsMessage, int, int, boolean, Secs2)}</li>
 * <li>To send Reply-(Header-only)-Message,
 * {@link #send(SecsMessage, int, int, boolean)}</li>
 * <li>To send Reply-Message by SML,
 * {@link #send(SecsMessage, SmlMessage)}</li>
 * </ul>
 * 
 * @author kenta-shimizu
 *
 */
public interface SecsMessageSendable {

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
	 * @param wbit SECS-II-WBit, set true if w-bit is 1
	 * @return Reply-Message if exist
	 * @throws SecsSendMessageException if send failed
	 * @throws SecsWaitReplyMessageException if receive message failed, e.g. Timeout-T3
	 * @throws SecsException if SECS failed
	 * @throws InterruptedException if interrupted
	 */
	default public Optional<SecsMessage> send(int strm, int func, boolean wbit)
			throws SecsSendMessageException,
			SecsWaitReplyMessageException,
			SecsException,
			InterruptedException {
		
		return this.send(strm, func, wbit, Secs2.empty());
	}
	
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
	 * @param wbit SECS-II-WBit, set true if w-bit is 1
	 * @param secs2 SECS-II-data, Not accept null
	 * @return Reply-Message if exist
	 * @throws SecsSendMessageException if send failed
	 * @throws SecsWaitReplyMessageException if receive message failed, e.g. Timeout-T3
	 * @throws SecsException if SECS failed
	 * @throws InterruptedException the interrupted
	 */
	public Optional<SecsMessage> send(int strm, int func, boolean wbit, Secs2 secs2)
			throws SecsSendMessageException,
			SecsWaitReplyMessageException,
			SecsException,
			InterruptedException;
	
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
	 * @param wbit SECS-II-WBit, set true if w-bit is 1
	 * @return {@code Optional.empty()}
	 * @throws SecsSendMessageException if send failed
	 * @throws SecsWaitReplyMessageException if receive message failed, e.g. Timeout-T3
	 * @throws SecsException if SECS failed
	 * @throws InterruptedException if interrupted
	 */
	default public Optional<SecsMessage> send(SecsMessage primaryMsg, int strm, int func, boolean wbit)
			throws SecsSendMessageException,
			SecsWaitReplyMessageException,
			SecsException,
			InterruptedException {
		
		return this.send(primaryMsg, strm, func, wbit, Secs2.empty());
	}
	
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
	 * @param wbit SECS-II-WBit, set true if w-bit is 1
	 * @param secs2 SECS-II-data, Not accept null
	 * @return {@code Optional.empty()}
	 * @throws SecsSendMessageException if send failed
	 * @throws SecsWaitReplyMessageException if receive message failed, e.g. Timeout-T3
	 * @throws SecsException if SECS failed
	 * @throws InterruptedException if interrupted
	 */
	public Optional<SecsMessage> send(SecsMessage primaryMsg, int strm, int func, boolean wbit, Secs2 secs2)
			throws SecsSendMessageException,
			SecsWaitReplyMessageException,
			SecsException,
			InterruptedException;
	
	/**
	 * Send Primary-Message by SML and receive Reply-Message<br />
	 * 
	 * <p>
	 * Blocking-method.<br />
	 * Wait until sended Primay-Message and received Reply-Message if exist.<br />
	 * </p>
	 * 
	 * @param sml the SML-Message
	 * @return Reply-Message if exist
	 * @throws SecsSendMessageException if send failed
	 * @throws SecsWaitReplyMessageException if receive message failed, e.g. Timeout-T3
	 * @throws SecsException if SECS failed
	 * @throws InterruptedException if interrupted
	 */
	default public Optional<SecsMessage> send(SmlMessage sml)
			throws SecsSendMessageException,
			SecsWaitReplyMessageException,
			SecsException,
			InterruptedException {
		
		return this.send(sml.getStream(), sml.getFunction(), sml.wbit(), sml.secs2());
	}
	
	/**
	 * Send Reply-Message by SML.
	 * 
	 * <p>
	 * Blocking-method.<br />
	 * Wait until sended.<br />
	 * </p>
	 * 
	 * @param primaryMsg Primary-Message.
	 * @param sml the SML-Message
	 * @return {@code Optional.empty()}
	 * @throws SecsSendMessageException if send failed
	 * @throws SecsWaitReplyMessageException if receive message failed, e.g. Timeout-T3
	 * @throws SecsException if SECS failed
	 * @throws InterruptedException if interrupted
	 */
	default public Optional<SecsMessage> send(SecsMessage primaryMsg, SmlMessage sml)
			throws SecsSendMessageException,
			SecsWaitReplyMessageException,
			SecsException,
			InterruptedException {
		
		return this.send(primaryMsg, sml.getStream(), sml.getFunction(), sml.wbit(), sml.secs2());
	}
	
}
