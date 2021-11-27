package example5;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Arrays;

import com.shimizukenta.secs.SecsCommunicator;
import com.shimizukenta.secs.SecsException;
import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.gem.COMMACK;
import com.shimizukenta.secs.gem.Clock;
import com.shimizukenta.secs.gem.ClockType;
import com.shimizukenta.secs.gem.DRACK;
import com.shimizukenta.secs.gem.DynamicCollectionEvent;
import com.shimizukenta.secs.gem.DynamicEventReportConfig;
import com.shimizukenta.secs.gem.DynamicEventReportException;
import com.shimizukenta.secs.gem.DynamicReport;
import com.shimizukenta.secs.gem.ERACK;
import com.shimizukenta.secs.gem.LRACK;
import com.shimizukenta.secs.gem.OFLACK;
import com.shimizukenta.secs.gem.ONLACK;
import com.shimizukenta.secs.gem.TIACK;
import com.shimizukenta.secs.hsms.HsmsConnectionMode;
import com.shimizukenta.secs.hsmsss.HsmsSsCommunicator;
import com.shimizukenta.secs.hsmsss.HsmsSsCommunicatorConfig;
import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.secs2.Secs2Exception;

/**
 * Example-5.
 * 
 * <p>
 * This is using gem examples.
 * </p>
 * 
 * @author kenta-shimizu
 *
 */
public class ExampleGem {

	public ExampleGem() {
		/* Nothing */
	}

	public static void main(String[] args) {
		
		final HsmsSsCommunicatorConfig equipConf = new HsmsSsCommunicatorConfig();
		equipConf.connectionMode(HsmsConnectionMode.PASSIVE);
		equipConf.socketAddress(new InetSocketAddress("127.0.0.1", 5000));
		equipConf.sessionId(10);
		equipConf.isEquip(true);
		equipConf.gem().mdln("MDLN-A");
		equipConf.gem().softrev("000001");
		equipConf.gem().clockType(ClockType.A16);
		
		final HsmsSsCommunicatorConfig hostConf = new HsmsSsCommunicatorConfig();
		hostConf.connectionMode(HsmsConnectionMode.ACTIVE);
		hostConf.socketAddress(new InetSocketAddress("127.0.0.1", 5000));
		hostConf.sessionId(10);
		hostConf.isEquip(false);
		hostConf.gem().clockType(ClockType.A16);
		
		
		try (
				SecsCommunicator equip = HsmsSsCommunicator.newInstance(equipConf);
				) {
			
			equip.addSecsMessageReceiveListener(msg -> {
				try {
					equipReceive(equip, msg);
				}
				catch ( InterruptedException ignore ) {
				}
			});
			
			equip.open();
			
			Thread.sleep(500L);
			
			try (
					SecsCommunicator host = HsmsSsCommunicator.newInstance(hostConf);
					) {
				
				host.addSecsLogListener(log -> {
					echo(log);
				});
				
				host.openAndWaitUntilCommunicatable();
				
				try {
					
					COMMACK commack = host.gem().s1f13();
					if ( commack != COMMACK.OK ) {
						return;
					}
					
					ONLACK onlack = host.gem().s1f17();
					if ( onlack != ONLACK.OK && onlack != ONLACK.AlreadyOnline ) {
						return;
					}
					
					TIACK tiack = host.gem().s2f31Now();
					if ( tiack != TIACK.OK ) {
						return;
					}
					
					DynamicEventReportConfig evRptConf = host.gem().newDynamicEventReportConfig();
					
					setDynamicEventReportConfiguration(evRptConf);
					
					evRptConf.s2f37DisableAll();
					evRptConf.s2f33DeleteAll();
					
					DRACK drack = evRptConf.s2f33Define();
					if ( drack != DRACK.OK ) {
						return;
					}
					
					LRACK lrack = evRptConf.s2f35();
					if ( lrack != LRACK.OK ) {
						return;
					}
					
					ERACK erack = evRptConf.s2f37Enable();
					if ( erack != ERACK.OK ) {
						return;
					}
					
					Thread.sleep(500L);
					
					/* This "now" is setted in #addDefineReport */
					evRptConf.s6f19("now");
					
					Thread.sleep(1000L);
					
					/* This "on-fire" is setted in #addEnableCollectionEvent */
					evRptConf.s6f15("on-fire");
					
					Thread.sleep(500L);
					
					OFLACK oflack = host.gem().s1f15();
					if ( oflack != OFLACK.OK ) {
						return;
					}
				}
				catch ( SecsException | Secs2Exception | DynamicEventReportException e ) {
					echo(e);
				}
				
				Thread.sleep(500L);
			}
		}
		catch ( InterruptedException ignore ) {
		}
		catch ( IOException e ) {
			echo(e);
		}
	}
	
	private static void setDynamicEventReportConfiguration(DynamicEventReportConfig conf) {
		
		/* Add reports with alias */
		DynamicReport rptNow = conf.addDefineReport("now", Arrays.asList(Long.valueOf(1001)));
		DynamicReport rptFoo = conf.addDefineReport("foo", Arrays.asList(Long.valueOf(2001)));
		
		/* Add Collection-Events with alias */
		DynamicCollectionEvent evOnFire = conf.addEnableCollectionEvent("on-fire", 101);
		
		/* Add Links */
		conf.addLinkByReport(evOnFire, Arrays.asList(rptNow, rptFoo));
	}
	
	private static void equipReceive(SecsCommunicator comm, SecsMessage msg) throws InterruptedException {
		
		try {
			
			if ( comm.deviceId() != msg.deviceId() ) {
				sendSxF0(comm, msg, 0);
				comm.gem().s9f1(msg);
				return;
			}
			
			int strm = msg.getStream();
			int func = msg.getFunction();
			
			switch ( strm ) {
			case 1: {
				
				switch ( func ) {
				case 13: {
					
					comm.gem().s1f14(msg, COMMACK.OK);
					break;
				}
				case 15: {
					
					comm.gem().s1f16(msg);
					break;
				}
				case 17: {
					
					comm.gem().s1f18(msg, ONLACK.OK);
					break;
				}
				default: {
					
					sendSxF0(comm, msg, strm);
					comm.gem().s9f5(msg);
				}
				}
				break;
			}
			case 2: {
				
				switch( func ) {
				case 31: {
					
					comm.gem().s2f32(msg, TIACK.OK);
					break;
				}
				case 33: {
					
					comm.gem().s2f34(msg, DRACK.OK);
					break;
				}
				case 35: {
					
					comm.gem().s2f36(msg, LRACK.OK);
					break;
				}
				case 37: {
					
					comm.gem().s2f38(msg, ERACK.OK);
					break;
				}
				default: {
					
					sendSxF0(comm, msg, strm);
					comm.gem().s9f5(msg);
				}
				}
				
				break;
			}
			case 6: {
				
				switch ( func ) {
				case 15: {
					
					/* Dummy-Reply */
					comm.send(
							msg,
							6, 16, false,
							Secs2.list(
									comm.gem().autoDataId(),
									msg.secs2(),
									Secs2.list(
											Secs2.list(
													Secs2.uint4(1),
													Secs2.list(
															Clock.now().toAscii16()
															)
													),
											Secs2.list(
													Secs2.uint4(2),
													Secs2.list(
															Secs2.int4(1234567890)
															)
													)
											)
									)
							);
					break;
				}
				case 17: {
					
					/* Unimplemented */
					break;
				}
				case 19: {
					
					/* Dummy-Reply*/
					comm.send(
							msg,
							6, 20, false,
							Secs2.list(
									Clock.now().toAscii16()
									)
							);
					break;
				}
				case 21: {
					
					/* Unimplemented */
					break;
				}
				default: {
					
					sendSxF0(comm, msg, strm);
					comm.gem().s9f5(msg);
				}
				}
				
				break;
			}
			default: {
				sendSxF0(comm, msg, 0);
				comm.gem().s9f3(msg);
			}
			}
		}
		catch ( SecsException e ) {
			echo(e);
		}
	}
	
	private static void sendSxF0(SecsCommunicator comm, SecsMessage primary, int strm) throws InterruptedException, SecsException {
		if ( primary.wbit() ) {
			comm.send(primary, strm, 0, false);
		}
	}
	
	private static final Object syncEcho = new Object();
	
	private static void echo(Object o) {
		synchronized ( syncEcho ) {
			if ( o instanceof Throwable ) {
				((Throwable)o).printStackTrace();
			} else {
				System.out.println(o);
			}
			System.out.println();
		}
	}
	
}
