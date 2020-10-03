package com.shimizukenta.secs.gem;

import java.io.Serializable;
import java.util.Objects;

import com.shimizukenta.secs.AbstractProperty;
import com.shimizukenta.secs.Property;
import com.shimizukenta.secs.ReadOnlyProperty;
import com.shimizukenta.secs.StringProperty;
import com.shimizukenta.secs.secs2.Secs2Item;

/**
 * This abstract class is GEM config.
 * 
 * <p>
 * To set Model-Number, {@link #mdln(CharSequence)}<br />
 * To set Software-Revision, {@link #softrev(CharSequence)}<br />
 * To set Clock-type, {@link #clockType(ClockType)}<br />
 * </p>
 * 
 * @author kenta-shimizu
 *
 */
public abstract class AbstractGemConfig implements Serializable {
	
	private static final long serialVersionUID = 1130854092113358850L;
	
	protected class Secs2NumberItemProperty extends AbstractProperty<Secs2Item> {
		
		private static final long serialVersionUID = 3803548762155640142L;
		
		public Secs2NumberItemProperty(Secs2Item initial) {
			super(Objects.requireNonNull(initial));
		}
		
		@Override
		public void set(Secs2Item v) {
			
			Objects.requireNonNull(v);
			
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
	
	
	private final StringProperty mdln = StringProperty.newInstance("      ");
	private final StringProperty softrev = StringProperty.newInstance("      ");
	private final Property<ClockType> clockType = Property.newInstance(ClockType.A16);
	private final Secs2NumberItemProperty dataIdSecs2Item = new Secs2NumberItemProperty(Secs2Item.UINT4);
	private final Secs2NumberItemProperty vIdSecs2Item = new Secs2NumberItemProperty(Secs2Item.UINT4);
	private final Secs2NumberItemProperty reportIdSecs2Item = new Secs2NumberItemProperty(Secs2Item.UINT4);
	private final Secs2NumberItemProperty collectionEventIdSecs2Item = new Secs2NumberItemProperty(Secs2Item.UINT4);
	
	public AbstractGemConfig() {
		/* Nothing */
	}
	
	/**
	 * Model-Number setter.
	 * 
	 * <p>
	 * use S1F2, S1F13, S1F14
	 * </p>
	 * 
	 * @param cs MODEL-NUMBER
	 */
	public void mdln(CharSequence cs) {
		this.mdln.set(Objects.requireNonNull(cs));
	}
	
	/**
	 * Model-Number getter.
	 * 
	 * @return Model-Number
	 */
	public ReadOnlyProperty<String> mdln() {
		return mdln;
	}
	
	/**
	 * Software-Revision setter.
	 * 
	 * <p>
	 * use S1F2, S1F13, S1F14
	 * </p>
	 * 
	 * @param cs SOFTWARE-RESION
	 */
	public void softrev(CharSequence cs) {
		this.softrev.set(Objects.requireNonNull(cs));
	}
	
	/**
	 * Software-Revision getter.
	 * 
	 * @return Software-Revision
	 */
	public ReadOnlyProperty<String> softrev() {
		return softrev;
	}
	
	/**
	 * Clock-type setter.
	 * 
	 * <p>
	 * use S2F18, S2F31
	 * </p>
	 * 
	 * @param type A16 or A12
	 */
	public void clockType(ClockType type) {
		this.clockType.set(Objects.requireNonNull(type));
	}
	
	/**
	 * Clock-type getter.
	 * 
	 * @return Clock-type
	 */
	public ReadOnlyProperty<ClockType> clockType() {
		return this.clockType;
	}
	
	/**
	 * DATA-ID Secs2Item type setter.
	 * 
	 * <p>
	 * type: INT1, INT2, INT4, INT8, UINT1, UINT2, UINT4, UINT8
	 * </p>
	 * 
	 * @param item item-type
	 */
	public void dataIdSecs2Item(Secs2Item item) {
		this.dataIdSecs2Item.set(item);
	}
	
	/**
	 * DATA-ID Secs2Item type getter.
	 * 
	 * @return Secs2Item
	 */
	public ReadOnlyProperty<Secs2Item> dataIdSecs2Item() {
		return this.dataIdSecs2Item;
	}
	
	/**
	 * V-ID Secs2Item type setter.
	 * 
	 * <p>
	 * type: INT1, INT2, INT4, INT8, UINT1, UINT2, UINT4, UINT8
	 * </p>
	 * 
	 * @param item item-type
	 */
	public void vIdSecs2Item(Secs2Item item) {
		this.vIdSecs2Item.set(item);
	}
	
	/**
	 * V-ID Secs2Item type getter.
	 * 
	 * @return Secs2Item
	 */
	public ReadOnlyProperty<Secs2Item> vIdSecs2Item() {
		return this.vIdSecs2Item;
	}
	
	/**
	 * REPORT-ID Secs2Item type setter.
	 * 
	 * <p>
	 * type: INT1, INT2, INT4, INT8, UINT1, UINT2, UINT4, UINT8
	 * </p>
	 * 
	 * @param item item-type
	 */
	public void reportIdSecs2Item(Secs2Item item) {
		this.reportIdSecs2Item.set(item);
	}
	
	/**
	 * REPORT-ID Secs2Item type getter.
	 * 
	 * @return Secs2Item
	 */
	public ReadOnlyProperty<Secs2Item> reportIdSecs2Item() {
		return this.reportIdSecs2Item;
	}

	/**
	 * COLLECTION-EVENT-ID Secs2Item type setter.
	 * 
	 * <p>
	 * type: INT1, INT2, INT4, INT8, UINT1, UINT2, UINT4, UINT8
	 * </p>
	 * 
	 * @param item item-type
	 */
	public void collectionEventIdSecs2Item(Secs2Item item) {
		this.collectionEventIdSecs2Item.set(item);
	}
	
	/**
	 * COLLECTION-EVENT-ID Secs2Item type getter.
	 * 
	 * @return Secs2Item
	 */
	public ReadOnlyProperty<Secs2Item> collectionEventIdSecs2Item() {
		return this.collectionEventIdSecs2Item;
	}
	
}
