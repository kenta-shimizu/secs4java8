package com.shimizukenta.secs.gem;

import java.time.LocalDateTime;

import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.secs2.Secs2Exception;

/**
 * This interface is implementation of Clock in GEM (SEMI-E30)<br />
 * Relates: S2F17, S2F18, S2F31<br />
 * Instances of this class are immutable.
 * 
 * @author kenta-shimizu
 *
 */
public interface Clock {
	
	/**
	 * Create new Clock instance from LocalDateTime.
	 * 
	 * @param LocalDateTime
	 * @return Clock-of-LocalDateTime
	 */
	public static Clock from(LocalDateTime ldt) {
		return AbstractClock.from(ldt);
	}
	
	/**
	 * Create new Clock instance of now.
	 * 
	 * @return Clock-of-Now
	 */
	public static Clock now() {
		return AbstractClock.now();
	}
	
	/**
	 * Create new Clock instance from Secs2<br />
	 * use for Secs2 of S2F18, S2F31
	 * 
	 * @param secs2
	 * @return Clock
	 * @throws Secs2Exception
	 */
	public static Clock from(Secs2 secs2) throws Secs2Exception {
		return AbstractClock.from(secs2);
	}
	
	/**
	 * 
	 * @return LocalDateTime
	 */
	public LocalDateTime toLocalDateTime();
	
	/**
	 * 
	 * @return Secs2.ascii(yyMMddhhmmss)
	 */
	public Secs2 toAscii12();
	
	/**
	 * 
	 * @return Secs2.ascii(yyyyMMddhhmmssSS)
	 */
	public Secs2 toAscii16();
	
}
