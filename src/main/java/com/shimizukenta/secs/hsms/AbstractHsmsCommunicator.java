package com.shimizukenta.secs.hsms;

import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;

import com.shimizukenta.secs.AbstractSecsCommunicator;
import com.shimizukenta.secs.AbstractSecsCommunicatorConfig;
import com.shimizukenta.secs.Property;

public abstract class AbstractHsmsCommunicator extends AbstractSecsCommunicator implements HsmsCommunicator {

	private final Property<HsmsCommunicateState> hsmsCommState = Property.newInstance(HsmsCommunicateState.NOT_CONNECTED);
	
	public AbstractHsmsCommunicator(AbstractSecsCommunicatorConfig config) {
		super(config);
		
		this.hsmsCommState.addChangeListener(state -> {
			this.notifyCommunicatableStateChange(state == HsmsCommunicateState.SELECTED);
		});
	}
	
	@Override
	public void open() throws IOException {
		super.open();
		
		this.executeTrySendHsmsMsgPassThroughQueueTask();
		this.executeSendedHsmsMsgPassThroughQueueTask();
		this.executeRecvHsmsMsgPassThroughQueueTask();
	}
	
	@Override
	public void close() throws IOException {
		super.close();
	}
	
	@Override
	public boolean addHsmsCommunicateStateChangeListener(HsmsCommunicateStateChangeListener listener) {
		return this.hsmsCommState.addChangeListener(listener::changed);
	}
	
	@Override
	public boolean removeHsmsCommunicateStateChangeListener(HsmsCommunicateStateChangeListener listener) {
		return this.hsmsCommState.removeChangeListener(listener::changed);
	}
	
	public void notifyHsmsCommunicateState(HsmsCommunicateState state) {
		this.hsmsCommState.set(state);
	}
	
	private final Collection<HsmsMessagePassThroughListener> trySendHsmsMsgPassThroughLstnrs = new CopyOnWriteArrayList<>();
	
	@Override
	public boolean addTrySendHsmsMessagePassThroughListener(HsmsMessagePassThroughListener lstnr) {
		return this.trySendHsmsMsgPassThroughLstnrs.add(lstnr);
	}
	
	@Override
	public boolean removeTrySendHsmsMessagePassThroughListener(HsmsMessagePassThroughListener lstnr) {
		return this.trySendHsmsMsgPassThroughLstnrs.remove(lstnr);
	}
	
	private final BlockingQueue<HsmsMessage> trySendHsmsMsgQueue = new LinkedBlockingQueue<>();
	
	public void notifyTrySendHsmsMessagePassThrough(HsmsMessage msg) throws InterruptedException {
		this.trySendHsmsMsgQueue.put(msg);
	}
	
	private void executeTrySendHsmsMsgPassThroughQueueTask() {
		this.executeLoopTask(() -> {
			final HsmsMessage msg = this.trySendHsmsMsgQueue.take();
			this.trySendHsmsMsgPassThroughLstnrs.forEach(l -> {
				l.passThrough(msg);
			});
		});
	}
	
	private final Collection<HsmsMessagePassThroughListener> sendedHsmsMsgPassThroughLstnrs = new CopyOnWriteArrayList<>();
	
	@Override
	public boolean addSendedHsmsMessagePassThroughListener(HsmsMessagePassThroughListener lstnr) {
		return this.sendedHsmsMsgPassThroughLstnrs.add(lstnr);
	}
	
	@Override
	public boolean removeSendedHsmsMessagePassThroughListener(HsmsMessagePassThroughListener lstnr) {
		return this.sendedHsmsMsgPassThroughLstnrs.remove(lstnr);
	}
	
	private final BlockingQueue<HsmsMessage> sendedHsmsMsgQueue = new LinkedBlockingQueue<>();
	
	public void notifySendedHsmsMessagePassThrough(HsmsMessage msg) throws InterruptedException {
		this.sendedHsmsMsgQueue.put(msg);
	}
	
	private void executeSendedHsmsMsgPassThroughQueueTask() {
		this.executeLoopTask(() -> {
			final HsmsMessage msg = this.sendedHsmsMsgQueue.take();
			this.sendedHsmsMsgPassThroughLstnrs.forEach(l -> {
				l.passThrough(msg);
			});
		});
	}
	
	private final Collection<HsmsMessagePassThroughListener> recvHsmsMsgPassThroughLstnrs = new CopyOnWriteArrayList<>();
	
	@Override
	public boolean addReceiveHsmsMessagePassThroughListener(HsmsMessagePassThroughListener lstnr) {
		return this.recvHsmsMsgPassThroughLstnrs.add(lstnr);
	}
	
	@Override
	public boolean removeReceiveHsmsMessagePassThroughListener(HsmsMessagePassThroughListener lstnr) {
		return this.recvHsmsMsgPassThroughLstnrs.remove(lstnr);
	}
	
	private final BlockingQueue<HsmsMessage> recvHsmsMsgQueue = new LinkedBlockingQueue<>();
	
	public void notifyReceiveHsmsMessagePassThrough(HsmsMessage msg) throws InterruptedException {
		this.recvHsmsMsgQueue.put(msg);
	}
	
	private void executeRecvHsmsMsgPassThroughQueueTask() {
		this.executeLoopTask(() -> {
			final HsmsMessage msg = this.recvHsmsMsgQueue.take();
			this.recvHsmsMsgPassThroughLstnrs.forEach(l -> {
				l.passThrough(msg);
			});
		});
	}	
	
}
