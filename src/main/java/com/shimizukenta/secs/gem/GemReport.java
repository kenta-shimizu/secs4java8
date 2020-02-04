package com.shimizukenta.secs.gem;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.shimizukenta.secs.secs2.AbstractSecs2;
import com.shimizukenta.secs.secs2.Secs2Exception;

public class GemReport {
	
	private final AbstractSecs2 rptId;
	private final List<AbstractSecs2> vids;
	private final String alias;
	
	public GemReport(AbstractSecs2 rptId, List<AbstractSecs2> vids, CharSequence alias) {
		this.rptId = Objects.requireNonNull(rptId);
		this.vids = Collections.unmodifiableList(Objects.requireNonNull(vids));
		this.alias = Objects.requireNonNull(alias).toString();
	}
	
	public GemReport(AbstractSecs2 rptId, List<AbstractSecs2> vids) {
		this(rptId, vids, "");
	}
	
	public GemReport(int rptId, List<? extends Number> vids, CharSequence alias) {
		this(AbstractSecs2.uint4(rptId)
				, vids.stream().map(vid -> AbstractSecs2.uint4(vid.intValue())).collect(Collectors.toList())
				, alias);
	}
	
	public GemReport(int rptId, List<? extends Number> vids) {
		this(rptId, vids, "");
	}
	
	public String alias() {
		return alias;
	}
	
	public BigInteger reportId() throws Secs2Exception {
		return rptId.getBigInteger(0);
	}
	
	public AbstractSecs2 reportIdSecs2() {
		return rptId;
	}
	
	public AbstractSecs2 secs2() {
		return AbstractSecs2.list(
				rptId
				, AbstractSecs2.list(vids));
	}
	
	@Override
	public int hashCode() {
		try {
			return reportId().hashCode();
		}
		catch ( Secs2Exception e ) {
			return e.hashCode();
		}
	}
	
	@Override
	public boolean equals(Object o) {
		
		if ( o instanceof GemReport ) {
			
			try {
				return ((GemReport) o).reportId().equals(reportId());
			}
			catch ( Secs2Exception e ) {
				return false;
			}
			
		} else {
			
			return false;
		}
	}

}
