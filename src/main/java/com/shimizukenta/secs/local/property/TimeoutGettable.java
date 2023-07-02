package com.shimizukenta.secs.local.property;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * TimeoutAndUnit value Getter.
 * 
 * @author kenta-shimizu
 * @see TimeUnit
 * @see TimeoutAndUnit
 * @see Gettable
 * 
 */
public interface TimeoutGettable extends Gettable<TimeoutAndUnit> {
	
	/**
	 * Value Getter.
	 * 
	 * @return value
	 */
	public TimeoutAndUnit get();
	
	/**
	 * Returns timeout(long) value.
	 * 
	 * @return timeout(long) value
	 * @see TimeoutAndUnit#timeout()
	 */
	default public long getTimeout() {
		return this.get().timeout();
	}
	
	/**
	 * Returns TimeUnit.
	 * 
	 * @return TimeUnit
	 * @see TimeoutAndUnit#unit()
	 */
	default public TimeUnit getTimeUnit() {
		return this.get().unit();
	}
	
	/**
	 * This calls TimeUnit#sleep(long).
	 * 
	 * @throws InterruptedException if interrupted while sleeping
	 * @see TimeUnit#sleep(long)
	 */
	default public void sleep() throws InterruptedException {
		TimeoutAndUnit a = this.get();
		a.unit().sleep(a.timeout());
	}
	
	/**
	 * This calls TimeUnit#timedJoin(Thread, long).
	 * 
	 * @param thread the Thread
	 * @throws InterruptedException if interrupted while waiting
	 * @see TimeUnit#timedJoin(Thread, long)
	 */
	default public void join(Thread thread) throws InterruptedException {
		TimeoutAndUnit a = this.get();
		a.unit().timedJoin(thread, a.timeout());
	}
	
	/**
	 * This calls TimeUnit#timedWait(Object, long).
	 * 
	 * @param sync the object to wait on
	 * @throws InterruptedException if interrupted while waiting
	 * @see TimeUnit#timedWait(Object, long)
	 */
	default public void wait(Object sync) throws InterruptedException {
		TimeoutAndUnit a = this.get();
		a.unit().timedWait(sync, a.timeout());
	}
	
	/**
	 * This calls BlockingQueue#poll(long, TimeUnit).
	 * 
	 * @param <T> Type
	 * @param queue the BlockingQueue
	 * @return poll-value
	 * @throws InterruptedException if interrupted while waiting
	 * @see BlockingQueue#poll(long, TimeUnit)
	 */
	default public <T> T blockingQueuePoll(BlockingQueue<T> queue) throws InterruptedException {
		TimeoutAndUnit a = this.get();
		return queue.poll(a.timeout(),  a.unit());
	}
	
	/**
	 * This calls Lock#tryLock(long, TimeUnit).
	 * 
	 * @param lock the Lock
	 * @return true if the lock was acquired and false if the waiting time elapsed before the lock was acquired
	 * @throws InterruptedException  if the current thread is interrupted while acquiring the lock (and interruption of lock acquisition is supported)
	 * @see Lock#tryLock(long, TimeUnit)
	 */
	default public boolean lockTryLock(Lock lock) throws InterruptedException {
		TimeoutAndUnit a = this.get();
		return lock.tryLock(a.timeout(), a.unit());
	}
	
	/**
	 * This calls Condition#await(long, TimeUnit).
	 * 
	 * @param condition the Condition
	 * @return false if the waiting time detectably elapsed before return from the method, else true
	 * @throws InterruptedException if the current thread is interrupted (and interruption of thread suspension is supported)
	 * @see Condition#await(long, TimeUnit)
	 */
	default public boolean conditionAwait(Condition condition) throws InterruptedException {
		TimeoutAndUnit a = this.get();
		return condition.await(a.timeout(), a.unit());
	}
	
	/**
	 * This calls Future#get(long, TimeUnit).
	 * 
	 * @param <T> Type
	 * @param future the Future
	 * @return future-result
	 * @throws InterruptedException if the current thread was interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @throws ExecutionException if the computation threw an exception
	 * @see Future#get(long, TimeUnit)
	 */
	default public <T> T futureGet(Future<T> future) throws InterruptedException, TimeoutException, ExecutionException {
		TimeoutAndUnit a = this.get();
		return future.get(a.timeout(), a.unit());
	}
	
	/**
	 * This calls ExecutorService#invokeAll(Collection, long, TimeUnit).
	 * 
	 * @param <T> Type
	 * @param executorService the ExecutorService
	 * @param tasks collection of tasks
	 * @return Future result list
	 * @throws InterruptedException if the current thread was interrupted while waiting
	 * @see ExecutorService#invokeAll(Collection, long, TimeUnit)
	 */
	default public <T> List<Future<T>> invokeAll(
			ExecutorService executorService,
			Collection<? extends Callable<T>> tasks)
					throws InterruptedException {
		TimeoutAndUnit a = this.get();
		return executorService.invokeAll(tasks, a.timeout(), a.unit());
	}
	
	/**
	 * This calls ExecutorService#invokeAny(Collection, long, TimeUnit).
	 * 
	 * @param <T> Type
	 * @param executorService the ExecutorService
	 * @param tasks  the collection of tasks
	 * @return Callable result
	 * @throws InterruptedException if the current thread was interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @throws ExecutionException if the computation threw an exception
	 * @see ExecutorService#invokeAny(Collection, long, TimeUnit)
	 */
	default public <T> T invokeAny(
			ExecutorService executorService,
			Collection<? extends Callable<T>> tasks)
					throws InterruptedException, TimeoutException, ExecutionException {
		TimeoutAndUnit a = this.get();
		return executorService.invokeAny(tasks, a.timeout(), a.unit());
	}
	
	/**
	 * This calls ExecutorService#awaitTermination(long, TimeUnit).
	 * 
	 * @param executorService the ExecutorService
	 * @return true if this executor terminated and false if the timeout elapsed before termination
	 * @throws InterruptedException if interrupted while waiting
	 * @see ExecutorService#awaitTermination(long, TimeUnit)
	 */
	default boolean awaitTermination(ExecutorService executorService) throws InterruptedException {
		TimeoutAndUnit a = this.get();
		return executorService.awaitTermination(a.timeout(), a.unit());
	}
	
}
