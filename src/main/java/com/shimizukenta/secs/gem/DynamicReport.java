package com.shimizukenta.secs.gem;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.secs2.Secs2Exception;

public class DynamicReport implements Serializable {
	
	private static final long serialVersionUID = -1459017309329426409L;
	
	private final Secs2 reportId;
	private final String alias;
	private final List<Secs2> vids;
	
	protected DynamicReport(Secs2 reportId, CharSequence alias, List<Secs2> vids) {
		this.reportId = reportId;
		this.alias = (alias == null ? null : alias.toString());
		this.vids = new ArrayList<>(vids);
	}
	
	public Secs2 secs2() {
		return Secs2.list(
				reportId,
				Secs2.list(vids));
	}
	
	public Secs2 reportId() {
		return reportId;
	}
	
	public String alias() {
		return alias;
	}
	
	private BigInteger bigInteger() throws Secs2Exception {
		return reportId.getBigInteger(0);
	}
	
	@Override
	public int hashCode() {
		try {
			return bigInteger().hashCode();
		}
		catch ( Secs2Exception giveup ) {
			return reportId.hashCode();
		}
	}
	
	@Override
	public boolean equals(Object o) {
		if ( o != null && (o instanceof DynamicReport) ) {
			try {
				return ((DynamicReport)o).bigInteger().equals(bigInteger());
			}
			catch ( Secs2Exception giveup ) {
			}
		}
		return false;
	}
}
