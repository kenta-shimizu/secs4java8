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
		return ascii().length();
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
		if ( this.ascii == null ) {
			this.ascii = new String(bytes, charset);
		}
		
		return this.ascii;
	}
	
	private byte[] bytes() {
		if ( this.bytes == null ) {
			this.bytes = ascii.getBytes(charset);
		}
		
		return this.bytes;
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
	protected String parsedJsonValue() {
		return "\"" + escapeJsonString(ascii()) + "\"";
	}
	
	
	private static final byte BACKSLASH = 0x5C;
	private static final byte[] BS = new byte[]{BACKSLASH, 0x62};	/* \\b */
	private static final byte[] HT = new byte[]{BACKSLASH, 0x74};	/* \\t */
	private static final byte[] LF = new byte[]{BACKSLASH, 0x6E};	/* \\n */
	private static final byte[] FF = new byte[]{BACKSLASH, 0x66};	/* \\f */
	private static final byte[] CR = new byte[]{BACKSLASH, 0x72};	/* \\r */
	
	private static final String escapeJsonString(CharSequence cs) {
		
		try (
				ByteArrayOutputStream strm = new ByteArrayOutputStream();
				) {
			
			byte[] bb = cs.toString().getBytes(StandardCharsets.US_ASCII);
			
			for (byte b : bb) {
				
				if (b == 0x08 /* BS */) {
					
					strm.write(BS);
					
				} else if (b == 0x09 /* HT */) {
					
					strm.write(HT);
					
				} else if (b == 0x0A /* LF */) {
					
					strm.write(LF);
					
				} else if (b == 0x0C /* FF */) {
					
					strm.write(FF);
					
				} else if (b == 0x0D /* CR */) {
					
					strm.write(CR);
					
				} else if (
						b == 0x22 /* " */
						|| b == 0x2F /* / */
						|| b == BACKSLASH /* \\ */
						) {
					
					strm.write(BACKSLASH);
					strm.write(b);
					
				} else {
					
					strm.write(b);
				}
			}
			
			return new String(strm.toByteArray(), StandardCharsets.US_ASCII);
		}
		catch (IOException notHappen) {
			notHappen.printStackTrace();
		}
		
		return "";
	}
	
	@Override
	protected String toStringValue() {
		return "\"" + ascii() + "\"";
	}
}
