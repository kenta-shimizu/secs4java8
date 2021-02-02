package com.shimizukenta.secs.hsmsss;

import java.time.LocalDateTime;
import java.util.Optional;

import com.shimizukenta.secs.AbstractSecsCommunicateStateChangeLog;

public final class HsmsSsCommunicateStateChangeLog extends AbstractSecsCommunicateStateChangeLog {
	
	private static final long serialVersionUID = 1216337647368678164L;
	
	private static final String commonSubjectHeader = "HSMS-SS communicate state changed: ";
	
	private HsmsSsCommunicateState state;
	
	private HsmsSsCommunicateStateChangeLog(CharSequence subject, LocalDateTime timestamp, HsmsSsCommunicateState state) {
		super(subject, timestamp, state);
		this.state = state;
	}
	
	private HsmsSsCommunicateStateChangeLog(CharSequence subject, HsmsSsCommunicateState state) {
		super(subject, state);
		this.state = state;
	}
	
	@Override
	public Optional<String> optionalValueString() {
		return Optional.empty();
	}
	
	public HsmsSsCommunicateState state() {
		return this.state;
	}
	
	public static HsmsSsCommunicateStateChangeLog get(HsmsSsCommunicateState state, LocalDateTime timestamp) {
		return new HsmsSsCommunicateStateChangeLog(createSubject(state), timestamp, state);
	}
	
	public static HsmsSsCommunicateStateChangeLog get(HsmsSsCommunicateState state) {
		return new HsmsSsCommunicateStateChangeLog(createSubject(state), state);
	}
	
	private static String createSubject(HsmsSsCommunicateState state) {
		return commonSubjectHeader + state.toString();
	}
	
}
