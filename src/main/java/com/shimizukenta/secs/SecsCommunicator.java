package com.shimizukenta.secs;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.shimizukenta.secs.gem.Gem;
import com.shimizukenta.secs.hsmsss.HsmsSsCommunicator;
import com.shimizukenta.secs.hsmsss.HsmsSsCommunicatorConfig;
import com.shimizukenta.secs.secs1ontcpip.Secs1OnTcpIpCommunicator;
import com.shimizukenta.secs.secs1ontcpip.Secs1OnTcpIpCommunicatorConfig;

/**
 * This interface is implementation of SECS-Communicating, open/close communicating, receive/send SECS-Message.
 * 
 * <ul>
 * <li>To create HSMS-SS-Communicator instance,
 * {@link HsmsSsCommunicator#newInstance(HsmsSsCommunicatorConfig)}</li>
 * <li>To create SECS-I-on-TCP/IP-Communicator instance,
 * {@link Secs1OnTcpIpCommunicator#newInstance(Secs1OnTcpIpCommunicatorConfig)}</li>
 * </ul>
 * <ul>
 * <li>To open communicating,
 * {@link #open()}</li>
 * <li>To close communicating,
 * {@link #close()}</li>
 * </ul>
 * <ul>
 * <li>To receive Primary-Message,
 * {@link #addSecsMessageReceiveListener(SecsMessageReceiveListener)}
 * or {@link #addSecsMessageReceiveListener(SecsMessageReceiveBiListener)}</li>
 * </ul>
 * <ul>
 * <li>To get communicate-state-changed,
 * {@link #addSecsCommunicatableStateChangeListener(SecsCommunicatableStateChangeListener)}
 * or {@link #addSecsCommunicatableStateChangeListener(SecsCommunicatableStateChangeBiListener)}</li>
 * <li>To wait until communicatable, {@link #waitUntilCommunicatable()}</li>
 * <li>To wait until not communicatable, {@link #waitUntilNotCommunicatable()}</li>
 * </ul>
 * <ul>
 * <li>To access GEM-interface, {@link #gem()}</li>
 * </ul>
 * <ul>
 * <li>To log communicating,
 * {@link #addSecsLogListener(SecsLogListener)}</li>
 * </ul>
 * 
 * @author kenta-shimizu
 *
 */
public interface SecsCommunicator extends OpenAndCloseable, SecsMessageSendable {
	
	/**
	 * Returns GEM-interface.
	 * 
	 * @return GEM-interface-instance
	 */
	public Gem gem();
	
	
	/**
	 * Returns true if communicatable.
	 * 
	 * <p>
	 * Communicatable is send and receive message.
	 * </p>
	 * 
	 * @return true if communicatable
	 */
	public boolean isCommunicatable();
	
	/**
	 * Wait until communicatable.
	 * 
	 * <p>
	 * Blocking-method.<br />
	 * If Already communicatable, do nothing.<br />
	 * </p>
	 * 
	 * @throws InterruptedException if interrupted
	 */
	public void waitUntilCommunicatable() throws InterruptedException;
	
	/**
	 * Wait until communicatable.
	 * 
	 * <p>
	 * Blocking-method.<br />
	 * If Already communicatable, do nothing.<br />
	 * </p>
	 * 
	 * @param timeout the timeout value
	 * @param unit the timeout unit
	 * @throws InterruptedException if interrupted
	 * @throws TimeoutException if timeout
	 */
	public void waitUntilCommunicatable(long timeout, TimeUnit unit) throws InterruptedException, TimeoutException;
	
	/**
	 * Wait until <strong>NOT</strong> communicatable.
	 * 
	 * <p>
	 * Blocking-method.<br />
	 * If Already not communicatable, do nothing.<br />
	 * </p>
	 * 
	 * @throws InterruptedException if interrupted
	 */
	public void waitUntilNotCommunicatable() throws InterruptedException;
	
	/**
	 * Wait until <strong>NOT</strong> communicatable.
	 * 
	 * <p>
	 * Blocking-method.<br />
	 * If Already not communicatable, do nothing.<br />
	 * </p>
	 * 
	 * @param timeout the timeout value
	 * @param unit the timeout unit
	 * @throws InterruptedException if interrupted
	 * @throws TimeoutException if timeout
	 */
	public void waitUntilNotCommunicatable(long timeout, TimeUnit unit) throws InterruptedException, TimeoutException;
	
	/**
	 * Open and wait until communicatable.
	 * 
	 * <p>
	 * Blocking-method.<br />
	 * If Already opened, do nothing.<br />
	 * If Already communicatable, do nothing.<br />
	 * </p>
	 * 
	 * @throws IOException if open failed
	 * @throws InterruptedException if interrupted
	 */
	public void openAndWaitUntilCommunicatable() throws IOException, InterruptedException;
	
	/**
	 * Open and wait until communicatable.
	 * 
	 * <p>
	 * Blocking-method.<br />
	 * If Already opened, do nothing.<br />
	 * If Already communicatable, do nothing.<br />
	 * </p>
	 * 
	 * @param timeout the timeout value
	 * @param unit the timeout unit
	 * @throws IOException if open failed
	 * @throws InterruptedException if interrupted
	 * @throws TimeoutException if timeout
	 */
	public void openAndWaitUntilCommunicatable(long timeout, TimeUnit unit) throws IOException, InterruptedException, TimeoutException;
	
	/**
	 * Is equipment getter.
	 * 
	 * @return {@code true} if communicator is equipment.
	 */
	public boolean isEquip();
	
	/**
	 * Returns Communicator DEVICE-ID.
	 * 
	 * <p>
	 * Returns -1 if not support DEVICE-ID communicator.<br />
	 * </p>
	 * 
	 * @return DEVICE-ID
	 */
	public int deviceId();
	
	/**
	 * Returns Communicator SESSION-ID.
	 * 
	 * <p>
	 * Returns {@code -1} if not support SESSION-ID communicator.<br />
	 * </p>
	 * 
	 * @return SESSION-ID
	 */
	public int sessionId();
	
	
	
	/* Secs-Message Receive Listener */
	
	/**
	 * Add Listener to receive Primary-Message.
	 * 
	 * <p>
	 * This Listener not receive Reply-Message.<br />
	 * </p>
	 * 
	 * @param listener Not accept {@code null}
	 * @return {@code true} if add success
	 */
	public boolean addSecsMessageReceiveListener(SecsMessageReceiveListener listener);
	
	/**
	 * Remove Listener.
	 * 
	 * @param listener Not accept {@code null}
	 * @return {@code true} if remove success
	 */
	public boolean removeSecsMessageReceiveListener(SecsMessageReceiveListener listener);
	
	
	/**
	 * Add Listener to receive Primary-Message.
	 * 
	 * <p>
	 * This Listener not receive Reply-Message.<br />
	 * </p>
	 * 
	 * @param listener Not accept {@code null}
	 * @return {@code true} if add success
	 */
	@Deprecated
	default public boolean addSecsMessageReceiveListener(SecsMessageReceiveBiListener listener) {
		return this.addSecsMessageReceiveBiListener(listener);
	}
	
	/**
	 * Remove Listener.
	 * 
	 * @param listener Not accept {@code null}
	 * @return {@code true} if remove success
	 */
	@Deprecated
	default public boolean removeSecsMessageReceiveListener(SecsMessageReceiveBiListener listener) {
		return this.removeSecsMessageReceiveBiListener(listener);
	}
	
	/**
	 * Add Listener to receive Primary-Message.
	 * 
	 * <p>
	 * This Listener not receive Reply-Message.<br />
	 * </p>
	 * 
	 * @param biListener Not accept {@code null}
	 * @return {@code true} if add success
	 */
	public boolean addSecsMessageReceiveBiListener(SecsMessageReceiveBiListener biListener);
	
	/**
	 * Remove Listener.
	 * 
	 * @param biListener Not accept {@code null}
	 * @return {@code true} if remove success
	 */
	public boolean removeSecsMessageReceiveBiListener(SecsMessageReceiveBiListener biListener);
	
	
	/* Secs-Log Receive Listener */
	
	/**
	 * Add Listener to log Communicating.
	 * 
	 * @param listener Not accept {@code null}
	 * @return {@code true} if add success
	 */
	public boolean addSecsLogListener(SecsLogListener listener);
	
	/**
	 * Remove Listener
	 * 
	 * @param listener Not accept {@code null}
	 * @return {@code true} if remove success
	 */
	public boolean removeSecsLogListener(SecsLogListener listener);
	
	
	/* Secs-Communicatable-State-Changed-Listener */
	
	/**
	 * Add Listener to get communicate-state-changed.
	 * 
	 * <p>
	 * Blocking-Listener.<br />
	 * Pass through quickly.<br />
	 * </p>
	 * 
	 * @param listener Not accept {@code null}
	 * @return {@code true} if add success
	 */
	public boolean addSecsCommunicatableStateChangeListener(SecsCommunicatableStateChangeListener listener);
	
	/**
	 * Remove Listener.
	 * 
	 * @param listener Not accept {@code null}
	 * @return {@code true} if remove success
	 */
	public boolean removeSecsCommunicatableStateChangeListener(SecsCommunicatableStateChangeListener listener);	
	
	/**
	 * Add Listener to get communicate-state-changed.
	 * 
	 * <p>
	 * Blocking-Listener.<br />
	 * Pass through quickly.<br />
	 * </p>
	 * 
	 * @param listener Not accept {@code null}
	 * @return {@code true} if add success
	 */
	@Deprecated
	default public boolean addSecsCommunicatableStateChangeListener(SecsCommunicatableStateChangeBiListener listener) {
		return this.addSecsCommunicatableStateChangeBiListener(listener);
	}
	
	/**
	 * Remove Listener.
	 * 
	 * @param listener Not accept {@code null}
	 * @return {@code true} if remove success
	 */
	@Deprecated
	default public boolean removeSecsCommunicatableStateChangeListener(SecsCommunicatableStateChangeBiListener listener) {
		return this.removeSecsCommunicatableStateChangeBiListener(listener);
	}
	
	/**
	 * Add Listener to get communicate-state-changed.
	 * 
	 * <p>
	 * Blocking-Listener.<br />
	 * Pass through quickly.<br />
	 * </p>
	 * 
	 * @param listener Not accept {@code null}
	 * @return {@code true} if add success
	 */
	public boolean addSecsCommunicatableStateChangeBiListener(SecsCommunicatableStateChangeBiListener listener);
	
	/**
	 * Remove Listener.
	 * 
	 * @param listener Not accept {@code null}
	 * @return {@code true} if remove success
	 */
	public boolean removeSecsCommunicatableStateChangeBiListener(SecsCommunicatableStateChangeBiListener listener);
	
	
	/* Try-Send Secs-Message Pass-through Listener */
	
	/**
	 * Add Listener to get SecsMesssage before sending.
	 * 
	 * @param listener Not accept {@code null}
	 * @return {@code true} if add success.
	 */
	public boolean addTrySendSecsMessagePassThroughListener(SecsMessagePassThroughListener listener);
	
	/**
	 * Remove Listener.
	 * 
	 * @param listener Not accept {@code null}
	 * @return {@code true} if remove success
	 */
	public boolean removeTrySendSecsMessagePassThroughListener(SecsMessagePassThroughListener listener);	
	
	/**
	 * Add Listener to get SecsMesssage before sending.
	 * 
	 * @param biListener Not accept {@code null}
	 * @return {@code true} if add success.
	 */
	public boolean addTrySendSecsMessagePassThroughBiListener(SecsMessagePassThroughBiListener biListener);
	
	/**
	 * Remove Listener.
	 * 
	 * @param biListener Not accept {@code null}
	 * @return {@code true} if remove success
	 */
	public boolean removeTrySendSecsMessagePassThroughBiListener(SecsMessagePassThroughBiListener biListener);	

	
	/* Sended Secs-Message Pass-through Listener */
	
	/**
	 * Add Listener to get sended SecsMesssage.
	 * 
	 * @param listener Not accept {@code null}
	 * @return {@code true} if add success
	 */
	public boolean addSendedSecsMessagePassThroughListener(SecsMessagePassThroughListener listener);
	
	/**
	 * Remove Listener.
	 * 
	 * @param listener Not accept {@code null}
	 * @return {@code true} if remove success
	 */
	public boolean removeSendedSecsMessagePassThroughListener(SecsMessagePassThroughListener listener);	
	
	/**
	 * Add Listener to get sended SecsMesssage.
	 * 
	 * @param biListener Not accept {@code null}
	 * @return {@code true} if add success
	 */
	public boolean addSendedSecsMessagePassThroughBiListener(SecsMessagePassThroughBiListener biListener);
	
	/**
	 * Remove Listener.
	 * 
	 * @param biListener Not accept {@code null}
	 * @return {@code true} if remove success
	 */
	public boolean removeSendedSecsMessagePassThroughBiListener(SecsMessagePassThroughBiListener biListener);	

	
	/* Receive Secs-Message Pass-through Listener */
	
	/**
	 * Add Listener to receive both Primary and Reply Message.
	 * 
	 * @param listener Not accept {@code null}
	 * @return {@code true} if add success
	 */
	public boolean addReceiveSecsMessagePassThroughListener(SecsMessagePassThroughListener listener);
	
	/**
	 * Remove BiListener.
	 * 
	 * @param listener Not accept {@code null}
	 * @return {@code true} if remove success
	 */
	public boolean removeReceiveSecsMessagePassThroughListener(SecsMessagePassThroughListener listener);
	
	/**
	 * Add BiListener to receive both Primary and Reply Message.
	 * 
	 * @param biListener Not accept {@code null}
	 * @return {@code true} if add success
	 */
	public boolean addReceiveSecsMessagePassThroughBiListener(SecsMessagePassThroughBiListener biListener);
	
	/**
	 * Remove Listener.
	 * 
	 * @param biListener Not accept {@code null}
	 * @return {@code true} if remove success
	 */
	public boolean removeReceiveSecsMessagePassThroughBiListener(SecsMessagePassThroughBiListener biListener);

	
	/**
	 * Add Listener to get SecsMesssage before sending.
	 * 
	 * @param listener Not accept {@code null}
	 * @return {@code true} if add success.
	 */
	@Deprecated
	default public boolean addTrySendMessagePassThroughListener(SecsMessagePassThroughListener listener) {
		return this.addTrySendSecsMessagePassThroughListener(listener);
	}
	
	/**
	 * Remove Listener.
	 * 
	 * @param listener Not accept {@code null}
	 * @return {@code true} if remove success
	 */
	@Deprecated
	default public boolean removeTrySendMessagePassThroughListener(SecsMessagePassThroughListener listener) {
		return this.removeTrySendSecsMessagePassThroughListener(listener);
	}
	
	/**
	 * Add Listener to get sended SecsMesssage.
	 * 
	 * @param listener Not accept {@code null}
	 * @return {@code true} if add success
	 */
	@Deprecated
	default public boolean addSendedMessagePassThroughListener(SecsMessagePassThroughListener listener) {
		return this.addSendedSecsMessagePassThroughListener(listener);
	}
	
	/**
	 * Remove Listener.
	 * 
	 * @param listener Not accept {@code null}
	 * @return {@code true} if remove success
	 */
	@Deprecated
	default public boolean removeSendedMessagePassThroughListener(SecsMessagePassThroughListener listener) {
		return this.removeSendedSecsMessagePassThroughListener(listener);
	}
	
	/**
	 * Add Listener to receive both Primary and Reply Message.
	 * 
	 * @param listener Not accept {@code null}
	 * @return {@code true} if add success
	 */
	@Deprecated
	default public boolean addReceiveMessagePassThroughListener(SecsMessagePassThroughListener listener) {
		return this.addReceiveSecsMessagePassThroughListener(listener);
	}
	
	/**
	 * Remove Listener.
	 * 
	 * @param listener Not accept {@code null}
	 * @return {@code true} if remove success
	 */
	@Deprecated
	default public boolean removeReceiveMessagePassThroughListener(SecsMessagePassThroughListener listener) {
		return this.removeReceiveSecsMessagePassThroughListener(listener);
	}
	
}
