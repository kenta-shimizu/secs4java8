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
	
	protected <T> T executeInvokeAny(Callable<T> task)
			throws InterruptedException, ExecutionException{
		return engine.executeInvokeAny(task);
	}
	
	protected <T> T executeInvokeAny(Callable<T> task1, Callable<T> task2)
			throws InterruptedException, ExecutionException{
		return engine.executeInvokeAny(task1, task2);
	}
	
	protected <T> T executeInvokeAny(Callable<T> task1, Callable<T> task2, Callable<T> task3)
			throws InterruptedException, ExecutionException{
		return engine.executeInvokeAny(task1, task2, task3);
	}
	
	protected <T> T executeInvokeAny(Collection<? extends Callable<T>> tasks, ReadOnlyTimeProperty timeout)
			throws InterruptedException, ExecutionException, TimeoutException {
		return engine.executeInvokeAny(tasks, timeout);
	}
	
	protected <T> T executeInvokeAny(Callable<T> task, ReadOnlyTimeProperty timeout)
			throws InterruptedException, ExecutionException, TimeoutException {
		return engine.executeInvokeAny(task, timeout);
	}
	
	protected <T> T executeInvokeAny(Callable<T> task1, Callable<T> task2, ReadOnlyTimeProperty timeout)
			throws InterruptedException, ExecutionException, TimeoutException {
		return engine.executeInvokeAny(task1, task2, timeout);
	}
	
	protected <T> T executeInvokeAny(Callable<T> task1, Callable<T> task2, Callable<T> task3, ReadOnlyTimeProperty timeout)
			throws InterruptedException, ExecutionException, TimeoutException {
		return engine.executeInvokeAny(task1, task2, task3, timeout);
	}
	
	protected void notifyReceiveMessage(SecsMessage msg) {
		engine.notifyReceiveMessage(msg);
	}
	
	protected void notifyLog(SecsLog log) {
		engine.notifyLog(log);
	}
	
	protected void notifyLog(CharSequence subject) {
		engine.notifyLog(subject);
	}
	
	protected void notifyLog(CharSequence subject, Object value) {
		engine.notifyLog(subject, value);
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
