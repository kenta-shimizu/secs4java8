package com.shimizukenta.secs.gem;

import java.io.Serializable;
import java.util.Objects;

public class GemConfig implements Serializable {
	
	private static final long serialVersionUID = 1130854092113358850L;
	
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
