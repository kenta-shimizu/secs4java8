package com.shimizukenta.secs;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractCollectionProperty<T>
		implements CollectionProperty<T>, Serializable {
	
	private static final long serialVersionUID = -4935312347859335201L;
	
	private final Object sync = new Object();
	
	private Collection<T> vv;
	
	public AbstractCollectionProperty(Collection<T> initial) {
		this.vv = Objects.requireNonNull(initial);
	}

	@Override
	public Collection<T> get() {
		synchronized ( sync ) {
			return vv.stream().map(v -> (T)v).collect(Collectors.toList());
		}
	}
	
	@Override
	public void set(Collection<? extends T> v) {
		throw new UnsupportedOperationException("Unsupport AbstractCollectionProperty#set");
	}
	
	private final Collection<PropertyChangeListener<? super Collection<T>>> listeners = new CopyOnWriteArrayList<>();
	
	@Override
	public boolean addChangeListener(PropertyChangeListener<? super Collection<T>> listener) {
		synchronized ( sync ) {
			boolean f = listeners.add(listener);
			if ( f ) {
				listener.changed(get());
			}
			return f;
		}
	}

	@Override
	public boolean removeChangeListener(PropertyChangeListener<? super Collection<T>> listener) {
		return listeners.remove(listener);
	}
	
	protected void notifyChanged() {
		synchronized ( sync ) {
			listeners.forEach(l -> {
				l.changed(get());
			});
			sync.notifyAll();
		}
	}
	
	private void notifyChanged(boolean f) {
		if ( f ) {
			notifyChanged();
		}
	}
	
	@Override
	public void waitUntil(Collection<T> v) throws InterruptedException {
		synchronized ( sync ) {
			for ( ;; ) {
				if ( Objects.equals(get(), v)) {
					return;
				}
				sync.wait();
			}
		}
	}
	
	@Override
	public void waitUntil(Collection<T> v, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		this.waitWithTimeout(() -> {
			this.waitUntil(v);
		}, timeout, unit);
	}
	
	@Override
	public void waitUntil(Collection<T> v, ReadOnlyTimeProperty tp) throws InterruptedException, TimeoutException {
		this.waitUntil(v, tp.getMilliSeconds(), TimeUnit.MILLISECONDS);
	}
	
	@Override
	public void waitUntilNot(Collection<T> v) throws InterruptedException {
		synchronized ( sync ) {
			for ( ;; ) {
				if ( ! Objects.equals(get(), v) ) {
					return;
				}
				sync.wait();
			}
		}
	}
	
	@Override
	public void waitUntilNot(Collection<T> v, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		this.waitWithTimeout(() -> {
			this.waitUntilNot(v);
		}, timeout, unit);
	}
	
	@Override
	public void waitUntilNot(Collection<T> v, ReadOnlyTimeProperty tp) throws InterruptedException, TimeoutException {
		this.waitUntilNot(v, tp.getMilliSeconds(), TimeUnit.MILLISECONDS);
	}
	
	private void waitWithTimeout(InterruptableRunnable r, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		
		final Future<T> f = new FutureTask<>(() -> {
			try {
				r.run();
			}
			catch ( InterruptedException ignore ) {
			}
		}, null);
		
		try {
			f.get(timeout, unit);
		}
		catch ( InterruptedException e ) {
			f.cancel(true);
			throw e;
		}
		catch ( TimeoutException e ) {
			f.cancel(true);
			throw e;
		}
		catch ( ExecutionException e ) {
			Throwable t = e.getCause();
			if ( t instanceof RuntimeException ) {
				throw (RuntimeException)t;
			} else {
				throw new RuntimeException(t);
			}
		}
	}
	
	@Override
	public int size() {
		synchronized ( sync ) {
			return get().size();
		}
	}

	@Override
	public boolean isEmpty() {
		synchronized ( sync ) {
			return get().isEmpty();
		}
	}

	@Override
	public boolean contains(Object o) {
		synchronized ( sync ) {
			return get().contains(o);
		}
	}
	
	@Override
	public boolean containsAll(Collection<?> c) {
		synchronized ( sync ) {
			return get().containsAll(c);
		}
	}
	
	@Override
	public Iterator<T> iterator() {
		synchronized ( sync ) {
			return get().stream().map(x -> (T)x).iterator();
		}
	}

	@Override
	public Object[] toArray() {
		synchronized ( sync ) {
			return get().toArray();
		}
	}

	@Override
	public <U> U[] toArray(U[] a) {
		synchronized ( sync ) {
			return get().toArray(a);
		}
	}
	
	@Override
	public boolean add(T v) {
		synchronized ( sync ) {
			boolean f = vv.add(v);
			notifyChanged(f);
			return f;
		}
	}

	@Override
	public boolean remove(Object o) {
		synchronized ( sync ) {
			boolean f = vv.remove(o);
			notifyChanged(f);
			return f;
		}
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		synchronized ( sync ) {
			boolean f = vv.addAll(c);
			notifyChanged(f);
			return f;
		}
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		synchronized ( sync ) {
			boolean f = vv.removeAll(c);
			notifyChanged(f);
			return f;
		}
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		synchronized ( sync ) {
			boolean f = vv.retainAll(c);
			notifyChanged(f);
			return f;
		}
	}
	
	@Override
	public void clear() {
		synchronized ( sync ) {
			if ( ! isEmpty() ) {
				vv.clear();
				notifyChanged();
			}
		}
	}
	
	@Override
	public boolean removeIf(Predicate<? super T> filter) {
		synchronized ( sync ) {
			boolean f = vv.removeIf(filter);
			notifyChanged(f);
			return f;
		}
	}
	
	
	
	@Override
	public Spliterator<T> spliterator() {
		synchronized ( sync ) {
			return get().stream().map(x -> (T)x).spliterator();
		}
	}
	
	@Override
	public Stream<T> stream() {
		synchronized ( sync ) {
			return get().stream().map(x -> (T)x);
		}
	}
	
	@Override
	public Stream<T> parallelStream() {
		synchronized ( sync ) {
			return get().parallelStream().map(x -> (T)x);
		}
	}
	
	@Override
	public void forEach(Consumer<? super T> action) {
		synchronized ( sync ) {
			get().forEach(action);
		}
	}

	
}
