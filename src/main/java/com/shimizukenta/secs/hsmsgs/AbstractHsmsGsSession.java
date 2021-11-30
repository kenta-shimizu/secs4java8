package com.shimizukenta.secs.hsmsgs;

import java.util.Optional;
import java.util.function.Consumer;

import com.shimizukenta.secs.AbstractSecsLog;
import com.shimizukenta.secs.hsms.AbstractHsmsAsyncSocketChannel;
import com.shimizukenta.secs.hsms.AbstractHsmsSession;
import com.shimizukenta.secs.hsms.HsmsCommunicateState;
import com.shimizukenta.secs.hsms.HsmsMessagePassThroughListener;

public abstract class AbstractHsmsGsSession extends AbstractHsmsSession {
	
	private final int sessionId;
	
	public AbstractHsmsGsSession(HsmsGsCommunicatorConfig config, int sessionId) {
		super(config);
		this.sessionId = sessionId;
	}
	
	@Override
	public int deviceId() {
		return -1;
	}
	
	@Override
	public int sessionId() {
		return this.sessionId;
	}
	
	private final Consumer<? super AbstractSecsLog> logConsumer = log -> {
		log.optionalAbstractSecsMessage()
		.filter(m -> m.sessionId() == this.sessionId())
		.ifPresent(m -> {
			try {
				this.notifyLog(log);
			}
			catch ( InterruptedException ignore ) {
			}
		});
	};
	
	private final HsmsMessagePassThroughListener trySendPassThroughLstnr = msg -> {
		if ( msg.sessionId() == this.sessionId() ) {
			try {
				this.notifyTrySendHsmsMessagePassThrough(msg);
				this.notifyTrySendMessagePassThrough(msg);
			}
			catch ( InterruptedException ignore ) {
			}
		}
	};
	
	private final HsmsMessagePassThroughListener sendedPassThroughLstnr = msg -> {
		if ( msg.sessionId() == this.sessionId() ) {
			try {
				this.notifySendedHsmsMessagePassThrough(msg);
				this.notifySendedMessagePassThrough(msg);
			}
			catch ( InterruptedException ignore ) {
			}
		}
	};
	
	private final HsmsMessagePassThroughListener recvPassThroughLstnr = msg -> {
		if ( msg.sessionId() == this.sessionId() ) {
			try {
				this.notifyReceiveHsmsMessagePassThrough(msg);
				this.notifyReceiveMessagePassThrough(msg);
			}
			catch ( InterruptedException ignore ) {
			}
		}
	};
	
	private final Object syncAsyncChannel = new Object();
	
	@Override
	public boolean setAsyncSocketChannel(AbstractHsmsAsyncSocketChannel asyncChannel) {
		
		synchronized ( this.syncAsyncChannel ) {
			
			boolean f = super.setAsyncSocketChannel(asyncChannel);
			
			if ( f ) {
				asyncChannel.addSecsLogListener(this.logConsumer);
				asyncChannel.addTrySendHsmsMessagePassThroughListener(this.trySendPassThroughLstnr);
				asyncChannel.addSendedHsmsMessagePassThroughListener(this.sendedPassThroughLstnr);
				asyncChannel.addReceiveHsmsMessagePassThroughListener(this.recvPassThroughLstnr);
				
				this.notifyHsmsCommunicateState(HsmsCommunicateState.SELECTED);
			}
			
			return f;
		}
	}
	
	@Override
	public boolean unsetAsyncSocketChannel() {
		
		synchronized ( this.syncAsyncChannel ) {
			
			final Optional<AbstractHsmsAsyncSocketChannel> op = this.optionalAsyncSocketChannel();
			
			boolean f = super.unsetAsyncSocketChannel();
			
			op.ifPresent(asyncChannel -> {
				asyncChannel.addSecsLogListener(this.logConsumer);
				asyncChannel.removeTrySendHsmsMessagePassThroughListener(this.trySendPassThroughLstnr);
				asyncChannel.removeSendedHsmsMessagePassThroughListener(this.sendedPassThroughLstnr);
				asyncChannel.removeReceiveHsmsMessagePassThroughListener(this.recvPassThroughLstnr);
			});
			
			this.notifyHsmsCommunicateState(HsmsCommunicateState.NOT_SELECTED);
			
			return f;
		}
	}
	
}
