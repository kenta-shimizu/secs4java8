package com.shimizukenta.secs.local.property;

import com.shimizukenta.secs.local.property.impl.AbstractStringProperty;

/**
 * String value Property, includes Getter, Setter, Observer.
 * 
 * <p>
 * This interface deny null inputs. If input null, replace to empty string.<br />
 * </p>
 * <ul>
 * <li>To build instance, {@link #newInstance()}</li>
 * <li>To set value, {@link #set(CharSequence)}</li>
 * <li>To toString(), {@link #toString()}</li>
 * <li>To detect value changed, {@link #addChangeListener(ChangeListener)}</li>
 * <li>To compute,
 * <ul>
 * <li>{@link #computeIsEmpty()}.</li>
 * <li>{@link #computeIsNotEmpty()}</li>
 * <li>{@link #computeContains(CharSequence)}</li>
 * <li>{@link #computeTrim()}</li>
 * <li>{@link #computeToUpperCase()}</li>
 * <li>{@link #computeToLowerCase()}</li>
 * <li>{@link #computeMatches(String)}</li>
 * <li>{@link #computeLength()}</li>
 * </ul>
 * </li>
 * <li>To wait until condition is true,
 * <ul>
 * <li>{@link #waitUntilIsEmpty()}</li>
 * <li>{@link #waitUntilIsNotEmptyAndGet()}</li>
 * <li>{@link #waitUntilEqualTo(StringObservable)}</li>
 * <li>{@link #waitUntilEqualToIgnoreCase(StringObservable)}</li>
 * </ul>
 * </li>
 * </ul>
 * 
 * @author kenta-shimizu
 * @see StringGettable
 * @see StringSettable
 * @see StringObservable
 * @see Property
 *
 */
public interface StringProperty extends Property<String>, StringGettable, StringSettable, StringObservable {
	
	/**
	 * Empty String instance builder
	 * 
	 * @return new Empty String instance
	 */
	public static StringProperty newInstance() {
		return newInstance(null);
	}
	
	/**
	 * Instance builder.
	 * 
	 * @param initial Initial CharSequence
	 * @return new-instance
	 */
	public static StringProperty newInstance(CharSequence initial) {
		
		return new AbstractStringProperty(initial) {

			private static final long serialVersionUID = -3211169903612875617L;
		};
	}
}
