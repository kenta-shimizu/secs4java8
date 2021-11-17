package com.shimizukenta.secs.hsmsss;

import java.io.IOException;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.Optional;

import com.shimizukenta.secs.AbstractSecsCommunicator;
import com.shimizukenta.secs.ReadOnlyTimeProperty;
import com.shimizukenta.secs.hsms.AbstractHsmsAsyncSocketChannel;
import com.shimizukenta.secs.hsms.AbstractHsmsLinktest;
import com.shimizukenta.secs.hsms.AbstractHsmsMessage;
import com.shimizukenta.secs.hsms.AbstractHsmsSession;
import com.shimizukenta.secs.hsms.HsmsAsyncSocketChannel;
import com.shimizukenta.secs.hsms.HsmsException;
import com.shimizukenta.secs.hsms.HsmsMessage;
import com.shimizukenta.secs.hsms.HsmsMessageBuilder;
import com.shimizukenta.secs.hsms.HsmsSendMessageException;
import com.shimizukenta.secs.hsms.HsmsTransactionManager;
import com.shimizukenta.secs.hsms.HsmsWaitReplyMessageException;

/**
 * This abstract class is implementation of HSMS-SS (SEMI-E37.1).
 * 
 * @author kenta-shimizu
 *
 */
public abstract class AbstractHsmsSsCommunicator extends AbstractSecsCommunicator
		implements HsmsSsCommunicator {
	
	private final HsmsSsCommunicatorConfig config;
	private final AbstractHsmsSession session;
	
	public AbstractHsmsSsCommunicator(HsmsSsCommunicatorConfig config) {
		super(config);
		
		this.config = config;
		
		this.session = new AbstractHsmsSession(config) {

			@Override
			public int sessionId() {
				
				return 1;
			}

			@Override
			protected HsmsAsyncSocketChannel asyncSocketChannel() {
				// TODO Auto-generated method stub
				return null;
			}};
	}
	
	@Override
	public void open() throws IOException {
		super.open();
		
//		this.hsmsCommStateProperty.addChangeListener(state -> {
//			notifyLog(HsmsSsCommunicateStateChangeLog.get(state));
//		});
//		
//		this.hsmsCommStateProperty.addChangeListener(state -> {
//			notifyCommunicatableStateChange(state.communicatable());
//		});
	}
	
	@Override
	public void close() throws IOException {
		super.close();
	}
	
	protected AbstractHsmsSession getSession() {
		
		//TODO
		
		return null;
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
				
				for (AbstractHsmsSession s : AbstractHsmsSsCommunicator.this.getAbstractHsmsSessions()) {
					return InnerHsmsSsAsyncSocketChannel.this.sendLinktestRequest(s);
				}
				
				//TODO
				//throw
				
				return null;
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
	
	protected HsmsAsyncSocketChannel buildAsyncSocketChannel(AsynchronousSocketChannel channel) {
		return new InnerHsmsSsAsyncSocketChannel(channel);
	}

	
	
//	private final Object syncSelectedConnection = new Object();
//	
//	protected boolean addSelectedConnection(AbstractInnerConnection c) {
//		synchronized ( syncSelectedConnection ) {
//			if ( this.selectedConnection == null ) {
//				this.selectedConnection = c;
//				return true;
//			} else {
//				return false;
//			}
//		}
//	}
//	
//	protected boolean removeSelectedConnection(AbstractInnerConnection c) {
//		synchronized ( this.syncSelectedConnection ) {
//			if ( this.selectedConnection == null ) {
//				return false;
//			} else {
//				this.selectedConnection = null;
//				return true;
//			}
//		}
//	}
//	
//	/* HSMS Communicate State */
//	protected HsmsCommunicateState hsmsSsCommunicateState() {
//		return hsmsCommStateProperty.get();
//	}
//	
//	protected void notifyHsmsSsCommunicateStateChange(HsmsCommunicateState state) {
//		hsmsCommStateProperty.set(state);
//	}
//	
//	
//	
//	@Override
//	public boolean linktest() throws InterruptedException {
//		try {
//			return send(createLinktestRequest()).isPresent();
//		}
//		catch ( SecsException e ) {
//			return false;
//		}
//	}
	
}
