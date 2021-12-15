package com.shimizukenta.secs.util;

import java.io.IOException;
import java.util.Optional;

import com.shimizukenta.secs.SecsCommunicator;
import com.shimizukenta.secs.SecsException;
import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.SecsSendMessageException;
import com.shimizukenta.secs.SecsWaitReplyMessageException;
import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.sml.SmlMessage;

public abstract class AbstractSecsCommunicatorEntity implements SecsCommunicatorEntity {
	
	private final SecsCommunicator comm;
	
	public AbstractSecsCommunicatorEntity(SecsCommunicator communicator) {
		this.comm = communicator;
	}
	
	@Override
	public void open() throws IOException {
		this.comm.open();
	}
	
	@Override
	public void close() throws IOException {
		this.comm.close();
	}
	
	@Override
	public void setReplySxF0(boolean doReply) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setSendS9Fy(boolean doSend) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public boolean addCommunicatableStateChangeListener(EntityCommunicatableStateChangeListener listener) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeCommunicatableStateChangeListener(EntityCommunicatableStateChangeListener listener) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addMessageReceiveListener(int strm, int func, EntityMessageReceiveListener listener) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeMessageReceiveListener(int strm, int func) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public Optional<SecsMessage> send(int strm, int func, boolean wbit)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException, InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<SecsMessage> send(int strm, int func, boolean wbit, Secs2 secs2)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException, InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<SecsMessage> send(SecsMessage primaryMsg, int strm, int func, boolean wbit)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException, InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<SecsMessage> send(SecsMessage primaryMsg, int strm, int func, boolean wbit, Secs2 secs2)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException, InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<SecsMessage> send(SmlMessage sml)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException, InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<SecsMessage> send(SecsMessage primaryMsg, SmlMessage sml)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException, InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

}
