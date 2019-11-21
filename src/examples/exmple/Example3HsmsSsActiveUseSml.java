package exmple;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Optional;

import secs.SecsException;
import secs.SecsMessage;
import secs.gem.ONLACK;
import secs.hsmsSs.HsmsSsCommunicator;
import secs.hsmsSs.HsmsSsCommunicatorConfig;
import secs.hsmsSs.HsmsSsProtocol;
import secs.secs2.Secs2Exception;
import secs.sml.SmlMessage;
import secs.sml.SmlMessageParser;
import secs.sml.SmlParseException;

public class Example3HsmsSsActiveUseSml {

	public Example3HsmsSsActiveUseSml() {
		/* Nothing */
	}
	
	/*
	 * Example-3 (Use SML)
	 * 1. open ACTIVE-instance
	 * 3. wait until SELECTED
	 * 3. get SmlMessageParser
	 * 4. send S1F13
	 * 5. send S1F17
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
						
						/* get SmlMessageParser */
						SmlMessageParser parser = SmlMessageParser.getInstance();
						
						{
							/* parse S1F13 <L[0] >. */
							SmlMessage sm = parser.parse("S1F13 W <L >.");
							
							/* send */
							comm.send(sm);
						}
						
						{
							/* parse S1F17 W. */
							SmlMessage sm = parser.parse("S1F17 W.");
							
							/* send */
							Optional<SecsMessage> op = comm.send(sm);
							
							ONLACK onlack = ONLACK.get(op.get().secs2());
							
							System.out.println("ONLACK: " + onlack);
						}
					}
				}
				catch ( InterruptedException ignore ) {
				}
				catch ( SecsException | Secs2Exception | SmlParseException e ) {
					e.printStackTrace();
				}
			});
			
			comm.open();
			
			
			synchronized ( Example3HsmsSsActiveUseSml.class ) {
				Example3HsmsSsActiveUseSml.class.wait();
			}
		}
		catch ( InterruptedException ignore ) {
		}
		catch ( IOException e ) {
			e.printStackTrace();
		}
	}

}
