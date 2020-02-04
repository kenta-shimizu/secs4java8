package com.shimizukenta.secs.secs2;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class Secs2Unicode extends AbstractSecs2 {

	private static final Secs2Item secs2Item = Secs2Item.UNICODE;
	
	private byte[] bytes;
	
	protected Secs2Unicode(byte[] bs) {
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
	public byte[] secs2Bytes() throws Secs2Exception {
		
		try (
				ByteArrayOutputStream st = new ByteArrayOutputStream();
				) {
			
			byte[] bs = bytes();
			
			st.write(createHeadBytes(secs2Item, bs.length));
			st.write(bs);
			
			return st.toByteArray();
		}
		catch ( IOException e ) {
			throw new Secs2Exception(e);
		}
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
