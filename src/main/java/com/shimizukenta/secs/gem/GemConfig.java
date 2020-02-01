package com.shimizukenta.secs.gem;

import java.util.Objects;

public class GemConfig {

	private String mdln;
	private String softrev;
	
	public GemConfig() {
		mdln = "      ";
		softrev = "      ";
	}
	
	public void mdln(CharSequence cs) {
		synchronized ( this ) {
			this.mdln = Objects.requireNonNull(cs).toString();
		}
	}
	
	public String mdln() {
		synchronized ( this ) {
			return mdln;
		}
	}
	
	public void softrev(CharSequence cs) {
		synchronized ( this ) {
			this.softrev = Objects.requireNonNull(cs).toString();
		}
	}
	
	public String softrev() {
		synchronized ( this ) {
			return softrev;
		}
	}
}
