package com.shimizukenta.secs.hsmsgs;

import java.io.IOException;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import com.shimizukenta.secs.AbstractBaseCommunicator;
import com.shimizukenta.secs.ReadOnlyTimeProperty;
import com.shimizukenta.secs.SecsCommunicatableStateChangeBiListener;
import com.shimizukenta.secs.SecsException;
import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.SecsMessageReceiveBiListener;
import com.shimizukenta.secs.SecsSendMessageException;
import com.shimizukenta.secs.SecsWaitReplyMessageException;
import com.shimizukenta.secs.hsms.AbstractHsmsAsyncSocketChannel;
import com.shimizukenta.secs.hsms.AbstractHsmsLinktest;
import com.shimizukenta.secs.hsms.AbstractHsmsMessage;
import com.shimizukenta.secs.hsms.AbstractHsmsSession;
import com.shimizukenta.secs.hsms.HsmsAsyncSocketChannel;
import com.shimizukenta.secs.hsms.HsmsException;
import com.shimizukenta.secs.hsms.HsmsMessage;
import com.shimizukenta.secs.hsms.HsmsMessageBuilder;
import com.shimizukenta.secs.hsms.HsmsSendMessageException;
import com.shimizukenta.secs.hsms.HsmsSession;
import com.shimizukenta.secs.hsms.HsmsTransactionManager;
import com.shimizukenta.secs.hsms.HsmsWaitReplyMessageException;
import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.sml.SmlMessage;

public abstract class AbstractHsmsGsCommunicator extends AbstractBaseCommunicator implements HsmsGsCommunicator {
	
	private final HsmsGsCommunicatorConfig config;
	
	public AbstractHsmsGsCommunicator(HsmsGsCommunicatorConfig config) {
		this.config = config;
	}
	
	@Override
	public void open() throws IOException {
		super.open();
	}
	
	@Override
	public void close() throws IOException {
		super.close();
	}
	
	abstract protected Set<AbstractHsmsSession> getAbstractHsmsSessions();
	
	@Override
	public Set<HsmsSession> getSessions() {
		return Collections.unmodifiableSet(this.getAbstractHsmsSessions());
	}
	
	@Override
	public HsmsSession getSession(int sessionId) throws HsmsGsUnknownSessionIdException {
		for ( AbstractHsmsSession s : this.getAbstractHsmsSessions() ) {
			if ( s.sessionId() == sessionId ) {
				return s;
			}
		}
		throw new HsmsGsUnknownSessionIdException(sessionId);
	}
	
	@Override
	public boolean existSession(int sessionId) {
		for ( AbstractHsmsSession s : getAbstractHsmsSessions() ) {
			if ( s.sessionId() == sessionId ) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public Optional<SecsMessage> send(int sessionId, int strm, int func, boolean wbit)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException, InterruptedException {
		
		return getSession(sessionId).send(strm, func, wbit);
	}
	
	@Override
	public Optional<SecsMessage> send(int sessionId, int strm, int func, boolean wbit, Secs2 secs2)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException, InterruptedException {
		
		return getSession(sessionId).send(strm, func, wbit, secs2);
	}
	
	@Override
	public Optional<SecsMessage> send(int sessionId, SecsMessage primaryMsg, int strm, int func, boolean wbit)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException, InterruptedException {
		
		return getSession(sessionId).send(primaryMsg, strm, func, wbit);
	}
	
	@Override
	public Optional<SecsMessage> send(int sessionId, SecsMessage primaryMsg, int strm, int func, boolean wbit, Secs2 secs2)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException, InterruptedException {
		
		return getSession(sessionId).send(primaryMsg, strm, func, wbit, secs2);
	}
	
	@Override
	public Optional<SecsMessage> send(int sessionId, SmlMessage sml)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException, InterruptedException {
		
		return getSession(sessionId).send(sml);
	}
	
	@Override
	public Optional<SecsMessage> send(int sessionId, SecsMessage primaryMsg, SmlMessage sml)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException, InterruptedException {
		
		return getSession(sessionId).send(primaryMsg, sml);
	}
	
	@Override
	public boolean addSecsMessageReceiveListener(SecsMessageReceiveBiListener lstnr) {
		boolean r = true;
		for ( AbstractHsmsSession s : this.getAbstractHsmsSessions() ) {
			if ( ! s.addSecsMessageReceiveListener(lstnr) ) {
				r = false;
			}
		}
		return r;
	}
	
	@Override
	public boolean removeSecsMessageReceiveListener(SecsMessageReceiveBiListener lstnr) {
		boolean r = true;
		for ( AbstractHsmsSession s : this.getAbstractHsmsSessions() ) {
			if ( ! s.removeSecsMessageReceiveListener(lstnr) ) {
				r = false;
			}
		}
		return r;
	}
	
	@Override
	public boolean addSecsCommunicatableStateChangeListener(SecsCommunicatableStateChangeBiListener lstnr) {
		boolean r = true;
		for ( AbstractHsmsSession s : this.getAbstractHsmsSessions() ) {
			if ( ! s.addSecsCommunicatableStateChangeListener(lstnr) ) {
				r = false;
			}
		}
		return r;
	}
	
	@Override
	public boolean removeSecsCommunicatableStateChangeListener(SecsCommunicatableStateChangeBiListener lstnr) {
		boolean r = true;
		for ( AbstractHsmsSession s : this.getAbstractHsmsSessions() ) {
			if ( ! s.removeSecsCommunicatableStateChangeListener(lstnr) ) {
				r = false;
			}
		}
		return r;
	}
	
	private final HsmsMessageBuilder msgBuilder = new AbstractHsmsGsMessageBuilder() {};
	
	private class InnerHsmsGsAsyncSocketChannel extends AbstractHsmsAsyncSocketChannel {
		
		public InnerHsmsGsAsyncSocketChannel(AsynchronousSocketChannel channel) {
			super(channel);
		}
		
		private final class InnerHsmsGsLinktest extends AbstractHsmsLinktest {
			
			public InnerHsmsGsLinktest() {
				super();
			}
			
			@Override
			protected ReadOnlyTimeProperty timer() {
				return AbstractHsmsGsCommunicator.this.config.linktest();
			}
			
			@Override
			protected Optional<HsmsMessage> send()
					throws HsmsSendMessageException,
					HsmsWaitReplyMessageException,
					HsmsException,
					InterruptedException {
				
				for (AbstractHsmsSession s : AbstractHsmsGsCommunicator.this.getAbstractHsmsSessions()) {
					return InnerHsmsGsAsyncSocketChannel.this.sendLinktestRequest(s);
				}
				
				//TODO
				//throw
				
				return null;
			}
		}
		
		private final InnerHsmsGsLinktest linktest = new InnerHsmsGsLinktest();
		
		@Override
		public void linktesting()
				throws HsmsSendMessageException,
				HsmsWaitReplyMessageException,
				HsmsException,
				InterruptedException {
			
			this.linktest.testing();
		}
		
		@Override
		protected HsmsMessageBuilder messageBuilder() {
			return AbstractHsmsGsCommunicator.this.msgBuilder;
		}
		
		private final HsmsTransactionManager<AbstractHsmsMessage> transMgr = new HsmsTransactionManager<>();
		
		@Override
		protected HsmsTransactionManager<AbstractHsmsMessage> transactionManager() {
			return this.transMgr;
		}
		
		@Override
		protected ReadOnlyTimeProperty timeoutT3() {
			return AbstractHsmsGsCommunicator.this.config.timeout().t3();
		}
		
		@Override
		protected ReadOnlyTimeProperty timeoutT6() {
			return AbstractHsmsGsCommunicator.this.config.timeout().t6();
		}
		
		@Override
		protected ReadOnlyTimeProperty timeoutT8() {
			return AbstractHsmsGsCommunicator.this.config.timeout().t8();
		}
	}
	
	protected HsmsAsyncSocketChannel buildAsyncSocketChannel(AsynchronousSocketChannel channel) {
		return new InnerHsmsGsAsyncSocketChannel(channel);
	}
	
}
