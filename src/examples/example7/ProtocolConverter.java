package example7;

import java.io.Closeable;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

import com.shimizukenta.secs.SecsCommunicator;
import com.shimizukenta.secs.SecsException;
import com.shimizukenta.secs.SecsLogListener;
import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.SecsMessageReceiveBiListener;
import com.shimizukenta.secs.gem.ACKC5;
import com.shimizukenta.secs.gem.ACKC6;
import com.shimizukenta.secs.gem.COMMACK;
import com.shimizukenta.secs.gem.ClockType;
import com.shimizukenta.secs.gem.ONLACK;
import com.shimizukenta.secs.gem.TIACK;
import com.shimizukenta.secs.hsms.HsmsConnectionMode;
import com.shimizukenta.secs.hsms.HsmsMessage;
import com.shimizukenta.secs.hsms.HsmsMessageType;
import com.shimizukenta.secs.hsmsss.HsmsSsCommunicator;
import com.shimizukenta.secs.hsmsss.HsmsSsCommunicatorConfig;
import com.shimizukenta.secs.hsmsss.HsmsSsMessageBuilder;
import com.shimizukenta.secs.secs1.Secs1Message;
import com.shimizukenta.secs.secs1.Secs1MessageBuilder;
import com.shimizukenta.secs.secs1.Secs1TooBigSendMessageException;
import com.shimizukenta.secs.secs1ontcpip.Secs1OnTcpIpCommunicator;
import com.shimizukenta.secs.secs1ontcpip.Secs1OnTcpIpCommunicatorConfig;
import com.shimizukenta.secs.secs1ontcpip.Secs1OnTcpIpReceiverCommunicator;
import com.shimizukenta.secs.secs1ontcpip.Secs1OnTcpIpReceiverCommunicatorConfig;
import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.secs2.Secs2Exception;

/**
 * HSMS-SS <-> SECS-I convert example, like SH-2000.<br/>
 * 
 * <p>
 * Connection diagram<br />
 * HSMS-SS-ACTIVE <-> HSMS-SS-PASSIVE <-> SECS-I-on-TCP/IP <-> SECS-I-on-TCP/IP-Receiver<br />
 * (HOST)             (Convert-SIDE-A)    (Convert-SIDE-B)     (EQUIP)<br />
 * </p>
 * <p>
 * This example is<br />
 * <ol>
 * <li>From HOST to EQUIP via Protocol-converter.</li>
 * <ol>
 * <li>send S1F13, receive S1F14</li>
 * <li>send S1F17, receive S1F18</li>
 * <li>send S2F31, receive S2F32</li>
 * </ol>
 * <li>From EQUIP to HOST via Protocol-converter.</li>
 * <ol>
 * <li>receive S5F1, reply S5F2</li>
 * </ol>
 * <li>From HOST to EQUIP via Protocol-converter.</li>
 * <ol>
 * <li>send S1F15, receive S1F16</li>
 * </ol>
 * </ol>
 * </p>
 * 
 * @author kenta-shimizu
 *
 */
public class ProtocolConverter implements Closeable {
	
	public static final byte REJECT_BY_OVERFLOW = (byte)0x80;
	public static final byte REJECT_BY_NOT_CONNECT = (byte)0x81;
	
	private final HsmsSsCommunicator hsmsSsComm;
	private final Secs1OnTcpIpCommunicator secs1Comm;
	private boolean opened;
	private boolean closed;
	
	public ProtocolConverter(
			Secs1OnTcpIpCommunicatorConfig secs1Config,
			HsmsSsCommunicatorConfig hsmsSsConfig
			) {
		
		this.opened = false;
		this.closed = false;
		
		this.secs1Comm = Secs1OnTcpIpCommunicator.newInstance(secs1Config);
		this.hsmsSsComm = HsmsSsCommunicator.newInstance(hsmsSsConfig);
		
		this.secs1Comm.addSecsMessageReceiveListener(msg -> {
			
			try {
				
				try {
					
					this.hsmsSsComm.send(this.toHsmsMessageFromSecs1Message(msg))
					.filter(r -> r.isDataMessage())
					.ifPresent(r -> {
						
						try {
							try {
								this.secs1Comm.send(this.toSecs1MessageFromHsmsMessage(r));
							}
							catch ( SecsException nothing ) {
							}
						}
						catch ( InterruptedException ignore ) {
						}
					});
				}
				catch ( SecsException nothing ) {
				}
			}
			catch ( InterruptedException ignore ) {
			}
		});
		
		this.hsmsSsComm.addSecsMessageReceiveListener(msg -> {
			
			try {
				
				try {
					
					this.secs1Comm.send(this.toSecs1MessageFromHsmsMessage(msg))
					.ifPresent(r -> {
						
						try {
							try {
								this.hsmsSsComm.send(this.toHsmsMessageFromSecs1Message(r));
							}
							catch ( SecsException nothing ) {
							}
						}
						catch (InterruptedException ignore ) {
						}
						
					});
				}
				catch ( Secs1TooBigSendMessageException e ) {
					
					try {
						this.hsmsSsComm.send(this.toHsmsRejectMessage(msg, REJECT_BY_OVERFLOW));
					}
					catch ( SecsException giveup ) {
					}
				}
				catch ( SecsException e ) {
					
					try {
						this.hsmsSsComm.send(this.toHsmsRejectMessage(msg, REJECT_BY_NOT_CONNECT));
					}
					catch ( SecsException giveup ) {
					}
				}
			}
			catch ( InterruptedException ignore ) {
			}
		});
	}

	public void open() throws IOException {
		
		synchronized ( this ) {
			if ( this.closed ) {
				throw new IOException("Already closed");
			}
			if ( this.opened ) {
				throw new IOException("Already opened");
			}
			this.opened = true;
		}
		
		this.secs1Comm.open();
		this.hsmsSsComm.open();
	}
	
	@Override
	public void close() throws IOException {
		
		synchronized ( this ) {
			if ( this.closed ) {
				return;
			}
			this.closed = true;
		}
		
		IOException ioExcept = null;
		
		try {
			this.secs1Comm.close();
		}
		catch ( IOException e ) {
			ioExcept = e;
		}
		
		try {
			this.hsmsSsComm.close();
		}
		catch ( IOException e ) {
			ioExcept = e;
		}
		
		if ( ioExcept != null ) {
			throw ioExcept;
		}
	}
	
	private HsmsMessage toHsmsMessageFromSecs1Message(SecsMessage msg) {
		
		byte[] bs = msg.header10Bytes();
		
		byte[] header = new byte[] {
				(byte)((int)(bs[0]) & 0x7F),
				bs[1],
				bs[2],
				bs[3],
				(byte)0,
				(byte)0,
				bs[6],
				bs[7],
				bs[8],
				bs[9]
		};
		
		return HsmsSsMessageBuilder.build(header, msg.secs2());
	}
	
	private Secs1Message toSecs1MessageFromHsmsMessage(SecsMessage msg)
			throws Secs1TooBigSendMessageException {
		
		byte[] bs = msg.header10Bytes();
		
		byte[] header = new byte[] {
				bs[0],
				bs[1],
				bs[2],
				bs[3],
				(byte)0,
				(byte)0,
				bs[6],
				bs[7],
				bs[8],
				bs[9]
		};
		
		if ( this.secs1Comm.isEquip() ) {
			header[0] |= (byte)0x80;
		} else {
			header[0] &= (byte)0x7F;
		}
		
		return Secs1MessageBuilder.build(header, msg.secs2());
	}
	
	private HsmsMessage toHsmsRejectMessage(SecsMessage ref, byte reason) {
		
		byte[] bs = ref.header10Bytes();
		
		byte[] header = new byte[] {
				bs[0],
				bs[1],
				(byte)0x0,
				reason,
				HsmsMessageType.REJECT_REQ.pType(),
				HsmsMessageType.REJECT_REQ.sType(),
				bs[6],
				bs[7],
				bs[8],
				bs[9]
		};
		
		return HsmsSsMessageBuilder.build(header);
	}
	
	public boolean addSecsLogListener(SecsLogListener l) {
		boolean a = this.secs1Comm.addSecsLogListener(l);
		boolean b = this.hsmsSsComm.addSecsLogListener(l);
		return a && b;
	}
	
	public boolean removeSecsLogListener(SecsLogListener l) {
		boolean a = this.secs1Comm.removeSecsLogListener(l);
		boolean b = this.hsmsSsComm.removeSecsLogListener(l);
		return a || b;
	}
	
	public static ProtocolConverter newInstance(
			Secs1OnTcpIpCommunicatorConfig secs1Config,
			HsmsSsCommunicatorConfig hsmsSsConfig) {
		
		return new ProtocolConverter(secs1Config, hsmsSsConfig);
	}
	
	public static ProtocolConverter open(
			Secs1OnTcpIpCommunicatorConfig secs1Config,
			HsmsSsCommunicatorConfig hsmsSsConfig)
					throws IOException {
		
		final ProtocolConverter inst = newInstance(secs1Config, hsmsSsConfig);
		
		try {
			inst.open();
		}
		catch ( IOException e ) {
			
			try {
				inst.close();
			}
			catch ( IOException giveup ) {
			}
			
			throw e;
		}
		
		return inst;
	}
	
	public static void main(String[] args) {

		final int deviceId = 10;
		final SocketAddress secs1Addr = new InetSocketAddress("127.0.0.1", 23000);
		final SocketAddress hsmsSsAddr = new InetSocketAddress("127.0.0.1", 5000);
		
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
					ProtocolConverter converter = ProtocolConverter.newInstance(secs1Side, hsmsSsSide);
					) {
				
//				converter.addSecsLogListener(ProtocolConverter::echo);
				
				converter.open();
				
				try (
						SecsCommunicator equip = Secs1OnTcpIpReceiverCommunicator.newInstance(equipConfig);
						) {
					
					equip.addSecsLogListener(ProtocolConverter::echo);
					equip.addSecsMessageReceiveListener(equipRecvListener());
					
					equip.open();
					
					try (
							SecsCommunicator host = HsmsSsCommunicator.newInstance(hostConfig);
							) {
						
						host.addSecsLogListener(ProtocolConverter::echo);
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
