package com.shimizukenta.secs;

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

import com.shimizukenta.secs.gem.ACKC5;
import com.shimizukenta.secs.gem.ACKC6;
import com.shimizukenta.secs.gem.COMMACK;
import com.shimizukenta.secs.gem.Clock;
import com.shimizukenta.secs.gem.ClockType;
import com.shimizukenta.secs.gem.OFLACK;
import com.shimizukenta.secs.gem.ONLACK;
import com.shimizukenta.secs.gem.TIACK;
import com.shimizukenta.secs.hsms.HsmsConnectionMode;
import com.shimizukenta.secs.hsmsss.HsmsSsCommunicator;
import com.shimizukenta.secs.hsmsss.HsmsSsCommunicatorConfig;
import com.shimizukenta.secs.local.property.BooleanCompution;
import com.shimizukenta.secs.local.property.BooleanProperty;
import com.shimizukenta.secs.secs2.Secs2Exception;
import com.shimizukenta.secs.sml.SmlMessage;
import com.shimizukenta.secs.sml.SmlParseException;

class SecsCommunicatorTest {

	private static InetSocketAddress getInetSocketAddress(int port) {
		return new InetSocketAddress("127.0.0.1", port);
	}
	
//	private static InetSocketAddress getInetSocketAddress() {
//		return new InetSocketAddress("127.0.0.1", 0);
//	}
	
	private static HsmsSsCommunicatorConfig activeCommunicatorConfig(SocketAddress socketAddr, boolean isEquip) {
		HsmsSsCommunicatorConfig config = new HsmsSsCommunicatorConfig();
		config.connectionMode(HsmsConnectionMode.ACTIVE);
		config.socketAddress(socketAddr);
		config.isEquip(isEquip);
		config.sessionId(10);
		config.timeout().t3(5.0F);
		config.timeout().t5(0.20F);
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
	
//	private static HsmsSsCommunicator activeCommunicator(boolean isEquip) {
//		return activeCommunicator(getInetSocketAddress(), isEquip);
//	}
	
	private static HsmsSsCommunicator passiveCommunicator(SocketAddress socketAddr, boolean isEquip) {
		return HsmsSsCommunicator.newInstance(passiveCommunicatorConfig(socketAddr, isEquip));
	}
	
	private static final SecsMessageReceiveBiListener equipReceiveListener = (SecsMessage primaryMsg, SecsCommunicator comm) -> {
		
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
			case 99: {
				
				switch (func) {
				case 1: {
					SmlMessage smlMsg = SmlMessage.of("S99F2 <B 0x00>.");
					comm.send(primaryMsg, smlMsg);
				}
				default: {
					if (wbit) {
						comm.send(primaryMsg, strm, 0, false);
					}
					comm.gem().s9f5(primaryMsg);
				}
				}
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
		catch (SmlParseException e) {
			fail(e);
		}
		catch (InterruptedException ignore) {
		}
	};
	
	private static final SecsMessageReceiveBiListener hostReceiveListener = (SecsMessage primaryMsg, SecsCommunicator comm) -> {
		
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
	@DisplayName("Run")
	void testRun() {
		
		final SocketAddress sockAddr = getInetSocketAddress(5003);
		
		final BooleanProperty equipCommunicatable = BooleanProperty.newInstance(false);
		final BooleanProperty hostCommunicatable = BooleanProperty.newInstance(false);
		final BooleanCompution bothCommunicatable = equipCommunicatable.and(hostCommunicatable);
		
		try (
				SecsCommunicator equip = passiveCommunicator(sockAddr, true);
				) {
			
			equip.addSecsCommunicatableStateChangeListener(equipCommunicatable::set);
			equip.addSecsMessageReceiveBiListener(equipReceiveListener);
			
			equip.open();
			
			Thread.sleep(100L);
			
			try (
					SecsCommunicator host = activeCommunicator(sockAddr, false);
					) {
				
				host.addSecsCommunicatableStateChangeListener(hostCommunicatable::set);
				host.addSecsMessageReceiveBiListener(hostReceiveListener);
				
				host.open();
				
				bothCommunicatable.waitUntilTrue(3L, TimeUnit.SECONDS);
				
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
					SmlMessage smlMsg = SmlMessage.of("S99F1 W.");
					Optional<SecsMessage> op = host.send(smlMsg);
					
					assertEquals(op.map(SecsMessage::getStream)
							.filter(strm -> strm.intValue() == 99)
							.isPresent(), true);
					assertEquals(op.map(SecsMessage::getFunction)
							.filter(strm -> strm.intValue() == 2)
							.isPresent(), true);
					assertEquals(op.map(SecsMessage::secs2)
							.flatMap(ss -> ss.optionalByte(0))
							.filter(b -> b.byteValue() == (byte)0x0)
							.isPresent(), true);
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
		catch (SmlParseException e) {
			fail(e);
		}
		catch (TimeoutException e) {
			fail(e);
		}
		catch (InterruptedException ignore) {
		}
	}
	
}
