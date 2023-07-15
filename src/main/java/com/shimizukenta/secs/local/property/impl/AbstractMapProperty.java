package com.shimizukenta.secs.local.property.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.shimizukenta.secs.local.property.ChangeListener;
import com.shimizukenta.secs.local.property.MapProperty;
import com.shimizukenta.secs.local.property.Observable;

/**
 * 
 * @author kenta-shimizu
 *
 * @param <K> Key
 * @param <V> Value
 */
public abstract class AbstractMapProperty<K, V> implements MapProperty<K, V> {
	
	private static final long serialVersionUID = -5051422061789589475L;
	
	/**
	 * Synbhronized Object.
	 */
	private final Object _sync = new Object();
	
	/**
	 * Immutable Map.
	 */
	private final Map<K, V> map;
	
	/**
	 * Constructor.
	 * 
	 * @param initial is extends {@code Map<K, V>}
	 */
	public AbstractMapProperty(Map<K, V> initial) {
		this.map = initial;
	}
	
	/**
	 * Value simple getter.
	 * 
	 * @return value
	 */
	protected Map<K, V> _simpleGet() {
		synchronized ( this._sync ) {
			return this.map;
		}
	}
	
	@Override
	public int size() {
		synchronized ( this._sync ) {
			return this._simpleGet().size();
		}
	}

	@Override
	public boolean isEmpty() {
		synchronized ( this._sync ) {
			return this._simpleGet().isEmpty();
		}
	}
	
	@Override
	public boolean containsKey(Object key) {
		synchronized ( this._sync ) {
			return this._simpleGet().containsKey(key);
		}
	}

	@Override
	public boolean containsValue(Object value) {
		synchronized ( this._sync ) {
			return this._simpleGet().containsValue(value);
		}
	}

	@Override
	public V get(Object key) {
		synchronized ( this._sync ) {
			return this._simpleGet().get(key);
		}
	}
	
	@Override
	public V put(K key, V value) {
		synchronized ( this._sync ) {
			PutResult r = this.__putCheck(key, value);
			if ( r.changed ) {
				this._notifyChanged();
			}
			return r.value;
		}
	}
	
	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		synchronized ( this._sync ) {
			boolean changed = false;
			
			for ( K key : m.keySet() ) {
				PutResult r = this.__putCheck(key, m.get(key));
				if ( r.changed ) {
					changed = true;
				}
			}
			
			if ( changed ) {
				this._notifyChanged();
			}
		}
	}
	
	/**
	 * Inner PutResult.
	 * 
	 * @author kenta-shimizu
	 *
	 */
	private class PutResult {
		
		private final boolean changed;
		private final V value;
		
		private PutResult(boolean changed, V value) {
			this.changed = changed;
			this.value = value;
		}
	}
	
	/**
	 * Returns true if value changed.
	 * 
	 * @param key key
	 * @param value value
	 * @return true if value changed
	 */
	private PutResult __putCheck(K key, V value) {
		
		Map<K, V> m = this._simpleGet();
		
		if ( m.containsKey(key) ) {
			if ( Objects.equals(m.get(key), value) ) {
				return new PutResult(false, value);
			}
		}
		
		V v = m.put(key, value);
		return new PutResult(true, v);
	}
	
	@Override
	public V remove(Object key) {
		synchronized ( this._sync ) {
			
			Map<K, V> m = this._simpleGet();
			
			if ( m.containsKey(key) ) {
				V v = m.remove(key);
				this._notifyChanged();
				return v;
			} else {
				return m.remove(key);
			}
		}
	}

	@Override
	public void clear() {
		synchronized ( this._sync ) {
			Map<K, V> m = this._simpleGet();
			if ( ! m.isEmpty() ) {
				m.clear();
				this._notifyChanged();
			}
		}
	}
	
	@Override
	public Set<K> keySet() {
		synchronized ( this._sync ) {
			return this._simpleGet().keySet();
		}
	}

	@Override
	public Collection<V> values() {
		synchronized ( this._sync ) {
			return this._simpleGet().values();
		}
	}
	
	@Override
	public Set<Entry<K, V>> entrySet() {
		synchronized ( this._sync ) {
			return this._simpleGet().entrySet();
		}
	}
	
	/**
	 * Change listeners.
	 */
	private final Collection<ChangeListener<? super Map<K, V>>> changeLstnrs = new HashSet<>();
	
	@Override
	public boolean addChangeListener(ChangeListener<? super Map<K, V>> l) {
		synchronized ( this._sync ) {
			boolean f = this.changeLstnrs.add(l);
			if ( f ) {
				l.changed(this._simpleGet());
			}
			return f;
		}
	}
	
	@Override
	public boolean removeChangeListener(ChangeListener<? super Map<K, V>> l) {
		synchronized ( this._sync ) {
			return this.changeLstnrs.remove(l);
		}
	}
	
	/**
	 * synchronized set and notify if map changed.
	 * 
	 * @param newMap is {@code Map<K, V>}
	 */
	protected void _syncSetAndNotifyChanged(Map<? extends K, ? extends V> newMap) {
		synchronized ( this._sync ) {
			final Map<K, V> x = this._simpleGet();
			if ( ! Objects.equals(newMap, x) ) {
				x.clear();
				x.putAll(newMap);
				this._notifyChanged();
			}
		}
	}
	
	/**
	 * Bind listener.
	 */
	private final ChangeListener<Map<? extends K, ? extends V>> changeLstnr = this::_syncSetAndNotifyChanged;
	
	@Override
	public boolean bind(Observable<? extends Map<K, V>> observer) {
		return observer.addChangeListener(this.changeLstnr);
	}
	
	@Override
	public boolean unbind(Observable<? extends Map<K, V>> observer) {
		return observer.removeChangeListener(this.changeLstnr);
	}
	
	/**
	 * Notify value to listeners.
	 * 
	 */
	protected void _notifyChanged() {
		synchronized ( this._sync ) {
			final Map<K, V> m = Collections.unmodifiableMap(this._simpleGet());
			for (ChangeListener<? super Map<K, V>> l : this.changeLstnrs ) {
				l.changed(m);
			}
		}
	}
	
	@Override
	public String toString() {
		synchronized ( this._sync ) {
			return Objects.toString(this._simpleGet());
		}
	}
	
}
