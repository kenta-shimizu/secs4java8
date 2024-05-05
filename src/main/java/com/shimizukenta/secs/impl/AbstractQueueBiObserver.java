package com.shimizukenta.secs.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EventListener;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.shimizukenta.secs.SecsGemAccessor;

public abstract class AbstractQueueBiObserver<C extends SecsGemAccessor, M extends EventListener, B extends EventListener, V> {
	
	private final BlockingQueue<V> queue = new LinkedBlockingQueue<>();
	private final Collection<M> lstnrs = new ArrayList<>();
	private final Collection<B> biLstnrs = new ArrayList<>();
	
	private final Object sync = new Object();
	
	private final C accessor;
	
	public AbstractQueueBiObserver(Executor executor, C accessor) {
		
		this.accessor = accessor;
		
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
		
		synchronized ( this.sync ) {
			
			for (M listener : this.lstnrs) {
				this.notifyValueToListener(listener, value);
			}
			
			for (B biListener : this.biLstnrs) {
				this.notifyValueToBiListener(biListener, value, this.accessor);
			}
		}
	}
	
	abstract protected void notifyValueToListener(M listener, V value);
	abstract protected void notifyValueToBiListener(B biListener, V value, C accessor);
	
	public boolean addListener(M listener) {
		synchronized ( this.sync ) {
			return this.lstnrs.add(Objects.requireNonNull(listener));
		}
	}
	
	public boolean removeListener(M listener) {
		synchronized ( this.sync ) {
			return this.lstnrs.remove(Objects.requireNonNull(listener));
		}
	}
	
	public boolean addBiListener(B biListener) {
		synchronized ( this.sync ) {
			return this.biLstnrs.add(Objects.requireNonNull(biListener));
		}
	}
	
	public boolean removeBiListener(B biListener) {
		synchronized ( this.sync ) {
			return this.biLstnrs.remove(Objects.requireNonNull(biListener));
		}
	}
	
	public void put(V value) throws InterruptedException {
		this.queue.put(value);
	}
	
}
