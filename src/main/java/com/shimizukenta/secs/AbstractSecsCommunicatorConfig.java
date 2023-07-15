package com.shimizukenta.secs;

import java.io.Serializable;

import com.shimizukenta.secs.gem.GemConfig;
import com.shimizukenta.secs.gem.impl.AbstractGemConfig;
import com.shimizukenta.secs.impl.SecsTimeoutImpl;
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
	
	/**
	 * Timeout.
	 */
	private final SecsTimeout timeout = new SecsTimeoutImpl();
	
	/**
	 * isEquip.
	 */
	private final BooleanProperty isEquip = BooleanProperty.newInstance(false);
	
	/**
	 * GemCOnfig.
	 * 
	 */
	private final GemConfig gem = new AbstractGemConfig() {
		
		private static final long serialVersionUID = -3386783271396322749L;
	};
	
	/**
	 * name.
	 */
	private final StringProperty name = StringProperty.newInstance("");
	
	/**
	 * logSubjectHeader.
	 */
	private final StringProperty logSubjectHeader = StringProperty.newInstance("");
	
	/**
	 * Constructor.
	 * 
	 */
	protected AbstractSecsCommunicatorConfig() {
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
	 * GemConfig getter.
	 * 
	 * @return GemConfig
	 */
	public GemConfig gem() {
		return gem;
	}
	
	/**
	 * Communicator-Name setter.
	 * 
	 * @param name Communicator Name
	 */
	public void name(CharSequence name) {
		this.name.set(name);
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
		this.logSubjectHeader.set(header);
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
