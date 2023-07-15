package com.shimizukenta.secs.local.property.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Predicate;

import com.shimizukenta.secs.local.property.ChangeListener;
import com.shimizukenta.secs.local.property.CollectionCompution;
import com.shimizukenta.secs.local.property.Observable;

/**
 * 
 * @author kenta-shimizu
 *
 * @param <E> Element
 * @param <T> Type
 */
public abstract class AbstractCollectionCompution<E, T extends Collection<E>> implements CollectionCompution<E, T> {
	
	private static final long serialVersionUID = -6611104182991862616L;
	
	/**
	 * Protected synchronized object.
	 */
	protected final Object _sync = new Object();
	
	/**
	 * Mutable value.
	 */
	private T v;
	
	/**
	 * Constructor.
	 * 
	 * @param initial is extends {@code Collection<E>}
	 */
	public AbstractCollectionCompution(T initial) {
		this.v = initial;
	}
	
	/**
	 * Value simple geter.
	 * 
	 * @return value
	 */
	protected T _simpleGet() {
		return this.v;
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
	public boolean contains(Object o) {
		synchronized ( this._sync ) {
			return this._simpleGet().contains(o);
		}
	}

	@Override
	public Iterator<E> iterator() {
		synchronized ( this._sync ) {
			return this._simpleGet().iterator();
		}
	}
	
	@Override
	public Object[] toArray() {
		synchronized ( this._sync ) {
			return this._simpleGet().toArray();
		}
	}

	@Override
	public <U> U[] toArray(U[] a) {
		synchronized ( this._sync ) {
			return this._simpleGet().toArray(a);
		}
	}
	
	@Override
	public boolean add(E e) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean remove(Object o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		synchronized ( this._sync ) {
			return this._simpleGet().containsAll(c);
		}
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public boolean removeIf(Predicate<? super E> filter) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {
		synchronized ( this._sync ) {
			throw new UnsupportedOperationException();
		}
	}
	
	/**
	 * Change listeners.
	 */
	private final Collection<ChangeListener<? super T>> changeLstnrs = new HashSet<>();
	
	@Override
	public boolean addChangeListener(ChangeListener<? super T> l) {
		synchronized ( this._sync ) {
			boolean f = this.changeLstnrs.add(l);
			if ( f ) {
				l.changed(this._simpleGet());
			}
			return f;
		}
	}

	@Override
	public boolean removeChangeListener(ChangeListener<? super T> l) {
		synchronized ( this._sync ) {
			return this.changeLstnrs.remove(l);
		}
	}
	
	/**
	 * synchronized set and notify if value changed.
	 * 
	 * @param c is new Collection
	 */
	protected void _syncSetAndNotifyChanged(T c) {
		synchronized ( this._sync ) {
			final T x = this._simpleGet();
			if ( ! Objects.equals(x, c) ) {
				x.clear();
				x.addAll(c);
				this._notifyChagned();
			}
		}
	}
	
	/**
	 * Bind listener.
	 */
	private final ChangeListener<T> bindLstnr = this::_syncSetAndNotifyChanged;
	
	/**
	 * Add listener to observer.
	 * 
	 * @param observer to add listener
	 * @return true if bind success
	 */
	public boolean bind(Observable<? extends T> observer) {
		return observer.addChangeListener(this.bindLstnr);
	}
	
	/**
	 * To remove listener to observer.
	 * 
	 * @param observer to remove listener
	 * @return true if unbind success
	 */
	public boolean unbind(Observable<? extends T> observer) {
		return observer.removeChangeListener(this.bindLstnr);
	}
	
	/**
	 * Notify to listeners.
	 * 
	 */
	protected void _notifyChagned() {
		final T v = this._unmodifiableCollection(this._simpleGet());
		for ( ChangeListener<? super T> l : this.changeLstnrs ) {
			l.changed(v);
		}
	}
	
	/**
	 * Prototype Collection getter.
	 * 
	 * @param c is extends Collection
	 * @return Extends Collection
	 */
	abstract protected T _unmodifiableCollection(T c);
	
	@Override
	public String toString() {
		synchronized ( this._sync ) {
			return Objects.toString(this._simpleGet());
		}
	}
	
}
