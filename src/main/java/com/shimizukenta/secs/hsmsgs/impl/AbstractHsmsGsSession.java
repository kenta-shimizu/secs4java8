package com.shimizukenta.secs.hsmsgs.impl;

import com.shimizukenta.secs.hsms.HsmsCommunicateState;
import com.shimizukenta.secs.hsms.impl.AbstractHsmsAsyncSocketChannel;
import com.shimizukenta.secs.hsms.impl.AbstractHsmsSession;
import com.shimizukenta.secs.hsmsgs.HsmsGsCommunicatorConfig;

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
	
	private final Object syncAsyncChannel = new Object();
	
	@Override
	public boolean setAsyncSocketChannel(AbstractHsmsAsyncSocketChannel asyncChannel) {
		synchronized ( this.syncAsyncChannel ) {
			boolean f = super.setAsyncSocketChannel(asyncChannel);
			if ( f ) {
				this.notifyHsmsCommunicateState(HsmsCommunicateState.SELECTED);
			}
			return f;
		}
	}
	
	@Override
	public boolean unsetAsyncSocketChannel() {
		synchronized ( this.syncAsyncChannel ) {
			boolean f = super.unsetAsyncSocketChannel();
			this.notifyHsmsCommunicateState(HsmsCommunicateState.NOT_SELECTED);
			return f;
		}
	}
	
}
