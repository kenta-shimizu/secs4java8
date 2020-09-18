package com.shimizukenta.secs.gem;

import java.util.List;

import com.shimizukenta.secs.secs2.Secs2;

public class SimpleDynamicReport extends AbstractDynamicReport {

	private static final long serialVersionUID = -1155543523918249663L;

	public SimpleDynamicReport(Secs2 reportId, CharSequence alias, List<? extends Secs2> vids) {
		super(reportId, alias, vids);
	}

}
