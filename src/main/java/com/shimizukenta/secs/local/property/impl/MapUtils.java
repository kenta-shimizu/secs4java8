package com.shimizukenta.secs.local.property.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Predicate;

import com.shimizukenta.secs.local.property.ChangeListener;
import com.shimizukenta.secs.local.property.Observable;
import com.shimizukenta.secs.local.property.TimeoutGettable;

/**
 * 
 * @author kenta-shimizu
 *
 */
public final class MapUtils {

	private MapUtils() {
		/* Nothing */
	}
	
	/**
	 * Inner Map.
	 * 
	 * @author kenta-shimizu
	 *
	 * @param <K> Key
	 * @param <V> Value
	 */
	private static class InnerMap<K, V> extends AbstractPredicateCompution<Map<K, V>> {
		
		private static final long serialVersionUID = 8252544696751954294L;
		
		public InnerMap(
				Predicate<? super Map<K, V>> compute) {

			super(compute, new HashMap<K, V>());
		}
		
		@Override
		protected void changedValue(Map<K, V> v) {
			synchronized ( this._sync ) {
				final Map<K, V> x = this.getLastValue();
				x.clear();
				x.putAll(v);
				this._syncSetAndNotifyChanged(Boolean.valueOf(this._compute.test(x)));
			}
		}
		
		public V waitUntilContainsKeyAndGet(Object key) throws InterruptedException {
			synchronized ( this._sync ) {
				return this.waitUntilTrueAndGet().get(key);
			}
		}
		
		public V waitUntilContainsKeyAndGet(
				Object key,
				long timeout,
				TimeUnit unit) throws InterruptedException, TimeoutException {
			
			synchronized ( this._sync ) {
				return this.waitUntilTrueAndGet(timeout, unit).get(key);
			}
		}
		
		public V waitUntilContainsKeyAndGet(
				Object key,
				TimeoutGettable p) throws InterruptedException, TimeoutException {
			
			synchronized ( this._sync ) {
				return this.waitUntilTrueAndGet(p).get(key);
			}
		}
	}
	
	private static <K, V> InnerMap<K, V> buildInnerMap(
			Observable<Map<K, V>> observer,
			Predicate<? super Map<K, V>> compute) {
		
		final InnerMap<K, V> i = new InnerMap<>(compute);
		
		i.bind(observer);
		
		return i;
	}
	
	private static <K, V> InnerMap<K, V> buildIsContainsKey(
			Observable<Map<K, V>> observer,
			Object key) {
		
		return buildInnerMap(observer, m -> m.containsKey(key));
	}
	
	/**
	 * Returns ContainsKey Compution.
	 * 
	 * @param <K> Key
	 * @param <V> Value
	 * @param observer MapObserver
	 * @param key key whose presence in this map is to be tested
	 * @return ContainsKey Compution
	 * @see Map#containsKey(Object)
	 */
	public static <K, V> AbstractPredicateCompution<Map<K, V>> computeContainsKey(
			Observable<Map<K, V>> observer,
			Object key) {
		
		return buildIsContainsKey(observer, key);
	}
	
	/**
	 * Returns NOT ContainsKey Compution.
	 * 
	 * @param <K> Key
	 * @param <V> Value
	 * @param observer MapObserver
	 * @param key key whose presence in this map is to be tested
	 * @return NOT ContainsKey Compution
	 * @see Map#containsKey(Object)
	 */
	public static <K, V> AbstractPredicateCompution<Map<K, V>> computeNotContainsKey(
			Observable<Map<K, V>> observer,
			Object key) {
		
		return buildInnerMap(observer, m -> ! m.containsKey(key));
	}
	
	/**
	 * IsEmpty Compution.
	 * 
	 * @param <K> Key
	 * @param <V> Value
	 * @param observer MapObserver
	 * @return IsEmpty Compution
	 * @see Map#isEmpty()
	 */
	public static <K, V> AbstractPredicateCompution<Map<K, V>> computeIsEmpty(
			Observable<Map<K, V>> observer) {
		
		return buildInnerMap(observer, Map::isEmpty);
	}
	
	/**
	 * NOT IsEmpty Compution.
	 * 
	 * @param <K> Key
	 * @param <V> Value
	 * @param observer MapObserver
	 * @return NOT IsEmpty Compution
	 * @see Map#isEmpty()
	 */
	public static <K, V> AbstractPredicateCompution<Map<K, V>> computeIsNotEmpty(
			Observable<Map<K, V>> observer) {
		
		return buildInnerMap(observer, m -> ! m.isEmpty());
	}
	
	private static <K, V> Map<K, V> waitUntilPredicate(
			AbstractPredicateCompution<Map<K, V>> i,
			Observable<Map<K, V>> observer) throws InterruptedException {
		
		try {
			return i.waitUntilTrueAndGet();
		}
		finally {
			i.unbind(observer);
		}
	}
	
	private static <K, V> Map<K, V> waitUntilPredicate(
			AbstractPredicateCompution<Map<K, V>> i,
			Observable<Map<K, V>> observer,
			long timeout,
			TimeUnit unit) throws InterruptedException, TimeoutException {
		
		try {
			return i.waitUntilTrueAndGet(timeout, unit);
		}
		finally {
			i.unbind(observer);
		}
	}
	
	private static <K, V> Map<K, V> waitUntilPredicate(
			AbstractPredicateCompution<Map<K, V>> i,
			Observable<Map<K, V>> observer,
			TimeoutGettable p) throws InterruptedException, TimeoutException {
		
		try {
			return i.waitUntilTrueAndGet(p);
		}
		finally {
			i.unbind(observer);
		}
	}
	
	/**
	 * Waiting until containsKey, and return value.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already containsKey, return value immediately.<br />
	 * </p>
	 * 
	 * @param <K> Key
	 * @param <V> Value
	 * @param observer MapObserver
	 * @param key  key whose presence in this map is to be tested
	 * @return value
	 * @throws InterruptedException if interrupted while waiting
	 * @see Map#containsKey(Object)
	 */
	public static <K, V> V waitUntilContainsKeyAndGet(
			Observable<Map<K, V>> observer,
			Object key) throws InterruptedException {
		
		final InnerMap<K, V> i = buildIsContainsKey(observer, key);
		try {
			return i.waitUntilContainsKeyAndGet(key);
		}
		finally {
			i.unbind(observer);
		}
	}
	
	/**
	 * Waiting until containsKey, and return value.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already containsKey, return value immediately.<br />
	 * </p>
	 * 
	 * @param <K> Key
	 * @param <V> Value
	 * @param observer MapObserver
	 * @param key  key whose presence in this map is to be tested
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @return value
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see Map#containsKey(Object)
	 */
	public static <K, V> V waitUntilContainsKeyAndGet(
			Observable<Map<K, V>> observer,
			Object key,
			long timeout,
			TimeUnit unit) throws InterruptedException, TimeoutException {
		
		final InnerMap<K, V> i = buildIsContainsKey(observer, key);
		try {
			return i.waitUntilContainsKeyAndGet(key, timeout, unit);
		}
		finally {
			i.unbind(observer);
		}
	}
	
	/**
	 * Waiting until containsKey, and return value.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already containsKey, return value immediately.<br />
	 * </p>
	 * 
	 * @param <K> Key
	 * @param <V> Value
	 * @param observer MapObserver
	 * @param key  key whose presence in this map is to be tested
	 * @param p is TimeoutProperty
	 * @return value
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see Map#containsKey(Object)
	 */
	public static <K, V> V waitUntilContainsKeyAndGet(
			Observable<Map<K, V>> observer,
			Object key,
			TimeoutGettable p) throws InterruptedException, TimeoutException {
		
		final InnerMap<K, V> i = buildIsContainsKey(observer, key);
		try {
			return i.waitUntilContainsKeyAndGet(key, p);
		}
		finally {
			i.unbind(observer);
		}
	}
	
	/**
	 * Waiting until NOT containsKey.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already <strong>NOT</strong> containsKey, pass through immediately.<br />
	 * </p>
	 * 
	 * @param <K> Key
	 * @param <V> Value
	 * @param observer MapObserver
	 * @param key key whose presence in this map is to be tested
	 * @throws InterruptedException if interrupted while waiting
	 */
	public static <K, V> void waitUntilNotContainsKey(
			Observable<Map<K, V>> observer,
			Object key) throws InterruptedException {
		
		waitUntilPredicate(computeNotContainsKey(observer, key), observer);
	}
	
	/**
	 * Waiting until NOT containsKey.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already <strong>NOT</strong> containsKey, pass through immediately.<br />
	 * </p>
	 * 
	 * @param <K> Key
	 * @param <V> Value
	 * @param observer MapObserver
	 * @param key  key whose presence in this map is to be tested
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see Map#containsKey(Object)
	 */
	public static <K, V> void waitUntilNotContainsKey(
			Observable<Map<K, V>> observer,
			Object key,
			long timeout,
			TimeUnit unit) throws InterruptedException, TimeoutException {
		
		waitUntilPredicate(computeNotContainsKey(observer, key), observer, timeout, unit);
	}
	
	/**
	 * Waiting until NOT containsKey.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already <strong>NOT</strong> containsKey, pass through immediately.<br />
	 * </p>
	 * 
	 * @param <K> Key
	 * @param <V> Value
	 * @param observer MapObserver
	 * @param key  key whose presence in this map is to be tested
	 * @param p is TimeoutProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see Map#containsKey(Object)
	 */
	public static <K, V> void waitUntilNotContainsKey(
			Observable<Map<K, V>> observer,
			Object key,
			TimeoutGettable p) throws InterruptedException, TimeoutException {
		
		waitUntilPredicate(computeNotContainsKey(observer, key), observer, p);
	}
	
	/**
	 * Waiting until isEmpty.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already isEmpty, pass through immediately.<br />
	 * </p>
	 * 
	 * @param <K> Key
	 * @param <V> Value
	 * @param observer MapObserver
	 * @throws InterruptedException if interrupted while waiting
	 * @see Map#isEmpty()
	 */
	public static <K, V> void waitUntilIsEmpty(
			Observable<Map<K, V>> observer) throws InterruptedException {
		
		waitUntilPredicate(computeIsEmpty(observer), observer);
	}
	
	/**
	 * Waiting until isEmpty.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already isEmpty, pass through immediately.<br />
	 * </p>
	 * 
	 * @param <K> Key
	 * @param <V> Value
	 * @param observer MapObserver
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see Map#isEmpty()
	 */
	public static <K, V> void waitUntilIsEmpty(
			Observable<Map<K, V>> observer,
			long timeout,
			TimeUnit unit) throws InterruptedException, TimeoutException {
		
		waitUntilPredicate(computeIsEmpty(observer), observer, timeout, unit);
	}
	
	/**
	 * Waiting until isEmpty.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already isEmpty, pass through immediately.<br />
	 * </p>
	 * 
	 * @param <K> Key
	 * @param <V> Value
	 * @param observer MapObserver
	 * @param p is TimeoutProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see Map#isEmpty()
	 */
	public static <K, V> void waitUntilIsEmpty(
			Observable<Map<K, V>> observer,
			TimeoutGettable p) throws InterruptedException, TimeoutException {
		
		waitUntilPredicate(computeIsEmpty(observer), observer, p);
	}
	
	/**
	 * Waiting until NOT isEmpty.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already <strong>NOT</strong> isEmpty, pass through immediately.<br />
	 * </p>
	 * 
	 * @param <K> Key
	 * @param <V> Value
	 * @param observer MapObserver
	 * @throws InterruptedException if interrupted while waiting
	 * @see Map#isEmpty()
	 */
	public static <K, V> void waitUntilIsNotEmpty(
			Observable<Map<K, V>> observer) throws InterruptedException {
		
		waitUntilPredicate(computeIsNotEmpty(observer), observer);
	}
	
	/**
	 * Waiting until NOT isEmpty.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already <strong>NOT</strong> isEmpty, pass through immediately.<br />
	 * </p>
	 * 
	 * @param <K> Key
	 * @param <V> Value
	 * @param observer MapObserver
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see Map#isEmpty()
	 */
	public static <K, V> void waitUntilIsNotEmpty(
			Observable<Map<K, V>> observer,
			long timeout,
			TimeUnit unit) throws InterruptedException, TimeoutException {
		
		waitUntilPredicate(computeIsNotEmpty(observer), observer, timeout, unit);
	}
	
	/**
	 * Waiting until NOT isEmpty.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already <strong>NOT</strong> isEmpty, pass through immediately.<br />
	 * </p>
	 * 
	 * @param <K> Key
	 * @param <V> Value
	 * @param observer MapObserver
	 * @param p is TimeoutProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see Map#isEmpty()
	 */
	public static <K, V> void waitUntilIsNotEmpty(
			Observable<Map<K, V>> observer,
			TimeoutGettable p) throws InterruptedException, TimeoutException {
		
		waitUntilPredicate(computeIsNotEmpty(observer), observer, p);
	}
	
	/**
	 * Inner KeySet.
	 * 
	 * @author kenta-shimizu
	 *
	 * @param <K> Key
	 * @param <V> Value
	 */
	private static class InnerKeySet<K, V> extends AbstractSetCompution<K> {
		
		private static final long serialVersionUID = -1028393058729307902L;
		
		public InnerKeySet() {
			super(new HashSet<>());
		}
		
		private void changedMap(Map<K, V> newMap) {
			synchronized ( this._sync ) {
				this._syncSetAndNotifyChanged(newMap.keySet());
			}
		}
		
		private final ChangeListener<Map<K, V>> bindLstnr = this::changedMap;
		
		public boolean bindMap(Observable<Map<K, V>> observer) {
			return observer.addChangeListener(this.bindLstnr);
		}
	}
	
	/**
	 * KeySet Compution.
	 * 
	 * @param <K> Key
	 * @param <V> Value
	 * @param observer MapCompution
	 * @return KeySet Compution
	 * @see Map#keySet()
	 */
	public static <K, V> AbstractSetCompution<K> computeKeySet(Observable<Map<K, V>> observer) {
		final InnerKeySet<K, V> i = new InnerKeySet<>();
		i.bindMap(observer);
		return i;
	}
	
	/**
	 * Inner Size.
	 * 
	 * @author kenta-shimizu
	 *
	 * @param <K> Key
	 * @param <V> Value
	 */
	private static class InnerSize<K, V> extends AbstractIntegerCompution {
		
		private static final long serialVersionUID = -7668752376760047988L;
		
		public InnerSize() {
			super();
		}
		
		private void changedMap(Map<K, V> map) {
			synchronized ( this._sync ) {
				this._syncSetAndNotifyChanged(Integer.valueOf(map.size()));
			}
		}
		
		private final ChangeListener<Map<K, V>> bindLstnr = this::changedMap;
		
		public boolean bindMap(Observable<? extends Map<K, V>> observer) {
			return observer.addChangeListener(this.bindLstnr);
		}
	}
	
	/**
	 * Size Integer Compution.
	 * 
	 * @param <K> Key
	 * @param <V> Value
	 * @param observer MapObserver
	 * @return Size Integer Compution
	 * @see Map#size()
	 */
	public static <K, V> AbstractIntegerCompution computeSize(Observable<? extends Map<K, V>> observer) {
		final InnerSize<K, V> i = new InnerSize<>();
		i.bindMap(observer);
		return i;
	}
	
}
