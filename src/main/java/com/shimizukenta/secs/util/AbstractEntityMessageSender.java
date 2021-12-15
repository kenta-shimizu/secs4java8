package com.shimizukenta.secs.util;

import java.util.Optional;

import com.shimizukenta.secs.SecsCommunicator;
import com.shimizukenta.secs.SecsException;
import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.SecsSendMessageException;
import com.shimizukenta.secs.SecsWaitReplyMessageException;
import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.sml.SmlMessage;

public abstract class AbstractEntityMessageSender implements EntityMessageSender {
	
	private final SecsCommunicator comm;
	private final Object syncDoSendS9F9 = new Object();
	private boolean doSendS9F9;
	
	public AbstractEntityMessageSender(SecsCommunicator communicator) {
		this.comm = communicator;
		this.doSendS9F9 = false;
	}
	
	@Override
	public void setSendS9F9(boolean doSend) {
		synchronized ( this.syncDoSendS9F9 ) {
			this.doSendS9F9 = doSend;
		}
	}
	
	@Override
	public Optional<SecsMessage> sendS9F9(SecsWaitReplyMessageException e)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException,
			InterruptedException {
		
		final SecsMessage refMsg = e.secsMessage().orElse(null);
		
		if ( refMsg != null ) {
			synchronized ( this.syncDoSendS9F9 ) {
				if ( this.doSendS9F9 ) {
					return comm.gem().s9f9(refMsg);
				}
			}
		}
		
		return Optional.empty();
	}
	
	public void sendS9F9IgnoreException(SecsWaitReplyMessageException e)
			throws InterruptedException {
		
		try {
			this.sendS9F9(e);
		}
		catch ( SecsException ignore ) {
		}
	}
	
	@Override
	public Optional<SecsMessage> send(int strm, int func, boolean wbit)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException,
			InterruptedException {
		
		try {
			return this.comm.send(strm, func, wbit);
		}
		catch ( SecsWaitReplyMessageException e ) {
			this.sendS9F9IgnoreException(e);
			throw e;
		}
	}
	
	@Override
	public Optional<SecsMessage> send(int strm, int func, boolean wbit, Secs2 secs2)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException,
			InterruptedException {
		
		try {
			return this.comm.send(strm, func, wbit, secs2);
		}
		catch ( SecsWaitReplyMessageException e ) {
			this.sendS9F9IgnoreException(e);
			throw e;
		}
	}
	
	@Override
	public Optional<SecsMessage> send(SecsMessage primaryMsg, int strm, int func, boolean wbit)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException,
			InterruptedException {
		
		try {
			return this.comm.send(primaryMsg, strm, func, wbit);
		}
		catch ( SecsWaitReplyMessageException e ) {
			this.sendS9F9IgnoreException(e);
			throw e;
		}
	}
	
	@Override
	public Optional<SecsMessage> send(SecsMessage primaryMsg, int strm, int func, boolean wbit, Secs2 secs2)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException,
			InterruptedException {
		
		try {
			return this.comm.send(primaryMsg, strm, func, wbit, secs2);
		}
		catch ( SecsWaitReplyMessageException e ) {
			this.sendS9F9IgnoreException(e);
			throw e;
		}
	}
	
	@Override
	public Optional<SecsMessage> send(SmlMessage sml)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException,
			InterruptedException {
		
		try {
			return this.comm.send(sml);
		}
		catch ( SecsWaitReplyMessageException e ) {
			this.sendS9F9IgnoreException(e);
			throw e;
		}
	}
	
	@Override
	public Optional<SecsMessage> send(SecsMessage primaryMsg, SmlMessage sml)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException,
			InterruptedException {
		
		try {
			return this.comm.send(primaryMsg, sml);
		}
		catch ( SecsWaitReplyMessageException e ) {
			this.sendS9F9IgnoreException(e);
			throw e;
		}
	}
	
}
