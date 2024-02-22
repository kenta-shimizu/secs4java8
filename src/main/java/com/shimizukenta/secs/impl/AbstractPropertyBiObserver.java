package com.shimizukenta.secs.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import com.shimizukenta.secs.SecsGemAccessor;
import com.shimizukenta.secs.local.property.Observable;

public abstract class AbstractPropertyBiObserver<C extends SecsGemAccessor,V, M, B> {
	
	private final Collection<M> listeners = new ArrayList<>();
	private final Collection<B> biListeners = new ArrayList<>();
	
	private final C communicator;
	
	private final Object sync = new Object();
	private V last;
	
	public AbstractPropertyBiObserver(C communicator, Observable<V> observer) {
		
		this.communicator = communicator;
		this.last = null;
		
		observer.addChangeListener((V value) -> {
			
			synchronized ( this.sync ) {
				
				this.last = value;
				
				for (M l : this.listeners ) {
					this.notifyToListener(l);
				}
				
				for (B l : this.biListeners ) {
					this.notifyToBiListener(l);
				}
			}
		});
	}
	
	abstract protected void notifyValueToListener(M listener, V value);
	abstract protected void notifyValueToBiListener(B biListener, V value, C communicator);
	
	private void notifyToListener(M listener) {
		this.notifyValueToListener(listener, this.last);
	}
	
	private void notifyToBiListener(B biListener) {
		this.notifyValueToBiListener(biListener, this.last, this.communicator);
	}
	
	public boolean addListener(M listener) {
		synchronized ( this.sync ) {
			boolean f = this.listeners.add(Objects.requireNonNull(listener));
			if ( f ) {
				this.notifyToListener(listener);
			}
			return f;
		}
	}
	
	public boolean removeListener(M listener) {
		synchronized ( this.sync ) {
			return this.listeners.remove(Objects.requireNonNull(listener));
		}
	}
	
	public boolean addBiListener(B biListener) {
		synchronized ( this.sync ) {
			boolean f = this.biListeners.add(Objects.requireNonNull(biListener));
			if ( f ) {
				this.notifyToBiListener(biListener);
			}
			return f;
		}
	}
	
	public boolean removeBiListener(B biListener) {
		synchronized ( this.sync ) {
			return this.biListeners.remove(Objects.requireNonNull(biListener));
		}
	}
	
}
