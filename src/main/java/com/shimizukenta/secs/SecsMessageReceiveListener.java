package com.shimizukenta.secs;

import java.util.EventListener;

public interface SecsMessageReceiveListener extends EventListener {

	public void receive(SecsMessage message);
}
