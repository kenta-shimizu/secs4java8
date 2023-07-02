package com.shimizukenta.secs.local.property.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.shimizukenta.secs.local.property.BooleanObservable;
import com.shimizukenta.secs.local.property.ChangeListener;

/**
 * 
 * @author kenta-shimizu
 *
 */
public class BooleanUtils {
	
	private BooleanUtils() {
		/* Nothing */
	}
	
	private static AbstractUnmodifiableBooleanProperty buildUnmodifiableBoolean(boolean f) {
		return new AbstractUnmodifiableBooleanProperty(f) {
			
			private static final long serialVersionUID = 715033907568380106L;
		};
	}
	
	private static final AbstractUnmodifiableBooleanProperty UNMOD_FALSE = buildUnmodifiableBoolean(false);
	private static final AbstractUnmodifiableBooleanProperty UNMOD_TRUE = buildUnmodifiableBoolean(true);
	
	/**
	 * Returns Unmodifiable False Property instance.
	 * 
	 * @return Unmodifiable False Property instance
	 */
	public static AbstractUnmodifiableBooleanProperty getUnmodifiableFalse() {
		return UNMOD_FALSE;
	}
	
	/**
	 * Returns Unmodifiable True Property instance.
	 * 
	 * @return Unmodifiable True Property instance
	 */
	public static AbstractUnmodifiableBooleanProperty getUnmodifiableTrue() {
		return UNMOD_TRUE;
	}
	
	/**
	 * Returns Unmodifiable Boolean Property instance.
	 * 
	 * @param f is boolean
	 * @return Unmodifiable Boolean Property instance
	 */
	public static AbstractUnmodifiableBooleanProperty getUnmodifiableBoolean(boolean f) {
		if ( f ) {
			return getUnmodifiableTrue();
		} else {
			return getUnmodifiableFalse();
		}
	}
	
	/**
	 * Returns LogicalCompution of AND({@code &&}) operation.
	 * 
	 * @param observers is collection of BooleanObservers
	 * @return LogicalCompution of AND({@code &&}) operation
	 */
	public static AbstractLogicalCompution and(Collection<? extends BooleanObservable> observers) {
		return new InnerCollection(observers, c -> c.stream().allMatch(Boolean::booleanValue));
	}
	
	/**
	 * Returns LogicalCompution of OR({@code ||}) operation.
	 * 
	 * @param observers is collection of BooleanObservers
	 * @return LogicalCompution of OR({@code ||}) operation
	 */
	public static AbstractLogicalCompution or(Collection<? extends BooleanObservable> observers) {
		return new InnerCollection(observers, c -> c.stream().anyMatch(Boolean::booleanValue));
	}
	
	/**
	 * Returns LogicalCompution of NOT({@code !}) operation.
	 * 
	 * @param observer BooleanObserver
	 * @return LogicalCompution of NOT({@code !}) operation
	 */
	public static AbstractLogicalCompution not(BooleanObservable observer) {
		return new InnerMono(observer, f -> ! f.booleanValue());
	}
	
	/**
	 * Returns LogicalCompution of XOR({@code ^}) operation.
	 * @param a is BooleanObserver
	 * @param b is BooleanObserver
	 * @return LogicalCompution of XOR({@code ^}) operation
	 */
	public static AbstractLogicalCompution xor(BooleanObservable a, BooleanObservable b) {
		return new InnerBi(a, b, (l, r) -> l.booleanValue() ^ r.booleanValue());
	}
	
	/**
	 * Returns LogicalCompution of NAND(NOT AND) operation.
	 * 
	 * @param observers is collection of BooleanObservers
	 * @return LogicalCompution of NAMD({NOT AND) operation
	 */
	public static AbstractLogicalCompution nand(Collection<? extends BooleanObservable> observers) {
		return not(and(observers));
	}
	
	/**
	 * Returns LogicalCompution of NOR(NOT OR) operation.
	 * 
	 * @param observers is collection of BooleanObservers
	 * @return LogicalCompution of NOR({NOT OR) operation
	 */
	public static AbstractLogicalCompution nor(Collection<? extends BooleanObservable> observers) {
		return not(or(observers));
	}
	
	/**
	 * Inner Mono
	 * 
	 * @author kenta-shimizu
	 *
	 */
	private static class InnerMono extends AbstractLogicalCompution {
		
		private static final long serialVersionUID = 4222943579843798685L;
		
		public InnerMono(BooleanObservable observer, Predicate<Boolean> compute) {
			super();
			
			observer.addChangeListener(f -> {
				synchronized ( this._sync ) {
					this._syncSetAndNotifyChanged(Boolean.valueOf(compute.test(f)));
				}
			});
		}
	}
	
	/**
	 * Inner Bi
	 * 
	 * @author kenta-shimizu
	 *
	 */
	private static class InnerBi extends AbstractLogicalCompution {
		
		private static final long serialVersionUID = -2708885090031897205L;
		
		private Boolean ll;
		private Boolean rr;
		
		private InnerBi(
				BooleanObservable left,
				BooleanObservable right,
				BiPredicate<Boolean, Boolean> compute) {
			
			super();
			
			this.ll = Boolean.FALSE;
			this.rr = Boolean.FALSE;
			
			left.addChangeListener(f -> {
				synchronized ( this._sync ) {
					this.ll = f;
					this._syncSetAndNotifyChanged(compute.test(this.ll, this.rr));
				}
			});
			
			right.addChangeListener(f -> {
				synchronized ( this._sync ) {
					this.rr = f;
					this._syncSetAndNotifyChanged(compute.test(this.ll, this.rr));
				}
			});
		}
	}
	
	/**
	 * Inner Collection
	 * 
	 * @author kenta-shimizu
	 *
	 */
	private static class InnerCollection extends AbstractLogicalCompution {
		
		private static final long serialVersionUID = -1549973920268106365L;
		
		private final Collection<Inner> inners = new ArrayList<>();
		private final Predicate<? super Collection<Boolean>> compute;
		
		public InnerCollection(
				Collection<? extends BooleanObservable> observers,
				Predicate<? super Collection<Boolean>> compute) {
			
			super();
			
			this.compute = compute;
			
			observers.forEach(o -> {
				Inner i = new Inner();
				this.inners.add(i);
				o.addChangeListener(i);
			});
		}
		
		/**
		 * Inner
		 * 
		 * @author kenta-shimizu
		 *
		 */
		private class Inner implements ChangeListener<Boolean> {
			
			boolean last;
			
			public Inner() {
				this.last = false;
			}

			@Override
			public void changed(Boolean v) {
				synchronized ( InnerCollection.this._sync ) {
					this.last = v;
					InnerCollection.this._syncSetAndNotifyChanged(
							InnerCollection.this.compute.test(
									InnerCollection.this.inners.stream()
									.map(i -> i.last)
									.collect(Collectors.toList())));
				}
			}
		}
	}
	
}
