package com.shimizukenta.secs.secs1.impl;

import java.io.IOException;
import java.util.Optional;

import com.shimizukenta.secs.SecsException;
import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.SecsSendMessageException;
import com.shimizukenta.secs.SecsWaitReplyMessageException;
import com.shimizukenta.secs.impl.AbstractSecsCommunicateStateObserverFacade;
import com.shimizukenta.secs.impl.AbstractSecsCommunicator;
import com.shimizukenta.secs.impl.SecsCommunicateStateDetectableImpl;
import com.shimizukenta.secs.secs1.AbstractSecs1CommunicatorConfig;
import com.shimizukenta.secs.secs1.Secs1Communicator;
import com.shimizukenta.secs.secs1.Secs1Exception;
import com.shimizukenta.secs.secs1.Secs1Message;
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
public abstract class AbstractSecs1Communicator extends AbstractSecsCommunicator
		implements Secs1Communicator,
		Secs1MessageReceiveObservableImpl,
		SecsCommunicateStateDetectableImpl,
		Secs1MessagePassThroughObservableImpl,
		Secs1LogObservableImpl {
	
	private final AbstractSecs1CommunicatorConfig config;
	private final Secs1MessageBuilder msgBuilder;
	private final AbstractSecs1Circuit circuit;
	
	private final AbstractSecs1MessageReceiveObserverFacade secs1MsgRecvObserver;
	private final AbstractSecsCommunicateStateObserverFacade secsCommunicateStateObserver;
	private final AbstractSecs1MessagePassThroughObserverFacade msgPassThroughObserver;
	
	
	public AbstractSecs1Communicator(AbstractSecs1CommunicatorConfig config) {
		super(config);
		this.config = config;
		this.msgBuilder = new AbstractSecs1MessageBuilder() {};
		this.circuit = new AbstractSecs1Circuit(this) {};
		
		this.secs1MsgRecvObserver = new AbstractSecs1MessageReceiveObserverFacade(this.executorService(), this) {};
		this.secsCommunicateStateObserver = new AbstractSecsCommunicateStateObserverFacade(this) {};
		this.msgPassThroughObserver = new AbstractSecs1MessagePassThroughObserverFacade(this.executorService()) {};
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
	public Optional<SecsMessage> send(int strm, int func, boolean wbit, Secs2 secs2)
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
	public Optional<SecsMessage> send(SecsMessage primaryMsg, int strm, int func, boolean wbit, Secs2 secs2)
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
	
	protected void putBytes(byte[] bs) throws InterruptedException {
		this.circuit.putBytes(bs);
	}
	
	abstract public void sendBytes(byte[] bs) throws Secs1SendByteException, InterruptedException;
	
	
	/* Secs1 Message Receivable  */
	
	@Override
	public AbstractSecs1MessageReceiveObserverFacade secs1MessageReceiveObserver() {
		return this.secs1MsgRecvObserver;
	}
	
	
	/* Communicate state detect */
	
	@Override
	public AbstractSecsCommunicateStateObserverFacade secsCommunicateStateObserver() {
		return this.secsCommunicateStateObserver;
	}
	
	
	/* Pass Through */
	
	@Override
	public AbstractSecs1MessagePassThroughObserverFacade secs1PassThroughObserver() {
		return this.msgPassThroughObserver;
	}
	
	public void notifyTrySendSecs1MessagePassThrough(Secs1Message message) throws InterruptedException {
		this.secs1PassThroughObserver().putToTrySendSecs1Message(message);
		this.secs1LogObserver().offerTrySendSecs1MessagePassThrough(message);
	}
	
	public void notifySendedSecs1MessagePassThrough(Secs1Message message) throws InterruptedException {
		this.secs1PassThroughObserver().putToSendedSecs1Message(message);
		this.secs1LogObserver().offerSendedSecs1MessagePassThrough(message);
	}
	
	public void notifyReceiveSecs1MessagePassThrough(Secs1Message message) throws InterruptedException {
		this.secs1PassThroughObserver().putToReceiveSecs1Message(message);
		this.secs1LogObserver().offerReceiveSecs1MessagePassThrough(message);
	}
	
}
