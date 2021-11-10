package com.shimizukenta.secs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.shimizukenta.secs.gem.Gem;
import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.sml.SmlMessage;

/**
 * This abstract class is implementation of SECS-communicate.
 * 
 * @author kenta-shimizu
 *
 */
public abstract class AbstractSecsCommunicator implements SecsCommunicator {
	
	private final ExecutorService execServ = Executors.newCachedThreadPool(r -> {
		Thread th = new Thread(r);
		th.setDaemon(true);
		return th;
	});
	
	protected ExecutorService executorService() {
		return execServ;
	}
	
	protected static Runnable createLoopTask(InterruptableRunnable task) {
		return new Runnable() {
			@Override
			public void run() {
				try {
					for ( ;; ) {
						task.run();
					}
				}
				catch ( InterruptedException ignore ) {
				}
			}
		};
	}
	
	protected void executeLoopTask(InterruptableRunnable r) {
		execServ.execute(createLoopTask(r));
	}
	
	protected <T> T executeInvokeAny(Collection<? extends Callable<T>> tasks)
			throws InterruptedException, ExecutionException{
		return execServ.invokeAny(tasks);
	}
	
	protected <T> T executeInvokeAny(Collection<? extends Callable<T>> tasks, ReadOnlyTimeProperty timeout)
			throws InterruptedException, ExecutionException, TimeoutException {
		return execServ.invokeAny(tasks, timeout.getMilliSeconds(), TimeUnit.MILLISECONDS);
	}
	
	
	private final BooleanProperty communicatable = BooleanProperty.newInstance(false);
	private final Collection<SecsCommunicatableStateChangeBiListener> commStateChangeBiLstnrs = new ArrayList<>();
	
	private final AbstractSecsCommunicatorConfig config;
	private final Gem gem;
	
	private boolean opened;
	private boolean closed;
	
	public AbstractSecsCommunicator(AbstractSecsCommunicatorConfig config) {
		
		this.communicatable.addChangeListener(f -> {
			this.commStateChangeBiLstnrs.forEach(l -> {
				l.changed(this, f);
			});
		});
		
		this.config = config;
		this.gem = Gem.newInstance(this, config.gem());
		
		opened = false;
		closed = false;
	}
	
	@Override
	public boolean isOpen() {
		synchronized ( this ) {
			return this.opened && ! this.closed;
		}
	}
	
	@Override
	public boolean isClosed() {
		synchronized ( this ) {
			return this.closed;
		}
	}
	
	@Override
	public void open() throws IOException {
		
		synchronized ( this ) {
			
			if ( this.closed ) {
				throw new IOException("Already closed");
			}
			
			if ( this.opened ) {
				throw new IOException("Already opened");
			}
			
			this.opened = true;
		}
		
		executeLogQueueTask();
		executeMsgRecvQueueTask();
		executeTrySendMsgPassThroughQueueTask();
		executeSendedMsgPassThroughQueueTask();
		executeRecvMsgPassThroughQueueTask();
	}
	
	@Override
	public void close() throws IOException {
		
		synchronized ( this ) {
			
			if ( this.closed ) {
				return ;
			}
			
			this.closed = true;
		}
		
		try {
			execServ.shutdownNow();
			if (! execServ.awaitTermination(5L, TimeUnit.SECONDS)) {
				throw new IOException("ExecutorService#shutdown failed");
			}
		}
		catch ( InterruptedException ignore ) {
		}
	}
	
	@Override
	public Gem gem() {
		return gem;
	}
	
//	@Override
//	public int deviceId() {
//		return config.deviceId().intValue();
//	}
	
	public boolean isEquip() {
		return config.isEquip().booleanValue();
	}
	
	@Override
	public void openAndWaitUntilCommunicating() throws IOException, InterruptedException {
		
		synchronized ( this ) {
			if ( ! isOpen() ) {
				open();
			}
		}
		
		communicatable.waitUntilTrue();
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
	public boolean addSecsMessageReceiveListener(SecsMessageReceiveListener l) {
		return msgRecvListeners.add(Objects.requireNonNull(l));
	}
	
	@Override
	public boolean removeSecsMessageReceiveListener(SecsMessageReceiveListener l) {
		return msgRecvListeners.remove(Objects.requireNonNull(l));
	}
	
	private final Collection<SecsMessageReceiveBiListener> msgRecvBiListeners = new CopyOnWriteArrayList<>();
	
	@Override
	public boolean addSecsMessageReceiveListener(SecsMessageReceiveBiListener l) {
		return msgRecvBiListeners.add(Objects.requireNonNull(l));
	}
	
	@Override
	public boolean removeSecsMessageReceiveListener(SecsMessageReceiveBiListener l) {
		return msgRecvBiListeners.remove(Objects.requireNonNull(l));
	}
	
	private final BlockingQueue<SecsMessage> msgRecvQueue = new LinkedBlockingQueue<>();
	
	private void executeMsgRecvQueueTask() {
		executeLoopTask(() -> {
			SecsMessage msg = msgRecvQueue.take();
			msgRecvListeners.forEach(l -> {
				l.received(msg);
			});
			msgRecvBiListeners.forEach(l -> {
				l.received(msg, this);
			});
		});
	}
	
	protected final boolean offerMsgRecvQueue(SecsMessage msg) {
		return msgRecvQueue.offer(msg);
	}
	
	protected void notifyReceiveMessage(SecsMessage msg) {
		offerMsgRecvQueue(msg);
	}
	
	
	/* Secs-Log Receive Listener */
	private final Collection<SecsLogListener> logListeners = new CopyOnWriteArrayList<>();
	
	@Override
	public boolean addSecsLogListener(SecsLogListener l) {
		return logListeners.add(Objects.requireNonNull(l));
	}
	
	@Override
	public boolean removeSecsLogListener(SecsLogListener l) {
		return logListeners.remove(Objects.requireNonNull(l));
	}
	
	private final BlockingQueue<SecsLog> logQueue = new LinkedBlockingQueue<>();
	
	private void executeLogQueueTask() {
		
		this.executorService().execute(() -> {
			
			try {
				for ( ;; ) {
					final SecsLog log = logQueue.take();
					logListeners.forEach(l -> {
						l.received(log);
					});
				}
			}
			catch ( InterruptedException ignore ) {
			}
			
			try {
				for ( ;; ) {
					
					final SecsLog log = logQueue.poll(100L, TimeUnit.MILLISECONDS);
					if ( log == null ) {
						break;
					}
					logListeners.forEach(l -> {
						l.received(log);
					});
				}
			}
			catch ( InterruptedException ignore ) {
			}
		});
	}
	
	protected final boolean offerLogQueue(AbstractSecsLog log) {
		return logQueue.offer(log);
	}
	
	protected void notifyLog(AbstractSecsLog log) {
		log.subjectHeader(this.config.logSubjectHeader().get());
		offerLogQueue(log);
	}
	
	protected void notifyLog(Throwable t) {
		
		notifyLog(new AbstractSecsThrowableLog(t) {
			
			private static final long serialVersionUID = -1271705310309086030L;
		});
	}
	
	
	/* Secs-Communicatable-State-Changed-Listener */
	
	@Override
	public boolean addSecsCommunicatableStateChangeListener(SecsCommunicatableStateChangeListener l) {
		return this.communicatable.addChangeListener(l::changed);
	}
	
	@Override
	public boolean removeSecsCommunicatableStateChangeListener(SecsCommunicatableStateChangeListener l) {
		return this.communicatable.removeChangeListener(l::changed);
	}
	
	@Override
	public boolean addSecsCommunicatableStateChangeListener(SecsCommunicatableStateChangeBiListener l) {
		synchronized ( this.commStateChangeBiLstnrs ) {
			boolean f = this.commStateChangeBiLstnrs.add(l);
			l.changed(this, this.communicatable.booleanValue());
			return f;
		}
	}
	
	@Override
	public boolean removeSecsCommunicatableStateChangeListener(SecsCommunicatableStateChangeBiListener l) {
		synchronized ( this.commStateChangeBiLstnrs ) {
			return this.commStateChangeBiLstnrs.remove(l);
		}
	}
	
	protected void notifyCommunicatableStateChange(boolean communicatable) {
		synchronized ( this.commStateChangeBiLstnrs ) {
			this.communicatable.set(communicatable);
		}
	}
	
	/* Try-Send Secs-Message Pass-through Listener */
	private final Collection<SecsMessagePassThroughListener> trySendMsgPassThroughListeners = new CopyOnWriteArrayList<>();
	
	@Override
	public boolean addTrySendMessagePassThroughListener(SecsMessagePassThroughListener l) {
		return trySendMsgPassThroughListeners.add(Objects.requireNonNull(l));
	}
	
	@Override
	public boolean removeTrySendMessagePassThroughListener(SecsMessagePassThroughListener l) {
		return trySendMsgPassThroughListeners.remove(Objects.requireNonNull(l));
	}
	
	private BlockingQueue<SecsMessage> trySendMsgPassThroughQueue = new LinkedBlockingQueue<>();
	
	private void executeTrySendMsgPassThroughQueueTask() {
		executeLoopTask(() -> {
			SecsMessage msg = trySendMsgPassThroughQueue.take();
			trySendMsgPassThroughListeners.forEach(l -> {l.passThrough(msg);});
		});
	}
	
	protected final boolean offerTrySendMsgPassThroughQueue(SecsMessage msg) {
		return trySendMsgPassThroughQueue.offer(msg);
	}
	
	protected void notifyTrySendMessagePassThrough(SecsMessage msg) {
		offerTrySendMsgPassThroughQueue(msg);
	}
	
	
	/* Sended Secs-Message Pass-through Listener */
	private final Collection<SecsMessagePassThroughListener> sendedMsgPassThroughListeners = new CopyOnWriteArrayList<>();
	
	@Override
	public boolean addSendedMessagePassThroughListener(SecsMessagePassThroughListener l) {
		return sendedMsgPassThroughListeners.add(Objects.requireNonNull(l));
	}
	
	@Override
	public boolean removeSendedMessagePassThroughListener(SecsMessagePassThroughListener l) {
		return sendedMsgPassThroughListeners.remove(Objects.requireNonNull(l));
	}
	
	private final BlockingQueue<SecsMessage> sendedMsgPassThroughQueue = new LinkedBlockingQueue<>();
	
	private void executeSendedMsgPassThroughQueueTask() {
		executeLoopTask(() -> {
			SecsMessage msg = sendedMsgPassThroughQueue.take();
			sendedMsgPassThroughListeners.forEach(l -> {l.passThrough(msg);});
		});
	}
	
	protected final boolean offerSendedMsgPassThroughQueue(SecsMessage msg) {
		return sendedMsgPassThroughQueue.offer(msg);
	}
	
	protected void notifySendedMessagePassThrough(SecsMessage msg) {
		offerSendedMsgPassThroughQueue(msg);
	}
	
	
	/* Receive Secs-Message Pass-through Listener */
	private final Collection<SecsMessagePassThroughListener> recvMsgPassThroughListeners = new CopyOnWriteArrayList<>();
	
	@Override
	public boolean addReceiveMessagePassThroughListener(SecsMessagePassThroughListener l) {
		return recvMsgPassThroughListeners.add(Objects.requireNonNull(l));
	}
	
	@Override
	public boolean removeReceiveMessagePassThroughListener(SecsMessagePassThroughListener l) {
		return recvMsgPassThroughListeners.remove(Objects.requireNonNull(l));
	}
	
	private final BlockingQueue<SecsMessage> recvMsgPassThroughQueue = new LinkedBlockingQueue<>();
	
	private void executeRecvMsgPassThroughQueueTask() {
		executeLoopTask(() -> {
			SecsMessage msg = recvMsgPassThroughQueue.take();
			recvMsgPassThroughListeners.forEach(l -> {l.passThrough(msg);});
		});
	}
	
	protected final boolean offerRecvMsgPassThroughQueue(SecsMessage msg) {
		return recvMsgPassThroughQueue.offer(msg);
	}
	
	protected void notifyReceiveMessagePassThrough(SecsMessage msg) {
		offerRecvMsgPassThroughQueue(msg);
	}
	
}
