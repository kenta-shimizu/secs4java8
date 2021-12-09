package com.shimizukenta.secstestutil;

import java.io.Closeable;
import java.io.IOException;

import com.shimizukenta.secs.SecsException;
import com.shimizukenta.secs.SecsLogListener;
import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.hsms.HsmsMessage;
import com.shimizukenta.secs.hsms.HsmsMessageType;
import com.shimizukenta.secs.hsmsss.HsmsSsCommunicator;
import com.shimizukenta.secs.hsmsss.HsmsSsCommunicatorConfig;
import com.shimizukenta.secs.hsmsss.HsmsSsMessageBuilder;
import com.shimizukenta.secs.secs1.Secs1Message;
import com.shimizukenta.secs.secs1.Secs1MessageBuilder;
import com.shimizukenta.secs.secs1.Secs1TooBigSendMessageException;
import com.shimizukenta.secs.secs1ontcpip.Secs1OnTcpIpCommunicator;
import com.shimizukenta.secs.secs1ontcpip.Secs1OnTcpIpCommunicatorConfig;

public class Secs1OnTcpIpHsmsSsConverter implements Closeable {
	
	public static final byte REJECT_BY_OVERFLOW = (byte)0x80;
	public static final byte REJECT_BY_NOT_CONNECT = (byte)0x81;
	
	private final HsmsSsCommunicator hsmsSsComm;
	private final Secs1OnTcpIpCommunicator secs1Comm;
	private boolean opened;
	private boolean closed;
	
	public Secs1OnTcpIpHsmsSsConverter(
			Secs1OnTcpIpCommunicatorConfig secs1Config,
			HsmsSsCommunicatorConfig hsmsSsConfig
			) {
		
		this.opened = false;
		this.closed = false;
		
		this.secs1Comm = Secs1OnTcpIpCommunicator.newInstance(secs1Config);
		this.hsmsSsComm = HsmsSsCommunicator.newInstance(hsmsSsConfig);
		
		this.secs1Comm.addSecsMessageReceiveListener(msg -> {
			
			try {
				
				try {
					
					this.hsmsSsComm.send(toHsmsMessageFromSecs1Message(msg))
					.filter(r -> r.isDataMessage())
					.ifPresent(r -> {
						
						try {
							try {
								this.secs1Comm.send(
										toSecs1MessageFromHsmsMessage(
												r,
												this.secs1Comm.isEquip()
												)
										);
							}
							catch ( SecsException nothing ) {
							}
						}
						catch ( InterruptedException ignore ) {
						}
					});
				}
				catch ( SecsException nothing ) {
				}
			}
			catch ( InterruptedException ignore ) {
			}
		});
		
		this.hsmsSsComm.addSecsMessageReceiveListener(msg -> {
			
			try {
				
				try {
					
					this.secs1Comm.send(
							toSecs1MessageFromHsmsMessage(
									msg,
									this.secs1Comm.isEquip()
									)
							)
					.ifPresent(r -> {
						
						try {
							try {
								this.hsmsSsComm.send(toHsmsMessageFromSecs1Message(r));
							}
							catch ( SecsException nothing ) {
							}
						}
						catch (InterruptedException ignore ) {
						}
						
					});
				}
				catch ( Secs1TooBigSendMessageException e ) {
					
					try {
						this.hsmsSsComm.send(toHsmsRejectMessage(msg, REJECT_BY_OVERFLOW));
					}
					catch ( SecsException giveup ) {
					}
				}
				catch ( SecsException e ) {
					
					try {
						this.hsmsSsComm.send(toHsmsRejectMessage(msg, REJECT_BY_NOT_CONNECT));
					}
					catch ( SecsException giveup ) {
					}
				}
			}
			catch ( InterruptedException ignore ) {
			}
		});
	}
	
	public void open() throws IOException {
		
		synchronized ( this ) {
			if ( this.closed ) {
				throw new IOException("Already closed");
			}
			if ( this.opened ) {
				throw new IOException("Already opened");
			}
			this.opened = true;
		}
		
		this.secs1Comm.open();
		this.hsmsSsComm.open();
	}
	
	@Override
	public void close() throws IOException {
		
		synchronized ( this ) {
			if ( this.closed ) {
				return;
			}
			this.closed = true;
		}
		
		IOException ioExcept = null;
		
		try {
			this.secs1Comm.close();
		}
		catch ( IOException e ) {
			ioExcept = e;
		}
		
		try {
			this.hsmsSsComm.close();
		}
		catch ( IOException e ) {
			ioExcept = e;
		}
		
		if ( ioExcept != null ) {
			throw ioExcept;
		}
	}
	
	private static HsmsMessage toHsmsMessageFromSecs1Message(SecsMessage msg) {
		
		byte[] bs = msg.header10Bytes();
		
		byte[] header = new byte[] {
				(byte)((int)(bs[0]) & 0x7F),
				bs[1],
				bs[2],
				bs[3],
				(byte)0,
				(byte)0,
				bs[6],
				bs[7],
				bs[8],
				bs[9]
		};
		
		return HsmsSsMessageBuilder.build(header, msg.secs2());
	}
	
	private static Secs1Message toSecs1MessageFromHsmsMessage(SecsMessage msg, boolean fromEquip)
			throws Secs1TooBigSendMessageException {
		
		byte[] bs = msg.header10Bytes();
		
		byte[] header = new byte[] {
				(byte)((int)(bs[0]) & 0x7F),
				bs[1],
				bs[2],
				bs[3],
				(byte)0,
				(byte)0,
				bs[6],
				bs[7],
				bs[8],
				bs[9]
		};
		
		if ( fromEquip ) {
			header[0] |= (byte)0x80;
		}
		
		return Secs1MessageBuilder.build(header, msg.secs2());
	}
	
	private static HsmsMessage toHsmsRejectMessage(SecsMessage ref, byte reason) {
		
		byte[] bs = ref.header10Bytes();
		
		byte[] header = new byte[] {
				bs[0],
				bs[1],
				(byte)0x0,
				reason,
				HsmsMessageType.REJECT_REQ.pType(),
				HsmsMessageType.REJECT_REQ.sType(),
				bs[6],
				bs[7],
				bs[8],
				bs[9]
		};
		
		return HsmsSsMessageBuilder.build(header);
	}
	
	public boolean addSecsLogListener(SecsLogListener l) {
		boolean a = this.secs1Comm.addSecsLogListener(l);
		boolean b = this.hsmsSsComm.addSecsLogListener(l);
		return a && b;
	}
	
	public boolean removeSecsLogListener(SecsLogListener l) {
		boolean a = this.secs1Comm.removeSecsLogListener(l);
		boolean b = this.hsmsSsComm.removeSecsLogListener(l);
		return a || b;
	}
	
	public static Secs1OnTcpIpHsmsSsConverter newInstance(
			Secs1OnTcpIpCommunicatorConfig secs1Config,
			HsmsSsCommunicatorConfig hsmsSsConfig) {
		
		return new Secs1OnTcpIpHsmsSsConverter(secs1Config, hsmsSsConfig);
	}
	
	public static Secs1OnTcpIpHsmsSsConverter open(
			Secs1OnTcpIpCommunicatorConfig secs1Config,
			HsmsSsCommunicatorConfig hsmsSsConfig)
					throws IOException {
		
		final Secs1OnTcpIpHsmsSsConverter inst = newInstance(secs1Config, hsmsSsConfig);
		
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
