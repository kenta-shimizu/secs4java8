package example6;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

import com.shimizukenta.secs.SecsException;
import com.shimizukenta.secs.gem.COMMACK;
import com.shimizukenta.secs.gem.ONLACK;
import com.shimizukenta.secs.hsms.HsmsConnectionMode;
import com.shimizukenta.secs.hsmsgs.HsmsGsCommunicator;
import com.shimizukenta.secs.hsmsgs.HsmsGsCommunicatorConfig;
import com.shimizukenta.secs.secs2.Secs2;

/**
 * Exampe-6.
 * 
 * <p>
 * This example is HSMS-GS Passive Equipment.<br />
 * Bind SocketAddress and waiting request.<br />
 * Receiving Primary-Message and reply.<br />
 * Add 3 Session.<br />
 * </p>
 * 
 * @author kenta-shimizu
 *
 */
public class ExampleHsmsGsPassive {

	public ExampleHsmsGsPassive() {
		/* Nothing */
	}
	
	private static final int SESSION_A = 100;
	private static final int SESSION_B = 200;
	private static final int SESSION_C = 300;
	
	private static final SocketAddress IPADDR = new InetSocketAddress("127.0.0.1", 5000);
	
	public static void main(String[] args) {
		
		try {
			
			HsmsGsCommunicatorConfig config = new HsmsGsCommunicatorConfig();
			
			config.addSessionId(SESSION_A);
			config.addSessionId(SESSION_B);
			config.addSessionId(SESSION_C);
			config.socketAddress(IPADDR);
			config.isEquip(true);
			config.connectionMode(HsmsConnectionMode.PASSIVE);
			config.rebindIfPassive(5.0F);
			config.isTrySelectRequest(false);
			config.timeout().t3(45.0F);
			config.timeout().t6( 5.0F);
			config.timeout().t8( 5.0F);
			config.notLinktest();
			
			try (
					HsmsGsCommunicator passive = HsmsGsCommunicator.newInstance(config);
					) {
				
				passive.addSecsLogListener(log -> {
					echo(log);
				});
				
				passive.getSession(SESSION_A).addSecsMessageReceiveListener((msg, session) -> {
					
					int strm = msg.getStream();
					int func = msg.getFunction();
					boolean wbit = msg.wbit();
					
					try {
						
						switch (strm) {
						case 1: {
							switch ( func ) {
							case 13: {
								session.send(
										msg,
										1, 14, false,
										Secs2.list(
												COMMACK.OK.secs2(),
												Secs2.list(
														Secs2.ascii("MDLN-A"),
														Secs2.ascii("100000")
														)
												)
										);
								break;
							}
							case 15: {
								session.gem().s1f16(msg);
								break;
							}
							case 17: {
								session.gem().s1f18(msg, ONLACK.OK);
								break;
							}
							default: {
								if ( wbit ) {
									session.send(msg, strm, 0, false);
								}
								session.gem().s9f5(msg);
							}
							}
							break;
						}
						default: {
							if ( wbit ) {
								session.send(msg, 0, 0, false);
							}
							session.gem().s9f3(msg);
						}
						}
					}
					catch ( InterruptedException ignore ) {
					}
					catch ( SecsException e ) {
						echo(e);
					}
				});
				
				passive.getSession(SESSION_B).addSecsMessageReceiveListener((msg, session) -> {
					
					int strm = msg.getStream();
					int func = msg.getFunction();
					boolean wbit = msg.wbit();
					
					try {
						
						switch (strm) {
						case 1: {
							switch ( func ) {
							case 13: {
								session.send(
										msg,
										1, 14, false,
										Secs2.list(
												COMMACK.OK.secs2(),
												Secs2.list(
														Secs2.ascii("MDLN-B"),
														Secs2.ascii("100000")
														)
												)
										);
								break;
							}
							case 15: {
								session.gem().s1f16(msg);
								break;
							}
							case 17: {
								session.gem().s1f18(msg, ONLACK.OK);
								break;
							}
							default: {
								if ( wbit ) {
									session.send(msg, strm, 0, false);
								}
								session.gem().s9f5(msg);
							}
							}
							break;
						}
						default: {
							if ( wbit ) {
								session.send(msg, 0, 0, false);
							}
							session.gem().s9f3(msg);
						}
						}
					}
					catch ( InterruptedException ignore ) {
					}
					catch ( SecsException e ) {
						echo(e);
					}
				});
				
				passive.getSession(SESSION_C).addSecsMessageReceiveListener((msg, session) -> {
					
					int strm = msg.getStream();
					int func = msg.getFunction();
					boolean wbit = msg.wbit();
					
					try {
						
						switch (strm) {
						case 1: {
							switch ( func ) {
							case 13: {
								session.send(
										msg,
										1, 14, false,
										Secs2.list(
												COMMACK.OK.secs2(),
												Secs2.list(
														Secs2.ascii("MDLN-C"),
														Secs2.ascii("100000")
														)
												)
										);
								break;
							}
							case 15: {
								session.gem().s1f16(msg);
								break;
							}
							case 17: {
								session.gem().s1f18(msg, ONLACK.OK);
								break;
							}
							default: {
								if ( wbit ) {
									session.send(msg, strm, 0, false);
								}
								session.gem().s9f5(msg);
							}
							}
							break;
						}
						default: {
							if ( wbit ) {
								session.send(msg, 0, 0, false);
							}
							session.gem().s9f3(msg);
						}
						}
					}
					catch ( InterruptedException ignore ) {
					}
					catch ( SecsException e ) {
						echo(e);
					}
				});
				
				passive.open();
				
				synchronized ( ExampleHsmsGsPassive.class ) {
					ExampleHsmsGsPassive.class.wait();
				}
			}
		}
		catch ( InterruptedException ignore ) {
		}
		catch ( Throwable t ) {
			echo(t);
		}
	}
	
	private static final Object syncEcho = new Object();
	
	private static void echo(Object o) {
		
		synchronized ( syncEcho ) {
			
			if ( o instanceof Throwable ) {
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
				}
				
			} else {
				System.out.println(o);
			}
			
			System.out.println();
		}
	}

}
