/**
 * Provides 'Property' intarfaces. this is similar to JavaFX 'javafx.beans.property'.
 * 
 * <p>
 * Includes Setter/Getter/Observer, Number/Comparative/Logical compution and blocking methods to wait until condition is true.<br />
 * </p>
 * <ul>
 * <li>Property
 * <ul>
 * <li>{@link BooleanProperty}</li>
 * <li>{@link IntegerProperty}</li>
 * <li>{@link LongProperty}</li>
 * <li>{@link FloatProperty}</li>
 * <li>{@link DoubleProperty}</li>
 * <li>{@link ObjectProperty}</li>
 * <li>{@link StringProperty}</li>
 * <li>{@link ListProperty}</li>
 * <li>{@link SetProperty}</li>
 * <li>{@link MapProperty}</li>
 * <li>{@link TimeoutProperty}</li>
 * </ul>
 * </li>
 * <li>Compution
 * <ul>
 * <li>{@link NumberCompution}</li>
 * <li>{@link ComparativeCompution}</li>
 * <li>{@link LogicalCompution}</li>
 * </ul>
 * </li>
 * </ul>
 * <ul>
 * <li>To build new instance, #newInstance</li>
 * <li>To set value, #set</li>
 * <li>To get value, #get</li>
 * <li>To detect value changed, #addChangeListener</li>
 * <li>To compute value, #compute... methods</li>
 * <li>To block until condition is true, #waitUntil... methods</li>
 * </ul>
 * 
 * @author kenta-shimizu
 *
 */
package com.shimizukenta.secs.local.property;
