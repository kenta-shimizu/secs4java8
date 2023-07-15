package com.shimizukenta.secs.local.property;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.shimizukenta.secs.local.property.impl.MapUtils;

/**
 * Map value Observer.
 * 
 * @author kenta-shimizu
 *
 * @param <K> Key Type
 * @param <V> Value Type
 * @see Observable
 * @see Map
 * 
 */
public interface MapObservable<K, V> extends Observable<Map<K, V>> {
	
	/**
	 * Returns BooleanCompution of Map#containsKey(Object) is true.
	 * 
	 * @param key key whose presence in this map is to be tested
	 * @return BooleanCompution of Map#containsKey(Object) is true
	 * @see Map#containsKey(Object)
	 * @see BooleanCompution
	 */
	default public BooleanCompution computeContainsKey(Object key) {
		return MapUtils.computeContainsKey(this, key);
	}
	
	/**
	 * Returns BooleanCompution of Map#containsKey(Object) is false
	 * 
	 * @param key key whose presence in this map is to be tested
	 * @return BooleanCompution of Map#containsKey(Object) is false
	 * @see Map#containsKey(Object)
	 * @see BooleanCompution
	 */
	default public BooleanCompution computeNotContainsKey(Object key) {
		return MapUtils.computeNotContainsKey(this, key);
	}
	
	/**
	 * Returns BooleanCompution of Map#isEmpty() is true.
	 * 
	 * @return BooleanCompution of Map#isEmpty() is true
	 * @see Map#isEmpty()
	 * @see BooleanCompution
	 */
	default public BooleanCompution computeIsEmpty() {
		return MapUtils.computeIsEmpty(this);
	}
	
	/**
	 * Returns BooleanCompution of Map#isEmpty() is false.
	 * 
	 * @return BooleanCompution of Map#isEmpty() is false
	 * @see Map#isEmpty()
	 * @see BooleanCompution
	 */
	default public BooleanCompution computeIsNotEmpty() {
		return MapUtils.computeIsNotEmpty(this);
	}
	
	/**
	 * Returns SetCompution of Map#keySet().
	 * 
	 * @return SetCompution of Map#keySet()
	 * @see Map#keySet()
	 * @see SetCompution
	 */
	default public SetCompution<K> computeKeySet() {
		return MapUtils.computeKeySet(this);
	}
	
	/**
	 * Returns IntegerCompution of Map#size().
	 * 
	 * @return IntegerCompution of Map#size()
	 * @see Map#size()
	 * @see IntegerCompution
	 */
	default public IntegerCompution computeSize() {
		return MapUtils.computeSize(this);
	}
	
	/**
	 * Waiting until Map#containsKey(Object) is true, and return value.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already Map#containsKey(Object) is true, return value immediately.<br />
	 * </p>
	 * 
	 * @param key key whose presence in this map is to be tested
	 * @return Map#get(Object)
	 * @throws InterruptedException if interrupted while waiting
	 * @see Map#containsKey(Object)
	 */
	default public V waitUntilContainsKeyAndGet(Object key) throws InterruptedException {
		return MapUtils.waitUntilContainsKeyAndGet(this, key);
	}
	
	/**
	 * Waiting until Map#containsKey(Object) is true, and return value.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already Map#containsKey(Object) is true, return value immediately.<br />
	 * </p>
	 * 
	 * @param key key whose presence in this map is to be tested
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @return Map#get(Object)
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see Map#containsKey(Object)
	 */
	default public V waitUntilContainsKeyAndGet(Object key, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		return MapUtils.waitUntilContainsKeyAndGet(this, key, timeout, unit);
	}
	
	/**
	 * Waiting until Map#containsKey(Object) is true, and return value.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already Map#containsKey(Object) is true, return value immediately.<br />
	 * </p>
	 * 
	 * @param key key whose presence in this map is to be tested
	 * @param p the TimeoutProperty
	 * @return Map#get(Object)
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see Map#containsKey(Object)
	 */
	default public V waitUntilContainsKeyAndGet(Object key, TimeoutGettable p) throws InterruptedException, TimeoutException {
		return MapUtils.waitUntilContainsKeyAndGet(this, key, p);
	}
	
	/**
	 * Waiting until Map#containsKey(Object) is false.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already Map#containsKey(Object) is false, pass through immediately.<br />
	 * </p>
	 * 
	 * @param key key whose presence in this map is to be tested
	 * @throws InterruptedException if interrupted while waiting
	 * @see Map#containsKey(Object)
	 */
	default public void waitUntilNotContainsKey(Object key) throws InterruptedException {
		MapUtils.waitUntilNotContainsKey(this, key);
	}
	
	/**
	 * Waiting until Map#containsKey(Object) is false.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already Map#containsKey(Object) is false, pass through immediately.<br />
	 * </p>
	 * 
	 * @param key key whose presence in this map is to be tested
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see Map#containsKey(Object)
	 */
	default public void waitUntilNotContainsKey(Object key, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		MapUtils.waitUntilNotContainsKey(this, key, timeout, unit);
	}
	
	/**
	 * Waiting until Map#containsKey(Object) is false.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already Map#containsKey(Object) is false, pass through immediately.<br />
	 * </p>
	 * 
	 * @param key key whose presence in this map is to be tested
	 * @param p the TimeoutProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see Map#containsKey(Object)
	 */
	default public void waitUntilNotContainsKey(Object key, TimeoutGettable p) throws InterruptedException, TimeoutException {
		MapUtils.waitUntilNotContainsKey(this, key, p);
	}
	
	/**
	 * Waiting until Map#isEmpty() is true.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already empty, pass through immediately.<br />
	 * </p>
	 * 
	 * @throws InterruptedException if interrupted while waiting
	 * @see Map#isEmpty()
	 */
	default public void waitUntilIsEmpty() throws InterruptedException {
		MapUtils.waitUntilIsEmpty(this);
	}
	
	/**
	 * Waiting until Map#isEmpty() is true.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already empty, pass through immediately.<br />
	 * </p>
	 * 
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see Map#isEmpty()
	 */
	default public void waitUntilIsEmpty(long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		MapUtils.waitUntilIsEmpty(this, timeout, unit);
	}
	
	/**
	 * Waiting until Map#isEmpty() is true.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already empty, pass through immediately.<br />
	 * </p>
	 * 
	 * @param p the TimeoutProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see Map#isEmpty()
	 */
	default public void waitUntilIsEmpty(TimeoutGettable p) throws InterruptedException, TimeoutException {
		MapUtils.waitUntilIsEmpty(this, p);
	}
	
	/**
	 * Waiting until Map#isEmpty() is false.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already <strong>NOT</strong> empty, pass through immediately.<br />
	 * </p>
	 * 
	 * @throws InterruptedException if interrupted while waiting
	 * @see Map#isEmpty()
	 */
	default public void waitUntilIsNotEmpty() throws InterruptedException {
		MapUtils.waitUntilIsNotEmpty(this);
	}
	
	/**
	 * Waiting until Map#isEmpty() is false.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already <strong>NOT</strong> empty, pass through immediately.<br />
	 * </p>
	 * 
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see Map#isEmpty()
	 */
	default public void waitUntilIsNotEmpty(long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		MapUtils.waitUntilIsNotEmpty(this, timeout, unit);
	}
	
	/**
	 * Waiting until Map#isEmpty() is false.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already <strong>NOT</strong> empty, pass through immediately.<br />
	 * </p>
	 * 
	 * @param p the TimeoutProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see Map#isEmpty()
	 */
	default public void waitUntilIsNotEmpty(TimeoutGettable p) throws InterruptedException, TimeoutException {
		MapUtils.waitUntilIsNotEmpty(this, p);
	}
	
}
