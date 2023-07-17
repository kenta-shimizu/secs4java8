package com.shimizukenta.secs.secs1.impl;

import java.io.IOException;
import java.util.Optional;

import com.shimizukenta.secs.SecsException;
import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.SecsSendMessageException;
import com.shimizukenta.secs.SecsWaitReplyMessageException;
import com.shimizukenta.secs.impl.AbstractSecsCommunicator;
import com.shimizukenta.secs.secs1.AbstractSecs1CommunicatorConfig;
import com.shimizukenta.secs.secs1.Secs1Communicator;
import com.shimizukenta.secs.secs1.Secs1Exception;
import com.shimizukenta.secs.secs1.Secs1Message;
import com.shimizukenta.secs.secs1.Secs1MessagePassThroughListener;
import com.shimizukenta.secs.secs1.Secs1SendByteException;
import com.shimizukenta.secs.secs1.Secs1SendMessageException;
import com.shimizukenta.secs.secs1.Secs1WaitReplyMessageException;
import com.shimizukenta.secs.secs2.Secs2;

/**
 * This abstract class is implementation of SECS-I (SEMI-E4).
 * 
 * @author kenta-shimizu
 *
 */
public abstract class AbstractSecs1Communicator extends AbstractSecsCommunicator implements Secs1Communicator {
	
	private final AbstractSecs1CommunicatorConfig config;
	private final Secs1MessageBuilder msgBuilder;
	private final AbstractSecs1Circuit circuit;
	
	private final Secs1MessagePassThroughQueueObserver trySendSecs1MsgPassThroughQueueObserver;
	private final Secs1MessagePassThroughQueueObserver sendedSecs1MsgPassThroughQueueObserver;
	private final Secs1MessagePassThroughQueueObserver recvSecs1MsgPassThroughQueueObserver;
	
	
	public AbstractSecs1Communicator(AbstractSecs1CommunicatorConfig config) {
		super(config);
		this.config = config;
		this.msgBuilder = new AbstractSecs1MessageBuilder(this) {};
		this.circuit = new AbstractSecs1Circuit(this) {};
		
		this.trySendSecs1MsgPassThroughQueueObserver = new Secs1MessagePassThroughQueueObserver(this);
		this.sendedSecs1MsgPassThroughQueueObserver = new Secs1MessagePassThroughQueueObserver(this);
		this.recvSecs1MsgPassThroughQueueObserver = new Secs1MessagePassThroughQueueObserver(this);
	}
	
	public AbstractSecs1CommunicatorConfig config() {
		return config;
	}
	
	public Secs1MessageBuilder messageBuilder() {
		return this.msgBuilder;
	}
	
	@Override
	public int deviceId() {
		return this.config.deviceId().intValue();
	}
	
	@Override
	public int sessionId() {
		return -1;
	}
	
	@Override
	public void open() throws IOException {
		super.open();
		
		this.executorService().execute(this.circuit);
	}
	
	@Override
	public void close() throws IOException {
		super.close();
	}
	
	@Override
	public Optional<SecsMessage> templateSend(int strm, int func, boolean wbit, Secs2 secs2)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException, InterruptedException {
		
		return this.circuit.send(this.messageBuilder().build(strm, func, wbit, secs2))
				.map(m -> (SecsMessage)m);
	}
	
	@Override
	public Optional<SecsMessage> templateSend(SecsMessage primaryMsg, int strm, int func, boolean wbit, Secs2 secs2)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException, InterruptedException {
		
		return this.circuit.send(this.messageBuilder().build(primaryMsg, strm, func, wbit, secs2))
				.map(m -> (SecsMessage)m);
	}
	
	@Override
	public Optional<Secs1Message> send(Secs1Message msg)
			throws Secs1SendMessageException, Secs1WaitReplyMessageException, Secs1Exception,
			InterruptedException {
		
		return this.circuit.send(Secs1MessageBuilder.fromMessage(msg));
	}
	
	protected void putByte(byte b) throws InterruptedException {
		this.circuit.putByte(b);
	}
	
	protected void putBytes(byte[] bs) throws InterruptedException {
		this.circuit.putBytes(bs);
	}
	
	abstract public void sendBytes(byte[] bs) throws Secs1SendByteException, InterruptedException;
	
	
	/* try send SECS-I Message Pass through */
	
	@Override
	public boolean addTrySendSecs1MessagePassThroughListener(Secs1MessagePassThroughListener lstnr) {
		return this.trySendSecs1MsgPassThroughQueueObserver.addListener(lstnr);
	}
	
	@Override
	public boolean removeTrySendSecs1MessagePassThroughListener(Secs1MessagePassThroughListener lstnr) {
		return this.trySendSecs1MsgPassThroughQueueObserver.removeListener(lstnr);
	}
	
	public void notifyTrySendSecs1MessagePassThrough(Secs1Message msg) throws InterruptedException {
		this.trySendSecs1MsgPassThroughQueueObserver.put(msg);
	}
	
	
	/* Sended SECS-I Message Pass Throught */
	
	@Override
	public boolean addSendedSecs1MessagePassThroughListener(Secs1MessagePassThroughListener lstnr) {
		return this.sendedSecs1MsgPassThroughQueueObserver.addListener(lstnr);
	}
	
	@Override
	public boolean removeSendedSecs1MessagePassThroughListener(Secs1MessagePassThroughListener lstnr) {
		return this.sendedSecs1MsgPassThroughQueueObserver.removeListener(lstnr);
	}
	
	public void notifySendedSecs1MessagePassThrough(Secs1Message msg) throws InterruptedException {
		this.sendedSecs1MsgPassThroughQueueObserver.put(msg);
	}
	
	
	/* Receive SECS-I Message Pass Through */
	
	@Override
	public boolean addReceiveSecs1MessagePassThroughListener(Secs1MessagePassThroughListener lstnr) {
		return this.recvSecs1MsgPassThroughQueueObserver.addListener(lstnr);
	}
	
	@Override
	public boolean removeReceiveSecs1MessagePassThroughListener(Secs1MessagePassThroughListener lstnr) {
		return this.recvSecs1MsgPassThroughQueueObserver.removeListener(lstnr);
	}
	
	public void notifyReceiveSecs1MessagePassThrough(Secs1Message msg) throws InterruptedException {
		this.recvSecs1MsgPassThroughQueueObserver.put(msg);
//		this.recvSecs1MsgPassThroughQueue.put(msg);
	}
	
}
