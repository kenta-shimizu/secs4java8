package secs.gem;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import secs.secs2.Secs2;
import secs.secs2.Secs2Exception;

public class GemReport {
	
	private final String alias;
	private final Secs2 rptId;
	private final List<Secs2> vids;
	
	public GemReport(CharSequence alias, Secs2 rptId, List<Secs2> vids) {
		this.alias = alias.toString();
		this.rptId = rptId;
		this.vids = Collections.unmodifiableList(vids);
	}
	
	public GemReport(CharSequence alias, int rptId, List<Integer> vids) {
		this(alias
				, Secs2.uint4(rptId)
				, vids.stream().map(vid -> Secs2.uint4(vid)).collect(Collectors.toList()));
	}
	
	public String alias() {
		return alias;
	}
	
	public BigInteger reportId() throws Secs2Exception {
		return rptId.getBigInteger(0);
	}
	
	public Secs2 reportIdSecs2() {
		return rptId;
	}
	
	public Secs2 secs2() {
		return Secs2.list(
				rptId
				, Secs2.list(vids));
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
