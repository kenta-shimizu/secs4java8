package com.shimizukenta.secs.sml;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.secs2.Secs2Item;

public class SmlDataItemParser {

	protected SmlDataItemParser() {
		/* Nothing */
	}
	
	private static class SingletonHolder {
		private static final SmlDataItemParser inst = new SmlDataItemParser();
	}
	
	public static SmlDataItemParser getInstance() {
		return SingletonHolder.inst;
	}
	
	/**
	 * parse to AbstractSecs2
	 * 
	 * @param SML-Format-AbstractSecs2-part-Character (<A "ascii">)
	 * @return AbstractSecs2
	 * @throws SmlParseException
	 */
	public Secs2 parse(CharSequence cs) throws SmlParseException {
		
		String s = cs.toString().trim();
		
		if ( s.isEmpty() ) {
			return Secs2.empty();
		}
			
		SmlDataItemParseResult r = pickSmlDataItemParseResult(s);
		
		switch ( r.secs2Item ) {
		case LIST: {
			
			String v = r.value();
			List<Secs2> values = parseList(v);
			r.checkCount(values.size());
			return Secs2.list(values);
			/* break; */
		}
		case ASCII : {
			
			String v = r.value();
			String a = parseAscii(v);
			r.checkCount(a.length());
			return Secs2.ascii(a);
			/* break; */
		}
		case BOOLEAN: {
			
			List<Boolean> bools = new ArrayList<>();
			for ( String v : r.values() ) {
				
				if ( v.equals("T") || v.equalsIgnoreCase("true") ) {
					
					bools.add(Boolean.TRUE);
					
				} else if ( v.equals("F") || v.equalsIgnoreCase("false") ) {
					
					bools.add(Boolean.FALSE);
					
				} else {
					
					throw new SmlParseException("BOOLEAN parse failed");
				}
			}
			return Secs2.bool(bools);
			/* break; */
		}
		case BINARY : {
			
			List<Byte> nums = new ArrayList<>();
			for ( String v : r.values() ) {
				nums.add(parseBinary(v));
			}
			return Secs2.binary(nums);
			/* break; */
		}
		case INT1: {
			
			List<Number> nums = new ArrayList<>();
			for ( String v : r.values() ) {
				nums.add(new BigInteger(v));
			}
			return Secs2.int1(nums);
			/* break; */
		}
		case INT2: {
			
			List<Number> nums = new ArrayList<>();
			for ( String v : r.values() ) {
				nums.add(new BigInteger(v));
			}
			return Secs2.int2(nums);
			/* break; */
		}
		case INT4: {
			
			List<Number> nums = new ArrayList<>();
			for ( String v : r.values() ) {
				nums.add(new BigInteger(v));
			}
			return Secs2.int4(nums);
			/* break; */
		}
		case INT8: {
			
			List<Number> nums = new ArrayList<>();
			for ( String v : r.values() ) {
				nums.add(new BigInteger(v));
			}
			return Secs2.int8(nums);
			/* break; */
		}
		case UINT1: {
			
			List<Number> nums = new ArrayList<>();
			for ( String v : r.values() ) {
				nums.add(new BigInteger(v).abs());
			}
			return Secs2.uint1(nums);
			/* break; */
		}
		case UINT2: {
			
			List<Number> nums = new ArrayList<>();
			for ( String v : r.values() ) {
				nums.add(new BigInteger(v).abs());
			}
			return Secs2.uint2(nums);
			/* break; */
		}
		case UINT4: {
			
			List<Number> nums = new ArrayList<>();
			for ( String v : r.values() ) {
				nums.add(new BigInteger(v).abs());
			}
			return Secs2.uint4(nums);
			/* break; */
		}
		case UINT8: {
			
			List<Number> nums = new ArrayList<>();
			for ( String v : r.values() ) {
				nums.add(new BigInteger(v).abs());
			}
			return Secs2.uint8(nums);
			/* break; */
		}
		case FLOAT4: {
			
			List<Number> nums = new ArrayList<>();
			for ( String v : r.values() ) {
				nums.add(Float.valueOf(v));
			}
			return Secs2.float4(nums);
			/* break; */
		}
		case FLOAT8: {
			
			List<Number> nums = new ArrayList<>();
			for ( String v : r.values() ) {
				nums.add(Double.valueOf(v));
			}
			return Secs2.float8(nums);
			/* break; */
		}
		default :
			
			throw new SmlParseException(r.secs2Item.toString() + " not support");
		}
	}
	
	
	private static final String GROUP_ITEM   = "ITEM";
	private static final String GROUP_COUNT  = "COUNT";
	private static final String GROUP_VALUES = "VALUES";
	private static final String pregDataItem = "<\\s*(?<" + GROUP_ITEM + ">[0-9A-Za-z]+)\\s*(\\[\\s*(?<" + GROUP_COUNT + ">[0-9]+)\\s*\\])?\\s*(?<" + GROUP_VALUES + ">.*)\\s*>";
	protected static final Pattern ptnDataItem = Pattern.compile("^" + pregDataItem + "$");
	
	private SmlDataItemParseResult pickSmlDataItemParseResult(String value) throws SmlParseException {
		
		Matcher m = ptnDataItem.matcher(value);
		
		if ( ! m.matches() ) {
			
			String err = value;
			if ( err.length() > 50 ) {
				err = err.substring(0, 50);
			}
			
			throw new SmlParseException("\"<type [count] value>\" parse failed. \"" + err + "\"");
		}
		
		String item   = m.group(GROUP_ITEM);
		String countStr  = m.group(GROUP_COUNT);
		String values = m.group(GROUP_VALUES);
		
		Secs2Item s2i = Secs2Item.symbol(item);
		
		if ( s2i == Secs2Item.UNDEFINED ) {
			throw new SmlParseException("Secs2Item symbol \"" + item + "\" not undefined");
		}
		
		int count = -1;
		if ( countStr != null ) {
			try {
				count = Integer.parseInt(countStr);
			}
			catch ( NumberFormatException e ) {
				throw new SmlParseException("Item [count] parse failed", e);
			}
		}
		
		return new SmlDataItemParseResult(s2i, values, count);
	}
	
	
	protected static String GROUP_BYTE = "BYTE";
	protected static final String pregByte = "0[Xx](?<" + GROUP_BYTE + ">[0-9A-Fa-f]{1,2})";
	protected static final Pattern ptnByte = Pattern.compile("^" + pregByte + "$");

	private Byte parseBinary(String value) throws SmlParseException {
		
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
	
	private String parseAscii(String value) throws SmlParseException {
		
		final AsciiSpecialCharacterCounter counter = new AsciiSpecialCharacterCounter();
		
		final StringBuilder ascii = new StringBuilder();
		final StringBuilder sb = new StringBuilder();
		boolean inQuote = false;
		
		for ( int i = 0, m = value.length(); i < m ; ++i ) {
			
			char c = value.charAt(i);
			
			boolean f = counter.put(c);
			
			if ( f != inQuote ) {
				
				inQuote = f;
				
				if ( f /* inQUote */) {
					
					for ( String v : splitValue(sb) ) {
						byte b = parseBinary(v);
						String a = new String(new byte[] {b}, StandardCharsets.US_ASCII);
						ascii.append(a);
					}
					
				} else {
					
					ascii.append(sb.toString());
				}
				
				sb.setLength(0);	/* clear */
				
			} else {
				
				sb.append(c);
			}
		}
		
		if ( counter.inQuote() ) {
			throw new SmlParseException("<A> not closed");
		}
		
		/* Insert bytes */
		for ( String v : splitValue(sb) ) {
			byte b = parseBinary(v);
			String a = new String(new byte[] {b}, StandardCharsets.US_ASCII);
			ascii.append(a);
		}
		
		return ascii.toString();
	}
	
	
	private List<Secs2> parseList(String value) throws SmlParseException {
		
		final ListSpecialCharacterCounter counter = new ListSpecialCharacterCounter();
		
		final List<String> values = new ArrayList<>();
		final StringBuilder sb = new StringBuilder();
		boolean closed = true;
		
		for ( int i = 0, m = value.length(); i < m ; ++i ) {
			
			char c = value.charAt(i);
			
			boolean f = counter.put(c);
			
			if ( f != closed ) {
				
				closed = f;
				
				sb.append(c);
				
				if ( f /* closed */) {
					
					values.add(sb.toString());
					sb.setLength(0);	/* clear */
				}
				
			} else {
				
				if ( ! closed ) {
					
					sb.append(c);
				}
			}
		}
		
		if ( ! counter.closed() ) {
			throw new SmlParseException("");
		}
		
		List<Secs2> ss = new ArrayList<>();
		for ( String v : values ) {
			ss.add(parse(v));
		}
		
		return ss;
	}
	
	private static List<String> splitValue(CharSequence cs) {
		
		String v = cs.toString().trim();
		
		if ( v.isEmpty() ) {
			
			return Collections.emptyList();
			
		} else {
			
			return Stream.of(v.split("\\s+")).collect(Collectors.toList());
		}
	}
	
	private class SmlDataItemParseResult {
		
		private final Secs2Item secs2Item;
		private final String value;
		private final int count;
		
		private SmlDataItemParseResult(Secs2Item s2i, String values, int count) {
			this.secs2Item = s2i;
			this.value = values;
			this.count = count;
		}
		
		private String value() {
			return value;
		}
		
		private List<String> values() throws SmlParseException {
			List<String> vs = splitValue(value);
			checkCount(vs.size());
			return vs;
		}
		
		private void checkCount(int count) throws SmlParseException {
			
			if ( this.count >= 0 ) {
				
				if ( count != this.count ) {
					throw new SmlParseException("Item [count] unmatch. count=" + this.count + ", items=" + count);
				}
			}
		}
	}
	
	private class ListSpecialCharacterCounter {
		
		private static final char ABS = '<';
		private static final char ABE = '>';
		private static final char DQ  = '"';
		private static final char SQ  = '\'';
		
		/**
		 * Angle-Bracket count
		 */
		private int ab;
		
		/**
		 * in Double-Quote
		 */
		private boolean dq;
		
		/**
		 * in Single-Quote
		 */
		private boolean sq;
		
		private ListSpecialCharacterCounter() {
			ab = 0;
			dq = false;
			sq = false;
		}
		
		private boolean closed() {
			return ab == 0 && ! dq && ! sq;
		}
		
		private boolean inQuote() {
			return dq || sq;
		}
		
		private boolean put(char c) throws SmlParseException {
			
			if ( c == ABS ) {
				
				if ( ! inQuote() ) {
					ab += 1;
				}
				
			} else if ( c == ABE ) {
				
				if ( ! inQuote() ) {
					ab -= 1;
					
					if ( ab < 0 ) {
						throw new SmlParseException("Item not too closed.");
					}
				}
				
			} else if ( c == DQ ) {
				
				if ( ! sq ) {
					dq = ! dq;
				}
				
			} else if ( c == SQ ) {
				
				if ( ! dq ) {
					sq = ! sq;
				}
			}
			
			return closed();
		}
	}
	
	private class AsciiSpecialCharacterCounter {
		
		private static final char DQ  = '"';
		private static final char SQ  = '\'';
		
		/**
		 * in Double-Quote
		 */
		private boolean dq;
		
		/**
		 * in Single-Quote
		 */
		private boolean sq;
		
		private AsciiSpecialCharacterCounter() {
			dq = false;
			sq = false;
		}
		
		private boolean inQuote() {
			return dq || sq;
		}
		
		private boolean put(char c) {
			
			if ( c == DQ ) {
				
				if ( ! sq ) {
					dq = ! dq;
				}
				
			} else if ( c == SQ ) {
				
				if ( ! dq ) {
					sq = ! sq;
				}
			}
			
			return inQuote();
		}
	}
	
}
