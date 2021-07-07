package com.shimizukenta.secs;

import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeoutException;

public abstract class AbstractSecsInnerEngine {
	
	private final AbstractSecsCommunicator engine;
	
	protected AbstractSecsInnerEngine(AbstractSecsCommunicator engine) {
		this.engine = engine;
	}
	
	protected ExecutorService executorService() {
		return engine.executorService();
	}
	
	protected static Runnable createLoopTask(InterruptableRunnable task) {
		return AbstractSecsCommunicator.createLoopTask(task);
	}
	
	protected void executeLoopTask(InterruptableRunnable r) {
		engine.executeLoopTask(r);
	}
	
	protected <T> T executeInvokeAny(Collection<? extends Callable<T>> tasks)
			throws InterruptedException, ExecutionException{
		return engine.executeInvokeAny(tasks);
	}
	
	protected <T> T executeInvokeAny(Collection<? extends Callable<T>> tasks, ReadOnlyTimeProperty timeout)
			throws InterruptedException, ExecutionException, TimeoutException {
		return engine.executeInvokeAny(tasks, timeout);
	}
	
	protected void notifyReceiveMessage(SecsMessage msg) {
		engine.notifyReceiveMessage(msg);
	}
	
	protected void notifyLog(AbstractSecsLog log) {
		engine.notifyLog(log);
	}
	
	protected void notifyLog(Throwable t) {
		engine.notifyLog(t);
	}
	
	protected void notifyTrySendMessagePassThrough(SecsMessage msg) {
		engine.notifyTrySendMessagePassThrough(msg);
	}
	
	protected void notifySendedMessagePassThrough(SecsMessage msg) {
		engine.notifySendedMessagePassThrough(msg);
	}
	
	protected void notifyReceiveMessagePassThrough(SecsMessage msg) {
		engine.notifyReceiveMessagePassThrough(msg);
	}
	
}
