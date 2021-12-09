package example6;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

import com.shimizukenta.secs.hsms.HsmsConnectionMode;
import com.shimizukenta.secs.hsms.HsmsSession;
import com.shimizukenta.secs.hsmsgs.HsmsGsCommunicator;
import com.shimizukenta.secs.hsmsgs.HsmsGsCommunicatorConfig;

/**
 * Example-6.
 * 
 * <p>
 * Run 'example6/ExampleHsmsGsPassive.java' with this, too.
 * </p>
 * <p>
 * This example is HSMS-GS Active Host.<br />
 * Add Two Session.
 * This example try-connect repeatedly until 'SELECTED' with T5-timeout intervals.<br />
 * </p>
 * 
 * @author kenta-shimizu
 *
 */
public class ExampleHsmsGsActiveTwo {

	public ExampleHsmsGsActiveTwo() {
		/* Nothing */
	}

	private static final int SESSION_B = 200;
	private static final int SESSION_C = 300;
	
	private static final SocketAddress IPADDR = new InetSocketAddress("127.0.0.1", 5000);
	
	public static void main(String[] args) {
		
		try {
			
			HsmsGsCommunicatorConfig config = new HsmsGsCommunicatorConfig();
			
			config.addSessionId(SESSION_B);
			config.addSessionId(SESSION_C);
			config.socketAddress(IPADDR);
			config.isEquip(false);
			config.connectionMode(HsmsConnectionMode.ACTIVE);
			config.isTrySelectRequest(true);
			config.retrySelectRequestTimeout(5.0F);
			config.timeout().t3(45.0F);
			config.timeout().t5(10.0F);
			config.timeout().t6( 5.0F);
			config.timeout().t8( 5.0F);
			config.notLinktest();
			config.linktest(120.0F);
			
			try (
					HsmsGsCommunicator active = HsmsGsCommunicator.newInstance(config);
					) {
				
				active.addSecsLogListener(log -> {
					echo(log);
				});
				
				active.open();
				
				{
					HsmsSession session = active.getSession(SESSION_B);
					
					session.waitUntilCommunicatable();
					Thread.sleep(200L);
					
					session.gem().s1f13();
					Thread.sleep(200L);
					
					session.gem().s1f17();
					Thread.sleep(200L);
					
					session.gem().s1f15();
					Thread.sleep(200L);
				}
				
				{
					HsmsSession session = active.getSession(SESSION_C);
					
					session.waitUntilCommunicatable();
					Thread.sleep(200L);
					
					session.gem().s1f13();
					Thread.sleep(200L);
					
					session.gem().s1f17();
					Thread.sleep(200L);
					
					session.gem().s1f15();
					Thread.sleep(200L);
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
