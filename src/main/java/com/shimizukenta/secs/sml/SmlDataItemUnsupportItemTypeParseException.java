package com.shimizukenta.secs.sml;

import com.shimizukenta.secs.secs2.Secs2Item;

/**
 * SML Data-Item Unsupprt Item type parse Exception.
 * 
 * @author kenta-shimizu
 *
 */
public class SmlDataItemUnsupportItemTypeParseException extends SmlDataItemParseException {

	private static final long serialVersionUID = -872470951593682050L;
	
	/**
	 * Constructor.
	 * 
	 * @param secs2ItemString the SECS-II-Item String
	 */
	public SmlDataItemUnsupportItemTypeParseException(String secs2ItemString) {
		super(secs2ItemString);
	}
	
	/**
	 * Construnctor.
	 * 
	 * @param secs2item the SECS-II-Item
	 */
	public SmlDataItemUnsupportItemTypeParseException(Secs2Item secs2item) {
		super(secs2item.toString());
	}
	
}
