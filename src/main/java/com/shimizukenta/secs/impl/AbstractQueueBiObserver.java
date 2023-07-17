package com.shimizukenta.secs.impl;

import java.util.Collection;
import java.util.EventListener;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public abstract class AbstractQueueBiObserver<C extends AbstractSecsCommunicator, M extends EventListener, B extends EventListener, V> {
	
	private final BlockingQueue<V> queue = new LinkedBlockingQueue<>();
	private final Collection<M> lstnrs = new CopyOnWriteArrayList<>();
	private final Collection<B> biLstnrs = new CopyOnWriteArrayList<>();
	
	private final C comm;
	
	public AbstractQueueBiObserver(C comm) {
		this.comm = comm;
		
		comm.executorService().execute(() -> {
			
			try {
				for ( ;; ) {
					final V v = this.queue.take();
					this.notifyValue(v);
				}
			}
			catch ( InterruptedException ignore ) {
			}
			
			try {
				for ( ;; ) {
					final V v = this.queue.poll(100L, TimeUnit.MILLISECONDS);
					if (v == null) {
						break;
					}
					this.notifyValue(v);
				}
			}
			catch ( InterruptedException ignore ) {
			}
		});
	}
	
	private void notifyValue(V value) {
		for (M listener : this.lstnrs) {
			this.notifyValueToListener(listener, value);
		}
		for (B biListener : this.biLstnrs) {
			this.notifyValueToBiListener(biListener, value, this.comm);
		}
	}
	
	abstract protected void notifyValueToListener(M listener, V value);
	abstract protected void notifyValueToBiListener(B biListener, V value, C communicator);
	
	public boolean addListener(M listener) {
		return this.lstnrs.add(listener);
	}
	
	public boolean removeListener(M listener) {
		return this.lstnrs.remove(listener);
	}
	
	public boolean addBiListener(B biListener) {
		return this.biLstnrs.add(biListener);
	}
	
	public boolean removeBiListener(B biListener) {
		return this.biLstnrs.remove(biListener);
	}
	
	public void put(V value) throws InterruptedException {
		this.queue.put(value);
	}
	
}
