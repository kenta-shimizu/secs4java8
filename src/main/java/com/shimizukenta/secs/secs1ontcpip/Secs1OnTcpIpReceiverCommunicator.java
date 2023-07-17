package com.shimizukenta.secs.secs1ontcpip;

import java.io.IOException;

import com.shimizukenta.secs.secs1.Secs1Communicator;
import com.shimizukenta.secs.secs1ontcpip.impl.AbstractSecs1OnTcpIpReceiverCommunicator;

/**
 * This instance is implementation of SECS-I (SEMI-E4) on TCP/IP-Receiver.
 * 
 * <ul>
 * <li>To create new instance, {@link #newInstance(Secs1OnTcpIpReceiverCommunicatorConfig)}</li>
 * <li>To create new instance and open, {@link #open(Secs1OnTcpIpReceiverCommunicatorConfig)}</li>
 * </ul>
 * 
 * @author kenta-shimizu
 *
 */
public interface Secs1OnTcpIpReceiverCommunicator extends Secs1Communicator {

	/**
	 * Create SECS-I-on-TCP/IP-Receiver instance.
	 * 
	 * @param config the SECS-I-on-TCP/IP Receiver config
	 * @return new Secs1OnTcpIpReceiver instance
	 */
	public static Secs1OnTcpIpReceiverCommunicator newInstance(Secs1OnTcpIpReceiverCommunicatorConfig config) {
		return new AbstractSecs1OnTcpIpReceiverCommunicator(config) {};
	}
	
	/**
	 * Create SECS-I-on-Tcp/IP-Receiver instance and {@link #open()}.
	 * 
	 * @param config the SECS-I-on-TCP/IP Receiver config
	 * @return new Secs1OnTcpIpReciever instance
	 * @throws IOException if open failed
	 */
	public static Secs1OnTcpIpReceiverCommunicator open(Secs1OnTcpIpReceiverCommunicatorConfig config) throws IOException {
		
		final Secs1OnTcpIpReceiverCommunicator inst = newInstance(config);
		
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
	
}
