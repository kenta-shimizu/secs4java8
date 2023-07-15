package com.shimizukenta.secs.gem.impl;

import java.io.Serializable;
import java.util.Objects;

import com.shimizukenta.secs.gem.ClockType;
import com.shimizukenta.secs.gem.GemConfig;
import com.shimizukenta.secs.local.property.ObjectProperty;
import com.shimizukenta.secs.local.property.StringProperty;
import com.shimizukenta.secs.secs2.Secs2Item;

/**
 * This abstract class is GEM config implementation.
 * 
 * @author kenta-shimizu
 *
 */
public abstract class AbstractGemConfig implements Serializable, GemConfig {
	
	private static final long serialVersionUID = -4167459257695681133L;
	
	private final StringProperty mdln = StringProperty.newInstance("      ");
	private final StringProperty softrev = StringProperty.newInstance("      ");
	
	private final ObjectProperty<ClockType> clockType = ObjectProperty.newInstance(ClockType.A16);
	
	private final ObjectProperty<Secs2Item> dataIdSecs2Item = ObjectProperty.newInstance(Secs2Item.UINT4);
	private final ObjectProperty<Secs2Item> vIdSecs2Item = ObjectProperty.newInstance(Secs2Item.UINT4);
	private final ObjectProperty<Secs2Item> reportIdSecs2Item = ObjectProperty.newInstance(Secs2Item.UINT4);
	private final ObjectProperty<Secs2Item> collectionEventIdSecs2Item = ObjectProperty.newInstance(Secs2Item.UINT4);
	
	private static Secs2Item requireNumberSecs2Item(Secs2Item i) {
		Objects.requireNonNull(i);
		
		switch (i) {
		case INT1:
		case INT2:
		case INT4:
		case INT8:
		case UINT1:
		case UINT2:
		case UINT4:
		case UINT8: {
			return i;
			/* break; */
		}
		default:{
			throw new IllegalArgumentException("Not support " + i.toString());
		}
		}
	}
	
	/**
	 * Constructor.
	 * 
	 */
	public AbstractGemConfig() {
		/* Nothing */
	}
	
	@Override
	public void mdln(CharSequence cs) {
		this.mdln.set(cs);
	}
	
	@Override
	public StringProperty mdln() {
		return mdln;
	}
	
	@Override
	public void softrev(CharSequence cs) {
		this.softrev.set(cs);
	}
	
	@Override
	public StringProperty softrev() {
		return softrev;
	}
	
	@Override
	public void clockType(ClockType type) {
		this.clockType.set(Objects.requireNonNull(type));
	}
	
	@Override
	public ObjectProperty<ClockType> clockType() {
		return this.clockType;
	}
	
	@Override
	public void dataIdSecs2Item(Secs2Item item) {
		this.dataIdSecs2Item.set(requireNumberSecs2Item(item));
	}
	
	@Override
	public ObjectProperty<Secs2Item> dataIdSecs2Item() {
		return this.dataIdSecs2Item;
	}
	
	@Override
	public void vIdSecs2Item(Secs2Item item) {
		this.vIdSecs2Item.set(requireNumberSecs2Item(item));
	}
	
	@Override
	public ObjectProperty<Secs2Item> vIdSecs2Item() {
		return this.vIdSecs2Item;
	}
	
	@Override
	public void reportIdSecs2Item(Secs2Item item) {
		this.reportIdSecs2Item.set(requireNumberSecs2Item(item));
	}
	
	@Override
	public ObjectProperty<Secs2Item> reportIdSecs2Item() {
		return this.reportIdSecs2Item;
	}

	@Override
	public void collectionEventIdSecs2Item(Secs2Item item) {
		this.collectionEventIdSecs2Item.set(requireNumberSecs2Item(item));
	}
	
	@Override
	public ObjectProperty<Secs2Item> collectionEventIdSecs2Item() {
		return this.collectionEventIdSecs2Item;
	}
	
}
