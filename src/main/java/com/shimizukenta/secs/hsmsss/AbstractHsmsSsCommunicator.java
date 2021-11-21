package com.shimizukenta.secs.hsmsss;

import java.io.IOException;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.Optional;

import com.shimizukenta.secs.ReadOnlyTimeProperty;
import com.shimizukenta.secs.SecsException;
import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.SecsSendMessageException;
import com.shimizukenta.secs.SecsWaitReplyMessageException;
import com.shimizukenta.secs.hsms.AbstractHsmsAsyncSocketChannel;
import com.shimizukenta.secs.hsms.AbstractHsmsCommunicator;
import com.shimizukenta.secs.hsms.AbstractHsmsLinktest;
import com.shimizukenta.secs.hsms.AbstractHsmsMessage;
import com.shimizukenta.secs.hsms.AbstractHsmsSession;
import com.shimizukenta.secs.hsms.HsmsException;
import com.shimizukenta.secs.hsms.HsmsMessage;
import com.shimizukenta.secs.hsms.HsmsMessageBuilder;
import com.shimizukenta.secs.hsms.HsmsSendMessageException;
import com.shimizukenta.secs.hsms.HsmsTransactionManager;
import com.shimizukenta.secs.hsms.HsmsWaitReplyMessageException;
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
	private final AbstractHsmsSession session;
	
	public AbstractHsmsSsCommunicator(HsmsSsCommunicatorConfig config) {
		super(config);
		
		this.config = config;
		
		this.session = new InnerHsmsSsSession(
				config,
				config.sessionId().intValue());
	}
	
	private final class InnerHsmsSsSession extends AbstractHsmsSession {
		
		private final int sessionId;
		
		public InnerHsmsSsSession(HsmsSsCommunicatorConfig config, int sessionId) {
			super(config);
			this.sessionId = sessionId;
		}

		@Override
		public int sessionId() {
			return this.sessionId;
		}

		@Override
		public boolean linktest() throws InterruptedException {
			
			try {
				Optional<HsmsMessage> op = this.asyncSocketChannel().sendLinktestRequest(this);
				return op.isPresent();
			}
			catch ( HsmsSendMessageException | HsmsWaitReplyMessageException | HsmsException giveup ) {
			}
			
			return false;
		}
	}
	
	@Override
	public void open() throws IOException {
		super.open();
	}
	
	@Override
	public void close() throws IOException {
		super.close();
	}
	
	@Override
	public boolean linktest() throws InterruptedException {
		return this.getSession().linktest();
	}
	
	@Override
	public Optional<SecsMessage> send(
			int strm,
			int func,
			boolean wbit,
			Secs2 secs2)
					throws SecsSendMessageException,
					SecsWaitReplyMessageException,
					SecsException,
					InterruptedException {
		
		return this.getSession().send(strm, func, wbit, secs2);
	}

	@Override
	public Optional<SecsMessage> send(
			SecsMessage primaryMsg,
			int strm,
			int func,
			boolean wbit,
			Secs2 secs2)
					throws SecsSendMessageException,
					SecsWaitReplyMessageException,
					SecsException,
					InterruptedException {
		
		return this.getSession().send(primaryMsg, strm, func, wbit, secs2);
	}
	
	protected AbstractHsmsSession getSession() {
		return this.session;
	}
	
	private final HsmsMessageBuilder msgBuilder = new AbstractHsmsSsMessageBuilder() {};
	
	private class InnerHsmsSsAsyncSocketChannel extends AbstractHsmsAsyncSocketChannel {
		
		public InnerHsmsSsAsyncSocketChannel(AsynchronousSocketChannel channel) {
			super(channel);
		}
		
		private final class InnerHsmsSsLinktest extends AbstractHsmsLinktest {
			
			public InnerHsmsSsLinktest() {
				super();
			}
			
			@Override
			protected ReadOnlyTimeProperty timer() {
				return AbstractHsmsSsCommunicator.this.config.linktest();
			}
			
			@Override
			protected Optional<HsmsMessage> send()
					throws HsmsSendMessageException,
					HsmsWaitReplyMessageException,
					HsmsException,
					InterruptedException {
				
				return InnerHsmsSsAsyncSocketChannel.this.sendLinktestRequest(
						AbstractHsmsSsCommunicator.this.getSession());
			}
		}
		
		private final InnerHsmsSsLinktest linktest = new InnerHsmsSsLinktest();
		
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
			return AbstractHsmsSsCommunicator.this.msgBuilder;
		}
		
		private final HsmsTransactionManager<AbstractHsmsMessage> transMgr = new HsmsTransactionManager<>();
		
		@Override
		protected HsmsTransactionManager<AbstractHsmsMessage> transactionManager() {
			return this.transMgr;
		}
		
		@Override
		protected ReadOnlyTimeProperty timeoutT3() {
			return AbstractHsmsSsCommunicator.this.config.timeout().t3();
		}
		
		@Override
		protected ReadOnlyTimeProperty timeoutT6() {
			return AbstractHsmsSsCommunicator.this.config.timeout().t6();
		}
		
		@Override
		protected ReadOnlyTimeProperty timeoutT8() {
			return AbstractHsmsSsCommunicator.this.config.timeout().t8();
		}
	}
	
	protected AbstractHsmsAsyncSocketChannel buildAsyncSocketChannel(AsynchronousSocketChannel channel) {
		
		final AbstractHsmsAsyncSocketChannel x = new InnerHsmsSsAsyncSocketChannel(channel);
		
		x.addSecsLogListener(log -> {
			try {
				this.notifyLog(log);
			}
			catch ( InterruptedException ignore ) {
			}
		});
		
		x.addTrySendHsmsMessagePassThroughListener(msg -> {
			try {
				this.notifyTrySendHsmsMessagePassThrough(msg);
				this.notifyTrySendMessagePassThrough(msg);
			}
			catch ( InterruptedException ignore ) {
			}
		});
		
		x.addSendedHsmsMessagePassThroughListener(msg -> {
			try {
				this.notifySendedHsmsMessagePassThrough(msg);
				this.notifySendedMessagePassThrough(msg);
			}
			catch ( InterruptedException ignore ) {
			}
		});
		
		x.addReceiveHsmsMessagePassThroughListener(msg -> {
			try {
				this.notifyReceiveHsmsMessagePassThrough(msg);
				this.notifyReceiveMessagePassThrough(msg);
			}
			catch ( InterruptedException ignore ) {
			}
		});
		
		return x;
	}
	
}
