package exmple;

import java.io.IOException;
import java.net.InetSocketAddress;

import secs.SecsException;
import secs.gem.ONLACK;
import secs.hsmsSs.HsmsSsCommunicator;
import secs.hsmsSs.HsmsSsCommunicatorConfig;
import secs.hsmsSs.HsmsSsProtocol;
import secs.secs2.Secs2Exception;

public class Example4HsmsSsActiveUseGem {

	public Example4HsmsSsActiveUseGem() {
		/* Nothing */
	}
	
	/*
	 * Example-4
	 * 1. open ACTIVE-instance
	 * 2. wait until SELECTED
	 * 3. send S1F13
	 * 4. send S1F17
	 * 
	 */

	public static void main(String[] args) {
		
		HsmsSsCommunicatorConfig config = new HsmsSsCommunicatorConfig();
		
		config.socketAddress(new InetSocketAddress("127.0.0.1", 5000));
		config.protocol(HsmsSsProtocol.ACTIVE);
		config.sessionId(10);
		config.isEquip(false);
		config.linktest(60.0F);
		config.timeout().t3(45.0F);
		config.timeout().t5(10.0F);
		config.timeout().t6( 5.0F);
		config.timeout().t8( 6.0F);
		
		try (
				HsmsSsCommunicator comm = HsmsSsCommunicator.newInstance(config);
				) {
			
			comm.addSecsLogListener(System.out::println);
			
			comm.addSecsMessageReceiveListener(msg -> {
				
				/* Nothing */
				
			});
			
			comm.addSecsCommunicatableStateChangeListener(state -> {
				
				try {
					
					if ( state /* SELECTED */ ) {
						
						/* send S1F13 W */
						comm.gem().s1f13();
						
						/* send S1F17 W */
						ONLACK onlack = comm.gem().s1f17();
							
						System.out.println("ONLACK: " + onlack);
					}
				}
				catch ( InterruptedException ignore ) {
				}
				catch ( SecsException | Secs2Exception e ) {
					e.printStackTrace();
				}
			});
			
			comm.open();
			
			
			synchronized ( Example4HsmsSsActiveUseGem.class ) {
				Example4HsmsSsActiveUseGem.class.wait();
			}
		}
		catch ( InterruptedException ignore ) {
		}
		catch ( IOException e ) {
			e.printStackTrace();
		}
	}

}
