package com.shimizukenta.secs;

import java.util.EventListener;

public interface SecsLogListener extends EventListener {
	public void received(SecsLog log);
}
