package com.shimizukenta.secs.hsms.impl;

import java.util.Optional;

import com.shimizukenta.secs.hsms.HsmsCommunicateState;
import com.shimizukenta.secs.hsms.HsmsSessionCommunicateStateLog;
import com.shimizukenta.secs.impl.AbstractSecsLog;

public abstract class AbstractHsmsSessionCommunicateStateLog extends AbstractSecsLog implements HsmsSessionCommunicateStateLog {

	private static final long serialVersionUID = 9043514385784740175L;
	
	private final Object sync = new Object();
	private String cacheToValueString;
	
	private final int sessionId;
	private final HsmsCommunicateState state;
	
	public AbstractHsmsSessionCommunicateStateLog(int sessionId, HsmsCommunicateState state) {
		super("HSMS-Session-Communicate state changed");
		this.sessionId = sessionId;
		this.state = state;
		
		this.cacheToValueString = null;
	}

	@Override
	public int sessionId() {
		return this.sessionId;
	}

	@Override
	public HsmsCommunicateState state() {
		return this.state;
	}
	
	@Override
	public Optional<String> optionalValueString() {
		
		synchronized (this.sync) {
			
			if (this.cacheToValueString == null) {
				
				final StringBuilder sb = new StringBuilder()
						.append("{\"sessionId\":")
						.append(this.sessionId)
						.append(",\"state\":\"")
						.append(this.state)
						.append("\"}");
				
				this.cacheToValueString = sb.toString();
			}
			
			return Optional.of(this.cacheToValueString);
		}
	}
	
	public static AbstractHsmsSessionCommunicateStateLog build(int sessionId, HsmsCommunicateState state) {
		return new AbstractHsmsSessionCommunicateStateLog(sessionId, state) {

			private static final long serialVersionUID = -1471354237568174667L;
		};
	}

}
