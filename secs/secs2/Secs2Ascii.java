package secs.secs2;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;

public class Secs2Ascii extends Secs2 {
	
	private static Secs2Item secs2Item = Secs2Item.ASCII;
	private static Charset charset = StandardCharsets.US_ASCII;

	private String ascii;
	private byte[] bytes;
	
	public Secs2Ascii(CharSequence cs) {
		super();
		
		Objects.requireNonNull(cs);
		
		this.ascii = cs.toString();
		this.bytes = null;
	}
	
	protected Secs2Ascii(byte[] bs) {
		super();
		
		Objects.requireNonNull(bs);
		
		this.ascii = null;
		this.bytes = Arrays.copyOf(bs, bs.length);
	}
	
	@Override
	public int size() {
		return ascii.length();
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
	
	private synchronized String ascii() {
		if ( ascii == null ) {
			ascii = new String(bytes, charset);
		}
		
		return ascii;
	}
	
	private byte[] bytes() {
		if ( bytes == null ) {
			bytes = ascii.getBytes(charset);
		}
		
		return bytes;
	}
	
	@Override
	public String getAscii() throws Secs2Exception {
		return ascii();
	}
	
	@Override
	public Secs2Item secs2Item() {
		return secs2Item;
	}
	
	@Override
	protected String toStringValue() {
		return "\"" + ascii() + "\"";
	}
}
