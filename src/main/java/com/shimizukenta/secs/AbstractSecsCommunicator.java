package com.shimizukenta.secs;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

import com.shimizukenta.secs.gem.Gem;
import com.shimizukenta.secs.gem.UsualGem;
import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.sml.SmlMessage;

public abstract class AbstractSecsCommunicator implements SecsCommunicator {

	private final AbstractSecsCommunicatorConfig config;
	private final Gem gem;
	
	private boolean opened;
	private boolean closed;
	private boolean lastCommunicatable;
	
	public AbstractSecsCommunicator(AbstractSecsCommunicatorConfig config) {
		
		this.config = config;
		this.gem = new UsualGem(this, config.gem());
		
		opened = false;
		closed = false;
		lastCommunicatable = false;
	}
	
	@Override
	public boolean isOpened() {
		synchronized ( this ) {
			return opened;
		}
	}
	
	@Override
	public boolean isClosed() {
		synchronized ( this ) {
			return closed;
		}
	}
	
	@Override
	public void open() throws IOException {
		
		synchronized ( this ) {
			
			if ( closed ) {
				throw new IOException("Already closed");
			}
			
			if ( opened ) {
				throw new IOException("Already opened");
			}
			
			opened = true;
		}
	}
	
	@Override
	public void close() throws IOException {
		
		synchronized ( this ) {
			
			if ( closed ) {
				return ;
			}
			
			closed = true;
		}
	}
	
	@Override
	public Gem gem() {
		return gem;
	}
	
	@Override
	public int deviceId() {
		return config.deviceId();
	}
	
	@Override
	public boolean isEquip() {
		return config.isEquip();
	}
	
	protected SecsTimeout timeout() {
		return config.timeout();
	}
	
	@Override
	public Optional<SecsMessage> send(int strm, int func, boolean wbit)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException
			, InterruptedException {
		
		return send(strm, func, wbit, Secs2.empty());
	}
	
	@Override
	public Optional<SecsMessage> send(SecsMessage primary, int strm, int func, boolean wbit)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException
			, InterruptedException {
		
		return send(primary, strm, func, wbit, Secs2.empty());
	}
	
	@Override
	public Optional<SecsMessage> send(SmlMessage sml)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException
			, InterruptedException {
		
		return send(sml.getStream(), sml.getFunction(), sml.wbit(), sml.secs2());
	}
	
	@Override
	public Optional<SecsMessage> send(SecsMessage primary, SmlMessage sml)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException
			, InterruptedException {
		
		return send(primary, sml.getStream(), sml.getFunction(), sml.wbit(), sml.secs2());
	}
	
	
	/* Secs-Message Receive Listener */
	private final Collection<SecsMessageReceiveListener> msgRecvListeners = new CopyOnWriteArrayList<>();
	
	@Override
	public boolean addSecsMessageReceiveListener(SecsMessageReceiveListener lstnr) {
		return msgRecvListeners.add(lstnr);
	}
	
	@Override
	public boolean removeSecsMessageReceiveListener(SecsMessageReceiveListener lstnr) {
		return msgRecvListeners.remove(lstnr);
	}
	
	protected void notifyReceiveMessage(SecsMessage msg) {
		msgRecvListeners.forEach(lstnr -> {
			lstnr.receive(msg);
		});
	}
	
	
	/* Secs-Log Receive Listener */
	private final Collection<SecsLogListener> logListeners = new CopyOnWriteArrayList<>();
	
	@Override
	public boolean addSecsLogListener(SecsLogListener lstnr) {
		return logListeners.add(lstnr);
	}
	
	@Override
	public boolean removeSecsLogListener(SecsLogListener lstnr) {
		return logListeners.remove(lstnr);
	}
	
	protected void notifyLog(SecsLog log) {
		logListeners.forEach(lstnr -> {
			lstnr.receive(log);
		});
	}
	
	protected void notifyLog(CharSequence subject) {
		notifyLog(new SecsLog(createLogSubject(subject)));
	}
	
	protected void notifyLog(CharSequence subject, Object value) {
		notifyLog(new SecsLog(createLogSubject(subject), value));
	}
	
	protected void notifyLog(Throwable t) {
		notifyLog(SecsLog.createThrowableSubject(t), t);
	}
	
	private String createLogSubject(CharSequence subject) {
		return config.communicatorName()
				.map(s -> s + ": " + subject.toString())
				.orElse(subject.toString());
	}
	
	/* Secs-Communicatable-State-Changed-Listener */
	private final Collection<SecsCommunicatableStateChangeListener> commStateChangeListeners = new CopyOnWriteArrayList<>();
	
	@Override
	public boolean addSecsCommunicatableStateChangeListener(SecsCommunicatableStateChangeListener lstnr) {
		
		synchronized ( commStateChangeListeners ) {
			
			lstnr.changed(lastCommunicatable);
			
			return commStateChangeListeners.add(lstnr);
		}
	}
	
	@Override
	public boolean removeSecsCommunicatableStateChangeListener(SecsCommunicatableStateChangeListener lstnr) {
		return commStateChangeListeners.remove(lstnr);
	}
	
	protected void notifyCommunicatableStateChange(boolean communicatable) {
		
		synchronized ( commStateChangeListeners ) {
			
			if ( lastCommunicatable != communicatable ) {
				
				lastCommunicatable = communicatable;
				
				commStateChangeListeners.forEach(lstnr -> {
					lstnr.changed(lastCommunicatable);
				});
			}
		}
	}
	
	
	/* Try-Send Secs-Message Pass-through Listener */
	private final Collection<SecsMessagePassThroughListener> trySendMsgPassThroughListeners = new CopyOnWriteArrayList<>();
	
	@Override
	public boolean addTrySendMessagePassThroughListener(SecsMessagePassThroughListener lstnr) {
		return trySendMsgPassThroughListeners.add(lstnr);
	}
	
	@Override
	public boolean removeTrySendMessagePassThroughListener(SecsMessagePassThroughListener lstnr) {
		return trySendMsgPassThroughListeners.remove(lstnr);
	}
	
	protected void notifyTrySendMessagePassThrough(SecsMessage msg) {
		trySendMsgPassThroughListeners.forEach(lstnr -> {
			lstnr.passThrough(msg);
		});
	}
	
	
	/* Sended Secs-Message Pass-through Listener */
	private final Collection<SecsMessagePassThroughListener> sendedMsgPassThroughListeners = new CopyOnWriteArrayList<>();
	
	@Override
	public boolean addSendedMessagePassThroughListener(SecsMessagePassThroughListener lstnr) {
		return sendedMsgPassThroughListeners.add(lstnr);
	}
	
	@Override
	public boolean removeSendedMessagePassThroughListener(SecsMessagePassThroughListener lstnr) {
		return sendedMsgPassThroughListeners.remove(lstnr);
	}
	
	protected void notifySendedMessagePassThrough(SecsMessage msg) {
		sendedMsgPassThroughListeners.forEach(lstnr -> {
			lstnr.passThrough(msg);
		});
	}
	
	
	/* Receive Secs-Message Pass-through Listener */
	private final Collection<SecsMessagePassThroughListener> recvMsgPassThroughListeners = new CopyOnWriteArrayList<>();
	
	@Override
	public boolean addReceiveMessagePassThroughListener(SecsMessagePassThroughListener lstnr) {
		return recvMsgPassThroughListeners.add(lstnr);
	}
	
	@Override
	public boolean removeReceiveMessagePassThroughListener(SecsMessagePassThroughListener lstnr) {
		return recvMsgPassThroughListeners.remove(lstnr);
	}
	
	protected void notifyReceiveMessagePassThrough(SecsMessage msg) {
		recvMsgPassThroughListeners.forEach(lstnr -> {
			lstnr.passThrough(msg);
		});
	}
	
}
