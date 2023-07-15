package com.shimizukenta.secs.local.property;

import java.util.Arrays;
import java.util.StringJoiner;

import com.shimizukenta.secs.local.property.impl.StringUtils;

/**
 * String value compution, includes Getter, Observer.
 * 
 * <p>
 * <strong>NOT</strong> includes Setter.<br />
 * </p>
 * <p>
 * This instance is built from other Property or Compution.<br />
 * </p>
 * <ul>
 * <li>To build joined StringCompution, {@link #join(CharSequence, StringObservable...)}.</li>
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
 * @see StringObservable
 * @see Compution
 *
 */
public interface StringCompution extends Compution<String>, StringGettable, StringObservable {
	
	/**
	 * Returns joined StringCompution.
	 * 
	 * @param delimiter CharSequence
	 * @param observers StringObservers
	 * @return joined StringCompution
	 * @see StringJoiner
	 */
	public static StringCompution join(CharSequence delimiter, StringObservable... observers) {
		return join(delimiter, Arrays.asList(observers));
	}
	
	/**
	 * Returns joined StringCompution.
	 * 
	 * @param delimiter CharSequence
	 * @param observers StringObservers
	 * @return joined StringCompution
	 * @see StringJoiner
	 */
	public static StringCompution join(CharSequence delimiter, Iterable<StringObservable> observers) {
		return StringUtils.computeJoin(delimiter, observers);
	}
	
}
