package com.shimizukenta.secs.impl;

import java.util.Collection;
import java.util.EventListener;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public abstract class AbstractQueueObserver<L extends EventListener, V> {
	
	private final Collection<L> lstnrs = new CopyOnWriteArrayList<>();
	private final BlockingQueue<V> queue = new LinkedBlockingQueue<>();
	
	public AbstractQueueObserver(Executor executor) {
		
		executor.execute(() -> {
			
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
					if ( v == null ) {
						break;
					}
					this.notifyValue(v);
				}
			}
			catch ( InterruptedException ignore ) {
			}
		});
	}
	
	abstract protected void notifyValueToListener(L listener, V value);
	
	private void notifyValue(V value) {
		for ( L l : this.lstnrs ) {
			this.notifyValueToListener(l, value);
		}
	}
	
	public boolean addListener(L listener) {
		return this.lstnrs.add(Objects.requireNonNull(listener));
	}
	
	public boolean removeListener(L listener) {
		return this.lstnrs.remove(Objects.requireNonNull(listener));
	}
	
	public void put(V value) throws InterruptedException {
		queue.put(value);
	}
	
}
