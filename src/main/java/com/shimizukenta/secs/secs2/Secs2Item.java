package com.shimizukenta.secs.secs2;

import java.util.Objects;

/**
 * SECS-II Data Item Type.
 * 
 * @author kenta-shimizu
 *
 */
public enum Secs2Item {
	
	UNDEFINED( (byte)0xFF, -1, "UNDEFINED"),
	
	LIST( (byte)0x00, -1, "L" ),
	BINARY( (byte)0x20, 1, "B" ),
	BOOLEAN ( (byte)0x24 , 1 , "BOOLEAN" ),
	ASCII ( (byte)0x40 , 1 , "A" ),
	JIS8 ( (byte)0x44 , 1 , "J" ),	/* Not support */
	UNICODE ( (byte)0x48 , 2 , "UNICODE" ),	/* Not support */
	INT8 ( (byte)0x60 , 8 , "I8" ),
	INT1 ( (byte)0x64 , 1 , "I1" ),
	INT2 ( (byte)0x68 , 2 , "I2" ),
	INT4 ( (byte)0x70 , 4 , "I4" ),
	FLOAT8 ( (byte)0x80 , 8 , "F8" ),
	FLOAT4 ( (byte)0x90 , 4 , "F4" ),
	UINT8 ( (byte)0xA0 , 8 , "U8" ),
	UINT1 ( (byte)0xA4 , 1 , "U1" ),
	UINT2 ( (byte)0xA8 , 2 , "U2" ),
	UINT4 ( (byte)0xB0 , 4 , "U4" ),
	;
	
	private final byte code;
	private final int size;
	private final String symbol;
	
	private Secs2Item(byte itemCode, int itemSize, String itemSymbol) {
		this.code = itemCode;
		this.size = itemSize;
		this.symbol = itemSymbol;
	}
	
	protected byte code() {
		return code;
	}
	
	protected int size() {
		return size;
	}
	
	/**
	 * 
	 * @return SML-Item-type-String
	 */
	protected String symbol() {
		return symbol;
	}
	
	/**
	 * Secs2Item getter by byte-code
	 * 
	 * @param itemCode byte
	 * @return Secs2Item
	 */
	public static Secs2Item get(byte itemCode) {
		
		byte b = (byte)( itemCode & 0xFC );
		
		for ( Secs2Item item : values() ) {
			if ( b == item.code ) {
				return item;
			}
		}
		
		return UNDEFINED;
	}
	
	/**
	 * Secs2Item getter by SML-Item-type-String
	 * 
	 * @param symbol
	 * @return Secs2Item
	 */
	public static Secs2Item symbol(CharSequence symbol) {
		
		Objects.requireNonNull(symbol);
		
		String s = symbol.toString();
		
		for ( Secs2Item item : values() ) {
			if ( s.equalsIgnoreCase(item.symbol) ) {
				return item;
			}
		}
		
		return UNDEFINED;
	}
	
}
