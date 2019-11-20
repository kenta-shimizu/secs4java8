package exmple;

import java.io.IOException;
import java.net.InetSocketAddress;

import secs.SecsException;
import secs.hsmsSs.HsmsSsCommunicator;
import secs.hsmsSs.HsmsSsCommunicatorConfig;
import secs.hsmsSs.HsmsSsProtocol;
import secs.secs2.Secs2Exception;

public class Example2HsmsSsActive {

	public Example2HsmsSsActive() {
		/* Nothing */
	}
	
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
						
						comm.gem().s1f13();
						
						System.out.println("ONLACK: " + comm.gem().s1f17());
					}
				}
				catch ( InterruptedException ignore ) {
				}
				catch ( SecsException | Secs2Exception e ) {
					e.printStackTrace();
				}
			});
			
			comm.open();
			
			
			synchronized ( Example2HsmsSsActive.class ) {
				Example2HsmsSsActive.class.wait();
			}
		}
		catch ( InterruptedException ignore ) {
		}
		catch ( IOException e ) {
			e.printStackTrace();
		}
		
	}

}
