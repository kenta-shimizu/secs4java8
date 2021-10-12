package com.shimizukenta.secs.hsmsss;

import java.time.LocalDateTime;
import java.util.Optional;

import com.shimizukenta.secs.AbstractSecsCommunicateStateChangeLog;
import com.shimizukenta.secs.hsms.HsmsCommunicateState;

public final class HsmsSsCommunicateStateChangeLog extends AbstractSecsCommunicateStateChangeLog {
	
	private static final long serialVersionUID = 1216337647368678164L;
	
	private static final String commonSubjectHeader = "HSMS-SS communicate state changed: ";
	
	private HsmsCommunicateState state;
	
	private HsmsSsCommunicateStateChangeLog(CharSequence subject, LocalDateTime timestamp, HsmsCommunicateState state) {
		super(subject, timestamp, state);
		this.state = state;
	}
	
	private HsmsSsCommunicateStateChangeLog(CharSequence subject, HsmsCommunicateState state) {
		super(subject, state);
		this.state = state;
	}
	
	@Override
	public Optional<String> optionalValueString() {
		return Optional.empty();
	}
	
	public HsmsCommunicateState state() {
		return this.state;
	}
	
	public static HsmsSsCommunicateStateChangeLog get(HsmsCommunicateState state, LocalDateTime timestamp) {
		return new HsmsSsCommunicateStateChangeLog(createSubject(state), timestamp, state);
	}
	
	public static HsmsSsCommunicateStateChangeLog get(HsmsCommunicateState state) {
		return new HsmsSsCommunicateStateChangeLog(createSubject(state), state);
	}
	
	private static String createSubject(HsmsCommunicateState state) {
		return commonSubjectHeader + state.toString();
	}
	
}
