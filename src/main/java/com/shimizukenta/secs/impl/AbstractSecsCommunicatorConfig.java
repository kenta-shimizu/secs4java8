package com.shimizukenta.secs.impl;

import java.io.Serializable;
import java.util.Objects;

import com.shimizukenta.secs.SecsTimeout;
import com.shimizukenta.secs.gem.AbstractGemConfig;
import com.shimizukenta.secs.local.property.BooleanProperty;
import com.shimizukenta.secs.local.property.StringProperty;

/**
 * This abstract class is implementation of communicate config.
 * 
 * <ul>
 * <li>To set is-equip, {@link #isEquip(boolean)}</li>
 * <li>To set timeouts, {@link #timeout()}</li>
 * <li>To set gem config, {@link #gem()}</li>
 * <li>To set log-subject-header, {@link #logSubjectHeader(CharSequence)}</li>
 * </ul>
 * 
 * @author kenta-shimizu
 *
 */
public abstract class AbstractSecsCommunicatorConfig implements Serializable {
	
	private static final long serialVersionUID = -8456991094606676409L;
	
	private final SecsTimeout timeout = new SecsTimeoutImpl();
	private final BooleanProperty isEquip = BooleanProperty.newInstance(false);
	
	private final AbstractGemConfig gem = new AbstractGemConfig() {
		
		private static final long serialVersionUID = -3386783271396322749L;
	};
	
	private final StringProperty name = StringProperty.newInstance("");
	private final StringProperty logSubjectHeader = StringProperty.newInstance("");
	
	public AbstractSecsCommunicatorConfig() {
		/* Nothing */
	}
	
	/**
	 * Set true if equipment.
	 * 
	 * @param f set {@code true} if equipment
	 */
	public void isEquip(boolean f) {
		this.isEquip.set(f);
	}
	
	/**
	 * Returns Is-Equipment-Property.
	 * 
	 * @return Is-Equipment-Property
	 */
	public BooleanProperty isEquip() {
		return isEquip;
	}
	
	/**
	 * SecsTimeout getter
	 * 
	 * @return SecsTimeout
	 */
	public SecsTimeout timeout() {
		return timeout;
	}
	
	/**
	 * AbstractGemConfig getter.
	 * 
	 * @return AbstractGemConfig
	 */
	public AbstractGemConfig gem() {
		return gem;
	}
	
	/**
	 * Communicator-Name setter.
	 * 
	 * @param name Communicator Name
	 */
	public void name(CharSequence name) {
		this.name.set(Objects.requireNonNull(name).toString());
	}
	
	/**
	 * Returns Communicaotor-Name property.
	 * 
	 * @return Communicator-Name-Property
	 */
	public StringProperty name() {
		return name;
	}
	
	/**
	 * if setted, insert header to subject of SecsLog
	 * 
	 * @param header insert header of SecsLog
	 */
	public void logSubjectHeader(CharSequence header) {
		this.logSubjectHeader.set(Objects.requireNonNull(header).toString());
	}
	
	/**
	 * Returns Log-Subject-Header getter-property
	 * 
	 * @return log-subject-header-property
	 */
	public StringProperty logSubjectHeader() {
		return logSubjectHeader;
	}
	
}
