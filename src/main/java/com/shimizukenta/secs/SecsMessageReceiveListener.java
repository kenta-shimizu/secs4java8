package com.shimizukenta.secs;

import java.util.EventListener;

public interface SecsMessageReceiveListener extends EventListener {

	public void received(SecsMessage message);
}
