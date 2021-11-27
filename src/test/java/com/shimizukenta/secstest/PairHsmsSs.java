package com.shimizukenta.secstest;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.shimizukenta.secs.SecsCommunicator;
import com.shimizukenta.secs.SecsException;
import com.shimizukenta.secs.SecsThrowableLog;
import com.shimizukenta.secs.gem.ACKC6;
import com.shimizukenta.secs.gem.COMMACK;
import com.shimizukenta.secs.gem.ONLACK;
import com.shimizukenta.secs.hsms.HsmsConnectionMode;
import com.shimizukenta.secs.hsmsss.HsmsSsCommunicator;
import com.shimizukenta.secs.hsmsss.HsmsSsCommunicatorConfig;
import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.secs2.Secs2Exception;

public class PairHsmsSs {
	
	private static final int testCycle = 1000;
	
	public int equipCounter;
	public int hostCounter;
	
	public PairHsmsSs() {
		this.equipCounter = 0;
		this.hostCounter = 0;
	}
	
	public static void main(String[] args) {
		
		List<Throwable> tt = new CopyOnWriteArrayList<>();
		
		long start = System.currentTimeMillis();
		
		SocketAddress addr = new InetSocketAddress("127.0.0.1", 5000);
		
		final HsmsSsCommunicatorConfig equipConfig = new HsmsSsCommunicatorConfig();
		final HsmsSsCommunicatorConfig hostConfig  = new HsmsSsCommunicatorConfig();
		
		equipConfig.logSubjectHeader("Equip: ");
		equipConfig.connectionMode(HsmsConnectionMode.PASSIVE);
		equipConfig.socketAddress(addr);
		equipConfig.sessionId(10);
		equipConfig.isEquip(true);
		equipConfig.rebindIfPassive(5.0F);
		equipConfig.timeout().t3(10.0F);
		equipConfig.linktest(6.0F);
		equipConfig.gem().mdln("MDLN-A");
		equipConfig.gem().softrev("000001");
		
		hostConfig.logSubjectHeader("Host: ");
		hostConfig.connectionMode(HsmsConnectionMode.ACTIVE);
		hostConfig.socketAddress(addr);
		hostConfig.sessionId(10);
		hostConfig.isEquip(false);
		hostConfig.timeout().t3(10.0F);
		hostConfig.linktest(6.0F);
		
		
		final PairHsmsSs inst = new PairHsmsSs();
		
		try (
			SecsCommunicator equip = HsmsSsCommunicator.newInstance(equipConfig);
		) {
			
			equip.addSecsLogListener(PairHsmsSs::echo);
			
			equip.addSecsLogListener(log -> {
				if ( log instanceof SecsThrowableLog ) {
					tt.add(((SecsThrowableLog) log).getCause());
				}
			});
			
			equip.addTrySendMessagePassThroughListener(msg -> {
				echo("equip-pt-trysnd: strm: " + msg.getStream() + ", func: " + msg.getFunction());
			});
			equip.addSendedMessagePassThroughListener(msg -> {
				echo("equip-pt-sended: strm: " + msg.getStream() + ", func: " + msg.getFunction());
			});
			equip.addReceiveMessagePassThroughListener(msg -> {
				echo("equip-pt-recved: strm: " + msg.getStream() + ", func: " + msg.getFunction());
			});
			
			equip.addSecsMessageReceiveListener(msg -> {
				
				try {
					
					int strm = msg.getStream();
					int func = msg.getFunction();
					boolean wbit = msg.wbit();
					
					if ( wbit ) {
						
						switch ( strm ) {
						case 1: {
							
							switch ( func ) {
							case 1: {
								
								equip.gem().s1f2(msg);
								break;
							}
							case 13: {
								
								equip.gem().s1f14(msg, COMMACK.OK);
								break;
							}
							case 15: {
								
								equip.gem().s1f16(msg);
								break;
							}
							case 17: {
								
								equip.gem().s1f18(msg, ONLACK.OK);
								break;
							}
							}
							break;
						}
						}
					}
				}
				catch ( SecsException e ) {
					echo(e);
				}
				catch ( InterruptedException ignore ) {
				}
			});
			
			equip.open();
			
			Thread.sleep(500L);
			
			try (
					SecsCommunicator host = HsmsSsCommunicator.newInstance(hostConfig);
					) {
				
				host.addSecsLogListener(PairHsmsSs::echo);
				
				host.addSecsLogListener(log -> {
					if ( log instanceof SecsThrowableLog ) {
						tt.add(((SecsThrowableLog) log).getCause());
					}
				});
				
				host.addTrySendMessagePassThroughListener(msg -> {
					echo("host-pt-trysnd: strm: " + msg.getStream() + ", func: " + msg.getFunction());
				});
				host.addSendedMessagePassThroughListener(msg -> {
					echo("host-pt-sended: strm: " + msg.getStream() + ", func: " + msg.getFunction());
				});
				host.addReceiveMessagePassThroughListener(msg -> {
					echo("host-pt-recved: strm: " + msg.getStream() + ", func: " + msg.getFunction());
				});
				
				host.addSecsMessageReceiveListener(msg -> {
					
					int strm = msg.getStream();
					int func = msg.getFunction();
					boolean wbit = msg.wbit();
					
					if ( wbit ) {
						
						try {
							switch ( strm ) {
							case 1: {
								
								switch ( func ) {
								case 1: {
									
									host.gem().s1f2(msg);
									break;
								}
								case 13: {
									
									host.gem().s1f14(msg, COMMACK.OK);
									break;
								}
								}
								break;
							}
							case 6: {
								
								switch ( func ) {
								case 3: {
									
									host.gem().s6f4(msg, ACKC6.OK);
									break;
								}
								}
								break;
							}
							}
						}
						catch ( SecsException e ) {
							echo(e);
						}
						catch ( InterruptedException ignore ) {
						}
					}
				});
				
				
				host.openAndWaitUntilCommunicatable();
				
				Thread.sleep(500L);
				
				final int m = testCycle;
				
				final Collection<Thread> threads = new ArrayList<>();
				
				threads.addAll(Arrays.asList(
						new Thread(() -> {
							
							try {
								
								for ( ; inst.equipCounter < m; ) {
									
									try {
										equip.gem().s1f13();
									}
									catch ( Secs2Exception e ) {
										echo(e);
									}
									
									Thread.sleep(1L);
									
									equip.gem().s1f1();
									Thread.sleep(1L);
									
									++ inst.equipCounter;
									echo("equip-count: " + inst.equipCounter);
								}
								
								equip.send(6, 3, true, createS6F3Secs2());
							}
							catch ( SecsException e ) {
								
								threads.forEach(Thread::interrupt);
								echo(e);
								
								throw new RuntimeException(e);
							}
							catch ( InterruptedException e ) {
							}
						}),
						new Thread(() -> {
							
							try {
								for (; inst.hostCounter < m; ) {
									
									try {
										host.gem().s1f13();
									}
									catch ( Secs2Exception e ) {
										echo(e);
									}
									
									Thread.sleep(1L);
									
									try {
										host.gem().s1f17();
									}
									catch ( Secs2Exception e ) {
										echo(e);
									}
									Thread.sleep(1L);
									
									try {
										host.gem().s1f15();
									}
									catch ( Secs2Exception e ) {
										echo(e);
									}
									Thread.sleep(1L);
									
									++ inst.hostCounter;
									echo("host-count: " + inst.hostCounter);
								}
							}
							catch ( SecsException e ) {
								
								threads.forEach(Thread::interrupt);
								echo(e);
								
								throw new RuntimeException(e);
							}
							catch ( InterruptedException e ) {
							}
						})
						));
				
				
				threads.forEach(Thread::start);
				
				for ( Thread th : threads ) {
					th.join();
				}
			}
			
			Thread.sleep(500L);
		}
		catch ( InterruptedException ignore ) {
		}
		catch ( Throwable t ) {
			echo(t);
		}
		
		tt.forEach(t -> {
			echo(t);
		});
		
		echo("reach end");
		echo("equip-count: " + inst.equipCounter);
		echo("host-count: " + inst.hostCounter);
		echo("throwables: " + tt.size());
		
		long end = System.currentTimeMillis();
		
		echo("elapsed: " + (end - start) + " milli-sec.");
	}
	
	private static synchronized void echo(Object o) {
		
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
	
	protected static Secs2 createS6F3Secs2() {
		
		Secs2 v = Secs2.list(
				Secs2.int4(-100, -200, -300, -400, -500),
				Secs2.uint4(100, 200, 300, 400, 500),
				Secs2.int8(-1000, -2000, -3000, -4000, -5000),
				Secs2.uint8(1000, 2000, 3000, 4000, 5000),
				Secs2.float4(-0.10F, -0.20F, -0.30F, -0.40F, -0.50F),
				Secs2.float8(-0.010D, -0.020D, -0.030D, -0.040D, -0.050D)
				);
		
		Secs2 vv = Secs2.list(
				Secs2.ascii("values"),
				v, v, v, v, v,
				v, v, v, v, v
				);

		Secs2 ss = Secs2.list(
				Secs2.ascii("ASCII"),
				Secs2.binary((byte)0x1),
				Secs2.list(
						Secs2.binary((byte)0xFF, (byte)0x0, (byte)0x1, (byte)0x2, (byte)0x3),
						Secs2.int1(-1, -2, -3, -4, -5),
						Secs2.uint1(1, 2, 3, 4, 5),
						Secs2.int2(-10, -20, -30, -40, -50),
						Secs2.uint2(10, 20, 30, 40, 50)
						),
				Secs2.bool(true),
				Secs2.int4(100, 200, 300, 400, 500),
				Secs2.float4(0.10F, 0.20F, 0.30F, 0.40F, 0.50F),
				vv
				);
		
		return ss;
	}
	
}
