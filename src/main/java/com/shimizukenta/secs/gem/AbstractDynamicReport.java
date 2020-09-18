package com.shimizukenta.secs.gem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.shimizukenta.secs.secs2.Secs2;

public abstract class AbstractDynamicReport implements DynamicReport, Serializable {
	
	private static final long serialVersionUID = -1459017309329426409L;
	
	private final Secs2 reportId;
	private final String alias;
	private final List<Secs2> vids;
	
	public AbstractDynamicReport(Secs2 reportId, CharSequence alias, List<? extends Secs2> vids) {
		this.reportId = reportId;
		this.alias = (alias == null ? null : alias.toString());
		this.vids = new ArrayList<>(vids);
	}
	
	@Override
	public Secs2 toS2F33Report() {
		return Secs2.list(
				reportId,
				Secs2.list(vids));
	}
	
	@Override
	public Secs2 reportId() {
		return reportId;
	}
	
	@Override
	public Optional<String> alias() {
		return alias == null ? Optional.empty() : Optional.of(alias);
	}
	
	@Override
	public List<Secs2> vids() {
		return Collections.unmodifiableList(vids);
	}
	
	@Override
	public int hashCode() {
		return reportId.hashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		if ( o != null && (o instanceof AbstractDynamicReport) ) {
			return ((AbstractDynamicReport)o).reportId.equals(reportId);
		}
		return false;
	}
}
