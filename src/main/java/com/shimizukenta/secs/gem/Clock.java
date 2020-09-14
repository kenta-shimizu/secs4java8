package com.shimizukenta.secs.gem;

import java.io.Serializable;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.secs2.Secs2Exception;

public class Clock implements Serializable {

	private static final long serialVersionUID = 7004605301054564451L;
	
	private static final int century;
	private static final int flacYear;
	
	static {
		int nowYear = LocalDateTime.now().getYear();
		century = (nowYear / 100) * 100;
		flacYear = nowYear % 100;
	}
	
	private final LocalDateTime dt;
	private Secs2 a12;
	private Secs2 a16;
	
	private Clock(LocalDateTime ldt) {
		this.dt = ldt;
		this.a12 = null;
		this.a16 = null;
	}
	
	public static Clock from(LocalDateTime ldt) {
		return new Clock(ldt);
	}
	
	public static Clock now() {
		return from(LocalDateTime.now());
	}
	
	public static Clock from(Secs2 secs2) throws Secs2Exception {
		
		String a = secs2.getAscii();
		int len = a.length();
		
		try {
			if ( len == 12 ) {
				
				int yyyy = getYear(a.substring(0, 2));
				int mm = Integer.valueOf(a.substring(2, 4));
				int dd = Integer.valueOf(a.substring(4, 6));
				int hh = Integer.valueOf(a.substring(6, 8));
				int ii = Integer.valueOf(a.substring(8, 10));
				int ss = Integer.valueOf(a.substring(10, 12));
				
				Clock c =  new Clock(LocalDateTime.of(yyyy, mm, dd, hh, ii, ss));
				c.a12 = secs2;
				return c;
				
			} else if ( len == 16 ) {
				
				int yyyy = Integer.valueOf(a.substring(0, 4));
				int mm = Integer.valueOf(a.substring(4, 6));
				int dd = Integer.valueOf(a.substring(6, 8));
				int hh = Integer.valueOf(a.substring(8, 10));
				int ii = Integer.valueOf(a.substring(10, 12));
				int ss = Integer.valueOf(a.substring(12, 14));
				int sss = Integer.valueOf(a.substring(14, 16)) * 10000000;
				
				Clock c = new Clock(LocalDateTime.of(yyyy, mm, dd, hh, ii, ss, sss));
				c.a16 = secs2;
				return c;
				
			} else {
				
				throw new Secs2Exception("Parse Failed \"" + a + "\"");
			}
		}
		catch ( DateTimeException | NumberFormatException e ) {
			throw new Secs2Exception(e);
		}
	}
	
	private static int getYear(String a2) {
		
		int yy = Integer.parseInt(a2);
		
		if ( flacYear < 25 ) {
			
			if ( yy >= 75 ) {
				return century - 100 + yy;
			}
			
		} else if ( flacYear >= 75 ) {
			
			if ( yy < 25 ) {
				return century + 100 + yy;
			}
		}
		
		return century + yy;
	}
	
	public LocalDateTime toLocalDateTime() {
		return dt;
	}
	
	private static DateTimeFormatter A14 = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
	
	public Secs2 toAscii12() {
		
		synchronized ( this ) {
			
			if ( this.a12 == null ) {
				String tt = dt.format(A14).substring(2, 14);
				this.a12 = Secs2.ascii(tt);
			}
			
			return this.a12;
		}
	}
	
	public Secs2 toAscii16() {
		
		synchronized ( this ) {
			
			if ( this.a16 == null ) {
				
				String tt = dt.format(A14);
				String ss = String.format("%02d", dt.getNano() / 10000000);
				this.a16 = Secs2.ascii(tt + ss);
			}
			
			return this.a16;
		}
	}
	
}
