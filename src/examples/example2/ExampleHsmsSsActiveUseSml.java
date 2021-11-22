package example2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Optional;

import com.shimizukenta.secs.SecsException;
import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.gem.ONLACK;
import com.shimizukenta.secs.hsms.HsmsConnectionMode;
import com.shimizukenta.secs.hsmsss.HsmsSsCommunicator;
import com.shimizukenta.secs.hsmsss.HsmsSsCommunicatorConfig;
import com.shimizukenta.secs.secs2.Secs2Exception;
import com.shimizukenta.secs.sml.SmlMessage;
import com.shimizukenta.secs.sml.SmlMessageParser;
import com.shimizukenta.secs.sml.SmlParseException;

/**
 * Example-2 (Use SML).
 * 
 * <p>
 * Use 'example1/ExampleHsmsSsPassive.java' with this, too.
 * </p>
 * <p>
 * This example is HSMS-SS Active Host.<br />
 * This example try-connect repeatedly until 'SELECTED' with T5-timeout intervals.<br />
 * </p>
 * 
 * @author kenta-shimizu
 *
 */
public class ExampleHsmsSsActiveUseSml {

	public ExampleHsmsSsActiveUseSml() {
		/* Nothing */
	}
	
	/*
	 * Example-2 (Use SML)
	 * 1. open ACTIVE-instance
	 * 2. wait until SELECTED
	 * 3. get SmlMessageParser
	 * 4. send S1F13
	 * 5. send S1F17
	 * 
	 */
	
	public static void main(String[] args) {
		
		HsmsSsCommunicatorConfig config = new HsmsSsCommunicatorConfig();
		
		config.socketAddress(new InetSocketAddress("127.0.0.1", 5000));
		config.connectionMode(HsmsConnectionMode.ACTIVE);
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
			
			
			synchronized ( ExampleHsmsSsActiveUseSml.class ) {
				ExampleHsmsSsActiveUseSml.class.wait();
			}
		}
		catch ( InterruptedException ignore ) {
		}
		catch ( IOException e ) {
			e.printStackTrace();
		}
	}

}
