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
import com.shimizukenta.secs.secs1.Secs1MessagePassThroughBiListener;
import com.shimizukenta.secs.secs1.Secs1MessagePassThroughListener;
import com.shimizukenta.secs.secs1.Secs1MessageReceiveBiListener;
import com.shimizukenta.secs.secs1.Secs1MessageReceiveListener;
import com.shimizukenta.secs.secs1.Secs1SendByteException;
import com.shimizukenta.secs.secs1.Secs1SendMessageException;
import com.shimizukenta.secs.secs1.Secs1TooBigMessageBodyException;
import com.shimizukenta.secs.secs1.Secs1TooBigSendMessageException;
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
	
	private final Secs1MessageReceiveQueueBiObserver secs1MsgRecvQueueBiObserver;
	
	private final Secs1MessagePassThroughQueueBiObserver trySendSecs1MsgPassThroughQueueBiObserver;
	private final Secs1MessagePassThroughQueueBiObserver sendedSecs1MsgPassThroughQueueBiObserver;
	private final Secs1MessagePassThroughQueueBiObserver recvSecs1MsgPassThroughQueueBiObserver;
	
	
	public AbstractSecs1Communicator(AbstractSecs1CommunicatorConfig config) {
		super(config);
		this.config = config;
		this.msgBuilder = new AbstractSecs1MessageBuilder() {};
		this.circuit = new AbstractSecs1Circuit(this) {};
		
		this.secs1MsgRecvQueueBiObserver = new Secs1MessageReceiveQueueBiObserver(this.executorService(), this);
		
		this.trySendSecs1MsgPassThroughQueueBiObserver = new Secs1MessagePassThroughQueueBiObserver(this.executorService(), this);
		this.sendedSecs1MsgPassThroughQueueBiObserver = new Secs1MessagePassThroughQueueBiObserver(this.executorService(), this);
		this.recvSecs1MsgPassThroughQueueBiObserver = new Secs1MessagePassThroughQueueBiObserver(this.executorService(), this);
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
		
		try {
			
			return this.circuit.send(this.messageBuilder().buildDataMessage(this, strm, func, wbit, secs2))
					.map(m -> (SecsMessage)m);
		}
		catch (Secs1TooBigMessageBodyException e) {
			throw new Secs1TooBigSendMessageException(e);
		}
	}
	
	@Override
	public Optional<SecsMessage> templateSend(SecsMessage primaryMsg, int strm, int func, boolean wbit, Secs2 secs2)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException, InterruptedException {
		
		try {
			return this.circuit.send(this.messageBuilder().buildDataMessage(this, primaryMsg, strm, func, wbit, secs2))
					.map(m -> (SecsMessage)m);
		}
		catch (Secs1TooBigMessageBodyException e) {
			throw new Secs1TooBigSendMessageException(e);
		}
	}
	
	@Override
	public Optional<Secs1Message> send(Secs1Message msg)
			throws Secs1SendMessageException, Secs1WaitReplyMessageException, Secs1Exception,
			InterruptedException {
		
		return this.circuit.send(msg);
	}
	
	protected void putByte(byte b) throws InterruptedException {
		this.circuit.putByte(b);
	}
	
	protected void putBytes(byte[] bs) throws InterruptedException {
		this.circuit.putBytes(bs);
	}
	
	abstract public void sendBytes(byte[] bs) throws Secs1SendByteException, InterruptedException;
	
	@Override
	public boolean addSecs1MessageReceiveListener(Secs1MessageReceiveListener listener) {
		return this.secs1MsgRecvQueueBiObserver.addListener(listener);
	}
	
	@Override
	public boolean removeSecs1MessageReceiveListener(Secs1MessageReceiveListener listener) {
		return this.secs1MsgRecvQueueBiObserver.removeListener(listener);
	}
	
	@Override
	public boolean addSecs1MessageReceiveBiListener(Secs1MessageReceiveBiListener biListener) {
		return this.secs1MsgRecvQueueBiObserver.addBiListener(biListener);
	}
	
	@Override
	public boolean removeSecs1MessageReceiveBiListener(Secs1MessageReceiveBiListener biListener) {
		return this.secs1MsgRecvQueueBiObserver.removeBiListener(biListener);
	}
	
	public void notifyReceiveSecs1Message(Secs1Message message) throws InterruptedException {
		super.notifyReceiveSecsMessage(message);
		this.secs1MsgRecvQueueBiObserver.put(message);
	}
	
	
	/* try send SECS-I Message Pass through */
	
	@Override
	public boolean addTrySendSecs1MessagePassThroughListener(Secs1MessagePassThroughListener lstnr) {
		return this.trySendSecs1MsgPassThroughQueueBiObserver.addListener(lstnr);
	}
	
	@Override
	public boolean removeTrySendSecs1MessagePassThroughListener(Secs1MessagePassThroughListener lstnr) {
		return this.trySendSecs1MsgPassThroughQueueBiObserver.removeListener(lstnr);
	}
	
	@Override
	public boolean addTrySendSecs1MessagePassThroughBiListener(Secs1MessagePassThroughBiListener biListener) {
		return this.trySendSecs1MsgPassThroughQueueBiObserver.addBiListener(biListener);
	}
	
	@Override
	public boolean removeTrySendSecs1MessagePassThroughBiListener(Secs1MessagePassThroughBiListener biListener) {
		return this.trySendSecs1MsgPassThroughQueueBiObserver.removeBiListener(biListener);
	}
	
	public void notifyTrySendSecs1MessagePassThrough(Secs1Message message) throws InterruptedException {
		super.notifyTrySendSecsMessagePassThrough(message);
		this.trySendSecs1MsgPassThroughQueueBiObserver.put(message);
	}
	
	
	/* Sended SECS-I Message Pass Throught */
	
	@Override
	public boolean addSendedSecs1MessagePassThroughListener(Secs1MessagePassThroughListener lstnr) {
		return this.sendedSecs1MsgPassThroughQueueBiObserver.addListener(lstnr);
	}
	
	@Override
	public boolean removeSendedSecs1MessagePassThroughListener(Secs1MessagePassThroughListener lstnr) {
		return this.sendedSecs1MsgPassThroughQueueBiObserver.removeListener(lstnr);
	}
	
	@Override
	public boolean addSendedSecs1MessagePassThroughBiListener(Secs1MessagePassThroughBiListener biListener) {
		return this.sendedSecs1MsgPassThroughQueueBiObserver.addBiListener(biListener);
	}
	
	@Override
	public boolean removeSendedSecs1MessagePassThroughBiListener(Secs1MessagePassThroughBiListener biListener) {
		return this.sendedSecs1MsgPassThroughQueueBiObserver.removeBiListener(biListener);
	}
	
	public void notifySendedSecs1MessagePassThrough(Secs1Message message) throws InterruptedException {
		super.notifySendedSecsMessagePassThrough(message);
		this.sendedSecs1MsgPassThroughQueueBiObserver.put(message);
	}
	
	
	/* Receive SECS-I Message Pass Through */
	
	@Override
	public boolean addReceiveSecs1MessagePassThroughListener(Secs1MessagePassThroughListener lstnr) {
		return this.recvSecs1MsgPassThroughQueueBiObserver.addListener(lstnr);
	}
	
	@Override
	public boolean removeReceiveSecs1MessagePassThroughListener(Secs1MessagePassThroughListener lstnr) {
		return this.recvSecs1MsgPassThroughQueueBiObserver.removeListener(lstnr);
	}
	
	@Override
	public boolean addReceiveSecs1MessagePassThroughBiListener(Secs1MessagePassThroughBiListener biListener) {
		return this.recvSecs1MsgPassThroughQueueBiObserver.addBiListener(biListener);
	}
	
	@Override
	public boolean removeReceiveSecs1MessagePassThroughBiListener(Secs1MessagePassThroughBiListener biListener) {
		return this.recvSecs1MsgPassThroughQueueBiObserver.removeBiListener(biListener);
	}
	
	public void notifyReceiveSecs1MessagePassThrough(Secs1Message message) throws InterruptedException {
		super.notifyReceiveSecsMessagePassThrough(message);
		this.recvSecs1MsgPassThroughQueueBiObserver.put(message);
	}
	
}
