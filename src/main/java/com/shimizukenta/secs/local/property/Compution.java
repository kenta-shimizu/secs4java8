package com.shimizukenta.secs.local.property;

import java.io.Serializable;

/**
 * Super Compution interface, includes Getter and Observer.
 * 
 * <p>
 * <strong>NOT</strong> includes Setter.<br />
 * </p>
 * 
 * @author kenta-shimizu
 *
 * @param <T> Type
 * @see Gettable
 * @see Observable
 * 
 * 
 */
public interface Compution<T> extends Gettable<T>, Observable<T>, Serializable {

}
