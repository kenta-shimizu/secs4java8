package com.shimizukenta.secs.hsms;

import java.util.Objects;

import com.shimizukenta.secs.AbstractSecsCommunicatorConfig;
import com.shimizukenta.secs.Property;
import com.shimizukenta.secs.ReadOnlyProperty;
import com.shimizukenta.secs.ReadOnlyTimeProperty;
import com.shimizukenta.secs.TimeProperty;

/**
 * This class is config of HSMS-SS-Communicator.
 * 
 * <p>
 * To set Active or Passive mode, {@link #connectionMode(HsmsConnectionMode)}<br />
 * </p>
 * 
 * @author kenta-shimizu
 *
 */
public abstract class AbstractHsmsCommunicatorConfig extends AbstractSecsCommunicatorConfig {
	
	private static final long serialVersionUID = 3767856791428188426L;
	
	private final Property<HsmsConnectionMode> connectionMode = Property.newInstance(HsmsConnectionMode.PASSIVE);
	private final TimeProperty linktest = TimeProperty.newInstance(-1.0F);
	private final TimeProperty rebindIfPassive = TimeProperty.newInstance(10.0F);
	
	public AbstractHsmsCommunicatorConfig() {
		super();
	}
	
	/**
	 * ACTIVE or PASSIVE Connection-Mode setter
	 * 
	 * @param mode
	 */
	public void connectionMode(HsmsConnectionMode mode) {
		this.connectionMode.set(Objects.requireNonNull(mode));
	}
	
	/**
	 * Connection-Mode getter
	 * 
	 * @return connection-mode
	 */
	public ReadOnlyProperty<HsmsConnectionMode> connectionMode() {
		return this.connectionMode;
	}
	
	/*
	 * Set Not-Linktest
	 * 
	 */
	public void notLinktest() {
		this.linktest.set(-1.0F);
	}
	
	/**
	 * Linktest cycle time setter
	 * 
	 * @param v linktest-cycle-seconds. value is {@code >= 0}
	 */
	public void linktest(float v) {
		if ( v < 0.0F ) {
			throw new IllegalArgumentException("linktest value is >= 0.0F");
		}
		this.linktest.set(v);
	}
	
	/**
	 * Linktest cycle getter
	 * 
	 * @return seconds. Not-linktest if {@code <0}
	 */
	public ReadOnlyTimeProperty linktest() {
		return linktest;
	}
	
	/**
	 * Set not rebind if Passive-protocol
	 * 
	 */
	public void notRebindIfPassive() {
		this.rebindIfPassive.set(-1.0F);
	}
	
	/**
	 * Rebind if Passive-Protocol, if bind failed, then rebind after this time.
	 * 
	 * @param v rebind after this time if Passive-protocol. value {@code >=0}
	 */
	public void rebindIfPassive(float v) {
		if ( v < 0.0F ) {
			throw new IllegalArgumentException("rebindIfPassive value is >= 0.0F");
		}
		this.rebindIfPassive.set(v);
	}
	
	/**
	 * rebind time getter.
	 * 
	 * @return seconds. Not rebind if {@code <0}
	 */
	public ReadOnlyTimeProperty rebindIfPassive() {
		return rebindIfPassive;
	}
	
}
