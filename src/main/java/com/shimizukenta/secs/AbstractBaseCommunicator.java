package com.shimizukenta.secs;

import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

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
	
	public <T> T executeInvokeAny(Collection<? extends Callable<T>> tasks, ReadOnlyTimeProperty timeout)
			throws InterruptedException, ExecutionException, TimeoutException {
		return execServ.invokeAny(tasks, timeout.getMilliSeconds(), TimeUnit.MILLISECONDS);
	}
	
	private final Object syncOpenClose = new Object();
	private boolean opened;
	private boolean closed;
	
	public AbstractBaseCommunicator() {
		this.opened = false;
		this.closed = false;
	}
	
	@Override
	public void open() throws IOException {
		
		synchronized ( this.syncOpenClose ) {
			
			if ( this.closed ) {
				throw new AlreadyClosedException();
			}
			
			if ( this.opened ) {
				throw new AlreadyOpenedException();
			}
			
			this.opened = true;
		}
	}
	
	@Override
	public void close() throws IOException {
		
		synchronized ( this.syncOpenClose ) {
			
			if ( this.closed ) {
				return;
			}
			
			this.closed = true;
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
			return this.opened && ! this.closed;
		}
	}
	
	@Override
	public boolean isClosed() {
		synchronized ( this.syncOpenClose ) {
			return this.closed;
		}
	}
	
	private class AlreadyClosedException extends IOException {
		
		private static final long serialVersionUID = 7336806506693638863L;
		
		public AlreadyClosedException() {
			super();
		}
	}
	
	private class AlreadyOpenedException extends IOException {
		
		private static final long serialVersionUID = 2168211058459635677L;
		
		public AlreadyOpenedException() {
			super();
		}
	}
	
	private class ExecutorServiceShutdownFailedException extends IOException {
		
		private static final long serialVersionUID = -4286787943602603541L;
		
		public ExecutorServiceShutdownFailedException() {
			super();
		}
	}
	
}
