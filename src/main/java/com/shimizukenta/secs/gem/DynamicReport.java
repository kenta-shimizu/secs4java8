package com.shimizukenta.secs.gem;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.secs2.Secs2Exception;

/**
 * This interface is implementation of Define-Report in GEM (SEMI-E30)<br />
 * To create new instance, {@link #newInstance(Secs2, CharSequence, List)}<br />
 * To get alias, {@link #alias()}<br />
 * To get RPTID, {@link #reportId()}<br />
 * To get VIDs, {@link #vids()}<br />
 * To S2F33 Single RPTID, {@link #toS2F33Report()}
 * Instances of this class are immutable.
 * 
 * @author kenta-shimizu
 *
 */
public interface DynamicReport {
	
	/**
	 * Create new instance.
	 * 
	 * @param reportId
	 * @param alias
	 * @param vids
	 * @return newInstance
	 */
	public static DynamicReport newInstance(Secs2 reportId, CharSequence alias, List<? extends Secs2> vids) {
		
		return new AbstractDynamicReport(reportId, alias, vids) {
			private static final long serialVersionUID = 1L;
		};
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
	public Secs2 toS2F33Report();
	
	/**
	 * RPTID getter
	 * 
	 * @return SECS-II RPTID
	 */
	public Secs2 reportId();
	
	/**
	 * Alias getter
	 * 
	 * @return has value if aliased.
	 */
	public Optional<String> alias();
	
	/**
	 * VIDs getter
	 * 
	 * @return List of SECS-II VID
	 */
	public List<Secs2> vids();
	
	
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
		return newInstance(rptid, null, vids);
	}
	
}
