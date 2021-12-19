package com.shimizukenta.secs.secs1ontcpip;

import java.io.IOException;

import com.shimizukenta.secs.secs1.Secs1Communicator;

/**
 * This instance is implementation of SECS-I (SEMI-E4) on TCP/IP.
 * 
 * <ul>
 * <li>To create new instance, {@link #newInstance(Secs1OnTcpIpCommunicatorConfig)}</li>
 * <li>To create new instance and open, {@link #open(Secs1OnTcpIpCommunicatorConfig)}</li>
 * </ul>
 * 
 * @author kenta-shimizu
 *
 */
public interface Secs1OnTcpIpCommunicator extends Secs1Communicator {
	
	/**
	 * Create SECS-I-on-TCP/IP instance.
	 * 
	 * @param config
	 * @return new Secs1OnTcpIp instance
	 */
	public static Secs1OnTcpIpCommunicator newInstance(Secs1OnTcpIpCommunicatorConfig config) {
		return new AbstractSecs1OnTcpIpCommunicator(config) {};
	}
	
	/**
	 * Create SECS-I-on-Tcp/IP instance and {@link #open()}.
	 * 
	 * @param config
	 * @return new Secs1OnTcpIp instance
	 * @throws IOException
	 */
	public static Secs1OnTcpIpCommunicator open(Secs1OnTcpIpCommunicatorConfig config) throws IOException {
		
		final Secs1OnTcpIpCommunicator inst = newInstance(config);
		
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
