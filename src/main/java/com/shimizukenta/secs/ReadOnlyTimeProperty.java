package com.shimizukenta.secs;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;

/**
 * Time getter
 * 
 * @author kenta-shimizu
 *
 */
public interface ReadOnlyTimeProperty extends ReadOnlyNumberProperty {
	
	/**
	 * Seconds float getter
	 * 
	 * @return Seconds from get().floatValue()
	 */
	public float getSeconds();
	
	/**
	 * MilliSeconds long getter.
	 * 
	 * @return MilliSeconds.
	 */
	public long getMilliSeconds();
	
	/**
	 * 
	 * @return true if > 0
	 */
	public boolean gtZero();
	
	/**
	 * 
	 * @return true if >= 0
	 */
	public boolean geZero();
	
	/**
	 * Thread#sleep
	 * 
	 * @throws InterruptedException
	 */
	public void sleep() throws InterruptedException;
	
	/**
	 * Synchronized wait
	 * 
	 * @param sync-object
	 * @throws InterruptedException
	 */
	public void wait(Object syncObj) throws InterruptedException;
	
	/**
	 * Future#get
	 * 
	 * @param <T>
	 * @param f
	 * @return future#get result
	 * @throws InterruptedException
	 * @throws TimeoutException
	 * @throws ExecutionException
	 */
	public <T> T future(Future<T> f) throws InterruptedException, TimeoutException, ExecutionException;
	
	/**
	 * BlockingQueue#poll
	 * 
	 * @param <T>
	 * @param queue
	 * @return BlockingQueue#poll result
	 * @throws InterruptedException
	 */
	public <T> T poll(BlockingQueue<T> queue) throws InterruptedException;

}
