package com.shimizukenta.secstest;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

import com.shimizukenta.secs.SecsCommunicator;
import com.shimizukenta.secs.SecsException;
import com.shimizukenta.secs.SecsMessageReceiveBiListener;
import com.shimizukenta.secs.gem.ACKC5;
import com.shimizukenta.secs.gem.ACKC6;
import com.shimizukenta.secs.gem.COMMACK;
import com.shimizukenta.secs.gem.ClockType;
import com.shimizukenta.secs.gem.ONLACK;
import com.shimizukenta.secs.gem.TIACK;
import com.shimizukenta.secs.hsms.HsmsConnectionMode;
import com.shimizukenta.secs.hsmsss.HsmsSsCommunicator;
import com.shimizukenta.secs.hsmsss.HsmsSsCommunicatorConfig;
import com.shimizukenta.secs.secs1ontcpip.Secs1OnTcpIpCommunicatorConfig;
import com.shimizukenta.secs.secs1ontcpip.Secs1OnTcpIpReceiverCommunicator;
import com.shimizukenta.secs.secs1ontcpip.Secs1OnTcpIpReceiverCommunicatorConfig;
import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.secs2.Secs2Exception;
import com.shimizukenta.secstestutil.Secs1OnTcpIpHsmsSsConverter;

public class ProtocolConvert {

	public ProtocolConvert() {
		/* Nothing */
	}
	
	private static final int deviceId = 10;
	private static final SocketAddress secs1Addr = new InetSocketAddress("127.0.0.1", 23000);
	private static final SocketAddress hsmsSsAddr = new InetSocketAddress("127.0.0.1", 5000);
	
	public static void main(String[] args) {
		
		try {
			
			final Secs1OnTcpIpCommunicatorConfig secs1Side = new Secs1OnTcpIpCommunicatorConfig();
			final HsmsSsCommunicatorConfig hsmsSsSide = new HsmsSsCommunicatorConfig();
			final Secs1OnTcpIpReceiverCommunicatorConfig equipConfig = new Secs1OnTcpIpReceiverCommunicatorConfig();
			final HsmsSsCommunicatorConfig hostConfig = new HsmsSsCommunicatorConfig();
			
			secs1Side.deviceId(deviceId);
			secs1Side.isEquip(false);
			secs1Side.isMaster(false);
			secs1Side.socketAddress(secs1Addr);
			secs1Side.timeout().t1( 1.0F);
			secs1Side.timeout().t2(15.0F);
			secs1Side.timeout().t3(45.0F);
			secs1Side.timeout().t4(45.0F);
			secs1Side.retry(3);
			secs1Side.reconnectSeconds(5.0F);
			secs1Side.logSubjectHeader("SECS-I-Side: ");
			secs1Side.name("Secs1Side");
			
			hsmsSsSide.sessionId(deviceId);
			hsmsSsSide.isEquip(true);
			hsmsSsSide.socketAddress(hsmsSsAddr);
			hsmsSsSide.connectionMode(HsmsConnectionMode.PASSIVE);
			hsmsSsSide.timeout().t3(45.0F);
			hsmsSsSide.timeout().t6( 5.0F);
			hsmsSsSide.timeout().t7(10.0F);
			hsmsSsSide.timeout().t8( 5.0F);
			hsmsSsSide.notLinktest();
			hsmsSsSide.rebindIfPassive(5.0F);
			hsmsSsSide.logSubjectHeader("HSMS-SS-Passive-Side: ");
			hsmsSsSide.name("HsmsSsPassiveSide");
			
			equipConfig.deviceId(deviceId);
			equipConfig.isEquip(true);
			equipConfig.isMaster(true);
			equipConfig.socketAddress(secs1Addr);
			equipConfig.timeout().t1( 1.0F);
			equipConfig.timeout().t2(15.0F);
			equipConfig.timeout().t3(45.0F);
			equipConfig.timeout().t4(45.0F);
			equipConfig.retry(3);
			equipConfig.rebindSeconds(5.0F);
			equipConfig.gem().mdln("MDLN-A");
			equipConfig.gem().softrev("000001");
			equipConfig.gem().clockType(ClockType.A16);
			equipConfig.logSubjectHeader("Equip: ");
			equipConfig.name("equip");
			
			hostConfig.sessionId(deviceId);
			hostConfig.isEquip(false);
			hostConfig.socketAddress(hsmsSsAddr);
			hostConfig.connectionMode(HsmsConnectionMode.ACTIVE);
			hostConfig.timeout().t3(45.0F);
			hostConfig.timeout().t5(10.0F);
			hostConfig.timeout().t6( 5.0F);
			hostConfig.timeout().t8( 5.0F);
			hostConfig.linktest(120.0F);
			hostConfig.logSubjectHeader("Host: ");
			hostConfig.name("host");
			
			try (
					Secs1OnTcpIpHsmsSsConverter converter = Secs1OnTcpIpHsmsSsConverter.newInstance(secs1Side, hsmsSsSide);
					) {
				
//				converter.addSecsLogListener(ProtocolConvert::echo);
				
				converter.open();
				
				try (
						SecsCommunicator equip = Secs1OnTcpIpReceiverCommunicator.newInstance(equipConfig);
						) {
					
					equip.addSecsLogListener(ProtocolConvert::echo);
					equip.addSecsMessageReceiveListener(equipRecvListener());
					
					equip.open();
					
					try (
							SecsCommunicator host = HsmsSsCommunicator.newInstance(hostConfig);
							) {
						
						host.addSecsLogListener(ProtocolConvert::echo);
						host.addSecsMessageReceiveListener(hostRecvListener());
						
						host.open();
						
						equip.waitUntilCommunicatable();
						host.waitUntilCommunicatable();
						
						executeCommand(host, equip);
					}
				}
			}
		}
		catch ( InterruptedException ignore ) {
		}
		catch ( Throwable t ) {
			echo(t);
		}
	}
	
	private static void executeCommand(
			SecsCommunicator host,
			SecsCommunicator equip)
					throws SecsException, Secs2Exception,
					IOException, InterruptedException {
		
		Thread.sleep(500L);
		
		echo("start");
		Thread.sleep(500L);
		
		host.gem().s1f13();
		Thread.sleep(200L);
		
		host.gem().s1f17();
		Thread.sleep(200L);
		
		host.gem().s2f31Now();
		Thread.sleep(200L);
		
		equip.send(
				5, 1, true,
				Secs2.list(
						Secs2.binary((byte)0x81),
						Secs2.uint2(1001),
						Secs2.ascii("ON FIRE")
						)
				);
		Thread.sleep(200L);
		
		host.gem().s1f15();
		Thread.sleep(200L);
		
		echo("end");
		Thread.sleep(500L);
	}
	
	private static SecsMessageReceiveBiListener equipRecvListener() {
		
		return (msg, comm) -> {
			
			int strm = msg.getStream();
			int func = msg.getFunction();
			boolean wbit = msg.wbit();
//			Secs2 body = msg.secs2();
			
			try {
				
				switch ( strm ) {
				case 1: {
					
					switch ( func ) {
					case 1: {
						
						if ( wbit ) {
							comm.gem().s1f2(msg);
						}
						break;
					}
					case 13: {
						
						if ( wbit ) {
							comm.gem().s1f14(msg, COMMACK.OK);
						}
						break;
					}
					case 15: {
						
						if ( wbit ) {
							comm.gem().s1f16(msg);
						}
						break;
					}
					case 17: {
						
						if ( wbit ) {
							comm.gem().s1f18(msg, ONLACK.OK);
						}
						break;
					}
					default: {
						
						if ( wbit ) {
							comm.send(msg, strm, 0, false);
						}
						
						comm.gem().s9f5(msg);
					}
					}
					break;
				}
				case 2: {
					
					switch ( func ) {
					case 17: {
						
						if ( wbit ) {
							comm.gem().s2f18Now(msg);
						}
						break;
					}
					case 31: {
						
						if ( wbit ) {
							comm.gem().s2f32(msg, TIACK.OK);
						}
						break;
					}
					default: {
						
						if ( wbit ) {
							comm.send(msg, strm, 0, false);
						}
						
						comm.gem().s9f5(msg);
					}
					}
					break;
				}
				default: {
					
					if ( wbit ) {
						comm.send(msg, 0, 0, false);
					}
					
					comm.gem().s9f3(msg);
				}
				}
			}
			catch ( InterruptedException ignore ) {
			}
			catch ( SecsException | Secs2Exception e ) {
				echo(e);
			}
		};
	}
	
	private static SecsMessageReceiveBiListener hostRecvListener() {
		
		return (msg, comm) -> {
			int strm = msg.getStream();
			int func = msg.getFunction();
			boolean wbit = msg.wbit();
//			Secs2 body = msg.secs2();
			
			try {
				
				switch ( strm ) {
				case 1: {
					
					switch ( func ) {
					case 1: {
						
						if ( wbit ) {
							comm.gem().s1f2(msg);
						}
						break;
					}
					case 13: {
						
						if ( wbit ) {
							comm.gem().s1f14(msg, COMMACK.OK);
						}
						break;
					}
					case 15: {
						
						if ( wbit ) {
							comm.gem().s1f16(msg);
						}
						break;
					}
					case 17: {
						
						if ( wbit ) {
							comm.gem().s1f18(msg, ONLACK.OK);
						}
						break;
					}
					default: {
						
						if ( wbit ) {
							comm.send(msg, strm, 0, false);
						}
					}
					}
					break;
				}
				case 2: {
					
					switch ( func ) {
					case 17: {
						
						if ( wbit ) {
							comm.gem().s2f18Now(msg);
						}
						break;
					}
					case 31: {
						
						if ( wbit ) {
							comm.gem().s2f32(msg, TIACK.OK);
						}
						break;
					}
					default: {
						
						if ( wbit ) {
							comm.send(msg, strm, 0, false);
						}
					}
					}
					break;
				}
				case 5: {
					
					switch ( func ) {
					case 1: {
						
						if ( wbit ) {
							comm.gem().s5f2(msg, ACKC5.OK);
						}
						break;
					}
					default: {
						
						if ( wbit ) {
							comm.send(msg, strm, 0, false);
						}
					}
					}
					break;
				}
				case 6: {
					
					switch ( func ) {
					case 4: {
						
						if ( wbit ) {
							comm.gem().s6f4(msg, ACKC6.OK);
						}
						break;
					}
					case 12: {
						
						if ( wbit ) {
							comm.gem().s6f12(msg, ACKC6.OK);
						}
						break;
					}
					default: {
						
						if ( wbit ) {
							comm.send(msg, strm, 0, false);
						}
					}
					}
					break;
				}
				default: {
					
					if ( wbit ) {
						comm.send(msg, 0, 0, false);
					}
				}
				}
			}
			catch ( InterruptedException ignore ) {
			}
			catch ( SecsException | Secs2Exception e ) {
				echo(e);
			}
		};
	}
	
	private static Object syncStaticEcho = new Object();
	
	private static void echo(Object o) {
		
		synchronized ( syncStaticEcho ) {
			
			if ( o instanceof Throwable) {
				
				try (
						StringWriter sw = new StringWriter();
						) {
					
					try (
							PrintWriter pw = new PrintWriter(sw);
							) {
						
						((Throwable) o).printStackTrace(pw);
						pw.flush();
						
						System.out.println(sw.toString());
					}
				}
				catch ( IOException e ) {
					e.printStackTrace();
				}
				
			} else {
				
				System.out.println(o);
			}
			
			System.out.println();
		}
	}
	
}
