package secs.sml;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import secs.secs2.Secs2;
import secs.secs2.Secs2Item;

public class SmlSecs2Parser {

	protected SmlSecs2Parser() {
		/* Nothing */
	}
	
	private static class SingletonHolder {
		private static final SmlSecs2Parser inst = new SmlSecs2Parser();
	}
	
	public static SmlSecs2Parser getInstance() {
		return SingletonHolder.inst;
	}
	
	/**
	 * parse to Secs2
	 * 
	 * @param SML-Format-Secs2-part-Character (<A "ascii">)
	 * @return Secs2
	 * @throws SmlParseException
	 */
	public Secs2 parse(CharSequence cs) throws SmlParseException {
		
		String s = cs.toString().trim();
		
		if ( s.isEmpty() ) {
			
			return Secs2.empty();
			
		} else {
			
			if ( ! s.startsWith("<") || ! s.endsWith(">") ) {
				throw new SmlParseException();
			}
			
			s = s.substring(1).trim();
			
			Secs2ItemResult s2ir= pickSecs2ItemResult(s);
			
			s = s.substring(0, s.length() - 1);
			
			switch ( s2ir.secs2Item ) {
			case LIST: {
				
				String v = pickValue(s, s2ir.endIndex);
				return Secs2.list(parseList(v));
				/* break; */
			}
			case ASCII : {
				
				String v = pickValue(s, s2ir.endIndex);
				return Secs2.ascii(parseAscii(v));
				/* break; */
			}
			case BOOLEAN: {
				
				List<Boolean> bools = new ArrayList<>();
				for ( String v : pickValues(s, s2ir.endIndex) ) {
					
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
				for ( String v : pickValues(s, s2ir.endIndex) ) {
					nums.add(parseByte(v));
				}
				return Secs2.binary(nums);
				/* break; */
			}
			case INT1: {
				
				List<Number> nums = new ArrayList<>();
				for ( String v : pickValues(s, s2ir.endIndex) ) {
					nums.add(new BigInteger(v));
				}
				return Secs2.int1(nums);
				/* break; */
			}
			case INT2: {
				
				List<Number> nums = new ArrayList<>();
				for ( String v : pickValues(s, s2ir.endIndex) ) {
					nums.add(new BigInteger(v));
				}
				return Secs2.int2(nums);
				/* break; */
			}
			case INT4: {
				
				List<Number> nums = new ArrayList<>();
				for ( String v : pickValues(s, s2ir.endIndex) ) {
					nums.add(new BigInteger(v));
				}
				return Secs2.int4(nums);
				/* break; */
			}
			case INT8: {
				
				List<Number> nums = new ArrayList<>();
				for ( String v : pickValues(s, s2ir.endIndex) ) {
					nums.add(new BigInteger(v));
				}
				return Secs2.int8(nums);
				/* break; */
			}
			case UINT1: {
				
				List<Number> nums = new ArrayList<>();
				for ( String v : pickValues(s, s2ir.endIndex) ) {
					nums.add(new BigInteger(v).abs());
				}
				return Secs2.uint1(nums);
				/* break; */
			}
			case UINT2: {
				
				List<Number> nums = new ArrayList<>();
				for ( String v : pickValues(s, s2ir.endIndex) ) {
					nums.add(new BigInteger(v).abs());
				}
				return Secs2.uint2(nums);
				/* break; */
			}
			case UINT4: {
				
				List<Number> nums = new ArrayList<>();
				for ( String v : pickValues(s, s2ir.endIndex) ) {
					nums.add(new BigInteger(v).abs());
				}
				return Secs2.uint4(nums);
				/* break; */
			}
			case UINT8: {
				
				List<Number> nums = new ArrayList<>();
				for ( String v : pickValues(s, s2ir.endIndex) ) {
					nums.add(new BigInteger(v).abs());
				}
				return Secs2.uint8(nums);
				/* break; */
			}
			case FLOAT4: {
				
				List<Number> nums = new ArrayList<>();
				for ( String v : pickValues(s, s2ir.endIndex) ) {
					nums.add(Float.valueOf(v));
				}
				return Secs2.float4(nums);
				/* break; */
			}
			case FLOAT8: {
				
				List<Number> nums = new ArrayList<>();
				for ( String v : pickValues(s, s2ir.endIndex) ) {
					nums.add(Double.valueOf(v));
				}
				return Secs2.float8(nums);
				/* break; */
			}
			default :
				
				throw new SmlParseException("");
			}
		}
	}
	
	
	private static final String sizeBlockStart = "[";
	private static final String sizeBlockEnd   = "]";
	
	private Secs2ItemResult pickSecs2ItemResult(String s) throws SmlParseException {
		
		int min = Stream.of("<", ">", " ", "\t", "\r", "\n", sizeBlockStart)
				.mapToInt(c -> s.indexOf(c))
				.filter(i -> i >= 0)
				.min().orElse(-1);
		
		if ( min < 0 ) {
			
			throw new SmlParseException("Secs2Item not found");
			
		} else {
			
			Secs2Item s2i = Secs2Item.symbol(s.substring(0, min));
			
			String ss = s.substring(min).trim();
			
			if ( ss.startsWith(sizeBlockStart) ) {
				
				int i = s.indexOf(sizeBlockEnd, min);
				
				if ( i < 0 ) {
					
					throw new SmlParseException("Item [size] not closed");
					
				} else {
					
					return new Secs2ItemResult(s2i, i + 1);
				}
				
			} else {
				
				return new Secs2ItemResult(s2i, min);
			}
		}
	}
	
	private String pickValue(String s, int startIndex) {
		return s.substring(startIndex).trim();
	}
	
	private List<String> pickValues(String s, int startIndex) {
		
		String ss = pickValue(s, startIndex);
		
		if ( ss.isEmpty() ) {
			
			return Collections.emptyList();
			
		} else {
			
			return Stream.of(ss.split("\\s+")).collect(Collectors.toList());
		}
	}
	
	protected static String GROUP_BYTE = "BYTE";
	protected static final String pregByte = "0[Xx](?<" + GROUP_BYTE + ">[0-9A-Fa-f]{1,2})";
	protected static final Pattern ptnByte = Pattern.compile("^" + pregByte + "$");

	private Byte parseByte(String s) throws SmlParseException {
		
		Matcher m = ptnByte.matcher(s);
		
		if ( m.matches() ) {
			
			int i = Integer.parseInt(m.group(GROUP_BYTE), 16);
			return Byte.valueOf((byte)i);
					
		} else {
			
			int i = Integer.parseInt(s, 10);
			return Byte.valueOf((byte)i);
		}
	}
	
	private String parseAscii(String s) throws SmlParseException {
		
		StringBuilder sb = new StringBuilder();
		
		
		//TODO
		
		
		return sb.toString();
	}
	
	private List<Secs2> parseList(String s) throws SmlParseException {
		
		List<Secs2> values = new ArrayList<>();
		
		for ( ;; ) {
			
			s = s.trim();
			
			if ( s.isEmpty() ) break;
			
			if ( s.startsWith("\"") ) {
				
				//TODO
				
			} else if ( s.startsWith("'") ) {
				
				//TODO
				
			} else if ( s.startsWith("0x") || s.startsWith("0X") ){
				
				//TODO
				
			} else {
				
				throw new SmlParseException("");
			}
			
		}
		
		return values;
	}
	
	private class Secs2ItemResult {
		
		private final Secs2Item secs2Item;
		private final int endIndex;
		
		private Secs2ItemResult(Secs2Item s2i, int endIndex) {
			this.secs2Item = s2i;
			this.endIndex = endIndex;
		}
	}
	
	private class AsciiResult {
		
		private final String ascii;
		private final int endIndex;
		
		private AsciiResult(String ascii, int endIndex) {
			this.ascii = ascii;
			this.endIndex = endIndex;
		}
	}
	
}
