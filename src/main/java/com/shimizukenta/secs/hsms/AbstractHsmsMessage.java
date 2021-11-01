package com.shimizukenta.secs.hsms;

import com.shimizukenta.secs.AbstractSecsMessage;
import com.shimizukenta.secs.secs2.Secs2;

public abstract class AbstractHsmsMessage extends AbstractSecsMessage implements HsmsMessage {
	
	private static final long serialVersionUID = 7234808180120409439L;
	
	public AbstractHsmsMessage() {
		super();
		
		//TODO
	}
	
	@Override
	public int getStream() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getFunction() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean wbit() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Secs2 secs2() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deviceId() {
		
		// TODO Auto-generated method stub
		return -1;
	}
	
	@Override
	public int sessionId() {
		
		//TODO
		return -1;
	}
	
	@Override
	public byte[] header10Bytes() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	protected String toJsonProxy() {
		//TODO
		return "{}";
	}
	
	@Override
	public HsmsMessageType messageType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isDataMessage() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public byte pType() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public byte sType() {
		// TODO Auto-generated method stub
		return 0;
	}

}
