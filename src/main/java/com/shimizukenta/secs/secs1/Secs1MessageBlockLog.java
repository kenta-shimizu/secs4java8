package com.shimizukenta.secs.secs1;

import com.shimizukenta.secs.SecsLog;

public class Secs1MessageBlockLog extends SecsLog {
	
	public Secs1MessageBlockLog(CharSequence cs, Secs1MessageBlock block) {
		super(cs, block.toHeaderBytesString());
	}
}
