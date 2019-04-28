package secs.gem;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import secs.secs2.Secs2;
import secs.secs2.Secs2Exception;

public class GemReport {
	
	private final Secs2 rptId;
	private final List<Secs2> vids;
	private final String alias;
	
	public GemReport(Secs2 rptId, List<Secs2> vids, CharSequence alias) {
		this.rptId = Objects.requireNonNull(rptId);
		this.vids = Collections.unmodifiableList(Objects.requireNonNull(vids));
		this.alias = Objects.requireNonNull(alias).toString();
	}
	
	public GemReport(Secs2 rptId, List<Secs2> vids) {
		this(rptId, vids, "");
	}
	
	public GemReport(int rptId, List<Integer> vids, CharSequence alias) {
		this(Secs2.uint4(rptId)
				, vids.stream().map(vid -> Secs2.uint4(vid)).collect(Collectors.toList())
				, alias);
	}
	
	public GemReport(int rptId, List<Integer> vids) {
		this(rptId, vids, "");
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
