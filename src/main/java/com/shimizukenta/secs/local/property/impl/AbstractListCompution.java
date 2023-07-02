package com.shimizukenta.secs.local.property.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

import com.shimizukenta.secs.local.property.ListCompution;

/**
 * 
 * @author kenta-shimizu
 *
 * @param <E> Element
 */
public abstract class AbstractListCompution<E> extends AbstractCollectionCompution<E, List<E>> implements ListCompution<E> {
	
	private static final long serialVersionUID = -7273441815983971442L;
	
	/**
	 * Constructor.
	 * 
	 * @param initial is extends {@code List<E>}
	 */
	public AbstractListCompution(List<E> initial) {
		super(initial);
	}
	
	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public E get(int index) {
		synchronized ( this._sync ) {
			return this._simpleGet().get(index);
		}
	}

	@Override
	public E set(int index, E element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void add(int index, E element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public E remove(int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int indexOf(Object o) {
		synchronized ( this._sync ) {
			return this._simpleGet().indexOf(o);
		}
	}

	@Override
	public int lastIndexOf(Object o) {
		synchronized  (this._sync ) {
			return this._simpleGet().lastIndexOf(o);
		}
	}

	@Override
	public ListIterator<E> listIterator() {
		synchronized ( this._sync ) {
			return this._simpleGet().listIterator();
		}
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		synchronized ( this._sync ) {
			return this._simpleGet().listIterator(index);
		}
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		synchronized ( this._sync ) {
			return this._simpleGet().subList(fromIndex, toIndex);
		}
	}
	
	@Override
	protected List<E> _unmodifiableCollection(List<E> c) {
		return Collections.unmodifiableList(c);
	}
	
}
