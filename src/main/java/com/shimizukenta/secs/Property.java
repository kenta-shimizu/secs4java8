package com.shimizukenta.secs;

public interface Property<T> {
	public void set(T v);
	public T get();
	public boolean addChangeListener(PropertyChangeListener<? super T> l);
	public boolean removeChangeListener(PropertyChangeListener<? super T> l);
	public void waitUntil(T v) throws InterruptedException;
	public void waitUntilNot(T v) throws InterruptedException;
}
