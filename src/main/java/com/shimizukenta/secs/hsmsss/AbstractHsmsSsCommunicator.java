package com.shimizukenta.secs.hsmsss;

import java.io.IOException;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.Optional;

import com.shimizukenta.secs.SecsException;
import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.SecsSendMessageException;
import com.shimizukenta.secs.SecsWaitReplyMessageException;
import com.shimizukenta.secs.hsms.AbstractHsmsAsyncSocketChannel;
import com.shimizukenta.secs.hsms.AbstractHsmsCommunicator;
import com.shimizukenta.secs.hsms.AbstractHsmsSession;
import com.shimizukenta.secs.hsms.HsmsException;
import com.shimizukenta.secs.hsms.HsmsMessage;
import com.shimizukenta.secs.hsms.HsmsMessageBuilder;
import com.shimizukenta.secs.hsms.HsmsSendMessageException;
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
	private final AbstractHsmsSsSession session;
	
	public AbstractHsmsSsCommunicator(HsmsSsCommunicatorConfig config) {
		super(config);
		
		this.config = config;
		
		this.session = new AbstractHsmsSsSession(
				config,
				config.sessionId().intValue()
				) {};
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
	
}
