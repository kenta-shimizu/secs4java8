package com.shimizukenta.secs.gem;

import java.io.Serializable;
import java.util.Objects;

import com.shimizukenta.secs.AbstractProperty;
import com.shimizukenta.secs.IntegerProperty;
import com.shimizukenta.secs.Property;
import com.shimizukenta.secs.StringProperty;
import com.shimizukenta.secs.secs2.Secs2Item;

public class GemConfig implements Serializable {
	
	private static final long serialVersionUID = 1130854092113358850L;
	
	protected class Secs2NumberItemProperty extends AbstractProperty<Secs2Item> {
		
		private static final long serialVersionUID = 3803548762155640142L;
		
		public Secs2NumberItemProperty(Secs2Item initial) {
			super(initial);
		}
		
		@Override
		public void set(Secs2Item v) {
			switch ( v ) {
			case INT1:
			case INT2:
			case INT4:
			case INT8:
			case UINT1:
			case UINT2:
			case UINT4:
			case UINT8: {
				super.set(v);
				break;
			}
			default: {
				throw new IllegalArgumentException("Not support " + v.toString());
			}
			}
		}
	}
	
	private final StringProperty mdln = new StringProperty("      ");
	private final StringProperty softrev = new StringProperty("      ");
	private final IntegerProperty clockSize = new IntegerProperty(16);
	private final Secs2NumberItemProperty dataIdSecs2Item = new Secs2NumberItemProperty(Secs2Item.UINT4);
	private final Secs2NumberItemProperty vIdSecs2Item = new Secs2NumberItemProperty(Secs2Item.UINT4);
	private final Secs2NumberItemProperty reportIdSecs2Item = new Secs2NumberItemProperty(Secs2Item.UINT4);
	private final Secs2NumberItemProperty collectionEventIdSecs2Item = new Secs2NumberItemProperty(Secs2Item.UINT4);
	
	public GemConfig() {
		/* Nothing */
	}
	
	/**
	 * use S1F2, S1F13, S1F14
	 * 
	 * @param cs
	 */
	public void mdln(CharSequence cs) {
		this.mdln.set(Objects.requireNonNull(cs));
	}
	
	public StringProperty mdln() {
		return mdln;
	}
	
	/**
	 * use S1F2, S1F13, S1F14
	 * 
	 * @param cs
	 */
	public void softrev(CharSequence cs) {
		this.softrev.set(Objects.requireNonNull(cs));
	}
	
	public StringProperty softrev() {
		return softrev;
	}
	
	/**
	 * use S2F18, S2F31
	 * 
	 * @param size is 16 or 12.
	 */
	public void clockSize(int size) {
		this.clockSize.set(size);
	}
	
	public IntegerProperty clockSize() {
		return this.clockSize;
	}
	
	/**
	 * Data-ID Secs2Item type
	 * 
	 * @param INT1, INT2, INT4, INT8, UINT1, UINT2, UINT4, UINT8
	 */
	public void dataIdSecs2Item(Secs2Item item) {
		this.dataIdSecs2Item.set(item);
	}
	
	public Property<Secs2Item> dataIdSecs2Item() {
		return this.dataIdSecs2Item;
	}
	
	public void vIdSecs2Item(Secs2Item item) {
		this.vIdSecs2Item.set(item);
	}
	
	public Property<Secs2Item> vIdSecs2Item() {
		return this.vIdSecs2Item;
	}
	
	public void reportIdSecs2Item(Secs2Item item) {
		this.reportIdSecs2Item.set(item);
	}
	
	public Property<Secs2Item> reportIdSecs2Item() {
		return this.reportIdSecs2Item;
	}

	public void collectionEventIdSecs2Item(Secs2Item item) {
		this.collectionEventIdSecs2Item.set(item);
	}
	
	public Property<Secs2Item> collectionEventIdSecs2Item() {
		return this.collectionEventIdSecs2Item;
	}
	
}
