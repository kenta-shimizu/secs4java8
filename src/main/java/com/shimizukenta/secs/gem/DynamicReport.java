package com.shimizukenta.secs.gem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.secs2.Secs2Exception;

public class DynamicReport implements Serializable {
	
	private static final long serialVersionUID = -1459017309329426409L;
	
	private final Secs2 reportId;
	private final String alias;
	private final List<Secs2> vids;
	
	protected DynamicReport(Secs2 reportId, CharSequence alias, List<? extends Secs2> vids) {
		this.reportId = reportId;
		this.alias = (alias == null ? null : alias.toString());
		this.vids = new ArrayList<>(vids);
	}
	
	/**
	 * newInstace from S2F33-Secs2-Single-Report.<br />
	 * Single-Report-Format:<br />
	 * &lt;L [2]<br />
	 * &nbsp;&nbsp;&lt;U4 report-id&gt;<br />
	 * &nbsp;&nbsp;&lt;L [n]<br />
	 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;U4 vid-1&gt;<br />
	 * &nbsp;&nbsp;&nbsp;&nbsp;...<br />
	 * &nbsp;&nbsp;&lt;<br />
	 * &gt;.
	 * 
	 * @param S2F33 Secs2 Single-Report
	 * @return DynamicReport
	 * @throws Secs2Exception
	 */
	public static DynamicReport fromS2F33Report(Secs2 secs2) throws Secs2Exception {
		Secs2 rptid = secs2.get(0);
		List<Secs2> vids = secs2.get(1).stream().collect(Collectors.toList());
		return new DynamicReport(rptid, null, vids);
	}
	
	/**
	 * to S2F33-Secs2-Single-Report.<br />
	 * Single-Report-Format:<br />
	 * &lt;L [2]<br />
	 * &nbsp;&nbsp;&lt;U4 report-id&gt;<br />
	 * &nbsp;&nbsp;&lt;L [n]<br />
	 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;U4 vid-1&gt;<br />
	 * &nbsp;&nbsp;&nbsp;&nbsp;...<br />
	 * &nbsp;&nbsp;&lt;<br />
	 * &gt;.
	 * 
	 * @return S2F33-single-report
	 */
	public Secs2 toS2F33Report() {
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
	
	public List<Secs2> vids() {
		return Collections.unmodifiableList(vids);
	}
	
	@Override
	public int hashCode() {
		return reportId.hashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		if ( o != null && (o instanceof DynamicReport) ) {
			return ((DynamicReport)o).reportId.equals(reportId);
		}
		return false;
	}
}
