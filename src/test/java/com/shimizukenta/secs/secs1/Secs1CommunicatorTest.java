package com.shimizukenta.secs.secs1;

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
import com.shimizukenta.secs.local.property.BooleanCompution;
import com.shimizukenta.secs.local.property.BooleanProperty;
import com.shimizukenta.secs.secs1ontcpip.Secs1OnTcpIpCommunicator;
import com.shimizukenta.secs.secs1ontcpip.Secs1OnTcpIpCommunicatorConfig;
import com.shimizukenta.secs.secs1ontcpip.Secs1OnTcpIpReceiverCommunicator;
import com.shimizukenta.secs.secs1ontcpip.Secs1OnTcpIpReceiverCommunicatorConfig;
import com.shimizukenta.secs.secs2.Secs2Exception;

class Secs1CommunicatorTest {
	
	private static InetSocketAddress getInetSocketAddress(int port) {
		return new InetSocketAddress("127.0.0.1", port);
	}
	
	private static InetSocketAddress getInetSocketAddress() {
		return new InetSocketAddress("127.0.0.1", 0);
	}
	
	private static final Secs1OnTcpIpCommunicatorConfig connectorConfig(SocketAddress sockAddr, boolean isEquip, boolean isMaster) {
		Secs1OnTcpIpCommunicatorConfig config = new Secs1OnTcpIpCommunicatorConfig();
		config.socketAddress(sockAddr);
		config.deviceId(10);
		config.isEquip(isEquip);
		config.isMaster(isMaster);
		config.gem().mdln("MDLN-A");
		config.gem().softrev("000001");
		config.gem().clockType(ClockType.A16);
		config.reconnectSeconds(0.2F);
		return config;
	}
	
	private static final Secs1OnTcpIpReceiverCommunicatorConfig receiverConfig(SocketAddress sockAddr, boolean isEquip, boolean isMaster) {
		Secs1OnTcpIpReceiverCommunicatorConfig config = new Secs1OnTcpIpReceiverCommunicatorConfig();
		config.socketAddress(sockAddr);
		config.deviceId(10);
		config.isEquip(isEquip);
		config.isMaster(isMaster);
		config.gem().mdln("MDLN-A");
		config.gem().softrev("000001");
		config.gem().clockType(ClockType.A16);
		config.rebindSeconds(0.3F);
		return config;
	}
	
	private static Secs1OnTcpIpCommunicator connectorCommunicator(SocketAddress sockAddr, boolean isEquip, boolean isMaster) {
		return Secs1OnTcpIpCommunicator.newInstance(connectorConfig(sockAddr, isEquip, isMaster));
	}
	
	private static Secs1OnTcpIpCommunicator connectorCommunicator(boolean isEquip, boolean isMaster) {
		return connectorCommunicator(getInetSocketAddress(), isEquip, isMaster);
	}
	
	private static Secs1OnTcpIpReceiverCommunicator receiverCommunicator(SocketAddress sockAddr, boolean isEquip, boolean isMaster) {
		return Secs1OnTcpIpReceiverCommunicator.newInstance(receiverConfig(sockAddr, isEquip, isMaster));
	}
	
	private static Secs1OnTcpIpReceiverCommunicator receiverCommunicator(boolean isEquip, boolean isMaster) {
		return receiverCommunicator(getInetSocketAddress(), isEquip, isMaster);
	}
	
	private static final Secs1MessageReceiveBiListener equipReceiveListener = (Secs1Message primaryMsg, Secs1GemAccessor comm) -> {
		
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
	
	private static final Secs1MessageReceiveBiListener hostReceiveListener = (Secs1Message primaryMsg, Secs1GemAccessor comm) -> {
		
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
	@DisplayName("Open twice connector")
	void testOpenTwiceConnector() {
		
		try (
				Secs1Communicator comm = connectorCommunicator(false, false);
				) {
			
			comm.open();	// 1st
			comm.open();	// 2nd, throw AlreadyOpenedException
			
			fail("not reach");
		}
		catch (AlreadyOpenedException e) {
			/* success */
		}
		catch (IOException e) {
			fail(e);
		}
	}
	
	@Test
	@DisplayName("Open twice receiver")
	void testOpenTwiceReceiver() {
		
		try (
				Secs1Communicator comm = receiverCommunicator(true, true);
				) {
			
			comm.open();	// 1st
			comm.open();	// 2nd, throw AlreadyOpenedException
			
			fail("not reach");
		}
		catch (AlreadyOpenedException e) {
			/* success */
		}
		catch (IOException e) {
			fail(e);
		}
	}
	
	@Test
	@DisplayName("Open after close connector")
	void testOpenAfterCloseConnector() {
		
		try (
				Secs1Communicator comm = connectorCommunicator(false, false);
				) {
			
			comm.open();	// 1st
			comm.close();
			comm.open();	// 2nd, throw AlreadyOpenedException
			
			fail("not reach");
		}
		catch (AlreadyClosedException e) {
			/* success */
		}
		catch (IOException e) {
			fail(e);
		}
	}
	
	@Test
	@DisplayName("Open after close receiver")
	void testOpenAfterCloseReceiver() {
		
		try (
				Secs1Communicator comm = receiverCommunicator(true, true);
				) {
			
			comm.open();	// 1st
			comm.close();
			comm.open();	// 2nd, throw AlreadyOpenedException
			
			fail("not reach");
		}
		catch (AlreadyClosedException e) {
			/* success */
		}
		catch (IOException e) {
			fail(e);
		}
	}
	
	@Test
	@DisplayName("Run")
	void testRun() {
		
		final BooleanProperty equipComm = BooleanProperty.newInstance(false);
		final BooleanProperty hostComm = BooleanProperty.newInstance(false);
		final BooleanCompution bothComm = equipComm.and(hostComm);
		
		final SocketAddress sockAddr = getInetSocketAddress(5001);
		
		try (
				Secs1Communicator equip = receiverCommunicator(sockAddr, true, true);
				) {
			
			equip.addSecs1MessageReceiveBiListener(equipReceiveListener);
			equip.addSecsCommunicatableStateChangeListener(equipComm::set);
			
			assertEquals(equip.isOpen(), false);
			assertEquals(equip.isClosed(), false);
			assertEquals(equip.isCommunicatable(), false);
			
			equip.open();
			
			assertEquals(equip.isOpen(), true);
			assertEquals(equip.isClosed(), false);
			assertEquals(equip.isCommunicatable(), false);
			
			Thread.sleep(100L);
			
			try (
					Secs1Communicator host = connectorCommunicator(sockAddr, false, false);
					) {
				
				host.addSecs1MessageReceiveBiListener(hostReceiveListener);
				host.addSecsCommunicatableStateChangeListener(hostComm::set);
				
				assertEquals(host.isOpen(), false);
				assertEquals(host.isClosed(), false);
				assertEquals(host.isCommunicatable(), false);
				
				host.open();
				
				bothComm.waitUntilTrue(3L, TimeUnit.SECONDS);
				
				assertEquals(host.isOpen(), true);
				assertEquals(host.isClosed(), false);
				assertEquals(host.isCommunicatable(), true);
				
				assertEquals(equip.isCommunicatable(), true);
				
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
			
			bothComm.waitUntilFalse(3L, TimeUnit.SECONDS);
		}
		catch (InterruptedException ignore) {
		}
		catch (TimeoutException e) {
			fail(e);
		}
		catch (SecsException | Secs2Exception e) {
			fail(e);
		}
		catch (IOException e) {
			fail(e);
		}
	}

}
