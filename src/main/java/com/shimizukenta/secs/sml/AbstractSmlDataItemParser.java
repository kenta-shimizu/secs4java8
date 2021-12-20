package com.shimizukenta.secs.sml;

import java.math.BigInteger;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.secs2.Secs2Item;

abstract public class AbstractSmlDataItemParser implements SmlDataItemParser {

	public AbstractSmlDataItemParser() {
		/* Nothing */
	}
	
	@Override
	public Secs2 parse(CharSequence cs) throws SmlParseException {
		
		String s = Objects.requireNonNull(cs).toString().trim();
		
		if ( s.isEmpty() ) {
			return Secs2.empty();
		}
		
		SeekValueResult r = parsing(s, 0);
		
		if ( r.endIndex < s.length() ) {
			throw new SmlParseException("SML not end. index: " + r.endIndex);
		}
		
		return r.value;
	}
	
	protected SeekValueResult parsing(String s, int fromIndex) throws SmlParseException {
		
		try {
			SeekCharResult r = seekAngleBranketBegin(s, fromIndex);
			SeekStringResult s2ir = this.seekSecs2ItemString(s, r.index + 1);
			SeekStringResult s2sr = this.seekSizeString(s, s2ir.endIndex);
			
			int nextIndex = s2sr.endIndex;
			String secs2ItemStr = s2ir.str;
			Secs2Item secs2Item = Secs2Item.symbol(secs2ItemStr);
			
			switch ( secs2Item ) {
			case LIST: {
				
				return parseList(s, nextIndex);
				/* break; */
			}
			case ASCII: {
				
				return parseAscii(s, nextIndex);
				/* break; */
			}
			case UNDEFINED: {
				
				int size = -1;
				if ( ! s2sr.str.isEmpty() ) {
					
					String ss = s2sr.str;
					ss = ss.substring(1, (ss.length() - 1));
					
					try {
						size = Integer.valueOf(ss);
					}
					catch ( NumberFormatException e ) {
						throw new SmlParseException("size parse failed. index: " + s2ir.endIndex, e);
					}
				}
				
				return parseExtend(s, nextIndex, secs2ItemStr, size);
				/* break; */
			}
			default: {
				
				return parseDefaults(s, nextIndex, secs2Item);
			}
			}
		}
		catch ( IndexOutOfBoundsException e ) {
			throw new SmlParseException(("parse failed. index: " + fromIndex), e);
		}
	}
	
	private SeekValueResult parseList(String str, int fromIndex) throws SmlParseException {
		
		List<Secs2> ll = new ArrayList<>();
		
		for ( int i = fromIndex ;; ) {
			
			SeekCharResult r = seekNextChar(str, i);
			
			if ( r.c == ABE ) {
				
				return new SeekValueResult(Secs2.list(ll), (r.index + 1));
				
			} else if ( r.c == ABB ) {
				
				SeekValueResult v = parsing(str, r.index);
				ll.add(v.value);
				i = v.endIndex;
				
			} else {
				
				throw new SmlParseException("List not found '<' or '>'. index: " + fromIndex);
			}
		}
	}
	
	private SeekValueResult parseAscii(String str, int fromIndex) throws SmlParseException {
		
		StringBuilder sb = new StringBuilder();
		
		for ( int i = fromIndex;; ) {
			
			SeekCharResult r = seekNextChar(str, i);
			
			if ( r.c == ABE ) {
				
				return seekValueResult(Secs2.ascii(sb), (r.index + 1));
				
			} else if ( r.c == DQUOT )  {
				
				SeekCharResult x = seekNextChar(str, (r.index + 1), DQUOT);
				
				String s = str.substring((r.index + 1), x.index);
				
				sb.append(s);
				i = x.index + 1;
				
			} else if ( r.c == ZERO ) {
				
				SeekCharResult x = seekNextChar(str, (r.index + 1), ABE, SPACE, DQUOT, TAB, CR, LF);
				
				String s = str.substring(r.index, x.index);
				byte[] bs = new byte[] {toByte(s).byteValue()};
				
				sb.append(new String(bs, StandardCharsets.US_ASCII));
				i = x.index;
				
			} else {
				
				throw new SmlParseException("Ascii not found '\"' or '0' or '>'. index: " + fromIndex);
			}
		}
	}
	
	private SeekValueResult parseDefaults(String str, int fromIndex, Secs2Item secs2Item)
			throws SmlParseException {
		
		SeekCharResult r = this.seekAngleBranketEnd(str, fromIndex);
		
		String ss = str.substring(fromIndex, r.index).trim();
		
		String[] values = ss.isEmpty() ? new String[0] : ss.split("\\s+");
		
		int endIndex = r.index + 1;
		
		switch ( secs2Item ) {
		case BOOLEAN :{
			List<Boolean> ll = new ArrayList<>();
			for ( String v : values ) {
				
				if ( v.equals("T") || v.equalsIgnoreCase("true") ) {
					
					ll.add(Boolean.TRUE);
					
				} else if ( v.equals("F") || v.equalsIgnoreCase("false") ) {
					
					ll.add(Boolean.FALSE);
					
				} else {
					
					try {
						if ( toByte(v).byteValue() == (byte)0x00 ) {
							
							ll.add(Boolean.FALSE);
							
						} else {
							
							ll.add(Boolean.TRUE);
						}
					}
					catch ( SmlParseException e ) {
						
						throw new SmlParseException("BOOLEAN parse failed", e);
					}
				}
			}
			return seekValueResult(Secs2.bool(ll), endIndex);
			/* break; */
		}
		case BINARY:{
			List<Byte> ll = new ArrayList<>();
			for ( String v : values ) {
				ll.add(toByte(v));
			}
			return seekValueResult(Secs2.binary(ll), endIndex);
			/* break */
		}
		case INT1: {
			List<Number> ll = new ArrayList<>();
			for ( String v : values ) {
				ll.add(new BigInteger(v));
			}
			return seekValueResult(Secs2.int1(ll), endIndex);
			/* break; */
		}
		case INT2: {
			List<Number> ll = new ArrayList<>();
			for ( String v : values ) {
				ll.add(new BigInteger(v));
			}
			return seekValueResult(Secs2.int2(ll), endIndex);
			/* break; */
		}
		case INT4: {
			List<Number> ll = new ArrayList<>();
			for ( String v : values ) {
				ll.add(new BigInteger(v));
			}
			return seekValueResult(Secs2.int4(ll), endIndex);
			/* break; */
		}
		case INT8: {
			List<Number> ll = new ArrayList<>();
			for ( String v : values ) {
				ll.add(new BigInteger(v));
			}
			return seekValueResult(Secs2.int8(ll), endIndex);
			/* break; */
		}
		case UINT1: {
			List<Number> ll = new ArrayList<>();
			for ( String v : values ) {
				ll.add(new BigInteger(v).abs());
			}
			return seekValueResult(Secs2.uint1(ll), endIndex);
			/* break; */
		}
		case UINT2: {
			List<Number> ll = new ArrayList<>();
			for ( String v : values ) {
				ll.add(new BigInteger(v).abs());
			}
			return seekValueResult(Secs2.uint2(ll), endIndex);
			/* break; */
		}
		case UINT4: {
			List<Number> ll = new ArrayList<>();
			for ( String v : values ) {
				ll.add(new BigInteger(v).abs());
			}
			return seekValueResult(Secs2.uint4(ll), endIndex);
			/* break; */
		}
		case UINT8: {
			List<Number> ll = new ArrayList<>();
			for ( String v : values ) {
				ll.add(new BigInteger(v).abs());
			}
			return seekValueResult(Secs2.uint8(ll), endIndex);
			/* break; */
		}
		case FLOAT4: {
			List<Number> ll = new ArrayList<>();
			for ( String v : values ) {
				ll.add(Float.valueOf(v));
			}
			return seekValueResult(Secs2.float4(ll), endIndex);
			/* break; */
		}
		case FLOAT8: {
			List<Number> ll = new ArrayList<>();
			for ( String v : values ) {
				ll.add(Double.valueOf(v));
			}
			return seekValueResult(Secs2.float8(ll), endIndex);
			/* break; */
		}
		default: {
			throw new SmlParseException("Unsupport Format " + secs2Item);
		}
		}
	}
	
	
	private static String GROUP_BYTE = "BYTE";
	private static final String pregByte = "0[Xx](?<" + GROUP_BYTE + ">[0-9A-Fa-f]{1,2})";
	private static final Pattern ptnByte = Pattern.compile("^" + pregByte + "$");
	
	private Byte toByte(String value) throws SmlParseException {
		
		Matcher m = ptnByte.matcher(value);
		
		try {
			if ( m.matches() ) {
				
				int i = Integer.parseInt(m.group(GROUP_BYTE), 16);
				return Byte.valueOf((byte)i);
						
			} else {
				
				int i = Integer.parseInt(value, 10);
				return Byte.valueOf((byte)i);
			}
		}
		catch ( NumberFormatException e ) {
			throw new SmlParseException("Binary parse failed \"" + value + "\"", e);
		}
	}

	
	/**
	 * 
	 * prototype-pattern
	 * 
	 * @param str
	 * @param fromIndex
	 * @param secs2ItemString
	 * @param size
	 * @return
	 * @throws SmlParseException
	 */
	protected SeekValueResult parseExtend(String str, int fromIndex, String secs2ItemString, int size)
			throws SmlParseException {
		
		throw new SmlParseException("UNKNOWN SECS2ITEM type: " + secs2ItemString);
	}
	
	
	protected static final char SPACE = (char)0x20;
	protected static final char ABB = '<';
	protected static final char ABE = '>';
	protected static final char SBB = '[';
	protected static final char SBE = ']';
	protected static final char DQUOT = '"';
	protected static final char ZERO = '0';
	protected static final char TAB = '\t';
	protected static final char CR = '\r';
	protected static final char LF = '\n';
	
	protected final class SeekCharResult {
		
		public final char c;
		public final int index;
		
		private SeekCharResult(char c, int index) {
			this.c = c;
			this.index = index;
		}
	}
	
	protected final class SeekStringResult {
		
		public final String str;
		public final int endIndex;
		
		private SeekStringResult(String str, int endIndex) {
			this.str = str;
			this.endIndex = endIndex;
		}
	}
	
	protected final class SeekValueResult {
		
		public final Secs2 value;
		public final int endIndex;
		
		private SeekValueResult(Secs2 value, int endIndex) {
			this.value = value;
			this.endIndex = endIndex;
		}
	}
	
	
	protected SeekCharResult seekNextChar(String s, int fromIndex) {
		for (int i = fromIndex; ; ++i) {
			char c = s.charAt(i);
			if ( c > SPACE ) {
				return seekCharResult(c, i);
			}
		}
	}
	
	protected SeekCharResult seekNextChar(String s, int fromIndex, char... seekChar) {
		for (int i = fromIndex; ; ++i) {
			char c = s.charAt(i);
			for ( char sc : seekChar ) {
				if ( c == sc ) {
					return seekCharResult(c, i);
				}
			}
		}
	}
	
	protected SeekCharResult seekAngleBranketBegin(String s, int fromIndex) {
		return seekNextChar(s, fromIndex, ABB);
	}
	
	protected SeekCharResult seekAngleBranketEnd(String s, int fromIndex) {
		return seekNextChar(s, fromIndex, ABE);
	}
	
	protected SeekStringResult seekSecs2ItemString(String s, int fromIndex) {
		
		int beginIndex;
		int endIndex;
		
		{
			SeekCharResult r = seekNextChar(s, fromIndex);
			beginIndex = r.index;
		}
		{
			SeekCharResult r = seekNextChar(s, (beginIndex + 1), SPACE, ABB, ABE, SBB, DQUOT);
			endIndex = r.index;
		}
		
		return seekStringResult(s.substring(beginIndex, endIndex), endIndex);
	}
	
	protected SeekStringResult seekSizeString(String s, int fromIndex) {
		
		int beginIndex;
		int endIndex;
		
		{
			SeekCharResult r = seekNextChar(s, fromIndex);
			if ( r.c != SBB ) {
				return seekStringResult("", fromIndex);
			}
			beginIndex = r.index;
		}
		{
			SeekCharResult r = seekNextChar(s, (beginIndex + 1), SBE);
			endIndex = r.index + 1;
		}
		
		return seekStringResult(s.substring(beginIndex, endIndex), endIndex);
	}
	
	protected final SeekCharResult seekCharResult(char c, int index) {
		return new SeekCharResult(c, index);
	}
	
	protected final SeekStringResult seekStringResult(String str, int endIndex) {
		return new SeekStringResult(str, endIndex);
	}
	
	protected final SeekValueResult seekValueResult(Secs2 value, int endIndex) {
		return new SeekValueResult(value, endIndex);
	}
	
}
