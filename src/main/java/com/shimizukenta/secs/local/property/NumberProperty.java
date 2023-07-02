package com.shimizukenta.secs.local.property;

/**
 * Number value Property, includes Getter, Setter, Observer.
 * 
 * @author kenta-shimizu
 * @see Number
 * @see Property
 * @see NumberGettable
 * @see NumberSettable
 * @see NumberObservable
 * 
 */
public interface NumberProperty<T extends Number> extends Property<T>, NumberGettable<T>, NumberSettable<T>, NumberObservable<T> {
	
}
