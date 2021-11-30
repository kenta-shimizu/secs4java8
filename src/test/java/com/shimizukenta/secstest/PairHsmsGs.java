package com.shimizukenta.secstest;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

import com.shimizukenta.secs.hsms.HsmsConnectionMode;
import com.shimizukenta.secs.hsms.HsmsSession;
import com.shimizukenta.secs.hsmsgs.HsmsGsCommunicator;
import com.shimizukenta.secs.hsmsgs.HsmsGsCommunicatorConfig;
import com.shimizukenta.secs.secs2.Secs2;

public class PairHsmsGs {

	public PairHsmsGs() {
		/* Nothing */
	}
	
	public static void main(String[] args) {
		
		final int session1 = 100;
		final int session2 = 200;
		final SocketAddress addr = new InetSocketAddress("127.0.0.1", 5000);
		
		echo ("started");
		
		try {
			final HsmsGsCommunicatorConfig equipConfig = new HsmsGsCommunicatorConfig();
			equipConfig.addSessionId(session1);
			equipConfig.addSessionId(session2);
			equipConfig.socketAddress(addr);
			equipConfig.isEquip(true);
			equipConfig.logSubjectHeader("Equip: ");
			equipConfig.connectionMode(HsmsConnectionMode.PASSIVE);
			equipConfig.rebindIfPassive(5.0F);
			equipConfig.isTrySelectRequest(false);
			equipConfig.retrySelectRequestTimeout(5.0F);
			equipConfig.timeout().t3(45.0F);
			equipConfig.timeout().t6( 5.0F);
			equipConfig.timeout().t8( 5.0F);
			equipConfig.notLinktest();
//			equipConfig.linktest(2.0F);
			
			final HsmsGsCommunicatorConfig hostConfig = new HsmsGsCommunicatorConfig();
			hostConfig.addSessionId(session1);
//			hostConfig.addSessionId(session2);
			hostConfig.socketAddress(addr);
			hostConfig.isEquip(false);
			hostConfig.logSubjectHeader("Host: ");
			hostConfig.connectionMode(HsmsConnectionMode.ACTIVE);
			hostConfig.isTrySelectRequest(true);
			hostConfig.retrySelectRequestTimeout(5.0F);
			hostConfig.timeout().t3(45.0F);
			hostConfig.timeout().t5(10.0F);
			hostConfig.timeout().t6( 5.0F);
			hostConfig.timeout().t8( 5.0F);
//			hostConfig.notLinktest();
			hostConfig.linktest(3.0F);
			
			try (
					HsmsGsCommunicator equipComm = HsmsGsCommunicator.newInstance(equipConfig);
					) {
				
				equipComm.addSecsLogListener(log -> {echo(log);});
				
				equipComm.addSecsMessageReceiveListener((msg, comm) -> {
					
					int strm = msg.getStream();
					int func = msg.getFunction();
					boolean wbit = msg.wbit();
					Secs2 body = msg.secs2();
					
					try {
						switch ( comm.sessionId() ) {
						case session1: {
							switch ( strm ) {
							case 1: {
								switch ( func ) {
								case 1: {
									if ( wbit ) {
										comm.send(msg, 1, 2, false, body);
									}
									break;
								}
								}
								break;
							}
							}
							break;
						}
						case session2: {
							
							//TODO
							
							break;
						}
						default: {
						}
						}
					}
					catch ( InterruptedException ignore ) {
					}
					catch ( Throwable t ) {
						echo(t);
					}
				});
				
				equipComm.open();
				
				Thread.sleep(200L);
				
				try (
						HsmsGsCommunicator hostComm = HsmsGsCommunicator.newInstance(hostConfig);
						) {
					
					hostComm.addSecsLogListener(log -> {echo(log);});
					
					hostComm.open();
					
					equipComm.getSession(session1).waitUntilCommunicatable();
					
					HsmsSession hostSession1 = hostComm.getSession(session1);
					hostSession1.waitUntilCommunicatable();
					
					hostSession1.send(1, 1, true, Secs2.list());
					
					Thread.sleep(509L);
					
					hostComm.send(session1, 1, 1, true, Secs2.binary((byte)0xFF));
					
					Thread.sleep(1000L);
				}
			}
		}
		catch ( InterruptedException ignore ) {
		}
		catch ( Throwable t ) {
			echo(t);
		}
		
		echo("finished.");
	}
	
	private static final Object syncEcho = new Object();
	
	private static void echo(Object o) {
		synchronized ( syncEcho ) {
			if ( o instanceof Throwable ) {
				final Throwable t = (Throwable)o;
				try (
						StringWriter sw = new StringWriter();
						) {
					try (
							PrintWriter pw = new PrintWriter(sw);
							) {
						t.printStackTrace(pw);
						pw.flush();
						System.out.println(sw.toString());
					}
				}
				catch ( IOException e ) {
					System.out.println("IOException");
				}

			} else {
				System.out.println(o);
			}
			System.out.println();
		}
	}

}
