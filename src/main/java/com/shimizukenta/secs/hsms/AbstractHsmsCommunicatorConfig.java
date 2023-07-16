package com.shimizukenta.secs.hsms;

import java.util.Objects;

import com.shimizukenta.secs.AbstractSecsCommunicatorConfig;
import com.shimizukenta.secs.local.property.BooleanProperty;
import com.shimizukenta.secs.local.property.ObjectProperty;
import com.shimizukenta.secs.local.property.TimeoutProperty;

/**
 * This class is config of HSMS-Communicator.
 * 
 * <ul>
 * <li>To set Active or Passive mode, {@link #connectionMode(HsmsConnectionMode)}</li>
 * <li>To set linktest-cycle-time. {@link #linktest(float)}</li>
 * <li>To set <strong>NOT</strong> linktest, {@link #notLinktest()}</li>
 * </ul>
 * 
 * @author kenta-shimizu
 *
 */
public abstract class AbstractHsmsCommunicatorConfig extends AbstractSecsCommunicatorConfig {
	
	private static final long serialVersionUID = 2402150927485521447L;
	
	/**
	 * Connection Mode.
	 * 
	 */
	private final ObjectProperty<HsmsConnectionMode> connectionMode = ObjectProperty.newInstance(HsmsConnectionMode.PASSIVE);
	
	/**
	 * Sync-Object of linktest.
	 * 
	 */
	private final Object syncLinktest = new Object();
	
	/**
	 * Linktest Timeout.
	 * 
	 */
	private final TimeoutProperty linktestTime = TimeoutProperty.newInstance(120.0F);
	
	/**
	 * doLinktest.
	 * 
	 */
	private final BooleanProperty doLinktest = BooleanProperty.newInstance(false);
	
	/**
	 * Sync-Object of RebindIfPassive.
	 * 
	 */
	private final Object syncRebindIfPassive = new Object();
	
	/**
	 * RebindIfPassive timeout.
	 * 
	 */
	private final TimeoutProperty rebindIfPassiveTime = TimeoutProperty.newInstance(10.0F);
	
	/**
	 * doRebindIfPassive.
	 * 
	 */
	private final BooleanProperty doRebindIfPassive = BooleanProperty.newInstance(true);
	
	/**
	 * Constructor.
	 * 
	 */
	protected AbstractHsmsCommunicatorConfig() {
		super();
	}
	
	/**
	 * ACTIVE or PASSIVE Connection-Mode setter
	 * 
	 * @param mode the HSMS-Connection-Mode
	 */
	public void connectionMode(HsmsConnectionMode mode) {
		this.connectionMode.set(Objects.requireNonNull(mode));
	}
	
	/**
	 * Returns Connection-Mode property.
	 * 
	 * @return connection-mode property
	 */
	public ObjectProperty<HsmsConnectionMode> connectionMode() {
		return this.connectionMode;
	}
	
	/**
	 * Set Not-Linktest
	 * 
	 */
	public void notLinktest() {
		synchronized ( this.syncLinktest ) {
			this.doLinktest.setFalse();
		}
	}
	
	/**
	 * Linktest cycle time setter.
	 * 
	 * @param seconds linktest-cycle-seconds. value is {@code >= 0}
	 */
	public void linktest(float seconds) {
		synchronized ( this.syncLinktest ) {
			this.linktestTime.set(seconds);
			this.doLinktest.setTrue();
		}
	}
	
	/**
	 * Returns Linktest cycle TimeProperty.
	 * 
	 * @return Linktest cycle TimeProperty
	 */
	public TimeoutProperty linktestTime() {
		return this.linktestTime;
	}
	
	/**
	 * Returns do-linktest-property.
	 * 
	 * @return do-linktest-property
	 */
	public BooleanProperty doLinkTest() {
		return this.doLinktest;
	}
	
	/**
	 * Set not rebind if Passive-protocol
	 * 
	 */
	public void notRebindIfPassive() {
		synchronized ( this.syncRebindIfPassive ) {
			this.doRebindIfPassive.setFalse();
		}
	}
	
	/**
	 * Rebind if Passive-Protocol, if bind failed, then rebind after this time.
	 * 
	 * @param seconds rebind after this time if Passive-protocol. value {@code >=0}
	 */
	public void rebindIfPassive(float seconds) {
		synchronized ( this.syncRebindIfPassive ) {
			this.rebindIfPassiveTime.set(seconds);
			this.doRebindIfPassive.setTrue();
		}
	}
	
	/**
	 * Returns rebind time TimeProperty.
	 * 
	 * @return Rebind-Time-Property
	 */
	public TimeoutProperty rebindIfPassiveTime() {
		return this.rebindIfPassiveTime;
	}
	
	/**
	 * Returns do-rebind-if-passive property.
	 * 
	 * @return do-rebind-if-passive property
	 */
	public BooleanProperty doRebindIfPassive() {
		return this.doRebindIfPassive;
	}
	
}
