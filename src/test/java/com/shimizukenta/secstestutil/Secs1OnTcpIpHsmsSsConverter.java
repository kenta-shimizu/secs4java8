package com.shimizukenta.secstestutil;

import java.io.Closeable;
import java.io.IOException;

import com.shimizukenta.secs.SecsException;
import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.hsmsss.HsmsSsCommunicator;
import com.shimizukenta.secs.hsmsss.HsmsSsCommunicatorConfig;
import com.shimizukenta.secs.hsmsss.HsmsSsMessage;
import com.shimizukenta.secs.hsmsss.HsmsSsMessageType;
import com.shimizukenta.secs.secs1.Secs1Communicator;
import com.shimizukenta.secs.secs1.Secs1Message;
import com.shimizukenta.secs.secs1.Secs1TooBigSendMessageException;
import com.shimizukenta.secs.secs1ontcpip.Secs1OnTcpIpCommunicator;
import com.shimizukenta.secs.secs1ontcpip.Secs1OnTcpIpCommunicatorConfig;
import com.shimizukenta.secs.secs1ontcpip.Secs1OnTcpIpNotConnectedException;

/**
 * 
 * run like SH-2000
 *
 */
public class Secs1OnTcpIpHsmsSsConverter implements Closeable {
	
	public static final byte REJECT_BY_OVERFLOW = (byte)0x80;
	public static final byte REJECT_BY_NOT_CONNECT = (byte)0x81;
	
	private final Secs1Communicator secs1;
	private final HsmsSsCommunicator hsmsSs;
	private boolean closed;
	
	public Secs1OnTcpIpHsmsSsConverter(
			Secs1OnTcpIpCommunicatorConfig secs1Config,
			HsmsSsCommunicatorConfig hsmsSsConfig) {
		
		this.secs1 = Secs1OnTcpIpCommunicator.newInstance(secs1Config);
		this.hsmsSs = HsmsSsCommunicator.newInstance(hsmsSsConfig);
		this.closed = false;
	}
	
	public void open() throws IOException {
		
		synchronized ( this ) {
			if ( closed ) {
				throw new IOException("Already closed");
			}
		}
		
		secs1.addSecsMessageReceiveListener(msg -> {
			
			byte[] head = createToHsmsSsHead(msg);
			
			try {
				hsmsSs.send(hsmsSs.createHsmsSsMessage(head, msg.secs2()))
				.ifPresent(this::replyToSecs1);
			}
			catch ( InterruptedException ignore ) {
			}
			catch ( SecsException giveup ) {
			}
		});
		
		hsmsSs.addSecsMessageReceiveListener(msg -> {
			
			byte[] head = createToSecs1Head(msg);
			
			try {
				secs1.send(secs1.createSecs1Message(head, msg.secs2()))
				.ifPresent(this::replyToHsmsSs);
			}
			catch ( InterruptedException ignore ) {
			}
			catch ( Secs1OnTcpIpNotConnectedException e ) {
				rejectToHsmsSs(msg, REJECT_BY_NOT_CONNECT);
			}
			catch ( Secs1TooBigSendMessageException e ) {
				rejectToHsmsSs(msg, REJECT_BY_OVERFLOW);
			}
			catch ( SecsException giveup ) {
			}
		});
		
		secs1.open();
		hsmsSs.open();
	}
	
	@Override
	public void close() throws IOException {
		
		synchronized ( this ) {
			if ( closed) {
				return;
			}
			
			this.closed = true;
		}
		
		IOException ioExcept = null;
		
		try {
			secs1.close();
		}
		catch ( IOException e ) {
			ioExcept = e;
		}
		
		try {
			hsmsSs.close();
		}
		catch ( IOException e ) {
			ioExcept = e;
		}
		
		if ( ioExcept != null ) {
			throw ioExcept;
		}
	}
	
	private byte[] createToSecs1Head(SecsMessage msg) {
		
		byte[] ref = msg.header10Bytes();
		
		byte[] head = new byte[] {
				ref[0],
				ref[1],
				ref[2],
				ref[3],
				(byte)0x0,
				(byte)0x0,
				ref[6],
				ref[7],
				ref[8],
				ref[9]
		};
		
		if ( secs1.isEquip() ) {
			head[0] |= (byte)0x80;
		}
		
		return head;
	}
	
	private byte[] createToHsmsSsHead(SecsMessage msg) {
		
		byte[] ref = msg.header10Bytes();
		
		byte[] head = new byte[] {
				(byte)(ref[0] & 0x7F),
				ref[1],
				ref[2],
				ref[3],
				(byte)0x0,
				(byte)0x0,
				ref[6],
				ref[7],
				ref[8],
				ref[9]
		};
		
		return head;
	}
	
	private void replyToSecs1(HsmsSsMessage msg) {
		
		try {
			byte[] head = createToSecs1Head(msg);
			secs1.send(secs1.createSecs1Message(head, msg.secs2()));
		}
		catch ( InterruptedException ignore ) {
		}
		catch ( SecsException giveup ) {
		}
	}
	
	private void replyToHsmsSs(Secs1Message msg) {
		
		try {
			byte[] head = createToHsmsSsHead(msg);
			hsmsSs.send(hsmsSs.createHsmsSsMessage(head, msg.secs2()));
		}
		catch ( InterruptedException ignore ) {
		}
		catch ( SecsException giveup ) {
		}
	}
	
	private void rejectToHsmsSs(SecsMessage primary, byte reason) {
		
		byte[] ref = primary.header10Bytes();
		HsmsSsMessageType mt = HsmsSsMessageType.REJECT_REQ;
		
		byte[] head = new byte[] {
				(byte)0xFF,
				(byte)0xFF,
				(byte)0x0,
				reason,
				mt.pType(),
				mt.sType(),
				ref[6],
				ref[7],
				ref[8],
				ref[9]
		};
		
		try {
			hsmsSs.send(hsmsSs.createHsmsSsMessage(head));
		}
		catch ( InterruptedException ignore ) {
		}
		catch ( SecsException giveup ) {
		}
	}
	
	public static Secs1OnTcpIpHsmsSsConverter open(
			Secs1OnTcpIpCommunicatorConfig secs1Config,
			HsmsSsCommunicatorConfig hsmsSsConfig)
					throws IOException {
		
		final Secs1OnTcpIpHsmsSsConverter inst = new Secs1OnTcpIpHsmsSsConverter(secs1Config, hsmsSsConfig);
		
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
