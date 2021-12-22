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
 * This interface is implementation of SECS-Communicating, open/close communicating, receive/send SECS-Message.<br />
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
	 * @throws InterruptedException
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
	 * @param timeout
	 * @param unit
	 * @throws InterruptedException
	 * @throws TimeoutException
	 */
	public void waitUntilCommunicatable(long timeout, TimeUnit unit) throws InterruptedException, TimeoutException;
	
	/**
	 * Wait until communicatable.
	 * 
	 * <p>
	 * Blocking-method.<br />
	 * If Already communicatable, do nothing.<br />
	 * </p>
	 * 
	 * @param tp
	 * @throws InterruptedException
	 * @throws TimeoutException
	 */
	public void waitUntilCommunicatable(ReadOnlyTimeProperty tp) throws InterruptedException, TimeoutException;
	
	/**
	 * Wait until <strong>NOT</strong> communicatable.
	 * 
	 * <p>
	 * Blocking-method.<br />
	 * If Already not communicatable, do nothing.<br />
	 * </p>
	 * 
	 * @throws InterruptedException
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
	 * @param timeout
	 * @param unit
	 * @throws InterruptedException
	 * @throws TimeoutException
	 */
	public void waitUntilNotCommunicatable(long timeout, TimeUnit unit) throws InterruptedException, TimeoutException;
	
	/**
	 * Wait until <strong>NOT</strong> communicatable.
	 * 
	 * <p>
	 * Blocking-method.<br />
	 * If Already not communicatable, do nothing.<br />
	 * </p>
	 * 
	 * @param tp
	 * @throws InterruptedException
	 * @throws TimeoutException
	 */
	public void waitUntilNotCommunicatable(ReadOnlyTimeProperty tp) throws InterruptedException, TimeoutException;
	
	/**
	 * Open and wait until communicatable.
	 * 
	 * <p>
	 * Blocking-method.<br />
	 * If Already opened, do nothing.<br />
	 * If Already communicatable, do nothing.<br />
	 * </p>
	 * 
	 * @throws IOException
	 * @throws InterruptedException
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
	 * @param timeout
	 * @param unit
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws TimeoutException
	 */
	public void openAndWaitUntilCommunicatable(long timeout, TimeUnit unit) throws IOException, InterruptedException, TimeoutException;
	
	/**
	 * Open and wait until communicatable.
	 * 
	 * <p>
	 * Blocking-method.<br />
	 * If Already opened, do nothing.<br />
	 * If Already communicatable, do nothing.<br />
	 * </p>
	 * 
	 * @param tp
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws TimeoutException
	 */
	public void openAndWaitUntilCommunicatable(ReadOnlyTimeProperty tp) throws IOException, InterruptedException, TimeoutException;
	
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
	 * @param lstnr Not accept {@code null}
	 * @return {@code true} if add success
	 */
	public boolean addSecsMessageReceiveListener(SecsMessageReceiveListener lstnr);
	
	/**
	 * Remove Listener.
	 * 
	 * @param lstnr Not accept {@code null}
	 * @return {@code true} if remove success
	 */
	public boolean removeSecsMessageReceiveListener(SecsMessageReceiveListener lstnr);
	
	
	/**
	 * Add Listener to receive Primary-Message.
	 * 
	 * <p>
	 * This Listener not receive Reply-Message.<br />
	 * </p>
	 * 
	 * @param lstnr Not accept {@code null}
	 * @return {@code true} if add success
	 */
	public boolean addSecsMessageReceiveListener(SecsMessageReceiveBiListener lstnr);
	
	/**
	 * Remove Listener.
	 * 
	 * @param lstnr Not accept {@code null}
	 * @return {@code true} if remove success
	 */
	public boolean removeSecsMessageReceiveListener(SecsMessageReceiveBiListener lstnr);
	
	
	/* Secs-Log Receive Listener */
	
	/**
	 * Add Listener to log Communicating.
	 * 
	 * @param lstnr Not accept {@code null}
	 * @return {@code true} if add success
	 */
	public boolean addSecsLogListener(SecsLogListener lstnr);
	
	/**
	 * Remove Listener
	 * 
	 * @param lstnr Not accept {@code null}
	 * @return {@code true} if remove success
	 */
	public boolean removeSecsLogListener(SecsLogListener lstnr);
	
	
	/* Secs-Communicatable-State-Changed-Listener */
	
	/**
	 * Add Listener to get communicate-state-changed.
	 * 
	 * <p>
	 * Blocking-Listener.<br />
	 * Pass through quickly.<br />
	 * </p>
	 * 
	 * @param lstnr Not accept {@code null}
	 * @return {@code true} if add success
	 */
	public boolean addSecsCommunicatableStateChangeListener(SecsCommunicatableStateChangeListener lstnr);
	
	/**
	 * Remove Listener.
	 * 
	 * @param lstnr Not accept {@code null}
	 * @return {@code true} if remove success
	 */
	public boolean removeSecsCommunicatableStateChangeListener(SecsCommunicatableStateChangeListener lstnr);	
	
	/**
	 * Add Listener to get communicate-state-changed.
	 * 
	 * <p>
	 * Blocking-Listener.<br />
	 * Pass through quickly.<br />
	 * </p>
	 * 
	 * @param lstnr Not accept {@code null}
	 * @return {@code true} if add success
	 */
	public boolean addSecsCommunicatableStateChangeListener(SecsCommunicatableStateChangeBiListener lstnr);
	
	/**
	 * Remove Listener.
	 * 
	 * @param lstnr Not accept {@code null}
	 * @return {@code true} if remove success
	 */
	public boolean removeSecsCommunicatableStateChangeListener(SecsCommunicatableStateChangeBiListener lstnr);

	/* Try-Send Secs-Message Pass-through Listener */
	
	/**
	 * Add Listener to get SecsMesssage before sending.
	 * 
	 * @param lstnr Not accept {@code null}
	 * @return {@code true} if add success.
	 */
	public boolean addTrySendMessagePassThroughListener(SecsMessagePassThroughListener lstnr);
	
	/**
	 * Remove Listener.
	 * 
	 * @param lstnr Not accept {@code null}
	 * @return {@code true} if remove success
	 */
	public boolean removeTrySendMessagePassThroughListener(SecsMessagePassThroughListener lstnr);	
	
	
	/* Sended Secs-Message Pass-through Listener */
	
	/**
	 * Add Listener to get sended SecsMesssage.
	 * 
	 * @param lstnr Not accept {@code null}
	 * @return {@code true} if add success
	 */
	public boolean addSendedMessagePassThroughListener(SecsMessagePassThroughListener lstnr);
	
	/**
	 * Remove Listener.
	 * 
	 * @param lstnr Not accept {@code null}
	 * @return {@code true} if remove success
	 */
	public boolean removeSendedMessagePassThroughListener(SecsMessagePassThroughListener lstnr);	
	
	
	/* Receive Secs-Message Pass-through Listener */
	
	/**
	 * Add Listener to receive both Primary and Reply Message.
	 * 
	 * @param lstnr Not accept {@code null}
	 * @return {@code true} if add success
	 */
	public boolean addReceiveMessagePassThroughListener(SecsMessagePassThroughListener lstnr);
	
	/**
	 * Remove Listener.
	 * 
	 * @param lstnr Not accept {@code null}
	 * @return {@code true} if remove success
	 */
	public boolean removeReceiveMessagePassThroughListener(SecsMessagePassThroughListener lstnr);
	
}
