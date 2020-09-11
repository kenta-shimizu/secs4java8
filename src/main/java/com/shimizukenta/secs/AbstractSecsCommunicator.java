package com.shimizukenta.secs;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
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
import com.shimizukenta.secs.gem.UsualGem;
import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.sml.SmlMessage;

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
	
	protected <T> T executeInvokeAny(Callable<T> task)
			throws InterruptedException, ExecutionException{
		return execServ.invokeAny(Collections.singleton(task));
	}
	
	protected <T> T executeInvokeAny(Callable<T> task1, Callable<T> task2)
			throws InterruptedException, ExecutionException{
		return execServ.invokeAny(Arrays.asList(task1, task2));
	}
	
	protected <T> T executeInvokeAny(Callable<T> task1, Callable<T> task2, Callable<T> task3)
			throws InterruptedException, ExecutionException{
		return execServ.invokeAny(Arrays.asList(task1, task2, task3));
	}
	
	protected <T> T executeInvokeAny(Collection<? extends Callable<T>> tasks, TimeProperty timeout)
			throws InterruptedException, ExecutionException, TimeoutException {
		return execServ.invokeAny(tasks, timeout.getMilliSeconds(), TimeUnit.MILLISECONDS);
	}
	
	protected <T> T executeInvokeAny(Callable<T> task, TimeProperty timeout)
			throws InterruptedException, ExecutionException, TimeoutException {
		return execServ.invokeAny(
				Collections.singleton(task),
				timeout.getMilliSeconds(),
				TimeUnit.MILLISECONDS);
	}
	
	protected <T> T executeInvokeAny(Callable<T> task1, Callable<T> task2, TimeProperty timeout)
			throws InterruptedException, ExecutionException, TimeoutException {
		return execServ.invokeAny(
				Arrays.asList(task1, task2),
				timeout.getMilliSeconds(),
				TimeUnit.MILLISECONDS);
	}
	
	protected <T> T executeInvokeAny(Callable<T> task1, Callable<T> task2, Callable<T> task3, TimeProperty timeout)
			throws InterruptedException, ExecutionException, TimeoutException {
		return execServ.invokeAny(
				Arrays.asList(task1, task2, task3),
				timeout.getMilliSeconds(),
				TimeUnit.MILLISECONDS);
	}
	
	
	private final AbstractSecsCommunicatorConfig config;
	private final Gem gem;
	
	private boolean opened;
	private boolean closed;
	
	public AbstractSecsCommunicator(AbstractSecsCommunicatorConfig config) {
		
		this.config = config;
		this.gem = new UsualGem(this, config.gem());
		
		opened = false;
		closed = false;
	}
	
	@Override
	public boolean isOpened() {
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
			execServ.shutdown();
			if (! execServ.awaitTermination(1L, TimeUnit.MILLISECONDS)) {
				execServ.shutdownNow();
				if (! execServ.awaitTermination(5L, TimeUnit.SECONDS)) {
					throw new IOException("ExecutorService#shutdown failed");
				}
			}
		}
		catch ( InterruptedException ignore ) {
		}
	}
	
	@Override
	public final Gem gem() {
		return gem;
	}
	
	@Override
	public final int deviceId() {
		return config.deviceId().intValue();
	}
	
	@Override
	public final boolean isEquip() {
		return config.isEquip().booleanValue();
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
	public boolean addSecsMessageReceiveListener(SecsMessageReceiveListener l) {
		return msgRecvListeners.add(Objects.requireNonNull(l));
	}
	
	@Override
	public boolean removeSecsMessageReceiveListener(SecsMessageReceiveListener l) {
		return msgRecvListeners.remove(Objects.requireNonNull(l));
	}
	
	private final BlockingQueue<SecsMessage> msgRecvQueue = new LinkedBlockingQueue<>();
	
	private void executeMsgRecvQueueTask() {
		executeLoopTask(() -> {
			SecsMessage msg = msgRecvQueue.take();
			msgRecvListeners.forEach(l -> {l.received(msg);});
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
		executeLoopTask(() -> {
			SecsLog log = logQueue.take();
			logListeners.forEach(l -> {l.received(log);});
		});
	}
	
	protected final boolean offerLogQueue(SecsLog log) {
		return logQueue.offer(log);
	}
	
	protected void notifyLog(SecsLog log) {
		offerLogQueue(new SecsLog(
				createLogSubject(log.subject()),
				log.timestamp(),
				log.value().orElse(null)));
	}
	
	protected void notifyLog(CharSequence subject) {
		offerLogQueue(new SecsLog(createLogSubject(subject)));
	}
	
	protected void notifyLog(CharSequence subject, Object value) {
		offerLogQueue(new SecsLog(
				createLogSubject(subject),
				value));
	}
	
	protected void notifyLog(Throwable t) {
		offerLogQueue(new SecsLog(
				createLogSubject(SecsLog.createThrowableSubject(t)),
				t));
	}
	
	private String createLogSubject(CharSequence subject) {
		if ( subject == null ) {
			return "No-subject";
		} else {
			return config.logSubjectHeader().get() + subject.toString();
		}
	}
	
	
	/* Secs-Communicatable-State-Changed-Listener */
	private final Property<Boolean> communicatable = new BooleanProperty(false);
	
	@Override
	public boolean addSecsCommunicatableStateChangeListener(SecsCommunicatableStateChangeListener l) {
		return this.communicatable.addChangeListener(l::changed);
	}
	
	@Override
	public boolean removeSecsCommunicatableStateChangeListener(SecsCommunicatableStateChangeListener l) {
		return this.communicatable.removeChangeListener(l::changed);
	}
	
	protected void notifyCommunicatableStateChange(boolean communicatable) {
		this.communicatable.set(communicatable);
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
