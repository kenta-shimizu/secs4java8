package com.shimizukenta.secs.secs1;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;

import com.shimizukenta.secs.AbstractSecsCommunicator;
import com.shimizukenta.secs.SecsException;
import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.SecsSendMessageException;
import com.shimizukenta.secs.SecsWaitReplyMessageException;
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
	
	public AbstractSecs1Communicator(AbstractSecs1CommunicatorConfig config) {
		super(config);
		this.config = config;
		this.msgBuilder = new AbstractSecs1MessageBuilder(this) {};
		this.circuit = new AbstractSecs1Circuit(this) {};
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
		
		this.executeTrySendSecs1MsgPassThroughQueueTask();
		this.executeSendedSecs1MsgPassThroughQueueTask();
		this.executeRecvSecs1MsgPassThroughQueueTask();
		
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
	
	
	private final Collection<Secs1MessagePassThroughListener> trySendSecs1MsgPassThroughLstnrs = new CopyOnWriteArrayList<>();
	
	@Override
	public boolean addTrySendSecs1MessagePassThroughListener(Secs1MessagePassThroughListener lstnr) {
		return this.trySendSecs1MsgPassThroughLstnrs.add(lstnr);
	}
	
	@Override
	public boolean removeTrySendSecs1MessagePassThroughListener(Secs1MessagePassThroughListener lstnr) {
		return this.trySendSecs1MsgPassThroughLstnrs.remove(lstnr);
	}
	
	private final BlockingQueue<Secs1Message> trySendSecs1MsgPassThroughQueue = new LinkedBlockingQueue<>();
	
	public void notifyTrySendSecs1MessagePassThrough(Secs1Message msg) throws InterruptedException {
		this.trySendSecs1MsgPassThroughQueue.put(msg);
	}
	
	private void executeTrySendSecs1MsgPassThroughQueueTask() {
		this.executeLoopTask(() -> {
			final Secs1Message msg = this.trySendSecs1MsgPassThroughQueue.take();
			this.trySendSecs1MsgPassThroughLstnrs.forEach(l -> {
				l.passThrough(msg);
			});
		});
	}
	
	private final Collection<Secs1MessagePassThroughListener> sendedSecs1MsgPassThroughLstnrs = new CopyOnWriteArrayList<>();
	
	@Override
	public boolean addSendedSecs1MessagePassThroughListener(Secs1MessagePassThroughListener lstnr) {
		return this.sendedSecs1MsgPassThroughLstnrs.add(lstnr);
	}
	
	@Override
	public boolean removeSendedSecs1MessagePassThroughListener(Secs1MessagePassThroughListener lstnr) {
		return this.sendedSecs1MsgPassThroughLstnrs.remove(lstnr);
	}
	
	private final BlockingQueue<Secs1Message> sendedSecs1MsgPassThroughQueue = new LinkedBlockingQueue<>();
	
	public void notifySendedSecs1MessagePassThrough(Secs1Message msg) throws InterruptedException {
		this.sendedSecs1MsgPassThroughQueue.put(msg);
	}
	
	private void executeSendedSecs1MsgPassThroughQueueTask() {
		this.executeLoopTask(() -> {
			final Secs1Message msg = this.sendedSecs1MsgPassThroughQueue.take();
			this.sendedSecs1MsgPassThroughLstnrs.forEach(l -> {
				l.passThrough(msg);
			});
		});
	}
	
	private final Collection<Secs1MessagePassThroughListener> recvSecs1MsgPassThroughLstnrs = new CopyOnWriteArrayList<>();
	
	@Override
	public boolean addReceiveSecs1MessagePassThroughListener(Secs1MessagePassThroughListener lstnr) {
		return this.recvSecs1MsgPassThroughLstnrs.add(lstnr);
	}
	
	@Override
	public boolean removeReceiveSecs1MessagePassThroughListener(Secs1MessagePassThroughListener lstnr) {
		return this.recvSecs1MsgPassThroughLstnrs.add(lstnr);
	}
	
	private final BlockingQueue<Secs1Message> recvSecs1MsgPassThroughQueue = new LinkedBlockingQueue<>();
	
	public void notifyReceiveSecs1MessagePassThrough(Secs1Message msg) throws InterruptedException {
		this.recvSecs1MsgPassThroughQueue.put(msg);
	}
	
	private void executeRecvSecs1MsgPassThroughQueueTask() {
		this.executeLoopTask(() -> {
			final Secs1Message msg = this.recvSecs1MsgPassThroughQueue.take();
			this.recvSecs1MsgPassThroughLstnrs.forEach(l -> {
				l.passThrough(msg);
			});
		});
	}
	
}
