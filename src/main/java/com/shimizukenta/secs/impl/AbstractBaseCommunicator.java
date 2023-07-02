package com.shimizukenta.secs.impl;

import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.shimizukenta.secs.AlreadyClosedException;
import com.shimizukenta.secs.AlreadyOpenedException;
import com.shimizukenta.secs.ExecutorServiceShutdownFailedException;
import com.shimizukenta.secs.OpenAndCloseable;
import com.shimizukenta.secs.local.property.BooleanCompution;
import com.shimizukenta.secs.local.property.BooleanProperty;
import com.shimizukenta.secs.local.property.TimeoutProperty;

public abstract class AbstractBaseCommunicator implements OpenAndCloseable {
	
	private final ExecutorService execServ = Executors.newCachedThreadPool(r -> {
		Thread th = new Thread(r);
		th.setDaemon(true);
		return th;
	});
	
	public ExecutorService executorService() {
		return this.execServ;
	}
	
	public static Runnable createLoopTask(InterruptableRunnable task) {
		
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
	
	public void executeLoopTask(InterruptableRunnable r) {
		execServ.execute(createLoopTask(r));
	}
	
	public <T> T executeInvokeAny(Collection<? extends Callable<T>> tasks)
			throws InterruptedException, ExecutionException{
		return execServ.invokeAny(tasks);
	}
	
	public <T> T executeInvokeAny(Collection<? extends Callable<T>> tasks, TimeoutProperty tp)
			throws InterruptedException, ExecutionException, TimeoutException {
		return tp.invokeAny(execServ, tasks);
	}
	
	private final Object syncOpenClose = new Object();
	private final BooleanProperty opened = BooleanProperty.newInstance(false);
	private final BooleanProperty closed = BooleanProperty.newInstance(false);
	private final BooleanCompution isOpen = this.opened.and(this.closed.not());
	
	public AbstractBaseCommunicator() {
		/* Nothing */
	}
	
	@Override
	public void open() throws IOException {
		
		synchronized ( this.syncOpenClose ) {
			
			if ( this.closed.booleanValue() ) {
				throw new AlreadyClosedException();
			}
			
			if ( this.opened.booleanValue() ) {
				throw new AlreadyOpenedException();
			}
			
			this.opened.setTrue();
		}
	}
	
	@Override
	public void close() throws IOException {
		
		synchronized ( this.syncOpenClose ) {
			
			if ( this.closed.booleanValue() ) {
				return;
			}
			
			this.closed.setTrue();
		}
		
		try {
			this.execServ.shutdown();
			if ( ! this.execServ.awaitTermination(1L, TimeUnit.MILLISECONDS) ) {
				this.execServ.shutdownNow();
				if ( ! this.execServ.awaitTermination(10L, TimeUnit.SECONDS) ) {
					throw new ExecutorServiceShutdownFailedException();
				}
			}
		}
		catch ( InterruptedException ignore ) {
		}
	}
	
	@Override
	public boolean isOpen() {
		synchronized ( this.syncOpenClose ) {
			return this.isOpen.booleanValue();
		}
	}
	
	@Override
	public boolean isClosed() {
		synchronized ( this.syncOpenClose ) {
			return this.closed.booleanValue();
		}
	}
	
}
