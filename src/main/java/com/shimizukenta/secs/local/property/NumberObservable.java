/**
 * 
 */
package com.shimizukenta.secs.local.property;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.shimizukenta.secs.local.property.impl.NumberUtils;

/**
 * Number value Observer.
 * 
 * @author kenta-shimizu
 * @see Number
 * @see Observable
 *
 */
public interface NumberObservable<T extends Number> extends Observable<T> {
	
	/**
	 * Returns true if Number is instance of Integer, otherwise false.
	 * 
	 * @return true if Number is instance of Integer, otherwise false.
	 */
	default public boolean isInteger() {
		return false;
	}
	
	/**
	 * Returns true if Number is instance of Long, otherwise false.
	 * 
	 * @return true if Number is instance of Long, otherwise false.
	 */
	default public boolean isLong() {
		return false;
	}
	
	/**
	 * Returns true if Number is instance of Float, otherwise false.
	 * 
	 * @return true if Number is instance of Float, otherwise false.
	 */
	default public boolean isFloat() {
		return false;
	}
	
	/**
	 * Returns true if Number is instance of Double, otherwise false.
	 * 
	 * @return true if Number is instance of Double, otherwise false.
	 */
	default public boolean isDouble() {
		return false;
	}
	
	
	/* Casts */
	
	/**
	 * Returns IntegerCompution coverted instance.
	 * 
	 * @return IntegerComution converted instance.
	 * @see IntegerCompution
	 */
	default public IntegerCompution toInteger() {
		return NumberUtils.toInteger(this);
	}
	
	/**
	 * Returns LongComputionn converted instance.
	 * 
	 * @return LongCompution converted instance.
	 * @see LongCompution
	 */
	default public LongCompution toLong() {
		return NumberUtils.toLong(this);
	}
	
	/**
	 * Returns FloatCompution converted instance.
	 * 
	 * @return FloatCompution converted instance.
	 * @see FloatCompution
	 */
	default public FloatCompution toFloat() {
		return NumberUtils.toFloat(this);
	}
	
	/**
	 * Returns DoubleCompution converted instance.
	 * 
	 * @return DoubleCompution comerted instance.
	 * @see DoubleCompution
	 */
	default public DoubleCompution toDouble() {
		return NumberUtils.toDouble(this);
	}
	
	/* Number Compution Bases */
	
	/**
	 * Returns NumberCompution of {@code this.value + observable.value}.
	 * 
	 * @param observer the NumberObserver
	 * @return NumberCompution of {@code this.value + observable.value}.
	 * @see NumberCompution#sum(NumberObservable, NumberObservable)
	 */
	default public NumberCompution add(NumberObservable<? extends Number> observer) {
		return NumberCompution.sum(this, observer);
	}
	
	/**
	 * NumberCompution of {@code this.value * observable.value}.
	 * 
	 * @param observer the NumberObserver
	 * @return NumberCompution of {@code this.value * observable.value}.
	 * @see NumberCompution#multiply(NumberObservable, NumberObservable)
	 */
	default public NumberCompution multiply(NumberObservable<? extends Number> observer) {
		return NumberCompution.multiply(this, observer);
	}
	
	/**
	 * Returns NumberCompution of {@code this.value - observable.value}.
	 * 
	 * @param observer the NumberObserver
	 * @return NumberCompution of {@code this.value - observable.value}.
	 * @see NumberCompution#subtract(NumberObservable, NumberObservable)
	 */
	default public NumberCompution subtract(NumberObservable<? extends Number> observer) {
		return NumberCompution.subtract(this, observer);
	}
	
	/**
	 * Returns NumberCompution of {@code -(this.value)}.
	 * 
	 * @return NumberCompution of {@code -(this.value)}.
	 * @see NumberCompution#negate()
	 */
	default public NumberCompution negate() {
		return NumberCompution.negate(this);
	}
	
	
	/* Number Compution Premitives */
	
	/**
	 * Returns NumberCompution of {@code this.value + value}.
	 * 
	 * @param value the int value
	 * @return NumberCompution of {@code this.value + value}.
	 */
	default public NumberCompution add(int value) {
		return this.add(NumberUtils.unmodifiableInteger(value));
	}
	
	/**
	 * Returns NumberCompution of {@code this.value + value}.
	 * 
	 * @param value the long value
	 * @return NumberCompution of {@code this.value + value}.
	 */
	default public NumberCompution add(long value) {
		return this.add(NumberUtils.unmodifiableLong(value));
	}
	
	/**
	 * Returns NumberCompution of {@code this.value + value}.
	 * 
	 * @param value the float value
	 * @return NumberCompution of {@code this.value + value}.
	 */
	default public NumberCompution add(float value) {
		return this.add(NumberUtils.unmodifiableFloat(value));
	}
	
	/**
	 * Returns NumberCompution of {@code this.value + value}.
	 * 
	 * @param value the double value
	 * @return NumberCompution of {@code this.value + value}.
	 */
	default public NumberCompution add(double value) {
		return this.add(NumberUtils.unmodifiableDouble(value));
	}
	
	/**
	 * Returns NumberCompution of {@code this.value * value}.
	 * 
	 * @param value the int value
	 * @return NumberCompution of {@code this.value * value}.
	 */
	default public NumberCompution multiply(int value) {
		return this.multiply(NumberUtils.unmodifiableInteger(value));
	}

	/**
	 * Returns NumberCompution of {@code this.value * value}.
	 * 
	 * @param value the long value
	 * @return NumberCompution of {@code this.value * value}.
	 */
	default public NumberCompution multiply(long value) {
		return this.multiply(NumberUtils.unmodifiableLong(value));
	}

	/**
	 * Returns NumberCompution of {@code this.value * value}.
	 * 
	 * @param value the float value
	 * @return NumberCompution of {@code this.value * value}.
	 */
	default public NumberCompution multiply(float value) {
		return this.multiply(NumberUtils.unmodifiableFloat(value));
	}
	
	/**
	 * Returns NumberCompution of {@code this.value * value}.
	 * 
	 * @param value the double value
	 * @return NumberCompution of {@code this.value * value}.
	 */
	default public NumberCompution multiply(double value) {
		return this.multiply(NumberUtils.unmodifiableDouble(value));
	}
	
	/**
	 * Returns NumberCompution of {@code this.value - value}.
	 * 
	 * @param value the int value
	 * @return NumberCompution of {@code this.value - value}.
	 */
	default public NumberCompution subtract(int value) {
		return this.subtract(NumberUtils.unmodifiableInteger(value));
	}
	
	/**
	 * Returns NumberCompution of {@code this.value - value}.
	 * 
	 * @param value the long value
	 * @return NumberCompution of {@code this.value - value}.
	 */
	default public NumberCompution subtract(long value) {
		return this.subtract(NumberUtils.unmodifiableLong(value));
	}
	
	/**
	 * Returns NumberCompution of {@code this.value - value}.
	 * 
	 * @param value the float value
	 * @return NumberCompution of {@code this.value - value}.
	 */
	default public NumberCompution subtract(float value) {
		return this.subtract(NumberUtils.unmodifiableFloat(value));
	}
	
	/**
	 * Returns NumberCompution of {@code this.value - value}.
	 * 
	 * @param value the double value
	 * @return NumberCompution of {@code this.value - value}.
	 */
	default public NumberCompution subtract(double value) {
		return this.subtract(NumberUtils.unmodifiableDouble(value));
	}
	
	
	/* Comparative Compution Bases */
	
	/**
	 * Returns ComparativeCompution of {@code this.value == observable.value}.
	 * 
	 * @param observer the NumberObserver
	 * @return ComparativeCompution of {@code this.value == observable.value}.
	 */
	default ComparativeCompution computeIsEqualTo(NumberObservable<? extends Number> observer) {
		return ComparativeCompution.isEqualTo(this, observer);
	}
	
	/**
	 * Returns ComparativeCompution of {@code this.value != observable.value}.
	 * 
	 * @param observer the NumberObserver
	 * @return ComparativeCompution of {@code this.value != observable.value}.
	 */
	default ComparativeCompution computeIsNotEqualTo(NumberObservable<? extends Number> observer) {
		return ComparativeCompution.isNotEqualTo(this, observer);
	}
	
	/**
	 * Returns ComparativeCompution of {@code this.value < observable.value}.
	 * 
	 * @param observer the NumberObserver
	 * @return ComparativeCompution of {@code this.value < observable.value}.
	 */
	default ComparativeCompution computeIsLessThan(NumberObservable<? extends Number> observer) {
		return ComparativeCompution.isLessThan(this, observer);
	}
	
	/**
	 * Returns ComparativeCompution of {@code this.value <= observable.value}.
	 * @param observer the NumberObserver
	 * @return ComparativeCompution of {@code this.value <= observable.value}.
	 */
	default ComparativeCompution computeIsLessThanOrEqualTo(NumberObservable<? extends Number> observer) {
		return ComparativeCompution.isLessThanOrEqualTo(this, observer);
	}
	
	/**
	 * Returns ComparativeCompution of {@code this.value > observable.value}.
	 * 
	 * @param observer the NumberObserver
	 * @return ComparativeCompution of {@code this.value > observable.value}.
	 */
	default ComparativeCompution computeIsGreaterThan(NumberObservable<? extends Number> observer) {
		return ComparativeCompution.isGreaterThan(this, observer);
	}
	
	/**
	 * Returns ComparativeCompution of {@code this.value >= observable.value}.
	 * 
	 * @param observer the NumberObserver
	 * @return ComparativeCompution of {@code this.value >= observable.value}.
	 */
	default ComparativeCompution computeIsGreaterThanOrEqualTo(NumberObservable<? extends Number> observer) {
		return ComparativeCompution.isGreaterThanOrEqualTo(this, observer);
	}
	
	/* Comparative Compution Primitives */
	
	/**
	 * Returns ComparativeCompution of {@code this.value == value}.
	 * 
	 * @param value the int value
	 * @return ComparativeCompution of {@code this.value == value}.
	 */
	default ComparativeCompution computeIsEqualTo(int value) {
		return this.computeIsEqualTo(NumberUtils.unmodifiableInteger(value));
	}
	
	/**
	 * Returns ComparativeCompution of {@code this.value == value}.
	 * 
	 * @param value the long value
	 * @return ComparativeCompution of {@code this.value == value}.
	 */
	default ComparativeCompution computeIsEqualTo(long value) {
		return this.computeIsEqualTo(NumberUtils.unmodifiableLong(value));
	}
	
	/**
	 * Returns ComparativeCompution of {@code this.value == value}.
	 * 
	 * @param value the float value
	 * @return ComparativeCompution of {@code this.value == value}.
	 */
	default ComparativeCompution computeIsEqualTo(float value) {
		return this.computeIsEqualTo(NumberUtils.unmodifiableFloat(value));
	}
	
	/**
	 * Returns ComparativeCompution of {@code this.value == value}.
	 * 
	 * @param value the double value
	 * @return ComparativeCompution of {@code this.value == value}.
	 */
	default ComparativeCompution computeIsEqualTo(double value) {
		return this.computeIsEqualTo(NumberUtils.unmodifiableDouble(value));
	}
	
	/**
	 * Returns ComparativeCompution of {@code this.value != value}.
	 * 
	 * @param value the int value
	 * @return ComparativeCompution of {@code this.value != value}.
	 */
	default ComparativeCompution computeIsNotEqualTo(int value) {
		return this.computeIsNotEqualTo(NumberUtils.unmodifiableInteger(value));
	}
	
	/**
	 * Returns ComparativeCompution of {@code this.value != value}.
	 * 
	 * @param value the long value
	 * @return ComparativeCompution of {@code this.value != value}.
	 */
	default ComparativeCompution computeIsNotEqualTo(long value) {
		return this.computeIsNotEqualTo(NumberUtils.unmodifiableLong(value));
	}
	
	/**
	 * Returns ComparativeCompution of {@code this.value != value}.
	 * 
	 * @param value the float value
	 * @return ComparativeCompution of {@code this.value != value}.
	 */
	default ComparativeCompution computeIsNotEqualTo(float value) {
		return this.computeIsNotEqualTo(NumberUtils.unmodifiableFloat(value));
	}
	
	/**
	 * Returns ComparativeCompution of {@code this.value != value}.
	 * 
	 * @param value the double value
	 * @return ComparativeCompution of {@code this.value != value}.
	 */
	default ComparativeCompution computeIsNotEqualTo(double value) {
		return this.computeIsNotEqualTo(NumberUtils.unmodifiableDouble(value));
	}
	
	/**
	 * Returns ComparativeCompution of {@code this.value != value}.
	 * 
	 * @param value the int value
	 * @return ComparativeCompution of {@code this.value != value}.
	 */
	default ComparativeCompution computeIsLessThan(int value) {
		return this.computeIsLessThan(NumberUtils.unmodifiableInteger(value));
	}
	
	/**
	 * Returns ComparativeCompution of {@code this.value < value}.
	 * 
	 * @param value the long value
	 * @return ComparativeCompution of {@code this.value < value}.
	 */
	default ComparativeCompution computeIsLessThan(long value) {
		return this.computeIsLessThan(NumberUtils.unmodifiableLong(value));
	}
	
	/**
	 * Returns ComparativeCompution of {@code this.value < value}.
	 * 
	 * @param value the float value
	 * @return ComparativeCompution of {@code this.value < value}.
	 */
	default ComparativeCompution computeIsLessThan(float value) {
		return this.computeIsLessThan(NumberUtils.unmodifiableFloat(value));
	}
	
	/**
	 * Returns ComparativeCompution of {@code this.value < value}.
	 * 
	 * @param value the double value
	 * @return ComparativeCompution of {@code this.value < value}.
	 */
	default ComparativeCompution computeIsLessThan(double value) {
		return this.computeIsLessThan(NumberUtils.unmodifiableDouble(value));
	}
	
	/**
	 * Returns ComparativeCompution of {@code this.value <= value}.
	 * 
	 * @param value the int value
	 * @return ComparativeCompution of {@code this.value <= value}.
	 */
	default ComparativeCompution computeIsLessThanOrEqualTo(int value) {
		return this.computeIsLessThanOrEqualTo(NumberUtils.unmodifiableInteger(value));
	}
	
	/**
	 * Returns ComparativeCompution of {@code this.value <= value}.
	 * 
	 * @param value the long value
	 * @return ComparativeCompution of {@code this.value <= value}.
	 */
	default ComparativeCompution computeIsLessThanOrEqualTo(long value) {
		return this.computeIsLessThanOrEqualTo(NumberUtils.unmodifiableLong(value));
	}
	
	/**
	 * Returns ComparativeCompution of {@code this.value <= value}.
	 * 
	 * @param value the float value
	 * @return ComparativeCompution of {@code this.value <= value}.
	 */
	default ComparativeCompution computeIsLessThanOrEqualTo(float value) {
		return this.computeIsLessThanOrEqualTo(NumberUtils.unmodifiableFloat(value));
	}
	
	/**
	 * Returns ComparativeCompution of {@code this.value <= value}.
	 * 
	 * @param value the double value
	 * @return ComparativeCompution of {@code this.value <= value}.
	 */
	default ComparativeCompution computeIsLessThanOrEqualTo(double value) {
		return this.computeIsLessThanOrEqualTo(NumberUtils.unmodifiableDouble(value));
	}
	
	/**
	 * Returns ComparativeCompution of {@code this.value > value}.
	 * 
	 * @param value the int value
	 * @return ComparativeCompution of {@code this.value > value}.
	 */
	default ComparativeCompution computeIsGreaterThan(int value) {
		return this.computeIsGreaterThan(NumberUtils.unmodifiableInteger(value));
	}
	
	/**
	 * Returns ComparativeCompution of {@code this.value > value}.
	 * 
	 * @param value the long value
	 * @return ComparativeCompution of {@code this.value > value}.
	 */
	default ComparativeCompution computeIsGreaterThan(long value) {
		return this.computeIsGreaterThan(NumberUtils.unmodifiableLong(value));
	}
	
	/**
	 * Returns ComparativeCompution of {@code this.value > value}.
	 * 
	 * @param value the float value
	 * @return ComparativeCompution of {@code this.value > value}.
	 */
	default ComparativeCompution computeIsGreaterThan(float value) {
		return this.computeIsGreaterThan(NumberUtils.unmodifiableFloat(value));
	}
	
	/**
	 * Returns ComparativeCompution of {@code this.value > value}.
	 * 
	 * @param value the double value
	 * @return ComparativeCompution of {@code this.value > value}.
	 */
	default ComparativeCompution computeIsGreaterThan(double value) {
		return this.computeIsGreaterThan(NumberUtils.unmodifiableDouble(value));
	}
	
	/**
	 * Returns ComparativeCompution of {@code this.value >= value}.
	 * 
	 * @param value the int value
	 * @return ComparativeCompution of {@code this.value >= value}.
	 */
	default ComparativeCompution computeIsGreaterThanOrEqualTo(int value) {
		return this.computeIsGreaterThanOrEqualTo(NumberUtils.unmodifiableInteger(value));
	}
	
	/**
	 * Returns ComparativeCompution of {@code this.value >= value}.
	 * 
	 * @param value the long value
	 * @return ComparativeCompution of {@code this.value >= value}.
	 */
	default ComparativeCompution computeIsGreaterThanOrEqualTo(long value) {
		return this.computeIsGreaterThanOrEqualTo(NumberUtils.unmodifiableLong(value));
	}
	
	/**
	 * Returns ComparativeCompution of {@code this.value >= value}.
	 * 
	 * @param value the float value
	 * @return ComparativeCompution of {@code this.value >= value}.
	 */
	default ComparativeCompution computeIsGreaterThanOrEqualTo(float value) {
		return this.computeIsGreaterThanOrEqualTo(NumberUtils.unmodifiableFloat(value));
	}
	
	/**
	 * Returns ComparativeCompution of {@code this.value >= value}.
	 * 
	 * @param value the double value
	 * @return ComparativeCompution of {@code this.value >= value}.
	 */
	default ComparativeCompution computeIsGreaterThanOrEqualTo(double value) {
		return this.computeIsGreaterThanOrEqualTo(NumberUtils.unmodifiableDouble(value));
	}
	
	/**
	 * Returns ComparativeCompution of {@code this.value == 0}.
	 * 
	 * @return ComparativeCompution of {@code this.value == 0}.
	 */
	default ComparativeCompution computeIsEqualToZero() {
		return this.computeIsEqualTo(NumberUtils.getUnmodifiableZero());
	}
	
	/**
	 * Returns ComparativeCompution of {@code this.value != 0}.
	 * 
	 * @return ComparativeCompution of {@code this.value != 0}.
	 */
	default ComparativeCompution computeIsNotEqualToZero() {
		return this.computeIsNotEqualTo(NumberUtils.getUnmodifiableZero());
	}
	
	/**
	 * Returns ComparativeCompution of {@code this.value < 0}.
	 * 
	 * @return ComparativeCompution of {@code this.value < 0}.
	 */
	default ComparativeCompution computeIsLessThanZero() {
		return this.computeIsLessThan(NumberUtils.getUnmodifiableZero());
	}

	/**
	 * Returns ComparativeCompution of {@code this.value <= 0}.
	 * 
	 * @return ComparativeCompution of {@code this.value <= 0}.
	 */
	default ComparativeCompution computeIsLessThanOrEqualToZero() {
		return this.computeIsLessThanOrEqualTo(NumberUtils.getUnmodifiableZero());
	}

	/**
	 * Returns ComparativeCompution of {@code this.value > 0}.
	 * 
	 * @return ComparativeCompution of {@code this.value > 0}.
	 */
	default ComparativeCompution computeIsGreaterThanZero() {
		return this.computeIsGreaterThan(NumberUtils.getUnmodifiableZero());
	}

	/**
	 * Returns ComparativeCompution of {@code this.value >= 0}.
	 * 
	 * @return ComparativeCompution of {@code this.value >= 0}.
	 */
	default ComparativeCompution computeIsGreaterThanOrEqualToZero() {
		return this.computeIsGreaterThanOrEqualTo(NumberUtils.getUnmodifiableZero());
	}
	
	/* Wait Until Bases */
	
	/**
	 * Waiting until {@code this value == observable value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param observer the NumberObserver
	 * @throws InterruptedException if interrupted while waiting
	 */
	default public void waitUntilEqualTo(NumberObservable<? extends Number> observer) throws InterruptedException {
		NumberUtils.waitUntil(NumberUtils.isEqualTo(this, observer), this, observer);
	}
	
	/**
	 * Waiting until {@code this.value == observable.value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param observer the NumberObserver
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public void waitUntilEqualTo(NumberObservable<? extends Number> observer, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		NumberUtils.waitUntil(NumberUtils.isEqualTo(this, observer), this, observer, timeout, unit);
	}
	
	/**
	 * Waiting until {@code this.value == observable.value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param observer the NumberObserver
	 * @param p is TimeProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public void waitUntilEqualTo(NumberObservable<? extends Number> observer, TimeoutGettable p) throws InterruptedException, TimeoutException {
		NumberUtils.waitUntil(NumberUtils.isEqualTo(this, observer), this, observer, p);
	}
	
	/**
	 * Waiting until {@code this.value != observable.value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param observer the NumberObserver
	 * @throws InterruptedException if interrupted while waiting
	 */
	default public void waitUntilNotEqualTo(NumberObservable<? extends Number> observer) throws InterruptedException {
		NumberUtils.waitUntil(NumberUtils.isNotEqualTo(this, observer), this, observer);
	}
	
	/**
	 * Waiting until {@code this.value != observable.value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param observer the NumberObserver
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public void waitUntilNotEqualTo(NumberObservable<? extends Number> observer, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		NumberUtils.waitUntil(NumberUtils.isNotEqualTo(this, observer), this, observer, timeout, unit);
	}
	
	/**
	 * Waiting until {@code this.value != observable.value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param observer the NumberObserver
	 * @param p is TimeProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public void waitUntilNotEqualTo(NumberObservable<? extends Number> observer, TimeoutGettable p) throws InterruptedException, TimeoutException {
		NumberUtils.waitUntil(NumberUtils.isNotEqualTo(this, observer), this, observer, p);
	}
	
	/**
	 * Waiting until {@code this.value < observable.value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param observer the NumberObserver
	 * @throws InterruptedException if interrupted while waiting
	 */
	default public void waitUntilLessThan(NumberObservable<? extends Number> observer) throws InterruptedException {
		NumberUtils.waitUntil(NumberUtils.isLessThan(this, observer), this, observer);
	}
	
	/**
	 * Waiting until {@code this.value < observable.value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param observer the NumberObserver
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public void waitUntilLessThan(NumberObservable<? extends Number> observer, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		NumberUtils.waitUntil(NumberUtils.isLessThan(this, observer), this, observer, timeout, unit);
	}
	
	/**
	 * Waiting until {@code this.value < observable.value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param observer the NumberObserver
	 * @param p is TimeProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public void waitUntilLessThan(NumberObservable<? extends Number> observer, TimeoutGettable p) throws InterruptedException, TimeoutException {
		NumberUtils.waitUntil(NumberUtils.isLessThan(this, observer), this, observer, p);
	}
	
	/**
	 * Waiting until {@code this.value <= observable.value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param observer the NumberObserver
	 * @throws InterruptedException if interrupted while waiting
	 */
	default public void waitUntilLessThanOrEqualTo(NumberObservable<? extends Number> observer) throws InterruptedException {
		NumberUtils.waitUntil(NumberUtils.isLessThanOrEqualTo(this, observer), this, observer);
	}
	
	/**
	 * Waiting until {@code this.value <= observable.value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param observer the NumberObserver
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public void waitUntilLessThanOrEqualTo(NumberObservable<? extends Number> observer, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		NumberUtils.waitUntil(NumberUtils.isLessThanOrEqualTo(this, observer), this, observer, timeout, unit);
	}
	
	/**
	 * Waiting until {@code this.value <= observable.value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param observer the NumberObserver
	 * @param p is TimeProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public void waitUntilLessThanOrEqualTo(NumberObservable<? extends Number> observer, TimeoutGettable p) throws InterruptedException, TimeoutException {
		NumberUtils.waitUntil(NumberUtils.isLessThanOrEqualTo(this, observer), this, observer, p);
	}
	
	/**
	 * Waiting until {@code this.value > observable.value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param observer the NumberObserver
	 * @throws InterruptedException if interrupted while waiting
	 */
	default public void waitUntilGreaterThan(NumberObservable<? extends Number> observer) throws InterruptedException {
		NumberUtils.waitUntil(NumberUtils.isGreaterThan(this, observer), this, observer);
	}
	
	/**
	 * Waiting until {@code this.value > observable.value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param observer the NumberObserver
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public void waitUntilGreaterThan(NumberObservable<? extends Number> observer, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		NumberUtils.waitUntil(NumberUtils.isGreaterThan(this, observer), this, observer, timeout, unit);
	}
	
	/**
	 * Waiting until {@code this.value > observable.value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param observer the NumberObserver
	 * @param p is TimeProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public void waitUntilGreaterThan(NumberObservable<? extends Number> observer, TimeoutGettable p) throws InterruptedException, TimeoutException {
		NumberUtils.waitUntil(NumberUtils.isGreaterThan(this, observer), this, observer, p);
	}
	
	/**
	 * Waiting until {@code this.value >= observable.value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param observer the NumberObserver
	 * @throws InterruptedException if interrupted while waiting
	 */
	default public void waitUntilGreaterThanOrEqualTo(NumberObservable<? extends Number> observer) throws InterruptedException {
		NumberUtils.waitUntil(NumberUtils.isGreaterThanOrEqualTo(this, observer), this, observer);
	}
	
	/**
	 * Waiting until {@code this.value >= observable.value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param observer the NumberObserver
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public void waitUntilGreaterThanOrEqualTo(NumberObservable<? extends Number> observer, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		NumberUtils.waitUntil(NumberUtils.isGreaterThanOrEqualTo(this, observer), this, observer, timeout, unit);
	}
	
	/**
	 * Waiting until {@code this.value >= observable.value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param observer the NumberObserver
	 * @param p the TimeoutProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public void waitUntilGreaterThanOrEqualTo(NumberObservable<? extends Number> observer, TimeoutGettable p) throws InterruptedException, TimeoutException {
		NumberUtils.waitUntil(NumberUtils.isGreaterThanOrEqualTo(this, observer), this, observer, p);
	}
	
	/* Wait until Primitives */
	
	/**
	 * Waiting until {@code this.value == value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param value the int value
	 * @throws InterruptedException if interrupted while waiting
	 */
	default public void waitUntilEqualTo(int value) throws InterruptedException {
		this.waitUntilEqualTo(NumberUtils.unmodifiableInteger(value));
	}
	
	/**
	 * Waiting until {@code this.value == value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param value the long value
	 * @throws InterruptedException if interrupted while waiting
	 */
	default public void waitUntilEqualTo(long value) throws InterruptedException {
		this.waitUntilEqualTo(NumberUtils.unmodifiableLong(value));
	}
	
	/**
	 * Waiting until {@code this.value == value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param value the float value
	 * @throws InterruptedException if interrupted while waiting
	 */
	default public void waitUntilEqualTo(float value) throws InterruptedException {
		this.waitUntilEqualTo(NumberUtils.unmodifiableFloat(value));
	}
	
	/**
	 * Waiting until {@code this.value == value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param value the double value
	 * @throws InterruptedException if interrupted while waiting
	 */
	default public void waitUntilEqualTo(double value) throws InterruptedException {
		this.waitUntilEqualTo(NumberUtils.unmodifiableDouble(value));
	}
	
	/**
	 * Waiting until {@code this.value == value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param value the int value
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public void waitUntilEqualTo(int value, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		this.waitUntilEqualTo(NumberUtils.unmodifiableInteger(value), timeout, unit);
	}
	
	/**
	 * Waiting until {@code this.value == value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param value the long value
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public void waitUntilEqualTo(long value, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		this.waitUntilEqualTo(NumberUtils.unmodifiableLong(value), timeout, unit);
	}
	
	/**
	 * Waiting until {@code this.value == value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param value the float value
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public void waitUntilEqualTo(float value, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		this.waitUntilEqualTo(NumberUtils.unmodifiableFloat(value), timeout, unit);
	}
	
	/**
	 * Waiting until {@code this.value == value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param value the double value
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public void waitUntilEqualTo(double value, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		this.waitUntilEqualTo(NumberUtils.unmodifiableDouble(value), timeout, unit);
	}
	
	/**
	 * Waiting until {@code this.value == value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param value the int value
	 * @param p the TimeoutProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public void waitUntilEqualTo(int value, TimeoutGettable p) throws InterruptedException, TimeoutException {
		this.waitUntilEqualTo(NumberUtils.unmodifiableInteger(value), p);
	}
	
	/**
	 * Waiting until {@code this.value == value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param value the long value
	 * @param p the TimeoutProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public void waitUntilEqualTo(long value, TimeoutGettable p) throws InterruptedException, TimeoutException {
		this.waitUntilEqualTo(NumberUtils.unmodifiableLong(value), p);
	}
	
	/**
	 * Waiting until {@code this.value == value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param value the float value
	 * @param p the TimeoutProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public void waitUntilEqualTo(float value, TimeoutGettable p) throws InterruptedException, TimeoutException {
		this.waitUntilEqualTo(NumberUtils.unmodifiableFloat(value), p);
	}
	
	/**
	 * Waiting until {@code this.value == value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param value the double value
	 * @param p the TimeoutProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public void waitUntilEqualTo(double value, TimeoutGettable p) throws InterruptedException, TimeoutException {
		this.waitUntilEqualTo(NumberUtils.unmodifiableDouble(value), p);
	}
	
	/**
	 * Waiting until {@code this.value != value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param value the int value
	 * @throws InterruptedException if interrupted while waiting
	 */
	default public void waitUntilNotEqualTo(int value) throws InterruptedException {
		this.waitUntilNotEqualTo(NumberUtils.unmodifiableInteger(value));
	}
	
	/**
	 * Waiting until {@code this.value != value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param value the long value
	 * @throws InterruptedException if interrupted while waiting
	 */
	default public void waitUntilNotEqualTo(long value) throws InterruptedException {
		this.waitUntilNotEqualTo(NumberUtils.unmodifiableLong(value));
	}
	
	/**
	 * Waiting until {@code this.value != value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param value the float value
	 * @throws InterruptedException if interrupted while waiting
	 */
	default public void waitUntilNotEqualTo(float value) throws InterruptedException {
		this.waitUntilNotEqualTo(NumberUtils.unmodifiableFloat(value));
	}
	
	/**
	 * Waiting until {@code this.value != value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param value the double value
	 * @throws InterruptedException if interrupted while waiting
	 */
	default public void waitUntilNotEqualTo(double value) throws InterruptedException {
		this.waitUntilNotEqualTo(NumberUtils.unmodifiableDouble(value));
	}
	
	/**
	 * Waiting until {@code this.value != value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param value the int value
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public void waitUntilNotEqualTo(int value, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		this.waitUntilNotEqualTo(NumberUtils.unmodifiableInteger(value), timeout, unit);
	}
	
	/**
	 * Waiting until {@code this.value != value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param value the long value
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public void waitUntilNotEqualTo(long value, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		this.waitUntilNotEqualTo(NumberUtils.unmodifiableLong(value), timeout, unit);
	}
	
	/**
	 * Waiting until {@code this.value != value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param value the float value
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public void waitUntilNotEqualTo(float value, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		this.waitUntilNotEqualTo(NumberUtils.unmodifiableFloat(value), timeout, unit);
	}
	
	/**
	 * Waiting until {@code this.value != value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param value the double value
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public void waitUntilNotEqualTo(double value, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		this.waitUntilNotEqualTo(NumberUtils.unmodifiableDouble(value), timeout, unit);
	}
	
	/**
	 * Waiting until {@code this.value != value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param value the int value
	 * @param p the TimeoutProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public void waitUntilNotEqualTo(int value, TimeoutGettable p) throws InterruptedException, TimeoutException {
		this.waitUntilNotEqualTo(NumberUtils.unmodifiableInteger(value), p);
	}
	
	/**
	 * Waiting until {@code this.value != value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param value the long value
	 * @param p the TimeoutProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public void waitUntilNotEqualTo(long value, TimeoutGettable p) throws InterruptedException, TimeoutException {
		this.waitUntilNotEqualTo(NumberUtils.unmodifiableLong(value), p);
	}
	
	/**
	 * Waiting until {@code this.value != value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param value the float value
	 * @param p the TimeoutProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public void waitUntilNotEqualTo(float value, TimeoutGettable p) throws InterruptedException, TimeoutException {
		this.waitUntilNotEqualTo(NumberUtils.unmodifiableFloat(value), p);
	}
	
	/**
	 * Waiting until {@code this.value != value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param value the double value
	 * @param p the TimeoutProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public void waitUntilNotEqualTo(double value, TimeoutGettable p) throws InterruptedException, TimeoutException {
		this.waitUntilNotEqualTo(NumberUtils.unmodifiableDouble(value), p);
	}
	
	/**
	 * Waiting until {@code this.value < value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param value the int value
	 * @throws InterruptedException if interrupted while waiting
	 */
	default public void waitUntilLessThan(int value) throws InterruptedException {
		this.waitUntilLessThan(NumberUtils.unmodifiableInteger(value));
	}
	
	/**
	 * Waiting until {@code this.value < value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param value the long value
	 * @throws InterruptedException if interrupted while waiting
	 */
	default public void waitUntilLessThan(long value) throws InterruptedException {
		this.waitUntilLessThan(NumberUtils.unmodifiableLong(value));
	}
	
	/**
	 * Waiting until {@code this.value < value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param value the float value
	 * @throws InterruptedException if interrupted while waiting
	 */
	default public void waitUntilLessThan(float value) throws InterruptedException {
		this.waitUntilLessThan(NumberUtils.unmodifiableFloat(value));
	}
	
	/**
	 * Waiting until {@code this.value < value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param value the double value
	 * @throws InterruptedException if interrupted while waiting
	 */
	default public void waitUntilLessThan(double value) throws InterruptedException {
		this.waitUntilLessThan(NumberUtils.unmodifiableDouble(value));
	}
	
	/**
	 * Waiting until {@code this.value < value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param value the int value
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public void waitUntilLessThan(int value, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		this.waitUntilLessThan(NumberUtils.unmodifiableInteger(value), timeout, unit);
	}
	
	/**
	 * Waiting until {@code this.value < value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param value the long value
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public void waitUntilLessThan(long value, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		this.waitUntilLessThan(NumberUtils.unmodifiableLong(value), timeout, unit);
	}
	
	/**
	 * Waiting until {@code this.value < value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param value the float value
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public void waitUntilLessThan(float value, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		this.waitUntilLessThan(NumberUtils.unmodifiableFloat(value), timeout, unit);
	}
	
	/**
	 * Waiting until {@code this.value < value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param value the double value
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public void waitUntilLessThan(double value, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		this.waitUntilLessThan(NumberUtils.unmodifiableDouble(value), timeout, unit);
	}
	
	/**
	 * Waiting until {@code this.value < value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param value the int value
	 * @param p the TimeoutProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public void waitUntilLessThan(int value, TimeoutGettable p) throws InterruptedException, TimeoutException {
		this.waitUntilLessThan(NumberUtils.unmodifiableInteger(value), p);
	}
	
	/**
	 * Waiting until {@code this.value < value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param value the long value
	 * @param p the TimeoutProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public void waitUntilLessThan(long value, TimeoutGettable p) throws InterruptedException, TimeoutException {
		this.waitUntilLessThan(NumberUtils.unmodifiableLong(value), p);
	}
	
	/**
v	 * Waiting until {@code this.value < value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param value the float value
	 * @param p the TimeoutProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public void waitUntilLessThan(float value, TimeoutGettable p) throws InterruptedException, TimeoutException {
		this.waitUntilLessThan(NumberUtils.unmodifiableFloat(value), p);
	}
	
	/**
	 * Waiting until {@code this.value < value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param value the double value
	 * @param p the TimeoutProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public void waitUntilLessThan(double value, TimeoutGettable p) throws InterruptedException, TimeoutException {
		this.waitUntilLessThan(NumberUtils.unmodifiableDouble(value), p);
	}
	
	/**
	 * Waiting until {@code this.value <= value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param value the int value
	 * @throws InterruptedException if interrupted while waiting
	 */
	default public void waitUntilLessThanOrEqualTo(int value) throws InterruptedException {
		this.waitUntilLessThanOrEqualTo(NumberUtils.unmodifiableInteger(value));
	}
	
	/**
	 * Waiting until {@code this.value <= value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param value the long value
	 * @throws InterruptedException if interrupted while waiting
	 */
	default public void waitUntilLessThanOrEqualTo(long value) throws InterruptedException {
		this.waitUntilLessThanOrEqualTo(NumberUtils.unmodifiableLong(value));
	}
	
	/**
	 * Waiting until {@code this.value <= value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param value the float value
	 * @throws InterruptedException if interrupted while waiting
	 */
	default public void waitUntilLessThanOrEqualTo(float value) throws InterruptedException {
		this.waitUntilLessThanOrEqualTo(NumberUtils.unmodifiableFloat(value));
	}
	
	/**
	 * Waiting until {@code this.value <= value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param value the double value
	 * @throws InterruptedException if interrupted while waiting
	 */
	default public void waitUntilLessThanOrEqualTo(double value) throws InterruptedException {
		this.waitUntilLessThanOrEqualTo(NumberUtils.unmodifiableDouble(value));
	}
	
	/**
	 * Waiting until {@code this.value <= value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param value the int value
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public void waitUntilLessThanOrEqualTo(int value, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		this.waitUntilLessThanOrEqualTo(NumberUtils.unmodifiableInteger(value), timeout, unit);
	}
	
	/**
	 * Waiting until {@code this.value <= value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param value the long value
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public void waitUntilLessThanOrEqualTo(long value, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		this.waitUntilLessThanOrEqualTo(NumberUtils.unmodifiableLong(value), timeout, unit);
	}
	
	/**
	 * Waiting until {@code this.value <= value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param value the float value
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public void waitUntilLessThanOrEqualTo(float value, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		this.waitUntilLessThanOrEqualTo(NumberUtils.unmodifiableFloat(value), timeout, unit);
	}
	
	/**
	 * Waiting until {@code this.value <= value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param value the double value
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public void waitUntilLessThanOrEqualTo(double value, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		this.waitUntilLessThanOrEqualTo(NumberUtils.unmodifiableDouble(value), timeout, unit);
	}
	
	/**
	 * Waiting until {@code this.value <= value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param value s int
	 * @param p the TimeoutProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public void waitUntilLessThanOrEqualTo(int value, TimeoutGettable p) throws InterruptedException, TimeoutException {
		this.waitUntilLessThanOrEqualTo(NumberUtils.unmodifiableInteger(value), p);
	}
	
	/**
v	 * Waiting until {@code this.value <= value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param value the long value
	 * @param p the TimeoutProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public void waitUntilLessThanOrEqualTo(long value, TimeoutGettable p) throws InterruptedException, TimeoutException {
		this.waitUntilLessThanOrEqualTo(NumberUtils.unmodifiableLong(value), p);
	}
	
	/**
	 * Waiting until {@code this.value <= value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param value the float value
	 * @param p the TimeoutProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public void waitUntilLessThanOrEqualTo(float value, TimeoutGettable p) throws InterruptedException, TimeoutException {
		this.waitUntilLessThanOrEqualTo(NumberUtils.unmodifiableFloat(value), p);
	}
	
	/**
	 * Waiting until {@code this.value <= value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param value the double value
	 * @param p the TimeoutProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public void waitUntilLessThanOrEqualTo(double value, TimeoutGettable p) throws InterruptedException, TimeoutException {
		this.waitUntilLessThanOrEqualTo(NumberUtils.unmodifiableDouble(value), p);
	}
	
	/**
	 * Waiting until {@code this.value > value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param value the int value
	 * @throws InterruptedException if interrupted while waiting
	 */
	default public void waitUntilGreaterThan(int value) throws InterruptedException {
		this.waitUntilGreaterThan(NumberUtils.unmodifiableInteger(value));
	}
	
	/**
	 * Waiting until {@code this.value > value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param value the long value
	 * @throws InterruptedException if interrupted while waiting
	 */
	default public void waitUntilGreaterThan(long value) throws InterruptedException {
		this.waitUntilGreaterThan(NumberUtils.unmodifiableLong(value));
	}
	
	/**
	 * Waiting until {@code this.value > value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param value the float value
	 * @throws InterruptedException if interrupted while waiting
	 */
	default public void waitUntilGreaterThan(float value) throws InterruptedException {
		this.waitUntilGreaterThan(NumberUtils.unmodifiableFloat(value));
	}
	
	/**
	 * Waiting until {@code this.value > value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param value the double value
	 * @throws InterruptedException if interrupted while waiting
	 */
	default public void waitUntilGreaterThan(double value) throws InterruptedException {
		this.waitUntilGreaterThan(NumberUtils.unmodifiableDouble(value));
	}
	
	/**
	 * Waiting until {@code this.value > value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param value the int value
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public void waitUntilGreaterThan(int value, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		this.waitUntilGreaterThan(NumberUtils.unmodifiableInteger(value), timeout, unit);
	}
	
	/**
	 * Waiting until {@code this.value > value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param value the long value
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public void waitUntilGreaterThan(long value, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		this.waitUntilGreaterThan(NumberUtils.unmodifiableLong(value), timeout, unit);
	}
	
	/**
	 * Waiting until {@code this.value > value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param value the float value
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public void waitUntilGreaterThan(float value, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		this.waitUntilGreaterThan(NumberUtils.unmodifiableFloat(value), timeout, unit);
	}
	
	/**
	 * Waiting until {@code this.value > value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param value the double value
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public void waitUntilGreaterThan(double value, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		this.waitUntilGreaterThan(NumberUtils.unmodifiableDouble(value), timeout, unit);
	}
	
	/**
	 * Waiting until {@code this.value > value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param value the int value
	 * @param p the TimeoutProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public void waitUntilGreaterThan(int value, TimeoutGettable p) throws InterruptedException, TimeoutException {
		this.waitUntilGreaterThan(NumberUtils.unmodifiableInteger(value), p);
	}
	
	/**
	 * Waiting until {@code this.value > value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param value the long value
	 * @param p the TimeoutProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public void waitUntilGreaterThan(long value, TimeoutGettable p) throws InterruptedException, TimeoutException {
		this.waitUntilGreaterThan(NumberUtils.unmodifiableLong(value), p);
	}
	
	/**
	 * Waiting until {@code this.value > value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param value the float value
	 * @param p the TimeoutProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public void waitUntilGreaterThan(float value, TimeoutGettable p) throws InterruptedException, TimeoutException {
		this.waitUntilGreaterThan(NumberUtils.unmodifiableFloat(value), p);
	}
	
	/**
	 * Waiting until {@code this.value > value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param value the double value
	 * @param p the TimeoutProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public void waitUntilGreaterThan(double value, TimeoutGettable p) throws InterruptedException, TimeoutException {
		this.waitUntilGreaterThan(NumberUtils.unmodifiableDouble(value), p);
	}
	
	/**
	 * Waiting until {@code this.value >= value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param value the int value
	 * @throws InterruptedException if interrupted while waiting
	 */
	default public void waitUntilGreaterThanOrEqualTo(int value) throws InterruptedException {
		this.waitUntilGreaterThanOrEqualTo(NumberUtils.unmodifiableInteger(value));
	}
	
	/**
	 * Waiting until {@code this.value >= value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param value the long value
	 * @throws InterruptedException if interrupted while waiting
	 */
	default public void waitUntilGreaterThanOrEqualTo(long value) throws InterruptedException {
		this.waitUntilGreaterThanOrEqualTo(NumberUtils.unmodifiableLong(value));
	}
	
	/**
	 * Waiting until {@code this.value >= value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param value the float value
	 * @throws InterruptedException if interrupted while waiting
	 */
	default public void waitUntilGreaterThanOrEqualTo(float value) throws InterruptedException {
		this.waitUntilGreaterThanOrEqualTo(NumberUtils.unmodifiableFloat(value));
	}
	
	/**
	 * Waiting until {@code this.value >= value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param value the double value
	 * @throws InterruptedException if interrupted while waiting
	 */
	default public void waitUntilGreaterThanOrEqualTo(double value) throws InterruptedException {
		this.waitUntilGreaterThanOrEqualTo(NumberUtils.unmodifiableDouble(value));
	}
	
	/**
	 * Waiting until {@code this.value >= value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param value the int value
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public void waitUntilGreaterThanOrEqualTo(int value, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		this.waitUntilGreaterThanOrEqualTo(NumberUtils.unmodifiableInteger(value), timeout, unit);
	}
	
	/**
	 * Waiting until {@code this.value >= value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param value the long value
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public void waitUntilGreaterThanOrEqualTo(long value, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		this.waitUntilGreaterThanOrEqualTo(NumberUtils.unmodifiableLong(value), timeout, unit);
	}
	
	/**
	 * Waiting until {@code this.value >= value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param value the float value
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public void waitUntilGreaterThanOrEqualTo(float value, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		this.waitUntilGreaterThanOrEqualTo(NumberUtils.unmodifiableFloat(value), timeout, unit);
	}
	
	/**
	 * Waiting until {@code this.value >= value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param value the double value
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public void waitUntilGreaterThanOrEqualTo(double value, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		this.waitUntilGreaterThanOrEqualTo(NumberUtils.unmodifiableDouble(value), timeout, unit);
	}
	
	/**
	 * Waiting until {@code this.value >= value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param value the int value
	 * @param p the TimeoutProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public void waitUntilGreaterThanOrEqualTo(int value, TimeoutGettable p) throws InterruptedException, TimeoutException {
		this.waitUntilGreaterThanOrEqualTo(NumberUtils.unmodifiableInteger(value), p);
	}
	
	/**
	 * Waiting until {@code this.value >= value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param value the long value
	 * @param p the TimeoutProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public void waitUntilGreaterThanOrEqualTo(long value, TimeoutGettable p) throws InterruptedException, TimeoutException {
		this.waitUntilGreaterThanOrEqualTo(NumberUtils.unmodifiableLong(value), p);
	}
	
	/**
	 * Waiting until {@code this.value >= value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param value the float value
	 * @param p the TimeoutProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public void waitUntilGreaterThanOrEqualTo(float value, TimeoutGettable p) throws InterruptedException, TimeoutException {
		this.waitUntilGreaterThanOrEqualTo(NumberUtils.unmodifiableFloat(value), p);
	}
	
	/**
	 * Waiting until {@code this.value >= value}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param value the double value
	 * @param p the TimeoutProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public void waitUntilGreaterThanOrEqualTo(double value, TimeoutGettable p) throws InterruptedException, TimeoutException {
		this.waitUntilGreaterThanOrEqualTo(NumberUtils.unmodifiableDouble(value), p);
	}
	
	/* Wait Until ZEROs */
	
	/**
	 * Waiting until {@code this.value == 0}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @throws InterruptedException if interrupted while waiting
	 */
	default public void waitUntilEqualToZero() throws InterruptedException {
		this.waitUntilEqualTo(NumberUtils.getUnmodifiableZero());
	}
	
	/**
	 * Waiting until {@code this.value == 0}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public void waitUntilEqualToZero(long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		this.waitUntilEqualTo(NumberUtils.getUnmodifiableZero(), timeout, unit);
	}
	
	/**
	 * Waiting until {@code this.value == 0}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param p the TimeoutProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public void waitUntilEqualToZero(TimeoutGettable p) throws InterruptedException, TimeoutException {
		this.waitUntilEqualTo(NumberUtils.getUnmodifiableZero(), p);
	}
	
	/**
	 * Waiting until {@code this.value != 0}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @throws InterruptedException if interrupted while waiting
	 */
	default public void waitUntilNotEqualToZero() throws InterruptedException {
		this.waitUntilNotEqualTo(NumberUtils.getUnmodifiableZero());
	}
	
	/**
	 * Waiting until {@code this.value != 0}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public void waitUntilNotEqualToZero(long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		this.waitUntilNotEqualTo(NumberUtils.getUnmodifiableZero(), timeout, unit);
	}
	
	/**
	 * Waiting until {@code this.value != 0}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param p the TimeoutProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public void waitUntilNotEqualToZero(TimeoutGettable p) throws InterruptedException, TimeoutException {
		this.waitUntilNotEqualTo(NumberUtils.getUnmodifiableZero(), p);
	}
	
	/**
	 * Waiting until {@code this.value < 0}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @throws InterruptedException if interrupted while waiting
	 */
	default public void waitUntilLessThanZero() throws InterruptedException {
		this.waitUntilLessThan(NumberUtils.getUnmodifiableZero());
	}
	
	/**
	 * Waiting until {@code this.value < 0}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public void waitUntilLessThanZero(long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		this.waitUntilLessThan(NumberUtils.getUnmodifiableZero(), timeout, unit);
	}
	
	/**
	 * Waiting until {@code this.value < 0}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param p the TimeoutProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public void waitUntilLessThanZero(TimeoutGettable p) throws InterruptedException, TimeoutException {
		this.waitUntilLessThan(NumberUtils.getUnmodifiableZero(), p);
	}
	
	/**
	 * Waiting until {@code this.value <= 0}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @throws InterruptedException if interrupted while waiting
	 */
	default public void waitUntilLessThanOrEqualToZero() throws InterruptedException {
		this.waitUntilLessThanOrEqualTo(NumberUtils.getUnmodifiableZero());
	}
	
	/**
	 * Waiting until {@code this.value <= 0}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public void waitUntilLessThanOrEqualToZero(long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		this.waitUntilLessThanOrEqualTo(NumberUtils.getUnmodifiableZero(), timeout, unit);
	}
	
	/**
	 * Waiting until {@code this.value <= 0}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param p the TimeoutProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public void waitUntilLessThanOrEqualToZero(TimeoutGettable p) throws InterruptedException, TimeoutException {
		this.waitUntilLessThanOrEqualTo(NumberUtils.getUnmodifiableZero(), p);
	}
	
	/**
	 * Waiting until {@code this.value > 0}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @throws InterruptedException if interrupted while waiting
	 */
	default public void waitUntilGreaterThanZero() throws InterruptedException {
		this.waitUntilGreaterThan(NumberUtils.getUnmodifiableZero());
	}
	
	/**
	 * Waiting until {@code this.value > 0}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public void waitUntilGreaterThanZero(long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		this.waitUntilGreaterThan(NumberUtils.getUnmodifiableZero(), timeout, unit);
	}
	
	/**
	 * Waiting until {@code this.value > 0}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param p the TimeoutProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public void waitUntilGreaterThanZero(TimeoutGettable p) throws InterruptedException, TimeoutException {
		this.waitUntilGreaterThan(NumberUtils.getUnmodifiableZero(), p);
	}
	
	/**
	 * Waiting until {@code this.value >= 0}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @throws InterruptedException if interrupted while waiting
	 */
	default public void waitUntilGreaterThanOrEqualToZero() throws InterruptedException {
		this.waitUntilGreaterThanOrEqualTo(NumberUtils.getUnmodifiableZero());
	}
	
	/**
	 * Waiting until {@code this.value >= 0}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public void waitUntilGreaterThanOrEqualToZero(long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		this.waitUntilGreaterThanOrEqualTo(NumberUtils.getUnmodifiableZero(), timeout, unit);
	}
	
	/**
	 * Waiting until {@code this.value >= 0}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param p the TimeoutProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 */
	default public void waitUntilGreaterThanOrEqualToZero(TimeoutGettable p) throws InterruptedException, TimeoutException {
		this.waitUntilGreaterThanOrEqualTo(NumberUtils.getUnmodifiableZero(), p);
	}
	
}
