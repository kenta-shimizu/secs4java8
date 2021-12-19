package com.shimizukenta.secs.util;

import java.io.IOException;
import java.util.Optional;

import com.shimizukenta.secs.SecsCommunicator;
import com.shimizukenta.secs.SecsException;
import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.SecsSendMessageException;
import com.shimizukenta.secs.SecsWaitReplyMessageException;
import com.shimizukenta.secs.gem.Gem;
import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.sml.SmlMessage;

public abstract class AbstractSecsCommunicatorEntity implements SecsCommunicatorEntity {
	
	private final SecsCommunicator comm;
	private final EntityEventAdapter eventAdapter = EntityEventAdapter.newInstance();
	private final EntityMessageSender sender;
	
	public AbstractSecsCommunicatorEntity(SecsCommunicator communicator) {
		this.comm = communicator;
		this.sender = EntityMessageSender.newInstance(communicator);
		this.eventAdapter.adaptToSecsCommunicator(this.comm);
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
	public boolean isOpen() {
		return this.comm.isOpen();
	}
	
	@Override
	public boolean isClosed() {
		return this.comm.isClosed();
	}
	
	@Override
	public void setReplySxF0(boolean doReply) {
		this.eventAdapter.setReplySxF0(doReply);
	}
	
	@Override
	public void setSendS9Fy(boolean doSend) {
		this.eventAdapter.setReplyS9Fy(doSend);
		this.sender.setSendS9F9(doSend);
	}
	
	@Override
	public boolean addCommunicatableStateChangeListener(EntityCommunicatableStateChangeListener listener) {
		return this.eventAdapter.addCommunicatableStateChangeListener(listener);
	}
	
	@Override
	public boolean removeCommunicatableStateChangeListener(EntityCommunicatableStateChangeListener listener) {
		return this.eventAdapter.removeCommunicatableStateChangeListener(listener);
	}
	
	@Override
	public boolean addMessageReceiveListener(int strm, int func, EntityMessageReceiveListener listener) {
		return this.eventAdapter.addMessageReceiveListener(strm, func, listener);
	}
	
	@Override
	public boolean removeMessageReceiveListener(int strm, int func) {
		return this.eventAdapter.removeMessageReceiveListener(strm, func);
	}
	
	@Override
	public Optional<SecsMessage> send(int strm, int func, boolean wbit)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException, InterruptedException {
		
		return this.sender.send(strm, func, wbit);
	}
	
	@Override
	public Optional<SecsMessage> send(int strm, int func, boolean wbit, Secs2 secs2)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException, InterruptedException {
		
		return this.sender.send(strm, func, wbit, secs2);
	}
	
	@Override
	public Optional<SecsMessage> send(SecsMessage primaryMsg, int strm, int func, boolean wbit)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException, InterruptedException {
		
		return this.sender.send(primaryMsg, strm, func, wbit);
	}
	
	@Override
	public Optional<SecsMessage> send(SecsMessage primaryMsg, int strm, int func, boolean wbit, Secs2 secs2)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException, InterruptedException {
		
		return this.sender.send(primaryMsg, strm, func, wbit, secs2);
	}
	
	@Override
	public Optional<SecsMessage> send(SmlMessage sml)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException, InterruptedException {
		
		return this.sender.send(sml);
	}
	
	@Override
	public Optional<SecsMessage> send(SecsMessage primaryMsg, SmlMessage sml)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException, InterruptedException {
		
		return this.sender.send(primaryMsg, sml);
	}
	
	@Override
	public Optional<SecsMessage> sendS9F9(SecsWaitReplyMessageException e)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException, InterruptedException {
		
		return this.sender.sendS9F9(e);
	}
	
	@Override
	public Gem gem() {
		return this.comm.gem();
	}
	
}
