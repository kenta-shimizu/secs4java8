package com.shimizukenta.secs.hsmsss;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.shimizukenta.secs.AlreadyClosedException;
import com.shimizukenta.secs.AlreadyOpenedException;
import com.shimizukenta.secs.SecsException;
import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.gem.ACKC5;
import com.shimizukenta.secs.gem.ACKC6;
import com.shimizukenta.secs.gem.COMMACK;
import com.shimizukenta.secs.gem.Clock;
import com.shimizukenta.secs.gem.ClockType;
import com.shimizukenta.secs.gem.OFLACK;
import com.shimizukenta.secs.gem.ONLACK;
import com.shimizukenta.secs.gem.TIACK;
import com.shimizukenta.secs.hsms.HsmsCommunicateState;
import com.shimizukenta.secs.hsms.HsmsConnectionMode;
import com.shimizukenta.secs.hsms.HsmsGemAccessor;
import com.shimizukenta.secs.hsms.HsmsMessage;
import com.shimizukenta.secs.hsms.HsmsMessageReceiveBiListener;
import com.shimizukenta.secs.local.property.BooleanCompution;
import com.shimizukenta.secs.local.property.BooleanProperty;
import com.shimizukenta.secs.local.property.ObjectProperty;
import com.shimizukenta.secs.secs2.Secs2Exception;

class HsmsSsCommunicatorTest {
	
	private static InetSocketAddress getInetSocketAddress(int port) {
		return new InetSocketAddress("127.0.0.1", port);
	}
	
	private static InetSocketAddress getInetSocketAddress() {
		return new InetSocketAddress("127.0.0.1", 0);
	}
	
	private static HsmsSsCommunicatorConfig activeCommunicatorConfig(SocketAddress socketAddr, boolean isEquip) {
		HsmsSsCommunicatorConfig config = new HsmsSsCommunicatorConfig();
		config.connectionMode(HsmsConnectionMode.ACTIVE);
		config.socketAddress(socketAddr);
		config.isEquip(isEquip);
		config.sessionId(10);
		config.timeout().t3(5.0F);
		config.timeout().t5(0.30F);
		return config;
	}
	
	private static HsmsSsCommunicatorConfig passiveCommunicatorConfig(SocketAddress socketAddr, boolean isEquip) {
		HsmsSsCommunicatorConfig config = new HsmsSsCommunicatorConfig();
		config.connectionMode(HsmsConnectionMode.PASSIVE);
		config.socketAddress(socketAddr);
		config.isEquip(isEquip);
		config.sessionId(10);
		config.timeout().t3(5.0F);
		config.gem().mdln("MDLN-A");
		config.gem().softrev("000001");
		config.gem().clockType(ClockType.A16);
		return config;
	}
	
	private static HsmsSsCommunicator activeCommunicator(SocketAddress socketAddr, boolean isEquip) {
		return HsmsSsCommunicator.newInstance(activeCommunicatorConfig(socketAddr, isEquip));
	}
	
	private static HsmsSsCommunicator activeCommunicator(boolean isEquip) {
		return activeCommunicator(getInetSocketAddress(), isEquip);
	}
	
	private static HsmsSsCommunicator passiveCommunicator(SocketAddress socketAddr, boolean isEquip) {
		return HsmsSsCommunicator.newInstance(passiveCommunicatorConfig(socketAddr, isEquip));
	}
	
	private static HsmsSsCommunicator passiveCommunicator(boolean isEquip) {
		return passiveCommunicator(getInetSocketAddress(), isEquip);
	}
	
	private static final HsmsMessageReceiveBiListener equipReceiveListener = (HsmsMessage primaryMsg, HsmsGemAccessor comm) -> {
		
		int strm = primaryMsg.getStream();
		int func = primaryMsg.getFunction();
		boolean wbit = primaryMsg.wbit();
		
		try {
			
			switch (strm) {
			case 1: {
				
				switch (func) {
				case 1: {
					if (wbit) {
						comm.gem().s1f2(primaryMsg);
					}
					break;
				}
				case 13: {
					if (wbit) {
						comm.gem().s1f14(primaryMsg, COMMACK.OK);
					}
					break;
				}
				case 15: {
					if (wbit) {
						comm.gem().s1f16(primaryMsg);
					}
					break;
				}
				case 17: {
					if (wbit) {
						comm.gem().s1f18(primaryMsg, ONLACK.OK);
					}
					break;
				}
				default: {
					if (wbit) {
						comm.send(primaryMsg, strm, 0, false);
					}
					comm.gem().s9f5(primaryMsg);
				}
				}
				break;
			}
			case 2: {
				
				switch (func) {
				case 31: {
					if (wbit) {
						comm.gem().s2f32(primaryMsg, TIACK.OK);
					}
					break;
				}
				default: {
					if (wbit) {
						comm.send(primaryMsg, strm, 0, false);
					}
					comm.gem().s9f5(primaryMsg);
					break;
				}
				}
				break;
			}
			default: {
				
				if (wbit) {
					comm.send(primaryMsg, 0, 0, false);
				}
				comm.gem().s9f3(primaryMsg);
			}
			}
		}
		catch (SecsException e) {
			fail(e);
		}
		catch (InterruptedException ignore) {
		}
	};
	
	private static final HsmsMessageReceiveBiListener hostReceiveListener = (HsmsMessage primaryMsg, HsmsGemAccessor comm) -> {
		
		int strm = primaryMsg.getStream();
		int func = primaryMsg.getFunction();
		boolean wbit = primaryMsg.wbit();
		
		try {
			
			switch (strm) {
			case 1: {
				
				switch (func) {
				case 1: {
					if (wbit) {
						comm.gem().s1f2(primaryMsg);
					}
					break;
				}
				case 13: {
					if (wbit) {
						comm.gem().s1f14(primaryMsg, COMMACK.OK);
					}
				}
				default: {
					if (wbit) {
						comm.send(primaryMsg, strm, 0, false);
					}
				}
				}
				break;
			}
			case 2: {
				
				switch (func) {
				case 17: {
					if (wbit) {
						comm.gem().s2f18Now(primaryMsg);
					}
				}
				default: {
					if (wbit) {
						comm.send(primaryMsg, strm, 0, false);
					}
				}
				}
				break;
			}
			case 5: {
				
				switch (func) {
				case 11: {
					if (wbit) {
						comm.gem().s5f2(primaryMsg, ACKC5.OK);
					}
					break;
				}
				default: {
					if (wbit) {
						comm.send(primaryMsg, strm, 0, false);
					}
				}
				}
				break;
			}
			case 6: {
				
				switch (func) {
				case 11: {
					if (wbit) {
						comm.gem().s6f12(primaryMsg, ACKC6.OK);
					}
					break;
				}
				default: {
					if (wbit) {
						comm.send(primaryMsg, strm, 0, false);
					}
				}
				}
				break;
			}
			default: {
				if (wbit) {
					comm.send(primaryMsg, 0, 0, false);
				}
			}
			}
		}
		catch (SecsException e) {
			fail(e);
		}
		catch (InterruptedException ignore) {
		}
	};
	
	@Test
	@DisplayName("Open twice Active")
	void testOpenTwiceActive() {
		
		try (
				HsmsSsCommunicator active = activeCommunicator(false);
				) {
			
			active.open();	// 1st
			active.open();	// 2nd reopen, throw AlreadyOpenedException
		}
		catch (AlreadyOpenedException e) {
			/* success */
		}
		catch (IOException e) {
			fail(e);
		}
	}
	
	@Test
	@DisplayName("Open twice Passive")
	void testOpenTwicePassive() {
		
		try (
				HsmsSsCommunicator passive = passiveCommunicator(true);
				) {
			
			passive.open();	// 1st
			passive.open();	// 2nd reopen, throw AlreadyOpenedException
		}
		catch (AlreadyOpenedException e) {
			/* success */
		}
		catch (IOException e) {
			fail(e);
		}
	}
	
	@Test
	@DisplayName("Open after close Active")
	void testOpenAfterCloseActive() {
		
		try (
				HsmsSsCommunicator active = activeCommunicator(false);
				) {
			
			active.open();	// 1st
			active.close();
			active.open();	// 2nd reopen, throw AlreadyClosedException
		}
		catch (AlreadyClosedException e) {
			/* success */
		}
		catch (IOException e) {
			fail(e);
		}
	}
	
	@Test
	@DisplayName("Open after close Passive")
	void testOpenAfterClosePassive() {
		
		try (
				HsmsSsCommunicator passive = passiveCommunicator(true);
				) {
			
			passive.open();	// 1st
			passive.close();
			passive.open();	// 2nd reopen, throw AlreadyClosedException
		}
		catch (AlreadyClosedException e) {
			/* success */
		}
		catch (IOException e) {
			fail(e);
		}
	}
	
	@Test
	@DisplayName("Reconnect after close")
	void testReconnectAfterClose() {
		
		BooleanProperty equipComm = BooleanProperty.newInstance(false);
		
		final SocketAddress sockAddr = getInetSocketAddress(5001);
		
		try (
				HsmsSsCommunicator equip = passiveCommunicator(sockAddr, true);
				) {
			
			equip.addSecsCommunicatableStateChangeListener(equipComm::set);
			
			assertEquals(equip.isClosed(), false);
			assertEquals(equip.isOpen(), false);
			assertEquals(equip.isCommunicatable(), false);
			
			equip.open();
			
			assertEquals(equip.isClosed(), false);
			assertEquals(equip.isOpen(), true);
			
			Thread.sleep(100L);
			
			try (
					HsmsSsCommunicator host = activeCommunicator(sockAddr, false);
					) {
				
				assertEquals(host.isClosed(), false);
				assertEquals(host.isOpen(), false);
				assertEquals(host.isCommunicatable(), false);
				
				host.openAndWaitUntilCommunicatable();
				equipComm.waitUntilTrue(3L, TimeUnit.SECONDS);
				
				assertEquals(host.isClosed(), false);
				assertEquals(host.isOpen(), true);
				assertEquals(host.isCommunicatable(), true);
			}
			
			equipComm.waitUntilFalse(3L, TimeUnit.SECONDS);
			
			/* reccoect other communicator */
			try (
					HsmsSsCommunicator host = activeCommunicator(sockAddr, false);
					) {
				
				assertEquals(host.isClosed(), false);
				assertEquals(host.isOpen(), false);
				assertEquals(host.isCommunicatable(), false);
				
				host.openAndWaitUntilCommunicatable();
				
				assertEquals(host.isClosed(), false);
				assertEquals(host.isOpen(), true);
				assertEquals(host.isCommunicatable(), true);
			}
		}
		catch (IOException e) {
			fail(e);
		}
		catch (TimeoutException e) {
			fail(e);
		}
		catch (InterruptedException ignore) {
		}
	}
	
	@Test
	@DisplayName("Selected fail because already selected")
	void testNotSelected() {
		
		final SocketAddress sockAddr = getInetSocketAddress(5002);
		
		try (
				HsmsSsCommunicator equip = passiveCommunicator(sockAddr, true);
				) {
			
			assertEquals(equip.isClosed(), false);
			assertEquals(equip.isOpen(), false);
			assertEquals(equip.isCommunicatable(), false);
			
			equip.open();
			
			assertEquals(equip.isClosed(), false);
			assertEquals(equip.isOpen(), true);
			
			Thread.sleep(100L);
			
			try (
					HsmsSsCommunicator host = activeCommunicator(sockAddr, false);
					) {
				
				assertEquals(host.isClosed(), false);
				assertEquals(host.isOpen(), false);
				assertEquals(host.isCommunicatable(), false);
				
				host.openAndWaitUntilCommunicatable();
				
				assertEquals(host.isClosed(), false);
				assertEquals(host.isOpen(), true);
				assertEquals(host.isCommunicatable(), true);
				
				try (
						HsmsSsCommunicator other = activeCommunicator(sockAddr, false);
						) {
					
					assertEquals(other.isClosed(), false);
					assertEquals(other.isOpen(), false);
					
					other.openAndWaitUntilCommunicatable(1L, TimeUnit.SECONDS);	// timeout because already selected.
					
					fail("not reach");
				}
				catch (TimeoutException e) {
					/* success */
				}
			}
		}
		catch (IOException e) {
			fail(e);
		}
		catch (InterruptedException ignore) {
		}
	}
	
	@Test
	@DisplayName("Run")
	void testRun() {
		
		final ObjectProperty<HsmsCommunicateState> hostCommState = ObjectProperty.newInstance(HsmsCommunicateState.NOT_CONNECTED);
		final ObjectProperty<HsmsCommunicateState> equipCommState = ObjectProperty.newInstance(HsmsCommunicateState.NOT_CONNECTED);
		final BooleanCompution bothCommSelected = hostCommState.computeIsEqualTo(HsmsCommunicateState.SELECTED).and(equipCommState.computeIsEqualTo(HsmsCommunicateState.SELECTED));
		
		final SocketAddress sockAddr = getInetSocketAddress(5003);
		
		try (
				HsmsSsCommunicator equip = passiveCommunicator(sockAddr, true);
				) {
			
			equip.addHsmsCommunicateStateChangeListener(equipCommState::set);
			equip.addHsmsMessageReceiveBiListener(equipReceiveListener);
			
			equip.open();
			
			Thread.sleep(100L);
			
			try (
					HsmsSsCommunicator host = activeCommunicator(sockAddr, false);
					) {
				
				host.addHsmsCommunicateStateChangeListener(hostCommState::set);
				host.addHsmsMessageReceiveBiListener(hostReceiveListener);
				
				host.open();
				
				bothCommSelected.waitUntilTrue(3L, TimeUnit.SECONDS);
				
				{
					COMMACK commack = host.gem().s1f13();
					assertEquals(commack, COMMACK.OK);
				}
				{
					ONLACK onlack = host.gem().s1f17();
					assertEquals(onlack, ONLACK.OK);
				}
				{
					Optional<SecsMessage> op = host.gem().s1f1();
					assertTrue(op.isPresent());
				}
				{
					COMMACK commack = equip.gem().s1f13();
					assertEquals(commack, COMMACK.OK);
				}
				{
					Clock clock = equip.gem().s2f17();
					assertNotNull(clock);
				}
				{
					TIACK tiack = host.gem().s2f31Now();
					assertEquals(tiack, TIACK.OK);
				}
				{
					OFLACK oflack = host.gem().s1f15();
					assertEquals(oflack, OFLACK.OK);
				}
			}
		}
		catch (IOException e) {
			fail(e);
		}
		catch (SecsException | Secs2Exception e) {
			fail(e);
		}
		catch (TimeoutException e) {
			fail(e);
		}
		catch (InterruptedException ignore) {
		}
	}
	
}
