package com.shimizukenta.secs.hsmsss.impl;

import java.io.IOException;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.Optional;

import com.shimizukenta.secs.SecsException;
import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.SecsSendMessageException;
import com.shimizukenta.secs.SecsWaitReplyMessageException;
import com.shimizukenta.secs.hsms.HsmsCommunicateStateChangeListener;
import com.shimizukenta.secs.hsms.HsmsException;
import com.shimizukenta.secs.hsms.HsmsMessage;
import com.shimizukenta.secs.hsms.HsmsMessageReceiveListener;
import com.shimizukenta.secs.hsms.HsmsSendMessageException;
import com.shimizukenta.secs.hsms.HsmsWaitReplyMessageException;
import com.shimizukenta.secs.hsms.impl.AbstractHsmsAsyncSocketChannel;
import com.shimizukenta.secs.hsms.impl.AbstractHsmsCommunicator;
import com.shimizukenta.secs.hsms.impl.AbstractHsmsSession;
import com.shimizukenta.secs.hsms.impl.HsmsMessageBuilder;
import com.shimizukenta.secs.hsmsss.HsmsSsCommunicateStateChangeBiListener;
import com.shimizukenta.secs.hsmsss.HsmsSsCommunicator;
import com.shimizukenta.secs.hsmsss.HsmsSsCommunicatorConfig;
import com.shimizukenta.secs.hsmsss.HsmsSsMessageReceiveBiListener;
import com.shimizukenta.secs.secs2.Secs2;

/**
 * This abstract class is implementation of HSMS-SS (SEMI-E37.1).
 * 
 * @author kenta-shimizu
 *
 */
public abstract class AbstractHsmsSsCommunicator extends AbstractHsmsCommunicator
		implements HsmsSsCommunicator {
	
	private final HsmsSsCommunicatorConfig config;
	private final AbstractHsmsSsSession session;
	
	private final HsmsSsMessageReceiveQueueBiObserver hsmsSsMsgRecvQueueBiObserver;
	private final HsmsSsCommunicateStatePropertyBiObserver commStatePropBiObserver;
	
	public AbstractHsmsSsCommunicator(HsmsSsCommunicatorConfig config) {
		super(config);
		
		this.config = config;
		
		this.session = new AbstractHsmsSsSession(config) {};
		
		this.hsmsSsMsgRecvQueueBiObserver = new HsmsSsMessageReceiveQueueBiObserver(this);
		this.commStatePropBiObserver = new HsmsSsCommunicateStatePropertyBiObserver(this, this.getHsmsCommunicateState());
	}
	
	@Override
	public int deviceId() {
		return this.getSession().deviceId();
	}
	
	@Override
	public int sessionId() {
		return this.getSession().sessionId();
	}
	
	@Override
	public void open() throws IOException {
		super.open();
		this.getSession().open();
	}
	
	public HsmsSsCommunicatorConfig config() {
		return this.config;
	}
	
	private final HsmsMessageBuilder msgBuilder = new AbstractHsmsSsMessageBuilder() {};
	
	public HsmsMessageBuilder msgBuilder() {
		return this.msgBuilder;
	}
	
	private final Object syncClosed = new Object();
	
	@Override
	public void close() throws IOException {
		
		synchronized ( this.syncClosed ) {
			
			if ( this.isClosed() ) {
				return;
			}
			
			try {
				this.getSession().separate();
			}
			catch ( InterruptedException giveup ) {
			}
			
			IOException ioExcept = null;
			try {
				super.close();
			}
			catch ( IOException e ) {
				ioExcept = e;
			}
			
			try {
				this.getSession().close();
			}
			catch ( IOException e ) {
				ioExcept = e;
			}
			
			if ( ioExcept != null ) {
				throw ioExcept;
			}
		}
	}
	
	@Override
	public boolean linktest() throws InterruptedException {
		return this.getSession().linktest();
	}
	
	@Override
	public Optional<SecsMessage> templateSend(int strm, int func, boolean wbit, Secs2 secs2)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException, InterruptedException {
		
		return this.getSession().templateSend(strm, func, wbit, secs2);
	}
	
	@Override
	public Optional<SecsMessage> templateSend(SecsMessage primaryMsg, int strm, int func, boolean wbit, Secs2 secs2)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException, InterruptedException {
		
		return this.getSession().templateSend(primaryMsg, strm, func, wbit, secs2);
	}
	
	@Override
	public Optional<HsmsMessage> send(HsmsMessage msg)
			throws HsmsSendMessageException,
			HsmsWaitReplyMessageException,
			HsmsException,
			InterruptedException {
		
		return this.getSession().send(msg);
	}
	
	protected AbstractHsmsSession getSession() {
		return this.session;
	}
	
	protected AbstractHsmsAsyncSocketChannel buildAsyncSocketChannel(AsynchronousSocketChannel channel) {
		return new AbstractHsmsSsAsyncSocketChannel(channel, this) {};
	}
	
	@Override
	public boolean addHsmsMessageReceiveListener(HsmsMessageReceiveListener listener) {
		return this.hsmsSsMsgRecvQueueBiObserver.addListener(listener);
	}
	
	@Override
	public boolean removeHsmsMessageReceiveListener(HsmsMessageReceiveListener listener) {
		return this.hsmsSsMsgRecvQueueBiObserver.removeListener(listener);
	}
	
	@Override
	public boolean addHsmsMessageReceiveBiListener(HsmsSsMessageReceiveBiListener biListener) {
		return this.hsmsSsMsgRecvQueueBiObserver.addBiListener(biListener);
	}
	
	@Override
	public boolean removeHsmsMessageReceiveBiListener(HsmsSsMessageReceiveBiListener biListener) {
		return this.hsmsSsMsgRecvQueueBiObserver.removeBiListener(biListener);
	}
	
	@Override
	protected void prototypeNotifyReceiveHsmsMessage(HsmsMessage message) throws InterruptedException {
		this.hsmsSsMsgRecvQueueBiObserver.put(message);
	}
	
	
	@Override
	public boolean addHsmsCommunicateStateChangeListener(HsmsCommunicateStateChangeListener listener) {
		return this.commStatePropBiObserver.addListener(listener);
	}
	
	@Override
	public boolean removeHsmsCommunicateStateChangeListener(HsmsCommunicateStateChangeListener listener) {
		return this.commStatePropBiObserver.removeListener(listener);
	}
	
	@Override
	public boolean addHsmsCommunicateStateChangeBiListener(HsmsSsCommunicateStateChangeBiListener biListener) {
		return this.commStatePropBiObserver.addBiListener(biListener);
	}
	
	@Override
	public boolean removeHsmsCommunicateStateChangeBiListener(HsmsSsCommunicateStateChangeBiListener biListener) {
		return this.commStatePropBiObserver.removeBiListener(biListener);
	}
	
}
