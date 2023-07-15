package com.shimizukenta.secs.local.property;

import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.shimizukenta.secs.local.property.impl.StringUtils;

/**
 * String value Observer.
 * 
 * @author shimizukenta
 * @see Observable
 */
public interface StringObservable extends Observable<String> {
	
	/**
	 * Returns StringCompution of converted to uppercase.
	 * 
	 * @return StringCompution of converted to uppercase
	 * @see String#toUpperCase()
	 */
	default public StringCompution computeToUpperCase() {
		return StringUtils.computeToUpperCase(this);
	}
	
	/**
	 * Returns StringCompution of converted to uppercase.
	 * 
	 * @param locale use the case transformation rules for this locale
	 * @return StringCompution of converted to uppercase
	 * @see String#toUpperCase(Locale)
	 */
	default public StringCompution computeToUpperCase(Locale locale) {
		return StringUtils.computeToUpperCase(this, locale);
	}
	
	/**
	 * Returns StringCompution of converted to lowercase.
	 * 
	 * @return StringCompution of converted to lowercase
	 * @see String#toLowerCase()
	 */
	default public StringCompution computeToLowerCase() {
		return StringUtils.computeToLowerCase(this);
	}
	
	/**
	 * Returns StringCompution of converted to lowercase.
	 * 
	 * @param locale use the case transformation rules for this locale
	 * @return StringCompution of converted to lowercase
	 * @see String#toLowerCase(Locale)
	 */
	default public StringCompution computeToLowerCase(Locale locale) {
		return StringUtils.computeToLowerCase(this, locale);
	}
	
	/**
	 * Return StringCompution of converted to trim.
	 * 
	 * @return StringCompution of converted to trim
	 * @see String#trim()
	 */
	default public StringCompution computeTrim() {
		return StringUtils.computeTrim(this);
	}
	
	/**
	 * Returns StringCompution of convert to concat.
	 * 
	 * @param str the String that is concatenated to the end of this String.
	 * @return StringCompution of convert to concat
	 * @see String#concat(String)
	 */
	default public StringCompution computeConcat(String str) {
		return StringUtils.computeConcat(this, str);
	}
	
	/**
	 * Returns StringCompution of convert to replace.
	 * 
	 * @param oldChar the old character.
	 * @param newChar the new character.
	 * @return StringCompution of convert to replace
	 * @see String#replace(char, char)
	 */
	default public StringCompution computeReplace(char oldChar, char newChar) {
		return StringUtils.computeReplace(this, oldChar, newChar);
	}
	
	/**
	 * Returns StringCompution of convert to replace.
	 * 
	 * @param target The sequence of char values to be replaced
	 * @param replacement  The replacement sequence of char values
	 * @return StringCompution of convert to replace
	 * @see String#replace(CharSequence, CharSequence)
	 */
	default public StringCompution computeReplace(CharSequence target, CharSequence replacement) {
		return StringUtils.computeReplace(this, target, replacement);
	}
	
	/**
	 * Returns StringCompution of convert to replaceAll.
	 * 
	 * @param regex the regular expression to which this string is to be matched
	 * @param replacement the string to be substituted for each match
	 * @return StringCompution of convert to replaceAll
	 * @see String#replaceAll(String, String)
	 */
	default public StringCompution computeReplaceAll(String regex, String replacement) {
		return StringUtils.computeReplaceAll(this, regex, replacement);
	}
	
	/**
	 * Returns StringCompution of convert to replaceFirst.
	 * 
	 * @param regex the regular expression to which this string is to be matched
	 * @param replacement the string to be substituted for the first match
	 * @return StringCompution of convert to replaceFirst
	 * @see String#replaceFirst(String, String)
	 */
	default public StringCompution computeReplaceFirst(String regex, String replacement) {
		return StringUtils.computeReplaceFirst(this, regex, replacement);
	}
	
	/**
	 * Returns BooleanCompution of isEmpty.
	 * 
	 * @return BooleanCompution of isEmpty
	 * @see StringProperty#isEmpty()
	 */
	default public BooleanCompution computeIsEmpty() {
		return StringUtils.computeIsEmpty(this);
	}
	
	/**
	 * Returns BooleanCompution of NOT isEmpty.
	 * 
	 * @return BooleanCompution of NOT isEmpty
	 * @see StringProperty#isEmpty()
	 */
	default public BooleanCompution computeIsNotEmpty() {
		return StringUtils.computeIsNotEmpty(this);
	}
	
	/**
	 * Returns BooleanCompution of contais.
	 * 
	 * @param s the sequence to search for
	 * @return BooleanCompution of contais
	 * @see String#contains(CharSequence)
	 */
	default public BooleanCompution computeContains(CharSequence s) {
		return StringUtils.computeContains(this, s);
	}
	
	/**
	 * Returns BooleanCompution of NOT contais.
	 * 
	 * @param s the sequence to search for
	 * @return BooleanCompution of NOT contais
	 * @see String#contains(CharSequence)
	 */
	default public BooleanCompution computeNotContains(CharSequence s) {
		return StringUtils.computeNotContains(this, s);
	}
	
	/**
	 * Returns BooleanCompution of startsWith.
	 * 
	 * @param prefix the prefix.
	 * @return BooleanCompution of startsWith
	 * @see String#startsWith(String)
	 */
	default public BooleanCompution computeStartsWith(String prefix) {
		return StringUtils.computeStartsWith(this, prefix);
	}
	
	/**
	 * Returns BooleanCompution of startsWith.
	 * 
	 * @param prefix the prefix.
	 * @param toOffset where to begin looking in this string.
	 * @return BooleanCompution of startsWith
	 * @see String#startsWith(String, int)
	 */
	default public BooleanCompution computeStartsWith(String prefix, int toOffset) {
		return StringUtils.computeStartsWith(this, prefix, toOffset);
	}
	
	/**
	 * Returns BooleanCompution of endsWith.
	 * 
	 * @param suffix the suffix.
	 * @return BooleanCompution of endsWith
	 * @see String#endsWith(String)
	 */
	default public BooleanCompution computeEndsWith(String suffix) {
		return StringUtils.computeEndsWith(this, suffix);
	}
	
	/**
	 * Returns BooleanCompution of matches.
	 * 
	 * @param regex the regular expression to which this string is to be matched
	 * @return Returns BooleanCompution of matches
	 * @see String#matches(String)
	 */
	default public BooleanCompution computeMatches(String regex) {
		return StringUtils.computeMatches(this, regex);
	}
	
	/**
	 * Returns BooleanCompution of contentEquals.
	 * 
	 * @param cs The sequence to compare this String against
	 * @return BooleanCompution of contentEquals
	 * @see String#contentEquals(CharSequence)
	 */
	default public BooleanCompution computeContentEqualTo(CharSequence cs) {
		return StringUtils.computeContentEqualTo(this, cs);
	}
	
	/**
	 * Returns BooleanCompution of contentEquals.
	 * 
	 * @param sb The StringBuffer to compare this String against
	 * @return BooleanCompution of contentEquals
	 * @see String#contentEquals(StringBuffer)
	 */
	default public BooleanCompution computeContentEqualTo(StringBuffer sb) {
		return StringUtils.computeContentEqualTo(this, sb);
	}
	
	/**
	 * Returns IntegerCompution of String length.
	 * 
	 * @return IntegerCompution of String length
	 * @see String#length()
	 */
	default public IntegerCompution computeLength() {
		return StringUtils.computeLength(this);
	}
	
	/**
	 * Returns IntegerCompution of String indexOf.
	 * 
	 * @param str the substring to search for.
	 * @return IntegerCompution of String IndexOf
	 * @see String#indexOf(String)
	 */
	default public IntegerCompution computeIndexOf(String str) {
		return StringUtils.computeIndexOf(this, str);
	}
	
	/**
	 * Returns IntegerCompution of String indexOf.
	 * 
	 * @param ch a character (Unicode code point).
	 * @return IntegerCompution of String indexOf
	 * @see String#indexOf(int)
	 */
	default public IntegerCompution computeIndexOf(int ch) {
		return StringUtils.computeIndexOf(this, ch);
	}
	
	/**
	 * Returns IntegerCompution of String indexOf.
	 * 
	 * @param str the substring to search for.
	 * @param fromIndex  the index from which to start the search.
	 * @return IntegerCompution of String indexOf
	 * @see String#indexOf(String, int)
	 */
	default public IntegerCompution computeIndexOf(String str, int fromIndex) {
		return StringUtils.computeIndexOf(this, str, fromIndex);
	}
	
	/**
	 * Returns IntegerCompution of String indexOf.
	 * 
	 * @param ch a character (Unicode code point).
	 * @param fromIndex the index to start the search from.
	 * @return IntegerCompution of String indexOf
	 * @see String#indexOf(int, int)
	 */
	default public IntegerCompution computeIndexOf(int ch, int fromIndex) {
		return StringUtils.computeIndexOf(this, ch, fromIndex);
	}
	
	/**
	 * Returns IntegerCompution of String lastIndexOf.
	 * 
	 * @param str the substring to search for.
	 * @return IntegerCompution of String lastIndexOf
	 * @see String#lastIndexOf(String)
	 */
	default public IntegerCompution computeLastIndexOf(String str) {
		return StringUtils.computeLastIndexOf(this, str);
	}
	
	/**
	 * Returns IntegerCompution of String lastIndexOf.
	 * 
	 * @param str the substring to search for.
	 * @param fromIndex the index to start the search from.
	 * @return IntegerCompution of String lastIndexOf
	 * @see String#lastIndexOf(String, int)
	 */
	default public IntegerCompution computeLastIndexOf(String str, int fromIndex) {
		return StringUtils.computeLastIndexOf(this, str, fromIndex);
	}
	
	/**
	 * Returns IntegerCompution of String lastIndexOf.
	 * 
	 * @param ch a character (Unicode code point).
	 * @return IntegerCompution of String lastIndexOf
	 * @see String#lastIndexOf(int)
	 */
	default public IntegerCompution computeLastIndexOf(int ch) {
		return StringUtils.computeLastIndexOf(this, ch);
	}
	
	/**
	 * Returns IntegerCompution of String lastIndexOf.
	 * 
	 * @param ch a character (Unicode code point).
	 * @param fromIndex the index to start the search from. There is no restriction on the value of fromIndex. If it is greater than or equal to the length of this string, it has the same effect as if it were equal to one less than the length of this string: this entire string may be searched. If it is negative, it has the same effect as if it were -1: -1 is returned.
	 * @return IntegerCompution of String lastIndexOf
	 * @see String#lastIndexOf(int, int)
	 */
	default public IntegerCompution computeLastIndexOf(int ch, int fromIndex) {
		return StringUtils.computeLastIndexOf(this, ch, fromIndex);
	}
	
	/**
	 * Returns BooleanCompution of String equals.
	 * 
	 * @param cs the character sequence
	 * @return BooleanCompution of String equals
	 * @see String#equals(Object)
	 */
	default public BooleanCompution computeIsEqualTo(CharSequence cs) {
		return StringUtils.computeIsEqualTo(this, StringUtils.unmodifiableString(cs));
	}
	
	/**
	 * Returns BooleanCompution of String equals.
	 * 
	 * @param observer the observer
	 * @return BooleanCompution of String equals
	 * @see String#equals(Object)
	 */
	default public BooleanCompution computeIsEqualTo(StringObservable observer) {
		return StringUtils.computeIsEqualTo(this, (observer == null ? StringUtils.getUnmodifiableEmptyString() : observer));
	}
	
	/**
	 * Returns BooleanCompution of NOT String equals.
	 * 
	 * @param cs the character sequence
	 * @return BooleanCompution of NOT String equals
	 * @see String#equals(Object)
	 */
	default public BooleanCompution computeIsNotEqualTo(CharSequence cs) {
		return StringUtils.computeIsNotEqualTo(this, StringUtils.unmodifiableString(cs));
	}
	
	/**
	 * Returns BooleanCompution of NOT String equals.
	 * 
	 * @param observer the observer
	 * @return BooleanCompution of NOT String equals
	 * @see String#equals(Object)
	 */
	default public BooleanCompution computeIsNotEqualTo(StringObservable observer) {
		return StringUtils.computeIsNotEqualTo(this, (observer == null ? StringUtils.getUnmodifiableEmptyString() : observer));
	}
	
	/**
	 * Returns BooleanCompution of {@code String#compareTo < 0}.
	 * 
	 * @param cs the character sequence
	 * @return BooleanCompution of {@code String#compareTo < 0}
	 * @see String#compareTo(String)
	 */
	default public BooleanCompution computeIsLessThan(CharSequence cs) {
		return StringUtils.computeIsLessThan(this, StringUtils.unmodifiableString(cs));
	}
	
	/**
	 * Returns BooleanCompution of {@code String#compareTo < 0}.
	 * 
	 * @param observer the observer
	 * @return BooleanCompution of {@code String#compareTo < 0}
	 * @see String#compareTo(String)
	 */
	default public BooleanCompution computeIsLessThan(StringObservable observer) {
		return StringUtils.computeIsLessThan(this, (observer == null ? StringUtils.getUnmodifiableEmptyString() : observer));
	}
	
	/**
	 * Returns BooleanCompution of {@code String#compareTo <= 0}.
	 * 
	 * @param cs the character sequence
	 * @return BooleanCompution of {@code String#compareTo <= 0}
	 * @see String#compareTo(String)
	 */
	default public BooleanCompution computeIsLessThanOrEqualTo(CharSequence cs) {
		return StringUtils.computeIsLessThanOrEqualTo(this, StringUtils.unmodifiableString(cs));
	}
	
	/**
	 * Returns BooleanCompution of {@code String#compareTo <= 0}.
	 * 
	 * @param observer the observer
	 * @return BooleanCompution of {@code String#compareTo <= 0}
	 * @see String#compareTo(String)
	 */
	default public BooleanCompution computeIsLessThanOrEqualTo(StringObservable observer) {
		return StringUtils.computeIsLessThanOrEqualTo(this, (observer == null ? StringUtils.getUnmodifiableEmptyString() : observer));
	}
	
	/**
	 * Returns BooleanCompution of {@code String#compareTo > 0}.
	 * 
	 * @param cs the character sequence
	 * @return BooleanCompution of {@code String#compareTo > 0}
	 * @see String#compareTo(String)
	 */
	default public BooleanCompution computeIsGreaterThan(CharSequence cs) {
		return StringUtils.computeIsGreaterThan(this, StringUtils.unmodifiableString(cs));
	}
	
	/**
	 * Returns BooleanCompution of {@code String#compareTo > 0}.
	 * 
	 * @param observer the observer
	 * @return BooleanCompution of {@code String#compareTo > 0}
	 * @see String#compareTo(String)
	 */
	default public BooleanCompution computeIsGreaterThan(StringObservable observer) {
		return StringUtils.computeIsGreaterThan(this, (observer == null ? StringUtils.getUnmodifiableEmptyString() : observer));
	}
	
	/**
	 * Returns BooleanCompution of {@code String#compareTo >= 0}.
	 * 
	 * @param cs the character sequence
	 * @return BooleanCompution of {@code String#compareTo >= 0}
	 * @see String#compareTo(String)
	 */
	default public BooleanCompution computeIsGreaterThanOrEqualTo(CharSequence cs) {
		return StringUtils.computeIsGreaterThanOrEqualTo(this, StringUtils.unmodifiableString(cs));
	}
	
	/**
	 * Returns BooleanCompution of {@code String#compareTo >= 0}.
	 * 
	 * @param observer the observer
	 * @return BooleanCompution of {@code String#compareTo >= 0}
	 * @see String#compareTo(String)
	 */
	default public BooleanCompution computeIsGreaterThanOrEqualTo(StringObservable observer) {
		return StringUtils.computeIsGreaterThanOrEqualTo(this, (observer == null ? StringUtils.getUnmodifiableEmptyString() : observer));
	}
	
	/**
	 * Returns BooleanCompution of String equalsIgnoreCase.
	 * 
	 * @param cs the character sequence
	 * @return BooleanCompution of String equalsIgnoreCase
	 * @see String#equalsIgnoreCase(String)
	 */
	default public BooleanCompution computeIsEqualToIgnoreCase(CharSequence cs) {
		return StringUtils.computeIsEqualToIgnoreCase(this, StringUtils.unmodifiableString(cs));
	}
	
	/**
	 * Returns BooleanCompution of String equalsIgnoreCase.
	 * 
	 * @param observer the observer
	 * @return BooleanCompution of String equalsIgnoreCase
	 * @see String#equalsIgnoreCase(String)
	 */
	default public BooleanCompution computeIsEqualToIgnoreCase(StringObservable observer) {
		return StringUtils.computeIsEqualToIgnoreCase(this, (observer == null ? StringUtils.getUnmodifiableEmptyString() : observer));
	}
	
	/**
	 * Returns BooleanCompution of NOT String equalsIgnoreCase.
	 * 
	 * @param cs the character sequence
	 * @return BooleanCompution of NOT String equalsIgnoreCase
	 * @see String#equalsIgnoreCase(String)
	 */
	default public BooleanCompution computeIsNotEqualToIgnoreCase(CharSequence cs) {
		return StringUtils.computeIsNotEqualToIgnoreCase(this, StringUtils.unmodifiableString(cs));
	}
	
	/**
	 * Returns BooleanCompution of NOT String equalsIgnoreCase.
	 * 
	 * @param observer the observer
	 * @return BooleanCompution of NOT String equalsIgnoreCase
	 * @see String#equalsIgnoreCase(String)
	 */
	default public BooleanCompution computeIsNotEqualToIgnoreCase(StringObservable observer) {
		return StringUtils.computeIsNotEqualToIgnoreCase(this, (observer == null ? StringUtils.getUnmodifiableEmptyString() : observer));
	}
	
	/**
	 * Waiting until value is empty.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already value is empty, pass through immediately.<br />
	 * </p>
	 * 
	 * @throws InterruptedException if interrupted while waiting
	 * @see String#isEmpty()
	 */
	default public void waitUntilIsEmpty() throws InterruptedException {
		StringUtils.waitUntil(StringUtils.computeIsEmpty(this), this);
	}
	
	/**
	 * Waiting until value is empty.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already value is empty, pass through immediately.<br />
	 * </p>
	 * 
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see String#isEmpty()
	 */
	default public void waitUntilIsEmpty(long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		StringUtils.waitUntil(StringUtils.computeIsEmpty(this), this, timeout, unit);
	}
	
	/**
	 * Waiting until value is empty.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already value is empty, pass through immediately.<br />
	 * </p>
	 * 
	 * @param p the TimeoutProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see String#isEmpty()
	 */
	default public void waitUntilIsEmpty(TimeoutGettable p) throws InterruptedException, TimeoutException {
		StringUtils.waitUntil(StringUtils.computeIsEmpty(this), this, p);
	}
	
	/**
	 * Waiting until value is NOT empty, and return last value.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already value is empty, return last value immediately.<br />
	 * </p>
	 * 
	 * @return last value
	 * @throws InterruptedException if interrupted while waiting
	 * @see String#isEmpty()
	 */
	default public String waitUntilIsNotEmptyAndGet() throws InterruptedException {
		return StringUtils.waitUntil(StringUtils.computeIsNotEmpty(this), this);
	}
	
	/**
	 * Waiting until value is NOT empty, and return last value.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already value is empty, return last value immediately.<br />
	 * </p>
	 * 
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @return last value
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see String#isEmpty()
	 */
	default public String waitUntilIsNotEmptyAndGet(long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		return StringUtils.waitUntil(StringUtils.computeIsNotEmpty(this), this, timeout, unit);
	}
	
	/**
	 * Waiting until value is NOT empty, and return last value.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already value is empty, return last value immediately.<br />
	 * </p>
	 * 
	 * @param p the TimeoutProperty
	 * @return last value
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see String#isEmpty()
	 */
	default public String waitUntilIsNotEmptyAndGet(TimeoutGettable p) throws InterruptedException, TimeoutException {
		return StringUtils.waitUntil(StringUtils.computeIsNotEmpty(this), this, p);
	}
	
	/**
	 * Waiting until value is contains, and return last value.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already value is contains, return last value immediately.<br />
	 * </p>
	 * 
	 * @param s the sequence to search for
	 * @return last value
	 * @throws InterruptedException if interrupted while waiting
	 * @see String#contains(CharSequence)
	 */
	default public String waitUntilContainsAndGet(CharSequence s) throws InterruptedException {
		return StringUtils.waitUntil(StringUtils.computeContains(this, s), this);
	}
	
	/**
	 * Waiting until value is contains, and return last value.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already value is contains, return last value immediately.<br />
	 * </p>
	 * 
	 * @param s the sequence to search for
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @return last value
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see String#contains(CharSequence)
	 */
	default public String waitUntilContainsAndGet(CharSequence s, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		return StringUtils.waitUntil(StringUtils.computeContains(this, s), this, timeout, unit);
	}
	
	/**
	 * Waiting until value is contains, and return last value.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already value is contains, return last value immediately.<br />
	 * </p>
	 * 
	 * @param s the sequence to search for
	 * @param p the TimeoutProperty
	 * @return last value
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see String#contains(CharSequence)
	 */
	default public String waitUntilContainsAndGet(CharSequence s, TimeoutGettable p) throws InterruptedException, TimeoutException {
		return StringUtils.waitUntil(StringUtils.computeContains(this, s), this, p);
	}
	
	/**
	 * Waiting until value is NOT contains, and return last value.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already value is contains, return last value immediately.<br />
	 * </p>
	 * 
	 * @param s the sequence to search for
	 * @return last value
	 * @throws InterruptedException if interrupted while waiting
	 * @see String#contains(CharSequence)
	 */
	default public String waitUntilNotContainsAndGet(CharSequence s) throws InterruptedException {
		return StringUtils.waitUntil(StringUtils.computeNotContains(this, s), this);
	}
	
	/**
	 * Waiting until value is NOT contains, and return last value.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already value is NOT contains, return last value immediately.<br />
	 * </p>
	 * 
	 * @param s the sequence to search for
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @return last value
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see String#contains(CharSequence)
	 */
	default public String waitUntilNotContainsAndGet(CharSequence s, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		return StringUtils.waitUntil(StringUtils.computeNotContains(this, s), this, timeout, unit);
	}
	
	/**
	 * Waiting until value is NOT contains, and return last value.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already value is NOT contains, return last value immediately.<br />
	 * </p>
	 * 
	 * @param s the sequence to search for
	 * @param p the TimeoutProperty
	 * @return last value
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see String#contains(CharSequence)
	 */
	default public String waitUntilNotContainsAndGet(CharSequence s, TimeoutGettable p) throws InterruptedException, TimeoutException {
		return StringUtils.waitUntil(StringUtils.computeNotContains(this, s), this, p);
	}
	
	/**
	 * Waiting until value is startsWith, and return last value.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already value is startsWith, return last value immediately.<br />
	 * </p>
	 * 
	 * @param prefix the prefix
	 * @return last value
	 * @throws InterruptedException if interrupted while waiting
	 * @see String#startsWith(String)
	 */
	default public String  waitUntilStartsWithAndGet(String prefix) throws InterruptedException {
		return StringUtils.waitUntil(StringUtils.computeStartsWith(this, prefix), this);
	}
	
	/**
	 * Waiting until value is startsWith, and return last value.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already value is startsWith, return last value immediately.<br />
	 * </p>
	 * 
	 * @param prefix the prefix
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @return last value
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see String#startsWith(String)
	 */
	default public String waitUntilStartsWithAndGet(String prefix, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		return StringUtils.waitUntil(StringUtils.computeStartsWith(this, prefix), this, timeout, unit);
	}
	
	/**
	 * Waiting until value is startsWith, and return last value.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already value is startsWith, return last value immediately.<br />
	 * </p>
	 * 
	 * @param prefix the prefix
	 * @param p the TimeoutProperty
	 * @return last value
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see String#startsWith(String)
	 */
	default public String waitUntilStartsWithAndGet(String prefix, TimeoutGettable p) throws InterruptedException, TimeoutException {
		return StringUtils.waitUntil(StringUtils.computeStartsWith(this, prefix), this, p);
	}
	
	/**
	 * Waiting until value is startsWith, and return last value.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already value is startsWith, return last value immediately.<br />
	 * </p>
	 * 
	 * @param prefix the prefix.
	 * @param toOffset where to begin looking in this string.
	 * @return last value
	 * @throws InterruptedException if interrupted while waiting
	 * @see String#startsWith(String, int)
	 */
	default public String waitUntilStartsWithAndGet(String prefix, int toOffset) throws InterruptedException {
		return StringUtils.waitUntil(StringUtils.computeStartsWith(this, prefix, toOffset), this);
	}
	
	/**
	 * Waiting until value is startsWith, and return last value.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already value is startsWith, return last value immediately.<br />
	 * </p>
	 * 
	 * @param prefix the prefix.
	 * @param toOffset where to begin looking in this string.
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @return last value
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see String#startsWith(String, int)
	 */
	default public String waitUntilStartsWithAndGet(String prefix, int toOffset, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		return StringUtils.waitUntil(StringUtils.computeStartsWith(this, prefix, toOffset), this, timeout, unit);
	}
	
	/**
	 * Waiting until value is startsWith, and return last value.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already value is startsWith, return last value immediately.<br />
	 * </p>
	 * 
	 * @param prefix the prefix.
	 * @param toOffset where to begin looking in this string.
	 * @param p the TimeoutProperty
	 * @return last value
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see String#startsWith(String, int)
	 */
	default public String waitUntilStartsWithAndGet(String prefix, int toOffset, TimeoutGettable p) throws InterruptedException, TimeoutException {
		return StringUtils.waitUntil(StringUtils.computeStartsWith(this, prefix, toOffset), this, p);
	}
	
	/**
	 * Waiting until value is endsWith, and return last value.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already value is endsWith, return last value immediately.<br />
	 * </p>
	 * 
	 * @param suffix the suffix
	 * @return last value
	 * @throws InterruptedException if interrupted while waiting
	 * @see String#endsWith(String)
	 */
	default public String waitUntilEndsWithAndGet(String suffix) throws InterruptedException {
		return StringUtils.waitUntil(StringUtils.computeEndsWith(this, suffix), this);
	}
	
	/**
	 * Waiting until value is endsWith, and return last value.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already value is endsWith, return last value immediately.<br />
	 * </p>
	 * 
	 * @param suffix the suffix
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @return last value
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see String#endsWith(String)
	 */
	default public String waitUntilEndsWithAndGet(String suffix, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		return StringUtils.waitUntil(StringUtils.computeEndsWith(this, suffix), this, timeout, unit);
	}
	
	/**
	 * Waiting until value is endsWith, and return last value.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already value is endsWith, return last value immediately.<br />
	 * </p>
	 * 
	 * @param suffix the suffix
	 * @param p the TimeoutProperty
	 * @return last value
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see String#endsWith(String)
	 */
	default public String waitUntilEndsWithAndGet(String suffix, TimeoutGettable p) throws InterruptedException, TimeoutException {
		return StringUtils.waitUntil(StringUtils.computeEndsWith(this, suffix), this, p);
	}
	
	/**
	 * Waiting until value is matches, and return last value.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already value is matches, return last value immediately.<br />
	 * </p>
	 * 
	 * @param regex the regular expression to which this string is to be matched
	 * @return last value
	 * @throws InterruptedException if interrupted while waiting
	 * @see String#matches(String)
	 */
	default public String waitUntilMatchesAndGet(String regex) throws InterruptedException {
		return StringUtils.waitUntil(StringUtils.computeMatches(this, regex), this);
	}
	
	/**
	 * Waiting until value is matches, and return last value.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already value is matches, return last value immediately.<br />
	 * </p>
	 * 
	 * @param regex the regular expression to which this string is to be matched
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @return last value
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see String#matches(String)
	 */
	default public String waitUntilMatchesAndGet(String regex, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		return StringUtils.waitUntil(StringUtils.computeMatches(this, regex), this, timeout, unit);
	}
	
	/**
	 * Waiting until value is matches, and return last value.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already value is matches, return last value immediately.<br />
	 * </p>
	 * 
	 * @param regex the regular expression to which this string is to be matched
	 * @param p the TimeoutProperty
	 * @return last value
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see String#matches(String)
	 */
	default public String waitUntilMatchesAndGet(String regex, TimeoutGettable p) throws InterruptedException, TimeoutException {
		return StringUtils.waitUntil(StringUtils.computeMatches(this, regex), this, p);
	}
	
	/**
	 * Waiting until value is contentEquals, and return last value.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already value is contentEquals, return last value immediately.<br />
	 * </p>
	 * 
	 * @param cs The sequence to compare this String against
	 * @return last value
	 * @throws InterruptedException if interrupted while waiting
	 * @see String#contentEquals(CharSequence)
	 */
	default public String waitUntilContentEqualToAndGet(CharSequence cs) throws InterruptedException {
		return StringUtils.waitUntil(StringUtils.computeContentEqualTo(this, cs), this);
	}
	
	/**
	 * Waiting until value is contentEquals, and return last value.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already value is contentEquals, return last value immediately.<br />
	 * </p>
	 * 
	 * @param cs The sequence to compare this String against
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @return last value
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see String#contentEquals(CharSequence)
	 */
	default public String waitUntilContentEqualToAndGet(CharSequence cs, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		return StringUtils.waitUntil(StringUtils.computeContentEqualTo(this, cs), this, timeout, unit);
	}
	
	/**
	 * Waiting until value is contentEquals, and return last value.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already value is contentEquals, return last value immediately.<br />
	 * </p>
	 * 
	 * @param cs The sequence to compare this String against
	 * @param p the TimeoutProperty
	 * @return last value
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see String#contentEquals(CharSequence)
	 */
	default public String waitUntilContentEqualToAndGet(CharSequence cs, TimeoutGettable p) throws InterruptedException, TimeoutException {
		return StringUtils.waitUntil(StringUtils.computeContentEqualTo(this, cs), this, p);
	}
	
	/**
	 * Waiting until value is contentEquals, and return last value.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already value is contentEquals, return last value immediately.<br />
	 * </p>
	 * 
	 * @param sb The StringBuffer to compare this String against
	 * @return last value
	 * @throws InterruptedException if interrupted while waiting
	 * @see String#contentEquals(StringBuffer)
	 */
	default public String waitUntilContentEqualToAndGet(StringBuffer sb) throws InterruptedException {
		return StringUtils.waitUntil(StringUtils.computeContentEqualTo(this, sb), this);
	}
	
	/**
	 * Waiting until value is contentEquals, and return last value.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already value is contentEquals, return last value immediately.<br />
	 * </p>
	 * 
	 * @param sb The StringBuffer to compare this String against
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @return last value
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see String#contentEquals(StringBuffer)
	 */
	default public String waitUntilContentEqualToAndGet(StringBuffer sb, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		return StringUtils.waitUntil(StringUtils.computeContentEqualTo(this, sb), this, timeout, unit);
	}
	
	/**
	 * Waiting until value is contentEquals, and return last value.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already value is contentEquals, return last value immediately.<br />
	 * </p>
	 * 
	 * @param sb The StringBuffer to compare this String against
	 * @param p the TimeoutProperty
	 * @return last value
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see String#contentEquals(StringBuffer)
	 */
	default public String waitUntilContentEqualToAndGet(StringBuffer sb, TimeoutGettable p) throws InterruptedException, TimeoutException {
		return StringUtils.waitUntil(StringUtils.computeContentEqualTo(this, sb), this, p);
	}
	
	/**
	 * Waiting until value is equal to cs.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param cs the character sequence
	 * @throws InterruptedException if interrupted while waiting
	 * @see String#equals(Object)
	 */
	default public void waitUntilEqualTo(CharSequence cs) throws InterruptedException {
		final StringObservable o = StringUtils.unmodifiableString(cs);
		StringUtils.waitUntil(StringUtils.computeIsEqualTo(this, o), this, o);
	}
	
	/**
	 * Waiting until value is equal to cs.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param cs the character sequence
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see String#equals(Object)
	 */
	default public void waitUntilEqualTo(CharSequence cs, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		final StringObservable o = StringUtils.unmodifiableString(cs);
		StringUtils.waitUntil(StringUtils.computeIsEqualTo(this, o), this, o, timeout, unit);
	}
	
	/**
	 * Waiting until value is equal to cs.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param cs the character sequence
	 * @param p the TimeoutProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see String#equals(Object)
	 */
	default public void waitUntilEqualTo(CharSequence cs, TimeoutGettable p) throws InterruptedException, TimeoutException {
		final StringObservable o = StringUtils.unmodifiableString(cs);
		StringUtils.waitUntil(StringUtils.computeIsEqualTo(this, o), this, o, p);
	}
	
	/**
	 * Waiting until value is equal to observer.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param observer the observer
	 * @throws InterruptedException if interrupted while waiting
	 * @see String#equals(Object)
	 */
	default public void waitUntilEqualTo(StringObservable observer) throws InterruptedException {
		final StringObservable o = observer == null ? StringUtils.getUnmodifiableEmptyString() : observer;
		StringUtils.waitUntil(StringUtils.computeIsEqualTo(this, o), this, o);
	}
	
	/**
	 * Waiting until value is equal to observer.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param observer the observer
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see String#equals(Object)
	 */
	default public void waitUntilEqualTo(StringObservable observer, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		final StringObservable o = observer == null ? StringUtils.getUnmodifiableEmptyString() : observer;
		StringUtils.waitUntil(StringUtils.computeIsEqualTo(this, o), this, o, timeout, unit);
	}
	
	/**
	 * Waiting until value is equal to observer.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param observer the observer
	 * @param p the TimeoutProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see String#equals(Object)
	 */
	default public void waitUntilEqualTo(StringObservable observer, TimeoutGettable p) throws InterruptedException, TimeoutException {
		final StringObservable o = observer == null ? StringUtils.getUnmodifiableEmptyString() : observer;
		StringUtils.waitUntil(StringUtils.computeIsEqualTo(this, o), this, o, p);
	}
	
	/**
	 * Waiting until value is NOT equal to cs.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param cs the character sequence
	 * @throws InterruptedException if interrupted while waiting
	 * @see String#equals(Object)
	 */
	default public void waitUntilNotEqualTo(CharSequence cs) throws InterruptedException {
		final StringObservable o = StringUtils.unmodifiableString(cs);
		StringUtils.waitUntil(StringUtils.computeIsNotEqualTo(this, o), this, o);
	}
	
	/**
	 * Waiting until value is NOT equal to cs.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param cs the character sequence
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see String#equals(Object)
	 */
	default public void waitUntilNotEqualTo(CharSequence cs, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		final StringObservable o = StringUtils.unmodifiableString(cs);
		StringUtils.waitUntil(StringUtils.computeIsNotEqualTo(this, o), this, o, timeout, unit);
	}
	
	/**
	 * Waiting until value is NOT equal to cs.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param cs the character sequence
	 * @param p the TimeoutProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see String#equals(Object)
	 */
	default public void waitUntilNotEqualTo(CharSequence cs, TimeoutGettable p) throws InterruptedException, TimeoutException {
		final StringObservable o = StringUtils.unmodifiableString(cs);
		StringUtils.waitUntil(StringUtils.computeIsNotEqualTo(this, o), this, o, p);
	}
	
	/**
	 * Waiting until value is NOT equal to observer value.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param observer the observer
	 * @throws InterruptedException if interrupted while waiting
	 * @see String#equals(Object)
	 */
	default public void waitUntilNotEqualTo(StringObservable observer) throws InterruptedException {
		final StringObservable o = observer == null ? StringUtils.getUnmodifiableEmptyString() : observer;
		StringUtils.waitUntil(StringUtils.computeIsNotEqualTo(this, o), this, o);
	}
	
	/**
	 * Waiting until value is NOT equal to observer value.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param observer the observer
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see String#equals(Object)
	 */
	default public void waitUntilNotEqualTo(StringObservable observer, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		final StringObservable o = observer == null ? StringUtils.getUnmodifiableEmptyString() : observer;
		StringUtils.waitUntil(StringUtils.computeIsNotEqualTo(this, o), this, o, timeout, unit);
	}
	
	/**
	 * Waiting until value is NOT equal to observer value.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param observer the observer
	 * @param p the TimeoutProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see String#equals(Object)
	 */
	default public void waitUntilNotEqualTo(StringObservable observer, TimeoutGettable p) throws InterruptedException, TimeoutException {
		final StringObservable o = observer == null ? StringUtils.getUnmodifiableEmptyString() : observer;
		StringUtils.waitUntil(StringUtils.computeIsNotEqualTo(this, o), this, o, p);
	}
	
	/**
	 * Waiting until value is {@code compareTo(cs.toString()) < 0}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param cs the Character Sequence
	 * @throws InterruptedException if interrupted while waiting
	 * @see String#compareTo(String)
	 */
	default public void waitUntilLessThan(CharSequence cs) throws InterruptedException {
		final StringObservable o = StringUtils.unmodifiableString(cs);
		StringUtils.waitUntil(StringUtils.computeIsLessThan(this, o), this, o);
	}
	
	/**
	 * Waiting until value is {@code compareTo(cs.toString()) < 0}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param cs the Character Sequence
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see String#compareTo(String)
	 */
	default public void waitUntilLessThan(CharSequence cs, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		final StringObservable o = StringUtils.unmodifiableString(cs);
		StringUtils.waitUntil(StringUtils.computeIsLessThan(this, o), this, o, timeout, unit);
	}
	
	/**
	 * Waiting until value is {@code compareTo(cs.toString()) < 0}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param cs the Character Sequence
	 * @param p the TimeoutProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see String#compareTo(String)
	 */
	default public void waitUntilLessThan(CharSequence cs, TimeoutGettable p) throws InterruptedException, TimeoutException {
		final StringObservable o = StringUtils.unmodifiableString(cs);
		StringUtils.waitUntil(StringUtils.computeIsLessThan(this, o), this, o, p);
	}
	
	/**
	 * Waiting until value is {@code compareTo(observer.toString()) < 0}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param observer the observer
	 * @throws InterruptedException if interrupted while waiting
	 * @see String#compareTo(String)
	 */
	default public void waitUntilLessThan(StringObservable observer) throws InterruptedException {
		final StringObservable o = observer == null ? StringUtils.getUnmodifiableEmptyString() : observer;
		StringUtils.waitUntil(StringUtils.computeIsLessThan(this, o), this, o);
	}
	
	/**
	 * Waiting until value is {@code compareTo(observer.toString()) < 0}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param observer the observer
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see String#compareTo(String)
	 */
	default public void waitUntilLessThan(StringObservable observer, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		final StringObservable o = observer == null ? StringUtils.getUnmodifiableEmptyString() : observer;
		StringUtils.waitUntil(StringUtils.computeIsLessThan(this, o), this, o, timeout, unit);
	}
	
	/**
	 * Waiting until value is {@code compareTo(observer.toString()) < 0}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param observer the observer
	 * @param p the TimeoutProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see String#compareTo(String)
	 */
	default public void waitUntilLessThan(StringObservable observer, TimeoutGettable p) throws InterruptedException, TimeoutException {
		final StringObservable o = observer == null ? StringUtils.getUnmodifiableEmptyString() : observer;
		StringUtils.waitUntil(StringUtils.computeIsLessThan(this, o), this, o, p);
	}
	
	/**
	 * Waiting until value is {@code compareTo(cs.toString()) <= 0}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param cs the Character Sequence
	 * @throws InterruptedException if interrupted while waiting
	 * @see String#compareTo(String)
	 */
	default public void waitUntilLessThanOrEqualTo(CharSequence cs) throws InterruptedException {
		final StringObservable o = StringUtils.unmodifiableString(cs);
		StringUtils.waitUntil(StringUtils.computeIsLessThanOrEqualTo(this, o), this, o);
	}
	
	/**
	 * Waiting until value is {@code compareTo(cs.toString()) <= 0}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param cs the Character Sequence
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see String#compareTo(String)
	 */
	default public void waitUntilLessThanOrEqualTo(CharSequence cs, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		final StringObservable o = StringUtils.unmodifiableString(cs);
		StringUtils.waitUntil(StringUtils.computeIsLessThanOrEqualTo(this, o), this, o, timeout, unit);
	}
	
	/**
	 * Waiting until value is {@code compareTo(cs.toString()) <= 0}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param cs the Character Sequence
	 * @param p the TimeoutProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see String#compareTo(String)
	 */
	default public void waitUntilLessThanOrEqualTo(CharSequence cs, TimeoutGettable p) throws InterruptedException, TimeoutException {
		final StringObservable o = StringUtils.unmodifiableString(cs);
		StringUtils.waitUntil(StringUtils.computeIsLessThanOrEqualTo(this, o), this, o, p);
	}
	
	/**
	 * Waiting until value is {@code compareTo(observer.toString()) <= 0}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param observer the observer
	 * @throws InterruptedException if interrupted while waiting
	 * @see String#compareTo(String)
	 */
	default public void waitUntilLessThanOrEqualTo(StringObservable observer) throws InterruptedException {
		final StringObservable o = observer == null ? StringUtils.getUnmodifiableEmptyString() : observer;
		StringUtils.waitUntil(StringUtils.computeIsLessThanOrEqualTo(this, o), this, o);
	}
	
	/**
	 * Waiting until value is {@code compareTo(observer.toString()) <= 0}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param observer the observer
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see String#compareTo(String)
	 */
	default public void waitUntilLessThanOrEqualTo(StringObservable observer, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		final StringObservable o = observer == null ? StringUtils.getUnmodifiableEmptyString() : observer;
		StringUtils.waitUntil(StringUtils.computeIsLessThanOrEqualTo(this, o), this, o, timeout, unit);
	}
	
	/**
	 * Waiting until value is {@code compareTo(observer.toString()) <= 0}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param observer the observer
	 * @param p the TimeoutProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see String#compareTo(String)
	 */
	default public void waitUntilLessThanOrEqualTo(StringObservable observer, TimeoutGettable p) throws InterruptedException, TimeoutException {
		final StringObservable o = observer == null ? StringUtils.getUnmodifiableEmptyString() : observer;
		StringUtils.waitUntil(StringUtils.computeIsLessThanOrEqualTo(this, o), this, o, p);
	}
	
	/**
	 * Waiting until value is {@code compareTo(cs.toString()) > 0}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param cs the Character Sequence
	 * @throws InterruptedException if interrupted while waiting
	 * @see String#compareTo(String)
	 */
	default public void waitUntilGreaterThan(CharSequence cs) throws InterruptedException {
		final StringObservable o = StringUtils.unmodifiableString(cs);
		StringUtils.waitUntil(StringUtils.computeIsGreaterThan(this, o), this, o);
	}
	
	/**
	 * Waiting until value is {@code compareTo(cs.toString()) > 0}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param cs the Character Sequence
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see String#compareTo(String)
	 */
	default public void waitUntilGreaterThan(CharSequence cs, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		final StringObservable o = StringUtils.unmodifiableString(cs);
		StringUtils.waitUntil(StringUtils.computeIsGreaterThan(this, o), this, o, timeout, unit);
	}
	
	/**
	 * Waiting until value is {@code compareTo(cs.toString()) > 0}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param cs the Character Sequence
	 * @param p the TimeoutProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see String#compareTo(String)
	 */
	default public void waitUntilGreaterThan(CharSequence cs, TimeoutGettable p) throws InterruptedException, TimeoutException {
		final StringObservable o = StringUtils.unmodifiableString(cs);
		StringUtils.waitUntil(StringUtils.computeIsGreaterThan(this, o), this, o, p);
	}
	
	/**
	 * Waiting until value is {@code compareTo(observer.toString()) > 0}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param observer the observer
	 * @throws InterruptedException if interrupted while waiting
	 * @see String#compareTo(String)
	 */
	default public void waitUntilGreaterThan(StringObservable observer) throws InterruptedException {
		final StringObservable o = observer == null ? StringUtils.getUnmodifiableEmptyString() : observer;
		StringUtils.waitUntil(StringUtils.computeIsGreaterThan(this, o), this, o);
	}
	
	/**
	 * Waiting until value is {@code compareTo(observer.toString()) > 0}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param observer the observer
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see String#compareTo(String)
	 */
	default public void waitUntilGreaterThan(StringObservable observer, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		final StringObservable o = observer == null ? StringUtils.getUnmodifiableEmptyString() : observer;
		StringUtils.waitUntil(StringUtils.computeIsGreaterThan(this, o), this, o, timeout, unit);
	}
	
	/**
	 * Waiting until value is {@code compareTo(observer.toString()) > 0}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param observer the observer
	 * @param p the TimeoutProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see String#compareTo(String)
	 */
	default public void waitUntilGreaterThan(StringObservable observer, TimeoutGettable p) throws InterruptedException, TimeoutException {
		final StringObservable o = observer == null ? StringUtils.getUnmodifiableEmptyString() : observer;
		StringUtils.waitUntil(StringUtils.computeIsGreaterThan(this, o), this, o, p);
	}
	
	/**
	 * Waiting until value is {@code compareTo(cs.toString()) >= 0}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param cs the Character Sequence
	 * @throws InterruptedException if interrupted while waiting
	 * @see String#compareTo(String)
	 */
	default public void waitUntilGreaterThanOrEqualTo(CharSequence cs) throws InterruptedException {
		final StringObservable o = StringUtils.unmodifiableString(cs);
		StringUtils.waitUntil(StringUtils.computeIsGreaterThanOrEqualTo(this, o), this, o);
	}
	
	/**
	 * Waiting until value is {@code compareTo(cs.toString()) >= 0}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param cs the Character Sequence
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see String#compareTo(String)
	 */
	default public void waitUntilGreaterThanOrEqualTo(CharSequence cs, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		final StringObservable o = StringUtils.unmodifiableString(cs);
		StringUtils.waitUntil(StringUtils.computeIsGreaterThanOrEqualTo(this, o), this, o, timeout, unit);
	}
	
	/**
	 * Waiting until value is {@code compareTo(cs.toString()) >= 0}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param cs the Character Sequence
	 * @param p the TimeoutProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see String#compareTo(String)
	 */
	default public void waitUntilGreaterThanOrEqualTo(CharSequence cs, TimeoutGettable p) throws InterruptedException, TimeoutException {
		final StringObservable o = StringUtils.unmodifiableString(cs);
		StringUtils.waitUntil(StringUtils.computeIsGreaterThanOrEqualTo(this, o), this, o, p);
	}
	
	/**
	 * Waiting until value is {@code compareTo(observer.toString()) >= 0}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param observer the observer
	 * @throws InterruptedException if interrupted while waiting
	 * @see String#compareTo(String)
	 */
	default public void waitUntilGreaterThanOrEqualTo(StringObservable observer) throws InterruptedException {
		final StringObservable o = observer == null ? StringUtils.getUnmodifiableEmptyString() : observer;
		StringUtils.waitUntil(StringUtils.computeIsGreaterThanOrEqualTo(this, o), this, o);
	}
	
	/**
	 * Waiting until value is {@code compareTo(observer.toString()) >= 0}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param observer the observer
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see String#compareTo(String)
	 */
	default public void waitUntilGreaterThanOrEqualTo(StringObservable observer, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		final StringObservable o = observer == null ? StringUtils.getUnmodifiableEmptyString() : observer;
		StringUtils.waitUntil(StringUtils.computeIsGreaterThanOrEqualTo(this, o), this, o, timeout, unit);
	}
	
	/**
	 * Waiting until value is {@code compareTo(observer.toString()) >= 0}.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param observer the observer
	 * @param p the TimeoutProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see String#compareTo(String)
	 */
	default public void waitUntilGreaterThanOrEqualTo(StringObservable observer, TimeoutGettable p) throws InterruptedException, TimeoutException {
		final StringObservable o = observer == null ? StringUtils.getUnmodifiableEmptyString() : observer;
		StringUtils.waitUntil(StringUtils.computeIsGreaterThanOrEqualTo(this, o), this, o, p);
	}
	
	/**
	 * Waiting until value is equalsIgnoreCase.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param cs The Character Sequence to compare this String against
	 * @throws InterruptedException if interrupted while waiting
	 * @see String#equalsIgnoreCase(String)
	 */
	default public void waitUntilEqualToIgnoreCase(CharSequence cs) throws InterruptedException {
		final StringObservable o = StringUtils.unmodifiableString(cs);
		StringUtils.waitUntil(StringUtils.computeIsEqualToIgnoreCase(this, o), this, o);
	}
	
	/**
	 * Waiting until value is equalsIgnoreCase.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param cs The Character Sequence to compare this String against
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see String#equalsIgnoreCase(String)
	 */
	default public void waitUntilEqualToIgnoreCase(CharSequence cs, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		final StringObservable o = StringUtils.unmodifiableString(cs);
		StringUtils.waitUntil(StringUtils.computeIsEqualToIgnoreCase(this, o), this, o, timeout, unit);
	}
	
	/**
	 * Waiting until value is equalsIgnoreCase.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param cs The Character Sequence to compare this String against
	 * @param p the TimeoutProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see String#equalsIgnoreCase(String)
	 */
	default public void waitUntilEqualToIgnoreCase(CharSequence cs, TimeoutGettable p) throws InterruptedException, TimeoutException {
		final StringObservable o = StringUtils.unmodifiableString(cs);
		StringUtils.waitUntil(StringUtils.computeIsEqualToIgnoreCase(this, o), this, o, p);
	}
	
	/**
	 * Waiting until value is equalsIgnoreCase.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param observer The observer to compare this String against
	 * @throws InterruptedException if interrupted while waiting
	 * @see String#equalsIgnoreCase(String)
	 */
	default public void waitUntilEqualToIgnoreCase(StringObservable observer) throws InterruptedException {
		final StringObservable o = observer == null ? StringUtils.getUnmodifiableEmptyString() : observer;
		StringUtils.waitUntil(StringUtils.computeIsEqualToIgnoreCase(this, o), this, o);
	}
	
	/**
	 * Waiting until value is equalsIgnoreCase.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param observer The observer to compare this String against
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see String#equalsIgnoreCase(String)
	 */
	default public void waitUntilEqualToIgnoreCase(StringObservable observer, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		final StringObservable o = observer == null ? StringUtils.getUnmodifiableEmptyString() : observer;
		StringUtils.waitUntil(StringUtils.computeIsEqualToIgnoreCase(this, o), this, o, timeout, unit);
	}
	
	/**
	 * Waiting until value is equalsIgnoreCase.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param observer The observer to compare this String against
	 * @param p the TimeoutProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see String#equalsIgnoreCase(String)
	 */
	default public void waitUntilEqualToIgnoreCase(StringObservable observer, TimeoutGettable p) throws InterruptedException, TimeoutException {
		final StringObservable o = observer == null ? StringUtils.getUnmodifiableEmptyString() : observer;
		StringUtils.waitUntil(StringUtils.computeIsEqualToIgnoreCase(this, o), this, o, p);
	}
	
	/**
	 * Waiting until value is NOT equalsIgnoreCase.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param cs The Character Sequence to compare this String against
	 * @throws InterruptedException if interrupted while waiting
	 * @see String#equalsIgnoreCase(String)
	 */
	default public void waitUntilNotEqualToIgnoreCase(CharSequence cs) throws InterruptedException {
		final StringObservable o = StringUtils.unmodifiableString(cs);
		StringUtils.waitUntil(StringUtils.computeIsNotEqualToIgnoreCase(this, o), this, o);
	}
	
	/**
	 * Waiting until value is NOT equalsIgnoreCase.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param cs The Character Sequence to compare this String against
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see String#equalsIgnoreCase(String)
	 */
	default public void waitUntilNotEqualToIgnoreCase(CharSequence cs, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		final StringObservable o = StringUtils.unmodifiableString(cs);
		StringUtils.waitUntil(StringUtils.computeIsNotEqualToIgnoreCase(this, o), this, o, timeout, unit);
	}
	
	/**
	 * Waiting until value is NOT equalsIgnoreCase.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param cs The Character Sequence to compare this String against
	 * @param p the TimeoutProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see String#equalsIgnoreCase(String)
	 */
	default public void waitUntilNotEqualToIgnoreCase(CharSequence cs, TimeoutGettable p) throws InterruptedException, TimeoutException {
		final StringObservable o = StringUtils.unmodifiableString(cs);
		StringUtils.waitUntil(StringUtils.computeIsNotEqualToIgnoreCase(this, o), this, o, p);
	}
	
	/**
	 * Waiting until value is NOT equalsIgnoreCase.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param observer The observer to compare this String against
	 * @throws InterruptedException if interrupted while waiting
	 * @see String#equalsIgnoreCase(String)
	 */
	default public void waitUntilNotEqualToIgnoreCase(StringObservable observer) throws InterruptedException {
		final StringObservable o = observer == null ? StringUtils.getUnmodifiableEmptyString() : observer;
		StringUtils.waitUntil(StringUtils.computeIsNotEqualToIgnoreCase(this, o), this, o);
	}
	
	/**
	 * Waiting until value is NOT equalsIgnoreCase.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param observer The observer to compare this String against
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see String#equalsIgnoreCase(String)
	 */
	default public void waitUntilNotEqualToIgnoreCase(StringObservable observer, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		final StringObservable o = observer == null ? StringUtils.getUnmodifiableEmptyString() : observer;
		StringUtils.waitUntil(StringUtils.computeIsNotEqualToIgnoreCase(this, o), this, o, timeout, unit);
	}
	
	/**
	 * Waiting until value is NOT equalsIgnoreCase.
	 * 
	 * <p>
	 * This is blocking method.<br />
	 * If already condition is true, pass through immediately.<br />
	 * </p>
	 * 
	 * @param observer The observer to compare this String against
	 * @param p the TimeoutProperty
	 * @throws InterruptedException if interrupted while waiting
	 * @throws TimeoutException if the wait timed out
	 * @see String#equalsIgnoreCase(String)
	 */
	default public void waitUntilNotEqualToIgnoreCase(StringObservable observer, TimeoutGettable p) throws InterruptedException, TimeoutException {
		final StringObservable o = observer == null ? StringUtils.getUnmodifiableEmptyString() : observer;
		StringUtils.waitUntil(StringUtils.computeIsNotEqualToIgnoreCase(this, o), this, o, p);
	}
	
}
