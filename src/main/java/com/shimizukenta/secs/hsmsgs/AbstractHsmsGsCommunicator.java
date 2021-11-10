package com.shimizukenta.secs.hsmsgs;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

import com.shimizukenta.secs.SecsCommunicatableStateChangeBiListener;
import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.SecsMessageReceiveBiListener;
import com.shimizukenta.secs.hsms.HsmsException;
import com.shimizukenta.secs.hsms.HsmsSendMessageException;
import com.shimizukenta.secs.hsms.HsmsSession;
import com.shimizukenta.secs.hsms.HsmsWaitReplyMessageException;
import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.sml.SmlMessage;

public abstract class AbstractHsmsGsCommunicator implements HsmsGsCommunicator {
	
	private final HsmsGsCommunicatorConfig config;
	private final Set<HsmsSession> sessions;
	
	public AbstractHsmsGsCommunicator(HsmsGsCommunicatorConfig config) {
		this.config = config;
		
		
	}
	
	@Override
	public void open() throws IOException {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub

	}
	
	@Override
	public Set<HsmsSession> getSessions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HsmsSession getSession(int sessionId) {
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<SecsMessage> send(int sessionId, int strm, int func, boolean wbit)
			throws HsmsSendMessageException, HsmsWaitReplyMessageException, HsmsException, InterruptedException {
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<SecsMessage> send(int sessionId, int strm, int func, boolean wbit, Secs2 secs2)
			throws HsmsSendMessageException, HsmsWaitReplyMessageException, HsmsException, InterruptedException {
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<SecsMessage> send(int sessionId, SecsMessage primaryMsg, int strm, int func, boolean wbit)
			throws HsmsSendMessageException, HsmsWaitReplyMessageException, HsmsException, InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<SecsMessage> send(int sessionId, SecsMessage primaryMsg, int strm, int func, boolean wbit, Secs2 secs2)
			throws HsmsSendMessageException, HsmsWaitReplyMessageException, HsmsException, InterruptedException {
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<SecsMessage> send(int sessionId, SmlMessage sml)
			throws HsmsSendMessageException, HsmsWaitReplyMessageException, HsmsException, InterruptedException {
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<SecsMessage> send(int sessionId, SecsMessage primaryMsg, SmlMessage sml)
			throws HsmsSendMessageException, HsmsWaitReplyMessageException, HsmsException, InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addSecsMessageReceiveListener(SecsMessageReceiveBiListener lstnr) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeSecsMessageReceiveListener(SecsMessageReceiveBiListener lstnr) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addSecsCommunicatableStateChangeListener(SecsCommunicatableStateChangeBiListener lstnr) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeSecsCommunicatableStateChangeListener(SecsCommunicatableStateChangeBiListener lstnr) {
		// TODO Auto-generated method stub
		return false;
	}

}
