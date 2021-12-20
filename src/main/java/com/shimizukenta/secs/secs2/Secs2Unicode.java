package com.shimizukenta.secs.secs2;

import java.util.Arrays;
import java.util.Objects;

public class Secs2Unicode extends AbstractSecs2 {
	
	private static final long serialVersionUID = -6855718016536094781L;
	
	private static final Secs2Item secs2Item = Secs2Item.UNICODE;
	
	private byte[] bytes;
	
	public Secs2Unicode(byte[] bs) {
		super();
		
		Objects.requireNonNull(bs);
		
		this.bytes = Arrays.copyOf(bs, bs.length);
	}
	
	private synchronized byte[] bytes() {
		
		if ( bytes == null ) {
			
			bytes = new byte[0];
		}
		
		return bytes;
	}

	@Override
	public int size() {
		return bytes().length;
	}
	
	@Override
	protected void putBytesPack(Secs2BytesPackBuilder builder) throws Secs2BuildException {
		this.putHeadAndBodyBytesToBytesPack(builder, bytes());
	}
	
	@Override
	public Secs2Item secs2Item() {
		return secs2Item;
	}
	
	@Override
	protected String toJsonValue() {
		return "false";
	}
	
	@Override
	protected String toStringValue() {
		return "NOT_SUPPORT";
	}

}
