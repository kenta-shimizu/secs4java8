package com.shimizukenta.secs.gem;

import com.shimizukenta.secs.local.property.ObjectProperty;
import com.shimizukenta.secs.local.property.StringProperty;
import com.shimizukenta.secs.secs2.Secs2Item;

/**
 * This interface is GEM config.
 * 
 * <p>
 * To set Model-Number, {@link #mdln(CharSequence)}<br />
 * To set Software-Revision, {@link #softrev(CharSequence)}<br />
 * To set Clock-type, {@link #clockType(ClockType)}<br />
 * </p>
 * 
 * @author shimizukenta
 *
 */
public interface GemConfig {

	/**
	 * Model-Number setter.
	 * 
	 * <p>
	 * use S1F2, S1F13, S1F14
	 * </p>
	 * 
	 * @param cs MODEL-NUMBER
	 */
	public void mdln(CharSequence cs);

	/**
	 * Returns Model-Number Property.
	 * 
	 * @return Model-Number Property
	 */
	public StringProperty mdln();

	/**
	 * Software-Revision setter.
	 * 
	 * <p>
	 * use S1F2, S1F13, S1F14
	 * </p>
	 * 
	 * @param cs SOFTWARE-RESION
	 */
	public void softrev(CharSequence cs);

	/**
	 * Returns Software-Revision Property.
	 * 
	 * @return Software-Revision-property
	 */
	public StringProperty softrev();

	/**
	 * Clock-type setter.
	 * 
	 * <p>
	 * use S2F18, S2F31
	 * </p>
	 * 
	 * @param type A16 or A12
	 */
	public void clockType(ClockType type);

	/**
	 * Returns Clock-type property.
	 * 
	 * @return Clock-type-property
	 */
	public ObjectProperty<ClockType> clockType();

	/**
	 * DATA-ID Secs2Item type setter.
	 * 
	 * <p>
	 * type: INT1, INT2, INT4, INT8, UINT1, UINT2, UINT4, UINT8
	 * </p>
	 * 
	 * @param item item-type
	 */
	public void dataIdSecs2Item(Secs2Item item);

	/**
	 * Returns DATA-ID Secs2Item type property.
	 * 
	 * @return Secs2Item property
	 */
	public ObjectProperty<Secs2Item> dataIdSecs2Item();

	/**
	 * V-ID Secs2Item type setter.
	 * 
	 * <p>
	 * type: INT1, INT2, INT4, INT8, UINT1, UINT2, UINT4, UINT8
	 * </p>
	 * 
	 * @param item item-type
	 */
	public void vIdSecs2Item(Secs2Item item);

	/**
	 * Returns V-ID Secs2Item type property.
	 * 
	 * @return Secs2Item property
	 */
	public ObjectProperty<Secs2Item> vIdSecs2Item();

	/**
	 * REPORT-ID Secs2Item type setter.
	 * 
	 * <p>
	 * type: INT1, INT2, INT4, INT8, UINT1, UINT2, UINT4, UINT8
	 * </p>
	 * 
	 * @param item item-type
	 */
	public void reportIdSecs2Item(Secs2Item item);

	/**
	 * Returns REPORT-ID Secs2Item type property
	 * 
	 * @return Secs2Item property
	 */
	public ObjectProperty<Secs2Item> reportIdSecs2Item();

	/**
	 * COLLECTION-EVENT-ID Secs2Item type setter.
	 * 
	 * <p>
	 * type: INT1, INT2, INT4, INT8, UINT1, UINT2, UINT4, UINT8
	 * </p>
	 * 
	 * @param item item-type
	 */
	public void collectionEventIdSecs2Item(Secs2Item item);

	/**
	 * Returns COLLECTION-EVENT-ID Secs2Item type property.
	 * 
	 * @return Secs2Item property
	 */
	public ObjectProperty<Secs2Item> collectionEventIdSecs2Item();

}